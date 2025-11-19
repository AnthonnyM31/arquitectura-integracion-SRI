# 🏛️ Sistema de Integración Gubernamental  
### (SRI – ANT – Registro Civil)

![Java](https://img.shields.io/badge/Backend-Java%2017%20%7C%20Spring%20Boot-green)
![React](https://img.shields.io/badge/Frontend-React%20%7C%20Vite-blue)
![Redis](https://img.shields.io/badge/Cache-Redis%20(Distributed)-red)
![Architecture](https://img.shields.io/badge/Design-C4%20Model-orange)

## 📌 Descripción del Proyecto

Este proyecto fue desarrollado para la asignatura **Diseño y Arquitectura de Software** con el objetivo de resolver un problema frecuente en instituciones gubernamentales:  
la **fragmentación de datos**, el **acceso disperso** y la **baja disponibilidad** de servicios externos.

El sistema unifica la información de varias entidades en una sola interfaz, permitiendo consultar:

1. **Datos Tributarios (SRI):** Validación de contribuyentes y estado fiscal.  
2. **Datos Personales:** Identificación de Personas Naturales.  
3. **Datos de Tránsito (ANT):** Puntos de licencia obtenidos mediante Web Scraping.  
4. **Datos Vehiculares:** Información del parque automotor asociado.

## 🏗️ Arquitectura y Diseño

Se implementó una arquitectura **microservicios simplificada (monorepo)**, documentada con el modelo **C4**, priorizando la **resiliencia** y la eficiencia frente a caídas de los servicios externos.

### 🔧 Características Técnicas Relevantes

- **Patrón Cache-Aside (Lazy Loading) con Redis:**  
  Mitiga las caídas frecuentes de la ANT.  
  - Si la web está caída y existe un dato reciente en caché → **el sistema sigue operando**.

- **Cliente Declarativo con OpenFeign:**  
  Desacopla la lógica del Backend de las peticiones al SRI.

- **Web Scraping con Jsoup:**  
  La ANT no dispone de API JSON, por lo que se desarrolló un mecanismo de extracción y limpieza de HTML.

- **Frontend Reactivo:**  
  Construido con Vite y TailwindCSS para alto rendimiento.

## 🚀 Guía de Instalación (Desde Cero)

### 🧩 Paso 1: Instalar Prerrequisitos

Instale los siguientes programas:

1. **Java JDK 17:**  
   https://corretto.aws/downloads/latest/amazon-corretto-17-x64-windows-jdk.msi  
2. **Node.js (LTS):**  
   https://nodejs.org/dist/v20.11.1/node-v20.11.1-x64.msi  
3. **Git:**  
   https://git-scm.com/download/win  
4. **Redis (Opcional):**  
   El sistema funciona sin Redis (solo mostrará advertencias).

### 🧩 Paso 2: Clonar el Proyecto

```bash
git clone https://github.com/AnthonnyM31/arquitectura-integracion-SRI
cd examen-arquitectura-integracion
```

### 🧩 Paso 3: Ejecutar el Backend

```bash
cd backend
./mvnw spring-boot:run
```

Espere hasta ver:

```
Started BackendApplication in X seconds
```

### 🧩 Paso 4: Ejecutar el Frontend

Abra otra terminal:

```bash
cd examen-arquitectura-integracion/frontend
npm install
npm run dev
```

### 🧩 Paso 5: Ingresar al Sistema

Abra el navegador en:

```
http://localhost:5173
```

## 🛠️ Retos de Desarrollo y Soluciones

Durante el desarrollo se identificaron y resolvieron los siguientes desafíos:

### 1️⃣ Incompatibilidad de Versiones (Vite vs Node Legacy)

**Problema:**  
La máquina del laboratorio tenía Node 18, incompatible con Vite 6 y TailwindCSS 4.

**Solución:**  
Se realizó *downgrade* controlado a **Vite 5 + Tailwind 3.4**, manteniendo estabilidad y rendimiento.

### 2️⃣ Integración con Sistemas Legacy (ANT en .jsp)

**Problema:**  
La ANT expone información en HTML (.jsp), no en formato consumible por aplicaciones modernas.

**Solución:**  
Se desarrolló un servicio de **Web Scraping con Jsoup**, que transforma HTML en objetos JSON limpios.

### 3️⃣ Alta Disponibilidad sin Infraestructura Cloud

**Problema:**  
Se debía soportar la caída del servicio externo sin usar AWS o Azure.

**Solución:**  
Se simuló un entorno resiliente usando:
- Redis Local  
- Manejo robusto de excepciones  
- Mecanismos de fallback y degradación controlada  

El sistema **nunca se cae**, aun si la ANT no responde.

## 🧪 Cómo Probar la Resiliencia

1. Realice una consulta real con un RUC válido.  
2. En los logs verá un **Cache Miss** (consulta directa).  
3. Repita la consulta inmediatamente.  
4. Ahora verá un **Cache Hit** (respuesta instantánea desde Redis).

## 👨‍💻 Equipo de Desarrollo

- **Mateo Coronel**  
- **Anthonny Mosquera**  
- **David Puga**

Materia: **Diseño y Arquitectura de Software**  
Facultad de Ingeniería y Ciencias Aplicadas
