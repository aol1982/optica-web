<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Inventario"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-box-seam"></i> Inventario de Aros</h2>

<c:if test="${not empty alertas}">
    <div class="alert alert-warning">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <strong>Alerta de stock bajo (menos de 5 unidades):</strong>
        <ul class="mb-0 mt-2">
            <c:forEach var="a" items="${alertas}">
                <li>${a.marca} — ${a.forma} (${a.material}) → quedan ${a.stock}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<div class="row g-4">
    <div class="col-md-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Nuevo aro</h5>
                <form method="post" action="${pageContext.request.contextPath}/vendedor/inventario">
                    <input type="hidden" name="accion" value="crear">
                    <div class="mb-2"><input type="text" name="marca" class="form-control" placeholder="Marca" required></div>
                    <div class="mb-2"><input type="text" name="material" class="form-control" placeholder="Material"></div>
                    <div class="mb-2"><input type="text" name="forma" class="form-control" placeholder="Forma"></div>
                    <div class="mb-2"><input type="text" name="color" class="form-control" placeholder="Color"></div>
                    <div class="mb-2"><input type="number" step="0.01" name="precio" class="form-control" placeholder="Precio" required></div>
                    <div class="mb-3"><input type="number" name="stock" class="form-control" placeholder="Stock" required min="0"></div>
                    <button type="submit" class="btn btn-success w-100"><i class="bi bi-plus-lg"></i> Agregar</button>
                </form>
            </div>
        </div>
    </div>

    <div class="col-md-8">
        <div class="card shadow-sm">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th><th>Marca</th><th>Material</th><th>Forma</th><th>Color</th>
                            <th>Precio</th><th>Stock</th><th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="a" items="${aros}">
                            <tr>
                                <td>${a.idAro}</td>
                                <td>${a.marca}</td>
                                <td>${a.material}</td>
                                <td>${a.forma}</td>
                                <td>${a.color}</td>
                                <td>L. <fmt:formatNumber value="${a.precio}" pattern="#,##0.00"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${a.stock < 5}"><span class="badge bg-danger">${a.stock}</span></c:when>
                                        <c:otherwise><span class="badge bg-success">${a.stock}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/vendedor/inventario" class="d-inline"
                                          onsubmit="return confirm('¿Eliminar este aro?')">
                                        <input type="hidden" name="accion" value="eliminar">
                                        <input type="hidden" name="idAro" value="${a.idAro}">
                                        <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
