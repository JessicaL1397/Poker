/*
   Main Interface for the Poker Game
   Author: Jessica Liao
   Date: 1/7/2020
*/

import java.util.Scanner;

public class PokerInterface
{
   public static void main(String[] args)
   {
      Scanner reader = new Scanner(System.in);
      boolean keepGoing = true;
      Deck deck = new Deck();
      Kitty kitty = new Kitty();
      
      int dealer = 0;//Evens, computer is dealer. Odd, player is dealer
      
      while(keepGoing)
      {
         System.out.println("Welcome to Poker! Player 1, what is your name?");
         String name = reader.nextLine();
         
         deck.shuffle();
         boolean inGame = true;
        
         System.out.println("Please note, all Aces have a value of 14!");
         System.out.println("How many cards would you like to play with?");
         int amtOfCards = reader.nextInt();   
         reader.nextLine();
         
         PokerPlayer p1 = new PokerPlayer(name, amtOfCards);
         ComputerPokerPlayer p2 = new ComputerPokerPlayer("Computer", amtOfCards);
         
         System.out.println("The ante is: " + Rules.getAnte()+  ". \n" + p1.getName() + ", would you like to continue? Enter y/n.");
         String ans = reader.nextLine();
         
         if(ans.equals("y"))
         {  
            dealer++;
            p1.deduct(Rules.getAnte());
            
            p2.deduct(Rules.getAnte());
            
            if(p1.deduct(Rules.getAnte()) < 0)
               System.out.println("It seems as though as you cannot play anymore! You're broke!\nThe computer wins, thank you for playing!");
            else if(p2.deduct(Rules.getAnte()) < 0)
               System.out.println("It seems as though as the computer cannot play anymore! You win!\nThank you for playing!");
            else
               {
                 kitty.update(Rules.getAnte()*2);
                }
         }
         else
         {
            System.out.println("Thank you for playing!");
            break;  
         }

         while(inGame)
            {    
                  deck.shuffle();
                  System.out.println("The cards are being dealt!");
                  for(int i = 0; i < amtOfCards; i++)
                  {
                     p1.setCard(deck.deal());
                     p2.setCard(deck.deal());
                  }
                  
                  System.out.println(p1.getName() + ", here are your cards: ");
                  System.out.println(p1.showHand());
                  if(dealer%2 == 0)
                  {   
                        // Begin betting round
                        inGame = betPlayerDealer(p1, p2, kitty);
                        if(!inGame)
                           break;
                        //Begin dicard phase
                        System.out.println("Here is your hand: ");
                        System.out.println(p1.showHand());
                        changeHands(p1, p2, deck);
                        System.out.println(p1.showHand());
                        // Bet again
                        inGame = betPlayerDealer(p1, p2,kitty);
                        if(!inGame)
                           break;
                        //Begin End Phase
                        inGame = revealAndScore(p1,p2,kitty);
                        if(!inGame)
                           break;
                 } // closes the dealer if statement
                 else
                 {
                        // Begin betting round
                        inGame = betCompDealer(p1, p2, kitty);
                        if(!inGame)
                           break;
                        //Begin dicard phase
                        System.out.println("Here is your hand: ");
                        System.out.println(p1.showHand());
                        changeHands(p1, p2, deck);
                        System.out.println(p1.showHand());
                        // Bet again
                        inGame = betCompDealer(p1, p2,kitty);
                        if(!inGame)
                           break;
                        //Begin End Phase
                        inGame = revealAndScore(p1,p2,kitty);
                        if(!inGame)
                           break;
                 } // closes the if else dealing with the dealer
                 
                }//closes while loop
            deck.returnToDeck(p1.getHand());
            deck.returnToDeck(p2.getHand());
            p1.discard();
            p2.discard();
            keepGoing = anotherGame(p1);
         }
                   
   }//Closes the interface
   
   
   public static void changeHands(PokerPlayer p1, ComputerPokerPlayer p2, Deck deck)
   {
      Scanner reader = new Scanner(System.in);
      System.out.println(p1.getName() + ", how many cards would you like to discard?");
      int cardToDiscard = reader.nextInt();  
      boolean toDiscardCards = true;
      if(cardToDiscard == 0)
         toDiscardCards = false;
      if(toDiscardCards)
      {
         reader.nextLine();
         System.out.println(p1.getName() + ", what cards would you like to discard? Separate the cards using only commas.");
         String discardCards = reader.nextLine();      
               
         Card[] discard = new Card[cardToDiscard];
               
         boolean keepGoing = true;
         int index = 0;
         String num;
         int discardIndex = 0;
                  
         while(keepGoing)
         {
            if(discardCards.length() > 1)//find the comma and the integer after it
            {
               int y = discardCards.indexOf(",",index);
               num = discardCards.substring(y+1,y+2);
               index+= 2;
            }
            else
               num = discardCards;
                  
            int x = Integer.parseInt(num);
            
            discard[discardIndex] = p1.discard(x-1);
                  
            discardIndex++;
                  
            if(discardIndex == cardToDiscard)
               keepGoing = false;
         }
         
         deck.fixCards(p1.getHand());
         Card[] p1Hand = p1.getHand();
         deck.fixCards(p1Hand);
         
         for(int i = (p1.getHand()).length; i > (p1.getHand()).length-cardToDiscard; i--)
            p1.setCard(deck.deal());
   
         deck.returnToDeck(discard);//End dealing with human player cards
      }
      int[] disIndex = new int[(p2.playHand(p2.getHand())).length];
      
      for(int i = 0; i<disIndex.length; i++)
      {
         disIndex[i] = p2.playHand(p2.getHand())[i];
      }
      
      if((p2.playHand(p2.getHand())).length != 1 && disIndex[0] != 6)
      {
         for(int i = 0; i < disIndex.length; i++)
         {
            int x = disIndex[i];
            deck.returnToDeck(p2.discard(x));
         }
         
         deck.fixCards(p2.getHand());
         Card[] p2Hand = p2.getHand();
         Deck.fixCards(p2Hand);
         
         for(int i = p2Hand.length; i > p2Hand.length-disIndex.length; i--)
         {
            p2.setCard(deck.deal());
         }
         
      }  
   }
   
