package weigl.knn;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MousePositionTracker extends MouseMotionAdapter {
    private MouseEvent lastEvent;

    @Override
    public void mouseMoved(MouseEvent e) {
	lastEvent = e;
    }

    public MouseEvent getLastEvent() {
	return lastEvent;
    }
}
