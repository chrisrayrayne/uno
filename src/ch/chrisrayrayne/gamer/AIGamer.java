package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.card.Card;

/**
 * Created by chrisrayrayne on 13.09.17.
 */
public abstract class AIGamer extends Gamer {

    public AIGamer(String nameValue) {
        super(nameValue);
    }

    protected Card playCard(Card c) {
        cards.remove(c);
        c.shoutedUno = shoutUno();
        return c;
    }

    @Override
    public boolean shoutUno(){
        if(this.cards.size()==1 && Math.random()>0.05) {
            return super.shoutUno();
        }
        return false;
    }

    @Override
    public Card.COLOR chooseColor() {
        return this.getColorWithMostCards();
    }
}
