package harjoitustyo.dokumentit;

/**
 * Abstrakti juuriluokka dokumenteille.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 */
 
 
import java.util.LinkedList;
import harjoitustyo.apulaiset.*;
import harjoitustyo.omalista.*;
import harjoitustyo.kokoelma.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

 
public abstract class Dokumentti<T> implements Tietoinen<Dokumentti>, Comparable<Dokumentti>{
 
   /*
    * Julkiset luokkavakiot.
    *
    */
     
   /** Tietojen erotin kaikille saatavilla olevana alkioina. */
   public static final String EROTIN = "///";
     
   /*
    * Attribuutit.
    *
    */
     
   /** Dokumentin yksilöivä kokonaisluku */
   private int tunniste;
    
    /** Dokumentin teksti */
    private String teksti;
    
    /*
     * Rakentaja.
     *
     */
     
     public Dokumentti(int numero, String juttu) throws IllegalArgumentException {
        tunniste(numero);
        teksti(juttu);
     }
     
     
    /*
     * Getterit ja setterit.
     *
     */
     
   public int tunniste() {
      return tunniste;
   }

   public void tunniste(int tunnus) throws IllegalArgumentException{
      if (tunnus > 0) {
         tunniste = tunnus;
      }
      else{
         throw new IllegalArgumentException();
      }
   }
   
   public String teksti() {
      return teksti;
   }

   public void teksti(String juttu) throws IllegalArgumentException{
      if (juttu != null && juttu != "") {
         teksti = juttu;
      }
      else{
         throw new IllegalArgumentException("Error");
      }
   }
    
   //Metodi tarkistaa ovatko kaikki hakusanat-listassa olevat 
   //sanat dokumentissa jolla metodia on kutsuttu ja palauttaa toden boolean arvon jos näin on 
   //ja epätoden jos ei.
   public boolean sanatTäsmäävät(LinkedList<String> hakusanat)
   throws IllegalArgumentException{
   
      if(hakusanat != null && this != null){
         if(hakusanat.isEmpty() == false){
            int siirtyma = 0;
            int numbero = 0;

            //Jaetaan dokumentin teksti sanoihin.
            String [] tuo = this.teksti().split(" ");
            //Tehdään hakusanat-listan kokoinen taulukko.
            String[] taulukko3 = new String [hakusanat.size()];
            for(int h = 0; h < tuo.length; h++){

                for(int y = 0; y < hakusanat.size(); y++){

                  String tämä = hakusanat.get(y);
                  //Luuppi vertaa yhtä dokumentin sanaa kaikkiin hakusanoihin,
                  //sitten seuraavaa sanaa kaikkiin hakusanoihin ja niin edelleen.
                  if(tämä.equals(tuo[h])){
                  boolean onjo = false;

                  if(taulukko3[0] != null){

                     int m;
                     for(m = 0; m < numbero; m++){
                        //Jos sana löytyy taulukosta se on löydetty dokumentista jo kerran.
                        if(taulukko3[m].equals(tämä)){
                           onjo = true;
                        }

                     }
                  }
                  //Jos sana löytyi dokumentista ja sitä ei oltu vielä tallennettu taulukkoon.
                  if(onjo == false){
                     siirtyma++;
                     //Siirtyma kasvaa aina kun taulukko3:een lisätään hakusana. 
                     //Hakusana lisätään jos se löytyy dokumentista ja se ei ole vielä taulukossa.
                     //Kun siirtyma on hakusanat-listan kokoinen, kaikki sanat on löytyneet dokumentista
                     //ja voidaan palauttaa tosi arvo.
                     if(siirtyma == hakusanat.size()){
                        return true;
                     }
                     //Taulukkoon tallennetaan hakusana
                     taulukko3 [numbero] = tämä;
                     numbero++;
                  }
               }
            }

         }
            //Kun sana vaihtuu, muuttujat nollataan.
            siirtyma = 0;
            numbero = 0;

         }
         else{
            throw new IllegalArgumentException();
         }
      }
      else{
         throw new IllegalArgumentException();
      }
      //Muuten palautetaan epätosi.
      return false;
   }
   
