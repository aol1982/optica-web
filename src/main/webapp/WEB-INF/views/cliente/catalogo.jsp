<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Catálogo"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-grid"></i> Catálogo de Aros</h2>

<c:if test="${param.error != null}">
    <div class="alert alert-warning">${param.error}</div>
</c:if>

<form method="get" action="${pageContext.request.contextPath}/cliente/catalogo" class="card p-3 mb-4">
    <div class="row g-2 align-items-end">
        <div class="col-md-3">
            <label class="form-label">Marca</label>
            <input type="text" name="marca" class="form-control" value="${marca}">
        </div>
        <div class="col-md-3">
            <label class="form-label">Material</label>
            <input type="text" name="material" class="form-control" value="${material}">
        </div>
        <div class="col-md-3">
            <label class="form-label">Forma</label>
            <input type="text" name="forma" class="form-control" value="${forma}">
        </div>
        <div class="col-md-3">
            <button type="submit" class="btn btn-dark w-100">
                <i class="bi bi-search"></i> Filtrar
            </button>
        </div>
    </div>
</form>

<div class="row row-cols-1 row-cols-md-3 g-4">
    <c:forEach var="aro" items="${aros}">
        <div class="col">
            <div class="card h-100 shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">${aro.marca}</h5>
                    <p class="card-text mb-1"><small class="text-muted">${aro.material} · ${aro.forma} · ${aro.color}</small></p>
                    <p class="fw-bold mb-2">L. <fmt:formatNumber value="${aro.precio}" pattern="#,##0.00"/></p>
                    <p class="mb-3">
                        <c:choose>
                            <c:when test="${aro.stock > 0}">
                                <span class="badge bg-success">Stock: ${aro.stock}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">Sin stock</span>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <form method="post" action="${pageContext.request.contextPath}/cliente/apartar">
                        <input type="hidden" name="idAro" value="${aro.idAro}">
                        <button type="submit" class="btn btn-outline-dark btn-sm w-100"
                                ${aro.stock == 0 ? 'disabled' : ''}>
                            <i class="bi bi-bookmark-plus"></i> Apartar
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty aros}">
        <div class="col-12">
            <div class="alert alert-info">No hay aros disponibles con esos filtros.</div>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
