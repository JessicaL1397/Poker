/*
   The Rules Class, where the cards are scored, tie breaked, and the ante is always 5.
   Author: Jessica Liao
   Date: 1/7/2020
*/


public class Rules
{
   private static int ante =  5;
   
   public static int getAnte()
   {
      return ante;
   }
   
   public static void setAnte(int num)
   {
      ante = num;
   }
   
   public static int scoreCards(Card[] hand)
   {
      boolean isTriple = false;
      boolean isQuad = false;
      boolean isSameSuit = false;
      boolean isSomeSortOfStraight = false;
               
      int pointVal = 0;

      //Checking for 4 of a kind only
      for(int a = 0; a < hand.length; a++)
      {
         for(int b = a+1; b < hand.length; b++)
         {
            for(int c = b+1; c < hand.length; c++)
            {
               for(int d = c+1; d < hand.length; d++)
               {
                  if(hand[a].getPointVal() == hand[b].getPointVal()
                   && hand[b].getPointVal() == hand[c].getPointVal()
                    && hand[c].getPointVal() == hand[d].getPointVal())
                  {
                     isQuad = true;
                     return 8;
                  }
               }
            }
         }
      }
      
      //Checking for triple, and then if it is a Full House
      int tripIndex1 = 0;
      int tripIndex2 = 0;
      int tripIndex3 = 0;
      for(int a = 0; a < hand.length; a++)
      {
         for(int b = a+1; b < hand.length; b++)
         {
            for(int c = b+1; c < hand.length; c++)
            {
               if(isQuad == false)
               {
                  if(hand[a].getPointVal()==hand[b].getPointVal() && hand[b].getPointVal()==hand[c].getPointVal())
                  {
                     isTriple = true;
                     tripIndex1 = a;
                     tripIndex2 = b;
                     tripIndex3 = c;
                     break;
                  }
               }
            }
         }
      }
      
      //Identifies what cards are not included in the triple
      if(isTriple)
      {
         Card[] tempHand = new Card[hand.length];
         for(int i = 0; i < hand.length; i++)
         {
            tempHand[i] = hand[i];
         }
   
         tempHand[tripIndex1] = null;
         tempHand[tripIndex2] = null;
         tempHand[tripIndex3] = null;
         Deck.fixCards(tempHand);
         //and then checking if the remaining cards have the smae value
         if(tempHand[1].getPointVal() == tempHand[0].getPointVal())
            return 7;
         return 4;
      }
     
     //Checking for pairs and if so, how many
     int numPair = 0;
     for(int a = 0; a < hand.length; a++)
      {
         for(int b = a+1; b < hand.length; b++)
         {
            if(isQuad == false && isTriple==false)
            {
               if(hand[a].getPointVal() == hand[b].getPointVal())
                  numPair++;
            }
            
         }
      }
      
      if(numPair == 2)
         //is2Pair = true;
         return 3;
      else if (numPair == 1)
         //isPair = true;
         return 2;
      else {}

     
      //Checking for Straights
         Deck.sortCards(hand);
         int check = hand[0].getPointVal();

         for(int i = 0; i < hand.length-1; i++)
         {
            if(hand[i].getPointVal() != hand[i+1].getPointVal()-1)
            {
               isSomeSortOfStraight = false;
               break;
            }
            else
               isSomeSortOfStraight = true;
         }
         
         
       //Checking for the same suit
         for(int i = 0; i < hand.length-1; i++)
         {
            if((hand[i].getSuit()).equals(hand[i+1].getSuit()))
               isSameSuit = true;
            else
            {
               isSameSuit = false;
               break;
            }
         }
         
       // Checking for specific Flush/Straight
         if(isSomeSortOfStraight)
         {
            if(isSomeSortOfStraight&& hand[0].getPointVal() == 15-hand.length && isSameSuit)
               //isRoyalFlush = true;
               return 10;
            else if(isSameSuit)
            {
               //isStraightFlush = true;
               return 9;
            }
            else if(!isSameSuit)
               return 5;
            else{}
         }
         else
         {
            if(isSameSuit)
               return 6;
         }
      
      return 1;
   
   }
   
   
   public static int breakTie(Card[] hand1, Card[] hand2)
   {
   
      int cardType = scoreCards(hand1);
      int max1 = 0; 
      int max2 = 0;
      int index1 = 0;
      int index2 = 0;
      
      //There's no good way to check if they both had royal flushes.
      //Player 1 will always win in that case.
      if(cardType == 10)
      {
         return 0;
      }
      
      // Checks anything that would just utilize the highest value card
      else if(cardType == 9 || cardType == 1 || cardType == 6 || cardType == 5) 
      {
         for(int i = 0; i < hand1.length; i++)
         {
            if(index1 < hand1[i].getPointVal())
               index1 = hand1[i].getPointVal();
         }
         
         for(int i = 0; i < hand2.length; i++)
         {
            if(index2 < hand2[i].getPointVal())
               index2 = hand2[i].getPointVal();
         }
         
      }
      //Checks for a singular pair
      else if(cardType == 2)
      {
         for(int a = 0; a < hand1.length; a++)
         {
            for(int b = a+1; b < hand1.length; b++)
            {
               if(hand1[a].getPointVal()==hand1[b].getPointVal())
               {
                  index1 = hand1[a].getPointVal();
               }
            }
         }
         
         
         for(int a = 0; a < hand2.length; a++)
         {
            for(int b = a+1; b < hand2.length; b++)
            {
               if(hand2[a].getPointVal()==hand2[b].getPointVal())
               {
                  index2 = hand2[a].getPointVal();
               }
            }
         }
             
     }
     //Checking for 2 pairs;
     else if(cardType == 3)
     {
         
       for(int a = 0; a < hand1.length; a++)
         {
            for(int b = a+1; b < hand1.length; b++)
            {
               if(hand1[a].getPointVal()==hand1[b].getPointVal())
               {
                  if(hand1[a].getPointVal() > index1)
                     index1 = hand1[a].getPointVal();
               }
            }
         }

       for(int a = 0; a < hand2.length; a++)
         {
            for(int b = a+1; b < hand2.length; b++)
            {
               if(hand2[a].getPointVal()==hand2[b].getPointVal())
               {
                  if(hand2[a].getPointVal()>index2)   
                     index2 = hand2[a].getPointVal();
               }
            }
         }
          
     }
     
     
     //Checking 4 of a kind
     else if(cardType == 8) 
     {
         
         for(int a = 0; a < hand1.length; a++)
         {
            for(int b = a+1; b < hand1.length; b++)
            {
               for(int c = b+1; c < hand1.length; c++)
               {
                  for(int d = c+1; d < hand1.length; d++)
                  {
                     if(hand1[a].getPointVal() == hand1[b].getPointVal() && hand1[b].getPointVal()==hand1[c].getPointVal() && hand1[c].getPointVal() == hand1[d].getPointVal())
                     {
                        index1 = hand1[a].getPointVal();
                     }
                  }
               }
            }
         }
         
         for(int a = 0; a < hand2.length; a++)
         {
            for(int b = a+1; b < hand2.length; b++)
            {
               for(int c = b+1; c < hand2.length; c++)
               {
                  for(int d = c+1; d < hand2.length; d++)
                  {
                     if(hand2[a].getPointVal() == hand2[b].getPointVal() && hand2[b].getPointVal()==hand2[c].getPointVal() && hand2[c].getPointVal() == hand2[d].getPointVal())
                     {
                        index2 = hand2[a].getPointVal();
                     }
                  }
               }
            }
         }
     
     }
     //Checking Triples
     else if(cardType == 4)
     {

         for(int a = 0; a < hand1.length; a++)
         {
            for(int b = a+1; b < hand1.length; b++)
            {
               for(int c = b+1; c < hand1.length; c++)
               {
                  if(hand1[a].getPointVal()==hand1[b].getPointVal() && hand1[b].getPointVal()==hand1[c].getPointVal())
                  {
                     index1 = hand1[a].getPointVal();
                  }
               }
            }
         }
         
         for(int a = 0; a < hand2.length; a++)
         {
            for(int b = a+1; b < hand2.length; b++)
            {
               for(int c = b+1; c < hand2.length; c++)
               {
                  if(hand2[a].getPointVal()==hand2[b].getPointVal() && hand2[b].getPointVal()==hand2[c].getPointVal())
                  {
                     index2 = hand2[a].getPointVal();
                  }
               }
            }
         }

     }
     //Checking Full Houses
     else
     {
         int indexA1 = 0;
         int indexA2 = 0;
         
         int indexB1 = 0;
         int indexB2 = 0;
         //Checking Triple
         for(int a = 0; a < hand1.length; a++)
            {
               for(int b = a+1; b < hand1.length; b++)
               {
                  for(int c = b+1; c < hand1.length; c++)
                  {
                     if(hand1[a].getPointVal()==hand1[b].getPointVal() && hand1[b].getPointVal()==hand1[c].getPointVal())
                     {
                        indexA1 = hand1[a].getPointVal();
                     }
                  }
               }
            }
         //Checking Double 
         for(int a = 0; a < hand1.length; a++)
            {
               for(int b = a+1; b < hand1.length; b++)
               {
                  if(hand1[a].getPointVal()==hand1[b].getPointVal())
                  {
                     if(hand1[a].getPointVal() > index1)
                        indexA2 = hand1[a].getPointVal();
                  }
               }
            }
        //Checking Triple 
         for(int a = 0; a < hand2.length; a++)
            {
               for(int b = a+1; b < hand2.length; b++)
               {
                  for(int c = b+1; c < hand2.length; c++)
                  {
                     if(hand2[a].getPointVal()==hand2[b].getPointVal() && hand2[b].getPointVal()==hand2[c].getPointVal())
                     {
                        indexB1 = hand2[a].getPointVal();
                     }
                  }
               }
            }
        //Checking Double
        for(int a = 0; a < hand2.length; a++)
            {
               for(int b = a+1; b < hand2.length; b++)
               {
                  if(hand2[a].getPointVal()==hand2[b].getPointVal())
                  {
                     if(hand2[a].getPointVal()>index2)   
                        indexB2 = hand2[a].getPointVal();
                  }
               }
            }
         
         if(indexA1 > indexA2)
            index1 = indexA1;
         else
            index1 = indexA2;
         
         if(indexB1 > indexB2)
            index2 = indexB1;
         else
            index2 = indexB2;
     }
     
     if(index1 > index2)
         return 0;
      return 1; 
   }
}