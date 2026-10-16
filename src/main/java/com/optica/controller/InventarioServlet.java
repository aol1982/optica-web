package com.optica.controller;

import com.optica.model.Aro;
import com.optica.service.AroService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "InventarioServlet", urlPatterns = {"/vendedor/inventario"})
public class InventarioServlet extends HttpServlet {

    private final AroService aroService = new AroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Aro> aros = aroService.listarTodos();
        List<Aro> alertas = aroService.listarStockBajo();

        req.setAttribute("aros", aros);
        req.setAttribute("alertas", alertas);
        req.getRequestDispatcher("/WEB-INF/views/vendedor/inventario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        try {
            if ("crear".equals(accion)) {
                Aro a = new Aro();
                a.setMarca(req.getParameter("marca"));
                a.setMaterial(req.getParameter("material"));
                a.setForma(req.getParameter("forma"));
                a.setColor(req.getParameter("color"));
                a.setPrecio(new BigDecimal(req.getParameter("precio")));
                a.setStock(Integer.parseInt(req.getParameter("stock")));
                aroService.crear(a);

            } else if ("actualizar".equals(accion)) {
                Aro a = new Aro();
                a.setIdAro(Integer.parseInt(req.getParameter("idAro")));
                a.setMarca(req.getParameter("marca"));
                a.setMaterial(req.getParameter("material"));
                a.setForma(req.getParameter("forma"));
                a.setColor(req.getParameter("color"));
                a.setPrecio(new BigDecimal(req.getParameter("precio")));
                a.setStock(Integer.parseInt(req.getParameter("stock")));
                aroService.actualizar(a);

            } else if ("eliminar".equals(accion)) {
                aroService.eliminar(Integer.parseInt(req.getParameter("idAro")));
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/vendedor/inventario");
    }
}
