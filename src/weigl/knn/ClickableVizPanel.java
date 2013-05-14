package weigl.knn;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClickableVizPanel extends JPanel implements MouseListener,
	KeyListener {
    private static final long serialVersionUID = 1L;
    private static final char MESH_KEY = 'm';
    private static final char CLEAR_KEY = 'c';
    private static final char MOUSE_MODE_KEY = 't';
    private static final char RANDOM_KEY = 'r';

    private int gridDelta = 50;
    private int bulletRadius = 10;
    private int meshDelta = 10;
    private int k = 4;

    private List<double[]> pointList = new LinkedList<>();
    private volatile int currentColor = 0;
    private boolean showMesh;
    private double[][] mesh;
    private boolean showMouseDecision = true;

    public static Color[] COLOR_CLASSES = { Color.RED, Color.BLUE, Color.GREEN,
	    Color.CYAN, Color.PINK, Color.magenta, Color.orange, Color.yellow };

    private MousePositionTracker mpt = new MousePositionTracker();

    public ClickableVizPanel() {
	setFocusable(true);
	addMouseListener(this);
	addKeyListener(this);
	addMouseMotionListener(mpt);

	addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
	    public void mouseMoved(MouseEvent e) {
		if (showMouseDecision) {
		    invalidate();
		    repaint();
		}
	    }
	});

    }

    @Override
    public void paint(Graphics g) {
	System.out.println("ClickableVizPanel.paint()");
	super.paint(g);
	Graphics2D g2 = (Graphics2D) g;
	drawGrid(g2);

	if (showMesh)
	    drawMesh(g2);

	if (showMouseDecision)
	    drawMouseDecision(g2);

	drawPoints(g2);
    }

    private void drawMouseDecision(Graphics2D g2) {
	Point lastMousePoint = mpt.getLastEvent().getPoint();

	KNNClassificator c = new KNNClassificator(pointList);
	final int w = getWidth();
	final int h = getHeight();

	double x = lastMousePoint.getX() / w;
	double y = lastMousePoint.getY() / h;

	Point p = lastMousePoint.getLocation();

	Calculation[] r = c.nearestPoints(x, y, getK());
	// System.out.println(Arrays.toString(r));
	System.out.println(p);

	for (Calculation q : r) {
	    if (q == null)
		break;
	    Color clr = COLOR_CLASSES[q.label];

	    Point t = new Point(p);
	    Point s = new Point((int) (q.x * w), (int) (q.y * h));

	    g2.setColor(clr);
	    g2.drawLine(t.x, t.y, s.x, s.y);

	    int distance = (int) t.distance(s);
	    g2.drawOval((int) t.x - distance, (int) t.y - distance,
		    (int) distance * 2, (int) distance * 2);
	}
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
	final int x = (int) ((p * getWidth()) - bulletRadius);
	final int y = (int) ((q * getHeight()) - bulletRadius);
	g.fillOval(x, y, bulletRadius * 2, bulletRadius * 2);
    }

    private void drawGrid(Graphics2D g2) {
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

	for (int x = gridDelta; x < centerX + gridDelta; x += gridDelta) {
	    g.drawLine(centerX + x, 0, centerX + x, height);
	    g.drawLine(centerX - x, 0, centerX - x, height);
	}

	for (int y = gridDelta; y < centerY + gridDelta; y += gridDelta) {
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
    }

    @Override
    public void mouseEntered(MouseEvent e) {
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
	if ('0' <= e.getKeyChar() && e.getKeyChar() <= '9') {
	    changeColor(e.getKeyChar() - '0');
	}

	if (e.getKeyCode() == KeyEvent.VK_F2) {
	    int k = askFor("k:", getK());
	    setK(k);
	}

	if (e.getKeyChar() == RANDOM_KEY) {
	    generateRandom();
	}

	if (e.getKeyChar() == MESH_KEY) {
	    showMesh = !showMesh;
	    if (showMesh)
		calculateMesh();
	}

	if (e.getKeyChar() == MOUSE_MODE_KEY) {
	    showMouseDecision = !showMouseDecision;
	}

	if (e.getKeyChar() == CLEAR_KEY) {
	    pointList.clear();
	    showMesh = false;
	}
	repaint();
	invalidate();
    }

    private int askFor(String string, int init) {
	String s = JOptionPane.showInputDialog(this, "k:", "" + init);
	try {
	    return Integer.parseInt(s);
	} catch (Exception e) {
	    return init;
	}
    }

    private void generateRandom() {
	final int w = getWidth();
	final int h = getHeight();
	RandomGenerator rg = new RandomGenerator(10, Math.min(w, h));

	for (int i = 0; i < k; i++) {
	    rg.newRound();
	    for (int j = 0; j < 3 * k; j++) {
		int[] p = rg.getNextFeature();
		double d[] = { (double) p[0] / w, (double) p[1] / h, i };
		pointList.add(d);
	    }
	}
	invalidate();
	repaint();
    }

    private void calculateMesh() {
	int w = getWidth();
	int h = getHeight();

	KNNClassificator c = new KNNClassificator(pointList);

	mesh = new double[3][((w / meshDelta) + 1) * ((h / meshDelta) + 1)];
	int i = 0;
	for (double x = 0; x < w; x += meshDelta) {
	    for (double y = 0; y < h; y += meshDelta, i++) {
		double p = x / w, q = y / h;
		mesh[0][i] = p;
		mesh[1][i] = q;
		mesh[2][i] = c.classify(p, q, k);
	    }
	}
    }

    private void changeColor(int i) {
	currentColor = i;
    }

    public int getGridDelta() {
	return gridDelta;
    }

    public void setGridDelta(int gridDelta) {
	this.gridDelta = gridDelta;
    }

    public int getBulletRadius() {
	return bulletRadius;
    }

    public void setBulletRadius(int bulletRadius) {
	this.bulletRadius = bulletRadius;
    }

    public int getMeshDelta() {
	return meshDelta;
    }

    public void setMeshDelta(int meshDelta) {
	this.meshDelta = meshDelta;
    }

    public int getK() {
	return k;
    }

    public void setK(int k) {
	this.k = k;
    }

    public int getCurrentColor() {
	return currentColor;
    }

    public void setCurrentColor(int currentColor) {
	this.currentColor = currentColor;
    }

    public boolean isShowMesh() {
	return showMesh;
    }

    public void setShowMesh(boolean showMesh) {
	this.showMesh = showMesh;
    }
}
