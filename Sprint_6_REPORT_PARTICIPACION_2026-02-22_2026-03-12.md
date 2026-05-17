# Informe de Participación — Sprint 6
**Organización:** LosDelFondopi2damiesbalmis
**Proyecto:** PeluPOS
**Periodo analizado:** 2026-02-22 a 2026-03-12
**Generado el:** 2026-03-22

---

## 1. Estadísticas Generales

- **Total de usuarios activos:** 3 (DavidRafaelGdaniec, Joel Vives, Francisco Javier García Gómez)
- **Usuarios inactivos:** 1 (Jesus Manuel Liñan Almagro — sin commits desde noviembre 2025)
- **Periodo analizado:** 2026-02-22 a 2026-03-12
- **Días con actividad registrada:** 11 (23/02, 26/02, 27/02, 02/03, 03/03, 05/03, 06/03, 09/03, 10/03, 11/03, 12/03)
- **Días laborables del sprint:** ~14 (semanas del 23/02–27/02, 02/03–06/03, 09/03–12/03)
- **Total de commits (todos los autores):** 86
- **Archivos modificados únicos:** 278
- **Tareas Sprint 6 en tablero:** 11 — todas en estado `Done`

---

## 2. Registro de Commits

> Se listan los **commits efectivos** (excluyendo merges automáticos). Los commits de actualización de diario se indican explícitamente. Los merges de PR se omiten de esta tabla por no representar trabajo original.

### David Rafael Gdaniec

