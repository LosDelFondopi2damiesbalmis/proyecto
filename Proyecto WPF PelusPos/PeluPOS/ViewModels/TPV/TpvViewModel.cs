using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using PeluPOS.Models.Entities;
using PeluPOS.Services;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace PeluPOS.ViewModels.TPV
{
    public partial class TpvViewModel : ObservableObject
    {
        private readonly ICatalogService _catalog;
        private readonly ITpvVentaService _venta;

        public ObservableCollection<TpvItemCard> Items { get; } = new();
        public ObservableCollection<TpvLineaViewModel> Ticket { get; } = new();

        [ObservableProperty] private bool modoProductos = true;
        [ObservableProperty] private string? busqueda;

        // Cobro
        [ObservableProperty] private string tipoPago = "Efectivo";
        [ObservableProperty] private bool pendiente;

        [ObservableProperty] private Cliente? clienteSeleccionado;
        [ObservableProperty] private string? error;

        public ObservableCollection<string> TiposPago { get; } = new()
        {
            "Efectivo", "Tarjeta", "Bizum", "Transferencia", "Otro"
        };

        public TpvViewModel(ICatalogService catalog, ITpvVentaService venta)
        {
            _catalog = catalog;
            _venta = venta;
        }

        public decimal Total => Ticket.Sum(t => t.Subtotal);

        public async Task LoadAsync()
        {
            await CargarItemsAsync();
            await CargarClienteMostradorSiExiste();
        }

        partial void OnModoProductosChanged(bool value)
        {
            _ = CargarItemsAsync();
        }

        partial void OnBusquedaChanged(string? value)
        {
            _ = CargarItemsAsync();
        }

        private async Task CargarItemsAsync()
        {
            var q = Busqueda?.Trim();

            Items.Clear();

            if (ModoProductos)
            {
                var productos = await _catalog.GetProductosAsync();
                var filtered = string.IsNullOrWhiteSpace(q)
                    ? productos
                    : productos.Where(p => p.Nombre.Contains(q, StringComparison.OrdinalIgnoreCase)).ToList();

                foreach (var p in filtered)
                    Items.Add(new TpvItemCard { Producto = p });
            }
            else
            {
                var servicios = await _catalog.GetServiciosAsync();
                var filtered = string.IsNullOrWhiteSpace(q)
                    ? servicios
                    : servicios.Where(s => s.Nombre.Contains(q, StringComparison.OrdinalIgnoreCase)).ToList();

                foreach (var s in filtered)
                    Items.Add(new TpvItemCard { Servicio = s });
            }
        }

        private async Task CargarClienteMostradorSiExiste()
        {
            var clientes = await _catalog.GetClientesAsync();
            ClienteSeleccionado = clientes.FirstOrDefault(c => c.Nombre.Contains("Mostrador", StringComparison.OrdinalIgnoreCase));
        }

        [RelayCommand]
        public void AddItem(TpvItemCard item)
        {
            Error = null;

            if (item.EsProducto && !item.Disponible)
            {
                Error = "Sin stock.";
                return;
            }

            // Buscar si ya existe línea del mismo item
            var existing = Ticket.FirstOrDefault(t =>
                (item.Producto != null && t.Linea.Producto?.Id == item.Producto.Id) ||
                (item.Servicio != null && t.Linea.Servicio?.Id == item.Servicio.Id));

            if (existing != null)
            {
                existing.Cantidad += 1;
                OnPropertyChanged(nameof(Total));
                return;
            }

            var linea = new LineaFactura
            {
                Id = 0, // se asigna al guardar en MockTpvVentaService
                Cantidad = 1,
                PrecioUnitario = item.Precio,
                Producto = item.Producto,
                Servicio = item.Servicio
            };

            Ticket.Add(new TpvLineaViewModel(linea));
            OnPropertyChanged(nameof(Total));
        }

        [RelayCommand]
        public void Inc(TpvLineaViewModel linea)
        {
            linea.Cantidad += 1;
            OnPropertyChanged(nameof(Total));
        }

        [RelayCommand]
        public void Dec(TpvLineaViewModel linea)
        {
            if (linea.Cantidad > 1) linea.Cantidad -= 1;
            OnPropertyChanged(nameof(Total));
        }

        [RelayCommand]
        public void RemoveLine(TpvLineaViewModel linea)
        {
            Ticket.Remove(linea);
            OnPropertyChanged(nameof(Total));
        }

        [RelayCommand]
        public async Task CobrarAsync(Empleado empleadoActivo)
        {
            Error = null;

            if (empleadoActivo == null)
            {
                Error = "No hay empleado activo. Inicia sesión.";
                return;
            }

            if (Ticket.Count == 0)
            {
                Error = "El ticket está vacío.";
                return;
            }

            if (ClienteSeleccionado == null)
            {
                Error = "Selecciona un cliente.";
                return;
            }

            var lineas = Ticket.Select(t => t.Linea).ToList();

            try
            {
                await _venta.CrearFacturaAsync(
                    empleadoActivo,
                    ClienteSeleccionado,
                    TipoPago,
                    Pendiente,
                    lineas
                );

                // Reset TPV
                Ticket.Clear();
                Pendiente = false;
                TipoPago = "Efectivo";
                OnPropertyChanged(nameof(Total));
            }
            catch (Exception ex)
            {
                Error = ex.Message;
            }
        }
    }
}
