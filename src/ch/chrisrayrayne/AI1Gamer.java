package ch.chrisrayrayne;

import java.util.ArrayList;

public class AI1Gamer extends Gamer{

    public AI1Gamer(String name){
        super(name);
    }

    @Override
    public Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile) {
        for(Card c: cards){
            if(c.isSameColorOrValue(topColor, topActionValue, topNumberValue)) {
                cards.remove(c);
                return c;
            }
        }
        for(Card c: cards){
            if(c.color.equals(Card.COLOR.BLACK) && !Card.ACTION.PLUS4.equals(c.actionValue) && this.cards.size()>1) {
                cards.remove(c);
                return c;
            }
        }

        for(Card c: cards){
            if(c.color.equals(Card.COLOR.BLACK) && Card.ACTION.PLUS4.equals(c.actionValue) && this.cards.size()>1) {
                cards.remove(c);
                return c;
            }
        }
        drawFromPile(pile);
        return null;
    }
}