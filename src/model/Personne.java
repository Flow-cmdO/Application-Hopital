package model;

public abstract class Personne {
    private String nom;
    private String prenom;
    private String id;

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Patient [nom=" + nom + ", prenom=" + prenom + "]";

    }
}
