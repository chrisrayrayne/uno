package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.card.Card;

public class AI0Gamer extends AIGamer{

    public AI0Gamer(String nameValue){
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