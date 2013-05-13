package weigl.knn;

import java.awt.BorderLayout;
import java.awt.Color;
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

    public static String help = "Click for creating a new feature. Select a class with the key <b>1-6</b>. View the classified room with <b>c</b>";

    public HelpPanel(Rectangle rect) {
	setLayout(new BorderLayout());
	lbl = new JLabel("Help close");
	lbl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	lblHelp = new JLabel("<html>" + help);
	add(lbl, BorderLayout.NORTH);
	add(lblHelp);
	setComponentAlpha(1f);

	lblHelp.setForeground(Color.WHITE);

	big = rect;
	small = new Rectangle((int) big.getX(), (int) big.getY(), 25, 25);

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
