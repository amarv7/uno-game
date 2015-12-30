package uno.game.presenter;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import uno.game.constants.CardSection;
import uno.game.constants.CardType;
import uno.game.constants.UnoConstants;
import uno.game.entities.Card;
import uno.game.entities.Player;
import uno.game.service.GameManagerService;
import uno.game.utility.UnoFutureCallback;
import uno.game.views.About;
import uno.game.views.GamePanelView;
import uno.game.views.NewPlayerPanelView;
import uno.game.views.UnoGameHome;
import uno.game.views.widgets.CardThumbnail;
import uno.game.views.widgets.FileMenu;
import uno.game.views.widgets.HelpFileMenu;
import uno.game.views.widgets.PlayerData;

/**
 * The Class UnoViewEventHandler, glues all the required events to the view
 * widgets communicates to the Game services, and provide interaction with all
 * the game operations.
 * 
 * @author Amar Verma
 */
public class UnoViewEventHandler {

    private JPanel discardPile;
    private JPanel drawPile;
    private JPanel addedPlayersPanel;
    private JLabel playerInfoLbl;
    private JLabel playerNameLbl;
    private JLabel infoImg;
    private JLabel infoMesg;
    private JPanel cardTiles;
    private JPanel pickCardDeck;
    private Player currentPlayer;
    private JButton skipBtn;
    private JButton announceBtn;
    private JButton addPlayerBtn;
    private JButton startGameBtn;
    private Card topDiscardPileCard;
    private JScrollPane playerCardsPanel;
    private UnoGameHome unoHomeObj;
    private GameManagerService gameManagerService;
    private GamePanelView gamePanel;
    private NewPlayerPanelView newPlayerPanelView;
    private JMenuItem startMenuItem;
    private JMenuItem resetMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem gameRulesMenuItem;
    private JMenuItem aboutMenuItem;
    private JDesktopPane desktopPane;

    /**
     * Constructor of event handler, gets all the required widgets where events
     * should be bind and need to be populated with player's operations.
     *
     * @param unoHomeObj
     *            the uno home view object
     */
    public UnoViewEventHandler(UnoGameHome unoHomeObj) {
	this.unoHomeObj = unoHomeObj;
	gameManagerService = new GameManagerService();
	gamePanel = unoHomeObj.getGamePanel();
	newPlayerPanelView = unoHomeObj.getNewPlayerPanel();
	startGameBtn = unoHomeObj.getStartGameBtn();
	desktopPane = unoHomeObj.getDesktopPane();
	addedPlayersPanel = newPlayerPanelView.getAddedPlayersList();
	addPlayerBtn = newPlayerPanelView.getAddPlayerBtn();
	playerInfoLbl = newPlayerPanelView.getPlayerInfoLbl();
	pickCardDeck = newPlayerPanelView.getPickCardDeck();
	playerNameLbl = gamePanel.getPlayerNameLbl();
	drawPile = gamePanel.getDrawPile();
	discardPile = gamePanel.getDiscardPile();
	infoImg = gamePanel.getInfoImg();
	infoMesg = gamePanel.getInfoMesg();
	cardTiles = gamePanel.getCardTiles();
	playerCardsPanel = gamePanel.getPlayerCardsPanel();
	announceBtn = gamePanel.getAnnounceBtn();
	skipBtn = gamePanel.getSkipBtn();
	FileMenu fileMenu = unoHomeObj.getFileMenu();
	startMenuItem = fileMenu.getStartMenuItem();
	resetMenuItem = fileMenu.getResetMenuItem();
	exitMenuItem = fileMenu.getExitMenuItem();
	HelpFileMenu helpFileMenu = unoHomeObj.getHelpFileMenu();
	gameRulesMenuItem = helpFileMenu.getGameRulesMenuItem();
	aboutMenuItem = helpFileMenu.getAboutMenuItem();
    }

    /**
     * Init method, brings the game on the initial state.
     */
    public void init() {
	bindEvents();
	playerInfoLbl.setText(null);
	infoMesg.setText(null);
	startGameBtn.setEnabled(false);
	startMenuItem.setEnabled(false);
	gamePanel.setVisible(false);
	infoImg.setVisible(false);
	announceBtn.setEnabled(false);
	skipBtn.setEnabled(false);
	pickCardDeck.add(new CardThumbnail(UnoConstants.CARD_FACE_DOWN_NAME, CardSection.DRAW_DISCARD_PILE));
    }

