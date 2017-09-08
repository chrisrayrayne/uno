package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

import java.util.ArrayList;

/**
 * Created by chrisrayrayne on 15.02.17.
 */
public class Plus4Card extends Card{

    public Plus4Card(){
        this.actionValue = ACTION.PLUS4;
        this.color = COLOR.BLACK;
    }

    @Override
    public void action(Game game, Gamer gamer) {
        game.topColor = gamer.chooseColor();

        game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
        Gamer nextGamer = game.gamers.get(game.i);
        ArrayList<Card> draw = new ArrayList<Card>(game.pile.subList(0, Math.max(0, game.pile.size()-1)));
        game.pile.removeAll(draw);
        nextGamer.addCards(draw);
    }
}
