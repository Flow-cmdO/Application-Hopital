package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Salle;
import util.Registre;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SalleServlet extends HttpServlet {

    private static final Registre<Salle> registre = new Registre<>();
    public static Registre<Salle> getRegistre() { return registre; }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("supprimer".equals(req.getParameter("action"))) {
            supprimerSalle(req, resp);
            return;
        }

        String numero = req.getParameter("numero");
        String type   = req.getParameter("type");
        String dispo  = req.getParameter("disponible");

        List<Salle> liste = registre.getTous();

        if (numero != null && !numero.isBlank())
            liste = liste.stream().filter(s -> s.getNumero().contains(numero)).collect(Collectors.toList());
        if (type != null && !type.isBlank())
            liste = liste.stream().filter(s -> s.getType().toLowerCase().contains(type.toLowerCase())).collect(Collectors.toList());
        if ("true".equals(dispo))
            liste = liste.stream().filter(Salle::isDisponible).collect(Collectors.toList());

        long sallesDisponibles = registre.getTous().stream().filter(Salle::isDisponible).count();

        req.setAttribute("totalSalles",      registre.taille());
        req.setAttribute("sallesDisponibles", sallesDisponibles);
        req.setAttribute("salles",  liste);
        req.setAttribute("numero", numero);
        req.setAttribute("type",   type);
        req.setAttribute("disponible", dispo);
        req.getRequestDispatcher("/salles.jsp").forward(req, resp);
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("ajouter".equals(action)) {
            ajouterSalle(req, resp);
        } else if ("modifier".equals(action)) {
            modifierSalle(req, resp);
        }
    }


    private void ajouterSalle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String numero = req.getParameter("numero");
            String type   = req.getParameter("type");

            Salle s = new Salle(numero, type);
            registre.ajouter(s);

            req.getSession().setAttribute("message", "Salle ajoutée avec succès.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("salles");
    }

    private void modifierSalle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String numero = req.getParameter("numero");
            String type   = req.getParameter("type");

            Salle s = registre.get(numero);
            s.setType(type);

            req.getSession().setAttribute("message", "Salle modifiée.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("salles");
    }

    private void supprimerSalle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            registre.supprimer(req.getParameter("numero"));
            req.getSession().setAttribute("message", "Salle supprimée.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("salles");
    }
}