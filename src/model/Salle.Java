package model;
import exception.CapaciteDepasseeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Salle implements Entite {
    private int id;
    private String numero;
    private String type;
    private int capacite;
    private final List<Lit> lits = new ArrayList<>();

    public Salle(int id, String numero, String type, int capacite) {
        this.id= id;
        this.numero = numero;
        this.type = type;
        this.capacite = capacite;
    }

    public String getNumero() {
        return numero;
    }

    public String getType() {
        return type;
    }

    public List<Lit> getLits() {
        return lits;
    }

    // Entite
    @Override
    public int getId() {return id;}

    @Override
    public String getLabel(){
        return "Salle "+numero+" ("+type+")";
    }

    public boolean isDisponible(){
        return lits.stream().anyMatch(lit -> !lit.isOccupe());
    }

    public List<Lit> getLitsLibres() {
        return lits.stream()
                .filter(lit -> !lit.isOccupe())
                .collect(Collectors.toList());
    }

    public List<Lit> getLitsOccupe() {
        return lits.stream()
                .filter(Lit::isOccupe)
                .collect(Collectors.toList());
    }

    public double getTauxOccupation(){
        if(lits.isEmpty()) return 0;
        return (double) getLitsOccupe().size()/lits.size();
    }

    public void ajouterLit(Lit lit) throws CapaciteDepasseeException {
        if (lits.size() > capacite) {
            throw new CapaciteDepasseeException(numero, capacite);
        }
        lits.add(lit);
    }


    @Override
    public String toString() {
        return "Salle [" +
                "numero='" + numero +
                ", type='" + type  +
                ", lits=" + lits +
                ']';
    }
}