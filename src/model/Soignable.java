package model;

import java.util.List;

public interface Soignable {
    public void ajouterSoin(Soin soin);
    List<Soin> getSoins();
    String getDossierMedical();
}
