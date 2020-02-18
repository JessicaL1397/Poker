/*
   Player Class that is the basis for the Poker Player, Deals with the cards and it's hand
   Author: Jessica Liao
   Date: 1/7/2020
*/

public class Player
{
   private Card[] hand;
   private final int MAX_SIZE;
   private int currentSize;
   private String name;
   
   public Player(String n, int max)
   {
      name = n;
      MAX_SIZE = max;
      hand = new Card[MAX_SIZE];
      currentSize = 0;
   }
   
   public String getName()
   {  
      return name;
   }
   
   public Card[] getHand()
   {
      return hand;
   }
   
   public void setCard(Card c)
   {
      if(currentSize < hand.length)
      {
         hand[currentSize] = c;
         currentSize++;
      }
   }
   
   public Card discard(int i)
   {
      if(i <= currentSize) 
      {
         Card temp = hand[i];
         hand[i] = null;
         currentSize--;
         return temp;
      }
      else
         return null;
   }
   
   public Card[] discard()
   {
      Card[] temp = hand;
      
      for(int i = 0; i < currentSize; i++)
      {
         hand[i] = null;
      }
      return temp;
   }
   
   
   public String showHand()
   {
      String retVal = "";
      int count = 0;
      for(int i = 1; i < currentSize+1; i++)
      {
         
         if(hand[count] != null)
         {
            retVal += "Card " + i + ": ";
            retVal += "" + hand[i-1] + "\n";
            count++;
         }    
      }
      
      return retVal;
   }
}