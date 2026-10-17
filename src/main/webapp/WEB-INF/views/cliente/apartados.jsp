<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Mis apartados"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-bookmark"></i> Mis apartados</h2>

<c:if test="${param.ok != null}">
    <div class="alert alert-success">Aro apartado correctamente.</div>
</c:if>

<div class="card shadow-sm">
    <div class="table-responsive">
        <table class="table table-hover mb-0 align-middle">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Aro</th>
                    <th>Fecha de reserva</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${apartados}">
                    <tr>
                        <td>${r.idReserva}</td>
                        <td>Aro #${r.idAro}</td>
                        <td>${r.fechaReserva}</td>
                        <td>
                            <c:choose>
                                <c:when test="${r.estado == 'APARTADO'}"><span class="badge bg-primary">Apartado</span></c:when>
                                <c:when test="${r.estado == 'PROCESADO'}"><span class="badge bg-success">Procesado</span></c:when>
                                <c:when test="${r.estado == 'CANCELADO'}"><span class="badge bg-secondary">Cancelado</span></c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${r.estado == 'APARTADO'}">
                                <form method="post" action="${pageContext.request.contextPath}/cliente/apartados" class="d-inline">
                                    <input type="hidden" name="idApartado" value="${r.idReserva}">
                                    <button type="submit" class="btn btn-outline-danger btn-sm">
                                        <i class="bi bi-x-circle"></i> Cancelar
                                    </button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty apartados}">
                    <tr>
                        <td colspan="5" class="text-center text-muted py-4">
                            Aún no tienes aros apartados.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
