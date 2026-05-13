package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Medecin;
import util.Registre;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Medecinservlet extends HttpServlet {

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

        List<Medecin> liste = registre.getTous();

        if (nom != null && !nom.isBlank())
            liste = liste.stream().filter(m -> m.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        if (specialite != null && !specialite.isBlank())
            liste = liste.stream().filter(m -> m.getSpecialite().toLowerCase().contains(specialite.toLowerCase())).collect(Collectors.toList());

        req.setAttribute("totalMedecins", registre.taille());
        req.setAttribute("medecins",   liste);
        req.setAttribute("nom",        nom);
        req.setAttribute("specialite", specialite);
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
            String id         = req.getParameter("id");
            String nom        = req.getParameter("nom");
            String prenom     = req.getParameter("prenom");
            String specialite = req.getParameter("specialite");

            Medecin m = new Medecin(id, nom, prenom, specialite);
            registre.ajouter(m);

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
            String id         = req.getParameter("id");
            String specialite = req.getParameter("specialite");

            Medecin m = registre.get(id);
            m.setSpecialite(specialite);

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
            registre.supprimer(req.getParameter("id"));
            req.getSession().setAttribute("message", "Médecin supprimé.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("medecins");
    }
}