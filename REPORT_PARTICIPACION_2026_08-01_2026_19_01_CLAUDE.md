# 📊 Informe de Participación en el Proyecto
## Periodo: 08/01/2026 - 19/01/2026

---

## 📈 Resumen de Estadísticas Generales

| Métrica | Valor |
|---------|-------|
| **Total de usuarios activos** | 3 |
| **Periodo analizado** | 08/01/2026 - 19/01/2026 (12 días) |
| **Días con actividad** | 2 días (12/01 y 19/01) |
| **Total de commits** | 12 commits |
| **Commits efectivos (sin merges)** | 7 commits |
| **Archivos modificados únicos** | 52 archivos |

---

## 📋 Detalle de Commits por Fecha

| Nombre | Fecha | Archivos modificados | Resumen de lo realizado |
|--------|-------|---------------------|-------------------------|
| **David Rafael Gdaniec** | 2026-01-12 | Usuario.kt | Añadió enumeración de roles (enum) al modelo Usuario en el proyecto Kotlin para definir diferentes tipos de roles de usuario en la aplicación. |
| **David Rafael Gdaniec** | 2026-01-12 | build.gradle.kts<br>datamocks.kt<br>UsuarioDaoMock.kt<br>UsuarioRepositoryConverter.kt<br>LocalesScreen.kt<br>ModifyLocalScreen.kt<br>NewLocalScreen.kt<br>MainScreen.kt<br>SelectorEmpleados.kt<br>Navigation.kt<br>VentasScreen.kt<br>DavidRafael_Gdaniec.md | Implementación completa del módulo de gestión de locales en el proyecto Kotlin: creó las pantallas de listado, edición y creación de locales. Desarrolló el composable SelectorEmpleados para selección de empleados. Implementó la vista inicial de VentasScreen. Actualizó la navegación para incluir las nuevas pantallas de locales. Modificó el modelo de usuario para incluir roles. Actualizó los mocks de datos para reflejar los cambios en el modelo. También actualizó su diario de trabajo. |
| **Joel Vives** | 2026-01-12 | MainWindow.xaml.cs<br>Usuario.cs<br>Roles.cs<br>EmpleadoService.cs<br>EmpleadosStatsService.cs<br>IEmpleadoService.cs<br>IEmpleadoStatsService.cs<br>EmpleadoEditorViewModel.cs<br>EmpleadoFacturasViewModel.cs<br>EmpleadosViewModel.cs<br>EmpleadoDialog.xaml<br>EmpleadoDialog.xaml.cs<br>EmpleadoFacturasPage.xaml<br>EmpleadoFacturasPage.xaml.cs<br>EmpleadosPage.xaml<br>EmpleadosPage.xaml.cs | Implementación completa del módulo de empleados en el proyecto WPF: creó la página de gestión de empleados con todas sus funcionalidades CRUD (crear, leer, actualizar, eliminar). Desarrolló los servicios de empleados (EmpleadoService) y estadísticas (EmpleadosStatsService) con sus respectivas interfaces. Implementó tres ViewModels para gestionar la lógica de empleados, edición y facturas asociadas. Creó las vistas XAML para el diálogo de empleado, página de empleados y página de facturas de empleados. Añadió la enumeración de roles al modelo y actualizó el modelo Usuario. |
| **David Rafael Gdaniec** | 2026-01-19 | MainScreen.kt<br>Navigation.kt<br>AddVentaScreen.kt<br>DetallesVentaScreen.kt | Creó dos nuevas pantallas completas para el módulo de ventas: AddVentaScreen (para añadir nuevas ventas con formulario completo de datos) y DetallesVentaScreen (para visualizar los detalles de una venta específica). Actualizó la navegación para incluir estas nuevas pantallas y modificó el MainScreen para integrar la funcionalidad de ventas. |
| **David Rafael Gdaniec** | 2026-01-19 | MainScreen.kt<br>AddVentaScreen.kt<br>DetallesVentaScreen.kt<br>VentasScreen.kt | Realizó una refactorización de la estructura del proyecto moviendo las pantallas de ventas desde el paquete raíz a ui/ventas para mejorar la organización del código. Actualizó las importaciones y referencias en MainScreen. Optimizó el código eliminando líneas innecesarias. |
| **Francisco Javier García** | 2026-01-19 | .idea/.gitignore<br>.idea/caches/deviceStreaming.xml<br>.idea/deviceManager.xml<br>.idea/misc.xml<br>.idea/modules.xml<br>.idea/proyecto.iml<br>.idea/vcs.xml<br>PeluPos/.idea/deviceManager.xml<br>DetalleEmpleadoScreen.kt<br>AppNavigation.kt | Implementó la pantalla DetalleEmpleadoScreen en el proyecto Kotlin para mostrar información detallada de un empleado. Actualizó la navegación (AppNavigation.kt) para incluir la nueva pantalla. Añadió archivos de configuración del IDE (.idea) aunque la mayoría son archivos de configuración que no deberían estar en el repositorio. |
| **Francisco Javier García** | 2026-01-19 | Archivos de configuración .idea (compiler.xml, deploymentTargetSelector.xml, deviceManager.xml, gradle.xml, inspectionProfiles/Project_Default.xml, studiobot.xml)<br>migrations.xml<br>misc.xml | Realizó limpieza de archivos de configuración del IDE Android Studio, eliminando múltiples archivos innecesarios (.idea) que no deberían estar versionados. Añadió migrations.xml y actualizó misc.xml. Estos cambios no aportan funcionalidad al proyecto, solo modifican configuraciones locales del entorno de desarrollo. |

