package uno.game.views;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import uno.game.constants.UnoConstants;
import java.awt.Color;
import javax.swing.ImageIcon;

public class About extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Create the About frame.
     */
    public About() {
    	setFrameIcon(new ImageIcon(About.class.getResource("/uno/game/resource/img_small/uno_icon.png")));
    	getContentPane().setBackground(new Color(0, 102, 153));
	setBounds(100, 100, 396, 225);
	setClosable(true);
	JLabel copyRightTxt = new JLabel(UnoConstants.COPYRIGHT_TXT);
	copyRightTxt.setForeground(new Color(255, 255, 153));
	copyRightTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
	copyRightTxt.setHorizontalAlignment(SwingConstants.CENTER);

	JLabel developedByTxt = new JLabel(UnoConstants.DEVELOPED_BY_TXT);
	developedByTxt.setForeground(new Color(255, 255, 153));
	developedByTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
	developedByTxt.setHorizontalAlignment(SwingConstants.CENTER);

	JLabel reqTxt = new JLabel(UnoConstants.GAME_REQ_TXT);
	reqTxt.setForeground(new Color(255, 255, 153));
	reqTxt.setFont(new Font("Tahoma", Font.PLAIN, 13));
	reqTxt.setHorizontalAlignment(SwingConstants.CENTER);
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
		groupLayout
			.createSequentialGroup()
			.addContainerGap()
			.addGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING, false)
					.addComponent(developedByTxt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(reqTxt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(copyRightTxt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)).addContainerGap()));
	groupLayout
		.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
			groupLayout.createSequentialGroup().addGap(55).addComponent(copyRightTxt).addGap(18).addComponent(reqTxt).addGap(18).addComponent(developedByTxt)
				.addContainerGap(56, Short.MAX_VALUE)));
	getContentPane().setLayout(groupLayout);
    }
}
