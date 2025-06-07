# Gestor de Contratos de Alquiler (Sistema de Gestión por Consola)

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Console App Icon](https://img.shields.io/badge/Type-Console%20Application-blue)

## 📚 Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características Principales](#características-principales)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Cómo Compilar y Ejecutar](#cómo-compilar-y-ejecutar)
- [Estructura de Directorios](#estructura-de-directorios)
- [Estado del Proyecto](#estado-del-proyecto)
- [Capturas de Pantalla](#capturas-de-pantalla)
- [Autor](#autor)


## Descripción del Proyecto

Este es un sistema de gestión de alquileres por consola desarrollado en Java utilizando Maven como herramienta de construcción. Permite a los usuarios administrar propiedades, inquilinos, contratos de alquiler, pagos e incidencias de manera eficiente. El objetivo principal es ofrecer una herramienta sencilla y efectiva para el control de la información relevante en la gestión de alquileres, sirviendo como una base sólida para futuras expansiones.

### Características Principales:

* **Gestión de Propiedades:** CRUD completo para propiedades (agregar, listar, buscar, actualizar, eliminar). Incluye la capacidad de marcar propiedades como alquiladas.
* **Gestión de Inquilinos:** CRUD completo para inquilinos con detalles como nombre, apellido, teléfono, DNI y email.
* **Gestión de Contratos de Alquiler:** Creación, listado (activos y todos), búsqueda, actualización, finalización (inactivación) y **eliminación permanente** de contratos. Los contratos manejan fechas de inicio y fin, propiedad asociada, inquilino y valor mensual.
* **Gestión de Pagos:** Registro, listado (todos o por contrato), búsqueda, confirmación, actualización y eliminación de pagos asociados a contratos.
* **Gestión de Incidencias:** Reporte, listado (todos o por propiedad), búsqueda, actualización y eliminación de incidencias relacionadas con las propiedades.

## Tecnologías Utilizadas

* **Java 17 (o superior):** Lenguaje de programación principal.
* **Apache Maven:** Herramienta de automatización de construcción y gestión de dependencias.
* **Java SE (Standard Edition):** Para el desarrollo de la aplicación de consola.
* **Programación Orientada a Objetos (POO):** Diseño modular y escalable.
* **Colecciones de Java (`ArrayList`, `Optional`):** Para la gestión de datos en memoria.
* **`java.time` API (LocalDate, DateTimeFormatter):** Para el manejo de fechas de forma moderna y segura, incluyendo formato DD-MM-YYYY.
* **Entrada/Salida por Consola (`Scanner`):** Interfaz de usuario interactiva.
* **IntelliJ IDEA:** Entorno de Desarrollo Integrado (IDE) utilizado para el desarrollo.

## Cómo Compilar y Ejecutar

Para ejecutar este proyecto, necesitas tener el JDK (Java Development Kit) instalado en tu sistema (versión 17 o superior recomendada) y Apache Maven (que suele venir integrado en IntelliJ IDEA).

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/Sebaacostamont/GestorAlquileresJava.git
    cd GestorAlquileresJava
    ```

2.  **Compilar y empaquetar el proyecto (usando Maven):**
    Asegúrate de estar en el directorio raíz del proyecto donde se encuentra `pom.xml`.
    ```bash
    mvn clean install
    ```
    Este comando compilará el código, ejecutará las pruebas (Próximamente incluiré test unitarios con JUnit) y empaquetará la aplicación en un archivo JAR ejecutable dentro del directorio `target/`.

3.  **Ejecutar la aplicación:**
    Después de compilar, encontrarás el archivo JAR ejecutable en la carpeta `target/`. El nombre del archivo JAR será `GestorAlquileres-1.0-SNAPSHOT.jar` (o similar, dependiendo de la versión en tu `pom.xml`).
    ```bash
    java -jar target/GestorAlquileres-1.0-SNAPSHOT.jar
    ```

## Estructura de Directorios

```plaintext
GestorAlquileresJava/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── Sebaacostam3/
│   │   │           └── gestoralquileres/
│   │   │               ├── app/
│   │   │               ├── modelo/
│   │   │               └── servicio/
│   │   └── resources/
│   └── test/
│       └── java/
```


## Estado del Proyecto

Funcional. Se encuentra en una fase estable con funcionalidades completas para gestión de alquileres por consola.  
Próximamente: Integración de persistencia con base de datos (JDBC / JPA / Hibernate). Creación e integración de una GUI en JavaFX  
Pendiente: Cobertura de pruebas unitarias con JUnit.

## Capturas de Pantalla

### Menú Principal
![Menú Principal](./docs/console-menu.png)

### Listado de Propiedades
![Listado de Propiedades](./docs/console-propiedades.png)

## Autor

**Sebastian Esteban Acosta Montoya**  
Estudiante de Ingeniería en Computación (UDELAR)  
[GitHub](https://github.com/Sebaacostamont) | [LinkedIn](https://www.linkedin.com/in/sebastián-acosta-689945244)
