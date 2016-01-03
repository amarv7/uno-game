package uno.game.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import uno.game.constants.UnoConstants;
import uno.game.utility.UnoFacade;

public class GamePanelView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel drawPile;
    private JPanel discardPile;
    private JPanel cardTiles;
    private JScrollPane playerCardsPanel;
    private JButton skipBtn;
    private JLabel playerNameLbl;
    private JLabel infoMesg;
    private JLabel infoImg;
    private CardLayout cardLayout;
    private JButton announceBtn;

    public GamePanelView() {
	setOpaque(false);
	setBounds(0, 34, 721, 462);
	setLayout(null);

	playerCardsPanel = new JScrollPane();
	playerCardsPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	playerCardsPanel.setBounds(49, 291, 624, 160);
	add(playerCardsPanel);

	cardTiles = new JPanel();
	playerCardsPanel.setViewportView(cardTiles);
	cardTiles.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

	skipBtn = new JButton(UnoConstants.SKIP_TXT);
	skipBtn.setBounds(270, 188, 65, 65);
	skipBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
	skipBtn.setLocation(295, 197);
	add(skipBtn);

	drawPile = new JPanel();
	drawPile.setBounds(190, 8, 126, 186);
	drawPile.setOpaque(false);
	drawPile.setLayout(new CardLayout(0, 0));
	add(drawPile);

	discardPile = new JPanel();
	discardPile.setBounds(406, 8, 126, 186);
	discardPile.setOpaque(false);
	cardLayout = new CardLayout(0, 0);
	discardPile.setLayout(cardLayout);
	add(discardPile);

	infoMesg = new JLabel();
	infoMesg.setFont(new Font("Tahoma", Font.BOLD, 16));
	infoMesg.setForeground(new Color(255, 0, 0));
	infoMesg.setVerticalAlignment(SwingConstants.CENTER);
	infoMesg.setHorizontalAlignment(SwingConstants.RIGHT);
	infoMesg.setBounds(295, 264, 378, 28);
	add(infoMesg);

	infoImg = new JLabel();
	infoImg.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.WINNER_IMG, true))));
	infoImg.setHorizontalAlignment(SwingConstants.CENTER);
	infoImg.setVerticalAlignment(SwingConstants.CENTER);
	infoImg.setBounds(49, 86, 126, 165);
	add(infoImg);

	playerNameLbl = new JLabel();
	playerNameLbl.setBounds(34, 211, 243, 40);
	playerNameLbl.setFont(new Font("Tahoma", Font.BOLD, 26));
	playerNameLbl.setVerticalAlignment(SwingConstants.BOTTOM);
	playerNameLbl.setForeground(new Color(139, 0, 139));
	playerNameLbl.setLocation(49, 252);
	add(playerNameLbl);

	announceBtn = new JButton();
	announceBtn.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.ANNOUNCE_IMG, true))));
	announceBtn.setBounds(362, 197, 65, 65);
	add(announceBtn);

    }

    public JScrollPane getPlayerCardsPanel() {
	return playerCardsPanel;
    }

    public CardLayout getCardLayout() {
	return cardLayout;
    }

    public JPanel getCardTiles() {
	return cardTiles;
    }

    public JButton getSkipBtn() {
	return skipBtn;
    }

    public JLabel getPlayerNameLbl() {
	return playerNameLbl;
    }

    public JLabel getInfoMesg() {
	return infoMesg;
    }

    public JLabel getInfoImg() {
	return infoImg;
    }

    public JButton getAnnounceBtn() {
	return announceBtn;
    }

    public JPanel getDrawPile() {
	return drawPile;
    }

    public JPanel getDiscardPile() {
	return discardPile;
    }

}
