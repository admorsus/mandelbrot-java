package mandelbrot;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

// Mandelbrot set display
public class Mandelbrot {

	// Image size
	int width;
	int height;

	// Color palette
	int[] palette = ColorPalettes.RedPalette();
	int innerColor = 0;

	// Adjustment settings
	double xoffset = 0;
	double yoffset = 0;
	double zoom = 1;

	// Fractal to display
	Fractal fractal = MandelbrotFractals.Mandelbrot;

	private int maxd;
	private JLabel display = new JLabel();

	public Mandelbrot(int width, int heigth) {
		this.width = width;
		this.height = heigth;
		this.maxd = Math.max(width, heigth);
		setDefaultCoords();
		addActions();
		draw();
	}

	// Main method
	public void draw() {
		Image img = mandelbrot();
		display.setIcon(new ImageIcon(img));
	}

	// Necesito una función que sea capaz de conseguir un punto que esté entre 
	
	// Add all actions
	public void addActions() {

		// Implements mouse click zoom
		display.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					zoom = zoom * 1.75;
				} else {
					zoom = zoom / 1.25;
				}

				xoffset += map(e.getX(), 0, width, -2, 2) / zoom;
				yoffset += map(e.getY(), 0, height, -2, 2) / zoom;
				draw();
			}
		});

		// Implements mouse wheel zoom
		display.addMouseWheelListener(new MouseAdapter() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				double ratio = 1.05;
				double depth = e.getWheelRotation();

				if (depth > 0) {
					depth = Math.abs(depth) / ratio;
				} else {
					depth = Math.abs(depth) * ratio;
				}

				zoom = zoom * depth;
				xoffset += map(e.getX(), 0, width, -2, 2) / zoom * 0.1;
				yoffset += map(e.getY(), 0, height, -2, 2) / zoom * 0.1;
				draw();
			}
		});

		// Implements mouse drag moving
		display.addMouseListener(new MouseAdapter() {
			int ix, iy;

			// Stores starting point
			public void mousePressed(MouseEvent e) {
				ix = e.getX();
				iy = e.getY();
			}

			// Offset the displaced distance
			public void mouseReleased(MouseEvent e) {
				int x = ix - e.getX();
				int y = iy - e.getY();
				int halfd = maxd / 2;

				xoffset += map(x, -halfd, halfd, -2, 2) / zoom;
				yoffset += map(y, -halfd, halfd, -2, 2) / zoom;
				draw();
			}
		});
	}

	// Set display dimensions
	public void setDimension(Dimension d) {
		height = d.height;
		width = d.width;
		draw();
	}

	// Set fractal's default coordinates
	public void setDefaultCoords() {
		this.xoffset = fractal.x;
		this.yoffset = fractal.y;
		this.zoom = fractal.zoom;
	}

	// Set given coordinates
	public void setCoords(double x, double y, double zoom) {
		this.xoffset = x;
		this.yoffset = y;
		this.zoom = zoom;
	}

	// Maps n between given min and max
	public static double map(double n, double inmin, double inmax, double outmin, double outmax) {
		return (n - inmin) * (outmax - outmin) / (inmax - inmin) + outmin;
	}

	// Mandelbrot image
	public Image mandelbrot() {

		var img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int max_iterations = 100;
		double iterator = 1;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				double a = map(x, 0, maxd, -2, 2) / zoom + xoffset;
				double b = map(y, 0, maxd, -2, 2) / zoom + yoffset;

				double n = 0;
				double ca = a, cb = b;

				while (n < max_iterations) {
					double aa = fractal.zreal(a, b);
					double bb = fractal.zimag(a, b);
					a = aa + ca;
					b = bb + cb;

					if (a * a + b * b > 16) {
						break;
					}
					n += iterator;
				}

				double bright = map(n, 0, max_iterations, 0, 255);
				// bright = map(bright, 0, 1, 0, 255);

				int color;
				if (n >= 100) {
					color = palette[innerColor];
				} else {
					int index = (int) Math.floor(bright);
					// if (index > 150) { System.out.println(index); }
					color = palette[index];
				}

				img.setRGB(x, y, color);
			}
		}

		return img;
	}

	public double getXoffset() {
		return xoffset;
	}

	public double getYoffset() {
		return yoffset;
	}

	public double getZoom() {
		return zoom;
	}

	public JLabel getDisplay() {
		return display;
	}
	
	public void setPalette(int[] palette) {
		this.palette = palette;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Mandelbrot");
		Mandelbrot fractal = new Mandelbrot(500, 1000);

		frame.getContentPane().add(fractal.getDisplay());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
