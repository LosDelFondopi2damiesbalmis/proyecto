# 📊 Informe de Participación en el Proyecto
## Periodo: 01/12/2025 - 22/12/2025

---

## 📈 Estadísticas Generales del Periodo

| Métrica | Valor |
|---------|-------|
| **Total de usuarios activos** | 3 |
| **Periodo analizado** | 01/12/2025 - 22/12/2025 (22 días) |
| **Días con actividad** | 8 días (01, 04, 15, 18, 19 de diciembre) |
| **Total de commits** | 15 commits (sin merges) |
| **Archivos modificados únicos** | 68 archivos |
| **Plataformas de desarrollo** | WPF (C#) y Android (Kotlin) |

---

## 📋 Tabla de Commits Detallados

| Nombre | Fecha | Archivos modificados | Resumen de lo que ha realizado |
|--------|-------|---------------------|--------------------------------|
| **Joel Vives** | 2025-12-19 | `docs/diarios/Joel_Vives.md` | Actualización del diario personal con las actividades realizadas hasta el 18/12 |
| **Francisco García** | 2025-12-18 | `MainScreen.kt`, `Sidebar.kt`, `NewProductScreen.kt`, `ProductsScreen.kt`, `Francisco_Garcia.md` | Implementación de la pantalla de nuevo producto en Kotlin con 233 líneas, mejoras en MainScreen (156 líneas) y actualización del diario |
| **Joel Vives** | 2025-12-18 | 19 archivos del proyecto WPF | Creación completa del módulo de productos: vistas (ProductosPage, ProductoDialog, StockDialog, FacturasProducto), ViewModels, y servicios. Ajustes gráficos en otras páginas |
| **Joel Vives** | 2025-12-15 | 24 archivos del proyecto WPF | Implementación del módulo de servicios y locales: creación de interfaces de servicio, ViewModels (ServiciosViewModel 117 líneas, LocalesViewModel 52 líneas), y todas las vistas necesarias. Reorganización de carpetas |
| **Francisco García** | 2025-12-15 | `MainScreen.kt`, `Sidebar.kt`, `Navigation.kt`, `ProductsScreen.kt` | Desarrollo de la pantalla de productos en Kotlin (486 líneas), mejora del sidebar (241 líneas) y navegación (44 líneas) |
| **Francisco García** | 2025-12-15 | `MainScreen.kt`, `Navigation.kt` | Corrección de bugs en MainScreen (6 líneas) y Navigation (2 líneas) |
| **David Gdaniec** | 2025-12-15 | `MainScreen.kt`, `ClienteScreen.kt`, `Sidebar.kt` | Separación del sidebar del MainScreen en componente independiente (78 líneas), mejoras en ClienteScreen |
| **Francisco García** | 2025-12-15 | `Project_Default.xml` | Configuración de inspección del proyecto en IntelliJ (61 líneas) |
| **Joel Vives** | 2025-12-04 | 8 archivos del proyecto WPF | Creación del módulo de clientes: implementación de ClienteService (26 líneas), IClienteService (11 líneas), ClientesViewModel (85 líneas), vistas de clientes y conversor StringNullOrEmptyToVisibility |
| **David Gdaniec** | 2025-12-04 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario personal con las tareas realizadas |
| **David Gdaniec** | 2025-12-04 | `MainScreen.kt` | Modificación de la barra derecha del MainScreen (129 líneas añadidas, 58 eliminadas) |
| **Joel Vives** | 2025-12-04 | `VSWorkspaceState.json`, `DocumentLayout.json`, `Colores.cs`, `ClientesPage.xaml`, `Joel_Vives.md` | Eliminación de archivo Colores.cs (91 líneas), creación de ClientesPage.xaml (156 líneas), actualización del diario de diciembre |
| **David Gdaniec** | 2025-12-01 | 9 archivos Kotlin | Creación de la vista principal en Kotlin: MainScreen (159 líneas), componentes reutilizables (DashboardCard, DataRow, NavButton, NavSeparator) y personalización de tema |
| **Joel Vives** | 2025-12-01 | 21 archivos del proyecto WPF | Inicialización completa del proyecto WPF PeluPOS: estructura de carpetas, modelos de entidades, MainWindow (129 líneas), DashboardPage (255 líneas), recursos de colores (Colores.xaml, Colores.cs), MockData (185 líneas) |
| **David Gdaniec** | 2025-12-01 | `Project_Default.xml` | Configuración inicial del perfil de inspección del proyecto (50 líneas) |

---

## 👥 Análisis de Contribuciones Individualizadas

| Nombre | Commits | Días activos | Contribución principal | Seguimiento | Discrepancias | Valoración global |
|--------|---------|--------------|----------------------|-------------|---------------|-------------------|
| **Joel Vives** | 5 commits efectivos (1 diario) | 4 días / 8 días máximos (50%) | Implementación completa del proyecto WPF con todos los módulos CRUD: Clientes, Servicios, Locales y Productos. Creación de arquitectura MVVM con ViewModels, Services e interfaces. Desarrollo de vistas XAML complejas con funcionalidad completa. | **Bueno** - Su diario está actualizado hasta el 15/12 con buen nivel de detalle. Las fechas de commits (01, 04, 15, 18/12) coinciden razonablemente con las entradas del diario (01, 04, 08, 11, 15/12). Mantiene actualización regular y coherente. | **Leve discrepancia**: El diario menciona trabajo los días 08/12 (Servicios) y 11/12 (Locales), pero el commit que incluye ambos módulos es del 15/12. Probablemente trabajó localmente esos días y subió todo junto el 15/12. También hay commit del 18/12 con productos pero diario solo hasta 15/12. | 🟢 **Excelente participación**. Es el miembro más productivo con commits de alta calidad y complejidad técnica. Implementó 4 módulos completos del sistema con arquitectura profesional. Su código es estructurado y bien organizado. |
| **Francisco García** | 3 commits efectivos (1 diario) | 2 días (15 y 18/12) / 8 días máximos (25%) | Desarrollo de interfaces en Android/Kotlin: pantalla de productos (ProductsScreen con 486 líneas), nueva pantalla producto (233 líneas), mejoras significativas en MainScreen y Sidebar. Implementación de sistema de navegación. | **Deficiente** - Su diario solo tiene una entrada para todo diciembre (15/12) con descripción muy breve: "Kotlin vistas 60 min". No refleja la cantidad real de trabajo realizado ni el esfuerzo invertido. | **Discrepancia importante**: El diario solo menciona 1 hora de trabajo el 15/12, pero los commits muestran trabajo sustancial ese día Y el 18/12 (no documentado). Además, los mensajes de commit son poco descriptivos ("update", "mirama", "fix" x2), dificultando la comprensión de cambios. | 🟡 **Participación media-baja**. Aunque su contribución técnica es relevante (más de 700 líneas de código en Kotlin), tiene serios problemas de documentación. Su diario está muy desactualizado y sus commits mal descritos. Necesita mejorar hábitos de documentación y comunicación. |
| **David Gdaniec** | 4 commits efectivos (1 diario) | 3 días (01, 04, 15/12) / 8 días máximos (37.5%) | Desarrollo de componentes Kotlin: creación de vista principal MainScreen (159 líneas), separación y desarrollo del Sidebar como componente independiente (78 líneas), componentes reutilizables (DashboardCard, DataRow, NavButton). Modificaciones en barra lateral. | **Regular** - Su diario está actualizado hasta el 04/12 con descripciones básicas. Las fechas coinciden: 01/12 "Creación de la vista" y 04/12 "Arreglando vistas para el main". Falta actualizar actividad del 15/12. | **Discrepancia moderada**: No documenta en el diario el trabajo realizado el 15/12 (Separación del sidebar), que es una contribución importante. El diario se queda corto frente al trabajo real efectuado. | 🟡 **Participación regular-media**. Contribución técnica sólida en componentes de UI reutilizables y arquitectura de vistas. Sin embargo, su documentación es incompleta, no refleja todo su trabajo de diciembre. Necesita mejorar consistencia en el seguimiento del diario. |
| **Jesús Liñán** | 0 commits | 0 días / 8 días máximos (0%) | Sin contribuciones en el código durante este periodo. | **Muy deficiente** - Su diario termina en noviembre sin ninguna entrada para diciembre. Completamente desactualizado. | **Discrepancia crítica**: El diario no refleja ninguna actividad en diciembre y tampoco hay commits. No hay evidencia de participación en el proyecto durante este periodo analizado. | 🔴 **Participación nula**. No existe evidencia de trabajo ni en commits ni en documentación del diario para el periodo de diciembre. Situación crítica que requiere intervención inmediata. |

---

## 📈 Resumen Ejecutivo

### 1. 🔍 Tendencias y Patrones

| Fecha | Tendencia | Descripción |
|-------|-----------|-------------|
| **01/12/2025** | 🚀 **Inicio Sprint Diciembre** | Arranque fuerte del periodo con inicialización de ambos proyectos (WPF y Kotlin). Joel crea estructura completa WPF, David inicia vistas Kotlin. Gran actividad fundacional. |
| **04/12/2025** | 📊 **Desarrollo Paralelo** | Trabajo simultáneo en ambas plataformas: Joel desarrolla módulo clientes en WPF, David ajusta MainScreen en Kotlin. Coordinación efectiva entre plataformas. |
| **15/12/2025** | 🎯 **Día Pico de Actividad** | Día más productivo del periodo con 5 commits de los 3 usuarios activos. Francisco desarrolla pantalla productos Kotlin, David separa Sidebar, Joel implementa servicios y locales WPF. Gran concentración de esfuerzo. |
| **18-19/12/2025** | ⚡ **Sprint Final** | Francisco y Joel cierran funcionalidades: productos en ambas plataformas. Francisco completa NewProductScreen Kotlin, Joel finaliza módulo productos WPF con todas sus vistas. |
| **General** | 📉 **Patrón de Trabajo Irregular** | Actividad concentrada en días específicos (01, 04, 15, 18, 19) con largos periodos sin commits. Sugiere trabajo local acumulado o falta de commits incrementales. Solo 8 días activos de 22 posibles (36% del periodo). |

### 2. ⚖️ Distribución del Trabajo

**Distribución de Commits:**
- **Joel Vives**: 6 commits (40% del total, incluyendo diario)
- **David Gdaniec**: 5 commits (33% del total, incluyendo diario)
- **Francisco García**: 4 commits (27% del total, incluyendo diario)
- **Jesús Liñán**: 0 commits (0%)

**Commits Efectivos (sin diarios):**
- **Joel Vives**: 5 commits efectivos (38% del trabajo técnico)
- **David Gdaniec**: 4 commits efectivos (31% del trabajo técnico)
- **Francisco García**: 3 commits efectivos (23% del trabajo técnico)
- **Jesús Liñán**: 0 commits (0%)

**Análisis de Actividad:**
- **Usuario más activo**: Joel Vives con 4 días de actividad (50% de los días activos), implementando 4 módulos completos CRUD del sistema WPF.
- **Usuarios medianamente activos**: David Gdaniec (3 días, 37.5%) y Francisco García (2 días, 25%) trabajando principalmente en la plataforma Kotlin.
- **Usuario inactivo**: Jesús Liñán sin ninguna participación durante el periodo.

**Distribución de Líneas de Código:**
- **Joel Vives**: ~1,900 líneas añadidas (archivos WPF: ViewModels, Services, Views XAML)
- **Francisco García**: ~1,100 líneas añadidas (archivos Kotlin: Screens, componentes UI)
- **David Gdaniec**: ~450 líneas añadidas (archivos Kotlin: componentes, MainScreen, Sidebar)

**Observación crítica**: La carga de trabajo está muy desbalanceada. Joel está llevando el peso del desarrollo WPF prácticamente solo, mientras que el equipo Kotlin (Francisco y David) tienen una participación más moderada. Jesús representa un 25% del equipo sin contribución visible, lo que sobrecarga al resto.

### 3. 🎓 Calidad del Trabajo

#### ✅ **Aspectos Positivos:**

1. **Arquitectura Profesional (Joel Vives)**: 
   - Implementación correcta del patrón MVVM en WPF
   - Separación adecuada: Models, Views, ViewModels, Services
   - Uso de interfaces (IClienteService, ILocalService, IServicioService, IProductoService) que facilitan testing y mantenimiento
   - Creación de recursos reutilizables (Converters, Colors, MockData)

2. **Componentización en Kotlin (David Gdaniec)**:
   - Separación del Sidebar como componente independiente reutilizable
   - Creación de componentes UI modulares (DashboardCard, DataRow, NavButton, NavSeparator)
   - Enfoque en reutilización y mantenibilidad del código

3. **Desarrollo de Funcionalidades Completas (Joel Vives)**:
   - Cada módulo incluye todas las capas: Service, ViewModel, View
   - Implementación de diálogos modales (ProductoDialog, StockDialog, ServicioDialog, NuevoLocalDialog)
   - Páginas de relaciones (FacturasProducto, FacturasServicio)

4. **Avance en Paralelo**:
   - Desarrollo simultáneo en dos plataformas diferentes (WPF y Kotlin)
   - Mantenimiento de consistencia en modelos de datos entre plataformas

#### ⚠️ **Áreas Problemáticas:**

1. **Mensajes de Commit Deficientes (Francisco García)**:
   - Commits con mensajes poco descriptivos: "update", "mirama", "fix", "fix"
   - Dificulta la comprensión del historial y la trazabilidad de cambios
   - No sigue buenas prácticas de commits semánticos

2. **Documentación Inconsistente**:
   - Francisco: diario extremadamente resumido (solo 1 entrada para diciembre de 1 hora)
   - David: diario desactualizado (no refleja trabajo del 15/12)
   - Jesús: diario abandonado (sin entradas de diciembre)
   - Solo Joel mantiene documentación actualizada y coherente

3. **Commits No Incrementales**:
   - Commits muy grandes con múltiples cambios agrupados
   - Ejemplo: Joel agrupa servicios y locales en un solo commit del 15/12
   - Dificulta el code review y la identificación de problemas

4. **Falta de Commits Diarios**:
   - Solo 8 días con actividad de 22 posibles (36%)
   - Sugiere trabajo local sin pushes regulares o periodos de inactividad
   - Riesgo de pérdida de trabajo y problemas de integración

5. **Desbalance Extremo en Participación**:
   - Un miembro del equipo (25%) sin contribución alguna
   - Joel cargando con el 70% del peso real del desarrollo efectivo
   - Riesgo de burnout y cuello de botella en el proyecto

### 4. 🔧 Áreas de Mejora

#### **Para el Equipo en General:**

1. **📅 Commits Diarios y Pequeños**:
   - Implementar la práctica de commits incrementales al final de cada día
   - Subir cambios aunque no estén 100% terminados (usar branches feature)
   - Objetivo: pasar de 8 días activos a mínimo 15 días en próximo periodo

2. **📝 Mensajes de Commit Descriptivos**:
   - Adoptar convención de commits semánticos: `tipo(ámbito): descripción`
   - Ejemplos: `feat(productos): añadir formulario de nuevo producto`, `fix(sidebar): corregir navegación`
   - Obligatorio incluir el "qué" y el "por qué" en mensajes importantes

3. **📖 Mantener Diarios Actualizados**:
   - Actualizar el diario el mismo día del commit (no retrospectivamente)
   - Incluir tiempo invertido, problemas encontrados y soluciones
   - Establecer revisión semanal del diario como tarea del Sprint

4. **🔄 Code Review Formal**:
   - Implementar Pull Requests obligatorios para cambios importantes
   - Al menos un compañero debe revisar antes de mergear
   - Usar branches feature por funcionalidad

#### **Para Francisco García:**

1. **🎯 Mejorar Calidad de Documentación**:
   - Actualizar diario con TODAS las sesiones de trabajo, no solo una
   - Dedicar 5-10 minutos al final de cada sesión para documentar
   - Incluir detalles técnicos de lo implementado

2. **💬 Mensajes de Commit Profesionales**:
   - NUNCA usar "fix", "update", "mirama" como mensaje completo
   - Describir QUÉ se arregló, QUÉ se actualizó
   - Ejemplo correcto: "feat(productos): implementar pantalla listado con búsqueda y filtros"

#### **Para David Gdaniec:**

1. **📊 Completar Documentación de Diciembre**:
   - Actualizar diario con las actividades del 15/12 (separación sidebar)
   - Mantener hábito de documentar inmediatamente después del trabajo
   - Ser más descriptivo en las reflexiones/aprendizaje

2. **📈 Aumentar Frecuencia de Commits**:
   - Dividir cambios grandes en commits más pequeños y específicos
   - Ejemplo: en vez de un commit con MainScreen + ClienteScreen + Sidebar, hacer 3 commits independientes

#### **Para Jesús Liñán:**

1. **🚨 URGENTE - Reincorporación al Proyecto**:
   - **Situación crítica**: Sin actividad en 22 días
   - Reunión inmediata con el equipo para entender las causas de inactividad
   - Establecer plan de recuperación con tareas específicas y plazos
   - Considerar reasignación de responsabilidades si es necesario

2. **📝 Retomar Documentación**:
   - Actualizar diario explicando situación de diciembre
   - Comprometerse con entradas diarias a partir de ahora
   - Transparencia sobre disponibilidad y capacidad de contribución

#### **Para Joel Vives:**

1. **⚡ Gestión de Carga de Trabajo**:
   - Aunque su trabajo es excelente, está llevando demasiada carga solo
   - Debe DELEGAR más tareas y empoderar al resto del equipo
   - Riesgo de burnout y convertirse en cuello de botella

2. **👥 Mentoría Activa**:
   - Dedicar tiempo a enseñar/guiar a compañeros menos activos
   - Pair programming con Francisco o David para nivelar conocimientos
   - Realizar mini code reviews para elevar calidad general del equipo

#### **Para el Proyecto:**

1. **🤝 Reuniones de Sincronización**:
   - Daily standups de 10 minutos (aunque sea de forma asíncrona)
   - Sprint retrospective formal al final del periodo
   - Identificar y solucionar bloqueos rápidamente

2. **📊 Métricas de Seguimiento**:
   - Dashboard con estadísticas de commits por usuario
   - Visualización de días activos vs. objetivo
   - Alertas tempranas de inactividad (>3 días sin commits)

3. **🎯 Definición de "Done"**:
   - Código implementado + Commit con mensaje descriptivo + Diario actualizado = Tarea completada
   - No considerar una tarea terminada hasta cumplir los 3 requisitos

4. **⚖️ Redistribución Equitativa**:
   - En el próximo Sprint Planning, asegurar que todos tengan carga similar
   - Asignar tareas según capacidad pero empujando a todos a contribuir
   - Establecer mínimo de commits/semana por persona (ej: 3 commits mínimo)

---

## 📌 Conclusión

El periodo de diciembre muestra un **progreso técnico significativo** especialmente en la plataforma WPF gracias al esfuerzo destacado de Joel Vives, y un avance moderado en Kotlin por parte de Francisco García y David Gdaniec. Sin embargo, existen **problemas serios de distribución del trabajo**, con un miembro del equipo completamente inactivo (Jesús Liñán) y otros con participación irregular.

La **calidad técnica** del código producido es generalmente buena, especialmente la arquitectura MVVM implementada, pero la **calidad de las prácticas de desarrollo** necesita mejoras urgentes: commits mal descritos, diarios desactualizados, y falta de trabajo incremental regular.

**Recomendación principal**: Es imperativo abordar la situación de participación de Jesús Liñán y establecer prácticas de desarrollo más rigurosas para todo el equipo antes del próximo sprint. Sin intervención, el proyecto corre riesgo de retrasos por sobrecarga de algunos miembros y falta de contribución de otros.

---

**Informe generado el 22 de diciembre de 2025**  
**Evaluador**: Sistema automatizado de análisis de repositorio Git
