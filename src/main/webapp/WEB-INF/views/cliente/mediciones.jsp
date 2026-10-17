<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="titulo" value="Mis mediciones"/>
</jsp:include>

<h2 class="mb-4"><i class="bi bi-eye"></i> Mi historial de mediciones</h2>

<div class="card shadow-sm">
    <div class="table-responsive">
        <table class="table table-hover mb-0">
            <thead class="table-dark">
                <tr>
                    <th>Fecha</th>
                    <th>Ojo</th>
                    <th>Esfera</th>
                    <th>Cilindro</th>
                    <th>Eje</th>
                    <th>Adición</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="m" items="${mediciones}">
                    <tr>
                        <td>${m.fecha}</td>
                        <td>${m.ojo}</td>
                        <td>${m.esfera}</td>
                        <td>${m.cilindro}</td>
                        <td>${m.eje}</td>
                        <td>${m.adicion}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty mediciones}">
                    <tr>
                        <td colspan="6" class="text-center text-muted py-4">
                            Aún no tienes mediciones registradas.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
