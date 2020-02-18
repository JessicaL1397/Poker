/*
   Coding for the ComputerPokerPlayer
   Author: Jessica Liao
   Date: 1/7/2020
*/


public class ComputerPokerPlayer extends PokerPlayer
{
   
   public ComputerPokerPlayer(String n, int cards)
   {
      super(n, cards);
   }
   
   public double useStrat(Card[] currHand)
   {
     double multiple = Strategy.useStrat(currHand); 
     return multiple; 
   }
   
   public double bet(Card[] currHand, double ante)
   {
      
      double bet = Strategy.useStrat(currHand) * ante;
      if(bet<0)
         return 0;
      return bet;
   }
   
   public double bet(Card[] hand)
   {
      
      double bet = Strategy.useStrat(hand) * 5;
      if(bet<0)
         return 0;
      return bet;
   }
   
   public int[] playHand(Card[] hand)
   {
      int x = Rules.scoreCards(hand);
      int[] discard; 
      int[] copy = new int[hand.length]; //will start values from 1 to the length
      
      for(int i = 0; i < hand.length; i++)
         copy[i] = i+1;
      
      if(x == 2 || x == 3) //Checks for pairs
      {
         discard = new int[3];
         int numPair = 0;
         for(int a = 0; a < hand.length; a++)
         {
            for(int b = a+1; b < hand.length; b++)
            {
               if(hand[a].getPointVal()==hand[b].getPointVal())
               {
                  copy[a] = 0; //sets the index of cards that should not be discarded to 0
                  copy[b] = 0;
                  numPair++;
               }
            }
         }
         
         discard = new int[hand.length - numPair*2];
         
         for(int i = 0; i < hand.length-numPair*2; i++)
         {
            if(copy[i] != 0)
               discard[i] = copy[i]-1;
         }
         
         return discard; 
      
      }
      else if(x == 4)//Works with the triples
      {
         discard = new int[2];
         for(int a = 0; a < hand.length; a++)
         {
            for(int b = a+1; b < hand.length; b++)
            {
               for(int c = b+1; c < hand.length; c++)
               {
                  if(hand[a].getPointVal()==hand[b].getPointVal() && hand[b].getPointVal()==hand[c].getPointVal())
                  {
                     copy[a] = 0;
                     copy[b] = 0;
                     copy[c] = 0;
                  }
               }
            }
         }
         discard = new int[2];
         
         for(int i = 0; i < 2; i++)
         {
            if(copy[i] != 0)
               discard[i] = copy[i]-1;
         }
         
         return discard;
      }
      // anything higher than a three-of-a-kind needs 4 or all 5 cards to work in some sort of pattern
      else 
      {
         discard = new int[1];
         discard[0] = 6;
      }
      return discard;
   }
   
}