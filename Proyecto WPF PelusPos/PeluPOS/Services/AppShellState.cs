using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PeluPOS.Services
{
    public class AppShellState : INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler? PropertyChanged;

        private bool _isTpvMode;
        public bool IsTpvMode
        {
            get => _isTpvMode;
            set
            {
                if (_isTpvMode == value) return;
                _isTpvMode = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(nameof(IsTpvMode)));
            }
        }
    }
}
