package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

public class NumberCard extends Card {

    public NumberCard(int value, COLOR color){
        super(color, null, value, value);
    }

    public void action(Game game, Gamer gamer){
        // no Action
    }
}
