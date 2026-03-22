# Informe de Participación — Sprint 5
**Organización:** LosDelFondopi2damiesbalmis  
**Proyecto:** PeluPOS  
**Periodo analizado:** 2026-01-20 a 2026-02-12  
**Sprint:** 5 (inicio oficial en tablero: 2026-01-22, duración: 22 días)

---

## 1. Estadísticas Generales

* **Total de usuarios activos:** 2 (DavidRafaelGdaniec, Joel Vives)
* **Usuarios inactivos (sin commits):** 2 (Francisco García / kennzzaki, Jesús Liñan / jesuslalm)
* **Periodo analizado:** 2026-01-20 a 2026-02-12
* **Días con actividad:** 2 (22/01/2026 y 12/02/2026)
* **Total de commits (excluido profesor):** 7 (4 efectivos + 3 merges)
* **Archivos modificados únicos:** 22

---

## 2. Registro de Commits

| Nombre | Fecha | Archivos modificados | Resumen de lo que ha realizado |
|---|---|---|---|
| DavidRafaelGdaniec | 2026-01-22 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario de trabajo (reestructuración de 26 ins / 25 del) |
| DavidRafaelGdaniec | 2026-01-22 | *(Merge PR #59 — sin archivos propios)* | Merge pull request #59 desde rama `david` |
| DavidRafaelGdaniec | 2026-02-12 | `app/build.gradle.kts`, `locales/LocalesScreen.kt`, `locales/ModifyLocalScreen.kt`, `locales/NewLocalScreen.kt`, `ui/navigation/DashboardRoute.kt`, `ui/navigation/LocalesRoute.kt`, `ui/navigation/NavGraphBuilder.kt`, `ui/navigation/ProductsRoute.kt`, `ui/navigation/VentasRoute.kt`, `gradle/libs.versions.toml`, `docs/diarios/DavidRafael_Gdaniec.md` | Refactorización de la navegación en la app Kotlin: implementación de `NavHost` y `NavGraphBuilder`, reubicación de la carpeta `locales` bajo `ui/`. Se crean rutas dedicadas para locales, dashboard, productos y ventas (11 archivos, +260 líneas) |
| DavidRafaelGdaniec | 2026-02-12 | *(Merge PR #61 — sin archivos propios)* | Merge pull request #61 desde rama `david` |
| Joel Vives | 2026-02-12 | `docs/diarios/Joel_Vives.md` | Actualización del diario de trabajo: entrada del 12/02 (+6 líneas) |
| Joel Vives | 2026-02-12 | `MainWindow.xaml.cs`, `Services/Ventas/IVentaService.cs`, `Services/Ventas/VentaDetalleViewModel.cs`, `Services/Ventas/VentaService.cs`, `ViewModels/VentaPage/VentaRowViewModel.cs`, `ViewModels/VentaPage/VentasViewModel.cs`, `Views/Ventas/VentaDetalleDialog.xaml`, `Views/Ventas/VentaDetalleDialog.xaml.cs`, `Views/Ventas/VentasPage.xaml`, `Views/Ventas/VentasPage.xaml.cs` | Creación completa del módulo de Ventas en WPF: pantalla de listado (`VentasPage`), diálogo de detalle (`VentaDetalleDialog`), ViewModel de listado (`VentasViewModel`), ViewModel de detalle (`VentaDetalleViewModel`), servicio (`VentaService`) e interfaz (`IVentaService`) (10 archivos, +564 líneas) |
| Joel Vives | 2026-02-12 | *(Merge — sin archivos propios)* | Merge branch 'main' de GitHub |

---

## 3. Análisis Individualizado de Contribuciones

| Usuario | Commits (Efectivos)* | Días activos | Contribución principal | Seguimiento (Diario) | Discrepancias |
|---|---|---|---|---|---|
| DavidRafaelGdaniec | 1 (código) + 1 (diario) | 2 / 24 días | Refactorización de la arquitectura de navegación en la app Kotlin (NavHost + NavGraphBuilder) y reorganización de carpetas | Al día: entrada del 22/01 y del 12/02 que coinciden con sus commits | Coherente. El diario del 22/01 menciona "Creación de las tareas del Sprint 5" pero el commit solo toca el diario; sin evidencia de creación de tareas en código ese día |
| Joel Vives | 1 (código) + 1 (diario) | 1 / 24 días | Implementación completa del módulo Ventas en WPF (vista, ViewModel, servicio, diálogo de detalle) | Solo una entrada en el periodo (12/02). Sin registro para el resto del sprint | No hay entradas en el diario entre el 15/12/2025 y el 12/02/2026 (casi 2 meses). El commit de código coincide exactamente con la entrada del diario del 12/02 |
| kennzzaki (Francisco García) | 0 | 0 / 24 días | Sin actividad | Sin entradas a partir de diciembre 2025 | El diario se detiene en diciembre 2025. Sin ningún commit ni entrada al diario durante el Sprint 5 |
| jesuslalm (Jesús Liñan) | 0 | 0 / 24 días | Sin actividad | Sin entradas a partir de noviembre 2025 | El diario se detiene en noviembre 2025. Sin ningún commit ni entrada al diario durante el Sprint 5 |

> *Commits efectivos: excluidos merges y commits exclusivos de diario.*

---

## 4. Resumen Análisis 📈 (Individual)

---

**DavidRafaelGdaniec:**

* **Contribución principal:** Refactorización de la arquitectura de navegación de la aplicación Android (Kotlin). La introducción de `NavGraphBuilder` y rutas dedicadas (`LocalesRoute`, `ProductsRoute`, `DashboardRoute`, `VentasRoute`) implica una mejora estructural significativa que facilita la escalabilidad del proyecto.
* **Seguimiento:** Dos entradas en el diario que coinciden con los dos días en que hizo commits (22/01 y 12/02). La entrada del 22/01 menciona "Creación de las tareas del Sprint 5", lo que es coherente con el inicio del sprint, aunque ese hecho no se refleja en los archivos comprometidos (solo se modificó el diario ese día, no el tablero de proyectos con tareas nuevas visibles).
* **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** En el tablero de GitHub Projects, el Sprint 5 únicamente contiene una tarea: "Tener una organización del tiempo más clara" (estado: *Retrospective*), asignada a todos los miembros. No existen tareas de desarrollo asignadas a David bajo Sprint 5. El trabajo de navegación realizado el 12/02 corresponde a tareas etiquetadas bajo Sprint 6 (`Crear las páginas principales en Kotlin`, `Creación correcta de los locales`). El sprint careció de planificación formal en el tablero.
* **Participación en el grupo de forma activa:** 🟡 — Presente y con aportación real, pero concentrada en el último día del sprint. La actividad del 22/01 es anecdótica (solo diario). Se esperaría mayor distribución del trabajo a lo largo del sprint.
* **Registro en Diario de Trabajo durante el Sprint:** 🟡 — Solo dos entradas en 24 días de sprint. Coinciden con commits, lo que es positivo para la trazabilidad, pero la cobertura semanal es muy escasa. Las reflexiones y problemas están prácticamente vacíos en las entradas de este período.
* **Participación en el Incremento:** 🟢 — El commit del 12/02 aporta un incremento técnico real y de calidad: refactorización de navegación con 11 archivos y +260 líneas. El uso de PR para integrar el trabajo en `main` es correcto.

---

**Joel Vives (SkYGi560):**

* **Contribución principal:** Implementación completa del módulo de Ventas en la aplicación WPF: pantalla de listado, diálogo de detalle, ViewModel, servicio e interfaz. Es el commit de mayor volumen del sprint (10 archivos, +564 líneas).
* **Seguimiento:** Solo una entrada en el diario durante todo el sprint, correspondiente al 12/02. No existe ninguna entrada entre el 15/12/2025 y el 12/02/2026 (aproximadamente 8 semanas sin registro). El salto temporal es muy llamativo.
* **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** En el tablero, el Sprint 5 carece de tareas de desarrollo asignadas a Joel (SkYGi560). Solo figura la tarea retrospectiva común. El módulo de Ventas creado en este sprint aparece posteriormente vinculado a Sprint 6 en el tablero (`Crear páginas de visualización de datos` etc.). No existe trazabilidad entre el trabajo comprometido y el tablero Scrum durante este sprint.
* **Participación en el grupo de forma activa:** 🟡 — Su única actividad se concentra en el último día del sprint (12/02). No hay evidencia de actividad, planificación ni seguimiento durante los días anteriores del sprint.
* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Solo 1 entrada en ~24 días de sprint. Ausencia completa de registro desde diciembre 2025 hasta el 12/02/2026. El diario no refleja el trabajo ni el proceso durante el sprint.
* **Participación en el Incremento:** 🟢 — A pesar de la concentración en un solo día, el incremento aportado es funcional y relevante: el módulo de Ventas completo es una funcionalidad nueva de alto valor para el proyecto PeluPOS.

---

**Francisco García (kennzzaki):**

* **Contribución principal:** Sin contribución en este sprint.
* **Seguimiento:** El diario tiene la última entrada en diciembre 2025. No hay ningún registro durante el periodo analizado del Sprint 5 (enero-febrero 2026).
* **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** Está asignado a la tarea retrospectiva del Sprint 5 ("Tener una organización del tiempo más clara"), pero sin tareas de desarrollo. En sprints anteriores estuvo asignado a tareas de Sprint 2. No existe evidencia de trabajo comprometido ni de interacción con el tablero durante el Sprint 5.
* **Participación en el grupo de forma activa:** 🔴 — Sin ningún commit ni actualización del diario durante todo el sprint. Ausencia total de actividad rastreable en el repositorio.
* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Diario sin actualizar desde el 15/12/2025. No hay entradas para enero ni febrero 2026.
* **Participación en el Incremento:** 🔴 — Sin contribución alguna al incremento del Sprint 5.

---

**Jesús Liñan (jesuslalm):**

* **Contribución principal:** Sin contribución en este sprint.
* **Seguimiento:** El diario se detiene en noviembre 2025. No hay ninguna entrada desde el 13/11/2025, incluyendo diciembre 2025, enero y febrero 2026.
* **Análisis de Tasks y/o UserStories asignadas durante el Sprint:** Al igual que el resto, está asignado como coasignado a la tarea retrospectiva del Sprint 5. No tiene tareas de desarrollo en el tablero bajo Sprint 5. La última tarea activa que aparece con su usuario es de Sprint 2. No hay ninguna evidencia de participación en el tablero durante los sprints 3, 4 ni 5.
* **Participación en el grupo de forma activa:** 🔴 — Sin commits desde finales de 2025. Sin actividad registrada en el repositorio durante todo el Sprint 5.
* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Diario abandonado desde noviembre 2025. Es el diario con menos actualizaciones del equipo.
* **Participación en el Incremento:** 🔴 — Sin ninguna contribución al incremento del Sprint 5.

---

## 5. Resumen Análisis 📈 (Por Grupo)

**LosDelFondopi2damiesbalmis — PeluPOS:**

* **Funcionalidad del incremento entregado:** 🟡 — Solo 2 de los 4 miembros han contribuido con trabajo de desarrollo (David y Joel), y ambos lo hicieron en el último día del sprint. Se crearon funcionalidades reales (módulo Ventas en WPF, refactorización de navegación en Kotlin), pero la mitad del equipo no tiene ningún commit. No hay tareas de desarrollo marcadas como *Done* en Sprint 5 en el tablero; el sprint estaba prácticamente vacío de planificación.
* **Realización de los eventos de Scrum, aplicando su filosofía:** 🟡 — Existe una tarjeta de retrospectiva ("Tener una organización del tiempo mas clara") en el tablero con estado *Retrospective*, lo que evidencia que se realizó al menos la retrospectiva. No se aprecian tarjetas de Sprint Review ni Sprint Planning formalizadas en el tablero para este sprint. El tablero carece de user stories o tareas asignadas al Sprint 5 con datos de desarrollo real.
* **Compromiso del equipo con el flujo de trabajo de Scrum a través de GitHub Projects:** 🔴 — El Sprint 5 tiene únicamente 1 tarea registrada (una tarea retrospectiva) y ninguna tarea de desarrollo asignada. El trabajo real realizado (Ventas en WPF y Navegación en Kotlin) no tiene reflejo en tarjetas del Sprint 5. Francisco y Jesús no tienen ninguna tarea asignada bajo su nombre en este sprint. El flujo de trabajo Scrum (backlog → sprint → doing → done) no se siguió correctamente.

---

## 6. Resumen Ejecutivo 📈

1. **Tendencias y Patrones:**
   * **2026-02-12 — Concentración de trabajo en el último día:** El patrón más llamativo del Sprint 5 es que toda la actividad de código ocurrió en el último día del sprint (12/02). Tanto David como Joel comprometieron su trabajo de forma simultánea el día de cierre o entrega. Este comportamiento es un indicador de procrastinación o de mala distribución del trabajo a lo largo del sprint, en lugar de un avance iterativo y continuo.
   * **Abandono progresivo del diario:** Los diarios de Jesús Liñan y Francisco García llevan abandonados 3 y 2 meses respectivamente. El de Joel tiene un salto de casi 2 meses. Solo David mantiene entradas mínimas coincidentes con sus commits.

2. **Calidad del Trabajo:**
   * El trabajo técnico de David y Joel es de buena factura: la refactorización de la navegación Android introduce una arquitectura más limpia y escalable, y el módulo de Ventas en WPF está completo con su separación en capas (View, ViewModel, Service, Interface). Sin embargo, ninguno de los dos usó ramas de feature con commits intermedios ni issues/PRs vinculados a tareas del Sprint 5.
   * La ausencia de test unitarios, revisiones cruzadas o comentarios en código sugiere que el equipo no aplica prácticas de calidad más allá de la estructura de carpetas.

3. **Áreas de Mejora:**
   * **Distribución del trabajo:** Evitar reservar toda la producción para el último día. Distribuir los commits a lo largo del sprint (al menos 1 commit por semana por miembro).
   * **Tablero Scrum:** Crear y asignar todas las tareas en `GitHub Projects` antes de iniciar el sprint. Cada miembro debe tener al menos una tarea en *Doing* y moverla a *Done* al completarla. Sin planificación visible, el Sprint no existe desde la perspectiva Scrum.
   * **Diario de trabajo:** Francisco y Jesús deben retomar el diario de manera urgente. La falta de registro durante meses hace imposible evaluar su trabajo.
   * **Participación de Francisco y Jesús:** Es preocupante la ausencia total de actividad de dos de los cuatro miembros durante un sprint completo. Se recomienda una conversación directa para identificar las causas y establecer compromisos concretos para el siguiente sprint.
   * **Retrospectiva como acción real:** La tarjeta "Tener una organización del tiempo más clara" debe traducirse en cambios concretos y medibles, no quedarse solo como título en el tablero.
