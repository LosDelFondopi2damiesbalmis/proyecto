# Manual de Usuario — PeluPos

> Sistema de gestión para peluquerías y barberías  
> Versión del documento: 1.0 — Mayo 2026

---

## Índice

1. [Introducción](#1-introducción)
2. [Arquitectura del Sistema](#2-arquitectura-del-sistema)
3. [Aplicación de Escritorio — WPF (Windows)](#3-aplicación-de-escritorio--wpf-windows)
   - [3.1 Requisitos del sistema](#31-requisitos-del-sistema)
   - [3.2 Inicio de sesión](#32-inicio-de-sesión)
   - [3.3 Dashboard principal](#33-dashboard-principal)
   - [3.4 Gestión de Clientes](#34-gestión-de-clientes)
   - [3.5 Gestión de Empleados](#35-gestión-de-empleados)
   - [3.6 Gestión de Locales](#36-gestión-de-locales)
   - [3.7 Gestión de Productos](#37-gestión-de-productos)
   - [3.8 Gestión de Servicios](#38-gestión-de-servicios)
   - [3.9 TPV — Terminal Punto de Venta](#39-tpv--terminal-punto-de-venta)
   - [3.10 Gestión de Ventas y Facturas](#310-gestión-de-ventas-y-facturas)
   - [3.11 Gestión de Usuarios](#311-gestión-de-usuarios)
   - [3.12 Roles y permisos](#312-roles-y-permisos)
4. [Aplicación Móvil — Android (Kotlin)](#4-aplicación-móvil--android-kotlin)
   - [4.1 Requisitos del sistema](#41-requisitos-del-sistema)
   - [4.2 Instalación](#42-instalación)
   - [4.3 Inicio de sesión y permisos](#43-inicio-de-sesión-y-permisos)
   - [4.4 Pantalla principal — Dashboard](#44-pantalla-principal--dashboard)
   - [4.5 Clientes](#45-clientes)
   - [4.6 Empleados](#46-empleados)
   - [4.7 Locales](#47-locales)
   - [4.8 Productos](#48-productos)
   - [4.9 Servicios](#49-servicios)
   - [4.10 TPV](#410-tpv)
   - [4.11 Ventas](#411-ventas)
   - [4.12 Usuarios](#412-usuarios)
5. [Documentación de la API REST](#5-documentación-de-la-api-rest)
   - [5.1 Información general](#51-información-general)
   - [5.2 Autenticación (Auth)](#52-autenticación-auth)
   - [5.3 Productos](#53-productos)
   - [5.4 Clientes](#54-clientes)
   - [5.5 Empleados](#55-empleados)
   - [5.6 Facturas](#56-facturas)
   - [5.7 Locales](#57-locales)
   - [5.8 Servicios](#58-servicios)
   - [5.9 Usuarios](#59-usuarios)
   - [5.10 Códigos de respuesta HTTP](#510-códigos-de-respuesta-http)
6. [Base de datos](#6-base-de-datos)
7. [Preguntas frecuentes (FAQ)](#7-preguntas-frecuentes-faq)

---

## 1. Introducción

**PeluPos** es un sistema de punto de venta (POS) diseñado específicamente para la gestión integral de peluquerías y barberías. Permite controlar clientes, empleados, productos, servicios, ventas y varios locales desde una sola plataforma.

El sistema está compuesto por tres módulos que trabajan de forma conjunta:

| Componente | Tecnología | Descripción |
|---|---|---|
| Aplicación de escritorio | C# / WPF (.NET) | Cliente para Windows con interfaz gráfica completa |
| Aplicación móvil | Kotlin / Jetpack Compose (Android) | App nativa para dispositivos Android |
| Backend / API REST | Java / Jakarta EE 10 + MySQL | Servidor central con base de datos relacional |

### Funcionalidades principales

- Gestión de clientes con seguimiento de deuda pendiente
- Gestión de empleados por local / sucursal
- Control de múltiples locales o sucursales
- Catálogo de productos con control de stock
- Catálogo de servicios con precio y empleado asignado
- Terminal Punto de Venta (TPV) con carrito de compra
- Registro de facturas con productos y servicios
- Gestión de usuarios con roles diferenciados (Administrador, Manager, Empleado)
- Autenticación segura mediante tokens JWT

---

## 2. Arquitectura del Sistema

```
┌─────────────────────┐        ┌────────────────────────┐
│  App WPF (Windows)  │        │  App Android (Kotlin)  │
│  C# / .NET / XAML   │        │  Kotlin / Compose      │
└────────┬────────────┘        └───────────┬────────────┘
         │                                 │
         │         HTTP + JWT              │
         └────────────────┬────────────────┘
                          │
                ┌─────────▼──────────┐
                │   API REST         │
                │   Java / Jakarta   │
                │   EE 10            │
                │   Puerto 8080      │
                └─────────┬──────────┘
                          │
                ┌─────────▼──────────┐
                │   Base de datos    │
                │   MySQL            │
                │   peluposbd        │
                └────────────────────┘
```

Todas las peticiones al servidor requieren un token JWT (excepto el login). El token se obtiene en el endpoint de login y debe enviarse en la cabecera `Authorization: Bearer <token>` en cada petición.

---

## 3. Aplicación de Escritorio — WPF (Windows)

### 3.1 Requisitos del sistema

| Requisito | Mínimo |
|---|---|
| Sistema operativo | Windows 10 / Windows 11 |
| .NET | .NET 6.0 o superior |
| RAM | 2 GB |
| Conexión de red | Necesaria (conexión al servidor API) |

### 3.2 Inicio de sesión

Al arrancar la aplicación se muestra el diálogo de **inicio de sesión**.

1. Introduce tu **nombre de usuario** en el campo correspondiente.
2. Introduce tu **contraseña**.
3. Pulsa el botón **Iniciar sesión**.

> ⚠️ Si las credenciales son incorrectas, se mostrará un mensaje de error. Contacta con el administrador del sistema si no recuerdas tu contraseña.

El sistema tiene tres niveles de acceso con diferentes permisos (ver [sección 3.12](#312-roles-y-permisos)).

### 3.3 Dashboard principal

Tras el inicio de sesión correcto, accedes al **Dashboard**. Esta pantalla muestra un resumen del estado del negocio:

- Tarjetas de resumen con totales de clientes, empleados, productos, servicios y facturas.
- Acceso rápido a todos los módulos a través del menú lateral.

El menú lateral permite navegar entre las secciones:

| Ítem del menú | Módulo |
|---|---|
| Clientes | Gestión de clientes |
| Empleados | Gestión de empleados |
| Locales | Gestión de sucursales |
| Productos | Catálogo y stock |
| Servicios | Catálogo de servicios |
| TPV | Terminal Punto de Venta |
| Ventas | Historial de facturas |
| Usuarios | Gestión de cuentas de acceso |

### 3.4 Gestión de Clientes

**Ruta:** Menú lateral → Clientes

En esta pantalla se muestra una lista con todos los clientes registrados. Para cada cliente se muestra:

- Nombre completo
- Teléfono de contacto
- Deuda pendiente (en euros)

#### Añadir un cliente

1. Pulsa el botón **Nuevo cliente**.
2. Rellena los campos del formulario:
   - **Nombre** *(obligatorio)*
   - **Teléfono**
   - **Deuda inicial** (por defecto 0,00 €)
3. Pulsa **Guardar**.

#### Editar un cliente

1. Selecciona el cliente en la lista.
2. Pulsa el botón **Editar** o haz doble clic sobre el registro.
3. Modifica los campos necesarios.
4. Pulsa **Guardar**.

#### Eliminar un cliente

1. Selecciona el cliente en la lista.
2. Pulsa **Eliminar**.
3. Confirma la acción en el diálogo de confirmación.

> ⚠️ Al eliminar un cliente, las facturas asociadas conservan su registro histórico pero la referencia al cliente queda como nula.

### 3.5 Gestión de Empleados

**Ruta:** Menú lateral → Empleados

Muestra la lista de todos los empleados dados de alta en el sistema.

#### Campos de un empleado

| Campo | Descripción |
|---|---|
| Nombre | Nombre completo del empleado |
| Cargo | Puesto de trabajo (p. ej. Barbero, Estilista) |
| Email | Correo electrónico |
| Teléfono | Número de contacto |
| Local | Sucursal a la que pertenece |

#### Añadir un empleado

1. Pulsa **Nuevo empleado**.
2. Rellena el formulario en el diálogo **EmpleadoDialog**.
3. Selecciona el local al que pertenece.
4. Pulsa **Guardar**.

#### Ver facturas de un empleado

1. Selecciona un empleado de la lista.
2. Pulsa **Ver facturas**.
3. Se abrirá la pantalla **EmpleadoFacturasPage** con el historial de ventas asociadas a ese empleado.

#### Editar / Eliminar

El funcionamiento es idéntico al descrito para clientes.

### 3.6 Gestión de Locales

**Ruta:** Menú lateral → Locales

Permite gestionar las distintas sucursales o establecimientos de la empresa.

#### Campos de un local

| Campo | Descripción |
|---|---|
| Nombre | Nombre del establecimiento |
| Dirección | Dirección física completa |

#### Añadir un local

1. Pulsa **Nuevo local**.
2. Rellena el formulario en el diálogo **NuevoLocalDialog**.
3. Pulsa **Guardar**.

> Los locales son referenciados por los empleados. No es posible eliminar un local si tiene empleados activos asignados.

### 3.7 Gestión de Productos

**Ruta:** Menú lateral → Productos

Gestiona el catálogo de productos físicos disponibles para la venta (champús, gominas, tintes, etc.).

#### Campos de un producto

| Campo | Descripción |
|---|---|
| Nombre | Nombre del producto |
| Precio de compra | Coste de adquisición |
| Precio de venta | Precio al que se vende al cliente |
| Stock | Unidades disponibles en almacén |

#### Añadir un producto

1. Pulsa **Nuevo producto** → se abre **ProductoDialog**.
2. Rellena todos los campos.
3. Pulsa **Guardar**.

#### Ajustar stock

1. Selecciona el producto.
2. Pulsa **Ajustar stock** → se abre **StockDialog**.
3. Introduce la cantidad a añadir o restar.
4. Pulsa **Confirmar**.

#### Ver facturas de un producto

Pulsa **Ver facturas** sobre un producto seleccionado para acceder al historial de ventas en las que aparece ese producto (**FacturasProductoPage**).

### 3.8 Gestión de Servicios

**Ruta:** Menú lateral → Servicios

Gestiona el catálogo de servicios que ofrecen los empleados (corte, tinte, manicura, etc.).

#### Campos de un servicio

| Campo | Descripción |
|---|---|
| Nombre | Nombre del servicio |
| Precio | Precio que se cobra al cliente |
| Descripción | Descripción detallada del servicio |
| Empleado | Empleado asignado por defecto para este servicio |

#### Añadir un servicio

1. Pulsa **Nuevo servicio** → se abre **ServicioDialog**.
2. Rellena los campos y selecciona el empleado responsable.
3. Pulsa **Guardar**.

#### Ver facturas de un servicio

Pulsa **Ver facturas** para ver el historial de facturas en que se incluyó ese servicio (**FacturasServicioPage**).

### 3.9 TPV — Terminal Punto de Venta

**Ruta:** Menú lateral → TPV

El TPV es el módulo principal para registrar ventas en tiempo real. Permite añadir productos y servicios a un carrito y generar una factura al finalizar.

#### Flujo de uso del TPV

1. **Seleccionar cliente:** Busca o selecciona el cliente en el desplegable. Si el cliente no existe, puedes crearlo desde el módulo de Clientes.
2. **Seleccionar empleado:** Selecciona el empleado que realiza la venta.
3. **Añadir productos:** Busca un producto en la lista y pulsa el botón **+** para añadirlo al carrito. Puedes modificar la cantidad directamente en el carrito.
4. **Añadir servicios:** De igual forma, busca un servicio y añádelo al carrito.
5. **Revisar el carrito:** En la zona lateral se muestra el resumen de la venta con subtotales por cada línea.
6. **Seleccionar método de pago:** Elige entre las opciones disponibles (Efectivo, Tarjeta, etc.).
7. **Cobrar:** Pulsa el botón **Cobrar** para generar la factura. Si el cliente tiene deuda, el sistema la tendrá en cuenta.
8. **Pago pendiente:** Si el cliente no puede pagar en ese momento, marca la factura como **Pendiente** para registrar la deuda.

> ⚠️ Al cobrar, el stock de los productos incluidos se reduce automáticamente.

### 3.10 Gestión de Ventas y Facturas

**Ruta:** Menú lateral → Ventas

Muestra el historial completo de todas las facturas generadas en el sistema.

#### Información de una factura

| Campo | Descripción |
|---|---|
| Nº Factura | Identificador único |
| Fecha | Fecha y hora de la venta |
| Cliente | Cliente asociado |
| Empleado | Empleado que realizó la venta |
| Monto | Importe total |
| Tipo de pago | Efectivo, Tarjeta, etc. |
| Pendiente | Indica si la factura está sin cobrar |

#### Filtrar facturas

Puedes filtrar el listado por:
- Rango de fechas
- Cliente
- Empleado
- Estado (pendiente / cobrada)

#### Ver detalle de una factura

1. Selecciona una factura en la lista.
2. Pulsa **Ver detalle** → se abre **FacturaDetalleDialog** con el desglose completo de productos y servicios incluidos.

#### Eliminar una factura

1. Selecciona la factura.
2. Pulsa **Eliminar**.
3. Confirma en el diálogo de confirmación.

> ⚠️ Eliminar una factura es una acción permanente y no puede deshacerse.

### 3.11 Gestión de Usuarios

**Ruta:** Menú lateral → Usuarios

> 🔒 Esta sección requiere rol **MANAGER** o **ADMINISTRADOR** para modificar usuarios. El rol EMPLEADO solo puede consultar el listado (GET).

Permite gestionar las cuentas de acceso al sistema. Cada usuario está vinculado a un empleado.

#### Campos de un usuario

| Campo | Descripción |
|---|---|
| Usuario | Nombre de usuario para el login |
| Contraseña | Contraseña cifrada (bcrypt) |
| Rol | ADMINISTRADOR / MANAGER / EMPLEADO |
| Empleado | Empleado al que pertenece esta cuenta |

#### Añadir un usuario

1. Pulsa **Nuevo usuario**.
2. Rellena el formulario con usuario, contraseña y rol.
3. Selecciona el empleado al que se vincula.
4. Pulsa **Guardar**.

#### Cambiar contraseña

1. Selecciona el usuario.
2. Pulsa **Editar**.
3. Introduce la nueva contraseña en el campo habilitado.
4. Pulsa **Guardar**.

#### Eliminar un usuario

Al eliminar un usuario se revoca su acceso al sistema. El empleado vinculado no se elimina.

### 3.12 Roles y permisos

El sistema dispone de tres roles de usuario con diferentes niveles de acceso. Los permisos están implementados en el filtro `AuthFilter.java` del servidor API, que evalúa el rol incluido en el token JWT antes de cada operación de escritura.

> Las restricciones aplican únicamente a operaciones de escritura (POST / PUT / DELETE). Cualquier usuario autenticado con un token JWT válido puede realizar operaciones de lectura (GET) sobre todos los recursos.

| Función | EMPLEADO | MANAGER | ADMINISTRADOR |
|---|:---:|:---:|:---:|
| **Clientes** | | | |
| Ver clientes | ✅ | ✅ | ✅ |
| Crear / editar clientes | ❌ | ✅ | ✅ |
| Eliminar clientes | ❌ | ✅ | ✅ |
| **Empleados** | | | |
| Ver empleados | ✅ | ✅ | ✅ |
| Crear / editar empleados | ❌ | ✅ | ✅ |
| Eliminar empleados | ❌ | ✅ | ✅ |
| **Locales** | | | |
| Ver locales | ✅ | ✅ | ✅ |
| Crear / editar / eliminar locales | ❌ | ❌ | ✅ |
| **Productos** | | | |
| Ver productos | ✅ | ✅ | ✅ |
| Crear / editar / eliminar productos | ✅ | ✅ | ✅ |
| **Servicios** | | | |
| Ver servicios | ✅ | ✅ | ✅ |
| Crear / editar / eliminar servicios | ✅ | ✅ | ✅ |
| **TPV y Facturas** | | | |
| Usar TPV | ✅ | ✅ | ✅ |
| Ver facturas | ✅ | ✅ | ✅ |
| Crear / editar / eliminar facturas | ✅ | ✅ | ✅ |
| **Usuarios** | | | |
| Ver usuarios | ✅ | ✅ | ✅ |
| Crear / editar / eliminar usuarios | ❌ | ✅ | ✅ |

**Resumen por rol:**

- **EMPLEADO** — Puede consultar todos los datos y operar con productos, servicios y facturas (TPV). No puede gestionar clientes, empleados, locales ni usuarios.
- **MANAGER** — Extiende al empleado añadiendo la gestión completa de clientes, empleados y usuarios. No puede modificar locales (solo el Administrador puede).
- **ADMINISTRADOR** — Acceso total sin restricciones, incluyendo la gestión de locales.

---

## 4. Aplicación Móvil — Android (Kotlin)

### 4.1 Requisitos del sistema

| Requisito | Mínimo |
|---|---|
| Sistema operativo | Android 8.0 (API 26) o superior |
| RAM | 2 GB |
| Conexión de red | Necesaria (Wi-Fi o datos móviles) |
| Espacio | ~50 MB libres |

### 4.2 Instalación

La aplicación se distribuye como un fichero `.apk` o a través de una tienda privada.

**Instalación desde APK:**

1. Transfiere el fichero `PeluPos.apk` al dispositivo Android.
2. En el dispositivo ve a **Ajustes → Seguridad → Fuentes desconocidas** y activa la opción (necesaria solo la primera vez).
3. Abre el fichero APK desde el gestor de archivos.
4. Sigue las instrucciones del asistente de instalación.
5. Una vez instalada, la app aparecerá en el menú de aplicaciones.

**Instalación desde código fuente (desarrollo):**

1. Abre el proyecto `Proyecto kotlin PeluPos/PeluPos/` en Android Studio.
2. Conecta el dispositivo Android o inicia un emulador.
3. Pulsa **Run ▶** o ejecuta `./gradlew assembleDebug`.

### 4.3 Inicio de sesión y permisos

Al abrir la app se muestra la pantalla de **Login**.

1. Introduce el **usuario** y la **contraseña**.
2. Pulsa **Entrar**.
3. El sistema verifica las credenciales contra la API y, si son correctas, guarda el token JWT en la sesión local.

> El token se almacena de forma segura en el dispositivo y se renueva automáticamente en cada sesión. Si el servidor no está disponible, la app mostrará un mensaje de error de conexión.

Los permisos de cada pantalla se aplican igual que en la versión WPF (ver [sección 3.12](#312-roles-y-permisos)).

### 4.4 Pantalla principal — Dashboard

Tras el login correcto se muestra el **Dashboard** con:

- Tarjetas con el resumen de clientes, empleados, productos y facturas recientes.
- Barra de navegación inferior (o menú lateral en tablets) con acceso a todos los módulos.

### 4.5 Clientes

**Navegación:** Barra inferior → Clientes

Muestra la lista de clientes en tarjetas con nombre, teléfono y deuda.

- **Buscar:** Campo de búsqueda en la parte superior para filtrar por nombre.
- **Añadir:** Botón flotante **+** para crear un nuevo cliente con nombre, teléfono y deuda inicial.
- **Editar:** Pulsa sobre una tarjeta de cliente para ver su detalle y editar sus datos.
- **Eliminar:** Desliza la tarjeta hacia un lado o usa el menú contextual para eliminarla.

### 4.6 Empleados

**Navegación:** Barra inferior → Empleados

Lista de empleados con nombre, cargo y local asignado.

- **Añadir empleado:** Botón **+** → formulario con nombre, cargo, email, teléfono y local.
- **Ver ventas:** Desde el detalle de un empleado, accede a sus facturas generadas.
- **Editar / Eliminar:** Desde el detalle o menú contextual.

### 4.7 Locales

**Navegación:** Menú → Locales

Gestión de sucursales. Solo accesible para roles MANAGER y ADMINISTRADOR.

- **Añadir local:** Nombre y dirección.
- **Editar / Eliminar:** Desde el detalle del local.

### 4.8 Productos

**Navegación:** Barra inferior → Productos

Catálogo de productos con nombre, precio de compra, precio de venta y stock.

- Los productos con **stock bajo** (0 unidades) se marcan visualmente en rojo.
- **Añadir:** Botón **+** con formulario completo.
- **Ajustar stock:** Opción disponible desde el detalle del producto.

### 4.9 Servicios

**Navegación:** Menú → Servicios

Catálogo de servicios con nombre, precio y descripción.

- **Añadir:** Botón **+** con selección de empleado responsable.
- **Editar / Eliminar:** Desde el detalle del servicio.

### 4.10 TPV

**Navegación:** Barra inferior → TPV

Módulo de cobro móvil, ideal para peluqueros que atienden en silla.

1. Selecciona el **cliente** (o crea uno nuevo en el momento).
2. Selecciona el **empleado** que presta el servicio.
3. Añade **productos** y/o **servicios** al carrito con el botón **+** junto a cada ítem.
4. El total se actualiza en tiempo real en la parte inferior.
5. Selecciona el **método de pago**.
6. Pulsa **Cobrar** para generar la factura.

> En modo offline (sin red), la app avisará y no permitirá finalizar la venta hasta que se restaure la conexión con el servidor.

### 4.11 Ventas

**Navegación:** Menú → Ventas

Historial de facturas con filtro por fecha, cliente o empleado.

- **Ver detalle:** Pulsa sobre una factura para ver el desglose de productos y servicios.
- **Estado pendiente:** Las facturas pendientes de cobro aparecen marcadas con un indicador naranja.

### 4.12 Usuarios

**Navegación:** Menú → Usuarios

> 🔒 La creación, edición y eliminación de usuarios requiere rol **MANAGER** o **ADMINISTRADOR**.

Gestión de cuentas de acceso al sistema. Permite crear, editar y eliminar usuarios vinculados a empleados.

---

## 5. Documentación de la API REST

### 5.1 Información general

| Parámetro | Valor |
|---|---|
| URL base | `http://localhost:8080/pelupos/servicio` |
| Protocolo | HTTP (configurable a HTTPS en producción) |
| Puerto | `8080` |
| Formato de datos | JSON (`Content-Type: application/json`) |
| Autenticación | JWT Bearer Token |

#### Cabeceras requeridas (todas las peticiones excepto login)

```http
Content-Type: application/json
Accept: application/json
Authorization: Bearer <jwtToken>
```

#### Variables de entorno (Postman)

| Variable | Descripción |
|---|---|
| `{{jwtToken}}` | Token JWT obtenido tras el login (se actualiza automáticamente) |

---

### 5.2 Autenticación (Auth)

#### `POST /auth/login`

Autentica al usuario y devuelve un token JWT.

**Request body:**
```json
{
  "usuario": "luciaPedoPis",
  "contrasena": "123456"
}
```

**Response exitosa (200 OK):**
```json
{
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Usuarios de prueba disponibles:**

| Usuario | Contraseña | Rol |
|---|---|---|
| `luciaPedoPis` | `123456` | ADMINISTRADOR |
| `Franco` | `123456` | MANAGER |
| `marcos` | `123456` | EMPLEADO |

> El token devuelto debe incluirse en la cabecera `Authorization: Bearer <jwtToken>` en todas las peticiones posteriores. En Postman, el script de test lo almacena automáticamente en la variable `{{jwtToken}}`.

---

#### `POST /auth/logout`

Invalida el token JWT actual. El token queda registrado en la tabla `token_verificado` (blacklist) y no puede reutilizarse.

**Cabeceras requeridas:** `Authorization: Bearer <jwtToken>`

**Request body:**
```json
{
  "usuario": "pedro",
  "contrasena": "123456",
  "rolUsuario": "MANAGER",
  "idEmpleado": {
    "idEmpleado": 5
  }
}
```

**Response exitosa (200 OK):** Confirmación de cierre de sesión.

---

### 5.3 Productos

Base path: `/productos`

#### `GET /productos` — Obtener todos los productos

Devuelve la lista completa de productos.

**Cabeceras:** `Authorization: Bearer <jwtToken>`

**Response exitosa (200 OK):**
```json
[
  {
    "idProducto": 1,
    "nombre": "Champú Argan Oil",
    "precioCompra": 5.50,
    "precioVenta": 12.00,
    "stock": 20
  }
]
```

#### `POST /productos` — Crear un producto

**Request body:**
```json
{
  "nombre": "Cera Mate",
  "precioCompra": 3.00,
  "precioVenta": 8.50,
  "stock": 15
}
```

**Response exitosa (201 Created):** El producto creado con su `idProducto` asignado.

#### `PUT /productos` — Actualizar un producto

**Request body:** Igual que POST pero incluyendo `"idProducto"` con el ID del producto a actualizar.

**Response exitosa (200 OK):** El producto actualizado.

#### `DELETE /productos/{id}` — Eliminar un producto

**Parámetros de ruta:**
- `id` — ID del producto a eliminar.

**Response exitosa (200 OK):** Confirmación de eliminación.

---

### 5.4 Clientes

Base path: `/clientes`

#### `GET /clientes` — Obtener todos los clientes

**Response exitosa (200 OK):**
```json
[
  {
    "idCliente": 1,
    "nombre": "Ana García",
    "deuda": 0.0,
    "telefono": 612345678
  }
]
```

#### `GET /clientes/{id}` — Obtener un cliente por ID

**Parámetros de ruta:** `id` — ID del cliente.

**Response exitosa (200 OK):** El objeto cliente correspondiente.

#### `POST /clientes` — Crear un cliente

**Request body:**
```json
{
  "nombre": "Carlos López",
  "deuda": 0.0,
  "telefono": 698765432
}
```

**Response exitosa (201 Created):** El cliente creado con su `idCliente`.

#### `PUT /clientes` — Actualizar un cliente

**Request body:** Igual que POST incluyendo `"idCliente"`.

**Response exitosa (200 OK):** El cliente actualizado.

#### `DELETE /clientes/{id}` — Eliminar un cliente

**Response exitosa (200 OK):** Confirmación de eliminación.

---

### 5.5 Empleados

Base path: `/empleados`

#### `GET /empleados` — Obtener todos los empleados

**Response exitosa (200 OK):**
```json
[
  {
    "idEmpleado": 1,
    "nombre": "María Pérez",
    "cargo": "Estilista",
    "email": "maria@pelupos.com",
    "telefono": 655123456,
    "idLocal": {
      "idLocal": 1,
      "nombre": "Sucursal Centro",
      "direccion": "Calle Mayor 5"
    }
  }
]
```

#### `GET /empleados/ventas/{id}` — Obtener facturas de un empleado

**Parámetros de ruta:** `id` — ID del empleado.

**Response exitosa (200 OK):** Lista de facturas asociadas al empleado.

#### `POST /empleados` — Crear un empleado

**Request body:**
```json
{
  "nombre": "Pedro Ruiz",
  "cargo": "Barbero",
  "email": "pedro@pelupos.com",
  "telefono": 611223344,
  "idLocal": { "idLocal": 1 }
}
```

#### `PUT /empleados` — Actualizar un empleado

**Request body:** Igual que POST incluyendo `"idEmpleado"`.

#### `DELETE /empleados/{id}` — Eliminar un empleado

**Response exitosa (200 OK):** Confirmación de eliminación.

> ⚠️ Al eliminar un empleado, su usuario vinculado también se elimina (CASCADE).

---

### 5.6 Facturas

Base path: `/facturas`

#### `GET /facturas` — Obtener todas las facturas

**Response exitosa (200 OK):**
```json
[
  {
    "idFactura": 1,
    "monto": 45.50,
    "fecha": "2025-12-15T10:30:00",
    "pendiente": false,
    "tipoPago": "Efectivo",
    "idCliente": { "idCliente": 2 },
    "idEmpleado": { "idEmpleado": 1 },
    "productos": [
      {
        "idProducto": 3,
        "cantidad": 2,
        "precioVendido": 12.00
      }
    ],
    "servicios": [
      {
        "idServicio": 1,
        "cantidad": 1,
        "precioCobrado": 21.50
      }
    ]
  }
]
```

#### `GET /facturas/{id}` — Obtener una factura por ID

**Parámetros de ruta:** `id` — ID de la factura.

#### `POST /facturas` — Crear una factura

**Request body:**
```json
{
  "monto": 35.00,
  "fecha": "2025-12-20T11:00:00",
  "pendiente": false,
  "tipoPago": "Tarjeta",
  "idCliente": { "idCliente": 1 },
  "idEmpleado": { "idEmpleado": 2 },
  "productos": [],
  "servicios": [
    {
      "idServicio": 2,
      "cantidad": 1,
      "precioCobrado": 35.00
    }
  ]
}
```

**Response exitosa (201 Created):** La factura creada con su `idFactura`.

#### `PUT /facturas` — Actualizar una factura

**Request body:** Igual que POST incluyendo `"idFactura"`.

#### `DELETE /facturas/{id}` — Eliminar una factura

**Response exitosa (200 OK):** Confirmación de eliminación.

---

### 5.7 Locales

Base path: `/locales`

#### `GET /locales` — Obtener todos los locales

**Response exitosa (200 OK):**
```json
[
  {
    "idLocal": 1,
    "nombre": "Sucursal Centro",
    "direccion": "Calle Mayor 5"
  }
]
```

#### `GET /locales/{id}` — Obtener un local por ID

#### `POST /locales` — Crear un local

**Request body:**
```json
{
  "nombre": "Sucursal Norte",
  "direccion": "Avenida del Norte 22"
}
```

#### `PUT /locales` — Actualizar un local

**Request body:** Igual que POST incluyendo `"idLocal"`.

#### `DELETE /locales/{id}` — Eliminar un local

> ⚠️ Solo es posible si el local no tiene empleados asignados, o si sus empleados tienen `id_local` en NULL.

---

### 5.8 Servicios

Base path: `/servicios`

#### `GET /servicios` — Obtener todos los servicios

**Response exitosa (200 OK):**
```json
[
  {
    "idServicio": 1,
    "nombre": "Corte de cabello",
    "precio": 15.00,
    "descripcion": "Corte clásico o moderno",
    "idEmpleado": { "idEmpleado": 1 }
  }
]
```

#### `POST /servicios` — Crear un servicio

**Request body:**
```json
{
  "nombre": "Tinte completo",
  "precio": 45.00,
  "descripcion": "Tinte con productos profesionales",
  "idEmpleado": { "idEmpleado": 2 }
}
```

#### `PUT /servicios` — Actualizar un servicio

**Request body:** Igual que POST incluyendo `"idServicio"`.

#### `DELETE /servicios/{id}` — Eliminar un servicio

---

### 5.9 Usuarios

Base path: `/usuarios`

> 🔒 Las operaciones de escritura (POST / PUT / DELETE) sobre usuarios requieren rol **MANAGER** o **ADMINISTRADOR**. La lectura (GET) está disponible para cualquier rol autenticado.

#### `GET /usuarios` — Obtener todos los usuarios

**Response exitosa (200 OK):**
```json
[
  {
    "idUsuario": 1,
    "usuario": "luciaPedoPis",
    "rolUsuario": "ADMINISTRADOR",
    "idEmpleado": { "idEmpleado": 3 }
  }
]
```

> La contraseña nunca se devuelve en las respuestas de la API.

#### `POST /usuarios` — Crear un usuario

**Request body:**
```json
{
  "usuario": "nuevoUsuario",
  "contrasena": "passwordSeguro123",
  "rolUsuario": "EMPLEADO",
  "idEmpleado": { "idEmpleado": 5 }
}
```

**Roles válidos:** `ADMINISTRADOR`, `MANAGER`, `EMPLEADO`

#### `PUT /usuarios` — Actualizar un usuario

**Request body:** Igual que POST incluyendo `"idUsuario"`.

#### `DELETE /usuarios/{id}` — Eliminar un usuario

**Response exitosa (200 OK):** Confirmación de eliminación.

---

### 5.10 Códigos de respuesta HTTP

| Código | Significado | Descripción |
|---|---|---|
| `200 OK` | Éxito | La operación se completó correctamente |
| `201 Created` | Creado | El recurso fue creado exitosamente |
| `400 Bad Request` | Error del cliente | Datos de entrada inválidos o faltantes |
| `401 Unauthorized` | No autenticado | Token JWT ausente, inválido o expirado |
| `403 Forbidden` | Sin permiso | El rol del usuario no permite esta acción |
| `404 Not Found` | No encontrado | El recurso solicitado no existe |
| `409 Conflict` | Conflicto | El recurso ya existe (p. ej. nombre de usuario duplicado) |
| `500 Internal Server Error` | Error del servidor | Error inesperado en el servidor |

---

## 6. Base de datos

El sistema utiliza una base de datos **MySQL** con el esquema `peluposbd`.

### Diagrama de tablas

```
Local ──────────────────────── Empleado
                                  │
                    ┌─────────────┤
                    │             │
                 Usuario       Servicio
                             
Cliente ──────── Factura ─────── Empleado
                    │
          ┌─────────┴──────────┐
          │                    │
   Factura_Producto     Factura_Servicio
          │                    │
       Producto             Servicio
```

### Descripción de tablas

#### `Local`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_local` | BIGINT PK | Identificador único |
| `nombre` | VARCHAR(100) | Nombre del local |
| `direccion` | VARCHAR(255) | Dirección física |

#### `Cliente`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_cliente` | BIGINT PK | Identificador único |
| `nombre` | VARCHAR(100) | Nombre completo |
| `deuda` | DECIMAL(10,2) | Deuda pendiente (default 0.0) |
| `telefono` | BIGINT | Número de contacto |

#### `Producto`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_producto` | BIGINT PK | Identificador único |
| `nombre` | VARCHAR(100) | Nombre del producto |
| `precio_compra` | DECIMAL(10,2) | Precio de adquisición |
| `precio_venta` | DECIMAL(10,2) | Precio al cliente |
| `stock` | INT | Unidades disponibles |

#### `Empleado`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_empleado` | BIGINT PK | Identificador único |
| `nombre` | VARCHAR(100) | Nombre completo |
| `cargo` | VARCHAR(50) | Cargo o puesto |
| `email` | VARCHAR(100) | Correo electrónico |
| `telefono` | BIGINT | Teléfono de contacto |
| `id_local` | BIGINT FK | Local al que pertenece |

#### `Usuario`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_usuario` | BIGINT PK | Identificador único |
| `usuario` | VARCHAR(50) UNIQUE | Nombre de usuario |
| `contrasena` | VARCHAR(255) | Contraseña cifrada (bcrypt) |
| `rol_usuario` | VARCHAR(20) | ADMINISTRADOR / MANAGER / EMPLEADO |
| `id_empleado` | BIGINT FK UNIQUE | Empleado vinculado |

#### `Servicio`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_servicio` | BIGINT PK | Identificador único |
| `nombre` | VARCHAR(100) | Nombre del servicio |
| `precio` | DECIMAL(10,2) | Precio al cliente |
| `descripcion` | TEXT | Descripción detallada |
| `id_empleado` | BIGINT FK | Empleado por defecto |

#### `Factura`
| Columna | Tipo | Descripción |
|---|---|---|
| `id_factura` | BIGINT PK | Identificador único |
| `monto` | DECIMAL(10,2) | Importe total |
| `fecha` | DATETIME | Fecha y hora de la venta |
| `pendiente` | BOOLEAN | True si está sin cobrar |
| `tipo_pago` | VARCHAR(50) | Método de pago |
| `id_cliente` | BIGINT FK | Cliente asociado |
| `id_empleado` | BIGINT FK | Empleado que realizó la venta |

#### `Factura_Producto` (tabla intermedia)
| Columna | Tipo | Descripción |
|---|---|---|
| `id_factura` | BIGINT FK | Referencia a Factura |
| `id_producto` | BIGINT FK | Referencia a Producto |
| `cantidad` | INT | Unidades vendidas |

#### `Factura_Servicio` (tabla intermedia)
| Columna | Tipo | Descripción |
|---|---|---|
| `id_factura` | BIGINT FK | Referencia a Factura |
| `id_servicio` | BIGINT FK | Referencia a Servicio |
| `cantidad` | INT | Número de veces que se realizó el servicio |

### Scripts SQL disponibles

El proyecto incluye los siguientes scripts en el directorio `API Rest PeluPosBD/`:

| Fichero | Descripción |
|---|---|
| `peluposbd.sql` | Crea el esquema y todas las tablas |
| `DatosPruebaPeluPosBD.sql` | Inserta datos de prueba (clientes, empleados, productos, etc.) |
| `BorrarBD.sql` | Elimina todas las tablas (útil para reiniciar el entorno) |

### Pasos para configurar la base de datos

```sql
-- 1. Ejecutar en MySQL Workbench o cliente MySQL:
SOURCE peluposbd.sql;

-- 2. Cargar datos de prueba (opcional):
SOURCE DatosPruebaPeluPosBD.sql;
```

---

## 7. Preguntas frecuentes (FAQ)

**¿Por qué no puedo iniciar sesión?**
- Verifica que el servidor API está en ejecución en `http://localhost:8080`.
- Comprueba que las credenciales son correctas (usuario y contraseña).
- El token JWT puede haber expirado; cierra la aplicación, vuelve a abrirla e inicia sesión de nuevo.

**¿Por qué no puedo eliminar un empleado?**
- Solo los usuarios con rol **MANAGER** o **ADMINISTRADOR** pueden eliminar empleados.
- Si el empleado tiene facturas asociadas, considera desvincularlo en lugar de eliminarlo.

**¿Qué ocurre si elimino un local que tiene empleados?**
- El sistema no permitirá la eliminación. Primero reasigna o elimina los empleados vinculados a ese local.

**¿Cómo recupero la contraseña de un usuario?**
- Un **ADMINISTRADOR** puede cambiar la contraseña desde el módulo de Usuarios en la aplicación WPF o Android.
- No existe recuperación automática por correo electrónico en esta versión.

**¿Puedo usar la app Android sin conexión?**
- No. La aplicación requiere conexión de red para comunicarse con la API REST. Sin conexión, no es posible realizar ventas ni consultar datos.

**¿El stock se actualiza automáticamente?**
- Sí. Al generar una factura con productos, el stock de cada producto se reduce automáticamente en función de las unidades vendidas.

**¿Qué formato tiene la fecha en la API?**
- Las fechas se envían y reciben en formato ISO 8601: `YYYY-MM-DDTHH:MM:SS` (p. ej. `2025-12-20T11:00:00`).

**¿Cómo importo la colección de Postman?**
1. Abre Postman.
2. Haz clic en **Import**.
3. Selecciona el fichero `API Rest PeluPosBD/pruebas Postman/PeluPos.postman_collection.json`.
4. La colección aparecerá con todas las peticiones preconfiguradas y el script de login que guarda el token automáticamente.

**¿Cómo despliego el servidor en producción?**
1. Genera el fichero `.war` del proyecto `ApiRestPeluPos` con el comando `ant` o desde NetBeans.
2. Despliega el `.war` en un servidor de aplicaciones compatible con Jakarta EE 10 (p. ej. Payara, GlassFish, WildFly).
3. Configura la cadena de conexión a MySQL en el fichero de recursos del servidor.
4. Actualiza la URL base en las aplicaciones cliente (WPF y Android) para apuntar al servidor de producción.

---

## Créditos

| Módulo | Desarrollador |
|---|---|
| API REST (Java / Jakarta EE) | David |
| Aplicación WPF (C# / .NET) | Joel |
| Aplicación Android (Kotlin) | David |

**Versión del sistema:** 1.0  
**Fecha de documentación:** Mayo 2026  
**Repositorio:** [GitHub — LosDelFondopi2damiesbalmis/proyecto](https://github.com/LosDelFondopi2damiesbalmis/proyecto)
