package com.optica.controller;

import com.optica.model.Usuario;
import com.optica.service.ApartadoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ApartarServlet", urlPatterns = {"/cliente/apartar"})
public class ApartarServlet extends HttpServlet {

    private final ApartadoService apartadoService = new ApartadoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        try {
            int idAro = Integer.parseInt(req.getParameter("idAro"));
            apartadoService.apartar(usuario.getIdUsuario(), idAro);
            resp.sendRedirect(req.getContextPath() + "/cliente/apartados?ok=1");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/cliente/catalogo?error=" + e.getMessage());
        }
    }
}
