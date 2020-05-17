package mandelbrot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {

	final static String app_name = "Fractal Diver";

	Mandelbrot display = new Mandelbrot(1200, 900);
	JFrame frame = new JFrame(app_name);

	private Window() {

		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		frame.setLocation(400, 100);

		display.setDefaultCoords();
		display.draw();

		pane.add(display.getDisplay(), BorderLayout.CENTER);
		pane.add(makeBotPanel(), BorderLayout.SOUTH);

		addActions();
		frame.pack();
		frame.setVisible(true);
	}

	private void addActions() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				display.setDimension(frame.getSize());
			}
		});

		frame.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg0) {
				display.setDimension(frame.getSize());
			}
		});
	}

	private JPanel makeBotPanel() {
		JPanel botpanel = new JPanel();
		botpanel.setLayout(new FlowLayout(0));

		// Coordinates button
		
		JButton coordsBtn = new JButton("Choose coords");
		coordsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CoordinatesDialog(display);
			}
		});
		botpanel.add(coordsBtn);
		
		// Fractal select ComboBox

		JComboBox fractalSel = new FractalChooser(display).box;
		botpanel.add(fractalSel);

		// Color palette ComboBox
		
		JComboBox colorSel = new ColorChooser(display).box;
		botpanel.add(colorSel);

		return botpanel;
	}

	public static void main(String[] args) {
		new Window();
	}

}
