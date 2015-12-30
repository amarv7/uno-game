package uno.game.entities;

import uno.game.constants.CardColors;
import uno.game.constants.CardType;
import uno.game.constants.UnoConstants;

/**
 * The Card class, contains the properties to be set required for playing the
 * Game. As objects to be added in to draw pile, discard pile and distributed to
 * all players.
 * 
 * @author Amar Verma
 */
public class Card {

    private CardColors color;
    private int score;
    private int number;
    private CardType cardType;
    private String cardName;

    /**
     * The Constructor for Action cards - Skip, Reverse and Draw-2
     *
     * @param color
     *            the color of card
     * @param actionCard
     *            the type of action card
     */
    public Card(CardColors color, CardType actionCard) {
	this.color = color;
	this.cardType = actionCard;
	this.cardName = color.getColor() + "-" + actionCard.getCardName();
	this.score = actionCard.getScore();
    }

    /**
     * The Constructor for Wild cards - Wild and Wild-4
     *
     * @param wildCard
     *            the type of wild card
     */
    public Card(CardType wildCard) {
	this.color = CardColors.ALL_FOUR_COLORS;
	this.cardType = wildCard;
	this.cardName = wildCard.getCardName();
	this.score = cardType.getScore();
    }

    /**
     * The Constructor for Number cards - (0-9)
     *
     * @param color
     *            the color of card
     * @param numberCard
     *            the type of number card
     * @param number
     *            the number of card
     */
    public Card(CardColors color, CardType numberCard, int number) {
	this.color = color;
	this.number = number;
	this.score = number;
	this.cardName = color.getColor() + "-" + number;
	this.cardType = numberCard;
    }

    /**
     * Gets the color of card
     *
     * @return the color
     */
    public CardColors getColor() {
	return color;
    }

    /**
     * Sets the color of card
     *
     * @param color
     *            the color
     */
    public void setColor(CardColors color) {
	this.color = color;
    }

    /**
     * Gets the score of card
     *
     * @return the score
     */
    public int getScore() {
	return score;
    }

    /**
     * Sets the score of card
     *
     * @param score
     *            the score
     */
    public void setScore(int score) {
	this.score = score;
    }

    /**
     * Gets the number of card
     *
     * @return the number
     */
    public int getNumber() {
	return number;
    }

    /**
     * Sets the number of card
     *
     * @param number
     *            the number
     */
    public void setNumber(int number) {
	this.number = number;
    }

    /**
     * Gets the type of card
     *
     * @return the card type
     */
    public CardType getCardType() {
	return cardType;
    }

    /**
     * Sets the type of card
     *
     * @param cardType
     *            the card type
     */
    public void setCardType(CardType cardType) {
	this.cardType = cardType;
    }

    /**
     * Gets the card name.
     *
     * @return the card name
     */
    public String getCardName() {
	return cardName;
    }

    /**
     * Sets the card name.
     *
     * @param cardName
     *            the card name
     */
    public void setCardName(String cardName) {
	this.cardName = cardName;
    }

    /**
     * Sets the color of card
     *
     * @param chosenColor
     *            the color
     */
    public void setColor(String chosenColor) {
	CardColors color = null;
	switch (chosenColor) {
	case (UnoConstants.RED): {
	    color = CardColors.RED;
	    break;
	}
	case (UnoConstants.BLUE): {
	    color = CardColors.BLUE;
	    break;
	}
	case (UnoConstants.GREEN): {
	    color = CardColors.GREEN;
	    break;
	}
	case (UnoConstants.YELLOW): {
	    color = CardColors.YELLOW;
	    break;
	}
	}
	setColor(color);
    }

    @Override
    public String toString() {
	return "Card [color=" + color + ", number=" + number + ", cardType=" + cardType.toString() + "]";
    }
}
