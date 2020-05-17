package mandelbrot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Window {

	final static String app_name = "Fractal Diver";

	File saveFolder;
	
	Mandelbrot display = new Mandelbrot(1200, 900);
	JFrame frame = new JFrame(app_name);

	private Window() {

		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		frame.setLocation(200, 50);

		display.setDefaultCoords();
		display.draw();

		pane.add(display.getDisplay(), BorderLayout.CENTER);
		
		JPanel botpanel = new JPanel();
		botpanel.setLayout(new BorderLayout());
		pane.add(botpanel, BorderLayout.SOUTH);
		botpanel.add(makeBotLeftPanel(), BorderLayout.WEST);
		botpanel.add(makeBotRightPanel(), BorderLayout.EAST);
		

		addActions();
		frame.pack();
		frame.setVisible(true);
	}

	private void addActions() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				display.setDimension(frame.getSize());
				display.draw();
			}
		});

		frame.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg0) {
				display.setDimension(frame.getSize());
				display.draw();
			}
		});
	}

	private JPanel makeBotLeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(0));

		// Coordinates button
		
		JButton coordsBtn = new JButton("Choose coords");
		coordsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new CoordinatesDialog(display);
			}
		});
		panel.add(coordsBtn);
		
		// Fractal select ComboBox

		JComboBox fractalSel = new FractalChooser(display).box;
		panel.add(fractalSel);

		// Color palette ComboBox
		
		JComboBox colorSel = new ColorChooser(display).box;
		panel.add(colorSel);
		
		JCheckBox innerColorCheck = new JCheckBox("Inner painting");
		innerColorCheck.setSelected(true);
		innerColorCheck.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					display.innerColor = 255;
				} else {
					display.innerColor = 0;
				}
				display.draw();
			}
		});
		panel.add(innerColorCheck);
		
		
		
		

		return panel;
	}
	
	
	private JPanel makeBotRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(1));
		
		JLabel positionLabel = new JLabel();
		panel.add(positionLabel);
		display.getDisplay().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Double[] coords = display.getCoords(e.getX(), e.getY());
				String x = NumberUtils.formatDouble(coords[0], coords[2]);
				String y = NumberUtils.formatDouble(coords[1], coords[2]);
				String z = NumberUtils.formatDouble(coords[2], coords[2]);
				positionLabel.setText("X: " + x + " Y: " + y + " Z: " + z);
			}
		});
		
		panel.add(new JSeparator(JSeparator.VERTICAL));
		
		JButton choosePathBtn = new JButton("Folder");
		ActionListener choosePathListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(saveFolder); // start at application current directory
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showOpenDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
					    saveFolder = fc.getSelectedFile();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};
		choosePathBtn.addActionListener(choosePathListener);
		JButton saveImageBtn = new JButton("Save image");
		panel.add(saveImageBtn);
		panel.add(choosePathBtn);
		saveImageBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (saveFolder == null) {
					choosePathListener.actionPerformed(null);
				}
				if (saveFolder != null) {
					try {
					    String name = display.toString();
					    String ending = ".png";
					    File output = new File(saveFolder, name + ending);
					    for (int n = 1; Files.exists(output.toPath()); n++) {
					    	ending = "(" + Integer.toString(n) + ").png";
					    	output = new File(saveFolder, name + ending);
					    }
					    
					    Files.createFile(output.toPath());
					    ImageIO.write(display.mandelbrot(), "png", output);
					    System.out.println("Image saved at " + output.getAbsolutePath());
					} catch (IOException e) {
					    e.printStackTrace();
					}
				}
			}
		});
		
		return panel;
		
	}

	public static void main(String[] args) {
		new Window();
	}

}
