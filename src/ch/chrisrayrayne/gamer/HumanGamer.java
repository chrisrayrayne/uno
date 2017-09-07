package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.card.Card;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by chrisrayrayne on 30.12.16.
 */
public class HumanGamer extends Gamer {

    public HumanGamer(String nameValue){
        super(nameValue);
    }

    private Card play(Card chosenCard, Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue){
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
        if(this.cards.size()==2){
            System.out.println("prefix 'u' to shout uno");
        }
    }

    @Override
    public Card play(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, ArrayList<Card> pile){
        Card playedCard;
        do {
            int card = -1;
            boolean shoutUno = false;
            do {
                this.printHand();
                Scanner scanner = new Scanner(System.in);

                try {
                    String entry = scanner.next();
                    if(entry!=null && entry.length()>0) {
                        if(entry.length()>1){
                            if("u".equals(entry.substring(0, 1))){
                                shoutUno = true;
                            }
                            card = Integer.parseInt(entry.substring(1, 2));
                        }else{
                            card = Integer.parseInt(entry);
                        }
                    }
                } catch (InputMismatchException e) {
                    card = -1;
                }
            }while(card<0 || card>this.cards.size());

            if(card==0){
                playedCard = null;
                Card c = this.drawFromPile(pile);
                if(canPlayCard(c, topColor, topActionValue, topNumberValue)){
                    String entry;
                    do {
                        if(this.cards.size()==2){
                            System.out.println("prefix 'u' to shout uno");
                        }
                        System.out.println(c.toString() + " => y to play, n to keep");
                        Scanner scanner = new Scanner(System.in);
                        entry = scanner.next();
                    }while(entry!=null && !(entry.contains("y") || entry.contains("n")));
                    if(entry!=null && entry.length()>0){
                        String doPlay = entry;
                        if(entry.length()>1){
                            if("u".equals(entry.substring(0, 1))){
                                shoutUno = true;
                            }
                            doPlay = entry.substring(1, 2);
                        }
                        if("y".equals(doPlay)) {
                            playedCard = playCard(topColor, topActionValue, topNumberValue, shoutUno, c);
                        }
                    }
                }
                break;
            }else{
                Card chosenCard = null;
                if(card<=this.cards.size()){
                    chosenCard = this.cards.get(card-1);
                }
                playedCard = playCard(topColor, topActionValue, topNumberValue, shoutUno, chosenCard);
            }
        }while(playedCard==null);
        return playedCard;
    }

    private Card playCard(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue, boolean shoutUno, Card c) {
        Card playedCard;
        playedCard = play(c, topColor, topActionValue, topNumberValue);
        if(playedCard!=null && shoutUno){
            playedCard.shoutedUno = shoutUno();
        }
        return playedCard;
    }

    @Override
    public Card.COLOR chooseColor() {
        int color;
        do {
            System.out.println("1: Blue");
            System.out.println("2: Green");
            System.out.println("3: Red");
            System.out.println("4: Yellow");

            Scanner scanner = new Scanner(System.in);

            try {
                color = scanner.nextInt();
            } catch (InputMismatchException e) {
                color = -1;
            }
        } while (color < 0 || color > 4);

        switch (color) {
            case 1:
                return Card.COLOR.BLUE;
            case 2:
                return Card.COLOR.GREEN;
            case 3:
                return Card.COLOR.RED;
            case 4:
                return Card.COLOR.YELLOW;
        }
        return chooseColor();
    }
}
