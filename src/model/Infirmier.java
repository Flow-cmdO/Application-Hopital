package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Infirmier extends Personnel implements Planifiable {
    private String specialite;
    private final List<LocalDateTime[]> planning = new ArrayList<>();

    public Infirmier(String nom, String prenom, int id, String matricule, String service, String specialite) {
        super(nom, prenom, id, matricule, service);
        this.specialite = specialite;
    }

    public String getSpecialite() {
        return specialite;
    }
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    // Planifiable
    @Override
    public void ajouterCreneau(LocalDateTime debut, LocalDateTime fin) {
        if  (!debut.isBefore(fin) || debut.isEqual(fin)) {
            throw new IllegalArgumentException("Créaneau invalide : la date du début doit être antérieure à la date de fin.");
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

    @Override
    public String toString(){
        return "Infirmier ["+getNom()+" "+ getPrenom()+", "+getMatricule()+", "+getService()+"]\n"
                + "service= "+getService()+", spécialité= "+ specialite+"\n";
    }
}


