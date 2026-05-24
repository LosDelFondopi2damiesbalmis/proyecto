-- Insertar datos de prueba en la tabla Local
INSERT INTO Local (nombre, direccion) VALUES 
('Peluquería Sede Central', 'Calle Gran Vía 15, Madrid'),
('Barbería Norte', 'Avenida de la Ilustración 45, Valencia'),
('Salón de Belleza Sur', 'Plaza de España 2, Sevilla'),
('Peluquería Express', 'Centro Comercial El Saler, Local 12, Valencia');


-- Insertamos empleados y los asignamos a los locales usando el id_local
INSERT INTO Empleado (nombre, cargo, email, telefono, id_local) VALUES 
('Ana García', 'Gerente', 'ana@pelupos.com', 600111222, 1),      -- Trabaja en Sede Central (1)
('Marcos López', 'Estilista', 'marcos@pelupos.com', 600333444, 1), -- Trabaja en Sede Central (1)
('Lucía Fernández', 'Barbera', 'lucia@pelupos.com', 600555666, 2),
('Antonio Valls', 'Barbera', 'lucia@pelupos.com', 600555666, 2),
('David Marín', 'Barbera', 'lucia@pelupos.com', 600555666, 2), 
('Carlos Ruiz', 'Estilista', 'carlos@pelupos.com', 600777888, 3);  -- Trabaja en Salón Sur (3)

-- ==========================================
-- 1. CREAMOS DATOS BASE (Si no los tienes ya)
-- ==========================================
SELECT * FROM Empleado;
-- Insertamos 2 clientes
INSERT INTO Cliente (nombre, telefono) VALUES 
('Juan Pérez', 600111222),
('María Gómez', 600333444);

-- Insertamos 2 productos (Champú y Cera)
INSERT INTO Producto (nombre, precio_compra, precio_venta, stock) VALUES 
('Champú Premium', 5.00, 15.00, 50),
('Cera moldeadora', 3.00, 10.00, 30);

-- Insertamos 2 servicios (Corte y Tinte)
INSERT INTO Servicio (nombre, precio, descripcion, id_empleado) VALUES 
('Corte de pelo caballero', 15.00, 'Corte a tijera y máquina', 1),
('Tinte completo', 35.00, 'Coloración total', 1);

-- ==========================================
-- 2. CREAMOS LAS FACTURAS PARA EL EMPLEADO 1
-- ==========================================

-- FACTURA 1: Creada el día 5 de este mes (Monto: 30.00€)
-- Atendida por el Empleado 1, al Cliente 1
INSERT INTO Factura (monto, fecha, pendiente, tipo_pago, id_cliente, id_empleado) VALUES 
(30.00, '2026-03-05 10:30:00', FALSE, 'Tarjeta', 1, 1);

-- FACTURA 2: Creada el día 10 de este mes (Monto: 45.00€)
-- Atendida por el Empleado 1, al Cliente 2
INSERT INTO Factura (monto, fecha, pendiente, tipo_pago, id_cliente, id_empleado) VALUES 
(45.00, '2026-03-10 16:45:00', FALSE, 'Efectivo', 2, 1);

-- ==========================================
-- 3. LLENAMOS EL CARRITO DE ESAS FACTURAS
-- ==========================================

-- Para la Factura 1 (ID 1): Vendemos 1 Champú y hacemos 1 Corte
INSERT INTO Factura_Producto (id_factura, id_producto, cantidad, precio_vendido) VALUES 
(1, 1, 1, 15.00); -- Vendimos el producto 1 (Champú)

INSERT INTO Factura_Servicio (id_factura, id_servicio, cantidad, precio_cobrado) VALUES 
(1, 1, 1, 15.00); -- Hicimos el servicio 1 (Corte)

-- Para la Factura 2 (ID 2): Vendemos 1 Cera y hacemos 1 Tinte
INSERT INTO Factura_Producto (id_factura, id_producto, cantidad, precio_vendido) VALUES 
(2, 2, 1, 10.00); -- Vendimos el producto 2 (Cera)

INSERT INTO Factura_Servicio (id_factura, id_servicio, cantidad, precio_cobrado) VALUES 
(1, 2, 1, 35.00); -- Hicimos el servicio 2 (Tinte)

SELECT id_servicio, nombre FROM Servicio;
SELECT id_factura, monto FROM Factura;

-- Insertamos cuentas de usuario enlazadas a los empleados existentes
INSERT INTO Usuario (usuario, contrasena, id_empleado) VALUES 
('admin', '123456', 1),      -- Cuenta para Ana García (Gerente Sede Central)
('marcos', '123456', 2),     -- Cuenta para Marcos López (Estilista Sede Central)
('lucia', '123456', 3),      -- Cuenta para Lucía Fernández (Barbera Norte)
('carlos', '123456', 4);     -- Cuenta para Carlos Ruiz (Salón Sur)
-- 1. Apagamos la vigilancia de relaciones
INSERT INTO usuario (usuario, contrasena, id_empleado, rol_usuario) 
VALUES ('luciaAdmin', '123456', 5, 'ADMINISTRADOR'),
	   ('Franco', '123456', 6, 'MANAGER');

