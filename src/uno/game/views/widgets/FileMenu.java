package uno.game.views.widgets;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import uno.game.views.UnoGameHome;

public class FileMenu extends JMenu {

    private static final long serialVersionUID = 1L;
    private JMenuItem startMenuItem;
    private JMenuItem resetMenuItem;
    private JMenuItem exitMenuItem;

    public FileMenu(String menuName) {
	super(menuName);
	startMenuItem = new JMenuItem("Start Game");
	startMenuItem.setIcon(new ImageIcon(UnoGameHome.class.getResource("/uno/game/resource/img_small/start.png")));
	resetMenuItem = new JMenuItem("Reset Game");
	resetMenuItem.setIcon(new ImageIcon(UnoGameHome.class.getResource("/uno/game/resource/img_small/reset.png")));
	exitMenuItem = new JMenuItem("Exit Game");
	exitMenuItem.setIcon(new ImageIcon(UnoGameHome.class.getResource("/uno/game/resource/img_small/exit.png")));
	add(startMenuItem);
	add(resetMenuItem);
	add(exitMenuItem);
    }

    public JMenuItem getStartMenuItem() {
	return startMenuItem;
    }

    public JMenuItem getResetMenuItem() {
	return resetMenuItem;
    }

    public JMenuItem getExitMenuItem() {
	return exitMenuItem;
    }

}
