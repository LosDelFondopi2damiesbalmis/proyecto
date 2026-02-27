using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.ViewModels.TPV
{
    public partial class SelectUserViewModel : ObservableObject
    {
        [ObservableProperty]
        public ObservableCollection<Empleado> users;

        [ObservableProperty] 
        private Empleado? selectedUser;
        [ObservableProperty] 
        private string? error;

        public SelectUserViewModel()
        {
            Users = new ObservableCollection<Empleado>(MockData.Empleados);
        }
    }
}
