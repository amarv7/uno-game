package uno.game.views.widgets;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import uno.game.views.UnoGameHome;

public class HelpFileMenu extends JMenu {

    private static final long serialVersionUID = 1L;
    private JMenuItem gameRulesMenuItem;
    private JMenuItem aboutMenuItem;

    public HelpFileMenu(String menuName) {
	super(menuName);
	gameRulesMenuItem = new JMenuItem("Game Rules");
	gameRulesMenuItem.setIcon(new ImageIcon(UnoGameHome.class.getResource("/uno/game/resource/img_small/gamehelp.png")));
	aboutMenuItem = new JMenuItem("About");
	aboutMenuItem.setIcon(new ImageIcon(UnoGameHome.class.getResource("/uno/game/resource/img_small/about.png")));
	add(gameRulesMenuItem);
	add(aboutMenuItem);
    }

    public JMenuItem getGameRulesMenuItem() {
	return gameRulesMenuItem;
    }

    public JMenuItem getAboutMenuItem() {
	return aboutMenuItem;
    }

}
