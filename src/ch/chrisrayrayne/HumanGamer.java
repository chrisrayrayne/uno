package ch.chrisrayrayne;

import java.util.ArrayList;

/**
 * Created by christoph on 30.12.16.
 */
public class HumanGamer extends Gamer {

    public HumanGamer(String name){
        super(-1, name);
    }

    public Card play(Card chosenCard, Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue){
        if (canPlayCard(chosenCard, topColor, topActionValue, topNumberValue)){
            cards.remove(chosenCard);
            return chosenCard;
        }
        return null;
    }

    public void printHand() {
        System.out.println(0 + ": Draw a Card from the Pile");
        for (int i = 0; i < this.cards.size(); i++){
            System.out.println((i+1) + ": " + this.cards.get(i).toString());
        }
    }
}
