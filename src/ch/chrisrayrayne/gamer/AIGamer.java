package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.card.Card;

public abstract class AIGamer extends Gamer {

    protected int thinkingPause = 500;

    AIGamer(String nameValue) {
        super(nameValue);
    }

    Card playCard(Card c) {
        cards.remove(c);
        this.shoutedUno = shoutUno();
        return c;
    }

    @Override
    public boolean shoutUno() {
        return this.cards.size() == 1 && Math.random() > 0.05 && super.shoutUno();
    }

    @Override
    public Card.COLOR chooseColor() {
        return this.getColorWithMostCards();
    }
}
