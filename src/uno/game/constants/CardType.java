package uno.game.constants;

public enum CardType {
    NUMBER(), //
    WILD_CARD(50, "wild"), //
    WILD_DRAW_FOUR_CARD(50, "wild4"), //
    DRAW_TWO_CARD(20, "draw2"), //
    REVERSE_CARD(20, "reverse"), //
    SKIP_CARD(20, "skip");

    private int score;
    private String cardName;

    private CardType() {
    }

    private CardType(int score, String cardName) {
	this.score = score;
	this.cardName = cardName;
    }

    public int getScore() {
	return score;
    }

    public String getCardName() {
	return cardName;
    }

}
