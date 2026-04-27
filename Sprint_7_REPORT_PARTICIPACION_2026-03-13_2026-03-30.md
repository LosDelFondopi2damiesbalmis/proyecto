# Sprint 7 — Informe de Participación

**Organización:** LosDelFondopi2damiesbalmis  
**Proyecto:** PeluPOS  
**Periodo:** 2026-03-13 a 2026-03-30  
**Sprint:** 7  

---

## 1. Estadísticas Generales

* **Total de usuarios activos:** 1 (DavidRafaelGdaniec)
* **Periodo analizado:** 2026-03-13 a 2026-03-30
* **Días con actividad:** 3 (13, 16 y 17 de marzo)
* **Total de commits:** 14 (de los cuales 3 efectivos de código, 3 actualizaciones de diario y 8 merges)
* **Archivos modificados únicos:** 33

---

## 2. Registro de Commits

| Nombre | Fecha | Archivos modificados | Resumen de lo que ha realizado |
|---|---|---|---|
| DavidRafaelGdaniec | 2026-03-13 | `pruebas Postman/Clientes.json`, `Empleados.json`, `Facturas.json`, `Locales.json`, `PeluPos.json`, `Productos.json`, `Servicios.json`, `Usuarios.json` | Unificación de todas las colecciones Postman dispersas en una sola colección (`PeluPos.postman_collection.json`) para mayor organización y coherencia en las pruebas de la API. |
| DavidRafaelGdaniec | 2026-03-13 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario de trabajo (entrada 13/03). |
| DavidRafaelGdaniec | 2026-03-13 | *(Merge)* | Merge pull request #102 / merge remote-tracking branch `origin/main` → `david`. |
| DavidRafaelGdaniec | 2026-03-13 | *(Merge)* | Merge pull request #103 integrando los cambios anteriores a `main`. |
| DavidRafaelGdaniec | 2026-03-16 | `peluposbd/RolUsuario.java`, `peluposbd/Usuario.java`, `peluposbd.sql` | Añadida la entidad `RolUsuario` en Java y la columna correspondiente en la base de datos, completando la gestión de roles de usuario en la API REST. |
| DavidRafaelGdaniec | 2026-03-16 | *(Merge)* | Merge pull request #104 integrando rol de usuario a `main`. |
| DavidRafaelGdaniec | 2026-03-17 | `lib/jjwt/*` (8 libs), `nbproject/*` (4 archivos), `conf/persistence.xml`, `auth/AuthFilter.java`, `auth/JwtUtil.java`, `peluposbd/TokenVerificado.java`, `peluposbd/TokenVerificadoJpaController.java`, `peluposbd/Usuario.java`, `serpelupos/ServiceRESTAuth.java`, `serpelupos/ServiceRestUsuario.java`, `BorrarBD.sql`, `DatosPruebaPeluPosBD.sql`, `peluposbd.sql`, `PeluPos.postman_collection.json` | Implementación completa de seguridad JWT en la API REST: filtro de autenticación (`AuthFilter`), generación/validación de tokens (`JwtUtil`), endpoint de autenticación (`ServiceRESTAuth`), tabla de tokens verificados en BD y actualización del colección Postman con rutas seguras. |
| DavidRafaelGdaniec | 2026-03-17 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización del diario de trabajo (entrada 17/03, primera versión). |
| DavidRafaelGdaniec | 2026-03-17 | `docs/diarios/DavidRafael_Gdaniec.md` | Actualización real del diario de trabajo (entrada 17/03, corrección). |
| DavidRafaelGdaniec | 2026-03-17 | *(Merge)* | Merge pull request #105, #106 y #107 + merge remote-tracking branch. |

---

## 3. Análisis Individualizado de Contribuciones

| Usuario | Commits (Efectivos)* | Días activos | Contribución principal | Seguimiento (Diario) | Discrepancias |
|---|---|---|---|---|---|
| DavidRafaelGdaniec | 3 (de 14 totales) | 3 / 12 días hábiles | Implementación de la API REST: consolidación Postman, roles de usuario y seguridad JWT completa | Diario actualizado los días 13/03 y 17/03. Entradas presentes pero escuetas | Las entradas del diario coinciden con los commits reales. No hay discrepancias significativas. El 16/03 tiene commit de código pero no entrada en diario. |
| Francisco Garcia (SkYGi560) | 0 | 0 / 12 | Sin actividad en el sprint | Sin entradas desde diciembre 2025 | Ausencia total tanto en commits como en diario. Sin tareas asignadas en Sprint 7. |
| Joel Vives (kennzzaki) | 0 | 0 / 12 | Sin actividad en el sprint | Última entrada: 02/03/2026 (Sprint 6) | Ausencia total en Sprint 7. Sin tareas asignadas. La última entrada del diario es del primer día de la semana anterior al sprint. |
| Jesus Liñan (jesuslalm) | 0 | 0 / 12 | Sin actividad en el sprint | Sin entradas desde noviembre 2025 | Ausencia prolongada en commits y diario. Sin tareas asignadas en Sprint 7. |

