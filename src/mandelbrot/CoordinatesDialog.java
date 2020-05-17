package mandelbrot;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CoordinatesDialog {

	JDialog dialog = new JDialog();

	public CoordinatesDialog(Mandelbrot f) {
		double x = f.getXoffset();
		double y = f.getYoffset();
		double z = f.getZoom();

		dialog.setLocationRelativeTo(f.getDisplay());
		dialog.setTitle("Coordinates");
		Container pane = dialog.getContentPane();
		pane.setLayout(new GridLayout(3, 2));

		var xfield = new JTextField(Double.toString(x), 6);
		xfield.setHorizontalAlignment(SwingConstants.LEADING);
		var yfield = new JTextField(Double.toString(y));
		yfield.setHorizontalAlignment(SwingConstants.LEADING);
		var zfield = new JTextField(Double.toString(z));
		zfield.setHorizontalAlignment(SwingConstants.LEADING);

		pane.add(new JLabel("X coord:"));
		pane.add(xfield);
		pane.add(new JLabel("Y coord:"));
		pane.add(yfield);
		pane.add(new JLabel("Zoom factor:"));
		pane.add(zfield);

		KeyAdapter closeOnEnter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					double x = Double.parseDouble(xfield.getText());
					double y = Double.parseDouble(yfield.getText());
					double z = Double.parseDouble(zfield.getText());

					f.setCoords(x, y, z);
					f.draw();
					dialog.dispose();
				}
			}
		};

		xfield.addKeyListener(closeOnEnter);
		yfield.addKeyListener(closeOnEnter);
		zfield.addKeyListener(closeOnEnter);

		// Set transparent window icon
		Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		dialog.setIconImage(icon);

		dialog.pack();
		dialog.setVisible(true);
	}
}
