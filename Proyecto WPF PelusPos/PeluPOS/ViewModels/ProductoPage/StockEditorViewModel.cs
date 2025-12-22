using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;

namespace PeluPOS.ViewModels.ProductoPage
{
    public partial class StockEditorViewModel : ObservableObject
    {
        [ObservableProperty] private long stock;
        [ObservableProperty] private string? error;

        public bool IsValid => Stock >= 0;

        public void Validate()
        {
            Error = Stock < 0 ? "El stock no puede ser negativo." : null;
        }
    }
}
