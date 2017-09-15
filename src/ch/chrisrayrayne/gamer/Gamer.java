package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public abstract class Gamer {
    public final String name;

    public boolean shoutedUno;

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

    public abstract Card play(Game game);

    public abstract Card.COLOR chooseColor();

    Card drawFromPile(Game game) {
        Card drawnCard = null;
        int cardsDrawn = 0;
        do {
            ArrayList<Card> drawnCards = game.drawFromPile(1);
            if(drawnCards!=null&& drawnCards.size() == 1){
                drawnCard = drawnCards.get(0);
                cardsDrawn++;
                this.addCard(drawnCard);
            }
            if(!game.drawToMatch){
                break;
            }
        }while(drawnCard==null || game.matchesCard(drawnCard));
        return drawnCard;
    }

    Card.COLOR getColorWithMostCards(){
        HashMap<Card.COLOR, Integer> colorCount = new HashMap<>();
        for(Card c: this.cards){
            colorCount.put(c.getColor(), colorCount.getOrDefault(c.getColor(), 0)+1);
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