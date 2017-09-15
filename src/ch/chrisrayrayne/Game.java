package ch.chrisrayrayne;

import ch.chrisrayrayne.card.*;
import ch.chrisrayrayne.gamer.AI0Gamer;
import ch.chrisrayrayne.gamer.AI1Gamer;
import ch.chrisrayrayne.gamer.Gamer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chrisrayrayne on 30.12.16.
 * Main Game of Uno / Game-Leader/Referee
 */
public class Game {
	private final ArrayList<Card> allCards = generateStack();
	public ArrayList<Gamer> gamers = new ArrayList<>();
	private ArrayList<Card> pile = new ArrayList<>();
	private ArrayList<Card> stack = new ArrayList<>();

	private int i = -1;
	private Gamer gamer;
	private Card.COLOR topColor = null;
	private Card.ACTION topActionValue = null;
	private Integer topNumberValue = null;
	private boolean clockwise = true;

	// Official Rules
	@SuppressWarnings("unused")
	private boolean jumpIn = false;
	@SuppressWarnings("unused")
	private boolean partners = false;
	@SuppressWarnings("unused")
	private boolean sevenO = false;
	@SuppressWarnings("unused")
	private boolean doubleing = false;
	@SuppressWarnings("unused")
	private boolean points = false;
	@SuppressWarnings("unused")
	private boolean cummulate = false;

	// Enhanced Rules
	@SuppressWarnings("unused")
	private boolean fastDouble = false;
	@SuppressWarnings("unused")
	private boolean jumpInBlack = false;
	@SuppressWarnings("unused")
	private boolean jumpInActioncards = false;
	public final boolean drawToMatch = true;
	@SuppressWarnings("unused")
	private boolean retourRetour = false;
	@SuppressWarnings("unused")
	private boolean letMeBe = false;
	@SuppressWarnings("unused")
	private boolean sleep = false;
	@SuppressWarnings("unused")
	private boolean finishPlaying = false;
	@SuppressWarnings("unused")
	private boolean unoUno = false;
	@SuppressWarnings("unused")
	private boolean brunoKunoUno = false;
	@SuppressWarnings("unused")
	private boolean complementaryUno = false;
	@SuppressWarnings("unused")
	private boolean pullBackIn = false;
	@SuppressWarnings("unused")
	private boolean cummulateBlock = false;
	@SuppressWarnings("unused")
	private boolean playAfterDrawActioncards = false;

