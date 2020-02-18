/*
   The code for the kitty, that deals with the updating and paying out of the pot
   Author: Jessica Liao
   Date:

*/
public class Kitty
{
   private double total;
   
   public Kitty()
   {
      total = 0.0;
   }
   
   public double getTotal()
   {
      return total;
   }
   
   public void update(double m)
   {
      total += m;
   }
   
   public double payout()
   {
      double temp = total;
      total = 0.0;
      return temp;
   }

}