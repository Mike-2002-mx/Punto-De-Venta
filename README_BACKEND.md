# 🖥️ POS System - Backend

Backend del sistema de punto de venta (POS) desarrollado con **Spring Boot** y **PostgreSQL**.  
Este servicio provee una API REST para la gestión de productos, ventas e inventario.


## 📖 Descripción
El backend provee los servicios y lógica de negocio para el sistema POS.  
Ofrece endpoints REST para:  
- Administrar productos.  
- Registrar ventas.  
- Manejar inventario.  

## ✨ Características
- CRUD de productos con validaciones.  
- Registro de ventas con actualización automática de inventario.  
- Persistencia con PostgreSQL usando Spring Data JPA.  
- Documentación de API con Swagger (OpenAPI).  

---

## 🛠️ Tecnologías
- **Lenguaje**: Java 23 
- **Framework**: Spring Boot 3  
- **Persistencia**: Spring Data JPA, Hibernate  
- **Base de Datos**: PostgreSQL 14+  
- **Documentación API**: Swagger / Springdoc OpenAPI  
- **Build Tool**: Maven  

---


## 📋 Requisitos Previos
- JDK 17+  
- PostgreSQL 14+  
- Maven 3.8+  
- Git  


## ⚙️ Instalación

```bash
# Clonar repositorio
git clone https://github.com/usuario/pos-system.git
cd pos-system/backend
```

Compilar el proyecto:
```bash
./mvnw clean install
```

---

## 🔧 Configuración
En `src/main/resources/application.properties` define la conexión a la base de datos:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/posdb
spring.datasource.username=postgres
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

---

## 🚀 Ejecución

Ejecutar con Maven:
```bash
./mvnw spring-boot:run
```

El backend quedará disponible en:  
👉 `http://localhost:8080`

Documentación Swagger:  
👉 `http://localhost:8080/swagger-ui.html`

---

## ✅ Pruebas

Ejecutar pruebas unitarias y de integración:
```bash
./mvnw test
```
