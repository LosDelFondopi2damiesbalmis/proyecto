using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Data;

namespace PeluPOS.Resources.Converters
{
    public sealed class InverseBoolToVisibilityConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            // true  => Collapsed
            // false => Visible
            if (value is bool b)
                return b ? Visibility.Collapsed : Visibility.Visible;

            // Si no llega un bool, por seguridad no mostramos
            return Visibility.Collapsed;
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            // Collapsed => true (TPV mode), Visible => false
            if (value is Visibility v)
                return v != Visibility.Visible;

            return true;
        }
    }
}
