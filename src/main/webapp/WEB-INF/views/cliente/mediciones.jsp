<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Mis mediciones"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-eye"></i> Mi historial de mediciones</h2>

<div class="card shadow-sm">
    <div class="table-responsive">
        <table class="table table-hover mb-0 text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th rowspan="2">Fecha</th>
                    <th colspan="3">Ojo Derecho (OD)</th>
                    <th colspan="3">Ojo Izquierdo (OI)</th>
                    <th rowspan="2">Adición</th>
                </tr>
                <tr>
                    <th>Esfera</th>
                    <th>Cilindro</th>
                    <th>Eje</th>
                    <th>Esfera</th>
                    <th>Cilindro</th>
                    <th>Eje</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="m" items="${mediciones}">
                    <tr>
                        <td>${m.fechaRegistro}</td>
                        <td>${m.esferaOd}</td>
                        <td>${m.cilindroOd}</td>
                        <td>${m.ejeOd}</td>
                        <td>${m.esferaOi}</td>
                        <td>${m.cilindroOi}</td>
                        <td>${m.ejeOi}</td>
                        <td>${m.adicion}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty mediciones}">
                    <tr>
                        <td colspan="8" class="text-center text-muted py-4">
                            Aún no tienes mediciones registradas.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
