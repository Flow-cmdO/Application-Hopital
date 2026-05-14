package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Medecin;
import util.Registre;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MedecinServlet extends HttpServlet {

    private static final Registre<Medecin> registre = new Registre<>();
    public static Registre<Medecin> getRegistre() { return registre; }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("supprimer".equals(req.getParameter("action"))) {
            supprimerMedecin(req, resp);
            return;
        }

        String nom        = req.getParameter("nom");
        String specialite = req.getParameter("specialite");
        String service    = req.getParameter("service");

        List<Medecin> liste = registre.getAll();

        if (nom != null && !nom.isBlank())
            liste = liste.stream()
                    .filter(m -> m.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .collect(Collectors.toList());
        if (specialite != null && !specialite.isBlank())
            liste = liste.stream()
                    .filter(m -> m.getSpecialite().toLowerCase().contains(specialite.toLowerCase()))
                    .collect(Collectors.toList());
        if (service != null && !service.isBlank())
            liste = liste.stream()
                    .filter(m -> m.getService().toLowerCase().contains(service.toLowerCase()))
                    .collect(Collectors.toList());

        req.setAttribute("totalMedecins", registre.taille());
        req.setAttribute("medecins",   liste);
        req.setAttribute("nom",        nom);
        req.setAttribute("specialite", specialite);
        req.setAttribute("service",    service);
        req.getRequestDispatcher("/medecins.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("ajouter".equals(action)) {
            ajouterMedecin(req, resp);
        } else if ("modifier".equals(action)) {
            modifierMedecin(req, resp);
        }
    }

    private void ajouterMedecin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int        id           = Integer.parseInt(req.getParameter("id"));
            String     nom          = req.getParameter("nom");
            String     prenom       = req.getParameter("prenom");
            String     matricule    = req.getParameter("matricule");
            String     service      = req.getParameter("service");
            int     numOrdre   = Integer.parseInt(req.getParameter("numOrdre"));
            String     specialite  = req.getParameter("specialite");

            Medecin m = new Medecin(nom, prenom, id, matricule,
                                    service, numOrdre, specialite);
            registre. create(m);

            req.getSession().setAttribute("message", "Médecin ajouté avec succès.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("medecins");
    }

    private void modifierMedecin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int    id         = Integer.parseInt(req.getParameter("id"));
            String specialite = req.getParameter("specialite");
            String service    = req.getParameter("service");
            double salaire    = Double.parseDouble(req.getParameter("salaire"));

            Medecin m = registre.getById(id)
                    .orElseThrow(() -> new Exception("Médecin introuvable avec l'id " + id));
            m.setSpecialite(specialite);
            m.setService(service);

            req.getSession().setAttribute("message", "Médecin modifié.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("medecins");
    }

    private void supprimerMedecin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            registre.delete(id);
            req.getSession().setAttribute("message", "Médecin supprimé.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("medecins");
    }
}
