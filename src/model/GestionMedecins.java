package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

public class GestionMedecins {

    private List<Medecin> medecins;

    public GestionMedecins() {
        this.medecins = new ArrayList<>();
    }

    public void ajouterMedecin(Medecin medecin) {
        medecins.add(medecin);
    }

    // nombre de médecins par spécialité
    public Map<String, Long> nombreParSpecialite() {
        return medecins.stream()
                .collect(Collectors.groupingBy(Medecin::getSpecialite, Collectors.counting()));
    }

    // médecin avec le plus de patients
    public Medecin medecinLePlusOccupe() {
        return medecins.stream()
                .max(Comparator.comparingInt(m -> m.getPatients().size()))
                .orElse(null);
    }

    // nombre total de soins effectués
    public int nombreTotalSoins() {
        return medecins.stream()
                .mapToInt(m -> m.getSoins().size())
                .sum();
    }
}