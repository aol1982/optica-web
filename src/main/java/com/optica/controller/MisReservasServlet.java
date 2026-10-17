package com.optica.controller;

import com.optica.dao.ClienteDAO;
import com.optica.dao.impl.ClienteDAOImpl;
import com.optica.model.Cliente;
import com.optica.model.ReservaAro;
import com.optica.model.Usuario;
import com.optica.service.ReservaAroService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "MisReservasServlet", urlPatterns = {"/cliente/apartados"})
public class MisReservasServlet extends HttpServlet {

    private final ReservaAroService reservaService = new ReservaAroService();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Cliente> cliente = clienteDAO.buscarPorIdUsuario(usuario.getIdUsuario());

        List<ReservaAro> reservas = cliente.isPresent()
                ? reservaService.listarPorCliente(cliente.get().getIdCliente())
                : Collections.emptyList();

        req.setAttribute("apartados", reservas);
        req.getRequestDispatcher("/WEB-INF/views/cliente/apartados.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("idApartado"));
            reservaService.cancelar(id);
        } catch (Exception ignored) {}

        resp.sendRedirect(req.getContextPath() + "/cliente/apartados");
    }
}