---

## 👥 Análisis de Contribuciones Individuales

| Usuario | Commits | Días activos | Contribución principal | Seguimiento | Discrepancias | Valoración global |
|---------|---------|--------------|------------------------|-------------|---------------|-------------------|
| **David Rafael Gdaniec** | 4 | 2/2 (100%) | Desarrollo completo del módulo de Locales y Ventas en Kotlin. Implementó pantallas de gestión de locales (listado, creación, edición), pantallas de ventas (añadir, detalles, listado), componente SelectorEmpleados reutilizable, y actualización del modelo de Usuario con roles. Total: ~1500 líneas de código productivo. | ✅ **Excelente** - El diario está actualizado al día. La entrada del 12/01/2026 coincide perfectamente con los commits realizados ese día. Refleja con precisión el trabajo realizado: creación de vistas de locales, composable SelectorEmpleados, roles en usuarios, y vista de ventas. | ✅ **Ninguna** - Existe total coherencia entre lo registrado en el diario y los commits realizados. El estudiante documenta sus avances de forma precisa y puntual. | 🟢 Participación sobresaliente. Lidera la actividad del proyecto con aportaciones técnicas significativas y constantes. Mantiene una excelente disciplina en la documentación de su trabajo. |
| **Joel Vives** | 1 | 1/2 (50%) | Desarrollo completo del módulo de Empleados en WPF. Implementó toda la arquitectura del módulo incluyendo servicios, interfaces, ViewModels (empleados, editor, facturas), vistas XAML completas con diálogos y páginas, y actualización del modelo con roles. Total: ~1000 líneas de código productivo. | ⚠️ **Aceptable con reservas** - El diario NO está actualizado para el periodo analizado. La última entrada es de diciembre 2025, sin registro de enero 2026. Aunque su trabajo en el commit del 12/01 es sustancial y de calidad, falta documentación en el diario. | ⚠️ **Discrepancia significativa** - No hay ninguna entrada en el diario para enero 2026, a pesar de haber realizado un commit importante el 12/01. Esto representa una falta de seguimiento y documentación del trabajo actual. | 🟡 Contribución técnica muy valiosa, pero debe mejorar el seguimiento documental. El trabajo realizado es de calidad y volumen considerable, sin embargo, el abandono del diario es preocupante y resta profesionalidad a su participación. |
| **Francisco Javier García** | 2 | 1/2 (50%) | Implementación de la pantalla DetalleEmpleadoScreen en Kotlin para visualización de información de empleados. Realización de limpieza de archivos de configuración del IDE. Total: ~300 líneas de código productivo (el resto son archivos de configuración que no deberían versionarse). | 🔴 **Deficiente** - El diario está completamente desactualizado. No tiene sección de enero 2026, y la última entrada es de diciembre 2025. No hay registro de su trabajo del 19/01. Además, el diario tiene muy pocas entradas en general comparado con sus compañeros. | 🔴 **Discrepancias graves** - No existe ningún registro en el diario de su trabajo de enero. Además, una parte significativa de sus commits (commit de "errores") incluye archivos de configuración del IDE que no deberían estar en el repositorio, lo que evidencia falta de conocimiento de buenas prácticas Git. | 🔴 Participación insuficiente. Aunque realizó una implementación funcional (DetalleEmpleadoScreen), su contribución es menor comparada con sus compañeros. La falta total de actualización del diario y la inclusión de archivos inapropiados en el repositorio demuestran falta de seguimiento y conocimiento de buenas prácticas. Necesita mejorar significativamente su compromiso con el proyecto. |

