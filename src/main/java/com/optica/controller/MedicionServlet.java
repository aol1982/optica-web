package com.optica.controller;

import com.optica.dao.ClienteDAO;
import com.optica.dao.impl.ClienteDAOImpl;
import com.optica.model.Medicion;
import com.optica.service.MedicionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebServlet(name = "MedicionServlet", urlPatterns = {"/vendedor/mediciones"})
public class MedicionServlet extends HttpServlet {

    private final MedicionService medicionService = new MedicionService();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("clientes", clienteDAO.listarTodos());
        req.getRequestDispatcher("/WEB-INF/views/vendedor/mediciones.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Medicion m = new Medicion();
            m.setIdCliente(Integer.parseInt(req.getParameter("idCliente")));
            
            m.setEsferaOd(parseBigDecimal(req.getParameter("esferaOd")));
            m.setCilindroOd(parseBigDecimal(req.getParameter("cilindroOd")));
            m.setEjeOd(parseInteger(req.getParameter("ejeOd")));
            
            m.setEsferaOi(parseBigDecimal(req.getParameter("esferaOi")));
            m.setCilindroOi(parseBigDecimal(req.getParameter("cilindroOi")));
            m.setEjeOi(parseInteger(req.getParameter("ejeOi")));
            
            m.setAdicion(parseBigDecimal(req.getParameter("adicion")));
            m.setFechaRegistro(LocalDateTime.now());

            medicionService.registrar(m);
            resp.sendRedirect(req.getContextPath() + "/vendedor/mediciones?ok=1");

        } catch (Exception e) {
            req.setAttribute("error", "Error al registrar medición: " + e.getMessage());
            req.setAttribute("clientes", clienteDAO.listarTodos());
            req.getRequestDispatcher("/WEB-INF/views/vendedor/mediciones.jsp").forward(req, resp);
        }
    }

    private BigDecimal parseBigDecimal(String val) {
        if (val == null || val.isBlank()) return null;
        return new BigDecimal(val.replace(",", "."));
    }

    private Integer parseInteger(String val) {
        if (val == null || val.isBlank()) return null;
        return Integer.parseInt(val);
    }
}