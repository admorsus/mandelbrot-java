package mandelbrot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class Window {

	final static String app_name = "Fractal";

	Mandelbrot fractal = new Mandelbrot(800, 700);
	JFrame frame = new JFrame(app_name);
	JPanel botpanel = new JPanel();
	JButton coordinatesBtn = new JButton("Choose coords");

	private Window() {

		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		frame.setLocation(400, 100);

		botpanel.setLayout(new BorderLayout());
		botpanel.add(coordinatesBtn, BorderLayout.WEST);
		JLabel label = new JLabel("made with <3 by admorsus");
		label.setForeground(Color.GRAY);
		botpanel.add(label, BorderLayout.EAST);

		fractal.setCoords(-0.6, 0.25, 1.25);
		fractal.draw();
		
		pane.add(fractal.getDisplay(), BorderLayout.CENTER);
		pane.add(botpanel, BorderLayout.SOUTH);

		addActions();
		frame.pack();
		frame.setVisible(true);
	}

	private void addActions() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		coordinatesBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				var dialog = new CoordinatesDialog(fractal);
			}
		});
	}

	public static void main(String[] args) {
		new Window();
	}

}
