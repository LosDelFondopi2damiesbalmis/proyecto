# 📊 Informe de Participación en el Repositorio Git
## Periodo: 22/02/2026 — 12/03/2026

---

## 📋 Estadísticas Generales

| Métrica | Valor |
|---|---|
| **Periodo analizado** | 22/02/2026 — 12/03/2026 (19 días naturales) |
| **Total de usuarios activos** | 3 (David Rafael Gdaniec, Joel Vives, Francisco Javier García) |
| **Usuarios sin actividad** | 1 (Jesús Liñan) |
| **Días con actividad** | 11 (23/02, 26/02, 27/02, 02/03, 03/03, 05/03, 06/03, 09/03, 10/03, 11/03, 12/03) |
| **Total de commits (sin merges)** | 41 |
| **Commits efectivos (excl. diario)** | 28 |
| **Archivos modificados únicos** | 277 |

---

## 📝 Tabla de Commits

> Se incluyen todos los commits (efectivos y de diario) del período, ordenados cronológicamente.

| Nombre | Fecha | Archivos modificados | Resumen de lo que ha realizado |
|---|---|---|---|
| DavidRafaelGdaniec | 23/02/2026 | `ui/tpv/TPVScreen.kt`, `ui/navigation/NavGraphBuilder.kt`, `ui/navigation/Navigation.kt`, `ui/tpv/SalesHistorialScreen.kt`, `ui/MainScreen.kt` | Creación del TPVScreen con historial de facturas e integración en la navegación principal de la app Kotlin. |
| DavidRafaelGdaniec | 23/02/2026 | `ui/empleados/EmpleadoFormScreen.kt`, `ui/empleados/EmpleadoStatsScreen.kt`, `ui/empleados/EmpleadosScreen.kt` | Creación de las tres pantallas de empleados: listado principal, estadísticas y formulario de creación. |
| DavidRafaelGdaniec | 23/02/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario con las tareas del día. |
| DavidRafaelGdaniec | 26/02/2026 | `app/build.gradle.kts`, `ui/clientes/ClienteScreen.kt`, `ui/clientes/NewClienteScreen.kt`, `ui/empleados/EmpleadosScreen.kt`, `ui/servicios/NewServicioScreen.kt`, `ui/tpv/TPVScreen.kt`, `ui/ventas/VentasScreen.kt` | Creación de las pantallas de nuevo cliente y nuevo servicio, implementación de los mocks de cliente en la app Kotlin. |
| DavidRafaelGdaniec | 26/02/2026 | `ui/tpv/TPVScreen.kt`, `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario (incluye también ajuste en TPVScreen.kt). |
| Joel Vives | 27/02/2026 | `Data/Seed/MockData.cs`, `Services/AuthService.cs`, `Services/CatalogService.cs`, `Services/IAuthService.cs`, `Services/ICatalogService.cs`, `Services/ITpvVentaService.cs`, `Services/SessionService.cs`, `ViewModels/TPV/SelectedUserViewModel.cs`, `Views/TPV/SelectedUserDialog.xaml`, `Views/TPV/SelectedUserDialog.xaml.cs`, `PeluPos.zip` | Primera versión del diálogo de selección de usuario para el TPV en WPF: servicios de autenticación, catálogo, sesión y ViewModel de selección de usuario. |
| Joel Vives | 27/02/2026 | `Services/TpvVentaService.cs`, `ViewModels/TPV/TpvItemCard.cs`, `ViewModels/TPV/TpvLineaViewModel.cs`, `ViewModels/TPV/TpvViewModel.cs` | Creación del ViewModel del TPV (versión 1): lógica de líneas de venta, tarjeta de ítem y servicio de venta. |
| Joel Vives | 27/02/2026 | `Views/TPV/TpvPage.xaml`, `Views/TPV/TpvPage.xaml.cs` | Primera versión de la vista principal del TPV en WPF (XAML + code-behind). |
| Joel Vives | 27/02/2026 | `MainWindow.xaml`, `MainWindow.xaml.cs`, `Resources/Converters/InverseBoolToVisibilityConverter.cs`, `Services/AppShellState.cs` | Implementación de la navegación hacia el TPV desde la ventana principal, añadiendo conversor de visibilidad y estado del shell. |
| Joel Vives | 27/02/2026 | `Data/Seed/MockData.cs`, `Models/Entities/Empleado.cs`, `Models/Entities/Factura.cs`, `Services/ITpvVentaService.cs`, `ViewModels/TPV/SelectedUserViewModel.cs`, `ViewModels/TPV/TpvViewModel.cs`, `Views/TPV/SelectedUserDialog.xaml`, `Views/TPV/SelectedUserDialog.xaml.cs`, `Views/TPV/TpvPage.xaml`, `Views/TPV/TpvPage.xaml.cs` | Creación de los usuarios mock, modelos Empleado y Factura, e integración casi completa del flujo TPV con selección de usuario. |
| Joel Vives | 27/02/2026 | `Services/TpvVentaService.cs` | Implementación de la TPV terminada: finalización del servicio de venta. |
| Joel Vives | 27/02/2026 | `MainWindow.xaml.cs`, `Views/Usuarios/UsuariosPage.xaml`, `Views/Usuarios/UsuariosPage.xaml.cs` | Creación de la pantalla de gestión de usuarios en WPF. |
| Joel Vives | 27/02/2026 | `MainWindow.xaml.cs`, `Services/AuthService.cs`, `Services/IAuthService.cs`, `Services/SessionService.cs`, `ViewModels/LoginViewModel.cs`, `Views/LoginDialog.xaml`, `Views/LoginDialog.xaml.cs`, `Views/TPV/TpvPage.xaml.cs` | Primera versión completa del login: servicio de autenticación, sesión, ViewModel de login y vista del diálogo. |
| Joel Vives | 27/02/2026 | `MainWindow.xaml.cs`, `Services/SessionService.cs`, `ViewModels/TPV/SelectedUserViewModel.cs`, `Views/TPV/SelectedUserDialog.xaml`, `Views/TPV/SelectedUserDialog.xaml.cs`, `Views/TPV/TpvPage.xaml.cs` | Login terminado correctamente: ajustes finales de sesión y flujo de selección de usuario en el TPV. |
| DavidRafaelGdaniec | 03/03/2026 | `navigation/DashboardRoute.kt`, `navigation/LocalesRoute.kt`, `navigation/NavGraphBuilder.kt`, `navigation/Navigation.kt`, `navigation/ProductsRoute.kt`, `navigation/VentasRoute.kt`, `ui/MainScreen.kt`, `ui/composables/DashboardCard.kt`, `ui/composables/DataRow.kt`, `ui/composables/Sidebar.kt`, + múltiples pantallas UI | Cambio en la estructura de carpetas del proyecto Kotlin y comienzo de la capa ViewModel (primer commit, reorganización). |
| DavidRafaelGdaniec | 03/03/2026 | `navigation/*`, `ui/composables/*`, `ui/features/*`, `ui/tpv/SalesHistorialScreen.kt` | Segundo commit del cambio estructural de carpetas: reorganización definitiva de rutas de navegación y pantallas UI al nuevo paquete `features`. |
| DavidRafaelGdaniec | 03/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario. |
| francisco javier garcia gomez | 02/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Commit etiquetado como "Actualización diario", pero modifica el archivo de diario de DavidRafael en lugar del propio. |
| francisco javier garcia gomez | 02/03/2026 | `.idea/deploymentTargetSelector.xml`, `ui/MainScreen.kt`, `ui/login/LoginScreen.kt` | Creación del LoginScreen en la app Kotlin siguiendo el diseño de la versión WPF. |
| Joel Vives | 02/03/2026 | `MainWindow.xaml`, `MainWindow.xaml.cs`, `Views/TPV/TpvPage.xaml`, `docs/diarios/Joel_Vives.md` | Actualización del diario y pequeños ajustes en MainWindow y TpvPage. |
| Joel Vives | 02/03/2026 | `App.xaml`, `PeluPOS.csproj`, `Resources/Estilos.xaml`, `Views/Clientes/ClientesPage.xaml`, `Views/DashboardPage.xaml`, `Views/Empleados/EmpleadoDialog.xaml`, `Views/Empleados/EmpleadoFacturasPage.xaml`, `Views/Empleados/EmpleadosPage.xaml`, `Views/Locales/LocalesPage.xaml`, `Views/Locales/NuevoLocalDialog.xaml`, `Views/LoginDialog.xaml`, + 11 vistas más | Adición de Material Design en el proyecto WPF (pendiente de finalizar): actualización de referencias en `.csproj`, estilos globales e integración en más de 20 vistas. |
| DavidRafaelGdaniec | 05/03/2026 | `ui/features/usuarios/UsuarioFormScreen.kt`, `ui/features/usuarios/UsuarioScreen.kt` | Creación de las pantallas de usuarios en la app Kotlin (listado y formulario). |
| DavidRafaelGdaniec | 05/03/2026 | `.idea/deviceManager.xml`, `app/build.gradle.kts`, `AndroidManifest.xml`, `PeluPosApp.kt`, `PeluPosApplication.kt`, `data/mocks/CarritoRepository.kt`, `navigation/NavHost.kt`, `navigation/TPVRoute.kt`, `navigation/VentasRoute.kt`, `ui/features/tpv/TPVScreen.kt`, `ui/features/tpv/TPVViewModel.kt`, `ui/features/tpv/TpvEvent.kt`, `ui/features/ventas/*` (5 archivos), `ui/viewmodels/TPVViewModel.kt`, `MainActivity.kt`, `build.gradle.kts` | Modificación del TPV para navegar a la página de ventas, crear tickets y primeras pantallas funcionales con ViewModels de ventas. |
| DavidRafaelGdaniec | 05/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario con las tareas del día. |
| DavidRafaelGdaniec | 06/03/2026 | `data/mocks/SessionRepository.kt`, `data/mocks/cliente/ClienteRepository.kt`, `data/mocks/empleado/EmpleadoRepository.kt`, `data/mocks/usuario/UsuarioRepository.kt`, `di/ModuleApp.kt`, `navigation/ClienteRoute.kt`, `navigation/LoginRoute.kt`, `navigation/NavHost.kt`, `navigation/UsuariosRoute.kt`, `ui/MainScreen.kt`, `ui/features/clientes/*` (6 archivos), `ui/features/login/*` (4 archivos), `ui/features/usuarios/*` (5 archivos) | Creación de la funcionalidad completa de clientes, login funcional y módulo de usuarios con ViewModels, repositorios mock, rutas de navegación y pantallas. |
| DavidRafaelGdaniec | 06/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario. |
| DavidRafaelGdaniec | 09/03/2026 | `data/mocks/local/LocalRepository.kt`, `di/ModuleApp.kt`, `navigation/LocalesRoute.kt`, `navigation/NavGraphBuilder.kt`, `navigation/NavHost.kt`, `ui/MainScreen.kt`, `ui/features/locales/*` (6 archivos) | Creación funcional del módulo de locales: repositorio mock, navegación, pantallas (listado, detalle, modificar, nuevo local) y ViewModel. |
| DavidRafaelGdaniec | 09/03/2026 | Múltiples repositorios mock (cliente, empleado, servicio, producto, carrito, usuario), `navigation/NavHost.kt`, `navigation/ProductsRoute.kt`, `ui/MainScreen.kt`, `ui/features/products/*` (5 archivos), `ui/features/servicios/*` (5 archivos) | Implementación completa de la funcionalidad de productos y servicios con ViewModels, estados UI, eventos y pantallas. |
| DavidRafaelGdaniec | 09/03/2026 | `navigation/NavHost.kt`, `navigation/ServiciosRoute.kt`, `ui/MainScreen.kt` | Creación de la ruta de navegación hacia la pantalla de servicios. |
| DavidRafaelGdaniec | 09/03/2026 | `navigation/AppNavHost.kt`, `navigation/DashboardRoute.kt`, `navigation/EmpleadosRoute.kt`, `navigation/ProductsRoute.kt`, `navigation/VentasRoute.kt`, `ui/MainScreen.kt`, `ui/composables/Sidebar.kt`, `ui/dashboard/DashboardUiState.kt`, `ui/dashboard/DashboardViewModel.kt`, `ui/dashboard/MainScreen.kt`, + múltiples pantallas del módulo empleados, clientes, locales, productos, servicios, TPV y ventas | ViewModel de empleados, pantalla principal del dashboard con datos mock y la aplicación ya es funcional en su totalidad con datos mock. Pendiente conectar con Room. |
| DavidRafaelGdaniec | 09/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario. |
| DavidRafaelGdaniec | 10/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario (primera del día). |
| DavidRafaelGdaniec | 10/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario (segunda del día, con más detalle). |
| DavidRafaelGdaniec | 10/03/2026 | `.idea/deploymentTargetSelector.xml`, `data/mocks/SessionRepository.kt`, `data/mocks/factura/FacturaDaoMock.kt`, `data/mocks/factura/FacturaRepository.kt`, `data/mocks/usuario/UsuarioDaoMock.kt`, `di/ModuleApp.kt`, `navigation/AppNavHost.kt`, `navigation/LoginRoute.kt`, `navigation/Navigation.kt`, `ui/composables/NavButton.kt`, `ui/composables/NavSeparator.kt`, `ui/composables/SelectorEmpleados.kt`, `ui/composables/Sidebar.kt`, `ui/dashboard/DashboardUiState.kt`, `ui/dashboard/DashboardViewModel.kt`, `ui/dashboard/MainScreen.kt`, `ui/features/empleados/EmpleadosScreen.kt`, `ui/features/login/*`, `ui/features/ventas/FacturacionViewModel.kt` | Eliminación de la navegación antigua y composables sin uso, creación de pantalla de permisos y actualización del dashboard con datos reales del mock. |
| DavidRafaelGdaniec | 10/03/2026 | `API Rest PeluPosBD/peluposbd.sql` | Creación del script SQL de la base de datos para la API REST. |
| DavidRafaelGdaniec | 10/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario. |
| DavidRafaelGdaniec | 11/03/2026 | `.gitignore`, librerías servlet/jakartaee10/mysql/json-org (JARs), `nbproject/*` (5 archivos), `src/conf/MANIFEST.MF`, `src/conf/persistence.xml`, todas las entidades JPA (`Cliente.java`, `Empleado.java`, `Factura.java`, `FacturaProducto.java`, `FacturaServicio.java`, `Local.java`, `Producto.java`, `Servicio.java`, `Usuario.java`), todos los `JpaController` correspondientes, excepciones, `ApplicationConfig.java`, `web/META-INF/context.xml`, `web/index.html`, `diagrama base de datos PeluPosBD.jpg` | Creación completa del proyecto API REST con JPA (NetBeans): diagrama de base de datos verificado, entidades JPA, controladores JPA para todas las clases, configuración de persistencia y estructura del proyecto web. |
| DavidRafaelGdaniec | 11/03/2026 | `.gitignore`, `lib/servlet-api/*`, `nbproject/build-impl.xml`, `nbproject/genfiles.properties`, `nbproject/project.properties`, `nbproject/rest-build.xml`, `src/java/peluposbd/Cliente.java`, `src/java/peluposbd/Empleado.java`, `src/java/peluposbd/FacturaProducto.java`, `src/java/peluposbd/FacturaServicio.java`, `src/java/peluposbd/Producto.java`, `src/java/peluposbd/Servicio.java`, `serpelupos/ServiceRest*` (6 servicios REST), `DatosPruebaPeluPosBD.sql`, `ui/features/locales/LocalDetailScreen.kt`, `ui/features/servicios/ServicioScreen.kt` | Creación del script de datos de prueba (`DatosPruebaPeluPosBD.sql`), API REST sin seguridad completa con todos los endpoints GET disponibles, pendiente de probar POST, PUT y DELETE. |
| DavidRafaelGdaniec | 11/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario. |
| DavidRafaelGdaniec | 12/03/2026 | `src/java/peluposbd/Factura.java`, `serpelupos/ServiceRestClientes.java`, `serpelupos/ServiceRestEmpleados.java`, `serpelupos/ServiceRestFacturas.java`, `serpelupos/ServiceRestLocal.java`, `serpelupos/ServiceRestProductos.java`, `serpelupos/ServiceRestServicios.java`, `pruebas Postman/Clientes.postman_collection.json`, `pruebas Postman/Empleados.postman_collection.json`, `pruebas Postman/Facturas.postman_collection.json`, `pruebas Postman/Locales.postman_collection.json`, `pruebas Postman/Productos.postman_collection.json`, `pruebas Postman/Servicios.postman_collection.json`, `pruebas Postman/Usuarios.postman_collection.json` | Arreglo de errores en la API REST y creación de colecciones Postman separadas para probar los métodos POST, PUT y DELETE de todas las entidades. |
| DavidRafaelGdaniec | 12/03/2026 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario. |

---

## 👥 Análisis de Contribuciones Individualizadas

| Usuario | Commits efectivos | Días activos | Contribución principal | Seguimiento (Diario) | Discrepancias | Valoración global |
|---|---|---|---|---|---|---|
| **David Rafael Gdaniec** | 17 | 9/9 (100%) | Desarrollo extensivo de la app Kotlin (arquitectura MVVM completa: locales, clientes, empleados, productos, servicios, TPV, ventas, login, dashboard) y creación íntegra del proyecto API REST con JPA (BD, entidades, endpoints, Postman). | Muy buen seguimiento: tiene entrada en el diario para cada día activo del período, con descripciones que reflejan correctamente el trabajo realizado. El diario está al día. | Leve: el commit de 26/02 etiquetado como "Actualización diario" incluye también código (TPVScreen.kt). La entrada del diario del 02/03 ("Creación del LoginScreen") no tiene commit propio en esa fecha; ese commit lo realizó Francisco García (puede indicar trabajo colaborativo no explicitado). | 🟢 |
| **Joel Vives** | 10 | 2/9 (22%) | Implementación del módulo TPV completo en WPF (servicios, ViewModels, vistas, login, navegación y gestión de usuarios); e integración de Material Design en todas las vistas WPF. | Seguimiento muy deficiente: el diario carece de entradas para enero y febrero de 2026. La entrada de marzo solo registra "02/03 — Arreglos de bugs", sin mencionar los 9 commits realizados el 27/02 (TPV completo, login, usuarios). El período más productivo del sprint (27/02) está completamente ausente del diario. | Los 9 commits del 27/02 no están reflejados en el diario. La entrada del 02/03 ("Arreglos de bugs") no describe con precisión el trabajo de ese día, que incluyó la integración de Material Design. Gap significativo entre la actividad real y lo registrado. | 🟡 |
| **Francisco Javier García Gómez** | 1 | 1/9 (11%) | Creación del LoginScreen en la app Kotlin basándose en el diseño ya existente en WPF. | Seguimiento muy deficiente: el diario no tiene entradas desde diciembre de 2025 (última: 15/12/2025). No existe ningún registro de actividad para el período analizado (febrero-marzo 2026). | El commit de "Actualización diario" modifica el archivo `docs/diarios/DavidRafael_Gdaniec.md` en lugar del suyo propio, lo que indica un error de archivo. Su propio diario no está actualizado. No hay discrepancia entre comits y diario, ya que el diario simplemente no existe para este período. | 🔴 |
| **Jesús Manuel Liñan Almagro** | 0 | 0/9 (0%) | Sin aportación técnica en el período. | Muy deficiente: el diario está detenido desde noviembre de 2025 (última entrada: 20/11/2025). No ha realizado ningún commit ni actualización del diario durante todo el período analizado ni en los meses previos. | No hay commits que contrastar con el diario, pero la ausencia total de actividad en ambos frentes confirma una desconexión completa del proyecto desde diciembre de 2025. | 🔴 |

---

## 📈 Resumen Ejecutivo

### 1. Tendencias y Patrones

| Fecha | Patrón / Tendencia | Descripción |
|---|---|---|
| 27/02/2026 | Pico puntual de actividad (Joel) | Joel acumula 9 commits en un solo día, implementando el TPV completo, login y usuarios en WPF. Esta concentración excesiva en una sola jornada (sin actividad previa ni posterior en el período) sugiere un trabajo intensivo y no distribuido. |
| 03/03 – 12/03/2026 | Sprint sostenido (David) | David mantiene actividad continua durante 8 días consecutivos con múltiples commits diarios de alta densidad. Se observa una progresión lógica y sistemática: primero arquitectura, luego módulos funcionales, finalmente integración de la API REST. |
| 02/03/2026 | Contribución puntual (Francisco) | Francisco realiza su única contribución del período en un único día, con un solo commit de código. No se registra actividad ni antes ni después. |
| 22/02 – 12/03/2026 | Inactividad total (Jesús) | Jesús no realiza ningún commit durante todo el período. Este patrón se extiende incluso desde diciembre de 2025, lo que apunta a una desvinculación prolongada del proyecto. |

### 2. Distribución del Trabajo

La distribución del trabajo durante el período ha sido **marcadamente desequilibrada**:

- **David Rafael Gdaniec** ha liderado el desarrollo de forma clara y sostenida, siendo responsable del **~85% de los commits efectivos** (17 de 28). Sus aportaciones cubren prácticamente la totalidad del proyecto Kotlin (MVVM completo, todos los módulos funcionales) y el proyecto API REST de cero. Es, sin duda, el miembro más activo y con mayor impacto técnico del equipo.

- **Joel Vives** ha realizado una contribución significativa en términos de calidad técnica (**10 commits efectivos**, todos centrados en el proyecto WPF), aunque concentrada en solo dos días del período. Su trabajo en el TPV, login y Material Design en WPF es sólido y relevante.

- **Francisco Javier García Gómez** presenta una participación mínima (**1 commit efectivo**), aportando únicamente una pantalla de login en Kotlin. Su nivel de implicación en el proyecto es muy bajo para el avance esperado en esta fase del desarrollo.

- **Jesús Manuel Liñan Almagro** no ha contribuido en absoluto al proyecto durante el período analizado ni en los meses precedentes. Su participación en el equipo está actualmente en riesgo.

### 3. Calidad del Trabajo

**Buenas prácticas detectadas:**
- David aplica de forma consistente la arquitectura **MVVM** en Kotlin (ViewModels, UiState, Events), con una separación clara de capas y un diseño escalable. La progresión de sus commits refleja un proceso planificado y metódico.
- Joel estructuró correctamente el proyecto WPF con separación de capas Services/ViewModels/Views y uso de interfaces para los servicios, lo que demuestra comprensión de patrones de diseño.
- La creación de un proyecto API REST completo con JPA en un período de 2-3 días (David) es una aportación técnica notable para el nivel del curso.
- David mantiene un diario técnico de calidad, con descripciones precisas y actualizadas.

**Áreas de mejora en calidad:**
- Joel realizó dos commits casi simultáneos en el mismo día (27/02) con mensajes que se solapan temáticamente (e.g., `selectedUserDialog primera version` y `Creados usuarios, implementacion casi terminada`). Una mejor granularidad en los commits mejora la trazabilidad.
- Se detectó un commit de Francisco que modifica el diario de otro compañero (`DavidRafael_Gdaniec.md`) por error, lo que refleja falta de cuidado en la gestión de ramas y commits.
- La adición de un archivo `.zip` (`PeluPos.zip`) en un commit de Joel es una mala práctica en Git; los binarios y archivos comprimidos no deben incluirse en el historial de código fuente.
- La calidad de los mensajes de commit es mejorable en general: "actuzlizacion diaria" (typo), "arreglos de bugs" son poco descriptivos. David en cambio tiende a escribir mensajes claros y expresivos.

### 4. Áreas de Mejora

1. **Distribución equitativa de tareas:** Es necesario revisar la asignación de trabajo en el equipo. El desequilibrio actual, donde un solo miembro asume la mayor parte del desarrollo, genera un riesgo técnico (dependencia de una sola persona) y académico (los demás no adquieren las competencias esperadas). Se recomienda replantear el sprint planning para garantizar que cada miembro tenga tareas concretas y verificables.

2. **Regularidad en los commits:** Concentrar todo el trabajo en un único día (como Joel con 27/02) no refleja una buena práctica de desarrollo colaborativo. Se recomienda distribuir los commits a lo largo de la semana para mejorar la trazabilidad y facilitar la integración continua.

3. **Mantenimiento del diario de trabajo:** Joel debe actualizar su diario inmediatamente tras cada sesión de trabajo, reflejando con precisión las tareas realizadas. Francisco debe retomar el hábito de actualizar el diario, dado que lleva sin hacerlo desde diciembre de 2025. Jesús debe reincorporarse activamente al proyecto y al seguimiento del diario.

4. **Reincorporación urgente de Jesús:** La ausencia de Jesús Liñan desde diciembre constituye el problema más grave del equipo. Se recomienda una reunión individual para identificar los motivos de la baja participación y establecer compromisos concretos y verificables para las próximas semanas.

5. **Buenas prácticas Git:** Evitar incluir archivos binarios o comprimidos en los commits, cuidar los mensajes descriptivos y evitar cometer cambios en archivos de otros compañeros por accidente. Se sugiere revisar las guías de uso de Git del equipo.

---

*Informe generado el 15/03/2026. Datos extraídos del historial de commits del repositorio Git del proyecto. Usuario excluido del análisis: @juanjobalmis.*
