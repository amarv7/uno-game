package uno.game.utility;

import java.util.LinkedList;
import java.util.Random;

import uno.game.constants.CardColors;
import uno.game.constants.CardType;
import uno.game.constants.UnoConstants;
import uno.game.entities.Card;

/**
 * UnoFacade is utility class, provide utilities to the UNO Game. Like - prepare
 * uno cards, shuffle the cards, validate the discarded card and provide icons
 * path.
 * 
 * @author Amar Verma
 */
public class UnoFacade {

    /**
     * Prepared the UNO Cards for Draw pile.
     *
     * @return the cards
     */
    public static LinkedList<Card> getCards() {
	LinkedList<Card> cards = new LinkedList<>();
	for (CardColors color : CardColors.values()) {
	    if (!color.equals(CardColors.ALL_FOUR_COLORS)) {
		for (int i = 0; i <= 9; i++) {
		    if (i == 0) {
			cards.push(new Card(color, CardType.NUMBER, i));
		    } else {
			cards.push(new Card(color, CardType.NUMBER, i));
			cards.push(new Card(color, CardType.NUMBER, i));
		    }
		}
		for (int i = 0; i < 2; i++) {
		    cards.push(new Card(color, CardType.DRAW_TWO_CARD));
		    cards.push(new Card(color, CardType.REVERSE_CARD));
		    cards.push(new Card(color, CardType.SKIP_CARD));
		}
	    }
	}
	for (int i = 0; i < 4; i++) {
	    cards.push(new Card(CardType.WILD_CARD));
	    cards.push(new Card(CardType.WILD_DRAW_FOUR_CARD));
	}
	shuffleCards(cards);
	return cards;
    }

    /**
     * Shuffle the cards.
     *
     * @param cards
     *            the cards
     */
    public static void shuffleCards(LinkedList<Card> cards) {
	LinkedList<Card> shuffledCardList = new LinkedList<>();
	Random randomGenerator = new Random();
	while (cards.size() != 0) {
	    int randomInt = randomGenerator.nextInt(cards.size());
	    if (randomInt < cards.size()) {
		shuffledCardList.addLast(cards.remove(randomInt));
	    }
	}
	cards.addAll(shuffledCardList);
	shuffledCardList = null;
    }

    /**
     * Validate card discarded card
     *
     * @param playerCard
     *            the player card
     * @param discardPileCard
     *            the discard pile card
     * @return true, validated as true of false
     */
    public static boolean validateCard(Card playerCard, Card discardPileCard) {
	boolean flag = false;
	CardColors playerCardColor = playerCard.getColor();
	CardColors discardPileCardColor = discardPileCard.getColor();
	CardType playerCardType = playerCard.getCardType();
	CardType discardPileCardType = discardPileCard.getCardType();

	if (playerCardType == CardType.WILD_CARD || playerCardType == CardType.WILD_DRAW_FOUR_CARD) {
	    flag = true;
	} else if (playerCardColor == discardPileCardColor) {
	    flag = true;
	} else if (discardPileCardType == CardType.NUMBER && playerCardType == CardType.NUMBER) {
	    flag = (playerCard.getNumber() == discardPileCard.getNumber()) ? true : false;
	} else if (discardPileCardType == playerCardType) {
	    flag = true;
	}
	if (flag && (discardPileCardType == CardType.WILD_CARD || discardPileCardType == CardType.WILD_DRAW_FOUR_CARD)) {
	    discardPileCard.setColor(CardColors.ALL_FOUR_COLORS);
	}
	return flag;
    }

    /**
     * Gets the icon path.
     *
     * @param fileName
     *            the file name
     * @param isBigIcon
     *            the icon is big or small
     * @return the icon path
     */
    public static String getIconPath(String fileName, boolean isBigIcon) {
	String imgPath = null;
	if (isBigIcon) {
	    imgPath = UnoConstants.BIG_IMG_PATH + "/" + fileName + UnoConstants.PNG_FILE;
	} else {
	    imgPath = UnoConstants.SMALL_IMG_PATH + "/" + fileName + UnoConstants.PNG_FILE;
	}
	return imgPath;
    }
}
