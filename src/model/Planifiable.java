package model;

import java.time.LocalDateTime;
import java.util.List;

public interface Planifiable {
    void planifier(LocalDateTime debut, LocalDateTime fin);
    List<LocalDateTime[]> getPlanning();
    boolean estDisponible(LocalDateTime debut, LocalDateTime fin);
}
