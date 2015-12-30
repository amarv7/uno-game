package uno.game.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import uno.game.constants.CardType;
import uno.game.constants.UnoConstants;
import uno.game.entities.Card;
import uno.game.entities.Player;
import uno.game.utility.CircularLinkedList;
import uno.game.utility.UnoFacade;
import uno.game.utility.UnoFutureCallback;

/**
 * The Class GameManagerService provides all the services to play the UNO game.
 * 
 * @author Amar Verma
 */
public class GameManagerService {

    private LinkedList<Card> drawPile;
    private LinkedList<Card> discardPile;
    private CircularLinkedList<Player> allPlayers;
    private List<Player> playersList;
    private boolean isPlayClockwise;
    private UnoFutureCallback unoFutureCallback;

    public GameManagerService() {
	playersList = new ArrayList<>(0);
	resetGame();
    }

    /**
     * Method distributes cards equally to all the registered players.
     *
     * @return Number of total distributed cards
     */
    public int distributeCards() {
	findDealer();
	allPlayers.addAll(playersList);
	final int totalCardsToDistribute = allPlayers.size() * UnoConstants.CARDS_TO_DISTRIBUTE;
	for (int i = 0; i < totalCardsToDistribute; i++) {
	    Player player = allPlayers.next();
	    player.addCard(drawPile.pollLast());
	}
	allPlayers.resetToFirst();
	return totalCardsToDistribute;
    }

