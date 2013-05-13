package weigl.knn;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

public class ClickableVizPanel extends JPanel implements MouseListener,
	KeyListener {
    private static final int DELTA = 50;
    private static final int RADIUS = 10;
    private static final int DELTA_MESH = 10;
    private static final int K = 2;
    private List<double[]> pointList = new LinkedList<>();
    private volatile int currentColor = 0;
    private boolean showMesh;
    private double[][] mesh;

    public static Color[] COLOR_CLASSES = { Color.RED, Color.BLUE, Color.GREEN,
	    Color.CYAN, Color.PINK, Color.magenta, Color.orange, Color.yellow };

    public ClickableVizPanel() {
	setFocusable(true);
	addMouseListener(this);
	addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
	System.out.println("ClickableVizPanel.paint()");
	super.paint(g);
	Graphics2D g2 = (Graphics2D) g;
	drawGrid(g2);
	if (showMesh)
	    drawMesh(g2);

	drawPoints(g2);

    }

    private void drawMesh(Graphics2D g2) {
	Graphics2D g = (Graphics2D) g2.create();
	AlphaComposite ac1 = AlphaComposite.getInstance(
		AlphaComposite.SRC_OVER, 0.2f);
	g.setComposite(ac1);

	for (int i = 0; i < mesh[0].length; i++) {
	    drawPoint(g, mesh[0][i], mesh[1][i],
		    COLOR_CLASSES[(int) mesh[2][i]]);
	}

    }

    private void drawPoints(Graphics2D g2) {
	Graphics2D g = (Graphics2D) g2.create();
	for (double[] p : pointList) {
	    drawPoint(g, p[0], p[1], COLOR_CLASSES[(int) p[2]]);
	}
    }

    private void drawPoint(Graphics2D g, double p, double q, Color color) {
	g.setColor(color);
	final int x = (int) ((p * getWidth()) - RADIUS);
	final int y = (int) ((q * getHeight()) - RADIUS);
	g.fillOval(x, y, RADIUS, RADIUS);
    }

    private void drawGrid(Graphics2D g2) {
	System.out.println("ClickableVizPanel.drawGrid()");
	final int width = getWidth();
	final int height = getHeight();

	System.out.println(width);
	System.out.println(height);

	int centerX = width / 2;
	int centerY = height / 2;

	g2.setColor(Color.white);
	g2.fillRect(0, 0, width, height);

	Graphics2D g = (Graphics2D) g2.create();
	g.setColor(Color.BLACK);
	g.setStroke(new BasicStroke(2));
	g.drawLine(centerX, 0, centerX, height);
	g.drawLine(0, centerY, width, centerY);

	g = (Graphics2D) g2.create();
	g.setStroke(new BasicStroke(1));
	g.setColor(Color.LIGHT_GRAY);

	for (int x = DELTA; x < centerX + DELTA; x += DELTA) {
	    g.drawLine(centerX + x, 0, centerX + x, height);
	    g.drawLine(centerX - x, 0, centerX - x, height);
	}

	for (int y = DELTA; y < centerY + DELTA; y += DELTA) {
	    g.drawLine(0, centerY + y, width, centerY + y);
	    g.drawLine(0, centerY - y, width, centerY - y);
	}

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
	if (e.getButton() == MouseEvent.BUTTON1) {
	    System.out.println(currentColor);
	    double x = e.getX(), y = e.getY();

	    pointList.add(new double[] { x / getWidth(), y / getHeight(),
		    currentColor });
	    repaint();
	    invalidate();
	}
	if (e.getButton() == MouseEvent.BUTTON3) {
	    KNNClassificator c = new KNNClassificator(pointList);

	    double x = (double) e.getX() / getWidth();
	    double y = (double) e.getY() / getWidth();

	    System.out.println(c.classify(x, y, 1));
	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	System.out.println("ClickableVizPanel.mouseReleased()");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	System.out.println("ClickableVizPanel.mouseEntered()");
    }

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
	System.out.println(e.getKeyChar());
	if ('0' <= e.getKeyChar() && e.getKeyChar() <= '9') {
	    changeColor(e.getKeyChar() - '0');
	}

	if (e.getKeyChar() == 'c') {
	    calculateMesh();
	    showMesh = true;
	    repaint();
	    invalidate();
	}

	if (e.getKeyChar() == 'x') {
	    showMesh = false;
	}

    }

    private void calculateMesh() {
	int w = getWidth();
	int h = getHeight();

	KNNClassificator c = new KNNClassificator(pointList);

	mesh = new double[3][((w / DELTA_MESH) + 1) * ((h / DELTA_MESH) + 1)];
	int i = 0;
	for (double x = 0; x < w; x += DELTA_MESH) {
	    for (double y = 0; y < h; y += DELTA_MESH, i++) {
		double p = x / w, q = y / h;
		mesh[0][i] = p;
		mesh[1][i] = q;
		mesh[2][i] = c.classify(p, q, K);
	    }
	}
    }

    private void changeColor(int i) {
	System.out.println("ClickableVizPanel.changeColor(" + i + ")");
	currentColor = i;
	System.out.println(currentColor);
	System.out.println(COLOR_CLASSES[currentColor]);
    }
}
