package uno.game.views.widgets;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uno.game.constants.UnoConstants;
import uno.game.utility.UnoFacade;
import uno.game.views.UnoGameHome;

public class PlayerData extends JPanel {

    private static final long serialVersionUID = 1895026743953116318L;

    private JLabel playerNameLbl;
    private JButton pickCardBtn;
    private JButton removePlayerBtn;

    public PlayerData(String playerName) {
	setLayout(new GridLayout(0, 3, 0, 0));
	setOpaque(false);
	playerNameLbl = new JLabel(playerName);
	setName(playerName);
	add(playerNameLbl);

	pickCardBtn = new JButton();
	pickCardBtn.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.PICK_IMG, false))));
	pickCardBtn.setToolTipText(UnoConstants.PICK_CARD_TXT);
	add(pickCardBtn);

	removePlayerBtn = new JButton();
	removePlayerBtn.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.REMOVE_IMG, false))));
	removePlayerBtn.setToolTipText(UnoConstants.REMOVE_PLAYER_TXT);
	add(removePlayerBtn);

	setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
    }

    public String getPlayerName() {
	return playerNameLbl.getText();
    }

    public JButton getPickCardBtn() {
	return pickCardBtn;
    }

    public JButton getRemovePlayerBtn() {
	return removePlayerBtn;
    }

    public PlayerData getPlayerData() {
	return this;
    }

}
