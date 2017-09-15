package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

public class DirectionChangeCard extends Card {

    public DirectionChangeCard(COLOR color){
        super(color, ACTION.DIRECTIONCHANGE, null, 20);
    }

    @Override
    public void action(Game game, Gamer gamer) {
        if(game.gamers.size() > 2) {
            game.reverseGameDirection();
        }else{
            game.continueGamer();
        }
    }
}
