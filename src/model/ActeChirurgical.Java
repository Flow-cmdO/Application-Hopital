package model;

import java.time.LocalDateTime;

public class ActeChirurgical extends Soin implements Urgence {
    private Salle salle;
    private int duree;
    private int urgence;

    public ActeChirurgical(String id, LocalDateTime date, String description, Medecin medecin, Patient patient, double cout, Salle salle, int urgence, int duree) {
        super(id, date, description, cout, patient, medecin);
        if (urgence < 1 || urgence > 5) {
            throw new IllegalArgumentException("Le niveau d'urgence doit être compris entre 1 et 5.");
        }
        this.salle = salle;
        this.duree = duree;
        this.urgence = urgence;
    }

    public int getDuree() {
        return duree;
    }


    public Salle getSalle() {
        return salle;
    }
 
    public void setSalle(Salle salle) {
        this.salle = salle;
    }
 
    public void setDuree(int duree) {
        this.duree = duree;
    }
 
    public void setUrgence(int urgence) {
        if (urgence < 1 || urgence > 5) {
            throw new IllegalArgumentException("Le niveau d'urgence doit être compris entre 1 et 5.");
        }
        this.urgence = urgence;
    }


    //Urgence
    @Override
    public int getNiveauUrgence() {
        return urgence;
    }

    @Override
    public boolean estPrioritaire() {
        if (urgence < 1 || urgence > 5) {
            throw new IllegalArgumentException("Le niveau d'urgence doit être compris entre 1 et 5.");
        }
        return urgence <= 2;
    }

    @Override
    public String toString() {
        return "Chirurgie [" +
                ", date='" + getDate() +
                ", description='" + getDescription() +
                ", duree='" + duree + "min\n" +
                ", urgence='" + urgence +
                (salle != null ? ", salle=" + salle :"")+
                (estPrioritaire() ? ", PRIORITAIRE" : "")+
                "]";

    }
}