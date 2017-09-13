package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.card.Card;

import java.util.ArrayList;

public class AI0Gamer extends AIGamer{

    public AI0Gamer(String nameValue){
        super(nameValue);
    }

    @Override
    public Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile, boolean drawToMatch) {
        for(Card c: cards){
            if(c.isSameColorOrValue(topColor, topActionValue, topNumberValue) && canPlayCard(c, topColor, topActionValue, topNumberValue)) {
                return playCard(c);
            }
        }
        for(Card c: cards){
            if(c.color.equals(Card.COLOR.BLACK) && !Card.ACTION.PLUS4.equals(c.actionValue) && this.cards.size()>1 && canPlayCard(c, topColor, topActionValue, topNumberValue)) {
                return playCard(c);
            }
        }

        for(Card c: cards){
            if(c.color.equals(Card.COLOR.BLACK) && Card.ACTION.PLUS4.equals(c.actionValue) && this.cards.size()>1 && canPlayCard(c, topColor, topActionValue, topNumberValue)) {
                return playCard(c);
            }
        }
        Card c = drawFromPile(pile, drawToMatch, topColor, topActionValue, topNumberValue);
        if(canPlayCard(c, topColor, topActionValue, topNumberValue)) {
            return playCard(c);
        }
        return null;
    }
}