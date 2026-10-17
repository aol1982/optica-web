<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Mis apartados"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-bookmark"></i> Mis apartados</h2>

<c:if test="${param.ok != null}">
    <div class="alert alert-success">Aro apartado correctamente.</div>
</c:if>

<div class="row row-cols-1 row-cols-md-3 g-4">
    <c:forEach var="a" items="${apartados}">
        <div class="col">
            <div class="card h-100 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Aro #${a.idAro}</h5>
                    <p class="text-muted mb-2">Apartado el ${a.fecha}</p>
                    <form method="post" action="${pageContext.request.contextPath}/cliente/apartados">
                        <input type="hidden" name="idApartado" value="${a.idApartado}">
                        <button type="submit" class="btn btn-outline-danger btn-sm w-100">
                            <i class="bi bi-trash"></i> Eliminar apartado
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty apartados}">
        <div class="col-12">
            <div class="alert alert-info">Aún no tienes aros apartados.</div>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
