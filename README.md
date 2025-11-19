# 🏛️ Sistema de Integración Gubernamental Resiliente (SRI - ANT)

![Java](https://img.shields.io/badge/Backend-Java%2017%20%7C%20Spring%20Boot-green)
![React](https://img.shields.io/badge/Frontend-React%20%7C%20Vite-blue)
![Redis](https://img.shields.io/badge/Architecture-Redis%20Cache%20Aside-red)
![Pattern](https://img.shields.io/badge/Pattern-C4%20Model-orange)

## 📋 Descripción del Proyecto
Solución de arquitectura de software diseñada para unificar y consultar datos distribuidos de entidades gubernamentales (SRI y ANT). El sistema resuelve la problemática de la **baja disponibilidad** en servicios legados mediante patrones de resiliencia.

### 🚀 Funcionalidades Principales
1.  **Validación Tributaria:** Conexión directa con API REST del SRI para validación de contribuyentes (Personas Naturales).
2.  **Consulta de Tránsito:** Extracción de puntos de licencia mediante **Web Scraping (Jsoup)** hacia la plataforma legacy de la ANT.
3.  **Parque Automotor:** Consulta integrada de vehículos asociados al contribuyente.
4.  **Alta Disponibilidad:** Implementación del patrón **Cache-Aside** utilizando **Redis**.
    * *Si la ANT falla:* El sistema entrega el último dato conocido (Cache Hit).
    * *Si la ANT responde:* El sistema actualiza el caché asíncronamente (Cache Miss).

## 🛠️ Stack Tecnológico
* **Backend:** Java 17, Spring Boot 3.2, Spring Cloud OpenFeign.
* **Frontend:** React 18, Vite 5, TailwindCSS 3.4.
* **Persistencia/Caché:** Redis (Modo Standalone/Cloud).
* **Herramientas:** Maven, Git, IcePanel (Diagramado).

## 🔧 Guía de Despliegue (Instalación Limpia)

Este proyecto está autocontenido. Para ejecutarlo en un entorno nuevo:

1.  **Clonar repositorio:**
    ```bash
    git clone <URL_DEL_REPO>
    ```
2.  **Iniciar Backend:**
    ```bash
    cd backend
    ./mvnw spring-boot:run
    ```
3.  **Iniciar Frontend:**
    ```bash
    cd frontend
    npm install
    npm run dev
    ```
4.  **Acceder:** Navegar a `http://localhost:5173`

---
**Materia:** Diseño y Arquitectura de Software  
**Facultad:** Ingeniería y Ciencias Aplicadas