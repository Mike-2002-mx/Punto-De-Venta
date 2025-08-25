# Point of Sale (POS) System Backend
The Point Of Sale (POS) frontend designed to support business operations with:
- Sales Module - Handle product sales.
- Inventory Module - Track and manage products in stock.
- Sales History - Review and track previous sales records.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Contact](#contact)

## General Information
This project was created with the intention of solving a common problem faced by small and medium-sized businesses: the difficulty of managing sales, inventory, and transaction history efficiently. Many businesses still rely on manual methods or outdated tools, which often leads to errors, wasted time, and poor visibility over their operations. On the other hand, most commercial POS systems available on the market tend to be costly or overly complex, making them inaccessible or impractical for smaller shops.

The purpose of this project is to provide a simple, intuitive, and efficient Point of Sale (POS) frontend that centralizes essential business operations. Through its main modules—sales, inventory management, and sales history—it allows users to process transactions, view and update product details, adjust stock levels, and review past records, all from a clean and easy-to-use interface.

## Technologies Used
The backend is built with the following technologies:

- **Lenguaje**: Java 23 
- **Framework**: Spring Boot 3  
- **Persistencia**: Spring Data JPA, Hibernate  
- **Base de Datos**: PostgreSQL 19+  
- **Documentación API**: Swagger / Springdoc OpenAPI  
- **Build Tool**: Maven  

## Features
### Sales Module
- Process sales efficiently and accurately.
### Inventory Module
- View product details.
- Add new products.
- Update existing products.
- Adjust stock levels.
### Sales History
- Review and track previous transactions with ease.

## Setup
Follow these steps to run the backend locally:

### 1. Prerequisites
Make sure you have installed:  
- **Java 21**  
- **Maven 3.9+**  
- **PostgreSQL 19+**  

Check versions with:  
```
java -version
mvn -v
psql --version
```

### 2. Clone the repository

```bash
# Clonar repositorio
git clone https://github.com/your-username/Punto-De-Venta.git
cd Punto-De-Venta
```

## 3. Configure the database

Create a PostgreSQL database(e.g., posdb)
Update the database connection in src/main/resources/application.properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/posdb
spring.datasource.username=postgres
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 4. Build and run the application

Ejecutar con Maven:
```
mvn spring-boot:run
```

### 5. Access the API

The backend will start at: `http://localhost:8080`

API documentation is available via Swagger UI: 
`http://localhost:8080/swagger-ui.html`


## Project Status
Project is: _in progress_ 

## Room for Improvement

### Current Improvements
- Optimize query performance for faster response times. 

### To-Do
- Add support for shopping cart functionality.  
- Implement user authentication and authorization.  

## Contact

- **Name:** Miguel Mateo 
- **GitHub:** [https://github.com/Mike-2002-mx](https://github.com/Mike-2002-mx)  
- **LinkedIn:** [https://www.linkedin.com/in/Miguel-Mateo](https://www.linkedin.com/in/miguel-mateo-link)


