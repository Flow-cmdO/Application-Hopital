package model;

import util.Registre;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        Medecin m1 = new Medecin("Dupont", "Jean", 1, "MAT001", "Cardiologie", 12345, "Cardiologue");
        Medecin m2 = new Medecin("Martin", "Sophie", 2, "MAT002", "Urgences", 67890, "Urgentiste");

        Patient p1 = new Patient("Bernard", "Luc", 1, 45, "Hypertension", "DOS001");
        Patient p2 = new Patient("Petit", "Marie", 2, 32, "Fracture", "DOS002");
        Patient p3 = new Patient("Durand", "Paul", 3, 60, "Diabète", "DOS003");

        Salle salle1 = new Salle(1, "Bloc A");

        Consultation c1 = new Consultation(1, "Consultation cardio", LocalDateTime.of(2026, 2, 23, 10, 0), 182, p3, m1, "Douleurs thoraciques", "192IS");
        Consultation c2 = new Consultation(2, "Consultation urgence", LocalDateTime.of(2026, 2, 23, 14, 0), 282, p2, m2, "Douleur bras droit", "1IE2Z");

        ActeChirurgical a1 = new ActeChirurgical(3, "Pose plâtre bras droit", LocalDateTime.of(2026, 2, 23, 16, 0), m1, p2, 350, salle1, 2, "Orthopédie");

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

        registreMedecins.create(m1);
        registreMedecins.create(m2);
        registrePatients.create(p1);
        registrePatients.create(p2);
        registrePatients.create(p3);

        // ─── Affichage ────────────────────────────────────────────────────
        System.out.println("=== MÉDECINS ===");
        registreMedecins.getAll().forEach(System.out::println);

        System.out.println("\n=== PATIENTS ===");
        registrePatients.getAll().forEach(System.out::println);

        System.out.println("\n=== DOSSIERS MÉDICAUX ===");
        System.out.println(p1.getDossierMedical());
        System.out.println(p2.getDossierMedical());

        System.out.println("\n=== FACTURATION ===");
        System.out.println("Facture " + p2.getLabel() + " : " + p2.facturer(p2.getSoins()) + "€");

        System.out.println("\n=== FILTRAGE (patients de Dr. Martin) ===");
        registrePatients.filtrer(p -> m2.getPatients().contains(p))
                .forEach(System.out::println);
    }
}