*\*Commits efectivos: excluyen merges y actualizaciones de diario.*

---

## 4. Resumen Análisis 📈 (Individual)

---

**David Rafael Gdaniec (DavidRafaelGdaniec):**

* **Contribución principal:** Ha sido el único miembro activo del equipo durante el Sprint 7. Su aportación ha sido la construcción completa de la capa de backend (API REST Java/JPA): organización de colecciones Postman, añadido de roles de usuario en la base de datos y, como pieza central, la implementación de seguridad JWT con filtro de autenticación, utilidades de token y endpoint de login. La API REST queda funcional y segura al término del sprint.

* **Seguimiento:** El diario recoge entradas del 13/03 y 17/03, que se corresponden con los días de actividad real. Sin embargo, falta la entrada del 16/03 (día en que se añadieron los roles). Las entradas presentes son breves pero informativas, incluyendo tiempo dedicado y tareas realizadas.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:**
  Las tres tareas asignadas a DavidRafaelGdaniec en Sprint 7 han pasado a estado **Done**:
  - ✅ *Comienzo de API Rest* → Done
  - ✅ *Crear la estructura inicial de API Rest* → Done
  - ✅ *Añadir seguridad a la API* → Done

  El ritmo de progresión es coherente: primero estructura (13/03), luego roles (16/03) y finalmente seguridad (17/03). No se detectan discrepancias entre el tablero y el repositorio.

* **Participación en el grupo de forma activa:** 🟡 — David participa y entrega resultado tangible, pero los días activos son solo 3 de 12 posibles (25 %). La concentración del trabajo en apenas dos días (16 y 17 de marzo) sugiere que la carga fue gestionada en periodos cortos e intensos en lugar de una participación distribuida a lo largo del sprint.

* **Registro en Diario de Trabajo durante el Sprint:** 🟡 — Existen entradas (13/03 y 17/03), pero falta el 16/03 y las entradas son relativamente breves. La regularidad mejora respecto a sprints anteriores de los compañeros, pero aún no alcanza el nivel reflexivo y detallado esperado.

* **Participación en el Incremento:** 🟢 — Las tres tareas del sprint están completadas y se puede demostrar el trabajo mediante commits, colección Postman y la API funcional con autenticación JWT. La contribución es significativa y hace avanzar el proyecto de forma clara.

---

**Francisco Garcia (SkYGi560):**

* **Contribución principal:** Ninguna en el Sprint 7. No hay commits ni tareas asignadas.

* **Seguimiento:** El diario está desactualizado desde diciembre de 2025, lo que representa más de tres meses sin registros.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** No se le asignó ninguna tarea en Sprint 7 en el tablero de GitHub Projects.

* **Participación en el grupo de forma activa:** 🔴 — Ausencia total durante el sprint. No hay evidencia de ningún tipo de actividad.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin entradas desde diciembre 2025.

* **Participación en el Incremento:** 🔴 — Sin contribución al incremento del sprint.

---

**Joel Vives (kennzzaki):**

* **Contribución principal:** Ninguna en el Sprint 7. No hay commits ni tareas asignadas.

* **Seguimiento:** La última entrada del diario es del 02/03/2026, que corresponde a la última semana del Sprint 6. No hay entradas para el Sprint 7.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** No se le asignó ninguna tarea en Sprint 7 en el tablero de GitHub Projects.

* **Participación en el grupo de forma activa:** 🔴 — Ausencia total durante el sprint.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Sin entradas para el periodo del Sprint 7. El diario quedó congelado al comienzo de este sprint.

* **Participación en el Incremento:** 🔴 — Sin contribución al incremento del sprint.

---

**Jesús Liñan (jesuslalm):**

* **Contribución principal:** Ninguna en el Sprint 7. No hay commits ni tareas asignadas.

* **Seguimiento:** El diario no tiene entradas desde noviembre de 2025, lo que representa casi cinco meses sin registros. Es la situación más preocupante del equipo.