	private ArrayList<Card> generateStack() {
		ArrayList<Card> cards = new ArrayList<>();
		for (int i = 1; i < 20; i++) {
			cards.add(new NumberCard(i / 2, Card.COLOR.BLUE));
			cards.add(new NumberCard(i / 2, Card.COLOR.GREEN));
			cards.add(new NumberCard(i / 2, Card.COLOR.RED));
			cards.add(new NumberCard(i / 2, Card.COLOR.YELLOW));
		}
		for (int i = 0; i < 2; i++) {
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
		for (int i = 0; i < 4; i++) {
			cards.add(new ColorChangeCard());
			cards.add(new Plus4Card());
		}
		return cards;
	}

	public Game() {
		gamers.add(new AI1Gamer("AI 11"));
		gamers.add(new AI1Gamer("AI 12"));
		gamers.add(new AI0Gamer("AI 01"));
		gamers.add(new AI0Gamer("AI 02"));
		//gamers.add(new HumanGamer("Player"));
	}

	private int continueGamer(int i) {
		if (this.clockwise) {
			i++;
			if (i >= this.gamers.size()) {
				i = 0;
			}
		} else {
			i--;
			if (i < 0) {
				i = this.gamers.size() - 1;
			}
		}
		return i;
	}

	public Gamer continueGamer() {
		this.i = this.continueGamer(this.i);
		return this.gamers.get(this.i);
	}

	private void printGame(boolean printStack, Card.COLOR color, boolean printHand, Gamer g) {
		//TODO Flush screen first
		if (printHand && g != null && g.isHumanPlayer()) {
			g.printHand();
		} else {
			for (Gamer gamer : this.gamers) {
				System.out.print(gamer.name + ": " + gamer.cards.size() + " / ");
			}
		}
		if (printStack) {
			Card topCard = getLastCard(stack);
			System.out.print("Stack: " + topCard.toString());
			if (topCard.getColor().equals(Card.COLOR.BLACK)) {
				System.out.print(" wished " + color);
			}
		}
		System.out.println();
	}

	private static Card getLastCard(ArrayList<Card> stack) {
		return stack.get(stack.size() - 1);
	}

	@SuppressWarnings("unused")
	private void printoutPile() {
		System.out.println("Pile");
		for (int i = 0; i < this.pile.size(); i++) {
			System.out.println((i + 1) + ": " + this.pile.get(i).toString());
		}
	}

	@SuppressWarnings("unused")
	private void printoutStack() {
		System.out.println("Stack");
		for (int i = 0; i < this.stack.size(); i++) {
			System.out.println((i + 1) + ": " + this.stack.get(i).toString());
		}
	}

	@SuppressWarnings("unused")
	private void printoutAllPlayer() {
		for (Gamer g : gamers) {
			g.printHand();
		}
	}

	void init() {
		this.pile = new ArrayList<>(this.allCards);
		Collections.shuffle(this.pile);
		for(int i=0; i < 7; i++){
			for (Gamer g : gamers) {
				g.addCards(this.drawFromPile(1));
			}
		}

		Card playedCard = this.pile.get(0);
		this.stack.add(playedCard);
		this.pile.remove(playedCard);
		this.topColor = playedCard != null ? playedCard.getColor() : topColor;
		this.topNumberValue = playedCard != null ? playedCard.getNumber() : topNumberValue;
		this.topActionValue = playedCard != null ? playedCard.getAction() : topActionValue;

		playedCard.action(this, this.gamer);

		this.printGame(playedCard != null, null, false, null);
	}

	void play() {
		do {
			if (this.pile.size() < 10) {
				moveFromStackToPileAndShuffle();
			}
			this.gamer = continueGamer();

			playRound();
			int sumCards = this.pile.size() + this.stack.size();
			for (Gamer tempG : this.gamers) {
				sumCards += tempG.cards.size();
			}
			if (sumCards != 108) {
				throw new IllegalStateException("sumCards is not correct " + sumCards);
			}
		} while (this.gamer.cards.size() > 0);
		System.out.println(this.gamer.name + " has won");
	}

	private void playRound() {
		Card playedCard = gamerPlay();

		if (playedCard != null) {
            if (this.gamer.shoutedUno) {
                this.gamer.shoutedUno = false;
            } else if (this.gamer.cards.size() == 1) {
                this.gamer.addCards(this.drawFromPile(2));
            }
			playedCard.action(this, this.gamer);
        }

		this.printGame(playedCard != null, this.topColor, false, this.gamer);
	}

	private Card gamerPlay() {
		Card playedCard = this.gamer.play(this);

		this.topColor = playedCard != null ? playedCard.getColor() : this.topColor;
		this.topNumberValue = playedCard != null ? playedCard.getNumber() : this.topNumberValue;
		this.topActionValue = playedCard != null ? playedCard.getAction() : this.topActionValue;

		if (playedCard != null) {
			this.stack.add(playedCard);
		}
		return playedCard;
	}

	private void moveFromStackToPileAndShuffle() {
		Card topCard = getLastCard(this.stack);
		this.stack.remove(topCard);
		Collections.shuffle(this.stack);
		this.pile.addAll(this.stack);
		this.stack.clear();
		this.stack.add(topCard);
	}

	public ArrayList<Card> drawFromPile(int count) {
		ArrayList<Card> draw;
		if (this.pile.size() < count) {
			draw = new ArrayList<>(this.pile);
		} else {
			draw = new ArrayList<>(this.pile.subList(0, count));
		}
		this.pile.removeAll(draw);
		return draw;
	}

	public Card.COLOR getTopColor() {
		return this.topColor;
	}

	public Integer getTopNumber() {
		return this.topNumberValue;
	}

	public Card.ACTION getTopAction() {
		return this.topActionValue;
	}

	public void reverseGameDirection() {
		this.clockwise = !this.clockwise;
	}

	public boolean canPlayCard(final Card card, final ArrayList<Card> cards) {
		if (cards != null && cards.contains(card)) {
			if (topActionValue != null && topActionValue.equals(Card.ACTION.PLUS4) && (card instanceof Plus2Card || card instanceof Plus4Card)) {
				return false;
			}
			if (topActionValue != null && topActionValue.equals(Card.ACTION.COLORCHANGE) && card instanceof ColorChangeCard) {
				return false;
			}
			if (Card.COLOR.BLACK.equals(card.getColor()) && cards.size() == 1) {
				return false;
			}

			if (card.isSameColorOrValue(this.topColor, this.topActionValue, topNumberValue)) {
				return true;
			}
			if (Card.COLOR.BLACK.equals(card.getColor()) && Card.ACTION.PLUS4.equals(card.getAction())) {
				for (Card c : cards) {
					if (!c.equals(card)) {
						if (c.getAction() != null && c.isSameColorOrValue(topColor, topActionValue, topNumberValue)) {
							return false;
						}
					}
				}
				return true;
			}
			if (Card.COLOR.BLACK.equals(this.getTopColor())) {
				return true;
			}
		}
		return false;
	}

	public void setTopColor(Card.COLOR topColor) {
		this.topColor = topColor;
	}

	public boolean matchesCard(Card card) {
		if (Card.COLOR.BLACK.equals(card.getColor())) {
			return true;
		}
		if (card.isSameColorOrValue(this.topColor, this.topActionValue, topNumberValue)) {
			return true;
		}
		return false;
	}
}