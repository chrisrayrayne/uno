package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

public class Plus4Card extends Card{

    public Plus4Card(){
        super(COLOR.BLACK, ACTION.PLUS4, null, 50);
    }

    @Override
    public void action(Game game, Gamer gamer) {
        game.setTopColor(gamer.chooseColor());

        Gamer nextGamer = game.continueGamer();
        nextGamer.addCards(game.drawFromPile(4));
    }
}
