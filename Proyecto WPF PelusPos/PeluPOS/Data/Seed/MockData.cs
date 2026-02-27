using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PeluPOS.Models.Entities;

namespace PeluPOS.Data.Seed
{
    public static class MockData
    {
        public static List<Empleado> Empleados;
        public static List<Cliente> Clientes;
        public static List<Producto> Productos;
        public static List<Servicio> Servicios;
        public static List<Factura> Facturas;
        public static List<Local> Locales;

        static MockData()
        {
            // ======================
            // PRODUCTOS
            // ======================
            Productos = new()
    {
        new Producto { Id = 1, Nombre = "Cerveza", PrecioCompra = 0.40m, PrecioVenta = 1.50m, Stock = 200 },
        new Producto { Id = 2, Nombre = "Agua", PrecioCompra = 0.10m, PrecioVenta = 1.00m, Stock = 150 },
        new Producto { Id = 3, Nombre = "Refresco Cola", PrecioCompra = 0.50m, PrecioVenta = 2.00m, Stock = 180 },
        new Producto { Id = 4, Nombre = "Refresco Naranja", PrecioCompra = 0.45m, PrecioVenta = 1.80m, Stock = 120 },
        new Producto { Id = 5, Nombre = "Snacks Salados", PrecioCompra = 0.20m, PrecioVenta = 1.20m, Stock = 90 }
    };

            // ======================
            // SERVICIOS
            // ======================
            Servicios = new()
    {
        new Servicio { Id = 1, Nombre = "Corte de pelo", Precio = 12.50m, Descripcion = "Corte básico" },
        new Servicio { Id = 2, Nombre = "Afeitado clásico", Precio = 7.50m, Descripcion = "Afeitado tradicional" },
        new Servicio { Id = 3, Nombre = "Corte + Barba", Precio = 18.00m, Descripcion = "Combo masculino" },
        new Servicio { Id = 4, Nombre = "Tinte de pelo", Precio = 25.00m, Descripcion = "Coloración completa" },
        new Servicio { Id = 5, Nombre = "Lavado y Peinado", Precio = 10.00m, Descripcion = "Lavado intensivo" }
    };

            // ======================
            // LOCALES
            // ======================
            Locales = new()
    {
        new Local { Id = 1, Nombre = "Barbería Centro", Direccion = "Calle Principal 123" },
        new Local { Id = 2, Nombre = "Barbería Norte", Direccion = "Avenida Libertad 45" },
        new Local { Id = 3, Nombre = "Barbería Sur", Direccion = "Calle Sol 99" }
    };

            // ======================
            // EMPLEADOS
            // ======================
            Empleados = new()
    {
        new Empleado
        {
            Id=1,
            Nombre="Pedro Martínez",
            Telefono=600112233,
            Email="pedro@local.com",
            Cargo="Barbero",
            LocalId=1,
            Usuario = new Usuario { Id=1, Contrasena="1234", Roles = Models.Enums.Roles.Empleado },
            Servicios = { Servicios[0], Servicios[1], Servicios[2] }
        },
        new Empleado
        {
            Id=2,
            Nombre="Laura Gómez",
            Telefono=611223344,
            Email="laura@local.com",
            Cargo="Estilista",
            LocalId=1,
            Usuario = new Usuario { Id=2, Contrasena="abcd", Roles = Models.Enums.Roles.Manager },
            Servicios = { Servicios[4], Servicios[3] }
        },
        new Empleado
        {
            Id=3,
            Nombre="Carlos Ruiz",
            Telefono=622334455,
            Email="carlos@local.com",
            Cargo="Barbero Senior",
            LocalId=2,
            Usuario = new Usuario { Id=3, Contrasena="9876", Roles = Models.Enums.Roles.Administrador },
            Servicios = { Servicios[0], Servicios[2], Servicios[3] }
        }
    };

            // Asignar empleados a locales
            Locales[0].Empleados.Add(Empleados[0]);
            Locales[0].Empleados.Add(Empleados[1]);
            Locales[1].Empleados.Add(Empleados[2]);

            // ======================
            // CLIENTES
            // ======================
            Clientes = new()
    {
        new Cliente { Id=1, Nombre="Juan López", Telefono=611223344, Deuda=0 },
        new Cliente { Id=2, Nombre="María Pérez", Telefono=622112233, Deuda=5.50m },
        new Cliente { Id=3, Nombre="Sergio Ramos", Telefono=633221144, Deuda=0 },
        new Cliente { Id=4, Nombre="Ana Torres", Telefono=644332211, Deuda=12.00m }
    };

            // ======================
            // FACTURAS + LINEAS
            // ======================
            Facturas = new();

            // FACTURA 1
            var factura1 = new Factura
            {
                Id = 1,
                Fecha = DateTime.Today,
                TipoPago = "Efectivo",
                Pendiente = false,
                Cliente = Clientes[0],
                Empleado = Empleados[0],
            };
            factura1.Lineas.Add(new LineaFactura
            {
                Id = 1,
                Cantidad = 1,
                PrecioUnitario = Servicios[0].Precio,
                Servicio = Servicios[0]
            });

            // FACTURA 2
            var factura2 = new Factura
            {
                Id = 2,
                Fecha = DateTime.Today.AddDays(-1),
                TipoPago = "Tarjeta",
                Pendiente = false,
                Cliente = Clientes[1],
                Empleado = Empleados[1],
            };
            factura2.Lineas.Add(new LineaFactura
            {
                Id = 2,
                Cantidad = 2,
                PrecioUnitario = Productos[0].PrecioVenta,
                Producto = Productos[0]
            });

            // FACTURA 3
            var factura3 = new Factura
            {
                Id = 3,
                Fecha = DateTime.Today.AddDays(-2),
                TipoPago = "Efectivo",
                Pendiente = true,
                Cliente = Clientes[3],
                Empleado = Empleados[2],
            };
            factura3.Lineas.Add(new LineaFactura
            {
                Id = 3,
                Cantidad = 1,
                PrecioUnitario = Servicios[3].Precio,
                Servicio = Servicios[3]
            });

            Facturas.Add(factura1);
            Facturas.Add(factura2);
            Facturas.Add(factura3);

            // Asignar facturas a clientes y empleados
            Clientes[0].Facturas.Add(factura1);
            Empleados[0].Facturas.Add(factura1);

            Clientes[1].Facturas.Add(factura2);
            Empleados[1].Facturas.Add(factura2);

            Clientes[3].Facturas.Add(factura3);
            Empleados[2].Facturas.Add(factura3);
        }
    }
    }
