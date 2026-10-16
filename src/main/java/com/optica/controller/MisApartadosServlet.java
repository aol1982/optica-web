package com.optica.controller;

import com.optica.model.Apartado;
import com.optica.model.Usuario;
import com.optica.service.ApartadoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MisApartadosServlet", urlPatterns = {"/cliente/apartados"})
public class MisApartadosServlet extends HttpServlet {

    private final ApartadoService apartadoService = new ApartadoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        List<Apartado> apartados = apartadoService.listarPorCliente(usuario.getIdUsuario());
        req.setAttribute("apartados", apartados);
        req.getRequestDispatcher("/WEB-INF/views/cliente/apartados.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("idApartado"));
            apartadoService.eliminar(id);
        } catch (Exception ignored) {}

        resp.sendRedirect(req.getContextPath() + "/cliente/apartados");
    }
}
