/*
   Deck class that deals with the deck and other such methods
   Author: Jessica Liao
   Date: 1/7/2020
*/

public class Deck
{
   private Card[] cards;//Contians Cards
   public final int MAX_SIZE = 52;//set when Deck created
   private int size;//keeps track of the size;
   
   public Deck()
   {
      cards = new Card[MAX_SIZE]; 
      size = MAX_SIZE;
      String[] suit = {"Spades", "Hearts", "Diamonds", "Clubs"};
      String[] number = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
      
      
      for(int i = 0; i < 4; i++)
      {    
         for(int a = 0; a < 13; a++)
         {
            if(a == 0)
            {
               cards[i*13 + a] = new Card(suit[i], number[a], 14);
            } else 
            cards[i*13 + a] = new Card(suit[i], number[a], a+1);
         }
      
      }
   } 
   
   public int getSize()
   {
      return size;
   }
   
   public Card[] getCards()
   {
      return cards;
   }
   
   public Card deal()
   {
      size -= 1; // size is the end of the a tual cards, not the entire deck
      int end = 0;
      fixCards(cards);
      for(int i = MAX_SIZE-1 ; i >=0; i--)
      {
         if(cards[i] != null)
         {
            end = i;
            break;
         }
            
      }
         Card temp = cards[end]; // what if it's null?
         cards[end] = null;
      return temp;
   }
   
   public boolean returnToDeck(Card c)
   {
      if(size < MAX_SIZE)
      {
         for (int i=size; i>=1; i--)
            cards[i]=cards[i-1];
         cards[0]= c;
         size++;                             
         cards[0] = c; 
         return true;
      }
      return false;
      
   }
   
   public boolean returnToDeck(Card[] c)
   {
      if(size + c.length <= MAX_SIZE)
      {
         fixCards(cards);
         fixCards(c);
         int cardCount = 0;
         for(int i = 0; i < c.length; i++)
         {
            if(c[i] != null)
               cardCount++;
         }
         for(int i = 0; i < cardCount; i++)
            returnToDeck(c[i]);
         return true;
      }
      return false;
   }
   

   public void shuffle()
   {
      for(int i = 0; i < size; i++)
      {
         int random = (int)(Math.random()*size);
         Card temp = cards[i];
         cards[i] = cards[random];
         cards[random] = temp;
      }
   }

   public static void sortCards(Card[] hand)
   {
      for(int i = 0; i < hand.length; i++)
      {
         int min = hand[i].getPointVal();
         int minIndex = i;
         for(int a = i; a < hand.length; a++)
         {
            if(hand[a].getPointVal() < min)
            {
               min = hand[a].getPointVal();
               minIndex = a;
            } 
         }
         Card temp = hand[i];
         hand[i] = hand[minIndex];
         hand[minIndex] = temp;
      }
      
   }
   
   
   public static void fixCards(Card[] hand)
   {
      for(int i = 0; i < hand.length; i++)
      {
         if(hand[i] == null)
         {
            for(int a = i; a < hand.length; a++)
            {
               if(a == hand.length-1)
               {
                  hand[a] = null;
               }
               else
               hand[a] = hand[a+1];
            }
         }
      }
   }  
   
   
   
   public boolean checkDeck()
    {
    	int spadesCnt=0, diamondsCnt=0, clubsCnt=0, heartsCnt=0;
    	int totalValue=0;
    	Card[] spades = new Card[20];
    	Card[] diamonds = new Card[20];
    	Card[] hearts = new Card[20];
    	Card[] clubs = new Card[20];
      System.out.println("*******Checking Deck **********/");	
    	for(int i=0; i<MAX_SIZE; i++)
    	{
    		if(cards[i]!=null && cards[i].getSuit().equals("Clubs"))
    		{
    		    		
    			clubs[clubsCnt]=cards[i];
    			clubsCnt++;
    		}
    		else if(cards[i]!=null && cards[i].getSuit().equals("Diamonds"))
    		{
    			diamonds[diamondsCnt]=cards[i];
    			diamondsCnt++;
    		}
    	
    		else if(cards[i]!=null && cards[i].getSuit().equals("Hearts"))
    		{
    			hearts[heartsCnt]=cards[i];
    			heartsCnt++;
    		}
    	
    		else if(cards[i]!=null && cards[i].getSuit().equals("Spades"))
    		{
    			spades[spadesCnt]=cards[i];
    			spadesCnt++;
    		}
    		if(cards[i]!=null )
					totalValue+=cards[i].getPointVal();//changed from getFaceValue to this
		
    	}
				for(int i=0; i<clubsCnt; i++)
					if(clubs[i]!=null)
						System.out.println(clubs[i]);
				System.out.println();
            
				for(int i=0; i<diamondsCnt; i++)
					if(diamonds[i]!=null)
						System.out.println(diamonds[i]);
				System.out.println();
            
				for(int i=0; i<spadesCnt; i++)
					if(spades[i]!=null)
						System.out.println(spades[i]);
				System.out.println();
            
				for(int i=0; i<heartsCnt; i++)
					if(hearts[i]!=null)
						System.out.println(hearts[i]);
						
						
    	
    	System.out.println("Clubs: " + clubsCnt + " Spades: " + spadesCnt + " Diamonds: " + diamondsCnt + " Hearts: " + heartsCnt);
      System.out.println("Total: " + totalValue);
    
    	if(clubsCnt==13 && spadesCnt == 13 && diamondsCnt==13 && heartsCnt==13 && totalValue==416)
    		return true;
    	return false;
     
    }
   
   public String toString()
   {
      String retVal = "Number of Cards in Deck: " + size + "\n";
      
      for(int i = 0; i < size; i++)
      {
         if(cards[i] != null)
            retVal += cards[i].getNumber() + " of " + cards[i].getSuit() + "\n";
      }
      
      return retVal;
   }

}