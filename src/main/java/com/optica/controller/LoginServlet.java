package com.optica.controller;

import com.optica.model.Usuario;
import com.optica.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            redirigirPorRol((Usuario) session.getAttribute("usuario"), req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String correo = req.getParameter("correo");
        String password = req.getParameter("password");

        try {
            Optional<Usuario> usuario = authService.login(correo, password);
            if (usuario.isEmpty()) {
                req.setAttribute("error", "Correo o contraseña incorrectos");
                req.setAttribute("correo", correo);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("usuario", usuario.get());
            redirigirPorRol(usuario.get(), req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Error del sistema: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }

    private void redirigirPorRol(Usuario u, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (u.getRol() == Usuario.Rol.CLIENTE) {
            resp.sendRedirect(req.getContextPath() + "/cliente/catalogo");
        } else if (u.getRol() == Usuario.Rol.ADMINISTRADOR) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard"); // Lo mandamos al panel de admin
        } else {
            resp.sendRedirect(req.getContextPath() + "/vendedor/inventario");
        }
    }
}