using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Data.Seed;
using PeluPOS.Models.Entities;

namespace PeluPOS.Services
{
    public partial class LoginViewModel : ObservableObject
    {
        [ObservableProperty]
        public ObservableCollection<Empleado> users;

        [ObservableProperty]
        private Empleado? selectedUser;
        [ObservableProperty]
        private string? error;

        public LoginViewModel()
        {
            Users = new ObservableCollection<Empleado>(MockData.Empleados);
        }
    }
}
