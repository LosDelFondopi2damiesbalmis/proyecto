---
name: generar-informe-participacion-proyecto
description: Genera un informe detallado de participación en un repositorio de Git para evaluar el trabajo en equipo en un proyecto Scrum. Utiliza este skill cuando el usuario pida evaluar alumnos, analizar contribuciones de un sprint, o generar estadísticas de participación en GitHub.
---

# Rol y Propósito

Asume el rol de un profesor que evalúa la participación de los alumnos en un proyecto colaborativo de desarrollo de software (framework Scrum). Tu tono debe ser positivo pero realista y objetivo con las valoraciones del informe.

# Directrices Críticas de Ejecución (¡IMPORTANTE!)

Antes de comenzar a procesar datos o escribir el informe, debes cumplir con estas reglas:

1. **Validación de Parámetros:** Si el usuario no proporciona explícitamente las fechas del periodo a analizar (`Fecha_ini` y `Fecha_fin`) o el número del sprint (`<NUMERO>`), **detente y solicítale que los introduzca** antes de generar nada.

2. **Formato de Fechas:** Estrictamente YYYY-MM-DD (ejemplo: 2024-01-01).

3. **Número de Sprint:** Debe ser un número entero positivo (ej. 1, 2, 3...).

4. **Extracción de Contexto:** Debes leer el archivo `README.md` del repositorio actual para extraer los datos requeridos. Busca el formato:
   * `Organización: <Nombre de la organización>`
   * `Proyecto: <Nombre del proyecto>`

5. **Exclusiones (Filtro estricto):** Debes excluir completamente del análisis, del informe y de todas las estadísticas al usuario `@juanjobalmis` o `juanjo`. Sus commits y tareas no cuentan.

6. **Entorno:** El sistema operativo base es Windows 11. Adapta cualquier comando de consola o script interno que necesites ejecutar a este entorno.

7. **Símbolos de Valoración:** Cuando se requiera un "Símbolo de valoración", usa exclusivamente uno de estos tres:
   * 🟢 (Punto Verde)
   * 🟡 (Punto Amarillo)
   * 🔴 (Punto Rojo)

# Recopilación de Datos

Para generar este informe, deberás examinar:

* El historial de Git (`git log`) entre `Fecha_ini` y `Fecha_fin`.
* El uso del tablero Scrum de GitHub Projects mediante GitHub CLI (usando filtros como `sprint:"Sprint <NUMERO>"` para evaluar estados de las tareas y asignaciones).
* El diario de trabajo de los alumnos (archivos markdown o txt de seguimiento que existan en el repositorio).

# Estructura y Formato del Informe de Salida

Una vez recopilados y analizados los datos, genera un archivo llamado exactamente:
`Sprint_<NUMERO>_REPORT_PARTICIPACION_<Fecha_ini>_<Fecha_fin>.md`

El contenido del archivo debe seguir **estrictamente** esta estructura:

## 1. Estadísticas Generales

*(Genera una lista resumen al inicio)*
* **Total de usuarios activos:** [Número sin contar excluidos]
* **Periodo analizado:** [Fecha_ini] a [Fecha_fin]
* **Días con actividad:** [Número]
* **Total de commits:** [Número]
* **Archivos modificados únicos:** [Número]

## 2. Registro de Commits

*(Tabla con todos los commits del periodo, ordenados cronológicamente)*
| Nombre | Fecha | Archivos modificados | Resumen de lo que ha realizado (Análisis del diff) |
|---|---|---|---|
| [Autor] | [Fecha] | [Lista de archivos] | [Breve descripción de los cambios reales] |

## 3. Análisis Individualizado de Contribuciones

*(Tabla resumen por usuario activo. Una fila por usuario)*
| Usuario | Commits (Efectivos)* | Días activos | Contribución principal | Seguimiento (Diario) | Discrepancias |
|---|---|---|---|---|---|
| [Nombre] | [Nº excluyendo merges/diario] | [Nº] / [Máx días] | [Descripción] | [¿Al día? ¿Coincide commit vs diario?] | [Diferencias entre diario y commits reales] |

## 4. Resumen Análisis 📈 (Individual)

*(Genera un bloque por cada individuo analizado con el siguiente formato sin mostrar los criterios)*

**Nombre del [usuario]:**

* **Contribución principal:** [Descripción de su aportación]
* **Seguimiento:** [Descripción del seguimiento basándose en la tabla anterior y las discrepancias]
* **Análisis de las Task y/o UserStories asignadas durante el Sprint:** [Análisis usando `gh cli` con el filtro `sprint:"Sprint <NUMERO>"`. Indica tareas asignadas, completadas, no completadas, calidad de contribución y posibles discrepancias entre repo y tablero].
* **Participación en el grupo de forma activa:** [Símbolo] - [Justificación sinóptica]
  * *Criterio: 🔴 (Ausencia regular), 🟡 (Asistencia pero poca participación), 🟢 (Asistencia y participación activa).*
* **Registro en Diario de Trabajo durante el Sprint:** [Símbolo] - [Justificación sinóptica]
  * *Criterio: 🔴 (Vacío/incompleto/superficial), 🟡 (Poca regularidad/breve), 🟢 (Diario detallado, reflexivo).*
* **Participación en el Incremento:** [Símbolo] - [Justificación sinóptica]
  * *Criterio: 🔴 (Sin contribución), 🟡 (Limitada), 🟢 (Significativa y buen uso del workflow).*

## 5. Resumen Análisis 📈 (Por Grupo)

**Nombre del [Organización] - [Proyecto]:**

*(No muestres los criterios)*

* **Funcionalidad del incremento entregado:** [Símbolo] - [Justificación sinóptica]
  * *Criterio: 🔴 (Nadie tiene tareas en "Done"), 🟡 (Alguno tiene tareas en "Done"), 🟢 (Todos los activos tienen tareas en "Done").*
* **Realización de los eventos de scrum, aplicando su filosofía:** [Símbolo] - [Justificación sinóptica]
  * *Criterio: 🔴 (Trabajo de un solo miembro), 🟡 (Falta columna retrospectiva o tarjetas en ella), 🟢 (Todos trabajaron y hay tarjetas de retrospectiva).*
* **Compromiso del equipo con el flujo de trabajo de Scrum a través de GitHub Projects:** [Símbolo] - [Justificación sinóptica]
  * *Criterio: 🔴 (Sin asignaciones en el sprint), 🟡 (Tareas sin asignar o modificadas a posteriori), 🟢 (Tareas asignadas y con cambios de estado correctos en el tiempo).*

## 6. Resumen Ejecutivo 📈

1. **Tendencias y Patrones:** * **[Fecha] - [Título]:** [Breve descripción de tendencias/patrones colaborativos observados].
2. **Calidad del Trabajo:** * [Análisis cualitativo, buenas prácticas y áreas de mejora observadas en el código/flujo].
3. **Áreas de Mejora:** * [Recomendaciones específicas para mejorar colaboración y calidad en el futuro].