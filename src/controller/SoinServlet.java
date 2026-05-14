package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.*;
import util.Registre;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SoinServlet extends HttpServlet {

    private static final Registre<Soin> registre = new Registre<>();
    public static Registre<Soin> getRegistre() { return registre; }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("supprimer".equals(req.getParameter("action"))) {
            supprimerSoin(req, resp);
            return;
        }

        String type      = req.getParameter("type");
        String dateStr   = req.getParameter("date");
        String idPatient = req.getParameter("idPatient");

        List<Soin> liste = registre.getAll();

        if (type != null && !type.isBlank())
            liste = liste.stream()
                    .filter(s -> s.getTypeSoin().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        if (dateStr != null && !dateStr.isBlank())
            liste = liste.stream()
                    .filter(s -> s.getDate().toLocalDate().toString().equals(dateStr))
                    .collect(Collectors.toList());
        if (idPatient != null && !idPatient.isBlank()) {
            int idP = Integer.parseInt(idPatient);
            liste = liste.stream()
                    .filter(s -> s.getPatient().getId() == idP)
                    .collect(Collectors.toList());
        }

        req.setAttribute("totalSoins", registre.taille());
        req.setAttribute("soins",     liste);
        req.setAttribute("type",      type);
        req.setAttribute("date",      dateStr);
        req.setAttribute("idPatient", idPatient);
        req.getRequestDispatcher("/soins.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("ajouter".equals(action)) {
            ajouterSoin(req, resp);
        } else if ("modifier".equals(action)) {
            modifierSoin(req, resp);
        }
    }

    private void ajouterSoin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int           id          = Integer.parseInt(req.getParameter("id"));
            String nom = req.getParameter("nom");
            LocalDateTime date        = LocalDateTime.parse(req.getParameter("date"));
            double        cout        = Double.parseDouble(req.getParameter("cout"));
            String        type        = req.getParameter("type");

            int     idPatient = Integer.parseInt(req.getParameter("idPatient"));
            int     idMedecin = Integer.parseInt(req.getParameter("idMedecin"));
            Patient patient   = PatientServlet.getRegistre().getById(idPatient)
                    .orElseThrow(() -> new Exception("Patient introuvable avec l'id " + idPatient));
            Medecin medecin   = MedecinServlet.getRegistre().getById(idMedecin)
                    .orElseThrow(() -> new Exception("Médecin introuvable avec l'id " + idMedecin));

            Soin soin;
            if ("ACTE_CHIRURGICAL".equals(type)) {
                String typeChirurgie  = req.getParameter("typeChirurgie");
                Salle salle   = PatientServlet.getRegistre().getById(idPatient)
                int    urgence  = Integer.parseInt(req.getParameter("niveauUrgence"));
                soin = new ActeChirurgical(id, nom, date,
                         medecin,patient, cout, salle, urgence, typeChirurgie);
            } else {
                String motif      = req.getParameter("motif");
                String ordonnance = req.getParameter("ordonnance");
                soin = new Consultation(id, nom, date, cout,
                        patient, medecin, motif, ordonnance);
            }

            registre.create(soin);

            patient.ajouterSoin(soin);
            medecin.ajouterSoin(soin);

            req.getSession().setAttribute("message", "Soin ajouté avec succès.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("soins");
    }

    private void modifierSoin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int    id          = Integer.parseInt(req.getParameter("id"));
            double cout        = Double.parseDouble(req.getParameter("cout"));

            Soin s = registre.getById(id)
                    .orElseThrow(() -> new Exception("Soin introuvable avec l'id " + id));
            s.setCout(cout);

            req.getSession().setAttribute("message", "Soin modifié.");
            req.getSession().setAttribute("messageType", "success");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("soins");
    }

    private void supprimerSoin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            registre.delete(id);
            req.getSession().setAttribute("message", "Soin supprimé.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("soins");
    }
}
