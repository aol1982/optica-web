package com.optica.controller;

import com.optica.dao.ClienteDAO;
import com.optica.dao.impl.ClienteDAOImpl;
import com.optica.model.Cliente;
import com.optica.model.Medicion;
import com.optica.model.Usuario;
import com.optica.service.MedicionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "MisMedicionesServlet", urlPatterns = {"/cliente/mediciones"})
public class MisMedicionesServlet extends HttpServlet {

    private final MedicionService medicionService = new MedicionService();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        Optional<Cliente> cliente = clienteDAO.buscarPorIdUsuario(usuario.getIdUsuario());

        if (cliente.isPresent()) {
            List<Medicion> mediciones = medicionService.listarPorCliente(cliente.get().getIdCliente());
            req.setAttribute("mediciones", mediciones);
        }

        req.getRequestDispatcher("/WEB-INF/views/cliente/mediciones.jsp").forward(req, resp);
    }
}
