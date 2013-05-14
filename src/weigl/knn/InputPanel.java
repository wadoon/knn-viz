package weigl.knn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends OverPanel {
    private static final long serialVersionUID = -2603831126086845829L;
    private JLabel lbl;
    private boolean helpShown = false;
    private Rectangle big;
    private Rectangle small;
    private JTextField txtK, txtMgd;
    private ClickableVizPanel cvp;

    public InputPanel(Rectangle rect, ClickableVizPanel p) {
	cvp = p;
	setLayout(new BorderLayout());
	lbl = new JLabel("P");
	lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
	lbl.setBorder(BorderFactory.createEmptyBorder(5, 8, 8, 5));

	JPanel content = new JPanel(new GridLayout(0, 2));
	content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	add(lbl, BorderLayout.NORTH);
	add(content);
	setComponentAlpha(1f);
	content.setOpaque(false);

	createInputs(content);

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

    private void createInputs(JPanel content) {
	content.add(createLabel("k:", txtK = createInput(cvp.getK())));
	content.add(txtK);

	content.add(createLabel("meshgrid dense:", txtMgd = createInput(cvp.getMeshDelta())));
	content.add(txtMgd);

	JButton btnUpdate = new JButton("!");
	btnUpdate.setBorderPainted(false);
	btnUpdate.setBackground(Color.WHITE);
	btnUpdate.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		cvp.setK(Integer.parseInt(txtK.getText()));
		cvp.setMeshDelta(Integer.parseInt(txtMgd.getText()));
	    }
	});
	content.add(createLabel("", null));
	content.add(btnUpdate);
    }

    private JTextField createInput(Object o) {
	JTextField txt = new JTextField(5);
	txt.setText(o.toString());
	txt.setOpaque(false);
	txt.setHorizontalAlignment(JTextField.RIGHT);
	lbl.setForeground(Color.WHITE);
	return txt;
    }

    private JLabel createLabel(String string, JComponent c) {
	JLabel lbl = new JLabel(string);
	lbl.setLabelFor(c);
	lbl.setForeground(Color.WHITE);
	return lbl;
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
