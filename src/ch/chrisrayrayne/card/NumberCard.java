package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

/**
 * Created by chrisrayrayne on 15.02.17.
 */
public class NumberCard extends Card {

    public NumberCard(int value, COLOR color){
        this.numberValue = new Integer(value);
        this.color = color;
    }

    public void action(Game game, Gamer gamer){
        // no Action
    }
}
