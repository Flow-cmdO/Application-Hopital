package model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Personne implements Soignable, Facturable {

    private int age;
    private String maladie;
    private String numeroDossier;

    private List<Soin> soins;

    // Constructeur
    public Patient(String nom, String prenom, int id, int age, String maladie, String numeroDossier) {
        super(nom, prenom, id);
        this.age = age;
        this.maladie = maladie;
        this.numeroDossier = numeroDossier;
        this.soins = new ArrayList<>();
    }

    // Getters
    public int getAge() { return age; }
    public String getMaladie() { return maladie; }
    public String getNumeroDossier() { return numeroDossier; }

    // Setters
    public void setAge(int age) { this.age = age; }
    public void setMaladie(String maladie) { this.maladie = maladie; }

    // Implémentation de Soignable
    @Override
    public void ajouterSoin(Soin soin) {
        soins.add(soin);
    }

    @Override
    public List<Soin> getSoins() {
        return soins;
    }

    @Override
    public String getDossierMedical() {
        return "Dossier n°" + numeroDossier + " - Maladie : " + maladie;
    }

    // Implémentation de Facturable
    @Override
    public double calculerFacture() {
        return 0.0;
    }

    // Affichage lisible d'un patient
    @Override
    public String toString() {
        return "Patient{nom=" + getNom() + ", age=" + age + ", maladie=" + maladie + ", dossier=" + numeroDossier + "}";
    }
}