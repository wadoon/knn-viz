package weigl.knn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class HelpPanel extends OverPanel {
    private JLabel lbl;
    private boolean helpShown = false;
    private Rectangle big;
    private Rectangle small;
    private JLabel lblHelp;

    public static String help = "<h3>Instruction</h3><br>"
	    + "&nbsp;&nbsp;* Click for creating a new feature.<br>"
	    + "&nbsp;&nbsp;* Select a class with the key <strong>1-6</strong>.<br>"
	    + "&nbsp;&nbsp;* You can drag and close this window<br>"
	    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;by clicking on '?'.<br>"
	    + "<h3>Keys:</h3><br>" + "&nbsp;&nbsp;m: toggle mesh grid<br>"
	    + "&nbsp;&nbsp;c: clear features<br>"
	    + "&nbsp;&nbsp;t: toggle mouse mode<br>"
	    + "<h3>Information</h3><br>License: gplv3 - Alexander Weigl"
	    + "<br>";

    public HelpPanel(Rectangle rect) {
	setLayout(new BorderLayout());
	lbl = new JLabel("?");
	lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
	lbl.setBorder(BorderFactory.createEmptyBorder(5, 8, 8, 5));

	lblHelp = new JLabel("<html>" + help);
	lblHelp.setVerticalAlignment(JLabel.TOP);
	lblHelp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	add(lbl, BorderLayout.NORTH);
	add(lblHelp);
	setComponentAlpha(1f);

	lblHelp.setForeground(Color.WHITE);

	big = rect;
	small = new Rectangle((int) big.getX(), (int) big.getY(), 25, 28);

	lbl.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		toggleVisibility();
	    }
	});
	toggleVisibility();

	new Dragger(lbl, this);
    }

    public void toggleVisibility() {
	if (helpShown) {
	    big = getBounds();
	    setBounds(small);
	} else {
	    // small = getBounds();
	    setBounds(big);
	}
	helpShown = !helpShown;
    }
}