| Nombre | Fecha | Archivos modificados | Resumen |
|---|---|---|---|
| DavidRafaelGdaniec | 23/02/2026 | `EmpleadoFormScreen.kt`, `EmpleadoUiState.kt`, `EmpleadosEvent.kt`, `EmpleadosScreen.kt`, `EmpleadosViewModel.kt` | Creación de la última pantalla de empleados con pantalla principal, estadísticas y formulario de alta (Kotlin). |
| DavidRafaelGdaniec | 23/02/2026 | `TPVScreen.kt` (Kotlin) | Creación del TPVScreen con historial de facturas y creación de tickets. |
| DavidRafaelGdaniec | 23/02/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 26/02/2026 | `ClienteDetailScreen.kt`, `ClienteScreen.kt`, mocks cliente | Creación de pantallas de nuevo cliente y nuevo servicio, implementación de mocks de cliente (Kotlin). |
| DavidRafaelGdaniec | 26/02/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 03/03/2026 | Carpetas y estructura de proyecto Kotlin | Cambio en la estructura de carpetas del proyecto y comienzo del primer ViewModel. |
| DavidRafaelGdaniec | 03/03/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 05/03/2026 | `UsuarioFormScreen.kt`, `UsuarioScreen.kt` | Creación de las pantallas de usuarios (Kotlin). |
| DavidRafaelGdaniec | 05/03/2026 | `AddVentaScreen.kt`, `DetallesVentaScreen.kt`, `FacturacionEvent.kt`, `VentasScreen.kt`, `FacturacionViewModel.kt`, `TPVScreen.kt`, `TPVViewModel.kt` | Modificación del TPV para navegar a ventas y crear tickets; primeros dos ViewModels funcionales (Kotlin). |
| DavidRafaelGdaniec | 05/03/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 06/03/2026 | `SessionRepository.kt`, `ClienteRoute.kt`, `LoginRoute.kt`, `NavHost.kt`, `UsuariosRoute.kt`, `ClienteDetailScreen.kt`, `ClienteScreen.kt` | Creación de la lógica de clientes, login y usuarios (Kotlin). |
| DavidRafaelGdaniec | 06/03/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 09/03/2026 | `LocalDetailScreen.kt`, `LocalesEvent.kt`, `LocalesScreen.kt`, `LocalesUiState.kt`, `LocalesViewModel.kt`, `ModifyLocalScreen.kt`, `NewLocalScreen.kt`, `LocalRepository.kt`, `ModuleApp.kt`, `LocalesRoute.kt` | Creación funcional de los locales con CRUD completo (Kotlin). |
| DavidRafaelGdaniec | 09/03/2026 | `ProductosViewModel.kt`, `ServiciosViewModel.kt`, `ProductosUiState.kt`, `ServiciosUiState.kt`, `NewProductScreen.kt`, `NewServicioScreen.kt`, repositorios mock | Funcionalidad de productos terminada y servicios en curso (Kotlin). |
| DavidRafaelGdaniec | 09/03/2026 | `ServiciosRoute.kt`, `NavHost.kt`, `MainScreen.kt` | Creación de la navegación a servicios (Kotlin). |
| DavidRafaelGdaniec | 09/03/2026 | `AppNavHost.kt`, `DashboardRoute.kt`, `EmpleadosRoute.kt`, `ProductsRoute.kt`, `VentasRoute.kt`, `DashboardViewModel.kt`, `DashboardUiState.kt`, `MainScreen.kt`, `EmpleadoUiState.kt`, `EmpleadosViewModel.kt` | Creación del ViewModel de empleado, pantalla principal y la aplicación ya funcional con datos mock (Kotlin). |
| DavidRafaelGdaniec | 09/03/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 10/03/2026 | `deploymentTargetSelector.xml`, `SessionRepository.kt`, `FacturaDaoMock.kt`, `FacturaRepository.kt`, `UsuarioDaoMock.kt`, `ModuleApp.kt`, `AppNavHost.kt`, `LoginRoute.kt`, `Navigation.kt`, `NavButton.kt`, `NavSeparator.kt`, `SelectorEmpleados.kt`, `Sidebar.kt`, `LoginEvent.kt`, `LoginScreen.kt`, `LoginViewModel.kt`, `PermisosScreen.kt`, `FacturacionViewModel.kt` | Eliminación de navegación antigua y composables sin uso; creación de pantalla de permisos y actualización correcta del dashboard (Kotlin). |
| DavidRafaelGdaniec | 10/03/2026 | `peluposbd.sql` | Creación del script SQL de la base de datos para la API REST. |
| DavidRafaelGdaniec | 10/03/2026 | `DavidRafael_Gdaniec.md` (x2) | Actualizaciones del diario de trabajo. |
| DavidRafaelGdaniec | 11/03/2026 | `peluposbd.sql`, `diagrama base de datos.jpg`, múltiples JPA controllers (`ClienteJpaController.java`, `EmpleadoJpaController.java`, `FacturaJpaController.java`, etc.), entidades JPA, `ServiceRestClientes.java`, `ServiceRestEmpleados.java`, `ServiceRestFacturas.java`, `ServiceRestLocal.java`, `ServiceRestProductos.java`, `ServiceRestServicios.java`, `ServiceRestUsuario.java`, `ApplicationConfig.java`, libs Jakarta EE | Creación de la base de datos verificada con el profesor y estructura completa de API REST Jakarta EE con JPA (GET funcional para todas las entidades). |
| DavidRafaelGdaniec | 11/03/2026 | `DatosPruebaPeluPosBD.sql`, `ServiceRestClientes.java`, `ServiceRestEmpleados.java`, `ServiceRestServicios.java`, `LocalDetailScreen.kt`, `ServicioScreen.kt` | Script de datos de prueba para la BD y API REST sin seguridad terminada (pendiente POST/PUT/DELETE en Postman). |
| DavidRafaelGdaniec | 11/03/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |
| DavidRafaelGdaniec | 12/03/2026 | `Factura.java`, `ServiceRestClientes.java`, `ServiceRestEmpleados.java`, `ServiceRestFacturas.java`, `ServiceRestLocal.java`, `ServiceRestProductos.java`, `ServiceRestServicios.java`, colecciones Postman (Clientes, Empleados, Facturas, Locales, Productos, Servicios, Usuarios) | Arreglo de errores en la API y pruebas completas de POST, PUT y DELETE con Postman para todas las entidades. |
| DavidRafaelGdaniec | 12/03/2026 | `DavidRafael_Gdaniec.md` | Actualización del diario de trabajo. |