---

## 📊 Resumen Ejecutivo 📈

### 1. Tendencias y Patrones 📅

| Fecha | Título | Descripción |
|-------|--------|-------------|
| **12/01/2026** | **Sprint de Desarrollo Intensivo** | Concentración significativa de actividad con tres desarrolladores trabajando simultáneamente. David y Joel realizaron implementaciones paralelas de gran envergadura en ambas plataformas (Kotlin y WPF), demostrando buena coordinación y división de tareas. |
| **19/01/2026** | **Fase de Refinamiento y Nuevas Funcionalidades** | David continuó desarrollando nuevas funcionalidades (pantallas de ventas), mientras Francisco realizó su primera contribución del periodo. La actividad se concentró principalmente en Kotlin. |
| **Patrón General** | **Trabajo Concentrado en Días Específicos** | Durante el periodo de 12 días, solo hubo actividad en 2 días (16.6% del tiempo). Esto sugiere un patrón de trabajo concentrado antes de entregas o reuniones, en lugar de un desarrollo continuo y distribuido. |

### 2. Distribución del Trabajo 📊

**Usuarios Más Activos:**
- **David Rafael Gdaniec** (57% de commits efectivos): Claramente el desarrollador más activo y consistente. Trabaja en ambos días del periodo, con contribuciones sustanciales que incluyen múltiples funcionalidades completas. Su trabajo abarca desde modelos hasta vistas completas con navegación integrada.

- **Joel Vives** (14% de commits efectivos): Una sola contribución, pero de gran calidad y volumen. Implementó un módulo completo con arquitectura bien estructurada (servicios, interfaces, ViewModels, vistas). Su trabajo, aunque puntual, es de alta calidad técnica.

**Usuarios Menos Activos:**
- **Francisco Javier García** (29% de commits efectivos): Aunque tiene 2 commits, uno de ellos es principalmente limpieza de configuración del IDE. Su contribución funcional real (DetalleEmpleadoScreen) es válida pero limitada en comparación con sus compañeros. Es el miembro que menos contribuye en términos de funcionalidad productiva.

**Análisis de Equidad:**
La distribución del trabajo es **desbalanceada**. David asume claramente el rol de líder técnico llevando la mayor carga de desarrollo. Existe una brecha considerable entre el desarrollador más activo y el menos activo. Se recomienda una distribución más equitativa de tareas para el próximo periodo.

### 3. Calidad del Trabajo 🎯

**Buenas Prácticas Observadas:**
- ✅ **Arquitectura bien estructurada**: Joel demuestra excelente conocimiento de patrones MVVM en WPF con separación clara de responsabilidades.
- ✅ **Código organizado**: David mantiene buena estructura de paquetes y realiza refactorizaciones para mejorar la organización (movimiento de pantallas ventas a ui/).
- ✅ **Commits descriptivos**: La mayoría de los commits tienen mensajes que explican claramente qué se realizó (especialmente David).
- ✅ **Desarrollo de componentes reutilizables**: Creación de SelectorEmpleados como composable reutilizable.
- ✅ **Trabajo paralelo coordinado**: David y Joel trabajaron el mismo día en módulos diferentes sin conflictos, demostrando buena comunicación.

**Áreas de Mejora Detectadas:**
- ⚠️ **Documentación de seguimiento**: Joel y Francisco no actualizan sus diarios, lo cual dificulta el seguimiento del proyecto y la evaluación continua.
- ⚠️ **Gestión de archivos Git**: Francisco incluye archivos de configuración del IDE (.idea/) que no deberían versionarse. Falta configuración adecuada de .gitignore.
- ⚠️ **Mensajes de commit poco profesionales**: Francisco utiliza mensajes como "mirama" y "errores" que no describen adecuadamente los cambios.
- ⚠️ **Commits de configuración innecesarios**: El commit "errores" de Francisco principalmente elimina y añade archivos de configuración del IDE sin aportar valor funcional.
- ⚠️ **Distribución temporal**: Solo 2 días de actividad en 12 días sugiere falta de trabajo continuo o sesiones de desarrollo muy espaciadas.

**Calificación Global de Calidad:** 7/10
- El código funcional es de buena calidad técnica
- Falta disciplina en documentación y buenas prácticas Git
- Necesita mejora en la consistencia del trabajo

