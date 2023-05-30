
/**
 * L.O.T:n ajoluokka.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 */

import harjoitustyo.UI.*;
import harjoitustyo.kokoelma.*;
import harjoitustyo.omalista.*;

public class Oope2HT {
   public static void main(String[] args) {
      
      UI käyttis = new UI();
      //Kutsutaan UI-luokan moi-metodia, jolla tervehditään käyttäjää.
      käyttis.moi();
      String argumentit = "Wrong number of command-line arguments!";
      
      //Jos komentoriviargumentteja on väärä määrä.
      if(args.length > 3 || args.length < 2){
         käyttis.lopeta(argumentit);
      }
      //Jos komentoriviargumenteja on kolme, niisyä viimeisen pitää olla echo,
      //tai ohjelma suljetaan.
      if(args.length == 3){
         if(!args[2].contains("echo")){
            käyttis.lopeta(argumentit);
         }
      }
      String arggi = args[0];
      String arggiSulku = args[1];

      boolean echo = false;
      //Jos tiedostonnimi ei sisällä "jokes", eikä "news", printataan virheviesti
      //ja suljetaan ohjelma.
      //Lopeta-metodille annetaan parametreina aina Stringi
      //, joka printataan ennen ohjelman lopettamista
      if(!arggi.contains("jokes") && !arggi.contains("news")){
         String poissa = "Missing file!";
         käyttis.lopeta(poissa);
      }
      //Jos komentoriviargumentteja on kolme ja viimeinen niistä on "echo"
      //,on boolean echo totta.
      if(args.length == 3){
         String arggiEcho = args[2];
         echo = arggiEcho.equals("echo");
      }
      //Pääluupille annetaan parametreilla onko echo päällä ja tiedostojen nimet.
      käyttis.pääLuuppi(echo, arggi, arggiSulku);
      //Viesti, joka printataan, kun pääluuppi loppuu.
      System.out.println("Program terminated.");
   }
}
