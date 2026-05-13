# Sprint 8 — Informe de Participación

**Organización:** LosDelFondopi2damiesbalmis  
**Proyecto:** PeluPOS  
**Periodo:** 2026-04-13 a 2026-04-30  
**Sprint:** 8  

---

## 1. Estadísticas Generales

* **Total de usuarios activos:** 1 (Joel Vives)
* **Periodo analizado:** 2026-04-13 a 2026-04-30
* **Días con actividad:** 2 (20 y 22 de abril)
* **Total de commits:** 6 (todos efectivos de código, realizados por Joel Vives)
* **Archivos modificados únicos:** 33

---

## 2. Registro de Commits

| Nombre | Fecha | Archivos modificados | Resumen de lo que ha realizado |
|---|---|---|---|
| Joel Vives | 2026-04-20 | `API Rest PeluPosBD/.../ServiceRESTAuth.java` | Modificación del response de la API REST Java (endpoint de autenticación) para ajustar el formato de respuesta al esperado por el cliente WPF. |
| Joel Vives | 2026-04-20 | `Models/ApiDtos/Auth/LoginRequestDto.cs`, `LoginResponseDto.cs`, `Empleados/EmpleadoDto.cs`, `Usuarios/UsuarioDto.cs` | Creación de los DTOs básicos de autenticación y empleado en el proyecto WPF para mapear las respuestas de la API. |
| Joel Vives | 2026-04-20 | `Services/Api/ApiClient.cs`, `AuthApiService.cs`, `UsuarioApiService.cs`, `Services/RoleMapper.cs`, `Services/SessionService.cs`, `ViewModels/LoginViewModel.cs`, `Views/LoginDialog.xaml`, `Views/LoginDialog.xaml.cs`, `MainWindow.xaml.cs` | Implementación de la capa de conexión con la API en WPF: cliente HTTP centralizado (`ApiClient`), servicios de autenticación y usuarios, mapeador de roles, gestión de sesión y flujo completo de login con diálogo visual. |
| Joel Vives | 2026-04-20 | `ApiDtos/Clientes/ClienteDto.cs`, `Common/ApiMessageDto.cs`, `Empleados/EmpleadoVentasResumenDto.cs`, `Facturas/FacturaDto.cs`, `Facturas/FacturasProductoDto.cs`, `Facturas/FacturasProductoPkDto.cs`, `Facturas/FacturasServicioDto.cs`, `Facturas/FacturasServicioPkDto.cs`, `Locales/LocalDto.cs`, `Productos/ProductoDto.cs`, `Servicios/ServicioDto.cs` | Creación de todos los DTOs restantes para mapear las entidades de la API REST (clientes, facturas, locales, productos, servicios). |
| Joel Vives | 2026-04-20 | `Services/Api/ClienteApiService.cs`, `EmpleadoApiService.cs`, `FacturaApiService.cs`, `LocalApiService.cs`, `ProductoApiService.cs`, `ServicioApiService.cs` | Creación de los servicios API para todas las entidades restantes, completando la capa de comunicación con el backend. |
| Joel Vives | 2026-04-22 | `Models/ApiDtos/Empleados/EmpleadoMiniDto.cs`, `Models/ApiDtos/Usuarios/EmpleadoMiniDto.cs`, `Models/ApiDtos/Usuarios/UsuarioDto.cs` | Reorganización del DTO `EmpleadoMiniDto` (movido de la carpeta `Empleados` a `Usuarios`) y ajuste de `UsuarioDto` para mayor coherencia del modelo. |

---

## 3. Análisis Individualizado de Contribuciones

| Usuario | Commits (Efectivos)* | Días activos | Contribución principal | Seguimiento (Diario) | Discrepancias |
|---|---|---|---|---|---|
| Joel Vives (kennzzaki) | 6 | 2 / 14 días hábiles | Integración completa de WPF con la API REST: DTOs, servicios API, autenticación y sesión | Sin entradas en el diario para abril 2026. Última entrada: 02/03/2026 | Realizó trabajo correspondiente a las tareas de Francisco (integración WPF-API) pero sin estar asignado en el tablero. No hay tarea en Sprint 8 asociada a su trabajo. |
| Francisco Garcia (SkYGi560) | 0 | 0 / 14 | Sin actividad en el sprint | Sin entradas desde diciembre 2025 | Tiene 3 tareas en Sprint 8 (`Crear las paginas principales en WPF` y `Crear la pagina del TPV` en "Doing", `Pasar los datos de prueba a mocks` en "Product Backlog") pero 0 commits. El tablero no refleja la realidad. |
| DavidRafaelGdaniec | 0 | 0 / 14 | Sin actividad en el sprint | Sin entradas desde marzo 2026 | Tiene 1 tarea asignada (`Implementación de la API Rest a Kotlin`) en estado "Product Backlog", sin ningún commit relacionado durante el sprint. |
| Jesus Liñan (jesuslalm) | 0 | 0 / 14 | Sin actividad en el sprint | Sin entradas desde noviembre 2025 | Sin tareas asignadas. Ausencia prolongada sin cambios. |

