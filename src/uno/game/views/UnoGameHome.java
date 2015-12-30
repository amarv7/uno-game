package uno.game.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import uno.game.constants.UnoConstants;
import uno.game.presenter.UnoViewEventHandler;
import uno.game.utility.UnoFacade;
import uno.game.views.widgets.FileMenu;
import uno.game.views.widgets.HelpFileMenu;

public class UnoGameHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JDesktopPane desktopPane;
    private JButton btnResetGame;
    private JButton startGameBtn;
    private GamePanelView gamePanel;
    private NewPlayerPanelView newPlayerPanelView;
    private FileMenu fileMenu;
    private HelpFileMenu helpFileMenu;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	try {
	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    UnoGameHome frame = new UnoGameHome();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    public UnoGameHome() {
	setIconImage(Toolkit.getDefaultToolkit().getImage(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.UNO_ICON, false))));
	setTitle(UnoConstants.UNO_TXT);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 757, 608);
	setResizable(false);

	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	fileMenu = new FileMenu(UnoConstants.FILE);
	helpFileMenu = new HelpFileMenu(UnoConstants.HELP);
	menuBar.add(fileMenu);
	menuBar.add(helpFileMenu);

	contentPane = new JPanel();
	contentPane.setBackground(new Color(230, 230, 250));
	contentPane.setBorder(null);
	setContentPane(contentPane);

	JLabel lblWelcomeToUno = new JLabel(UnoConstants.WELCOME_TITLE);
	lblWelcomeToUno.setBackground(new Color(238, 232, 170));
	lblWelcomeToUno.setHorizontalAlignment(SwingConstants.CENTER);
	lblWelcomeToUno.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
	lblWelcomeToUno.setForeground(new Color(128, 0, 0));

	desktopPane = new JDesktopPane();
	GroupLayout groupLayout = new GroupLayout(contentPane);
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE).addContainerGap())
		.addComponent(lblWelcomeToUno, GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE));
	groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
		groupLayout.createSequentialGroup().addComponent(lblWelcomeToUno, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
			.addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE).addContainerGap()));

	newPlayerPanelView = new NewPlayerPanelView();
	desktopPane.add(newPlayerPanelView);
	newPlayerPanelView.setOpaque(false);

	btnResetGame = new JButton(UnoConstants.RESET_GAME);
	btnResetGame.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.RESET_IMG, false))));
	btnResetGame.setBounds(610, 3, 118, 28);
	desktopPane.add(btnResetGame);

	startGameBtn = new JButton(UnoConstants.START_GAME);
	startGameBtn.setHorizontalAlignment(SwingConstants.LEFT);
	startGameBtn.setIcon(new ImageIcon(UnoGameHome.class.getResource(UnoFacade.getIconPath(UnoConstants.START_IMG, false))));
	startGameBtn.setBounds(500, 3, 112, 28);
	desktopPane.add(startGameBtn);
	gamePanel = new GamePanelView();
	gamePanel.getPlayerNameLbl().setForeground(new Color(0, 255, 255));
	gamePanel.setBounds(0, 34, 729, 465);
	desktopPane.add(gamePanel);
	contentPane.setLayout(groupLayout);
	new UnoViewEventHandler(this).init();
    }

    public JButton getResetGameBtn() {
	return btnResetGame;
    }

    public JButton getStartGameBtn() {
	return startGameBtn;
    }

    public GamePanelView getGamePanel() {
	return gamePanel;
    }

    public NewPlayerPanelView getNewPlayerPanel() {
	return newPlayerPanelView;
    }

    public FileMenu getFileMenu() {
	return fileMenu;
    }

    public HelpFileMenu getHelpFileMenu() {
	return helpFileMenu;
    }

    public JDesktopPane getDesktopPane() {
	return desktopPane;
    }
}
