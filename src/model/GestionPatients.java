package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestionPatients {

    private List<Patient> patients;

    public GestionPatients() {
        this.patients = new ArrayList<>();
    }

    // Tri par nom
    public List<Patient> trierParNom(boolean croissant) {
        List<Patient> trie = new ArrayList<>(patients);
        trie.sort(Comparator.comparing(Patient::getNom));
        if (!croissant) Collections.reverse(trie);
        return trie;
    }

    // Tri par age
    public List<Patient> trierParAge(boolean croissant) {
        List<Patient> trie = new ArrayList<>(patients);
        trie.sort(Comparator.comparingInt(Patient::getAge));
        if (!croissant) Collections.reverse(trie);
        return trie;
    }

    // nombre de patients par statut
    public Map<Patient.Statut, Long> nombreParStatut() {
        return patients.stream().collect(Collectors.groupingBy(Patient::getStatut, Collectors.counting()));
    }

    // âge moyen des patients
    public double ageMoyen() {
        return patients.stream().mapToInt(Patient::getAge).average().orElse(0);
    }

    // nombre total de soins
    public int nombreTotalSoins() {
        return patients.stream().mapToInt(p -> p.getSoins().size()).sum();
    }

}