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
		String x = NumberUtils.formatDouble(f.getXoffset(), f.getZoom());
		String y = NumberUtils.formatDouble(f.getYoffset(), f.getZoom());
		String z = NumberUtils.formatDouble(f.getZoom(), f.getZoom());

		dialog.setLocationRelativeTo(f.getDisplay());
		dialog.setTitle("Coordinates");
		Container pane = dialog.getContentPane();
		pane.setLayout(new GridLayout(3, 2));

		var xfield = new JTextField(x);
		xfield.setHorizontalAlignment(SwingConstants.LEADING);
		var yfield = new JTextField(y);
		yfield.setHorizontalAlignment(SwingConstants.LEADING);
		var zfield = new JTextField(z);
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
					double x = Double.parseDouble(xfield.getText().replace(',', '.'));
					double y = Double.parseDouble(yfield.getText().replace(',', '.'));
					double z = Double.parseDouble(zfield.getText().replace(',', '.'));

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
