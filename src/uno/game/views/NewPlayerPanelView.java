package uno.game.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

import uno.game.constants.UnoConstants;
import uno.game.utility.UnoFacade;

public class NewPlayerPanelView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel addNewPlayerPanel;
    private JTextField playerTextField;
    private JButton addPlayerBtn;
    private JLabel playerInfoLbl;
    private JPanel addedPlayersList;
    private JPanel pickCardDeck;

    public NewPlayerPanelView() {
	setBounds(10, 3, 420, 426);
	setLayout(null);
	addNewPlayerPanel = new JPanel();
	addNewPlayerPanel.setBounds(0, 0, 310, 58);
	addNewPlayerPanel.setOpaque(false);
	add(addNewPlayerPanel);
	addNewPlayerPanel.setLayout(null);

	JLabel lblPlayerName = new JLabel(UnoConstants.NEW_PLAYER_TXT);
	lblPlayerName.setBounds(0, 7, 79, 14);
	lblPlayerName.setForeground(new Color(0, 255, 255));
	lblPlayerName.setFont(new Font("Tahoma", Font.BOLD, 12));
	playerTextField = new JTextField();
	playerTextField.setBounds(85, 1, 126, 28);
	playerTextField.setColumns(10);
	playerTextField.setToolTipText(UnoConstants.ADD_PLAYERS_MSG);

	addPlayerBtn = new JButton(UnoConstants.ADD_TXT);
	addPlayerBtn.setBounds(223, 0, 71, 30);
	addPlayerBtn.setToolTipText(UnoConstants.ADD_PLAYERS_MSG);
	addPlayerBtn.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.ADD_IMG, false))));

	playerInfoLbl = new JLabel(UnoConstants.ADD_PLAYERS_MSG);
	playerInfoLbl.setFont(new Font("Tahoma", Font.BOLD, 11));
	playerInfoLbl.setForeground(new Color(255, 0, 0));
	playerInfoLbl.setBounds(0, 28, 294, 27);

	addNewPlayerPanel.add(lblPlayerName);
	addNewPlayerPanel.add(playerTextField);
	addNewPlayerPanel.add(addPlayerBtn);
	addNewPlayerPanel.add(playerInfoLbl);

	createAddedPlayersList();

	pickCardDeck = new JPanel();
	pickCardDeck.setBackground(SystemColor.inactiveCaption);
	pickCardDeck.setBounds(263, 58, 126, 186);
	pickCardDeck.setOpaque(false);
	add(pickCardDeck);
	pickCardDeck.setLayout(new CardLayout(0, 0));
    }

    public JPanel getAddNewPlayerPanel() {
	return addNewPlayerPanel;
    }

    public JTextField getPlayerTextField() {
	return playerTextField;
    }

    public JButton getAddPlayerBtn() {
	return addPlayerBtn;
    }

    public JLabel getPlayerInfoLbl() {
	return playerInfoLbl;
    }

    public JPanel getAddedPlayersList() {
	return addedPlayersList;
    }

    public JPanel getPickCardDeck() {
	return pickCardDeck;
    }

    public void createAddedPlayersList() {
	addedPlayersList = new JPanel();
	addedPlayersList.setBounds(0, 58, 239, 360);
	addedPlayersList.setBackground(new Color(250, 250, 210));
	addedPlayersList.setBorder(new CompoundBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(184, 134, 11)), new MatteBorder(2, 2, 2, 2, (Color) new Color(238, 232, 170))));
	add(addedPlayersList);
	addedPlayersList.setLayout(new GridLayout(10, 0, 0, 0));
    }

}