* **Análisis de las Tasks y/o UserStories asignadas durante el Sprint:** No se le asignó ninguna tarea en Sprint 7 en el tablero de GitHub Projects.

* **Participación en el grupo de forma activa:** 🔴 — Ausencia prolongada y sin evidencia de actividad en este ni en sprints anteriores recientes.

* **Registro en Diario de Trabajo durante el Sprint:** 🔴 — Diario abandonado desde noviembre 2025.

* **Participación en el Incremento:** 🔴 — Sin contribución al incremento del sprint.

---

## 5. Resumen Análisis 📈 (Por Grupo)

**LosDelFondopi2damiesbalmis — PeluPOS:**

* **Funcionalidad del incremento entregado:** 🟡 — DavidRafaelGdaniec tiene sus tres tareas en "Done" y el incremento (API REST con seguridad JWT) es funcional. Sin embargo, tres de los cuatro miembros del equipo no tienen ninguna tarea completada ni siquiera asignada en este sprint, por lo que el incremento es obra de un único miembro.

* **Realización de los eventos de Scrum, aplicando su filosofía:** 🔴 — Solo un miembro del equipo trabajó activamente durante el sprint. Además, no se han creado tarjetas de retrospectiva para el Sprint 7 (la última retrospectiva registrada en el tablero corresponde al Sprint 5). La ausencia de retrospectiva y la participación unipersonal impiden hablar de trabajo en equipo bajo la filosofía Scrum.

* **Compromiso del equipo con el flujo de trabajo de Scrum a través de GitHub Projects:** 🟡 — Las tareas asignadas a DavidRafaelGdaniec están correctamente asignadas y gestionadas, con cambios de estado coherentes con el trabajo realizado. No obstante, tres miembros del equipo no tienen ninguna tarea asignada en el sprint, lo que evidencia una falta de planificación colectiva en el Sprint Planning.

---

## 6. Resumen Ejecutivo 📈

1. **Tendencias y Patrones:**
   * **2026-03-13 a 2026-03-17 — Trabajo individual concentrado:** El Sprint 7 sigue el patrón ya observado en sprints anteriores: un único miembro (DavidRafaelGdaniec) concentra toda la actividad del repositorio. El trabajo se realizó en tres días intensivos durante la primera mitad del sprint, sin actividad registrada a partir del 17 de marzo hasta el final del periodo (30 de marzo). La segunda mitad del sprint quedó completamente inactiva para todo el equipo.
   * **Desincronización creciente del equipo:** Francisco, Joel y Jesús llevan respectivamente más de 3, más de 3 y más de 4 meses sin actividad en el repositorio. Esta desconexión se ha ido incrementando sprint a sprint.

2. **Calidad del Trabajo:**
   * El trabajo técnico de DavidRafaelGdaniec en este sprint es de buena calidad: la implementación JWT incluye filtro de autenticación, utilidades de token, entidad de tokens verificados en BD y endpoint dedicado, lo que refleja un entendimiento sólido de la seguridad en APIs REST.
   * El uso de ramas y pull requests es correcto, con integración ordenada a `main` mediante PRs consecutivos (#102-#107).
   * Las colecciones Postman consolidadas son un buen ejemplo de mantenimiento de calidad del proyecto de pruebas.

3. **Áreas de Mejora:**
   * **Urgente — Reincorporación del equipo:** Francisco, Joel y Jesús deben retomar su participación de forma inmediata. Sin distribución de trabajo, el proyecto recae sobre una sola persona y el equipo pierde el sentido de Scrum.
   * **Sprint Planning colectivo:** El hecho de que solo un miembro tenga tareas asignadas indica que el Sprint Planning no se está realizando como un evento colectivo. Es necesario que todos los miembros tomen tareas y se comprometan con ellas al inicio del sprint.
   * **Retrospectiva:** No se ha generado ninguna tarjeta de retrospectiva desde el Sprint 5. Es importante retomar este evento para identificar mejoras y mantener el espíritu ágil.
   * **Distribución temporal del trabajo:** Incluso el miembro activo concentra su trabajo en pocos días. Distribuir las tareas a lo largo del sprint favorece la revisión continua y reduce riesgos.
   * **Diario de trabajo:** Solo DavidRafaelGdaniec mantiene el diario relativamente al día (aunque con entradas breves). Los demás miembros deben retomarlo urgentemente como herramienta de seguimiento y reflexión.
