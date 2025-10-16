# Joel Vives Documentación

## Descripcion del problema

En el sector de la peluquería suele haber mucho descontrol sobre la gestion de los clientes y del stock de la tienda. Ademas muchas veces la gente que trabaja en estos sitios no estan muy familiarizados con las tecnologías y suelen tomar caminos mas tradicionales para la gestión lo cual limita mucho la producción y el uso de los productos entre otros problemas.

## Descripción de la solución propuesta

**Nombre de la aplicacion** es una solución software multiplataforma que esta diseñada para ayudar a la gestión de empleados, de clientes y de stock. El sistema tendra tres componentes principales:

1. **Aplicacion Movil para los empleados de a pie (Android Kotlin y Jetpack Compose):** Será algo opcional que podrán tener las peluquerías con mas empleados pero que no será obligatorio para las peluqerías mas pequeñas. Aquí se tendra acceso a las herramientas mas sencillas de la aplicación y no habra acceso a nada de administración
2. **Aplicación escritorio para el Gerente(WPF con C# y MVVM):** Una herramienta con acceso total a todas las herramientas de la aplicación como crear clientes, crear proveedores, formas de pago, productos y los servicios de la tienda. Además de tener acceso a todos los datos y el rendimiento general de la empresa.
3. **Servicio Backend (API Rest con Java):** Gestionara la lógica de la aplicación y la autenticación del usuario mediante JWT y asegurará una comunicación segura con la base de datos.

![pepe](out/diagram/diagram.svg)

## Planificación Aproximada

| Sprint | Semanas | Fecha | Entrega Clave |
| -- | ----- | ---- | ------------- |
| 1 | 7-8 | 31 oct | Modelo de dominio Java + diagramas |
