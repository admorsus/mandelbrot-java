package mandelbrot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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

		frame.pack();
		frame.setVisible(true);
	}
	
	private JPanel makeBotPanel() {
		JPanel botpanel = new JPanel();
		botpanel.setLayout(new FlowLayout(0));
		
		JButton coordsBtn = new JButton("Choose coords");
		coordsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CoordinatesDialog(display);
			}
		});
		botpanel.add(coordsBtn);
		
		FractalList list = new FractalList(display);
		botpanel.add(list.getCombo());
		
		return botpanel;
	}

	public static void main(String[] args) {
		new Window();
	}

}
