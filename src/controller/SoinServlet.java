package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Consultation;
import util.Registre;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SoinServlet extends HttpServlet {

    private static final Registre<Consultation> registre = new Registre<>();
    public static Registre<Consultation> getRegistre() { return registre; }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if ("supprimer".equals(req.getParameter("action"))) {
            supprimerSoin(req, resp);
            return;
        }

        String motif = req.getParameter("motif");
        String date  = req.getParameter("date");
        String id    = req.getParameter("id");

        List<Consultation> liste = registre.getTous();

        if (motif != null && !motif.isBlank())
            liste = liste.stream().filter(s -> s.getMotif().toLowerCase().contains(motif.toLowerCase())).collect(Collectors.toList());
        if (date != null && !date.isBlank())
            liste = liste.stream().filter(s -> s.getDate() != null && s.getDate().contains(date)).collect(Collectors.toList());
        if (id != null && !id.isBlank())
            liste = liste.stream().filter(s -> s.getId().equalsIgnoreCase(id)).collect(Collectors.toList());

        req.setAttribute("totalSoins", registre.taille());
        req.setAttribute("soins", liste);
        req.setAttribute("motif", motif);
        req.setAttribute("date",  date);
        req.setAttribute("id",    id);
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
            String id          = req.getParameter("id");
            String date        = req.getParameter("date");
            String description = req.getParameter("description");
            String motif       = req.getParameter("motif");

            Consultation c = new Consultation(id, date, description, motif, null, null);
            registre.ajouter(c);

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
            String id          = req.getParameter("id");
            String description = req.getParameter("description");

            Consultation c = registre.get(id);
            c.setDescription(description);

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
            registre.supprimer(req.getParameter("id"));
            req.getSession().setAttribute("message", "Soin supprimé.");
            req.getSession().setAttribute("messageType", "warning");
        } catch (Exception e) {
            req.getSession().setAttribute("message", "Erreur : " + e.getMessage());
            req.getSession().setAttribute("messageType", "danger");
        }
        resp.sendRedirect("soins");
    }
}