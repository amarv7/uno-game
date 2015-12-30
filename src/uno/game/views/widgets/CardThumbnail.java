package uno.game.views.widgets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import uno.game.constants.CardSection;
import uno.game.utility.UnoFacade;
import uno.game.views.UnoGameHome;

public class CardThumbnail extends JLabel {

    private static final long serialVersionUID = 6109591951782326447L;
    private CardSection cardSection;

    public CardThumbnail(String fileName, CardSection cardSection) {
	super();
	setName(fileName);
	setHorizontalAlignment(SwingConstants.CENTER);
	this.cardSection = cardSection;
	setIcon();
    }

    private void setIcon() {
	boolean isBigThumbnail;
	isBigThumbnail = (cardSection == CardSection.PLAYER_CARDS) ? false : true;
	String filePath = UnoFacade.getIconPath(getName(), isBigThumbnail);
	setIcon(new ImageIcon(UnoGameHome.class.getResource(filePath)));
    }

    public void setCardSection(CardSection cardSection) {
	this.cardSection = cardSection;
	setIcon();
    }
}