### Joel Vives

| Nombre | Fecha | Archivos modificados | Resumen |
|---|---|---|---|
| Joel Vives | 27/02/2026 | WPF — `selectedUserDialog` (views/viewmodels) | Creación de la primera versión del diálogo de selección de usuario (WPF). |
| Joel Vives | 27/02/2026 | WPF — `TPVViewModel` | Creación del ViewModel para el TPV (primera versión). |
| Joel Vives | 27/02/2026 | WPF — `TPVPage` (view) | Creación de la primera versión de la pantalla TPV. |
| Joel Vives | 27/02/2026 | WPF — Navegación | Implementación de la navegación hacia el TPV. |
| Joel Vives | 27/02/2026 | WPF — Usuarios (views/services) | Creación de la lógica de usuarios; implementación casi terminada. |
| Joel Vives | 27/02/2026 | WPF — `TPVPage`, servicios, viewmodels | Implementación del TPV completada. |
| Joel Vives | 27/02/2026 | WPF — `UsuariosPage` (view) | Creación de la pantalla de usuarios. |
| Joel Vives | 27/02/2026 | WPF — `LoginPage`, `LoginViewModel` | Primera versión de la pantalla de login con ViewModel. |
| Joel Vives | 27/02/2026 | WPF — `LoginPage`, `LoginViewModel` | Login terminado correctamente con autenticación funcional. |
| Joel Vives | 02/03/2026 | WPF — recursos MaterialDesign | Añadido de la librería MaterialDesign al proyecto WPF (no completado). |
| Joel Vives | 02/03/2026 | `Joel_Vives.md` | Actualización del diario de trabajo. |

### Francisco Javier García Gómez

| Nombre | Fecha | Archivos modificados | Resumen |
|---|---|---|---|
| Francisco García | 02/03/2026 | WPF — `LoginScreen` | Creación de la pantalla de login en WPF. |
| Francisco García | 02/03/2026 | `DavidRafael_Gdaniec.md` ⚠️ | **Anomalía:** el commit titulado "Actualización diario" modifica el diario de David, no el propio. |

---

## 3. Análisis Individualizado de Contribuciones

| Usuario | Commits (Efectivos)* | Días activos | Contribución principal | Seguimiento (Diario) | Discrepancias |
|---|---|---|---|---|---|
| DavidRafaelGdaniec | ~17 efectivos (71 totales incl. merges/diario) | 10 / 14 lab. | Desarrollo completo de la app Kotlin (UI + lógica + data mocks) y creación de la API REST Java (JPA, Jakarta EE, BD SQL, Postman) | Diario detallado y al día: entradas desde 23/02 con resumen de horas y tareas | Leve duplicación en commit del 03/03 con descripción similar; tasas de merge muy altas (normal con ramas feature) |
| Joel Vives | ~10 efectivos (12 total) | 2 / 14 lab. | Implementación completa del TPV y login en WPF en un único día intenso (27/02); pequeña contribución el 02/03 | Diario con entrada 27/02 referenciada en git; entrada de 02/03 breve. No hay entradas para semanas del 03/03 al 12/03 | **Sin tareas asignadas en el tablero Sprint 6** pese a haber realizado trabajo WPF relevante; ausencia total las 2 últimas semanas del sprint |
| Francisco García | ~1 efectivo (3 total) | 1 / 14 lab. | Creación del login screen en WPF el 02/03 | **Sin entradas en diario desde diciembre 2025.** El commit "Actualización diario" del 02/03 modifica el diario de David, no el suyo | Contribución completamente desconectada de la tarea asignada (lógica Kotlin); **el diario propio no se actualiza en toda la duración del sprint** |
| Jesus Liñan | 0 efectivos (0 total) | 0 / 14 lab. | Sin actividad | **Sin entradas en diario desde noviembre 2025** | Ausencia total; sin tareas asignadas en Sprint 6 |

