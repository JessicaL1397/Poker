/*
   The Strategy that the computer uses
   Author: Jessica Liao
   Date: 1/7/2020
*/


public class Strategy
{
   public static double useStrat(Card[] hand)
   {  
        int cardType = Rules.scoreCards(hand);
        if(cardType == 1)
        {
          return -1; // returns how much to multiply the bet by to raise the ante.
        }
        else if(cardType > 2 && cardType < 5)
        {
            return 1;
        }
        else if(cardType >= 5 && cardType < 8)
        {
            return 1.25;
        }
        else
            return 1.5;
   
   }
   


}