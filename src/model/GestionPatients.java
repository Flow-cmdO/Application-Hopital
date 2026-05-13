package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
}