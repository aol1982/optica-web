# Óptica Web — Sistema de Gestión con Arquitectura DAO

Aplicación web en Java (Jakarta EE) para gestionar los procesos de una óptica:
- **Clientes:** registro, historial de mediciones, catálogo de aros, apartados.
- **Vendedores:** inventario (aros, lunas, accesorios), ventas, alertas de stock.

## Tecnologías
- Java 21 LTS (Temurin)
- Jakarta Servlet 6 / JSP 3.1 / JSTL 3
- JDBC puro con patrón DAO
- SQL SERVER with AZURE
- Maven 3.10
- Apache Tomcat 10

## Estado del desarrollo
- [x] Estructura Maven
- [x] Modelos del dominio
- [x] Utilidades (PasswordUtil, ConexionBD placeholder)
- [x] Interfaces DAO
- [ ] Implementaciones JDBC
- [ ] Servicios
- [ ] Servlets
- [ ] Vistas JSP
- [ ] Conexión a SQL Server
- [ ] Despliegue en Tomcat
