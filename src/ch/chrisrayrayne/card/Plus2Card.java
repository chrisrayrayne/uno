package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

public class Plus2Card extends Card {

    public Plus2Card(COLOR color){
        super(color, ACTION.PLUS2, null, 20);
    }

    @Override
    public void action(Game game, Gamer gamer) {
        Gamer nextGamer = game.continueGamer();
        nextGamer.addCards(game.drawFromPile(2));
    }
}
