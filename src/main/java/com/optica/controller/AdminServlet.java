package com.optica.controller;

import com.optica.service.AuthService;
import com.optica.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/dashboard"})
public class AdminServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || usuario.getRol() != Usuario.Rol.ADMINISTRADOR) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado. Solo administradores.");
            return;
        }

        req.setAttribute("vendedores", authService.listarVendedores());
        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        try {
            if ("crearVendedor".equals(accion)) {
                String correo = req.getParameter("correo");
                String password = req.getParameter("password");
                authService.registrarVendedor(correo, password);
            } else if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("idUsuario"));
                authService.eliminarUsuario(id);
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }
}