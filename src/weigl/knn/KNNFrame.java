package weigl.knn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KNNFrame extends JFrame {
    private static final long serialVersionUID = 4973091727526645208L;
    ClickableVizPanel clickableVizPanel = new ClickableVizPanel();
    private JPanel glassPane = new JPanel(null, true);

    public KNNFrame() {
	setTitle("k nearest neighbor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(clickableVizPanel);
	setSize(750, 750);

	setGlassPane(glassPane);
	glassPane.setOpaque(false);

	OverPanel op = new HelpPanel(new Rectangle(10, 10, 350, 350));
	op.setBackground(new Color(0, 0, 0, 0.4f));
	op.setOpaque(true);
	glassPane.add(op);

	OverPanel ip = new InputPanel(new Rectangle(10, 400, 200, 100),
		clickableVizPanel);
	ip.setBackground(new Color(0, 0, 0, 0.4f));
	ip.setOpaque(true);
	// glassPane.add(ip);

	glassPane.setVisible(true);
    }

    public static void main(String[] args) {
	KNNFrame frame = new KNNFrame();
	frame.setVisible(true);
    }
}
