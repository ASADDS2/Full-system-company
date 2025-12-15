# Servicio de Cotización Full System

Un microservicio robusto para la gestión de cotizaciones de servicios, construido con **Java 21**, **Spring Boot 3.2.5** y una **Interfaz Web Moderna**, siguiendo **Arquitectura Hexagonal**.

## 🚀 Cómo ejecutar el proyecto

Este sistema consta de dos partes: Backend (Java) y Frontend (Node.js).

### Requisitos Previos
*   Java 21 JDK
*   Node.js (v18 o superior)
*   Navegador Web (Chrome, Edge, Firefox)

### Paso 1: Levantar el Backend (Servidor)
1.  Abrir una terminal en la carpeta `fullsystemservice`.
2.  Ejecutar el siguiente comando para iniciar la aplicación:
    ```bash
    ./mvnw clean compile spring-boot:run
    ```
    *(En Windows PowerShell: `.\mvnw clean compile spring-boot:run`)*
3.  Esperar a que aparezca el mensaje `Started FullsystemApplication in ... seconds`.
4.  El backend estará disponible en: `http://localhost:8080`.
    *   **Swagger UI (Documentación API):** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    *   **Prometheus (Métricas):** [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

### Paso 2: Levantar el Frontend (Web App)
1.  Abrir una **nueva** terminal en la carpeta `full-system-web-app`.
2.  Instalar las dependencias (solo la primera vez):
    ```bash
    npm install
    ```
3.  Iniciar el servidor web:
    ```bash
    npm start
    ```
4.  La aplicación web estará disponible en: `http://localhost:3000`.

---

## 🏗️ Decisiones Técnicas Tomadas

1.  **Arquitectura Hexagonal (Ports & Adapters)**:
    *   Se eligió para desacoplar el núcleo del negocio (Dominio) de los frameworks externos (Spring Boot, BD).
    *   Permite cambiar la base de datos o el framework web sin afectar las reglas de negocio.
    *   Mejora la testabilidad del núcleo.

2.  **Base de Datos en Memoria (H2)**:
    *   Se optó por H2 para desarrollo local para eliminar la necesidad de instalar MySQL o Docker en la máquina del evaluador/desarrollador.
    *   Facilita la ejecución inmediata del proyecto (`maven run` y listo).

3.  **Frontend sin Frameworks Pesados**:
    *   Se utilizó **Vanilla JS (ES6+)** con el **Patrón Módulo (Revealing Module Pattern)** en lugar de React o Angular.
    *   **Motivo**: Reducir la complejidad de configuración y build tools para un alcance de CRUD, demostrando sólidos fundamentos de JavaScript y manipulación del DOM.
    *   **MVC**: Se implementó una arquitectura Modelo-Vista-Controlador manual en el cliente para mantener el código organizado.

4.  **Observabilidad**:
    *   Se incluyó **Spring Actuator** y endpoint de **Prometheus** para exponer métricas.
    *   Se decidió no forzar el uso de Docker para Prometheus/Grafana para simplificar la corrección rápida, pero la data está disponible en `/actuator/prometheus`.

5.  **Comunicación API**:
    *   Se habilitó **CORS** globalmente para permitir que el frontend (puerto 3000) hable con el backend (puerto 8080) sin restricciones en desarrollo.

---

## 💡 Suposiciones Realizadas

1.  **Unicidad de Clientes**: No se gestiona un maestro de clientes. El nombre del cliente se ingresa libremente en cada cotización. Se asume que la identificación "Recurrente" es un flag manual de confianza.
2.  **Persistencia Volátil**: Al usar H2 en memoria, se asume que **los datos se pierden al reiniciar el servidor**. Esto es aceptable para ambientes de desarrollo y pruebas de concepto.
3.  **Moneda**: Todos los valores monetarios se manejan y formatean asumiendo **Pesos Colombianos (COP)** sin decimales visibles, aunque el backend soporta `BigDecimal` para precisión.
4.  **Horario Laboral**: Para el cálculo de horas, se asume una entrada simple de "cantidad de horas", sin validación de calendarios o días hábiles.
5.  **Seguridad**: No se implementó autenticación (JWT/OAuth) para agilizar el acceso y prueba de las funcionalidades CRUD, asumiendo que es una herramienta interna en una red segura.

---

## 📋 Reglas de Negocio Implementadas

1.  **Cálculo de Subtotal**: `Tarifa Hora * Horas`.
2.  **Descuento**: **10%** automático si se marca el checkbox "Cliente Recurrente".
3.  **Recargo**: **15%** automático si las horas superan **40**.
4.  **Total**: `Subtotal - Descuento + Recargo`.

---
**Full System Company © 2025**