*\*Commits efectivos: excluyen merges y actualizaciones de diario. En este sprint todos los commits de Joel son efectivos de código.*

---

## 4. Resumen Análisis 📈 (Individual)

---

**Joel Vives (kennzzaki):**

* **Contribución principal:** Ha sido el único miembro activo del Sprint 8. En dos días concentró una aportación técnica relevante: la integración completa del cliente WPF con la API REST, incluyendo cliente HTTP centralizado, servicios para todas las entidades, DTOs completos, gestión de sesión, mapeador de roles y flujo de login con diálogo visual funcional. También realizó un ajuste en el backend Java (ServiceRESTAuth) para adaptar el formato de respuesta.

* **Seguimiento:** El diario de trabajo no tiene ninguna entrada para abril de 2026. La última entrada registrada data del 02/03/2026. La ausencia total de seguimiento durante el sprint es un punto negativo claro, especialmente cuando la actividad técnica fue significativa.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** Joel no tiene ninguna tarea asignada formalmente en Sprint 8 en el tablero de GitHub Projects. Sin embargo, el trabajo realizado corresponde funcionalmente a las tareas asignadas a Francisco (`Crear las páginas principales en WPF`, `Crear la página del TPV`). Esta desconexión entre quien realiza el trabajo y quien tiene la tarea asignada en el tablero constituye una discrepancia seria en la gestión del sprint. Ninguna de sus contribuciones se refleja en el tablero Scrum.

* **Participación en el grupo de forma activa:** 🟡 — Realiza trabajo técnico de calidad, pero la actividad se concentra en solo 2 de 14 días hábiles (14 % del sprint) y no hay planificación formal de su trabajo en el tablero.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin ninguna entrada en el diario para el periodo del Sprint 8. El diario está abandonado desde marzo de 2026.

* **Participación en el Incremento:** 🟡 — La aportación técnica es significativa y el código producido conecta la aplicación WPF con la API REST. Sin embargo, las tareas no están formalmente asignadas ni registradas en el tablero, lo que impide rastrear el incremento a través del flujo Scrum.

---

**Francisco Garcia (SkYGi560):**

* **Contribución principal:** Ninguna en el Sprint 8. No hay commits relacionados con las tareas que tiene asignadas.

* **Seguimiento:** El diario no tiene entradas desde diciembre de 2025, casi cinco meses de abandono.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** Tiene 3 tareas asignadas en Sprint 8:
  - `Crear las paginas principales en WPF` → **Doing** (sin commits asociados)
  - `Crear la pagina del TPV` → **Doing** (sin commits asociados)
  - `Pasar los datos de prueba a mocks en WPF` → **Product Backlog** (no iniciada)

  El estado "Doing" en dos tareas no se corresponde con actividad real en el repositorio. Estas tareas las realizó en la práctica Joel Vives, aunque sin estar formalmente asignado. Se detecta una discrepancia grave entre el tablero y el repositorio.

* **Participación en el grupo de forma activa:** 🔴 — Ausencia total de actividad en el sprint.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin entradas desde diciembre 2025.

* **Participación en el Incremento:** 🔴 — Sin contribución al incremento del sprint.

---

**David Rafael Gdaniec (DavidRafaelGdaniec):**

* **Contribución principal:** Ninguna en el Sprint 8. No hay commits en el periodo.

* **Seguimiento:** El diario tiene su última entrada el 17/03/2026. Sin entradas para el periodo del Sprint 8.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** Tiene 1 tarea asignada:
  - `Implementación de la API Rest a Kotlin` → **Product Backlog** (no iniciada)

  La tarea sigue en estado inicial sin ningún avance registrado ni en el tablero ni en el repositorio. El sprint finaliza sin que David haya iniciado su única tarea asignada.

* **Participación en el grupo de forma activa:** 🔴 — Ausencia total durante el sprint, sin ningún commit ni actividad registrada.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin entradas para el periodo del Sprint 8. El diario quedó parado tras el Sprint 7.

* **Participación en el Incremento:** 🔴 — Sin contribución al incremento del sprint.

---

**Jesús Liñan (jesuslalm):**

* **Contribución principal:** Ninguna en el Sprint 8. No hay commits ni tareas asignadas.

