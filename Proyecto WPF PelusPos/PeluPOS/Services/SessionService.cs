using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;
using PeluPOS.Models.Enums;

namespace PeluPOS.Services
{
    public static class SessionService
    {
        public static event Action? SessionChanged;

        public static Usuario? CurrentUser { get; private set; }
        public static Empleado? CurrentEmpleado { get; private set; }

        public static bool IsLoggedIn => CurrentUser != null;

        public static bool CanAccessDashboard =>
            CurrentUser?.Roles == Roles.Administrador ||
            CurrentUser?.Roles == Roles.Manager;

        public static void Login(Usuario user, Empleado empleado)
        {
            CurrentUser = user;
            CurrentEmpleado = empleado;
            SessionChanged?.Invoke();
        }

        public static void Logout()
        {
            CurrentUser = null;
            CurrentEmpleado = null;
            SessionChanged?.Invoke();
        }
    }
}
