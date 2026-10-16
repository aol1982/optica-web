package com.optica.controller;

import com.optica.model.Aro;
import com.optica.service.AroService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CatalogoServlet", urlPatterns = {"/cliente/catalogo"})
public class CatalogoServlet extends HttpServlet {

    private final AroService aroService = new AroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String marca = req.getParameter("marca");
        String material = req.getParameter("material");
        String forma = req.getParameter("forma");

        List<Aro> aros = aroService.filtrar(marca, material, forma);

        req.setAttribute("aros", aros);
        req.setAttribute("marca", marca);
        req.setAttribute("material", material);
        req.setAttribute("forma", forma);

        req.getRequestDispatcher("/WEB-INF/views/cliente/catalogo.jsp").forward(req, resp);
    }
}