> *Commits efectivos = commits reales de código o documentación, excluyendo merge-commits de PR y commits cuyo único cambio es el diario.

---

## 4. Resumen Análisis 📈 (Individual)

---

**Nombre: David Rafael Gdaniec (@DavidRafaelGdaniec)**

- **Contribución principal:** David ha liderado el desarrollo técnico de todo el Sprint 6. Por un lado ha construido de cero (o casi) la **app Android en Kotlin** con Jetpack Compose: login + permisos, gestión de empleados, clientes, usuarios, locales, productos y servicios, TPV funcional con ventas y facturación — todo con repositorios mock, ViewModels y arquitectura MVVM limpia. Por otro ha diseñado, verificado y creado la **API REST Indonesia JPA/Jakarta EE** para conectar la BD MySQL con los clientes (Kotlin + WPF), incluyendo el script SQL completo, los JPA controllers, todos los endpoints GET funcionales y las pruebas POST/PUT/DELETE en Postman.

- **Seguimiento:** El diario está perfectamente al día, con entradas detalladas (fecha, sesión, tareas, horas) para cada día de trabajo del sprint. Las horas registradas (2h el 23/02, 2h el 26/02, 1h el 02/03, 2h el 03/03, 5h el 05/03, 5h el 06/03, 5h el 09/03, 2h el 10/03, 4.5h el 11/03, 3h el 12/03) son coherentes con la envergadura de los commits realizados. No se detectan discrepancias relevantes entre el diario y el historial de commits.

- **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** David tiene **11 tareas asignadas en Sprint 6**, todas en estado **Done**:
  - ✅ #43 Crear las páginas principales en Kotlin
  - ✅ #44 Crear las páginas de administrador y visualización de los datos
  - ✅ #45 Crear la pantalla de TPV con diseño básico
  - ✅ #56 Comenzar y terminar la lógica de la aplicación de Kotlin (co-asignada con @kennzzaki)
  - ✅ #69 Reflejar los datos mock ya funcional del TPV con Ventas
  - ✅ #70 Hacer la lógica correcta para usuarios y el login
  - ✅ #71 Creación de los clientes correcta
  - ✅ #72 Creación correcta de los productos y de los servicios
  - ✅ #73 Creación correcta de los locales
  - ✅ #83 Creación funcional para el dashboard con datos mock
  - ✅ #92 Creación de la base de datos SQL

  Todos los commits del repositorio respaldan clara y cronológicamente el cierre de cada tarea. La tarea #92 (BD SQL) se enlaza directamente con los commits de API REST del 10/03 al 12/03.

- **Participación en el grupo de forma activa:** 🟢 — Activo 10 de 14 días laborables, con commits relevantes y progresivos durante todo el sprint. Carga de trabajo muy por encima de la media del equipo.

- **Registro en Diario de Trabajo durante el Sprint:** 🟢 — Diario detallado y reflexivo, actualizado con regularidad (9 entradas en el sprint), con horas estimadas y descripción de problemas/soluciones.

- **Participación en el Incremento:** 🟢 — Contribución muy significativa: la app Kotlin es funcional con datos mock y la API REST está operativa con todas las entidades. Las 11 tareas del sprint pasan a "Done" gracias principalmente a su trabajo.

---

**Nombre: Joel Vives (@SkYGi560)**

- **Contribución principal:** Joel realizó un trabajo intenso el **27/02** aplicando la implementación del TPV en WPF: creó el dialog de selección de usuario, el ViewModel del TPV, la pantalla TPVPage, la navegación, la pantalla de usuarios y la pantalla de login (con primera versión y versión final). El **02/03** añadió MaterialDesign al proyecto (sin concluir). No hay more commits hasta el final del sprint.

- **Seguimiento:** El diario incluye entradas para el período de Sprint 6 (27/02 y 02/03), pero **sin cubrir las dos últimas semanas del sprint** (desde 03/03 hasta 12/03 no hay ninguna entrada). Esto supone un seguimiento parcial e incompleto para el conjunto del sprint.

