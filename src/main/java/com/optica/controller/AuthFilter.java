package com.optica.controller;

import com.optica.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/cliente/*", "/vendedor/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith("/cliente/") && usuario.getRol() != Usuario.Rol.CLIENTE) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        }

        if (path.startsWith("/vendedor/")) {
            if (usuario.getRol() != Usuario.Rol.VENDEDOR && usuario.getRol() != Usuario.Rol.ADMINISTRADOR) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}