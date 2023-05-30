package harjoitustyo.dokumentit;

/**
 * Konkreettinen luokka vitseille.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 */
 
 public class Vitsi extends Dokumentti {
    
    /*
     * Attribuutit.
     *
     */
     
     private String laji;
     
    /*
     * Rakentaja.
     *
     */
     
     public Vitsi(int tunniste,String tyyppi, String teksti) {
        super(tunniste, teksti);
        laji(tyyppi);
   }
   
    /*
     * Asettavat ja palauttavat aksessorit
     *
     */
     
   public String laji() {
      return laji;
   }

   public void laji(String tyyppi) {
      if (tyyppi != null && tyyppi != "") {
         laji = tyyppi;
      }
      else{
         throw new IllegalArgumentException();
      }
   }
   
   //ToString-metodin korvaus tulostaa vitsin oikeassa muodossa.
   @Override
   public String toString(){
      String ss = super.toString().replace("///", "///" + laji + "///");
      return ss;

   }
   
}