- **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** Joel **no tiene ninguna tarea asignada en el tablero del Sprint 6**. Las tareas WPF que le corresponden (`Crear las páginas principales en WPF`, `Crear la página del TPV`) aparecen asignadas al **Sprint 7**, lo que sugiere que el trabajo que realizó durante este sprint no fue registrado en el tablero en el momento correcto o que el tablero no refleja fielmente la planificación de Joel. Esta es una discrepancia importante entre el repositorio y el tablero Scrum.

- **Participación en el grupo de forma activa:** 🟡 — Participó activamente durante el primer tercio del sprint (27/02–02/03), pero estuvo completamente inactivo las dos semanas siguientes. La concentración de toda la actividad en un solo día y la ausencia posterior limitan la valoración.

- **Registro en Diario de Trabajo durante el Sprint:** 🟡 — Hay entradas para los días de actividad del sprint, pero el diario deja de actualizarse a partir del 02/03. Sección de marzo 2026 muy incompleta (solo 1 entrada).

- **Participación en el Incremento:** 🟡 — El trabajo de WPF (TPV, login, usuarios) es técnicamente sólido y supone una contribución real al incremento, pero al no estar registrado en el sprint board ni continuado durante las semanas siguientes, el impacto se ve limitado desde la perspectiva Scrum.

---

**Nombre: Francisco Javier García Gómez (@kennzzaki)**

