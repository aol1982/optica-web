<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Ventas"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-cart-check"></i> Ventas</h2>

<c:if test="${param.ok != null}">
    <div class="alert alert-success">Venta #${param.ok} registrada correctamente.</div>
</c:if>
<c:if test="${error != null}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="row g-4">
    <div class="col-md-5">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="card-title">Nueva venta</h5>
                <form method="post" action="${pageContext.request.contextPath}/vendedor/ventas">
                    <div class="mb-2">
                        <label class="form-label">Cliente</label>
                        <select name="idCliente" class="form-select" required>
                            <option value="">— Selecciona un cliente —</option>
                            <c:forEach var="cl" items="${clientes}">
                                <option value="${cl.idUsuario}">${cl.nombre} (${cl.correo})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-2">
                        <label class="form-label">Aro</label>
                        <select name="idAro" class="form-select">
                            <option value="">— Sin aro —</option>
                            <c:forEach var="a" items="${aros}">
                                <option value="${a.idAro}" ${a.stock == 0 ? 'disabled' : ''}>
                                    ${a.marca} — ${a.forma} (stock: ${a.stock}) — L. ${a.precio}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-2">
                        <label class="form-label">Medición (opcional)</label>
                        <input type="number" name="idMedicion" class="form-control" placeholder="ID de medición">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Cantidad</label>
                        <input type="number" name="cantidad" class="form-control" value="1" min="1" required>
                    </div>
                    <button type="submit" class="btn btn-success w-100">
                        <i class="bi bi-cash-coin"></i> Registrar venta
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="col-md-7">
        <div class="card shadow-sm">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th><th>Fecha</th><th>Cliente</th><th>Subtotal</th><th>Impuesto</th><th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="v" items="${ventas}">
                            <tr>
                                <td>${v.idVenta}</td>
                                <td>${v.fecha}</td>
                                <td>${v.idCliente}</td>
                                <td>L. <fmt:formatNumber value="${v.subtotal}" pattern="#,##0.00"/></td>
                                <td>L. <fmt:formatNumber value="${v.impuesto}" pattern="#,##0.00"/></td>
                                <td class="fw-bold">L. <fmt:formatNumber value="${v.total}" pattern="#,##0.00"/></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty ventas}">
                            <tr><td colspan="6" class="text-center text-muted py-4">Sin ventas registradas.</td></tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
