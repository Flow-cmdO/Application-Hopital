package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Medecin extends Personnel implements Soignable, Planifiable {
    private int numOrdre;
    private String specialite;
    private final List<Patient> patients = new ArrayList();

    // Début et fin d'un créaneau (Planifiable)
    private final List<LocalDateTime[]> planning = new ArrayList<>();
    // Soin donné par Medecin (Soin)
    private final List<Soin> soins = new ArrayList<>();

    public Medecin(String nom, String prenom, int id, String matricule, String service, int numOrdre, String specialite) {
        super(nom, prenom, id, matricule, service);
        this.numOrdre = numOrdre;
        this.specialite = specialite;
    }

    public int getNumOrdre(){
        return numOrdre;
    }
    public void setNumOrdre(int numOrdre){
        this.numOrdre = numOrdre;
    }
    public String getSpecialite() {
        return specialite;
    }
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void ajouterPatient(Patient p){
        patients.add(p);
    }

    public void supprimerPatient(Patient p){
        patients.remove(p);
    }

    public List<Patient> getPatients(){
        return new ArrayList<>(patients);
    }

    @Override
    public List<LocalDateTime[]> getPlanning() {
        return planning;
    }

    @Override
    public void ajouterCreneau(LocalDateTime debut, LocalDateTime fin) {

    }

    @Override
    public boolean estDisponible(LocalDateTime debut, LocalDateTime fin) {
        return false;
    }

    @Override
    public void ajouterSoin(Soin soin) {

    }

    @Override
    public List<Soin> getSoins() {
        return new ArrayList<>(soins);
    }

    @Override
    public String getDossierMedical() {
        return null;
    }

    @Override
    public String toString(){
        return "Medecin ["+getNom()+" "+getPrenom()+", "+getMatricule()+", "+getService()+"]\n"
                + "Spécialité= "+specialite+", N° odre= "+numOrdre;
    }
}

