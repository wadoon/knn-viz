package weigl.knn;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

public class Dragger implements MouseMotionListener, MouseListener {

    private Point lastPoint;
    private JComponent component;

    public Dragger(JComponent listen, JComponent c) {
	component = c;
	listen.addMouseListener(this);
	listen.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
	lastPoint = e.getLocationOnScreen();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	lastPoint = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	int deltaPointX = (int) (e.getLocationOnScreen().getX() - lastPoint
		.getX());
	int deltaPointY = (int) (e.getLocationOnScreen().getY() - lastPoint
		.getY());

	Point p = component.getLocation();
	p = new Point(p.x + deltaPointX, p.y + deltaPointY);
	component.setLocation(p);

	lastPoint = e.getLocationOnScreen();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
