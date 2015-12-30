package uno.game.constants;

public enum CardColors {
    ALL_FOUR_COLORS("four colors"),//
    RED("red"), //
    YELLOW("yellow"), //
    BLUE("blue"), //
    GREEN("green");

    private String colorText;

    private CardColors(String colorText) {
	this.colorText = colorText;
    }

    public String getColor() {
	return colorText;
    }
}
