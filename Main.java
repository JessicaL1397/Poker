import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Deck d = new Deck();
        System.out.println(d);
        d.shuffle();
        System.out.println(d);
        d.checkDeck();
        System.out.println(d);

        Card z = d.deal();
        System.out.println(z);
        //System.out.println(d.getSize());

        Card y = d.deal();
        System.out.println(y);
        //System.out.println(d.getSize());

        Card x = d.deal();
        System.out.println(x);
        //System.out.println(d.getSize());

        Card w = d.deal();
        System.out.println(w);
        //System.out.println(d.getSize()); 

//        Card[] arr = {z, y, x, w};
        System.out.println(d);
        System.out.println(d.returnToDeck(z));
        System.out.println(d);
        System.out.println(d.returnToDeck(y));
//        d.returnToDeck(arr);

        System.out.println(d);
        d.checkDeck();
        //System.out.println(d.getSize());

//        Card[] hand = new Card[5];
//        hand[0] = new Card("Spades", "One", 1);
//        hand[1] = new Card("Spades", "Three", 3);
//        hand[2] = null; //new Card("Spades", "Two", 2);
//        hand[3] = new Card("Spades", "King", 13);
//        hand[4] = new Card("Spades", "Seven", 7);
//
//        Deck.fixCards(hand);
//        Deck.sortCards(hand);
//        System.out.println(Arrays.toString(hand));
    }
}