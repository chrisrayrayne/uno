package ch.chrisrayrayne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by chrisrayrayne on 30.12.16.
 */
public class Gamer {
    public final int AIStrength;
    public final String name;

    public ArrayList<Card> cards = new ArrayList<Card>();

    public Gamer(final int strength, final String name) {
        this.AIStrength = strength;
        this.name = name;
    }

    public void addCard(Card c) {
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

    public Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile) {
        for(Card c: cards){
            if(c.isSameColorOrValue(topColor, topActionValue, topNumberValue)) {
                cards.remove(c);
                return c;
            }
        }
        for(Card c: cards){
            if(c.color.equals(Card.COLOR.BLACK) && !Card.ACTION.PLUS4.equals(c.actionValue) && this.cards.size()>1) {
                cards.remove(c);
                return c;
            }
        }

        for(Card c: cards){
            if(c.color.equals(Card.COLOR.BLACK) && Card.ACTION.PLUS4.equals(c.actionValue) && this.cards.size()>1) {
                cards.remove(c);
                return c;
            }
        }
        drawFromPile(pile);
        return null;
    }

    public void drawFromPile(ArrayList<Card> pile) {
        if(pile.size()>0) {
            this.addCard(pile.get(0));
            pile.remove(0);
        }
    }

    protected boolean canPlayCard(Card chosenCard, Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue) {
        if(chosenCard!=null && this.cards.contains(chosenCard)){

            if(Card.COLOR.BLACK.equals(chosenCard.color)  && this.cards.size()>1){
                if(Card.ACTION.PLUS4.equals(chosenCard.actionValue)){
                    for(Card c: this.cards){
                        if(!c.equals(chosenCard)) {
                            if (c.isSameColorOrValue(topColor, topActionValue, topNumberValue) || Card.COLOR.BLACK.equals((c.color))) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
            if(chosenCard.isSameColorOrValue(topColor, topActionValue, topNumberValue)) {
                return true;
            }
        }
        return false;
    }

    public Card.COLOR getColorWithMostCards(){
        HashMap<Card.COLOR, Integer> colorCount = new HashMap<Card.COLOR, Integer>();
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
}