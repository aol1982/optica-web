<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registro — Óptica Web</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center" style="min-height: 100vh;">

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body p-4">
                    <div class="text-center mb-4">
                        <i class="bi bi-person-plus display-4"></i>
                        <h3 class="mt-2">Crear cuenta</h3>
                    </div>

                    <c:if test="${error != null}">
                        <div class="alert alert-danger py-2">${error}</div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/registro">
                        <div class="mb-3">
                            <label class="form-label">Nombre completo</label>
                            <input type="text" name="nombre" class="form-control" required value="${nombre}">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">DNI</label>
                                <input type="text" name="dni" class="form-control" required value="${dni}">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Teléfono</label>
                                <input type="text" name="telefono" class="form-control" value="${telefono}">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Correo</label>
                            <input type="email" name="correo" class="form-control" required value="${correo}">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Contraseña</label>
                                <input type="password" name="password" class="form-control" required minlength="6">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Confirmar contraseña</label>
                                <input type="password" name="password2" class="form-control" required minlength="6">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-dark w-100">Registrarme</button>
                    </form>

                    <hr class="my-4">
                    <p class="text-center mb-0">
                        ¿Ya tienes cuenta?
                        <a href="${pageContext.request.contextPath}/login">Inicia sesión</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
