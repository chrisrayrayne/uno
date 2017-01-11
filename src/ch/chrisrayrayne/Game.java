package ch.chrisrayrayne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

	private final ArrayList<Card> allCards = generateStack();
	private ArrayList<Gamer> gamers = new ArrayList<Gamer>();
	private ArrayList<Card> pile = new ArrayList<Card>();
	private ArrayList<Card> stack = new ArrayList<Card>();

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
		gamers.add(new Gamer(1, "AI 1"));
		gamers.add(new Gamer(1, "AI 1"));
		gamers.add(new Gamer(1, "AI 2"));
		gamers.add(new Gamer(1, "AI 3"));
	}

	public static void main(String[] args) {
		int i = -1;
		Gamer gamer;
		Card.COLOR topColor = null;
		Card.ACTION topActionValue = null;
		Integer topNumberValue = null;

		Game g = new Game();
		Card playedCard = g.start();
		topColor = playedCard!=null ? playedCard.color : topColor;
		topNumberValue = playedCard!=null ? playedCard.numberValue : topNumberValue;
		topActionValue = playedCard!=null ? playedCard.actionValue : topActionValue;
		boolean clockwise = true;
		g.printGame(playedCard!=null, null, false, null);
		do {
			if(g.pile.size()<10){
				Card topCard = getLastCard(g.stack);
				g.stack.remove(topCard);
				Collections.shuffle(g.stack);
				g.pile.addAll(g.stack);
				g.stack.clear();
				g.stack.add(topCard);
			}
			i = continueGamer(i, g.gamers.size(), clockwise);
			gamer = g.gamers.get(i);

			if(gamer.AIStrength<0){
				do {
					int card = -1;
					do {
						gamer.printHand();
						Scanner scanner = new Scanner(System.in);

						try {
							card = scanner.nextInt();
						} catch (InputMismatchException e) {
							card = -1;
						}
					}while(card<0 || card>gamer.cards.size());

					if(card==0){
						playedCard = null;
						gamer.drawFromPile(g.pile);
						break;
					}else{
						Card chosenCard = null;
						if(card<=gamer.cards.size()){
							chosenCard = gamer.cards.get(card-1);
						}
						playedCard = ((HumanGamer) gamer).play(chosenCard, topColor, topActionValue, topNumberValue);
					}
				}while(playedCard==null);
			}else {
				playedCard = gamer.play(topColor, topActionValue, topNumberValue, g.pile);
			}
			topColor = playedCard!=null ? playedCard.color : topColor;
			topNumberValue = playedCard!=null ? playedCard.numberValue : topNumberValue;
			topActionValue = playedCard!=null ? playedCard.actionValue : topActionValue;
			if(playedCard!=null && Card.COLOR.BLACK.equals(topColor)){
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
							topColor = Card.COLOR.BLUE;
							break;
						case 2:
							topColor = Card.COLOR.GREEN;
							break;
						case 3:
							topColor = Card.COLOR.RED;
							break;
						case 4:
							topColor = Card.COLOR.YELLOW;
							break;
					}
				}else{
					topColor = gamer.getColorWithMostCards();
				}
			}
			if(playedCard!=null && Card.ACTION.DIRECTIONCHANGE.equals(topActionValue)){
				clockwise = !clockwise;
			}
			if(playedCard!=null && Card.ACTION.SUSPEND.equals(topActionValue)){
				i = continueGamer(i, g.gamers.size(), clockwise);
			}
			if(playedCard!=null && Card.ACTION.PLUS4.equals(topActionValue)){
				i = continueGamer(i, g.gamers.size(), clockwise);
				Gamer nextGamer = g.gamers.get(i);
				ArrayList<Card> draw = new ArrayList<Card>(g.pile.subList(0,4));
				g.pile.removeAll(draw);
				nextGamer.addCards(draw);
			}
			if(playedCard!=null && Card.ACTION.PLUS2.equals(topActionValue)){
				i = continueGamer(i, g.gamers.size(), clockwise);
				Gamer nextGamer = g.gamers.get(i);
				ArrayList<Card> draw = new ArrayList<Card>(g.pile.subList(0,2));
				g.pile.removeAll(draw);
				nextGamer.addCards(draw);
			}
			g.stack.add(playedCard);
			g.printGame(playedCard!=null, topColor, false, gamer);
		}while(gamer.cards.size()>0);
		System.out.println(gamer.name + " has won");
	}

	private static int continueGamer(int i, int gamerSize, boolean clockwise) {
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
		if(printHand && g!=null && g.AIStrength<0) {
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

	private Card start() {
		pile = new ArrayList<Card>(allCards);
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
		return getLastCard(this.stack);
	}
}