   public static boolean betPlayerDealer(PokerPlayer p1, ComputerPokerPlayer p2, Kitty kitty)
   {
      Scanner reader = new Scanner(System.in);
      System.out.println("Would you like to bet or fold? Enter b/f");
      String playerAns = reader.nextLine();
      double betAmt = 0.0;
      if(playerAns.equals("b"))
      {
         System.out.println("How much would you like to bet?");
         betAmt = reader.nextDouble();
         kitty.update(betAmt);
         p1.deduct(betAmt);
      }

      boolean keepGoing = true; // Begins the computer segment of betting
      double compBetAmt = p2.bet(p2.getHand(), betAmt);
      String computerCOrF = "";
                     
      if(compBetAmt <=0)
         computerCOrF = "f";
      else if(compBetAmt == 1)
      {
         kitty.update(compBetAmt);
         p2.deduct(compBetAmt);
         computerCOrF = "c";
      }
      else
      {
         kitty.update(compBetAmt);
         p2.deduct(compBetAmt);
         computerCOrF = "r";
      }

      // Deals with the choices of the computer and player
     if(playerAns.equals("f") && (computerCOrF.equals("c")||computerCOrF.equals("r")))
     {
         double amt = kitty.payout();
         System.out.println("The computer has won! They won $"+ amt+". This was their hand: ");
         p2.increase(amt);
         System.out.println(p2.showHand());
         keepGoing = false;
     }
     else if(playerAns.equals("b") && (computerCOrF.equals("c")))
     {}
     else if(playerAns.equals("b")&&(computerCOrF.equals("r")))
     {
         System.out.println("The Computer has decided to raise the bet by $" +(compBetAmt-betAmt));
         reader.nextLine();
         System.out.println("Would you like to match that or would you like to fold? Enter m/f");
         String ans = reader.nextLine();
            if(ans.equals("f"))
            {
               double amt = kitty.payout();
               System.out.println("The computer has won! They won $"+amt+ ". This was their hand: ");
               p2.increase(amt);
               System.out.println(p2.showHand());
               keepGoing = false;
            }
            else
            {
               if(p1.deduct(compBetAmt-betAmt) < 0)
               {
                  System.out.println("You can't afford the bet! Computer wins!");
                  keepGoing = false;
               }
               else
                  kitty.update(compBetAmt-betAmt);
            }
     
     }
     else if(playerAns.equals("f") && computerCOrF.equals("f"))
     {
         kitty.payout();
         System.out.println("You both decided to fold!");
         keepGoing = false;
     }
     else
     {
         double amt = kitty.payout();
         System.out.println("The Computer has decided to fold. This was their hand. You win! You won $"+amt);
         System.out.println(p2.showHand());
         p1.increase(amt);
         keepGoing = false;
     }
      return keepGoing;//returns if someone has won or the game should ask to be played again
   }
   
