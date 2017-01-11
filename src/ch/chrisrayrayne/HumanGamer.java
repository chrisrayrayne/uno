package ch.chrisrayrayne;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by chrisrayrayne on 30.12.16.
 */
public class HumanGamer extends Gamer {

    public HumanGamer(String name){
        super(-1, name);
    }

    public Card play(Card chosenCard, Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue){
        if (canPlayCard(chosenCard, topColor, topActionValue, topNumberValue)){
            cards.remove(chosenCard);
            return chosenCard;
        }
        return null;
    }

    public void printHand() {
        System.out.println(0 + ": Draw a Card from the Pile");
        for (int i = 0; i < this.cards.size(); i++){
            System.out.println((i+1) + ": " + this.cards.get(i).toString());
        }
    }

    @Override
    public Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile){
        Card playedCard = null;
        do {
            int card = -1;
            do {
                this.printHand();
                Scanner scanner = new Scanner(System.in);

                try {
                    card = scanner.nextInt();
                } catch (InputMismatchException e) {
                    card = -1;
                }
            }while(card<0 || card>this.cards.size());

            if(card==0){
                playedCard = null;
                this.drawFromPile(pile);
                break;
            }else{
                Card chosenCard = null;
                if(card<=this.cards.size()){
                    chosenCard = this.cards.get(card-1);
                }
                playedCard = play(chosenCard, topColor, topActionValue, topNumberValue);
            }
        }while(playedCard==null);
        return playedCard;
    }
}
