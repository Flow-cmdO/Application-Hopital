package model;
import java.util.ArrayList;
import java.util.List;

public class Salle {
    private String numero;
    private String type;
    private List<Lit> lits;

    public Salle(String numero, String type) {
        this.numero = numero;
        this.type = type;
        this.lits = new ArrayList<>();
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

    public boolean isDisponible() {
        for (Lit lit : lits) {
            if (lit.isDisponible()) {
                return true;
            }
        }
        return false;
    }

    public void ajouterLit(Lit lit) {
        lits.add(lit);
    }

    @Override
    public String toString() {
        return "Salle{" +
                "numero='" + numero + '\'' +
                ", type='" + type + '\'' +
                ", lits=" + lits +
                '}';
    }
}