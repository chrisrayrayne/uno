package ch.chrisrayrayne.card;

import ch.chrisrayrayne.Game;
import ch.chrisrayrayne.gamer.Gamer;

import java.util.Comparator;

public abstract class Card {

    public enum COLOR{BLACK, BLUE, GREEN, RED, YELLOW}
    public enum ACTION{COLORCHANGE, PLUS2, PLUS4, DIRECTIONCHANGE, SUSPEND}

    private ACTION actionValue;
    private Integer numberValue;
    private COLOR color;
    private int points;

    public Card(COLOR colorValue, ACTION actionValue, Integer numberValue, int pointsValue){
        this.color = colorValue;
        this.actionValue = actionValue;
        this.numberValue = numberValue;
        this.points = pointsValue;
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
            if(o1==null){
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
        return this.isSameColor(topColor) || this.isSameValue(topActionValue, topNumberValue);
    }

    public boolean isSameColor(Card.COLOR topColor) {
        return this.color.equals(topColor);
    }

    public boolean isSameValue(Card.ACTION topActionValue, Integer topNumberValue) {
        return (topActionValue!=null && topActionValue.equals(this.actionValue)) ||
                (topNumberValue!=null && topNumberValue.equals(this.numberValue));
    }

    public String toString(){
        return (this.color!=null ? this.color : "") + " " + (this.numberValue!=null ? this.numberValue : (this.actionValue!=null ? this.actionValue : ""));
    }

    public abstract void action(Game game, Gamer gamer);

    @SuppressWarnings("unused")
    public int getPoints() {
        return this.points;
    }

    public COLOR getColor() {
        return this.color;
    }

    public ACTION getAction() {
        return this.actionValue;
    }

    public Integer getNumber() {
        return this.numberValue;
    }
}
