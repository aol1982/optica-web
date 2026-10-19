<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Admin Dashboard"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-shield-lock"></i> Panel de Administración</h2>

<div class="row mb-4">
    <div class="col-md-4">
        <a href="${pageContext.request.contextPath}/vendedor/inventario" class="text-decoration-none">
            <div class="card text-white bg-success shadow-sm h-100">
                <div class="card-body text-center">
                    <i class="bi bi-box-seam display-4"></i>
                    <h5 class="card-title mt-2">Gestionar Inventario</h5>
                    <p class="card-text small mb-0">Agregar, editar y eliminar lentes</p>
                </div>
            </div>
        </a>
    </div>
    <div class="col-md-4">
        <a href="${pageContext.request.contextPath}/vendedor/ventas" class="text-decoration-none">
            <div class="card text-white bg-primary shadow-sm h-100">
                <div class="card-body text-center">
                    <i class="bi bi-cart-check display-4"></i>
                    <h5 class="card-title mt-2">Registrar Ventas</h5>
                    <p class="card-text small mb-0">Procesar nuevas ventas de lentes</p>
                </div>
            </div>
        </a>
    </div>
</div>

<c:if test="${not empty sessionScope.flashError}">
    <div class="alert alert-danger alert-dismissible fade show">
        ${sessionScope.flashError}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("flashError"); %>
</c:if>

<div class="row g-4">
    <div class="col-md-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Nuevo Vendedor</h5>
                <form method="post" action="${pageContext.request.contextPath}/admin/dashboard">
                    <input type="hidden" name="accion" value="crearVendedor">
                    <div class="mb-3">
                        <label class="form-label">Correo del Vendedor</label>
                        <input type="email" name="correo" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Contraseña</label>
                        <input type="password" name="password" class="form-control" required minlength="6">
                    </div>
                    <button type="submit" class="btn btn-primary w-100"><i class="bi bi-person-plus"></i> Registrar Vendedor</button>
                </form>
            </div>
        </div>
    </div>

    <div class="col-md-8">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Lista de Vendedores</h5>
                <div class="table-responsive">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="v" items="${vendedores}">
                                <tr>
                                    <td>${v.idUsuario}</td>
                                    <td>${v.correo}</td>
                                    <td><span class="badge bg-info">${v.rol}</span></td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/admin/dashboard" class="d-inline"
                                              onsubmit="return confirm('¿Eliminar a este vendedor?')">
                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="idUsuario" value="${v.idUsuario}">
                                            <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty vendedores}">
                                <tr><td colspan="4" class="text-center text-muted py-4">No hay vendedores registrados.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>