using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommunityToolkit.Mvvm.ComponentModel;
using PeluPOS.Models.Entities;

namespace PeluPOS.ViewModels.TPV
{
    public partial class SelectUserViewModel : ObservableObject
    {
        public ObservableCollection<Usuario> Users { get; } = new();

        [ObservableProperty] 
        private Usuario? selectedUser;
        [ObservableProperty] 
        private string? error;
    }
}
