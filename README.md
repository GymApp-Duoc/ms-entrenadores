#ms-entrenadores (GymApp )

Este microservicio gestiona el registro, las especialidades y el estado del staff de entrenadores dentro del ecosistema GymApp. Forma parte de una arquitectura distribuida basada en microservicios y está diseñado bajo los principios de Inversión de Control (IoC), separación de responsabilidades (CRS) y alta disponibilidad.

---

##Arquitectura y Tecnologías

El proyecto ha sido desarrollado aplicando estrictamente las buenas prácticas de la industria:
- **Framework:** Spring Boot 3.4.x (Java 17)
- **Persistencia:** Spring Data JPA + PostgreSQL
- **Migraciones de BD:** Flyway (Versionamiento estructurado)
- **Seguridad:** Spring Security + JWT (Tokens Stateless)
- **Comunicación Síncrona:** Spring Cloud OpenFeign
- **Documentación API:** SpringDoc OpenAPI 3 (Swagger UI)
- **Despliegue (CI/CD):** Docker + Render Cloud

###Modelo Relacional
A continuación se presenta el Modelo Relacional de su base de datos aislada:

<p align="center">
  <img width="853" height="510" alt="Evidencia Modelo Relacional" src="image_7815fe.png">
</p>

*(Nota: Asegúrate de que el nombre del archivo de la imagen coincida con la que tienes en tu repositorio).*

---

## Principios de Diseño Implementados

Cumpliendo con los requisitos del Hito 2, este microservicio incorpora:
1. **Inyección de Dependencias Segura:** Se utiliza `@Autowired` por constructor en todas las capas (Controller, Service, Security), garantizando objetos inmutables y facilitando el testing.
2. **Patrón DTO:** Transferencia de datos segura mediante `EntrenadorRequestDTO` y `EntrenadorResponseDTO`, protegiendo el modelo de dominio interno (`Entrenador`).
3. **Resiliencia en Comunicación Cruzada:** Manejo de excepciones elegante mediante `FeignException` al comunicarse con `ms-clases` para validar dependencias antes de eliminar registros físicos.
4. **Configuración Externalizada:** Variables sensibles migradas a `application.yaml` y gestionadas a través de variables de entorno para su despliegue seguro.

---

##Despliegue en la Nube (Render)

Este microservicio (MVP) se encuentra desplegado de manera independiente en la infraestructura de Render, conectado a una base de datos PostgreSQL nativa.

* **Base de Datos:** PostgreSQL (Render Free Tier)
* **Entorno de Ejecución:** Java Runtime Environment
* **Data Demo:** Se inicializa automáticamente con Data Demo a través de Flyway (`V2__insertar_datos_iniciales.sql`).

###Prueba de Humo (Swagger UI)
Puede explorar y ejecutar los endpoints del microservicio (CRUD y Reportes) directamente en la nube a través de Swagger UI:



---

##Ejecución Local y Testing

Para ejecutar el proyecto de manera local saltando las pruebas (simulando entorno cloud):
```bash
./mvnw clean package -DskipTests
java -jar target/ms-entrenadores-0.0.1-SNAPSHOT.jar