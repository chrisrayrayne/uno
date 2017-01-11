package ch.chrisrayrayne;

import java.util.Comparator;

/**
 * Created by christoph on 30.12.16.
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
        this.numberValue = value;
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
                return o1.color.toString().compareToIgnoreCase(o2.color.toString());
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
}
