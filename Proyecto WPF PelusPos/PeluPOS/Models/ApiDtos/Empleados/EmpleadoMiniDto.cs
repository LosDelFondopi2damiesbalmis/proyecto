public static class RoleMapper
{
    public static Roles Parse(string role) => role.ToUpperInvariant() switch
    {
        "ADMINISTRADOR" => Roles.Administrador,
        "MANAGER" => Roles.Manager,
        "EMPLEADO" => Roles.Empleado,
        _ => throw new ArgumentOutOfRangeException(nameof(role), $"Rol no válido: {role}")
    };
}