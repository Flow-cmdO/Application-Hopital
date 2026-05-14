package model;

import exception.MedecinIndisponibleException;

import java.time.LocalDateTime;
import java.util.List;

public interface Planifiable {
    void ajouterCreneau(LocalDateTime debut, LocalDateTime fin) throws MedecinIndisponibleException;
    List<LocalDateTime[]> getPlanning();
    boolean estDisponible(LocalDateTime debut, LocalDateTime fin);
}