   public static boolean betCompDealer(PokerPlayer p1, ComputerPokerPlayer p2, Kitty kitty)
   {
      Scanner reader = new Scanner(System.in);
      double compBetAmt = p2.bet(p2.getHand());
      String computerCOrF = "";
      boolean keepGoing = true;
      
      if(compBetAmt <=0) // Computer's bet
      {
         computerCOrF = "f";
         System.out.println("The computer has decided to fold.");
         compBetAmt = 0.0;
      }
      else 
      {
         System.out.println("The Computer has decided to bet $" + compBetAmt);
         computerCOrF = "c";
      }
      p2.deduct(compBetAmt);
      kitty.update(compBetAmt);
      //Begin player's bet
         System.out.println("Would you like to check, raise the bet or fold? Enter c/r/f");
         String ans = reader.nextLine();
         
            if(ans.equals("f")&&computerCOrF.equals("c"))
            {
               double amt = kitty.payout();
               System.out.println("The computer has won! They won $"+amt+ ".This was their hand: ");
               p2.increase(amt);
               System.out.println(p2.showHand());
               keepGoing = false;
            }
            else if(ans.equals("c") && computerCOrF.equals("c"))
            {
               kitty.update(2*compBetAmt);
               p1.deduct(compBetAmt);
               p2.deduct(compBetAmt);
            }
            else if(computerCOrF.equals("f")&& ((ans.equals("r"))||(ans.equals("c"))))
            {
               double amt = kitty.payout();
               System.out.println("The Computer has decided to fold. This was their hand. You win! You won $"+amt);
               System.out.println(p2.showHand());
               p1.increase(amt);
               keepGoing = false;
            }
            else if(ans.equals("f") && computerCOrF.equals("f"))
            {
               kitty.payout();
               System.out.println("You both decided to fold!");
               keepGoing = false;
            }
            else
            {
               System.out.println("How much would you like to raise the bet?");
               double raise = reader.nextDouble();
               
               if(p2.deduct(raise) > 0)
               {
                  System.out.println("The computer accepts!");
                  p1.deduct(raise + compBetAmt);
                  kitty.update(raise*2 + compBetAmt);
                  
               }
               else
               {
                  System.out.println("The computer can't play anymore! You win!");
                  p1.increase(kitty.payout());
               }
            
            }
   return keepGoing;
  }
   
   
   public static boolean revealAndScore(PokerPlayer p1, ComputerPokerPlayer p2, Kitty kitty)
   {
      System.out.println("Here was the computer's hand!");
      System.out.println(p2.showHand());
      int a = Rules.scoreCards(p1.getHand());
      int b = Rules.scoreCards(p2.getHand());
      int winner = 0;
      boolean keepGoing = false;
      
      if(a == b)
      {
         winner = Rules.breakTie(p1.getHand(), p2.getHand());
         
      }
      else if (a > b)
         winner = 0;
      else
         winner = 1;
      
      if(winner == 0)
      {
         double amt = kitty.payout();
         p1.increase(amt);
         System.out.println("You win! Congratulations!");
         return false;
      }
      else
      {
         p2.increase(kitty.payout());
         System.out.println("The Computer won!");
         return false; // will return that the while loop is false and should be broken out of
      }
   
   }
   
   public static boolean anotherGame(PokerPlayer p1)
   {//asks if the player wants to play another game
      Scanner reader = new Scanner(System.in);
      System.out.println("Would you like to play another game? Enter y/n");
      String response = reader.nextLine();
      if(response.equals("y"))
      {
         return true;
      }
      else
      {
         System.out.println("Thank you for playing! You leave the game with: $" + p1.getMoney() + "!");
         return false;
      }
   
   }
}