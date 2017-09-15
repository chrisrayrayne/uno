package ch.chrisrayrayne.gamer;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.card.Card;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanGamer extends Gamer {

    public HumanGamer(String nameValue){
        super(nameValue);
    }

    private Card play(Game game, Card chosenCard){
        if (game.canPlayCard(chosenCard, this.cards)){
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
    public Card play(Game game){
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
                            card = Integer.parseInt(entry.substring(1));
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
                Card c = this.drawFromPile(game);
                if(game.canPlayCard(c, this.cards)){
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
                            doPlay = entry.substring(1);
                        }
                        if("y".equals(doPlay)) {
                            playedCard = playCard(game, c, shoutUno);
                        }
                    }
                }
                break;
            }else{
                Card chosenCard = null;
                if(card<=this.cards.size()){
                    chosenCard = this.cards.get(card-1);
                }
                playedCard = playCard(game, chosenCard, shoutUno);
            }
        }while(playedCard==null);
        return playedCard;
    }

    private Card playCard(Game game, Card c, boolean shoutUno) {
        Card playedCard;
        playedCard = play(game, c);
        this.shoutedUno = playedCard != null && shoutUno && shoutUno();
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
