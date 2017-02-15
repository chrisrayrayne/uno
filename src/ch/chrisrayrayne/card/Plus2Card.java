package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

import java.util.ArrayList;

/**
 * Created by chrisrayrayne on 15.02.17.
 */
public class Plus2Card extends Card {

    public Plus2Card(COLOR color){
        this.actionValue = ACTION.PLUS2;
        this.color = color;
    }

    @Override
    public void action(Game game, Gamer gamer) {
        game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
        Gamer nextGamer = game.gamers.get(game.i);
        ArrayList<Card> draw = new ArrayList<Card>(game.pile.subList(0,2));
        game.pile.removeAll(draw);
        nextGamer.addCards(draw);
    }
}
