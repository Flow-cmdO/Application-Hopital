package model;

public class Patient extends Personne implements Soignable, Facturable {

    private int age;
    private String maladie;
    private String numeroDossier;

    // Constructeur
    public Patient(String nom, String prenom, int age, String maladie, String numeroDossier) {
        super(nom, prenom);
        this.age = age;
        this.maladie = maladie;
        this.numeroDossier = numeroDossier;
    }

    // Getters
    public int getAge() { return age; }
    public String getMaladie() { return maladie; }
    public String getNumeroDossier() { return numeroDossier; }

    // Setters
    public void setAge(int age) { this.age = age; }
    public void setMaladie(String maladie) { this.maladie = maladie; }

    // Implémentation de l'interface Soignable
    @Override
    public void soigner() {
        System.out.println("Le patient " + getNom() + " est en cours de soins pour : " + maladie);
    }

    // Implémentation de l'interface Facturable
    // La logique de calcul sera définie plus tard
    @Override
    public double calculerFacture() {
        return 0.0;
    }

    // Affichage lisible d'un patient
    @Override
    public String toString() {
        return "Patient{nom=" + getNom() + ", age=" + age +
                ", maladie=" + maladie + ", dossier=" + numeroDossier + "}";
    }
}