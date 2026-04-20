using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;
using PeluPOS.Models.Enums;

namespace PeluPOS.Services;

public static class SessionService
{
    public static event Action? SessionChanged;
    public static event Action? LoginRequired;

    public static string? JwtToken { get; private set; }
    public static long? CurrentUserId { get; private set; }
    public static string? CurrentUsername { get; private set; }
    public static Roles? CurrentRole { get; private set; }
    public static long? CurrentEmpleadoId { get; private set; }

    public static bool IsLoggedIn => !string.IsNullOrWhiteSpace(JwtToken);

    public static bool CanSeeSidebar =>
        CurrentRole == Roles.Administrador || CurrentRole == Roles.Manager;

    public static void Login(string token, long userId, string username, Roles role, long? empleadoId)
    {
        JwtToken = token;
        CurrentUserId = userId;
        CurrentUsername = username;
        CurrentRole = role;
        CurrentEmpleadoId = empleadoId;
        SessionChanged?.Invoke();
    }

    public static void Logout()
    {
        JwtToken = null;
        CurrentUserId = null;
        CurrentUsername = null;
        CurrentRole = null;
        CurrentEmpleadoId = null;

        SessionChanged?.Invoke();
        LoginRequired?.Invoke();
    }
}
