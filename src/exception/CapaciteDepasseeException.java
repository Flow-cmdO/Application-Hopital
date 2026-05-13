package exception;

public class CapaciteDepasseeException extends Exception {
    private final String nomSalle;
    private final int capacite;

    public CapaciteDepasseeException(String nomSalle, int capacite) {
        super("La salle ["+nomSalle+"] à atteint sa capacité maximal ("+capacite+") de patients.");
        this.nomSalle = nomSalle;
        this.capacite = capacite;
    }

    public String getNomSalle() {
        return nomSalle;
    }
    public int getCapacite() {
        return capacite;
    }
}
