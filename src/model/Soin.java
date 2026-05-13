package model;

import java.time.LocalDateTime;

public abstract class Soin {
    private String id;
    private LocalDateTime date;
    private double cout;
    private Patient patient;
    private Medecin medecin;

    public Soin(String id, LocalDateTime  date, double cout, Patient patient, Medecin medecin) {
        this.id = id;
        this.date = date;
        this.cout = cout;
        this.patient = patient;
        this.medecin = medecin;
    }

    public String getId() {
        return id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public double getCout() {
        return cout;
    }
    public void setCout(double cout) {
        this.cout = cout;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Medecin getMedecin() {
        return medecin;
    }
    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }


    public abstract String getTypeSoin();

    @Override
    public String toString() {
        return "Soin [" +
                "id='" + id +
                ", date='" + date +
                ", cout=" + cout +
                ", patient=" + patient.getLabel()
                + ", medecin=" + medecin.getLabel()+
                "]";
    }

    
}
