package com.optica.controller;

import com.optica.dao.ClienteDAO;
import com.optica.dao.impl.ClienteDAOImpl;
import com.optica.model.Cliente;
import com.optica.model.Usuario;
import com.optica.service.ReservaAroService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ReservaAroServlet", urlPatterns = {"/cliente/apartar"})
public class ReservaAroServlet extends HttpServlet {

    private final ReservaAroService reservaService = new ReservaAroService();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Cliente> cliente = clienteDAO.buscarPorIdUsuario(usuario.getIdUsuario());
        if (cliente.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cliente/catalogo?error=Cliente no encontrado");
            return;
        }

        try {
            int idAro = Integer.parseInt(req.getParameter("idAro"));
            reservaService.apartar(cliente.get().getIdCliente(), idAro);
            resp.sendRedirect(req.getContextPath() + "/cliente/apartados?ok=1");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/cliente/catalogo?error=" + e.getMessage());
        }
    }
}
