package weigl.knn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AppletStarter extends JApplet {
    private static final long serialVersionUID = 1L;
    private String clazz;
    private Class<JFrame> frameClass;

    public AppletStarter() {
    }

    @SuppressWarnings("unchecked")
    public void start() {
	clazz = getParameter("frame");
	try {
	    frameClass = (Class<JFrame>) Class.forName(clazz);
	} catch (ClassCastException | ClassNotFoundException e) {
	    add(new JLabel(e.getMessage()));
	    return;
	}

	JButton btn = new JButton(getParameter("buttonLabel"));
	btn.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    JFrame frame = frameClass.newInstance();
		    frame.setVisible(true);
		} catch (InstantiationException | IllegalAccessException e1) {
		    removeAll();
		    add(new JLabel(e1.getMessage()));
		}
	    }
	});
	add(btn);
    }
}
