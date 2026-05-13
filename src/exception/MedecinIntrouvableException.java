package exception;

public class MedecinIntrouvableException extends Exception {
    private final int id;

    public MedecinIntrouvableException(int id){
        super("Le médecin avec l'id ["+id+"] n'existe pas.");
        this.id=id;
    }

    public int getId(){
        return id;
    }
}