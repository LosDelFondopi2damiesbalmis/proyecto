SET FOREIGN_KEY_CHECKS = 0;
-- 2. Vaciamos TODAS las tablas y reiniciamos los IDs a 1
TRUNCATE TABLE Factura_Producto;
TRUNCATE TABLE Factura_Servicio;
TRUNCATE TABLE Factura;
TRUNCATE TABLE Usuario;
TRUNCATE TABLE Servicio;
TRUNCATE TABLE Empleado;
TRUNCATE TABLE Local;
TRUNCATE TABLE Cliente;
TRUNCATE TABLE Producto;
TRUNCATE TABLE token_verificado;
-- 3. Volvemos a encender la vigilancia (¡Súper importante!)
SET FOREIGN_KEY_CHECKS = 1;