package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.card.Card;

import java.util.ArrayList;

public class AI1Gamer extends Gamer{

    public AI1Gamer(String name){
        super(name);
    }

    @Override
    public Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile) {
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
        Card c = drawFromPile(pile);
        if(canPlayCard(c, topColor, topActionValue, topNumberValue)) {
            playCard(c);
        }
        return null;
    }

    private Card playCard(Card c) {
        cards.remove(c);
        c.shoutedUno = shoutUno();
        return c;
    }

    @Override
    public boolean shoutUno(){
        if(cards.size()==1 && Math.random()>0.05) {
            return super.shoutUno();
        }
        return false;
    }

    @Override
    public Card.COLOR chooseColor() {
        return this.getColorWithMostCards();
    }
}