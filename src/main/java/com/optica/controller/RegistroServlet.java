package com.optica.controller;

import com.optica.model.Usuario;
import com.optica.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombre = req.getParameter("nombre");
        String dni = req.getParameter("dni");
        String telefono = req.getParameter("telefono");
        String correo = req.getParameter("correo");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        req.setAttribute("nombre", nombre);
        req.setAttribute("dni", dni);
        req.setAttribute("telefono", telefono);
        req.setAttribute("correo", correo);

        if (password == null || !password.equals(password2)) {
            req.setAttribute("error", "Las contraseñas no coinciden");
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
            return;
        }

        try {
            Usuario u = authService.registrarCliente(nombre, dni, telefono, correo, password);
            req.getSession(true).setAttribute("usuario", u);
            resp.sendRedirect(req.getContextPath() + "/cliente/catalogo");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error del sistema: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp").forward(req, resp);
        }
    }
}
