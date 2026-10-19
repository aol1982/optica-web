package com.optica.controller;

import com.optica.model.Aro;
import com.optica.model.Usuario;
import com.optica.service.AroService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "InventarioServlet", urlPatterns = {"/vendedor/inventario"})
public class InventarioServlet extends HttpServlet {

    private final AroService aroService = new AroService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        List<Aro> aros = aroService.listarTodos();
        List<Aro> alertas = aroService.listarStockBajo();
        req.setAttribute("aros", aros);
        req.setAttribute("alertas", alertas);
        
        // Enviamos un booleano a la vista para saber si mostrar el formulario
        boolean esAdmin = (usuario != null && usuario.getRol() == Usuario.Rol.ADMINISTRADOR);
        req.setAttribute("esAdmin", esAdmin);

        req.getRequestDispatcher("/WEB-INF/views/vendedor/inventario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // BLOQUEO: Solo el administrador puede modificar el inventario
        if (usuario == null || usuario.getRol() != Usuario.Rol.ADMINISTRADOR) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado. Solo el administrador puede modificar el inventario.");
            return;
        }

        String accion = req.getParameter("accion");
        try {
            if ("crear".equals(accion)) {
                Aro a = new Aro();
                a.setMarca(req.getParameter("marca"));
                a.setMaterial(req.getParameter("material"));
                a.setForma(req.getParameter("forma"));
                a.setColor(req.getParameter("color"));
                a.setPrecio(new BigDecimal(req.getParameter("precio")));
                a.setStock(Integer.parseInt(req.getParameter("stock")));
                
                String imgUrl = req.getParameter("imagenUrl");
                if (imgUrl == null || imgUrl.isBlank()) {
                    imgUrl = "https://placehold.co/400x300/EEEEEE/333333?text=" + a.getMarca();
                }
                a.setImagenUrl(imgUrl);
                
                aroService.crear(a);
            } else if ("actualizar".equals(accion)) {
                Aro a = new Aro();
                a.setIdAro(Integer.parseInt(req.getParameter("idAro")));
                a.setMarca(req.getParameter("marca"));
                a.setMaterial(req.getParameter("material"));
                a.setForma(req.getParameter("forma"));
                a.setColor(req.getParameter("color"));
                a.setPrecio(new BigDecimal(req.getParameter("precio")));
                a.setStock(Integer.parseInt(req.getParameter("stock")));
                
                String imgUrl = req.getParameter("imagenUrl");
                if (imgUrl == null || imgUrl.isBlank()) {
                    imgUrl = "https://placehold.co/400x300/EEEEEE/333333?text=" + a.getMarca();
                }
                a.setImagenUrl(imgUrl);
                
                aroService.actualizar(a);
            } else if ("eliminar".equals(accion)) {
                aroService.eliminar(Integer.parseInt(req.getParameter("idAro")));
            }
        } catch (Exception e) {
            req.getSession().setAttribute("flashError", e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/vendedor/inventario");
    }
}