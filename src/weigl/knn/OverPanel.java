package weigl.knn;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class OverPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private float componentAlpha = 0.5f;

    public float getComponentAlpha() {
	return componentAlpha;
    }

    public void setComponentAlpha(float componentAlpha) {
	this.componentAlpha = componentAlpha;
    }

    public OverPanel() {
    }

    @Override
    public void paint(Graphics g) {
	g.setColor(getBackground());
	g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

	Graphics2D g2 = (Graphics2D) g.create();
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
		getComponentAlpha());
	g2.setComposite(ac);
	super.paintChildren(g2);
    }
}
