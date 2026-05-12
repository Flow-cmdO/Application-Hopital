package src;
public class ActeChirurgical extends Soin implements Urgence {
    private Salle salle;
    private int duree;
    private int urgence;

    public ActeChirurgical(String id, String date, String description, String type) {
        super(id, date);
        this.salle = null;
        this.duree = 0;
        this.urgence = 0;
    }

    public int getDuree() {
        return duree;
    }

    public int getNiveauUrgence() {
        return urgence;
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
        this.urgence = urgence;
    }

    @Override
    public String decrire() {
        return "ActeChirurgical{" +
                "date='" + getDate() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", duree='" + duree + "min" '\'' +
                ", urgence='" + urgence + '\'' + (salle != null ? ", salle=" + salle : "") +
                '}';
    }

    @Override
    public String toString() {
        return "ActeChirurgical{" +
                "id='" + getId() + '\'' +
                ", duree='" + duree + '\'' +
                ", urgence='" + urgence + '\'' +
                ", salle=" + salle +
                '}';
    }
}