### 4. Áreas de Mejora 🎯

#### 4.1 Mejoras en Seguimiento y Documentación
- **Prioridad ALTA**: Todos los miembros deben actualizar sus diarios de trabajo **el mismo día** que realizan commits. Joel y Francisco deben ponerse al día inmediatamente.
- **Acción requerida**: Establecer una regla de equipo: "No commit sin entrada en el diario". Esto garantiza trazabilidad y facilita las evaluaciones.
- **Sugerencia**: Incluir en las reuniones de equipo una revisión rápida del estado de los diarios personales.

#### 4.2 Buenas Prácticas Git
- **Prioridad ALTA**: Francisco debe aprender sobre .gitignore y eliminar los archivos .idea/ del repositorio mediante un commit de limpieza.
- **Acción requerida**: Crear/actualizar el archivo .gitignore para excluir:
  ```
  .idea/
  *.iml
  .gradle/
  build/
  local.properties
  ```
- **Formación necesaria**: Sesión breve sobre qué archivos deben y no deben versionarse en proyectos Kotlin/Android.

#### 4.3 Mensajes de Commit Profesionales
- **Prioridad MEDIA**: Francisco debe mejorar la calidad de sus mensajes de commit. Deben seguir el formato: `[Tipo] Descripción breve clara`
- **Ejemplos buenos**:
  - `[Feature] Implementa pantalla de detalle de empleado`
  - `[Fix] Corrige error de navegación en DetalleEmpleadoScreen`
  - `[Refactor] Limpia archivos de configuración del IDE`
- **Evitar**: Mensajes vagos como "mirama", "errores", "cambios", etc.

#### 4.4 Distribución del Trabajo
- **Prioridad ALTA**: Necesario equilibrar mejor la carga de trabajo. David no debe ser el único desarrollador principal.
- **Acción requerida**: En el siguiente Sprint Planning:
  - Asignar tareas de complejidad similar a todos los miembros
  - Francisco debe asumir responsabilidad de al menos un módulo completo
  - Joel debe mantener su ritmo pero con más frecuencia
- **Objetivo**: Alcanzar una distribución más cercana a 33%-33%-33% en lugar del actual 57%-14%-29%.

#### 4.5 Frecuencia de Trabajo
- **Prioridad MEDIA**: Fomentar trabajo más distribuido en lugar de concentrado en días específicos.
- **Sugerencia**: Establecer pequeños objetivos diarios o cada 2-3 días para mantener ritmo constante.
- **Beneficios**: Menos riesgo de bloqueos, mejor integración continua, feedback más temprano.

#### 4.6 Jesús Manuel Liñán Almagro
- **Situación**: No aparece en los commits del periodo analizado (08/01 - 19/01).
- **Prioridad CRÍTICA**: Investigar las razones de su ausencia total en el desarrollo.
- **Acción inmediata**: Reunión con Jesús para:
  - Entender si hay problemas personales o bloqueos técnicos
  - Asignar tareas específicas y factibles para el próximo sprint
  - Establecer seguimiento semanal de su progreso
- **Riesgo**: Si esta tendencia continúa, el proyecto puede verse seriamente afectado y su evaluación será insuficiente.

---

## 📌 Conclusiones Finales

El proyecto muestra **avances técnicos significativos** con implementación de dos módulos completos (Locales/Ventas en Kotlin y Empleados en WPF), sin embargo, presenta **serios desafíos en cuanto a equidad de participación y seguimiento documental**.

**Puntos Fuertes:**
- Calidad técnica del código producido
- Arquitectura bien diseñada
- Trabajo paralelo sin conflictos

**Puntos Críticos:**
- Desbalance marcado en la carga de trabajo
- Falta de actualización de diarios (2 de 3 usuarios sin actualizar)
- Ausencia total de un miembro del equipo (Jesús)
- Malas prácticas Git en un integrante

**Recomendación General:**
Es necesario intervenir **de forma inmediata** para corregir los hábitos de trabajo del equipo. Sin cambios, el proyecto corre riesgo de depender excesivamente de un solo desarrollador, lo cual es insostenible a largo plazo. Se recomienda una reunión de retrospectiva enfocada en compromisos concretos y medibles para el próximo periodo.

---

*Informe generado el: 21 de enero de 2026*  
*Periodo analizado: 08/01/2026 - 19/01/2026*  
*Herramienta: Git Log Analysis*
