package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

/**
 * Created by chrisrayrayne on 15.02.17.
 */
public class DirectionChangeCard extends Card {

    public DirectionChangeCard(COLOR color){
        this.actionValue = ACTION.DIRECTIONCHANGE;
        this.color = color;
    }

    @Override
    public void action(Game game, Gamer gamer) {
        if(game.gamers.size()<=2) {
            game.clockwise = !game.clockwise;
        }else{
            game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
        }
    }
}
