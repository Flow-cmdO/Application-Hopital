package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Lit;
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

        List<Salle> liste = registre.getAll();

        if (numero != null && !numero.isBlank())
            liste = liste.stream()
                    .filter(s -> s.getNumero().contains(numero))
                    .collect(Collectors.toList());
        if (type != null && !type.isBlank())
            liste = liste.stream()
                    .filter(s -> s.getType().toLowerCase().contains(type.toLowerCase()))
                    .collect(Collectors.toList());
        if ("true".equals(dispo))
            liste = liste.stream()
                    .filter(Salle::isDisponible)
                    .collect(Collectors.toList());

        long sallesDisponibles = registre.getAll().stream()
                .filter(Salle::isDisponible).count();

        req.setAttribute("totalSalles",       registre.taille());
        req.setAttribute("sallesDisponibles", sallesDisponibles);
        req.setAttribute("salles",     liste);
        req.setAttribute("numero",     numero);
        req.setAttribute("type",       type);
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
        } else if ("ajouterLit".equals(action)) {
            ajouterLit(req, resp);
        }
    }

    private void ajouterSalle(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int    id          = Integer.parseInt(req.getParameter("id"));
            String numero      = req.getParameter("numero");
            String type        = req.getParameter("type");
            int    capacite = Integer.parseInt(req.getParameter("capacite"));

            Salle s = new Salle(id, numero, type, capacite);
            registre.create(s);

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
            int    id          = Integer.parseInt(req.getParameter("id"));
            String type        = req.getParameter("type");
            int    capaciteMax = Integer.parseInt(req.getParameter("capaciteMax"));

            Salle s = registre.getById(id)
                    .orElseThrow(() -> new Exception("Salle introuvable avec l'id " + id));
            s.setType(type);
            s.setCapacite(capaciteMax);

            req.getSession().setAttribute("message", "Salle modifiée.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("salles");
    }

    private void ajouterLit(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int    idSalle = Integer.parseInt(req.getParameter("idSalle"));
            String idLit   = req.getParameter("idLit");

            Salle s = registre.getById(idSalle)
                    .orElseThrow(() -> new Exception("Salle introuvable avec l'id " + idSalle));
            s.ajouterLit(new Lit(idLit));

            req.getSession().setAttribute("message", "Lit ajouté à la salle.");
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
            int id = Integer.parseInt(req.getParameter("id"));
            registre.delete(id);
            req.getSession().setAttribute("message", "Salle supprimée.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("salles");
    }
}
