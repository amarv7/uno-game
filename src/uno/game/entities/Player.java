package uno.game.entities;

import java.util.ArrayList;
import java.util.List;

import uno.game.constants.UnoConstants;

/**
 * The Class Player, for each player its instance will be created and will be
 * added in CircularLinkedList<Player>, which facilitates players to play in
 * Round robin pattern. Comparable implementation has been provided to Sort the
 * list on player's priority bases.
 * 
 * @author Amar Verma
 */
public class Player implements Comparable<Player> {

    private String playerName;
    private int playerPriority;
    private int score;
    private List<Card> listOfCards;
    private boolean canPlay;
    private int draw;
    private boolean announcedUNO;

    /**
     * The Constructor of Player class, take the player's name and initialized
     * its required member variables.
     *
     * @param playerName
     *            the player name
     */
    public Player(String playerName) {
	this.playerName = playerName;
	listOfCards = new ArrayList<>(UnoConstants.CARDS_TO_DISTRIBUTE);
	canPlay = true;
	draw = UnoConstants.DRAW_NONE;
    }

    /**
     * Gets the player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
	return playerName;
    }

    /**
     * Sets the player name.
     *
     * @param playerName
     *            the player name
     */
    public void setPlayerName(String playerName) {
	this.playerName = playerName;
    }

    /**
     * Gets the player priority.
     *
     * @return the player priority
     */
    public int getPlayerPriority() {
	return playerPriority;
    }

    /**
     * Sets the player priority.
     *
     * @param playerPriority
     *            the player priority
     */
    public void setPlayerPriority(int playerPriority) {
	this.playerPriority = playerPriority;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
	return score;
    }

    /**
     * Gets the all cards score.
     *
     * @return the all cards score
     */
    public int getAllCardsScore() {
	int sum = 0;
	for (Card card : listOfCards) {
	    sum += card.getScore();
	}
	return sum;
    }

    /**
     * Sets the score.
     *
     * @param score
     *            the score
     */
    public void setScore(int score) {
	this.score = score;
    }

    /**
     * Gets the list of cards.
     *
     * @return the list of cards
     */
    public List<Card> getListOfCards() {
	return listOfCards;
    }

    /**
     * Sets the list of cards.
     *
     * @param listOfCards
     *            the list of cards
     */
    public void setListOfCards(List<Card> listOfCards) {
	this.listOfCards = listOfCards;
    }

    /**
     * Adds the card to the cards list, validates if there are no more cards to
     * draw then unblock the player to play. Also sets announce as false if
     * Player unable to play its last card and had to draw card.
     *
     * @param card
     *            the card
     */
    public void addCard(Card card) {
	listOfCards.add(card);
	if (!canPlay) {
	    if (--draw == UnoConstants.DRAW_NONE) {
		canPlay = true;
	    }
	}
	if (getRemainingCards() > 1) {
	    announcedUNO = false;
	}
    }

    /**
     * Discard the players card.
     *
     * @param card
     *            the card
     * @return the card
     */
    public Card discard(Card card) {
	listOfCards.remove(card);
	return card;
    }

    /**
     * Gets the card from the cards list.
     *
     * @param cardName
     *            the card name
     * @return the card
     */
    public Card getCard(String cardName) {
	Card cardToRemove = null;
	for (Card card : listOfCards) {
	    if (card.getCardName().equals(cardName)) {
		cardToRemove = card;
		break;
	    }
	}
	return cardToRemove;
    }

    /**
     * Gets the number of remaining cards of a player.
     *
     * @return the remaining cards
     */
    public int getRemainingCards() {
	return listOfCards.size();
    }

    /**
     * Can Player play his card. On some scenarios player has to be blocked to
     * play card e.g. If Player has to forces to draw2/draw4 cards from draw
     * pile, or player discarded wild/wild4 cards and required Announce a color.
     *
     * @return true, if can play
     */
    public boolean canPlay() {
	return canPlay;
    }

    /**
     * Sets the can player play the card.
     *
     * @param canPlay
     *            the can player play
     */
    public void setCanPlay(boolean canPlay) {
	this.canPlay = canPlay;
    }

    /**
     * Gets the number of cards to draw
     *
     * @return the draw
     */
    public int getDraw() {
	return draw;
    }

    /**
     * Sets the cards to draw, force the player to draw cards from draw pile
     * until player will be blocked to play.
     *
     * @param draw
     *            the draw
     */
    public void setDraw(int draw) {
	if (draw > UnoConstants.DRAW_NONE) {
	    canPlay = false;
	} else if (draw == UnoConstants.DRAW_NONE) {
	    canPlay = true;
	}
	this.draw = draw;
    }

    /**
     * Sets the announced uno as true if player has only one card remaining.
     *
     * @param announcedUNO
     *            the announced uno
     */
    public void setAnnouncedUNO(boolean announcedUNO) {
	this.announcedUNO = announcedUNO;
    }

    /**
     * Checks if announced uno is true.
     *
     * @return true, if checks if is announced uno
     */
    public boolean isAnnouncedUNO() {
	return announcedUNO;
    }

    @Override
    public int compareTo(Player obj) {
	int result = 0;
	if (this.playerPriority > obj.getPlayerPriority()) {
	    result = -1;
	} else if (this.playerPriority < obj.getPlayerPriority()) {
	    result = 1;
	} else {
	    result = 0;
	}
	return result;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Player other = (Player) obj;
	if (playerName == null) {
	    if (other.playerName != null)
		return false;
	} else if (!playerName.equals(other.playerName))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Player [playerName=" + playerName + ", playerPriority=" + playerPriority + ", score=" + score + ", listOfCards=" + listOfCards + "]";
    }
}
