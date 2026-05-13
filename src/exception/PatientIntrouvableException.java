package exception;

public class PatientIntrouvableException extends Exception {
    private final int id;

    public PatientIntrouvableException(int id){
        super("Le patient avec l'id [" + id + "] n'existe pas.");
        this.id=id;
    }

    public int getId(){
        return id;
    }
}
