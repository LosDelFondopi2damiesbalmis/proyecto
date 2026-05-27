using System;
using System.Windows.Controls;
using PeluPOS.ViewModels;

namespace PeluPOS.Views
{
    public partial class DashboardPage : Page
    {
        private readonly DashboardViewModel _vm;

        public DashboardPage()
        {
            InitializeComponent();
            _vm = new DashboardViewModel();
            DataContext = _vm;
            DateLabel.Text = DateTime.Now.ToString("dddd, d 'de' MMMM 'de' yyyy",
                new System.Globalization.CultureInfo("es-ES"));
            Loaded += async (_, __) => await _vm.LoadAsync();
        }
    }
}
