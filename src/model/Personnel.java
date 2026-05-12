package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Personnel extends Personne {
    private String matricule;
    private String service;

    public Personnel(String nom, String prenom, int id, String matricule, String service) {
        super(nom, prenom, id);
        this.matricule = matricule;
        this.service = service;
    }

    public String getMatricule() {
        return matricule;
    }
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }

   @Override
    public String toString() {
        return "Personnel[" + "matricule=" + matricule + ", nom=" + getNom() +", " +
                "prenom=" + getPrenom() +", service=" + service + "]";
   }
}