- **Contribución principal:** Francisco realizó únicamente **un commit efectivo** durante todo el Sprint 6: la creación del login screen en WPF el **02/03/2026**. Su aportación es muy escasa en comparación con las expectativas y con la tarea que tiene asignada (#56 "Comenzar y terminar la lógica de la aplicación de Kotlin"), que fue ejecutada en su totalidad por David.

- **Seguimiento:** El diario propio **no tiene ninguna entrada desde diciembre 2025**. El único commit de actualización de diario del sprint (02/03) modifica erróneamente el **diario de David**, no el suyo. Esto apunta a que Francisco estaba trabajando en la rama de David y realizó un commit sobre el archivo equivocado, o que no gestionó correctamente su rama de trabajo.

- **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** Francisco está co-asignado con David en la tarea **#56 — "Comenzar y terminar la lógica de la aplicación de Kotlin"**, marcada como `Done`. Sin embargo, la totalidad del trabajo visible en el repositorio para esta tarea lo han realizado David y Joel. El único commit de Francisco en el sprint es de WPF, no de Kotlin. No existe evidencia en el repositorio de que Francisco haya contribuido a la lógica Kotlin. Hay, por tanto, una discrepancia clara entre la asignación de la tarea y la participación real.

- **Participación en el grupo de forma activa:** 🔴 — Activo únicamente 1 de 14 días laborables del sprint, con una única contribución efectiva mínima. Las semanas 2 y 3 del sprint no tienen actividad registrada.

- **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin entradas en el diario durante el sprint. El commit "Actualización diario" modificó el diario de otro compañero por error.

- **Participación en el Incremento:** 🔴 — La contribución al incremento es prácticamente nula. El commit de WPF (login screen) representa trabajo inicial pero incompleto, y no guarda relación con la tarea asignada en el sprint.

---

**Nombre: Jesus Manuel Liñan Almagro (@jesuslalm)**

- **Contribución principal:** Ninguna. Jesus no ha realizado ningún commit en el período del Sprint 6, ni en los sprints anteriores desde noviembre de 2025.

- **Seguimiento:** El diario de Jesus está vacío desde noviembre de 2025. No existe ninguna actualización durante el Sprint 6.

- **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** Jesus **no tiene ninguna tarea asignada en el Sprint 6**. Su ausencia en el tablero y en el repositorio es completa.

- **Participación en el grupo de forma activa:** 🔴 — Sin ninguna actividad registrada durante el sprint.

- **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin ninguna entrada de diario desde noviembre 2025.

- **Participación en el Incremento:** 🔴 — Sin ninguna contribución al incremento del sprint.

---

## 5. Resumen Análisis 📈 (Por Grupo)

**LosDelFondopi2damiesbalmis — PeluPOS**

- **Funcionalidad del incremento entregado:** 🟡 — Solo un miembro activo (David) tiene tareas marcadas como `Done` en el tablero de Sprint 6. Joel trabajó en WPF con commits relevantes, pero no tiene tareas asignadas al sprint en el tablero. Francisco y Jesus no contribuyeron al incremento de forma significativa. El producto tiene un avance real gracias al trabajo de David, pero el equipo no participó conjuntamente como cabría esperar en Scrum.

- **Realización de los eventos de Scrum, aplicando su filosofía:** 🔴 — No se han encontrado **tarjetas de retrospectiva para el Sprint 6** en el tablero de GitHub Projects. Los sprints anteriores (1, 2, 3, 5) sí tienen retrospectiva registrada, lo que supone una ruptura del patrón en este sprint. El reparto de tareas entre los miembros activos es muy desigual (prácticamente todo en un solo miembro), y Joel trabaja fuera de las tareas planificadas del sprint.

- **Compromiso del equipo con el flujo de trabajo de Scrum a través de GitHub Projects:** 🟡 — El tablero contiene 11 tareas en `Done`, todas cerradas correctamente, lo que indica un buen uso del tablero por parte de David. Sin embargo:
  - Joel no tiene **ninguna tarea asignada** en Sprint 6 pese a contribuir activamente.
  - Francisco está co-asignado en una tarea Kotlin pero su trabajo real fue en WPF.
  - Jesus no aparece en ninguna tarea.
  - La mayoría de tareas estaban asignadas a un solo miembro, sin distribución equitativa del backlog.

---

## 6. Resumen Ejecutivo 📈

1. **Tendencias y Patrones:**
   - **2026-02-22 al 2026-03-12 — Sprint 6 muy desequilibrado:** La dinámica de trabajo muestra una concentración extrema de actividad en un único miembro (David Rafael Gdaniec), quien acaparó el 83% de los commits totales (71/86) y la totalidad de las tareas del tablero. Joel tuvo un "burst" de actividad puntual (27/02) que produjo trabajo WPF de calidad pero no sostenido. Francisco y Jesus estuvieron prácticamente ausentes.

2. **Calidad del Trabajo:**
   - La calidad técnica del trabajo de **David** es muy alta: arquitectura MVVM correcta en Kotlin, uso de Hilt para inyección de dependencias, separación entre UI/ViewModel/Repositorio y una API REST bien estructurada con JPA controllers y soporte completo CRUD. Los commits son descriptivos y el workflow de ramas + PR se usa de forma consistente.
   - El trabajo de **Joel** en WPF es funcional y está bien organizado (ViewModel, navegación, múltiples pantallas en un solo día), aunque la falta de continuidad lo penaliza.
   - El trabajo de **Francisco** es muy limitado y presenta problemas de proceso (commit en rama de otro miembro, diario modificado erróneamente).

3. **Áreas de Mejora:**
   - **Distribución del trabajo:** Es necesario que todos los miembros del equipo participen activamente en cada sprint. Actualmente solo David sostiene el incremento.
   - **Tablero Scrum al día:** Joel debe registrar sus tareas en el sprint board en el momento en que las comienza, no dejarlas para el sprint siguiente. La retrospectiva de Sprint 6 no se ha registrado — es necesario hacerlo.
   - **Diarios de trabajo:** Francisco, Joel (parcialmente) y Jesus deben actualizar sus propios diarios durante el sprint, no al final o de forma retroactiva. El error de Francisco al actualizar el diario de David indica una mala gestión de ramas.
   - **Compromiso y presencia:** Se recomienda una conversación directa con Jesus Liñan y Francisco García sobre su implicación en el proyecto, ya que su ausencia continuada pone en riesgo la consecución de los objetivos restantes del curso.
   - **Retrospectiva:** El equipo debe realizar y registrar la retrospectiva del Sprint 6 en el tablero antes de cerrar el sprint formalmente.
