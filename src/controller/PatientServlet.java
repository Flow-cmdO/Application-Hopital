package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Patient;
import util.Registre;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PatientServlet extends HttpServlet {

    private static final Registre<Patient> registre = new Registre<>();
    public static Registre<Patient> getRegistre() { return registre; }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("supprimer".equals(req.getParameter("action"))) {
            supprimerPatient(req, resp);
            return;
        }

        String nom    = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String idStr  = req.getParameter("id");

        List<Patient> liste = registre.getAll();

        if (nom != null && !nom.isBlank())
            liste = liste.stream()
                    .filter(p -> p.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .collect(Collectors.toList());
        if (prenom != null && !prenom.isBlank())
            liste = liste.stream()
                    .filter(p -> p.getPrenom().toLowerCase().contains(prenom.toLowerCase()))
                    .collect(Collectors.toList());
        if (idStr != null && !idStr.isBlank()) {
            int id = Integer.parseInt(idStr);
            liste = liste.stream()
                    .filter(p -> p.getId() == id)
                    .collect(Collectors.toList());
        }

        req.setAttribute("totalPatients", registre.taille());
        req.setAttribute("patients", liste);
        req.setAttribute("nom",    nom);
        req.setAttribute("prenom", prenom);
        req.setAttribute("id",     idStr);
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
            int    id            = Integer.parseInt(req.getParameter("id"));
            String nom           = req.getParameter("nom");
            String prenom        = req.getParameter("prenom");
            int    age           = Integer.parseInt(req.getParameter("age"));
            String maladie       = req.getParameter("maladie");
            String numeroDossier = req.getParameter("numeroDossier");

            Patient p = new Patient(nom, prenom, id, age, maladie, numeroDossier);
            registre.getAll(p);

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
            int    id      = Integer.parseInt(req.getParameter("id"));
            String nom     = req.getParameter("nom");
            String prenom  = req.getParameter("prenom");
            int    age     = Integer.parseInt(req.getParameter("age"));
            String maladie = req.getParameter("maladie");

            Patient p = registre.getById(id)
                    .orElseThrow(() -> new Exception("Patient introuvable avec l'id " + id));
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setAge(age);
            p.setMaladie(maladie);

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
            int id = Integer.parseInt(req.getParameter("id"));
            registre.delete(id);
            req.getSession().setAttribute("message", "Patient supprimé.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("patients");
    }
}
