CREATE SCHEMA peluposbd DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

use peluposbd;


-- ==========================================
-- 1. TABLAS PRINCIPALES (Sin dependencias)
-- ==========================================

CREATE TABLE Local (
    id_local BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL
);

CREATE TABLE Cliente (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    deuda DECIMAL(10, 2) DEFAULT 0.0,
    telefono BIGINT
);

CREATE TABLE Producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio_compra DECIMAL(10, 2) NOT NULL,
    precio_venta DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
);

-- ==========================================
-- 2. TABLAS CON DEPENDENCIAS (Claves Foráneas)
-- ==========================================

CREATE TABLE Empleado (
    id_empleado BIGINT AUTO_INCREMENT PRIMARY KEY,
    telefono BIGINT,
    email VARCHAR(100),
    cargo VARCHAR(50),
    nombre VARCHAR(100) NOT NULL,
    id_local BIGINT,
    FOREIGN KEY (id_local) REFERENCES Local(id_local) ON DELETE SET NULL
);

CREATE TABLE Usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    id_empleado BIGINT UNIQUE, -- UNIQUE porque un empleado suele tener solo 1 usuario
    FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado) ON DELETE CASCADE
);

CREATE TABLE Servicio (
    id_servicio BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion TEXT,
    id_empleado BIGINT, -- El empleado que realiza este servicio por defecto
    FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado) ON DELETE SET NULL
);

-- ==========================================
-- 3. LA TABLA CENTRAL: FACTURA
-- ==========================================

CREATE TABLE Factura (
    id_factura BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATETIME NOT NULL,
    pendiente BOOLEAN DEFAULT FALSE,
    tipo_pago VARCHAR(50),
    id_cliente BIGINT,
    id_empleado BIGINT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE SET NULL,
    FOREIGN KEY (id_empleado) REFERENCES Empleado(id_empleado) ON DELETE SET NULL
);

-- ==========================================
-- 4. TABLAS INTERMEDIAS (Para las List<>)
-- ==========================================

-- Relaciona Facturas con Productos (Un carrito de la compra)
CREATE TABLE Factura_Producto (
    id_factura BIGINT,
    id_producto BIGINT,
    cantidad INT DEFAULT 1, -- ¡Ojo! He añadido 'cantidad', es vital para un TPV
    precio_vendido DECIMAL(10, 2), -- Guardar el precio al que se vendió en ese momento
    PRIMARY KEY (id_factura, id_producto),
    FOREIGN KEY (id_factura) REFERENCES Factura(id_factura) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto) ON DELETE CASCADE
);

-- Relaciona Facturas con Servicios
CREATE TABLE Factura_Servicio (
    id_factura BIGINT,
    id_servicio BIGINT,
    cantidad INT DEFAULT 1,
    precio_cobrado DECIMAL(10, 2),
    PRIMARY KEY (id_factura, id_servicio),
    FOREIGN KEY (id_factura) REFERENCES Factura(id_factura) ON DELETE CASCADE,
    FOREIGN KEY (id_servicio) REFERENCES Servicio(id_servicio) ON DELETE CASCADE
);