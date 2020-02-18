/*
   Card class, with only the suit, number, and point value
   Author: Jessica Liao
   Date: 1/7/2020
*/

public class Card
{
   private final String number;
   private final String suit;
   private final int pointVal;
   
   public Card(String s, String n, int p)
   {
      number = n;
      suit = s;
      pointVal = p;
   
   }
   
   public String getNumber()
   {
      return number;
   }
   
   public String getSuit()
   {
      return suit;
   }
   
   public int getPointVal()
   {
      return pointVal;
   }
   
   public String toString()
   {
      return "\n" + number + " of " + suit;
   }
   


}