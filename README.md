# Seminario-de-Practica-de-Informatica
Mantis-PM: Prototipo de Sistema de Monitoreo de Equipos Industriales

Descripción:
Mantis-PM es un prototipo de aplicación Java para la gestión de lecturas de sensores industriales, generación de alertas y seguimiento de órdenes de trabajo. La arquitectura del sistema sigue principios de programación orientada a objetos y patrones de diseño como DAO (Data Access Object) para la persistencia de datos. El prototipo permite insertar lecturas de sensores, procesar alertas basadas en umbrales críticos y registrar intervenciones técnicas asociadas a las alertas generadas.

Tecnologías utilizadas:

- Java 17
- MySQL (base de datos relacional)
- JDBC para la conexión con la base de datos
- Patrones de diseño: DAO, modelo de dominio
- Gestión de excepciones y trazabilidad de errores

Objetivo del proyecto:
Simular el flujo de datos completo desde la medición de sensores hasta la gestión de alertas y órdenes de trabajo, asegurando consistencia, escalabilidad y claridad en la relación entre entidades del sistema.

Estructura del repositorio:
- src/main/java/com/mantispm/modelo/ → Clases de modelo de dominio
- src/main/java/com/mantispm/dao/ → Clases DAO para persistencia de datos
- src/main/java/com/mantispm/servicio/ → Lógica de negocio y procesamiento de alertas
- src/main/java/com/mantispm/Main.java → Interfaz de consola del prototipo
- mantis_pm_schema.sql → Esquema de base de datos con datos de prueba

Estado:
Prototipo funcional orientado a fines académicos, con posibilidad de extensión futura para integración de colas, listas de procesamiento y mejoras en la interfaz de usuario.
