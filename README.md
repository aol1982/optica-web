# Óptica Web - Sistema de Gestión

Aplicación web completa para la gestión de una óptica, desarrollada en Java (Jakarta EE), con patrón DAO y base de datos en Azure SQL.

## 🚀 Tecnologías
- Java 21 LTS
- Jakarta Servlets, JSP y JSTL
- JDBC puro (Patrón DAO)
- Azure SQL Database (SQL Server)
- Apache Tomcat 10
- Bootstrap 5 (Frontend)
- BCrypt (Seguridad)

## ⚙️ Requisitos previos
- JDK 21
- Maven 3.9+
- Apache Tomcat 10
- Acceso a la base de datos (Azure SQL)

## 🛠️ Instalación local
1. Clonar el repositorio:
   `git clone https://github.com/aol1982/optica-web.git`
2. Crear el archivo `src/main/resources/db.properties` basado en `db.properties.example` y colocar las credenciales de Azure.
3. Compilar el proyecto:
   `mvn clean package`
4. Copiar el archivo `target/optica-web.war` a la carpeta `webapps` de Tomcat.
5. Iniciar Tomcat y acceder a: `http://localhost:8080/optica-web/`

## 👤 Credenciales de prueba
- **Cliente:** `cliente@test.com` / `cliente123`
- **Vendedor:** `admin@optica.com` / `admin123`

## 📂 Arquitectura
- `model`: Clases de negocio (Usuario, Cliente, Aro, Venta, etc.)
- `dao`: Interfaces de acceso a datos.
- `dao.impl`: Implementaciones JDBC.
- `service`: Lógica de negocio.
- `controller`: Servlets (Manejo de peticiones web).