package model;

import java.util.List;

public class Patient extends Personne implements Soignable , Facturable {

    public Patient(String nom, String prenom, int id) {
        super(nom, prenom, id);
    }

    @Override
    public double facturer(List<Soin> soins) {
        return 0;
    }

    @Override
    public void ajouterSoin(Soin soin) {

    }

    @Override
    public List<Soin> getSoins() {
        return List.of();
    }

    @Override
    public String getDossierMedical() {
        return "";
    }
}
