package harjoitustyo.omalista;

//Otetaan käyttöön yliluokka.
import java.util.LinkedList;
import harjoitustyo.apulaiset.*;
import harjoitustyo.dokumentit.*;
import harjoitustyo.kokoelma.*;
import java.lang.Comparable;

/**
 * Konkreettinen luokka vitseille.
 * <p>
 * Olio-ohjelmoinnin perusteet II, kevät 2020.
 *
 * @version 0.1
 * @author Oona Saksman
 * @param <E>
 */
public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

   
   @SuppressWarnings({"unchecked"})
   public void lisää(E uusi) throws IllegalArgumentException{
      Comparable tempNode;
      //Jos lisättävä olio ei ole null ja se on comparablen instanssi.
      if(uusi != null && uusi instanceof java.lang.Comparable) {
         
         //Jos listassa ei ole vielä mitään, lisätään olio.
         if(this.isEmpty() == true) {
            this.add(uusi);
         }
         
         else {
            //Listaa käydään läpi, kunnes löydetään olio, jonka tunnus on 
            //pienempi kuin vertailtavan.
            
            for (int o = 0; o < this.size(); o++) { 
               tempNode = (Comparable)this.get(o);
               int palauta = tempNode.compareTo(uusi);
               if(palauta > 0) {
                  //Olio lisätään listaan.
                  this.add(o, uusi);
                  break;
               }
               //Olio lisätään listan loppuun.
               else if(o == this.size() - 1){
                  this.add(o + 1, uusi);
                  break;
               }
             
            }
         }
      }
      //Jos lisättävällä on null-arvo.
      else{
         throw new IllegalArgumentException("uusi on null");
      }
   }
}
