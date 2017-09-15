package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

public class ColorChangeCard extends Card{

    public ColorChangeCard(){
        super(COLOR.BLACK, ACTION.COLORCHANGE, null, 50);
    }

    @Override
    public void action(Game game, Gamer gamer) {
        game.setTopColor(gamer.chooseColor());
    }
}
