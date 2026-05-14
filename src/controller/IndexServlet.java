package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Salle;

import java.io.IOException;

public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("totalPatients", PatientServlet.getRegistre().taille());
        req.setAttribute("totalMedecins", MedecinServlet.getRegistre().taille());
        req.setAttribute("totalSoins",    SoinServlet.getRegistre().taille());
        req.setAttribute("totalSalles",   SalleServlet.getRegistre().taille());
        req.setAttribute("sallesDisponibles",
                SalleServlet.getRegistre().getAll().stream()
                        .filter(Salle::isDisponible)
                        .count()
        );

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
