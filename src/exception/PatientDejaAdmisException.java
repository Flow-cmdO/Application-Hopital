package exception;

public class PatientDejaAdmisException extends Exception {
    private final String nom;
    private final String numeroDossier;

    public PatientDejaAdmisException(String nom, String numeroDossier) {
        super("Le patient"+nom+"["+numeroDossier+"] est déjà admis.");
        this.nom = nom;
        this.numeroDossier = numeroDossier;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public String getNom() {
        return nom;
    }
}
