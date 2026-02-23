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
        public static Usuario? CurrentUser { get; private set; }
        public static Empleado? CurrentEmpleado { get; private set; }

        public static bool IsLoggedIn => CurrentUser != null;

        public static bool IsAdmin =>
            CurrentUser?.Roles == Roles.Administrador;

        public static bool IsManager =>
            CurrentUser?.Roles == Roles.Manager;

        public static bool IsEmpleado =>
            CurrentUser?.Roles == Roles.Empleado;

        public static void Login(Usuario usuario)
        {
            CurrentUser = usuario;
            CurrentEmpleado = usuario.Empleado;
        }

        public static void Logout()
        {
            CurrentUser = null;
            CurrentEmpleado = null;
        }
    }
}
