package model;

public abstract class Personne implements Entite {
    private String nom;
    private String prenom;
    private int id;

    public Personne(String nom, String prenom, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
    }

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
    public void setId(int id) {this.id = id;}

    // Entite
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return nom+" "+prenom;
    }

    @Override
    public String toString() {
        return "Personne [nom=" + nom + ", prenom=" + prenom + "]";

    }
}