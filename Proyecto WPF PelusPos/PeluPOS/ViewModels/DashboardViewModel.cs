using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using PeluPOS.Services;

namespace PeluPOS.ViewModels
{
    public class DashboardVentaRow
    {
        public string Hora { get; set; } = "";
        public string Cliente { get; set; } = "";
        public string Concepto { get; set; } = "";
        public string Total { get; set; } = "";
    }

    public class DashboardEmpleadoRow
    {
        public string Puesto { get; set; } = "";
        public string Nombre { get; set; } = "";
        public string NumServicios { get; set; } = "";
        public string TotalImporte { get; set; } = "";
    }

    public class DashboardViewModel : INotifyPropertyChanged
    {
        // ── KPIs ────────────────────────────────────────────────────────────
        private int _ventasHoy;
        public int VentasHoy
        {
            get => _ventasHoy;
            set { _ventasHoy = value; OnPropertyChanged(); }
        }

        private string _importeHoy = "0 €";
        public string ImporteHoy
        {
            get => _importeHoy;
            set { _importeHoy = value; OnPropertyChanged(); }
        }

        private int _clientesHoy;
        public int ClientesHoy
        {
            get => _clientesHoy;
            set { _clientesHoy = value; OnPropertyChanged(); }
        }

        private int _stockBajo;
        public int StockBajo
        {
            get => _stockBajo;
            set { _stockBajo = value; OnPropertyChanged(); }
        }

        // ── Listas ───────────────────────────────────────────────────────────
        public ObservableCollection<DashboardVentaRow> UltimasVentas { get; } = new();
        public ObservableCollection<DashboardEmpleadoRow> TopEmpleados { get; } = new();

        // ── Estado de carga ──────────────────────────────────────────────────
        private bool _isLoading = true;
        public bool IsLoading
        {
            get => _isLoading;
            set { _isLoading = value; OnPropertyChanged(); }
        }

        private string _errorMessage = "";
        public string ErrorMessage
        {
            get => _errorMessage;
            set { _errorMessage = value; OnPropertyChanged(); OnPropertyChanged(nameof(HasError)); }
        }

        // ── Propiedades booleanas para visibilidad en XAML ───────────────────
        public bool HasError    => !string.IsNullOrEmpty(_errorMessage);
        public bool HasVentas   => UltimasVentas.Count > 0;
        public bool HasEmpleados => TopEmpleados.Count > 0;

        // ── Carga de datos ───────────────────────────────────────────────────
        public async Task LoadAsync()
        {
            IsLoading = true;
            ErrorMessage = "";
            try
            {
                var today = DateTime.Today;

                // Cargar facturas y productos en paralelo
                var facturasTask = AppServices.FacturaApi.GetAllAsync();
                var productosTask = AppServices.ProductoApi.GetAllAsync();

                await Task.WhenAll(facturasTask, productosTask);

                var facturas = facturasTask.Result;
                var productos = productosTask.Result;

                // ── KPI: Ventas hoy / Importe hoy / Clientes hoy ────────────
                var facturasHoy = facturas
                    .Where(f => f.fecha.Date == today)
                    .ToList();

                VentasHoy = facturasHoy.Count;
                ImporteHoy = facturasHoy.Sum(f => f.monto).ToString("N2") + " €";
                ClientesHoy = facturasHoy
                    .Select(f => f.idCliente?.idCliente)
                    .Where(id => id.HasValue)
                    .Distinct()
                    .Count();

                // ── KPI: Stock bajo (stock <= 5) ─────────────────────────────
                StockBajo = productos.Count(p => p.stock <= 5);

                // ── Últimas ventas (las 5 más recientes del día o generales) ─
                var recientes = facturas
                    .OrderByDescending(f => f.fecha)
                    .Take(8)
                    .ToList();

                UltimasVentas.Clear();
                OnPropertyChanged(nameof(HasVentas));
                foreach (var f in recientes)
                {
                    // Determinar concepto: primer servicio, si no, primer producto
                    string concepto = "";
                    if (f.facturaServicioCollection?.Count > 0)
                    {
                        var nombres = f.facturaServicioCollection
                            .Where(s => s.servicio?.nombre != null)
                            .Select(s => s.servicio!.nombre)
                            .ToList();
                        concepto = nombres.Count > 0
                            ? string.Join(", ", nombres.Take(2))
                            : "Servicio";
                    }
                    else if (f.facturaProductoCollection?.Count > 0)
                    {
                        var nombres = f.facturaProductoCollection
                            .Where(p => p.producto?.nombre != null)
                            .Select(p => p.producto!.nombre)
                            .ToList();
                        concepto = nombres.Count > 0
                            ? string.Join(", ", nombres.Take(2))
                            : "Producto";
                    }
                    else
                    {
                        concepto = "Venta";
                    }

                    UltimasVentas.Add(new DashboardVentaRow
                    {
                        Hora = f.fecha.ToString("HH:mm"),
                        Cliente = f.idCliente?.nombre ?? "—",
                        Concepto = concepto,
                        Total = f.monto.ToString("N2") + " €"
                    });
                }

                // ── Top empleados del mes actual ──────────────────────────────
                var inicioMes = new DateTime(today.Year, today.Month, 1);
                var facturasDelMes = facturas
                    .Where(f => f.fecha >= inicioMes)
                    .ToList();

                var porEmpleado = facturasDelMes
                    .Where(f => f.idEmpleado != null)
                    .GroupBy(f => f.idEmpleado!.idEmpleado)
                    .Select(g => new
                    {
                        Nombre = g.First().idEmpleado?.nombre ?? "—",
                        NumFacturas = g.Count(),
                        Total = g.Sum(f => f.monto)
                    })
                    .OrderByDescending(x => x.Total)
                    .Take(5)
                    .ToList();

                TopEmpleados.Clear();
                OnPropertyChanged(nameof(HasEmpleados));
                for (int i = 0; i < porEmpleado.Count; i++)
                {
                    var emp = porEmpleado[i];
                    TopEmpleados.Add(new DashboardEmpleadoRow
                    {
                        Puesto = $"{i + 1}.",
                        Nombre = emp.Nombre,
                        NumServicios = $"{emp.NumFacturas} ventas",
                        TotalImporte = emp.Total.ToString("N2") + " €"
                    });
                }
                OnPropertyChanged(nameof(HasVentas));
                OnPropertyChanged(nameof(HasEmpleados));
            }
            catch (Exception ex)
            {
                ErrorMessage = $"Error al cargar datos: {ex.Message}";
            }
            finally
            {
                IsLoading = false;
            }
        }

        // ── INotifyPropertyChanged ───────────────────────────────────────────
        public event PropertyChangedEventHandler? PropertyChanged;
        private void OnPropertyChanged([CallerMemberName] string? name = null)
            => PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));
    }
}
