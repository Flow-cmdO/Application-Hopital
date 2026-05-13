package exception;

import java.time.LocalDateTime;

public class MedecinIndisponibleException extends Exception {
   private final String nom;
   private final LocalDateTime debut;
   private final LocalDateTime fin;

   public MedecinIndisponibleException(String nom, LocalDateTime debut, LocalDateTime fin) {
       super("Le Dr. "+nom+" n'est pas disponible du "+debut+" au "+fin+".");
       this.nom = nom;
       this.debut = debut;
       this.fin = fin;
   }

   public String getNom() {
       return nom;
   }
   public LocalDateTime getDebut() {
       return debut;
   }
   public LocalDateTime getFin() {
       return fin;
   }
}