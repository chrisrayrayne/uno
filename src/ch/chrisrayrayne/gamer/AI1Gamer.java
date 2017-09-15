package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.card.Card;

public class AI1Gamer extends AIGamer {

    public AI1Gamer(String nameValue) {
        super(nameValue);
    }

    @Override
    public Card play(Game game) {
        try {
            Thread.sleep(thinkingPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Card c: cards){
            if(c.isSameColor(game.getTopColor()) && game.canPlayCard(c, this.cards)) {
                return playCard(c);
            }
        }
        for(Card c: cards){
            if(c.isSameValue(game.getTopAction(), game.getTopNumber()) && game.canPlayCard(c, this.cards)) {
                return playCard(c);
            }
        }
        for(Card c: cards){
            if(game.canPlayCard(c, this.cards)) {
                return playCard(c);
            }
        }

        Card c = drawFromPile(game);
        if(game.canPlayCard(c, this.cards)) {
            return playCard(c);
        }
        return null;
    }
}
