package harjoitustyo.UI;

/**
 * Konkreettinen luokka kokoelmalle.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 */
 
import java.util.*;
import harjoitustyo.kokoelma.*;
import harjoitustyo.omalista.*;
import harjoitustyo.dokumentit.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
 
public class UI {
   
   //Julkinen luokkavakio.
   public static final String ERROR = "Error!";
   
   
   boolean jatka = true;
   
   //Metodi käyttäjän tervehtimiseen.
   public void moi(){
      System.out.println("Welcome to L.O.T.");
   }
    
   //Metodi ohjelman sulkemiseen. Saa parametreinä viestin, joka printataan ennen sulkemista.
   public void lopeta(String message){
      System.out.println(message);
      System.out.println("Program terminated.");
      System.exit(0);
   }
    
   //Metodi pyytää käyttäjältä käskyn, seuloo mikä käsky on kyseessä ja kutsuu sopivaa metodia.
   public void pääLuuppi(boolean echo, String tiedostoNimi, String sulku){
      Kokoelma ki = new Kokoelma();
      boolean uutinen = true;
      
      if(tiedostoNimi.contains("jokes")){
         uutinen = false;
      }
       
      //Kutsuu kokoelman lataa-metodia parametrinä tiedoston nimi.
      ki.lataa(tiedostoNimi); 
      //Kutsuu kokoelman lataa2-metodia, parametrina sulkusanatiedoston nimi.
      ki.lataa2(sulku);
      //Luodaan scanner-olio käskyjen lukemista varten.
      Scanner lukija = new Scanner(System.in);
 
      //Luuppi pyörii kunnes käyttäjä antaa quit-komennon.
      do{
      
         System.out.println("Please, enter a command:");
         String kasky = lukija.nextLine();
         String[] kaskyt = kasky.split(" ");
         int numero3 = 3;
         //Jos parametrinä saatu echo on arvoltaan tosi, printataan käsky uudelleen.
         if (echo){
            System.out.println(kasky);
         }
         
         if(kaskyt.length == 1 && kasky.equals("echo")){
            //Jos käsky on echo ja echo on jo tosi, 
            //echosta tulee epätosi ja mitään ei printata.
            if(echo){
               echo = false;
            }
            //Jos echo on epätosi, siitä tulee tosi ja ohjelma printtaa "echo".
            else{
               echo = true;
               System.out.println("echo");
            }
         }
         //Jos komento on reset, kutsutaan kokoelman kaikenPoisto-metodia,
         //joka tyhjentää linkitetyn listan. Tämän jälkeen tiedosto ladataan uudelleen.
         else if(kasky.equals("reset")){
            ki.kaikenPoisto();
            ki.lataa(tiedostoNimi);
         }
         //Jos komennossa on kaksi osaa joista ensimmäinen on polish, tallennetaan
         //jälkimmäinen osa jossa poistettavat merkin on ja kutsutaan kokoelman lataa3-metodia.
         else if(kaskyt[0].equals("polish") && kaskyt.length == 2){
            String valiMerkit = kaskyt[1];  
            ki.lataa3(valiMerkit);
         }
         //Jos käsky on print, iteroidaan dokumenttilistan läpi
         //ja printataan jokainen dokumenttti.
         else if(kaskyt.length == 1 && kaskyt[0].equals("print")){
            OmaLista om = ki.dokumentit();
            for(int k = 0; k < om.size(); k++){
               System.out.println(om.get(k));
            }
         }
         //Jos käsky on print ja jokin kokonaisluku.
         else if(kaskyt[0].equals("print") && kaskyt.length == 2
                  && kaskyt[1].matches("-?\\d+(.\\d+)?")
                  && !kaskyt[1].contains(".")){
            //Tallennetaan luku muuttujaan
            int numero2 = Integer.parseInt(kaskyt[1]);
            //Jos numeroa vastaava dokumentti löytyy, se printataan.
            if(ki.hae(numero2) != null){
               System.out.println(ki.hae(numero2));
            }
            //Jos dokumenttia sillä numerolla ei löydy.
            else{
               System.out.println(ERROR);
            }
         }
         //Jos käsky on find ja jokin muu sana/joitain muita sanoja.
         else if(kaskyt[0].equals("find") && kaskyt.length > 1){
            //Tehdään hakusanoista linkitetty lista.
            LinkedList<String> hakusanat = new LinkedList<>();
            for(int w = 1; w < kaskyt.length; w++){
               hakusanat.add(kaskyt[w]);
            }
            //Jos hakusanat ei ole tyhjä, kutsutaan kokoelman hae2-metodia.
            if(hakusanat != null){
               ki.hae2(hakusanat);
            }
        }
        //Jos komennon eka osa on add, siinä on enemmän kuin kaksi osaa ja se sisältää ///. 
        else if(kaskyt[0].equals("add") && kaskyt.length >= 2
                && kaskyt[1].contains("///")){
           //Jaetaan käsky ///-merkkien kohdista.
           String[] kaskyt2 = kasky.split("///"); //add tunnus,,, laji pv,,, teksti
           //Jaetaan tehdyn taulukon ensimmäinen osa vielä välin kohdalta.
           String [] kaskyt3 = kaskyt2[0].split(" "); //add,,, tunnus
           int tunnus = Integer.parseInt(kaskyt3[1]);
           
           //Jos annettu tunnus ei vastaa vielä yhtään dokumenttia.
           if(ki.hae(tunnus) == null){
              //Jos dokumentilla on päivämäärä ja uutinen on tosi.
              if(kaskyt2[1].matches("\\d+\\b.\\b\\d+\\b.\\b\\d+") && uutinen){
                 String pvm = kaskyt2[1];
                 
                 //Tallennetaan päivämäärä localdate muuttujaan datetimeformatterin avulla.
                 DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
                 LocalDate aika2 = LocalDate.parse(pvm, dateFormatter);

                 String juttu = kaskyt2[2];  
                 Uutinen rivi = new Uutinen(tunnus, aika2, juttu);
                 //Uutis-olio lisätään oikeaan kohtaan listaan.
                 ki.lisää(rivi);
              }
              //Jos dokumentilla ei ole päivämäärää ja uutinen ei ole tosi.
              else if(!kaskyt2[1].matches("\\d+\\b.\\b\\d+\\b.\\b\\d+") && !uutinen){
                 String tyyppi = kaskyt2[1];
                 String juttu = kaskyt2[2];
                 Vitsi rivi2 = new Vitsi(tunnus, tyyppi, juttu);
                 //Visti-olio lisätään oikeaan kohtaan listaan.
                 ki.lisää(rivi2);
              }
              //Printataan error-viesti, koska käyttäjä yrittää tallentaa uutista vitsien sekaan
              //tai toisinpäin.
              else{
                 System.out.println(ERROR);
              }
           }
           //Jos tunnuksella on jo löydettävissä dokumentti.
           else{
                System.out.println(ERROR);
             }
            
          }
          //Jos käskyssä on kaksi osaa joista ensimmäinen on remove ja toinen numero.
          else if(kaskyt[0].equals("remove") && kaskyt.length == 2
                  && kaskyt[1].matches("-?\\d+(.\\d+)?")){
             int kasky4 = Integer.parseInt(kaskyt[1]);
             //Jos annetulla tunnuksella on löydettävissä dokumentti, kutsutaan kokoelman
             //poista-metodia.
             if(ki.hae(kasky4) != null){
                ki.poista(kasky4);
             }
             //Jos poistettavaa ei ole prinataan error-viesti.
             else{
                System.out.println(ERROR);
             }
          }
                    
          //Luuppi pyörii, kunnes käyttäjä antaa "quit"-komennon.
          else if (kasky.equals("quit")){
             jatka = false;
          }
          //Jos käsky on jokin muu printataan error-viesti.
          else{
             System.out.println(ERROR);
          }
       }
       while (jatka);
   }
}