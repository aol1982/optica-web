<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Registrar Medición"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-eye"></i> Registrar Nueva Medición</h2>

<c:if test="${param.ok != null}">
    <div class="alert alert-success">Medición registrada correctamente.</div>
</c:if>
<c:if test="${error != null}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<div class="card shadow-sm mb-4">
    <div class="card-body">
        <form method="post" action="${pageContext.request.contextPath}/vendedor/mediciones">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Cliente</label>
                    <select name="idCliente" class="form-select" required>
                        <option value="">— Selecciona un cliente —</option>
                        <c:forEach var="cl" items="${clientes}">
                            <option value="${cl.idCliente}">${cl.nombre} (${cl.dni})</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 border-end">
                    <h6 class="text-muted">Ojo Derecho (OD)</h6>
                    <div class="row g-2">
                        <div class="col-4"><input type="number" step="0.25" name="esferaOd" class="form-control" placeholder="Esfera"></div>
                        <div class="col-4"><input type="number" step="0.25" name="cilindroOd" class="form-control" placeholder="Cilindro"></div>
                        <div class="col-4"><input type="number" name="ejeOd" class="form-control" placeholder="Eje"></div>
                    </div>
                </div>
                <div class="col-md-6">
                    <h6 class="text-muted">Ojo Izquierdo (OI)</h6>
                    <div class="row g-2">
                        <div class="col-4"><input type="number" step="0.25" name="esferaOi" class="form-control" placeholder="Esfera"></div>
                        <div class="col-4"><input type="number" step="0.25" name="cilindroOi" class="form-control" placeholder="Cilindro"></div>
                        <div class="col-4"><input type="number" name="ejeOi" class="form-control" placeholder="Eje"></div>
                    </div>
                </div>
            </div>

            <div class="row mt-3">
                <div class="col-md-4">
                    <label class="form-label">Adición</label>
                    <input type="number" step="0.25" name="adicion" class="form-control" placeholder="Ej: 1.50">
                </div>
            </div>

            <button type="submit" class="btn btn-primary mt-4 w-100"><i class="bi bi-save"></i> Guardar Medición</button>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>