Pricing Service Microservice
============================

Este proyecto es un microservicio de pricing para un sistema de comercio electrónico. La aplicación está diseñada siguiendo los principios de la arquitectura hexagonal y Domain-Driven Design (DDD). El servicio se encarga de determinar el precio final (pvp) y la tarifa aplicable a un producto en función de distintos rangos de fechas, la marca y otros parámetros.

Características
---------------

-   **Determinación de precios:**\
    Selecciona la tarifa correcta según el producto, la marca y la fecha de aplicación, considerando reglas de prioridad.

-   **Persistencia en memoria:**\
    Uso de H2 en memoria para pruebas y desarrollo, con migraciones gestionadas por Liquibase en formato YAML.

-   **Observabilidad:**\
    Integración con OpenTelemetry para trazabilidad distribuida. Se utiliza el exportador de Jaeger para visualizar las trazas.

-   **Tests de comportamiento:**\
    Implementación de pruebas de aceptación con Cucumber y Gherkin, permitiendo validar diferentes escenarios de precios basados en la fecha y otros parámetros.

-   **Contenerización y despliegue en Kubernetes (Pendiente):**\
    El servicio se dockeriza y se despliega en un clúster de Kubernetes usando Helm, con escalado horizontal (mínimo 2 réplicas y máximo 5 según la carga) y balanceo de carga vía Ingress.

Tecnologías Utilizadas
----------------------

-   **Java 21 y Spring Boot 3**: Desarrollo del microservicio.

-   **Arquitectura hexagonal y DDD**: Organización y separación de responsabilidades.

-   **H2 Database + Liquibase**: Base de datos en memoria para desarrollo y migraciones.

-   **OpenTelemetry**: Para trazabilidad distribuida, configurado con el exportador Jaeger.

-   **Docker (Pendiente)**: Contenerización de la aplicación.

-   **Kubernetes & Helm (Pendiente)**: Despliegue y gestión de réplicas del servicio.

-   **Cucumber & Gherkin**: Tests de comportamiento (BDD) para validar escenarios de pricing.

-   **Micrometer (Pendiente)**: Para métricas y monitoreo (integrable con Prometheus y Grafana).

Estructura del Proyecto
-----------------------

La estructura del proyecto se organiza en módulos:

-   **domain**: Modelo y lógica de dominio.

-   **application**: Casos de uso y lógica de aplicación.

-   **infrastructure**: Adaptadores, persistencia (H2 + Liquibase), REST API, integración con OpenTelemetry, etc.

-   **behavior-tests**: Pruebas de comportamiento (BDD) con Cucumber y Gherkin.

Requisitos Previos
------------------

-   **Java 21**

-   **Maven 3.x**

-   **Docker**

-   (Pendiente) **Kubernetes** (por ejemplo, Minikube o Kind para entornos locales)

-   (Pendiente) **Helm**

-   **Jaeger**: Para visualizar trazas. Puedes levantarlo usando Docker:


``` 
docker run -d --name jaeger \ -e COLLECTOR_ZIPKIN_HTTP_HTTP_PORT=9411 \
 -p 5775:5775 \
 -p 6831:6831/udp \
 -p 6832:6832/udp \
 -p 5778:5778 \
 -p 16686:16686 \
 -p 14250:14250 \
 -p 14268:14268 \
 -p 14279:14279 \
 -p 14285:14285 \
 -p 9411:9411 \
 jaegertracing/all-in-one:1.37 
```

Cómo Compilar y Ejecutar
------------------------

### 1\. Compilación y Pruebas

Para compilar y ejecutar las pruebas unitarias y de integración (excluyendo los tests de behavior si se desea en otro módulo):

`mvn clean install`

### 2\. Ejecutar Tests de Behavior (BDD)

Si tienes un módulo separado para los tests de comportamiento, puedes ejecutarlos con Maven usando el perfil behavior-tests:

`mvn test -Pbehavior-tests`

### 3\. Observabilidad

-   **OpenTelemetry**: La configuración en el módulo de infraestructura integra OpenTelemetry con Jaeger.

-   **Ver trazas**: Accede a la UI de Jaeger en `http://localhost:16686/search` para ver las trazas.

Configuración Adicional
-----------------------

### Liquibase

-   Las migraciones de la base de datos se gestionan con Liquibase (en formato YAML).

-   Los changelogs se encuentran en `src/main/resources/db/changelog/`.

-   Ejemplo de changelog para crear la tabla `PRICES` y cargar datos iniciales.

### OpenTelemetry

-   Configuración en `OpenTelemetryConfig.java` en el módulo de infraestructura:

    -   Exportador Jaeger configurado en `http://localhost:14250`.

    -   El `Tracer` se crea con el nombre `"pricing-service"` y la versión `"1.0"`.

-   Para ver trazas, asegúrate de que Jaeger esté corriendo.

Licencia
--------

Este proyecto está bajo la Licencia MIT.