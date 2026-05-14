package model;

import exception.MedecinIndisponibleException;

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

    // Gestion patient
    public void ajouterPatient(Patient p) {
        if (p==null) { throw new IllegalArgumentException("Le patient ne peut pas être vide."); }
        patients.add(p);
    }

    public void supprimerPatient(Patient p){
        patients.remove(p);
    }

    public List<Patient> getPatients(){
        return new ArrayList<>(patients);
    }

    // Planifiable
    @Override
    public void ajouterCreneau(LocalDateTime debut, LocalDateTime fin) throws MedecinIndisponibleException {
            if (!debut.isBefore(fin) || debut.isEqual(fin)) {
                throw new MedecinIndisponibleException(getLabel(), debut, fin);
            }
            planning.add(new LocalDateTime[]{debut, fin});
    }

    @Override
    public List<LocalDateTime[]> getPlanning() {
        return new ArrayList<>(planning);
    }

    @Override
    public boolean estDisponible(LocalDateTime debut, LocalDateTime fin) {
        return planning.stream().noneMatch(creneau ->
                debut.isBefore(creneau[1]) && fin.isAfter(creneau[0]));
    }

    // Soignable
    @Override
    public void ajouterSoin(Soin soin) {
        if (soins.isEmpty()) {
            throw new IllegalArgumentException("Le soin n'existe pas.");
        }
        soins.add(soin);
    }

    @Override
    public List<Soin> getSoins() {
        return new ArrayList<>(soins);
    }

    @Override
    public String getDossierMedical() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Dossier Medical - Dr. ")
                .append(getNom()).append(" ").append(getPrenom()).append("]")
                .append("\nN°").append(getMatricule())
                .append("\nService=").append(getService())
                .append("\nSpécialité=").append(specialite);

        if (patients.isEmpty()) {
            sb.append("Aucun patient pris en charge par ce medecin.");
        } else {
            sb.append("\nPatient(s) suivis : ");
            patients.forEach(p-> sb.append("- ").append(p).append("\n"));
        }
        if (soins.isEmpty()) {
            sb.append("Aucun soin autorisé par ce medecin.");
        } else {
            sb.append("\nSoin(s) autorisé : ");
            soins.forEach(s -> sb.append("- ").append(s).append("\n"));
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        return "Medecin ["+getNom()+" "+getPrenom()+", "+getMatricule()+", "+getService()+"]\n"
                + "Spécialité= "+specialite+", N° odre= "+numOrdre;
    }
}

