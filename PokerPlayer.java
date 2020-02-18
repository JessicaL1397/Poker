/*
   General PokerPlayer Code, that deals with both the player and the money related to the Game
   Author: Jessica Liao
   Date: 1/7/2020
*/

public class PokerPlayer extends Player
{
   private double money;
   
   public PokerPlayer(String name,int cards)
   {
      super(name, cards);
      money = 100;
   }
   
   public boolean canCoverBet(double amt)
   {
      if(money > amt)
      {
         return true;
      }
      return false;
   }
   
   public double deduct(double amt)
   {
      if(canCoverBet(amt))
      {
         money -= amt;
         return money;
      }
      else
         return -1;
   }
   
   public void increase(double amt)
   {
      money += amt;
   }
   
   public double getMoney()
   {
      return money;
   }
}