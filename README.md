# DataService

Microservicio Spring Boot encargado de administrar la información operativa del sistema: ventas, productos, empleados, sucursales, regiones, ciudades y detalle de ventas.

## Requisitos

- Java 21
- Maven
- Base de datos MySQL disponible
- Variables de entorno para la conexión a la base de datos

## Instalación y ejecución

```bash
mvn clean install
mvn spring-boot:run
```

El servicio se ejecuta por defecto en `http://localhost:8091`.

## Configuración

Define las siguientes variables de entorno antes de iniciar el servicio:

- `SPRING_DATASOURCE_URL` - URL JDBC de la base de datos MySQL.
- `SPRING_DATASOURCE_USERNAME` - Usuario de la base de datos.
- `SPRING_DATASOURCE_PASSWORD` - Contraseña de la base de datos.

## Funcionalidades principales

- Gestión completa de entidades maestras y transaccionales.
- Búsquedas filtradas por fecha, sucursal, región, ciudad, cargo y categoría.
- Exposición de endpoints REST para consumo desde otros microservicios o frontends.
- Inicialización automática del esquema con JPA al arrancar la aplicación.

## Notas

- El microservicio tiene habilitada la ejecución de tareas programadas con `@EnableScheduling`.
- La configuración de JPA está preparada para recrear el esquema en cada arranque mediante `ddl-auto: create-drop`.