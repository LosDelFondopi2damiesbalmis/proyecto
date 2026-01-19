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

namespace PeluPOS.ViewModels
{
    public partial class EmpleadosViewModel : ObservableObject
    {
        private readonly IEmpleadoService _service;
        private readonly List<Empleado> _todos = new();

        [ObservableProperty] private string? textoBusqueda;
        [ObservableProperty] private Empleado? empleadoSeleccionado;

        public ObservableCollection<Empleado> EmpleadosFiltrados { get; } = new();
        public ObservableCollection<Local> Locales { get; } = new();

        public EmpleadosViewModel(IEmpleadoService service)
        {
            _service = service;
        }

        public async Task LoadAsync()
        {
            _todos.Clear();
            var empleados = await _service.GetAllAsync();
            _todos.AddRange(empleados);

            Locales.Clear();
            foreach (var l in await _service.GetLocalesAsync())
                Locales.Add(l);

            AplicarFiltro();
        }

        partial void OnTextoBusquedaChanged(string? value) => AplicarFiltro();

        private void AplicarFiltro()
        {
            var filtro = TextoBusqueda?.Trim();
            IEnumerable<Empleado> res = _todos;

            if (!string.IsNullOrWhiteSpace(filtro))
                res = res.Where(e => e.Nombre.Contains(filtro, StringComparison.OrdinalIgnoreCase));

            EmpleadosFiltrados.Clear();
            foreach (var e in res) EmpleadosFiltrados.Add(e);
        }

        [RelayCommand]
        public async Task CreateAsync((string Nombre, long Telefono, string Email, string Cargo, long LocalId, string Password) data)
        {
            await _service.CreateAsync(data.Nombre, data.Telefono, data.Email, data.Cargo, data.LocalId, data.Password);
            await LoadAsync();
        }

        [RelayCommand]
        public async Task EditAsync((long Id, string Nombre, long Telefono, string Email, string Cargo, long LocalId) data)
        {
            await _service.UpdateAsync(data.Id, data.Nombre, data.Telefono, data.Email, data.Cargo, data.LocalId);
            await LoadAsync();
        }

        [RelayCommand]
        public async Task DeleteAsync(long id)
        {
            await _service.DeleteAsync(id);
            await LoadAsync();

            if (EmpleadoSeleccionado?.Id == id)
                EmpleadoSeleccionado = null;
        }
    }
}
