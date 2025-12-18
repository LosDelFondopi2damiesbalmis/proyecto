using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;
using PeluPOS.Services.Locales;

namespace PeluPOS.ViewModels.LocalPage
{
    public partial class LocalesViewModel : ObservableObject
    {
        private readonly ILocalService _localService;

        [ObservableProperty] private Local? localSeleccionado;
        [ObservableProperty] private bool isLoading;

        public ObservableCollection<Local> Locales { get; } = new();

        public LocalesViewModel(ILocalService localService)
        {
            _localService = localService;
        }

        public async Task LoadAsync()
        {
            if (IsLoading) return;
            IsLoading = true;

            try
            {
                Locales.Clear();
                var items = await _localService.GetAllAsync();
                foreach (var l in items)
                    Locales.Add(l);
            }
            finally
            {
                IsLoading = false;
            }
        }

        public async Task AddLocalAsync(string nombre, string direccion)
        {
            var local = await _localService.AddAsync(nombre, direccion);
            Locales.Add(local);
            LocalSeleccionado = local;
        }
    }
}
