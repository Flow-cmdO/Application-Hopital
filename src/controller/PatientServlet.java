package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Patient;
import model.Registre;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Patientservlet extends HttpServlet {

    private static final Registre<Patient> registre = new Registre<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("supprimer".equals(action)) {
            supprimerPatient(req, resp);
            return;
        }

        String nom    = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String id     = req.getParameter("id");

        List<Patient> liste = registre.getTous();

        if (nom != null && !nom.isBlank())
            liste = liste.stream().filter(p -> p.getNom().toLowerCase().contains(nom.toLowerCase())).collect(Collectors.toList());
        if (prenom != null && !prenom.isBlank())
            liste = liste.stream().filter(p -> p.getPrenom().toLowerCase().contains(prenom.toLowerCase())).collect(Collectors.toList());
        if (id != null && !id.isBlank())
            liste = liste.stream().filter(p -> p.getId().equalsIgnoreCase(id)).collect(Collectors.toList());


        req.setAttribute("totalPatients", registre.taille());

        req.setAttribute("patients", liste);
        req.setAttribute("nom",    nom);
        req.setAttribute("prenom", prenom);
        req.setAttribute("id",     id);
        req.getRequestDispatcher("/patients.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("ajouter".equals(action)) {
            ajouterPatient(req, resp);
        } else if ("modifier".equals(action)) {
            modifierPatient(req, resp);
        }
    }


    private void ajouterPatient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String id     = req.getParameter("id");
            String nom    = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String date   = req.getParameter("dateNaissance");

            Patient p = new Patient(id, nom, prenom, date);
            registre.ajouter(p);

            req.getSession().setAttribute("message", "Patient ajouté avec succès.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("patients");
    }

    private void modifierPatient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String id     = req.getParameter("id");
            String nom    = req.getParameter("nom");
            String prenom = req.getParameter("prenom");

            Patient p = registre.get(id);
            p.setNom(nom);
            p.setPrenom(prenom);

            req.getSession().setAttribute("message", "Patient modifié avec succès.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("patients");
    }

    private void supprimerPatient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String id = req.getParameter("id");
            registre.supprimer(id);
            req.getSession().setAttribute("message", "Patient supprimé.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("patients");
    }
}