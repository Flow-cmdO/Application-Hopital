package model;
public class Consultation extends Soin {
    private String motif;
    private String ordonnance;
    private Medecin medecin;

    public Consultation(String id, String date, String description, String motif, String ordonnance, Medecin medecin) {
        super(id, date, description);
        this.motif = motif;
        this.ordonnance = ordonnance;
        this.medecin = medecin;
    }

    public String getMotif() {
        return motif;
    }

    public String getOrdonnance() {
        return ordonnance;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setOrdonnance(String o) {
        this.ordonnance = o;
    }

    @Override
    public String decrire() {
        return "Consultation du {" +
                ", date='" + getDate() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", motif='" + motif + '\'' +
                ", ordonnance='" + ordonnance + '\'' +
                ", medecin=" + medecin +
                '}';
    }

    @Override
    public String toString() {
        return "Consultation {" +
                "id='" + getId() + '\'' +
                ", motif='" + motif + '\'' +
                '}';
    }
}