* **Seguimiento:** Sin entradas en el diario desde noviembre de 2025, lo que acumula ya más de cinco meses de abandono completo.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** No tiene ninguna tarea asignada en Sprint 8. Su nombre no aparece en ninguna tarjeta del tablero en este sprint.

* **Participación en el grupo de forma activa:** 🔴 — Ausencia prolongada sin ninguna evidencia de actividad.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Diario completamente abandonado desde noviembre de 2025.

* **Participación en el Incremento:** 🔴 — Sin contribución al incremento del sprint.

---

## 5. Resumen Análisis 📈 (Por Grupo)

**LosDelFondopi2damiesbalmis — PeluPOS:**

* **Funcionalidad del incremento entregado:** 🔴 — Ninguna tarea del Sprint 8 ha alcanzado el estado "Done" al final del periodo. Las tareas de Francisco permanecen en "Doing" (sin commits reales que las respalden) y la tarea de David en "Product Backlog". El trabajo realizado por Joel, aunque técnicamente valioso, no está registrado en el tablero y no puede contabilizarse como incremento formal del sprint.

* **Realización de los eventos de Scrum, aplicando su filosofía:** 🔴 — Solo un miembro del equipo trabajó activamente durante el sprint. No hay tarjetas de retrospectiva para el Sprint 8 en el tablero. La desconexión entre quien hace el trabajo (Joel) y quien tiene las tareas asignadas (Francisco) indica que el Sprint Planning no se realizó correctamente o que los acuerdos tomados en él no se cumplieron.

* **Compromiso del equipo con el flujo de trabajo de Scrum a través de GitHub Projects:** 🔴 — El tablero no refleja la realidad del sprint: Joel realizó trabajo sin tener tareas asignadas, Francisco tiene tareas en "Doing" sin commits, y David tiene su tarea en "Product Backlog" sin avance. El flujo de trabajo Scrum no se ha seguido en ninguno de sus aspectos fundamentales durante este sprint.

---

## 6. Resumen Ejecutivo 📈

1. **Tendencias y Patrones:**
   * **2026-04-20 a 2026-04-22 — Trabajo puntual sin planificación:** El Sprint 8 muestra el patrón más preocupante hasta la fecha: Joel Vives realiza un bloque de trabajo intensivo (5 commits en un solo día) que resuelve funcionalidades importantes, pero sin coordinación con el tablero ni con el resto del equipo. El trabajo es técnicamente útil pero metodológicamente incoherente dentro del marco Scrum.
   * **Desconexión crónica del equipo:** Tres de los cuatro miembros llevan entre 2 y 5 meses sin actividad en el repositorio. Esta situación se ha repetido en sprints anteriores sin corrección visible.
   * **Inversión de roles no documentada:** El trabajo que correspondía a Francisco (integración WPF-API) lo realizó Joel, pero sin asignación formal. Esta práctica, aunque resuelve el problema técnico a corto plazo, invisibiliza quién trabaja realmente y quién no en el proyecto.

2. **Calidad del Trabajo:**
   * La integración de WPF con la API REST realizada por Joel tiene buena estructura: cliente HTTP centralizado, servicios separados por entidad, DTOs bien tipados y flujo de autenticación completo. El hecho de que también ajustara el backend (ServiceRESTAuth) para hacerlo compatible refleja comprensión del sistema completo.
   * El mensaje de commit "cosas" (22/04) es un ejemplo de mala práctica: los mensajes de commit deben ser descriptivos para facilitar el seguimiento del historial. En este caso el cambio real (reorganización de DTOs) habría merecido un mensaje explicativo.

3. **Áreas de Mejora:**
   * **Crítico — Asignación real de tareas:** Las tareas del tablero deben reflejar quién realmente va a hacer el trabajo. Si Joel va a trabajar en la integración WPF, debe tener las tareas asignadas a él, no a Francisco.
   * **Crítico — Reincorporación inmediata:** Francisco, David y Jesús deben comprometerse con el proyecto en el siguiente sprint. Tres miembros inactivos en un equipo de cuatro es insostenible.
   * **Retrospectiva obligatoria:** No se ha creado ninguna tarjeta de retrospectiva desde el Sprint 5. Este evento es imprescindible para que el equipo reflexione sobre lo que está fallando y proponga mejoras concretas.
   * **Diarios al día:** Ningún miembro tiene el diario actualizado para el periodo del Sprint 8. Joel debería haber registrado su trabajo intensivo del 20 de abril; los demás deben retomar el hábito urgentemente.
   * **Mensajes de commit descriptivos:** Mensajes como "cosas" no aportan información útil al historial. Se recomienda seguir el formato estándar: tipo + descripción breve de los cambios reales.
