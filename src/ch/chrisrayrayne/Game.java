package ch.chrisrayrayne;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chrisrayrayne on 30.12.16.
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
			cards.add(new Card(i/2, Card.COLOR.BLUE));
			cards.add(new Card(i/2, Card.COLOR.GREEN));
			cards.add(new Card(i/2, Card.COLOR.RED));
			cards.add(new Card(i/2, Card.COLOR.YELLOW));
		}
		for(int i=0; i<2; i++){
			cards.add(new Card(Card.ACTION.DIRECTIONCHANGE, Card.COLOR.BLUE));
			cards.add(new Card(Card.ACTION.DIRECTIONCHANGE, Card.COLOR.GREEN));
			cards.add(new Card(Card.ACTION.DIRECTIONCHANGE, Card.COLOR.RED));
			cards.add(new Card(Card.ACTION.DIRECTIONCHANGE, Card.COLOR.YELLOW));

			cards.add(new Card(Card.ACTION.PLUS2, Card.COLOR.BLUE));
			cards.add(new Card(Card.ACTION.PLUS2, Card.COLOR.GREEN));
			cards.add(new Card(Card.ACTION.PLUS2, Card.COLOR.RED));
			cards.add(new Card(Card.ACTION.PLUS2, Card.COLOR.YELLOW));

			cards.add(new Card(Card.ACTION.SUSPEND, Card.COLOR.BLUE));
			cards.add(new Card(Card.ACTION.SUSPEND, Card.COLOR.GREEN));
			cards.add(new Card(Card.ACTION.SUSPEND, Card.COLOR.RED));
			cards.add(new Card(Card.ACTION.SUSPEND, Card.COLOR.YELLOW));
		}
		for(int i=0; i<4; i++){
			cards.add(new Card(Card.ACTION.COLORCHANGE, Card.COLOR.BLACK));
			cards.add(new Card(Card.ACTION.PLUS4, Card.COLOR.BLACK));
		}
		return cards;
	}

	public Game(){
		//gamers.add(new HumanGamer("Player"));
		gamers.add(new AI1Gamer("AI 1"));
		gamers.add(new AI1Gamer("AI 1"));
		gamers.add(new AI1Gamer("AI 2"));
		gamers.add(new AI1Gamer("AI 3"));
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
		System.out.flush();
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

			this.stack.add(playedCard);
			playedCard.action(this, this.gamer);
			this.printGame(playedCard != null, this.topColor, false, this.gamer);
		} while (this.gamer.cards.size() > 0);
		System.out.println(this.gamer.name + " has won");
	}
}
