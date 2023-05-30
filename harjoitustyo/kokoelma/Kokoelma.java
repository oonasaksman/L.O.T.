package harjoitustyo.kokoelma;

/**
 * Konkreettinen luokka kokoelmalle.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 */
 

 
import java.util.LinkedList;
import java.io.File;
import java.time.format.DateTimeFormatter;
import harjoitustyo.apulaiset.*;
import java.time.LocalDate;
import java.util.Scanner;
import harjoitustyo.dokumentit.*;
import harjoitustyo.omalista.*;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter; 
import java.lang.Comparable;
  
 public class Kokoelma extends Object implements Kokoava<Dokumentti>{
 
   public static final String ERROR = "Error!";
    
   //Sisältää viitteet kokoelmaan kuuluviin dokumenttiolioihin.
   private harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti> dokumentit;
    
   private LinkedList<String> sulkusanat;
   
   //Oletusrakentaja, jossa dokumentit-attribuuttiin liitetään 
   //rakentajassa luotu tyhjä listaolio.
   public Kokoelma() {
      this.dokumentit = new OmaLista<>();
      this.sulkusanat = new LinkedList<>();
   }

  @SuppressWarnings ({"unchecked"})
  public harjoitustyo.omalista.OmaLista<harjoitustyo.dokumentit.Dokumentti> dokumentit(){
     return dokumentit;
  }
    
    //Metodi lukee dokumentit tiedostosta ja tallentaa ne kutsumalla lisää-metodia.
    public void lataa(String tiedostonNimi) {
       Scanner tiedostonLukija = null;
       try {
          //Luodaan tiedostoon liittyvä olio ja liitetään lukija tiedostoon.
          File tiedosto = new File(tiedostonNimi);
          tiedostonLukija = new Scanner(tiedosto);
          //Luetaan tiedostoa rivi kerrallaan ja käsitellään rivi. 
          while (tiedostonLukija.hasNextLine()) {
          
             Uutinen rivi;
             Vitsi rivi2;
             //Tarkistetaan onko tiedostossa vitsejä vai uutisia ja luodaan
             //oliot sen perusteella.
             if(tiedostonNimi.contains("jokes")) {
                String s = tiedostonLukija.nextLine();
                String[] osat = s.split("///");
                int tunnus = Integer.parseInt(osat[0]);
                String tyyppi = osat[1];
                String juttu = osat[2];
                rivi2 = new Vitsi(tunnus, tyyppi, juttu);  
                //Olio lisätään oikeaan kohtaan listaan.
                if(hae(tunnus) == null){
                   dokumentit.lisää(rivi2);
                }
               
             }
             else if(tiedostonNimi.contains("news")) {
                String s = tiedostonLukija.nextLine();
                String[] osat = s.split("///");
                int tunnus = Integer.parseInt(osat[0]);
                String date = osat[1];

                //Tallennetaan päivämäärä datetimeformatterin avulla.
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
                LocalDate aika2 = LocalDate.parse(date, dateFormatter);

                String juttu = osat[2];  
                rivi = new Uutinen(tunnus, aika2, juttu);
                //Olio lisätään oikeaan kohtaan listaan.
                dokumentit.lisää(rivi);
             }
          }
          //Suljetaan tiedostonlukija.
          tiedostonLukija.close();
      }
      catch (FileNotFoundException | NumberFormatException e) {
      //Suljetaan tiedostonlukija tarvittaessa ja palautetaan tieto virheestä.

         tiedostonLukija.close();
         String missing2 = "Missing file!";
         System.out.println("Missing file!");
         System.out.println("Program terminated.");
         System.exit(0);
         
      }
   }
    
   //Metodi lukee ja tallentaa tiedostosta sulkusanat.
   public void lataa2(String sulku){
      Scanner tiedostonLukija = null;

      try{
       
         File tiedosto2 = new File(sulku);
         
         tiedostonLukija = new Scanner(tiedosto2);
         
         while (tiedostonLukija.hasNextLine()) {
            String s2 = tiedostonLukija.nextLine();
            sulkusanat.add(s2);
         }
      }
      //Jos tiedostoa ei löydy, printataan virheviesti ja ohjelma lopetetaan.
      catch (FileNotFoundException n){
         System.out.println("Missing file!");
         System.out.println("Program terminated.");
         System.exit(0);
      }
   }


   //Metodi iteroi dokumenttien läpi ja kutsuu niillä siivoa-metodia.
   public void lataa3(String valimerkit){
      for(int v = 0; v < dokumentit.size(); v++){
         Dokumentti dokki = dokumentit.get(v);
         dokki.siivoa(sulkusanat, valimerkit);
      }
      
   }
   
   //Metodi iteroi dokumenttien läpi ja vertaa parametrinä saatua numeroa ja dokumenttien
   //tunnistetta. Jos luvut ovat samat palautetaan kyseinen dokumentti-olio.
   public Dokumentti hae(int number) throws IllegalArgumentException{
      
      if(dokumentit != null) {
         for (int x = 0; x < dokumentit.size(); x++) { 
            Dokumentti takku = dokumentit.get(x);
            if(number == takku.tunniste()) {
               return takku;
            }

         }
            
      }
      //Jos dokumenttilista on tyhjä.
      else{
         throw new IllegalArgumentException(ERROR);
      }
      return null;   
   }
   
   //Metodi kutsuu kaikilla dokumentit listan olioilla sanatTäsmäävät-metodia
   //hakusanat-lista parametrinä ja printtaa dokumentin tunnisteen jos 
   //palautettu arvo on tosi.
   public void hae2(LinkedList<String> hakusanat){
      //Jos hakusanat ja dokumentit -listat eivät ole tyhjiä
      if(hakusanat.isEmpty() == false && dokumentit.isEmpty() == false){
         for(int s = 0; s < dokumentit.size(); s++){
            Dokumentti solmu = dokumentit.get(s);
            if(solmu != null && hakusanat != null){
               //Jos sanatTäsmäävät palauttaa toden arvon, printataan kyseisen dokumentin tunniste.
               if(solmu.sanatTäsmäävät(hakusanat) == true){
                  System.out.println(solmu.tunniste());
               }
            }
         }
      }
      //Jos jompi kumpi listoista on tyhjä printataan error-viesti.
      else{
         System.out.println(ERROR);
      }
   }
   
   //Metodi kutsuu OmaLista-luokan lisää-metodia parametrinä lisättävä dokumentti.
   public void lisää (Dokumentti uusi) throws IllegalArgumentException {
      if(uusi != null && uusi instanceof java.lang.Comparable){
         if(hae(uusi.tunniste()) == null){
            dokumentit.lisää(uusi);
         }
         //Heittää virheen jos dokumentti samalla tunnisteella on jo listassa.
         else{
            throw new IllegalArgumentException(ERROR);
         }
      }
      //Heittää virheen jos lisättävä dokumentti on null tai ei ole comparablen instanssi.
      else{
         throw new IllegalArgumentException(ERROR);
      }
   }
   
   //Metodi poistaa kaikki oliot listasta iteroimalla sen läpi lopusta alkuun
   //ja kutsumalla poista-metodia.
   public void kaikenPoisto() {
      int numeroo = dokumentit.size();
      for(int c = numeroo - 1; c >= 0; c--){
         
         Dokumentti tempNode2 = (Dokumentti)dokumentit.get(c);
         poista(tempNode2.tunniste());
      }
      
   }
  //Poistaa dokumentin jonka tunniste on sama kuin numero joka annetaan parametrina.
   public void poista (int tunnis) throws  IllegalArgumentException {
         dokumentit.remove(hae(tunnis));
   }
   
}

 