package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.card.Card;
import ch.chrisrayrayne.card.ColorChangeCard;
import ch.chrisrayrayne.card.Plus2Card;
import ch.chrisrayrayne.card.Plus4Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by chrisrayrayne on 30.12.16.
 */
public abstract class Gamer {
    public final String name;

    public Gamer(String nameValue){
        this.name = nameValue;
    }

    public ArrayList<Card> cards = new ArrayList<>();

    private void addCard(Card c) {
        cards.add(c);
        Collections.sort(cards, Card.sort);
    }

    public void addCards(ArrayList<Card> c) {
        cards.addAll(c);
        Collections.sort(cards, Card.sort);
    }

    public void printHand() {
        for (int i = 0; i < this.cards.size(); i++){
            System.out.println((i+1) + ": " + this.cards.get(i).toString());
        }
    }

    public abstract Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile);

    public abstract Card.COLOR chooseColor();

    public Card drawFromPile(ArrayList<Card> pile) {
        Card drawnCard = null;
        if(pile.size()>0) {
            drawnCard = pile.get(0);
            this.addCard(drawnCard);
            pile.remove(drawnCard);
        }
        return drawnCard;
    }

    protected boolean canPlayCard(Card chosenCard, Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue) {
        if(chosenCard!=null && this.cards.contains(chosenCard)){
            if(topActionValue!=null && topActionValue.equals(Card.ACTION.PLUS4) && (chosenCard instanceof Plus2Card || chosenCard instanceof Plus4Card)){
                return false;
            }
            if(topActionValue!=null && topActionValue.equals(Card.ACTION.COLORCHANGE) && chosenCard instanceof ColorChangeCard){
                return false;
            }
            if(Card.COLOR.BLACK.equals(chosenCard.color) && this.cards.size()==1){
                return false;
            }
            if(Card.COLOR.BLACK.equals(chosenCard.color) && Card.ACTION.PLUS4.equals(chosenCard.actionValue)){
                for(Card c: this.cards){
                    if(!c.equals(chosenCard)) {
                        if (c.actionValue!=null && c.isSameColorOrValue(topColor, topActionValue, topNumberValue)) {
                            return false;
                        }
                    }
                }
                return true;
            }
            if(chosenCard.isSameColorOrValue(topColor, topActionValue, topNumberValue)) {
                return true;
            }
            if(Card.COLOR.BLACK.equals(chosenCard.color)){
                return true;
            }
        }
        return false;
    }

    public Card.COLOR getColorWithMostCards(){
        HashMap<Card.COLOR, Integer> colorCount = new HashMap<>();
        for(Card c: this.cards){
            colorCount.put(c.color, colorCount.getOrDefault(c.color, 0)+1);
        }
        Card.COLOR maxColor = null;
        int maxCount = -1;
        for(Card.COLOR key: colorCount.keySet()){
            if(colorCount.get(key)>maxCount){
                maxColor = key;
                maxCount = colorCount.get(key);
            }
        }
        return maxColor;
    }

    public boolean shoutUno(){
        boolean shouted = false;
        if(this.cards.size()==1) {
            System.out.println(this.name + " shouted UNO!");
            shouted = true;
        }
        return shouted;
    }

    public boolean isHumanPlayer(){
        return this instanceof HumanGamer;
    }
}