package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

/**
 * Created by chrisrayrayne on 15.02.17.
 */
public class SuspendCard extends Card {

    public SuspendCard(COLOR color){
        this.actionValue = ACTION.SUSPEND;
        this.color = color;
    }

    @Override
    public void action(Game game, Gamer gamer) {
        if(this!=null && Card.ACTION.SUSPEND.equals(game.topActionValue)){
            game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
        }
    }
}