    /**
     * Add the player to the players list
     *
     * @param playerName
     *            the player name
     * @return true, if adds the player
     */
    public boolean addPlayer(String playerName) {
	Player player = new Player(playerName);
	if (!playersList.contains(player)) {
	    playersList.add(player);
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Gets the player from the player list
     *
     * @param playerName
     *            the player name
     * @return the player object
     */
    public Player getPlayer(String playerName) {
	for (Player player : playersList) {
	    if (player.getPlayerName().equals(playerName)) {
		return player;
	    }
	}
	return null;
    }

    /**
     * Removes the player from player's list
     *
     * @param playerName
     *            the player name
     */
    public void removePlayer(String playerName) {
	Player player = getPlayer(playerName);
	playersList.remove(player);
	allPlayers.remove(player);
    }

    /**
     * Method sort the players list in descending order, on the basis of
     * comparable provided for high priority players. First player is the dealer
     * in sorted players list.
     */
    private void findDealer() {
	Collections.sort(playersList);
    }

    /**
     * Pick card method allows the player to pick a random card from draw pile
     * to set his priority.
     *
     * @return the card
     */
    public Card pickCard() {
	Random randomGenerator = new Random();
	Card card;
	while (true) {
	    int randomInt = randomGenerator.nextInt(drawPile.size());
	    if (randomInt < drawPile.size()) {
		card = drawPile.remove(randomInt);
		drawPile.addLast(card);
		break;
	    }
	}
	return card;
    }

    /**
     * Sets the player priority on the bases of randomly picked card.
     *
     * @param playerName
     *            the player name
     * @param score
     *            the score
     */
    public void setPlayerPriority(String playerName, int score) {
	Player player = getPlayer(playerName);
	player.setPlayerPriority(score);
    }

    /**
     * Start game, entry point for the UNO Game services.
     *
     * @return true, if start game
     */
    public boolean startGame() {
	Card topCard = drawPile.peekLast();
	if (topCard.getCardType() == CardType.WILD_CARD || topCard.getCardType() == CardType.WILD_DRAW_FOUR_CARD) {
	    shuffleCards();
	    return false;
	}
	checkIfReverseCard(topCard);
	discardPile.addLast(drawPile.pollLast());
	return true;
    }

    /**
     * Gets the discard pile last card.
     *
     * @return the discard pile last card
     */
    public Card getDiscardPileLastCard() {
	if (discardPile.size() > 0) {
	    return discardPile.getLast();
	}
	return null;
    }

    /**
     * Gets the all cards from the draw pile.
     *
     * @return the all cards
     */
    public String[] getAllCards() {
	String[] cardsArr = new String[drawPile.size()];
	for (int i = 0; i < drawPile.size(); i++) {
	    cardsArr[i] = drawPile.get(i).getCardName();
	}
	return cardsArr;
    }

    /**
     * Shuffle the cards.
     */
    public void shuffleCards() {
	UnoFacade.shuffleCards(drawPile);
    }

    /**
     * Draw card from the draw pile. If draw pile is empty it will intimate to
     * Gui layer to recreate its draw pile.
     *
     * @return the card
     */
    public Card drawCard() {
	Card card = null;
	if (!drawPile.isEmpty()) {
	    card = drawPile.pollLast();
	}
	if (drawPile.isEmpty()) {
	    unoFutureCallback.doTask();
	}
	return card;
    }

    /**
     * Discard the player's card. Before discarding it validates whether Player
     * played right card or not.
     *
     * @param playerCard
     *            the player card
     * @return true, if discard is successful
     */
    public boolean discard(Card playerCard) {
	boolean isValidated = UnoFacade.validateCard(playerCard, discardPile.peekLast());
	if (isValidated) {
	    checkIfReverseCard(playerCard);
	    discardPile.addLast(playerCard);
	    return true;
	}
	return false;
    }

    /**
     * Reverse current player in the sequence, change the current player pointer
     * in CircularLinkedList to next or previous.
     *
     * @return the next of previous player
     */
    private Player reverseCurrentPlayer() {
	if (isPlayClockwise) {
	    return allPlayers.next();
	} else {
	    return allPlayers.previous();
	}
    }

    /**
     * Recreate Draw pile, if draw pile is empty. All the cards from discard
     * pile will added to draw pile.
     */
    public void recreateDrawPile() {
	drawPile.addAll(discardPile);
	UnoFacade.shuffleCards(drawPile);
	discardPile.clear();
	unoFutureCallback.doTask();
    }

    /**
     * Check if reverse card and change the playing sequence to clockwise or
     * vice versa.
     *
     * @param card
     *            the card
     */
    private void checkIfReverseCard(Card card) {
	if (card.getCardType() == CardType.REVERSE_CARD) {
	    if (isPlayClockwise) {
		isPlayClockwise = false;
	    } else {
		isPlayClockwise = true;
	    }
	    reverseCurrentPlayer();
	}
    }

    /**
     * Gets the score for winner, adds all the cards scores from other players
     * and adds to Winner score.
     *
     * @param winnerPlayer
     *            the winner player
     * @return the score for winner
     */
    public int getScoreForWinner(Player winnerPlayer) {
	int score = 0;
	for (Player player : playersList) {
	    if (!player.equals(winnerPlayer)) {
		score += player.getAllCardsScore();
	    }
	}
	winnerPlayer.setScore(score);
	return score;

    }

    /**
     * Reset the Game, make the services ready to play from the beginning.
     */
    public void resetGame() {
	drawPile = UnoFacade.getCards();
	discardPile = new LinkedList<>();
	playersList = new ArrayList<>(0);
	allPlayers = new CircularLinkedList<>();
	isPlayClockwise = true;
    }

    /**
     * Gets the next player, if clockwise next player will be returned otherwise
     * previous player.
     *
     * @return the next player
     */
    public Player getNextPlayer() {
	if (isPlayClockwise) {
	    return allPlayers.next();
	} else {
	    return allPlayers.previous();
	}
    }

    /**
     * Adds the recreate draw pile listener, callback will be called whenever
     * draw pile will be empty. Draw pile will be shuffle and recreated from
     * taking all cards from discard pile, intern intimate to Gui draw pile to
     * recreate its draw pile alo.
     *
     * @param unoFutureCallback
     *            the uno future callback
     */
    public void addRecreateDrawPileListener(UnoFutureCallback unoFutureCallback) {
	this.unoFutureCallback = unoFutureCallback;
    }
}
