<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${param.titulo != null ? param.titulo : 'Óptica Web'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-eyeglasses"></i> Óptica Web
        </a>
        <c:if test="${sessionScope.usuario != null}">
            <ul class="navbar-nav me-auto">
                <c:if test="${sessionScope.usuario.rol == 'CLIENTE'}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/cliente/catalogo">Catálogo</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/cliente/apartados">Mis apartados</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/cliente/mediciones">Mis mediciones</a></li>
                </c:if>
                <c:if test="${sessionScope.usuario.rol == 'VENDEDOR'}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/vendedor/inventario">Inventario</a></li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/vendedor/ventas">Ventas</a></li>
                </c:if>
            </ul>
            <span class="navbar-text text-white me-3">
                <i class="bi bi-person-circle"></i> ${sessionScope.usuario.correo}
            </span>
            <a class="btn btn-outline-light btn-sm" href="${pageContext.request.contextPath}/logout">Salir</a>
        </c:if>
    </div>
</nav>

<main class="container">
