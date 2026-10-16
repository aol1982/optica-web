package com.optica.controller;

import com.optica.model.*;
import com.optica.service.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "VentaServlet", urlPatterns = {"/vendedor/ventas"})
public class VentaServlet extends HttpServlet {

    private final VentaService ventaService = new VentaService();
    private final AroService aroService = new AroService();
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("aros", aroService.listarTodos());
        req.setAttribute("clientes", authService.listarClientes());
        req.setAttribute("ventas", ventaService.listarTodas());

        req.getRequestDispatcher("/WEB-INF/views/vendedor/ventas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario vendedor = (Usuario) session.getAttribute("usuario");

        try {
            int idCliente = Integer.parseInt(req.getParameter("idCliente"));
            Integer idMedicion = parsearNullable(req.getParameter("idMedicion"));
            Integer idAro = parsearNullable(req.getParameter("idAro"));
            Integer idLuna = parsearNullable(req.getParameter("idLuna"));
            int cantidad = Integer.parseInt(req.getParameter("cantidad"));

            Venta v = ventaService.registrarVenta(idCliente, vendedor.getIdUsuario(),
                    idMedicion, idAro, idLuna, cantidad);

            resp.sendRedirect(req.getContextPath() + "/vendedor/ventas?ok=" + v.getIdVenta());

        } catch (Exception e) {
            req.setAttribute("error", "Error al registrar venta: " + e.getMessage());
            req.setAttribute("aros", aroService.listarTodos());
            req.setAttribute("clientes", authService.listarClientes());
            req.setAttribute("ventas", ventaService.listarTodas());
            req.getRequestDispatcher("/WEB-INF/views/vendedor/ventas.jsp").forward(req, resp);
        }
    }

    private Integer parsearNullable(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }
}
