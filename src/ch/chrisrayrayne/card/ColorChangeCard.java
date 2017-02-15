package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

/**
 * Created by chrisrayrayne on 15.02.17.
 */
public class ColorChangeCard extends Card{

    public ColorChangeCard(){
        this.actionValue = ACTION.COLORCHANGE;
        this.color = COLOR.BLACK;
    }

    @Override
    public void action(Game game, Gamer gamer) {
        game.topColor = gamer.chooseColor();
    }
}