    /**
     * Bind events on all widgets
     */
    private void bindEvents() {

	gameManagerService.addRecreateDrawPileListener(new UnoFutureCallback() {

	    @Override
	    public void doTask() {
		JOptionPane.showInternalMessageDialog(gamePanel, UnoConstants.REDRAW_PILE_MSG);
		gameManagerService.recreateDrawPile();
		resetShuffleDrawPile();
		resetDiscardPile();
		validate();
	    }

	});

	addPlayerBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		JTextField playerTextField = newPlayerPanelView.getPlayerTextField();
		playerInfoLbl.setText(null);

		if (addedPlayersPanel.getComponentCount() >= 10) {
		    JOptionPane.showMessageDialog(gamePanel, UnoConstants.ADDED_PLAYERS_MSG);
		} else {
		    String playerName = playerTextField.getText();
		    if (playerName.length() > 0) {
			PlayerData playerData = getPlayerData(playerName);
			boolean isPlayerAdded = gameManagerService.addPlayer(playerName);
			if (isPlayerAdded) {
			    addedPlayersPanel.add(playerData);
			    playerInfoLbl.setText(UnoConstants.PICK_CARD_MSG);
			    validate();
			} else {
			    playerInfoLbl.setText(UnoConstants.PLAYER_EXISTS_MSG);
			}
		    }
		}
		enableStartGameOption();
		playerTextField.setText(null);
	    }
	});

	startGameBtn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		startGame();
	    }
	});

	announceBtn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		skipBtn.setEnabled(false);
		CardType cardType = topDiscardPileCard.getCardType();
		int size = currentPlayer.getRemainingCards();
		if (cardType == CardType.WILD_CARD || cardType == CardType.WILD_DRAW_FOUR_CARD) {
		    String announcedColor = announceForColor();
		    if (announcedColor != null && announcedColor.length() > 0 && announcedColor != UnoConstants.SELECT_COLORS) {
			topDiscardPileCard.setColor(announcedColor);
			currentPlayer.setCanPlay(true);
			nextPlayerToPlay();
			if (cardType == CardType.WILD_DRAW_FOUR_CARD) {
			    infoMesg.setText(UnoConstants.PLAYER_DRAW_4_MSG);
			    currentPlayer.setDraw(UnoConstants.DRAW_FOUR);
			    skipBtn.setEnabled(false);
			} else {
			    skipBtn.setEnabled(true);
			    infoMesg.setText(null);
			}
			announceBtn.setEnabled(false);
		    } else {
			infoMesg.setText(UnoConstants.ANNOUNCE_COLOR_MSG);
		    }
		} else if (size == 1) {
		    if (announceUNO() == UnoConstants.YES) {
			currentPlayer.setAnnouncedUNO(true);
			currentPlayer.setDraw(UnoConstants.DRAW_NONE);
			infoMesg.setText(null);
			announceBtn.setEnabled(false);
			updateTopDiscardPileCard();
		    }
		}
	    }
	});

	skipBtn.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		CardType cardType = topDiscardPileCard.getCardType();
		if (cardType == CardType.SKIP_CARD) {
		    currentPlayer.setCanPlay(true);
		    nextPlayerToPlay();
		    infoMesg.setText(null);
		} else {
		    int dialogInput = JOptionPane.showOptionDialog(gamePanel, UnoConstants.SKIP_MSG, UnoConstants.SKIP_TXT, 0, 3, null, null, null);
		    if (dialogInput == UnoConstants.YES) {
			nextPlayerToPlay();
		    }
		}
	    }
	});

	unoHomeObj.getResetGameBtn().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		resetGame();
	    }
	});

	startMenuItem.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		startGame();
	    }
	});

	resetMenuItem.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		resetGame();
	    }
	});

	exitMenuItem.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		unoHomeObj.dispose();
	    }
	});

	gameRulesMenuItem.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
		    File myFile = new File("/path/to/file.pdf");
		    Desktop.getDesktop().open(myFile);
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
	    }
	});

	aboutMenuItem.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		About about = new About();
		about.setVisible(true);
		int width = about.getWidth() / 2;
		int height = about.getHeight() / 2;
		int dpWidth = desktopPane.getWidth() / 2 - width;
		int dpHeight = desktopPane.getHeight() / 2 - height;
		about.setLocation(dpWidth, dpHeight);
		desktopPane.add(about, 0);
	    }
	});
    }

    /**
     * Start game, entry point of Game
     */
    private void startGame() {
	gamePanel.setVisible(true);
	newPlayerPanelView.setVisible(false);
	resetShuffleDrawPile();
	distributeCards();
	resetDiscardPile();
	addEventOnDrawPile(true);
	infoMesg.setText(UnoConstants.DRAW_CARD_TO_START);
	startGameBtn.setEnabled(false);
	startMenuItem.setEnabled(false);
	validate();
    }

    /**
     * Reset discard pile.
     */
    private void resetDiscardPile() {
	discardPile.removeAll();
	discardPile.add(new CardThumbnail(UnoConstants.DISCARD_PILE_NAME, CardSection.DRAW_DISCARD_PILE));
	discardPile.validate();
    }

    /**
     * Adds the event on draw pile.
     *
     * @param isStartGame
     *            isStartGame, first time at the time of starting it should be
     *            true.
     */
    private void addEventOnDrawPile(boolean isStartGame) {
	drawPile.addMouseListener(new MouseAdapter() {
	    private boolean startGameFlag = isStartGame;

	    @Override
	    public void mouseExited(MouseEvent e) {
		drawPile.setCursor(new Cursor(0));
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		drawPile.setCursor(new Cursor(12));
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		infoMesg.setText(null);
		int componentCount = drawPile.getComponentCount();
		if (componentCount > 1) {
		    int dialogInput = JOptionPane.showOptionDialog(gamePanel, UnoConstants.DRAW_CARD_MSG, UnoConstants.DRAW_CARD_TXT, 0, 3, null, null, null);
		    if (dialogInput == UnoConstants.YES) {
			CardThumbnail drawnCard = (CardThumbnail) drawPile.getComponent(componentCount - 1);
			if (startGameFlag) {
			    boolean startGame = gameManagerService.startGame();
			    if (startGame) {
				removeFromDrawPile(drawnCard);
				addToDiscardPile(drawnCard);
				startGameFlag = false;
			    } else {
				resetShuffleDrawPile();
				addToDiscardPile(drawnCard);
				JOptionPane.showMessageDialog(gamePanel, UnoConstants.BEGIN_GAME_ERROR_MSG);
				removeFromDiscardPile(drawnCard);
			    }
			} else {
			    Card card = gameManagerService.drawCard();
			    currentPlayer.addCard(card);
			    cardTiles.add(getPlayerCard(card.getCardName()));
			    removeFromDrawPile(drawnCard);
			    cardTiles.validate();
			    playerCardsPanel.validate();
			    if (currentPlayer.canPlay()) {
				skipBtn.setEnabled(true);
			    }
			}
		    }
		} else {
		    drawPile.removeAll();
		    addToDrawPile(new CardThumbnail(UnoConstants.DRAW_PILE_NAME, CardSection.DRAW_DISCARD_PILE));
		}
	    }
	});
    }

    /**
     * Enable start game button. It required minimum two players should added
     */
    private void enableStartGameOption() {
	boolean canEnable = addedPlayersPanel.getComponentCount() > 1 ? true : false;
	startGameBtn.setEnabled(canEnable);
	startMenuItem.setEnabled(canEnable);
    }

    /**
     * Reset and shuffle draw pile. Its required before starting the game, if
     * first card is wild/wild-4 card or draw pile becomes empty.
     */
    private void resetShuffleDrawPile() {
	drawPile.removeAll();
	drawPile.add(new CardThumbnail(UnoConstants.CARD_FACE_DOWN_NAME, CardSection.DRAW_DISCARD_PILE));
	for (String cardName : gameManagerService.getAllCards()) {
	    drawPile.add(new CardThumbnail(cardName, CardSection.DRAW_DISCARD_PILE));
	}
	drawPile.validate();
    }

    /**
     * Distribute cards to the players, fills the card tiles of the players
     */
    private void distributeCards() {
	int totalDistributedCards = gameManagerService.distributeCards();
	while (totalDistributedCards-- > 0) {
	    drawPile.remove(drawPile.getComponentCount() - 1);
	}
	drawPile.validate();
    }

    /**
     * Brings next player to play, and prepares the view and widgets to let the
     * next player play the game.
     */
    private void nextPlayerToPlay() {
	skipBtn.setEnabled(false);
	infoImg.setVisible(false);
	playerCardsPanel.remove(cardTiles);
	cardTiles = new JPanel();
	playerCardsPanel.setViewportView(cardTiles);
	cardTiles.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
	currentPlayer = gameManagerService.getNextPlayer();
	playerNameLbl.setText(currentPlayer.getPlayerName());
	List<Card> listOfCards = currentPlayer.getListOfCards();
	for (Card card : listOfCards) {
	    cardTiles.add(getPlayerCard(card.getCardName()));
	}
	if (currentPlayer.getRemainingCards() == 1) {
	    announceBtn.setEnabled(true);
	    infoMesg.setText(UnoConstants.ANNOUNCE_UNO_OR_DRAW_MSG);
	    currentPlayer.setDraw(UnoConstants.DRAW_TWO);
	    skipBtn.setEnabled(false);
	}
	playerCardsPanel.validate();
	cardTiles.validate();
    }

    /**
     * Add card to the discard pile.
     *
     * @param card
     *            the card
     */
    private void addToDiscardPile(CardThumbnail card) {
	discardPile.add(card);
	gamePanel.getCardLayout().last(discardPile);
	discardPile.validate();
	updateTopDiscardPileCard();
    }

    /**
     * Remove card from the discard pile.
     *
     * @param card
     *            the card
     */
    private void removeFromDiscardPile(CardThumbnail card) {
	discardPile.remove(card);
	gamePanel.getCardLayout().last(discardPile);
	discardPile.validate();
	updateTopDiscardPileCard();
    }

    /**
     * Add card to the draw pile.
     *
     * @param card
     *            the card
     */
    private void addToDrawPile(CardThumbnail card) {
	drawPile.add(card);
	drawPile.validate();
    }

    /**
     * Removes card from the draw pile.
     *
     * @param card
     *            the card
     */
    private void removeFromDrawPile(CardThumbnail card) {
	drawPile.remove(card);
	drawPile.validate();
    }

    /**
     * Update top card/last card on the discard pile. The top card on the
     * discard pile sets the Rules for the next player, and these rules should
     * be followed.
     */
    private void updateTopDiscardPileCard() {
	topDiscardPileCard = gameManagerService.getDiscardPileLastCard();
	boolean checkForWinnerPlayer = checkForWinnerPlayer();
	if (topDiscardPileCard != null && checkForWinnerPlayer) {
	    CardType cardType = topDiscardPileCard.getCardType();
	    if (cardType == CardType.WILD_CARD || cardType == CardType.WILD_DRAW_FOUR_CARD) {
		announceBtn.setEnabled(true);
		skipBtn.setEnabled(false);
		infoMesg.setText(UnoConstants.ANNOUNCE_CLICK_MSG);
		currentPlayer.setCanPlay(false);
	    } else if (cardType == CardType.SKIP_CARD) {
		infoMesg.setText(UnoConstants.PLAYER_SKIP_MSG);
		nextPlayerToPlay();
		skipBtn.setEnabled(true);
		currentPlayer.setCanPlay(false);
	    } else if (cardType == CardType.DRAW_TWO_CARD) {
		infoMesg.setText(UnoConstants.PLAYER_DRAW_2_MSG);
		skipBtn.setEnabled(false);
		nextPlayerToPlay();
		currentPlayer.setDraw(UnoConstants.DRAW_TWO);
	    } else {
		nextPlayerToPlay();
		infoMesg.setText(null);
	    }
	}
    }

    /**
     * Check and validates for winner player.
     */
    private boolean checkForWinnerPlayer() {
	boolean canNextPlayerPlay = true;
	if (currentPlayer != null && currentPlayer.getRemainingCards() == 0) {
	    if (currentPlayer.isAnnouncedUNO()) {
		infoImg.setVisible(true);
		int scoreForWinner = gameManagerService.getScoreForWinner(currentPlayer);
		infoMesg.setText(currentPlayer.getPlayerName() + " is " + UnoConstants.WINNER_TXT + " , Score:" + scoreForWinner);
		skipBtn.setEnabled(false);
		announceBtn.setEnabled(false);
		canNextPlayerPlay = false;
		int dialogInput = JOptionPane.showOptionDialog(gamePanel, UnoConstants.CONTINUE_GAME_MSG, UnoConstants.CONTINUE_GAME_TXT, 0, 3, null, null, null);
		if (dialogInput == UnoConstants.YES) {
		    infoMesg.setText(null);
		    infoImg.setVisible(false);
		    skipBtn.setEnabled(false);
		    announceBtn.setEnabled(false);
		    currentPlayer.setAnnouncedUNO(false);
		    canNextPlayerPlay = true;
		} else {
		    playerCardsPanel.remove(cardTiles);
		    cardTiles = new JPanel();
		    playerCardsPanel.setViewportView(cardTiles);
		    cardTiles.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		    playerCardsPanel.validate();
		}
	    }
	} else if (currentPlayer != null && currentPlayer.getRemainingCards() == 1 && !currentPlayer.isAnnouncedUNO()) {
	    announceBtn.setEnabled(true);
	    infoMesg.setText(UnoConstants.ANNOUNCE_UNO_OR_DRAW_MSG);
	    currentPlayer.setDraw(UnoConstants.DRAW_TWO);
	    skipBtn.setEnabled(false);
	    cardTiles.validate();
	    canNextPlayerPlay = false;
	}
	return canNextPlayerPlay;
    }

    /**
     * Announce for color.
     *
     * @return the string
     */
    private String announceForColor() {
	String[] selectColor = new String[5];
	selectColor[0] = UnoConstants.SELECT_COLORS;
	selectColor[1] = UnoConstants.RED;
	selectColor[2] = UnoConstants.GREEN;
	selectColor[3] = UnoConstants.BLUE;
	selectColor[4] = UnoConstants.YELLOW;
	return (String) JOptionPane.showInputDialog(gamePanel, UnoConstants.SELECT_VALUES, UnoConstants.ANNOUNCE_TXT, 3, null, selectColor, 0);
    }

    /**
     * Announce for win.
     *
     * @return the int
     */
    private int announceUNO() {
	return JOptionPane.showOptionDialog(gamePanel, UnoConstants.ANNOUNCE_MSG, UnoConstants.ANNOUNCE_TXT, 0, 3, null, null, null);
    }

    /**
     * Validate and refresh Home view.
     */
    private void validate() {
	unoHomeObj.validate();
    }

    /**
     * Reset the Game.
     */
    private void resetGame() {
	newPlayerPanelView.setVisible(true);
	gamePanel.setVisible(false);
	gameManagerService.resetGame();
	drawPile.removeAll();
	discardPile.removeAll();
	cardTiles.removeAll();
	addedPlayersPanel.removeAll();
	infoMesg.setText(null);
	playerInfoLbl.setText(null);
	playerNameLbl.setText(null);
	infoImg.setVisible(false);
	announceBtn.setVisible(false);
	unoHomeObj.validate();
	resetPickCardDesk(UnoConstants.CARD_FACE_DOWN_NAME);
    }

    /**
     * Reset pick card desk.
     *
     * @param cardName
     *            the card name
     */
    private void resetPickCardDesk(String cardName) {
	pickCardDeck.removeAll();
	pickCardDeck.add(new CardThumbnail(cardName, CardSection.DRAW_DISCARD_PILE));
	pickCardDeck.validate();
    }

    /**
     * Gets the player data, UI to display Players
     *
     * @param playerName
     *            the player name
     * @return the player UI Widget
     */
    private PlayerData getPlayerData(String playerName) {

	PlayerData player = new PlayerData(playerName);
	player.getRemovePlayerBtn().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		addedPlayersPanel.remove(player);
		gameManagerService.removePlayer(player.getPlayerName());
		enableStartGameOption();
	    }
	});
	player.getPickCardBtn().addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		Card card = gameManagerService.pickCard();
		playerInfoLbl.setText(null);
		int score = card.getScore();
		resetPickCardDesk(card.getCardName());
		playerInfoLbl.setText(player.getName() + " , Score: " + score);
		gameManagerService.setPlayerPriority(player.getName(), score);
		player.getPickCardBtn().setEnabled(false);
	    }
	});
	return player;
    }

    /**
     * Gets the player card, UI Widget to display cards
     *
     * @param cardName
     *            the card name
     * @return the player card UI widget
     */
    private CardThumbnail getPlayerCard(String cardName) {
	CardThumbnail card = new CardThumbnail(cardName, CardSection.PLAYER_CARDS);

	card.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseExited(MouseEvent e) {
		card.setCursor(new Cursor(0));
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		card.setCursor(new Cursor(12));
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
		if (currentPlayer.canPlay()) {
		    int dialogInput = JOptionPane.showOptionDialog(gamePanel, UnoConstants.DISCARD_TXT, UnoConstants.DISCARD_MSG, 0, 3, null, null, null);
		    if (dialogInput == UnoConstants.YES) {
			Card cardToRemove = currentPlayer.getCard(card.getName());
			boolean result = gameManagerService.discard(cardToRemove);
			if (result) {
			    currentPlayer.discard(cardToRemove);
			    cardTiles.remove(card);
			    cardTiles.validate();
			    playerCardsPanel.validate();
			    addToDiscardPile(new CardThumbnail(card.getName(), CardSection.DRAW_DISCARD_PILE));
			}
		    }
		}
	    }
	});
	return card;
    }

}
