package ch.chrisrayrayne;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by chrisrayrayne on 30.12.16.
 */
public class Card {

    public static enum COLOR{BLACK, BLUE, GREEN, RED, YELLOW}
    public static enum ACTION{COLORCHANGE, PLUS2, PLUS4, DIRECTIONCHANGE, SUSPEND}

    public ACTION actionValue;
    public Integer numberValue;
    public COLOR color;

    public Card(ACTION value, COLOR color){
        this.actionValue = value;
        this.color = color;
    }

    public Card(int value, COLOR color){
        this.numberValue = new Integer(value);
        this.color = color;
    }

    public int compare(Card card){
        return 1;
    }

    public static Comparator<Card> sort = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            if(o1==null && o2==null){
                return 0;
            }
            if(o1!=null && o2==null){
                return 1;
            }
            if(o1==null && o2!=null){
                return -1;
            }
            if(o1.color.equals(o2.color)){
                if(o1.numberValue!=null && o2.numberValue!=null){
                    return o1.numberValue.compareTo(o2.numberValue);
                }else if(o1.actionValue!=null && o2.actionValue!=null){
                    return o1.actionValue.toString().compareToIgnoreCase(o2.actionValue.toString());
                }else if(o1.numberValue!=null && o2.actionValue!=null){
                    return -1;
                }else if(o2.numberValue!=null && o1.actionValue!=null){
                    return 1;
                }
            }else{
                if(o1.color==COLOR.BLACK && o2.color!=COLOR.BLACK){
                    return 1;
                }else if(o1.color!=COLOR.BLACK && o2.color==COLOR.BLACK){
                    return -1;
                }else{
                    return o1.color.toString().compareToIgnoreCase(o2.color.toString());
                }
            }
            return o1.toString().compareToIgnoreCase(o2.toString());
        }
    };

    public boolean isSameColorOrValue(Card.COLOR topColor, Card.ACTION topActionValue, Integer topNumberValue) {
        return (this.color.equals(topColor)) ||
                (topActionValue!=null && topActionValue.equals(this.actionValue)) ||
                (topNumberValue!=null && topNumberValue.equals(this.numberValue));
    }

    public String toString(){
        return (this.color!=null ? this.color : "") + " " + (this.numberValue!=null ? this.numberValue : (this.actionValue!=null ? this.actionValue : ""));
    }

    public void action(Game game, Gamer gamer){
        if(this!=null && Card.COLOR.BLACK.equals(game.topColor)){
            if(gamer.AIStrength<0){
                int color = -1;
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
                }while(color<0 || color>4);

                switch(color){
                    case 1:
                        game.topColor = Card.COLOR.BLUE;
                        break;
                    case 2:
                        game.topColor = Card.COLOR.GREEN;
                        break;
                    case 3:
                        game.topColor = Card.COLOR.RED;
                        break;
                    case 4:
                        game.topColor = Card.COLOR.YELLOW;
                        break;
                }
            }else{
                game.topColor = gamer.getColorWithMostCards();
            }
        }
        if(this!=null && Card.ACTION.DIRECTIONCHANGE.equals(game.topActionValue)){
            game.clockwise = !game.clockwise;
        }
        if(this!=null && Card.ACTION.SUSPEND.equals(game.topActionValue)){
            game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
        }
        if(this!=null && Card.ACTION.PLUS4.equals(game.topActionValue)){
            game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
            Gamer nextGamer = game.gamers.get(game.i);
            ArrayList<Card> draw = new ArrayList<Card>(game.pile.subList(0,4));
            game.pile.removeAll(draw);
            nextGamer.addCards(draw);
        }
        if(this!=null && Card.ACTION.PLUS2.equals(game.topActionValue)){
            game.i = game.continueGamer(game.i, game.gamers.size(), game.clockwise);
            Gamer nextGamer = game.gamers.get(game.i);
            ArrayList<Card> draw = new ArrayList<Card>(game.pile.subList(0,2));
            game.pile.removeAll(draw);
            nextGamer.addCards(draw);
        }
    }
}