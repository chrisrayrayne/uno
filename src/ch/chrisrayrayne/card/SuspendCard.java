package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

public class SuspendCard extends Card {

    public SuspendCard(COLOR color){
        super(color, ACTION.SUSPEND, null, 20);
    }

    @Override
    public void action(Game game, Gamer gamer) {
        if(game!=null && Card.ACTION.SUSPEND.equals(game.getTopAction())){
            game.continueGamer();
        }
    }
}