   //Metodi poistaa dokumenteista käyttäjän antamat välimerkit, tiedostosta luetut
   //sulkusanat ja muuttaa isot kirjaimet pieniksi.
   public void siivoa(LinkedList<String> sulkusanat, String välimerkit)
   throws IllegalArgumentException{
      
      //Välimerkkien poistaminen.
      if(sulkusanat == null || välimerkit == null || this == null){
         throw new IllegalArgumentException();
      }
      if(sulkusanat.isEmpty() == true || ("".equals(välimerkit))){
         throw new IllegalArgumentException();
      }
      else{
         //Tallennetaan dokumentin teksti muuttujaan.
         String solmu0 = this.teksti();

         String valimerkit [] = välimerkit.split("");

            //Iteroidaan välimerkkien läpi. Jos sana sisältää välimerkin,
            //se korvataan tyhjällä.
            for(int i = 0; i < valimerkit.length; i++){
               if(solmu0.contains(valimerkit[i])){
                  solmu0 = solmu0.replace(valimerkit[i], "");
               }
            }
   
      //Isojen alkukirjainten muuttaminen pieniksi.
      solmu0 = solmu0.toLowerCase();
      
      //Sulkusanojen poistaminen.
      String teksti [] = solmu0.split(" ");
      String solmu3 = " ";
      String tekstiUusi = "";
      int offset2 = 0;
      int a;
      
      //Sanoista poistetaan välimerkit, jotta voidaan verrata niitä sulkusanojen kanssa.
      for(a = 0; a < teksti.length; a++){
         String [] yksdee2 = {".", "!", "\\?", ",", "\"", ":", ";"};
         for(int p = 0; p < yksdee2.length; p++){
            if(teksti[a].contains(yksdee2[p])){
               tekstiUusi = teksti[a].replace(yksdee2[p], "");
            }
            else{
               tekstiUusi = teksti[a];
            }
         }
         offset2 = 0;
         //Verrataan sanoja joista välimerkin on poistettu sulkusanoihin. 
         //Jos ne ovat samat liitetään alkuperäinen sana välimerkkeineen solmu3:een
         for(int i = 0; i < sulkusanat.size(); i++){
            if(!tekstiUusi.equals(sulkusanat.get(i))){
               offset2++;
               if(offset2 == sulkusanat.size()){
                  solmu3 = solmu3.concat(teksti[a]);
                  solmu3 = solmu3.concat(" ");
                  
              }
            }
         }
            
         }
         //Stringin lopusta poistetaan turhat välit.
         solmu3 = solmu3.trim();
         //Kaksi väliä korvataan yhdellä.
         solmu3 = solmu3.replace("  ", " ");
         //Jos /// perässä on väli se poistetaan.
         solmu3 = solmu3.replace("/// ", "///");
         //Dokumentille asetetaan uusi teksti
         this.teksti(solmu3);
      }
   }
   
   /*
    * Object-luokan metodien korvaukset.
    *
    */
    
    /*
     * Muodostaa dokumentin merkkijonoesityksen, joka koostuu tunnisteesta
     * erottimesta ja tekstistä.
     *
     * @return dokumentin merkkijonoesitys.
     */
     
   @Override
      
   public String toString() {
      //Hyödynnetään vakioita, jotta ohjelma olisi joustavampi.
      return tunniste + EROTIN + teksti;
   }
   
   public boolean equals(Dokumentti eka, Dokumentti toka){
       return eka.tunniste()== toka.tunniste();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Dokumentti<?> other = (Dokumentti<?>) obj;
      if (this.tunniste != other.tunniste) {
         return false;
      }
      if (!Objects.equals(this.teksti, other.teksti)) {
         return false;
      }
      return true;
   }
   
   
   
   @Override
   
   public int compareTo(Dokumentti tempNode) {
      // Tämän olion tunniste on suurempi.
      if (tempNode.tunniste() < this.tunniste()) {
         return 1;
      }
      // Olioilla on sama tunnus.
      else if (tempNode.tunniste() == this.tunniste()) {
         return 0;
      }
      // Verrattavan tunniste on suurempi.
      else {
         return -1;
      }
   }
   
   @Override
   public int hashCode() {
      int hash = 7;
      hash = 17 * hash + this.tunniste;
      hash = 17 * hash + Objects.hashCode(this.teksti);
      return hash;
   }
   
   
 }  