package weigl.knn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class KNNFrame extends JFrame {

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

	OverPanel op = new HelpPanel(new Rectangle(10, 10, 250, 400));
	op.setBackground(new Color(0, 0, 0, 0.4f));
	op.setOpaque(true);
	glassPane.add(op);

	glassPane.setVisible(true);
    }

    public static void main(String[] args) {
	KNNFrame frame = new KNNFrame();
	frame.setVisible(true);
    }
}
