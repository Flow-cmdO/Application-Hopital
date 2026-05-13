package model;

import java.time.LocalDateTime;

public class ActeChirurgical extends Soin implements Urgence {
    private Salle salle;
    private String typeChirurgie;
    private int urgence;

    public ActeChirurgical(String id, LocalDateTime date, Medecin medecin, Patient patient, double cout, Salle salle, int urgence, String typeChirurgie) {
        super(id, date, cout, patient, medecin);
        if (urgence < 1 || urgence > 5) {
            throw new IllegalArgumentException("Le niveau d'urgence doit être compris entre 1 et 5.");
        }
        this.salle = salle;
        this.urgence = urgence;
        this.typeChirurgie = typeChirurgie;
    }

    public String getTypeChirurgie() {
        return typeChirurgie;
    }


    public Salle getSalle() {
        return salle;
    }
 
    public void setSalle(Salle salle) {
        this.salle = salle;
    }
 
    public void setTypeChirurgie(String typeChirurgie) {
        this.typeChirurgie = typeChirurgie;
    }
 
    public void setUrgence(int urgence) {
        if (urgence < 1 || urgence > 5) {
            throw new IllegalArgumentException("Le niveau d'urgence doit être compris entre 1 et 5.");
        }
        this.urgence = urgence;
    }

    @Override
    public String getTypeSoin(){
        return "Acte chirurgical";
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
                ", type='" + typeChirurgie +
                ", urgence='" + urgence +
                (salle != null ? ", salle=" + salle :"")+
                (estPrioritaire() ? ", PRIORITAIRE" : "")+
                "]";

    }
}