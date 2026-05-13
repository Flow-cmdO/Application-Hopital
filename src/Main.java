package view;

import model.*;
import util.Registre;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        // ─── Création des médecins ─────────────────────────────────────────
        Medecin m1 = new Medecin("Dupont", "Jean", 1, "MAT001", "Cardiologie", 12345, "Cardiologue");
        Medecin m2 = new Medecin("Martin", "Sophie", 2, "MAT002", "Urgences", 67890, "Urgentiste");

        // ─── Création des patients ─────────────────────────────────────────
        Patient p1 = new Patient("Bernard", "Luc", 1, 45, "Hypertension", "DOS001");
        Patient p2 = new Patient("Petit", "Marie", 2, 32, "Fracture", "DOS002");
        Patient p3 = new Patient("Durand", "Paul", 3, 60, "Diabète", "DOS003");

        // ─── Création des soins ────────────────────────────────────────────
        Consultation c1 = new Consultation("S001", "2024-01-15", "Douleurs thoraciques", "Contrôle tension", null, m1);
        Consultation c2 = new Consultation("S002", "2024-01-16", "Douleur bras droit", "Fracture suspectée", null, m2);

        ActeChirurgical a1 = new ActeChirurgical("S003", "2024-01-17", "Pose plâtre bras droit");
        a1.setDuree(45);
        a1.setUrgence(2);

        // ─── Ajout soins aux patients ──────────────────────────────────────
        p1.ajouterSoin(c1);
        p2.ajouterSoin(c2);
        p2.ajouterSoin(a1);

        // ─── Ajout patients au médecin ─────────────────────────────────────
        m1.ajouterPatient(p1);
        m2.ajouterPatient(p2);
        m2.ajouterPatient(p3);

        // ─── Planning médecin ──────────────────────────────────────────────
        try {
            m1.ajouterCreneau(
                    LocalDateTime.of(2024, 1, 15, 9, 0),
                    LocalDateTime.of(2024, 1, 15, 12, 0)
            );
            m2.ajouterCreneau(
                    LocalDateTime.of(2024, 1, 15, 8, 0),
                    LocalDateTime.of(2024, 1, 15, 20, 0)
            );
        } catch (Exception e) {
            System.out.println("Erreur planning : " + e.getMessage());
        }

        // ─── Registres ────────────────────────────────────────────────────
        Registre<Medecin> registreMedecins = new Registre<>();
        Registre<Patient> registrePatients = new Registre<>();

        registreMedecins.ajouter(m1);
        registreMedecins.ajouter(m2);
        registrePatients.ajouter(p1);
        registrePatients.ajouter(p2);
        registrePatients.ajouter(p3);

        // ─── Affichage ────────────────────────────────────────────────────
        System.out.println("=== MÉDECINS ===");
        registreMedecins.getTous().forEach(System.out::println);

        System.out.println("\n=== PATIENTS ===");
        registrePatients.getTous().forEach(System.out::println);

        System.out.println("\n=== DOSSIERS MÉDICAUX ===");
        System.out.println(p1.getDossierMedical());
        System.out.println(p2.getDossierMedical());

        System.out.println("\n=== FACTURATION ===");
        System.out.println("Facture " + p2.getLabel() + " : " + p2.facturer(p2.getSoins()) + "€");

        System.out.println("\n=== FILTRAGE (patients de Dr. Martin) ===");
        registrePatients.filtrer(p -> m2.getPatients().contains(p))
                .forEach(System.out::println);

        System.out.println("\n=== TRI (patients par nom) ===");
        registrePatients.trier((a, b) -> a.getNom().compareTo(b.getNom()))
                .forEach(System.out::println);
    }
}