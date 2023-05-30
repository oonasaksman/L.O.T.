package harjoitustyo.dokumentit;

/**
 * Konkreettinen luokka uutisille.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 */
 
 import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
 
 
 public class Uutinen extends Dokumentti {
    
    /*
     * Attribuutit.
     *
     */
    
    private LocalDate päivämäärä;
    
    /*
     * Rakentaja.
     *
     */
     
     public Uutinen(int tunniste, LocalDate aika, String teksti){
        super(tunniste, teksti);
        päivämäärä(aika);
   }
  
    /*
     * Asettavat ja palauttavat aksessorit
     *
     */
     
   public LocalDate päivämäärä() {
      return päivämäärä;
   }

   public void päivämäärä(LocalDate aika) throws IllegalArgumentException{
      if (aika != null) {
         päivämäärä = aika;
      }
      else{
         throw new IllegalArgumentException();
      }
   }
   
   //Formatteri palauttaa toStringille päivämäärän stringinä oikeassa muodossa.
   public String format(LocalDate aika2) {
      DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("d.M.yyyy");
      String aika = dateFormatter2.format(aika2);
      return aika;
   }
   
   //ToString-metodin korvaus tulostaa uutisen oikeassa muodossa.
   public String toString(){
      String ss = super.toString().replace("///", "///" + format(päivämäärä) + "///");
      return ss;
   }
   
 }