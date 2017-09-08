package ch.chrisrayrayne;

import ch.chrisrayrayne.card.*;
import ch.chrisrayrayne.gamer.AI1Gamer;
import ch.chrisrayrayne.gamer.Gamer;
import ch.chrisrayrayne.gamer.HumanGamer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chrisrayrayne on 30.12.16.
 * Main Game of Uno / Game-Leader/Referee
 */
public class Game {
	private final ArrayList<Card> allCards = generateStack();
	public ArrayList<Gamer> gamers = new ArrayList<Gamer>();
	public ArrayList<Card> pile = new ArrayList<Card>();
	public ArrayList<Card> stack = new ArrayList<Card>();

	public int i = -1;
	public Gamer gamer;
	public Card.COLOR topColor = null;
	public Card.ACTION topActionValue = null;
	public Integer topNumberValue = null;
	public boolean clockwise = true;

	private ArrayList<Card> generateStack() {
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int i=1; i<20; i++){
			cards.add(new NumberCard(i/2, Card.COLOR.BLUE));
			cards.add(new NumberCard(i/2, Card.COLOR.GREEN));
			cards.add(new NumberCard(i/2, Card.COLOR.RED));
			cards.add(new NumberCard(i/2, Card.COLOR.YELLOW));
		}
		for(int i=0; i<2; i++){
			cards.add(new DirectionChangeCard(Card.COLOR.BLUE));
			cards.add(new DirectionChangeCard(Card.COLOR.GREEN));
			cards.add(new DirectionChangeCard(Card.COLOR.RED));
			cards.add(new DirectionChangeCard(Card.COLOR.YELLOW));

			cards.add(new Plus2Card(Card.COLOR.BLUE));
			cards.add(new Plus2Card(Card.COLOR.GREEN));
			cards.add(new Plus2Card(Card.COLOR.RED));
			cards.add(new Plus2Card(Card.COLOR.YELLOW));

			cards.add(new SuspendCard(Card.COLOR.BLUE));
			cards.add(new SuspendCard(Card.COLOR.GREEN));
			cards.add(new SuspendCard(Card.COLOR.RED));
			cards.add(new SuspendCard(Card.COLOR.YELLOW));
		}
		for(int i=0; i<4; i++){
			cards.add(new ColorChangeCard());
			cards.add(new Plus4Card());
		}
		return cards;
	}

	public Game(){
		gamers.add(new AI1Gamer("AI 1"));
		gamers.add(new AI1Gamer("AI 2"));
		gamers.add(new AI1Gamer("AI 3"));
		gamers.add(new HumanGamer("Player"));
	}

	public static int continueGamer(int i, int gamerSize, boolean clockwise) {
		if(clockwise) {
            i++;
            if (i >= gamerSize) {
                i = 0;
            }
        }else{
            i--;
            if (i < 0) {
                i = gamerSize-1;
            }
        }
		return i;
	}

	private void printGame(boolean printStack, Card.COLOR color, boolean printHand, Gamer g) {
		//TODO Flush screen first
		if(printHand && g!=null && g.isHumanPlayer()) {
			g.printHand();
		}else{
			for(Gamer gamer: this.gamers) {
				System.out.print(gamer.name + ": " + gamer.cards.size() + " / ");
			}
		}
		if(printStack) {
			Card topCard = getLastCard(stack);
			System.out.print("Stack: " + topCard.toString());
			if(topCard.color.equals(Card.COLOR.BLACK)){
				System.out.print(" wished " + color);
			}
		}
		System.out.println();
	}

	private static Card getLastCard(ArrayList<Card> stack) {
		return stack.get(stack.size() - 1);
	}

	private void printoutPile() {
		System.out.println("Pile");
		for (int i = 0; i < this.pile.size(); i++){
			System.out.println((i+1) + ": " + this.pile.get(i).toString());
		}
	}

	private void printoutStack() {
		System.out.println("Stack");
		for (int i = 0; i < this.stack.size(); i++){
			System.out.println((i+1) + ": " + this.stack.get(i).toString());
		}
	}

	private void printoutAllPlayer() {
		for(Gamer g: gamers){
			g.printHand();
		}
	}

	public void init() {
		this.pile = new ArrayList<Card>(allCards);
		Collections.shuffle(pile);
		for(int i=0; i<3; i++){
			for(Gamer g: gamers) {
				ArrayList<Card> draw = new ArrayList<Card>();
				for (int o = 0; (i < 2 ? o < 3 : o < 1); o++) {
					draw.add(pile.get(0));
					pile.remove(0);
				}
				g.addCards(draw);
			}
		}
		Card c = this.pile.get(0);
		this.stack.add(c);
		this.pile.remove(c);
		while(getLastCard(this.stack).actionValue!=null) {
			this.stack.add(this.pile.get(0));
			this.pile.remove(0);
		}
		Card playedCard = getLastCard(this.stack);
		this.topColor = playedCard != null ? playedCard.color : topColor;
		this.topNumberValue = playedCard != null ? playedCard.numberValue : topNumberValue;
		this.topActionValue = playedCard != null ? playedCard.actionValue : topActionValue;
		this.printGame(playedCard != null, null, false, null);
	}

	public void play(){
		do {
			if (this.pile.size() < 10) {
				Card topCard = getLastCard(this.stack);
				this.stack.remove(topCard);
				Collections.shuffle(this.stack);
				this.pile.addAll(this.stack);
				this.stack.clear();
				this.stack.add(topCard);
			}
			this.i = continueGamer(this.i, this.gamers.size(), this.clockwise);
			this.gamer = this.gamers.get(this.i);

			Card playedCard = this.gamer.play(this.topColor, this.topActionValue, this.topNumberValue, this.pile);
            this.topColor = playedCard != null ? playedCard.color : this.topColor;
            this.topNumberValue = playedCard != null ? playedCard.numberValue : this.topNumberValue;
            this.topActionValue = playedCard != null ? playedCard.actionValue : this.topActionValue;

            if(playedCard!=null) {
				this.stack.add(playedCard);
				playedCard.action(this, this.gamer);
				if(playedCard.shoutedUno){
					playedCard.shoutedUno = false;
				}else if(this.gamer.cards.size()==1){
					ArrayList<Card> draw = new ArrayList<Card>(this.pile.subList(0, Math.max(0, this.pile.size()-1)));
					this.pile.removeAll(draw);
					this.gamer.addCards(draw);
				}
			}
			this.printGame(playedCard != null, this.topColor, false, this.gamer);
			int sumCards = this.pile.size() + this.stack.size();
            for(Gamer tempG: this.gamers){
				sumCards += tempG.cards.size();
			}
			if(sumCards!=108){
            	throw new IllegalStateException("sumCards is not correct " + sumCards);
			}
		} while (this.gamer.cards.size() > 0);
		System.out.println(this.gamer.name + " has won");
	}
}