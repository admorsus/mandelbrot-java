package mandelbrot;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class Mandelbrot {

	int width;
	int height;
	int maxd;
	
	int[] palette = generatePalette();

	double xoffset = 0;
	double yoffset = 0;
	double zoom = 1;

	JLabel display = new JLabel();

	Fractal fractal = Fractal.mandelbrot;

	public Mandelbrot(int width, int heigth) {
		this.width = width;
		this.height = heigth;
		this.maxd = Math.max(width, heigth);
		setDefaultCoords();
		addActions();
		draw();
	}
	
	public void setDefaultCoords() {
		this.xoffset = fractal.x;
		this.yoffset = fractal.y;
		this.zoom = fractal.zoom;
	}

	public void setCoords(double x, double y, double zoom) {
		this.xoffset = x;
		this.yoffset = y;
		this.zoom = zoom;
	}

	public static double map(double n, double inmin, double inmax, double outmin, double outmax) {
		return (n - inmin) * (outmax - outmin) / (inmax - inmin) + outmin;
	}

	public static int[] generatePalette() {
		int[] palette = new int[256];
		int roffset = 24;
		int goffset = 0;
		int boffset = 16;
		for (int i = 0; i < 256; i++) {
			palette[i] = (255 << 24) | (roffset << 16) | (goffset << 8) | boffset;
			if (i < 64) {
				roffset += 3;
			} else if (i < 128) {
				goffset += 3;
			} else if (i < 192) {
				boffset += 3;
			}
		}

		return palette;
	}

	public void addActions() {

		display.addMouseListener(new MouseAdapter() {

			// Implements mouse click zoom
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

		display.addMouseWheelListener(new MouseAdapter() {

			// Implements mouse wheel zoom
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

		display.addMouseListener(new MouseAdapter() {
			int ix, iy;

			public void mousePressed(MouseEvent e) {
				ix = e.getX();
				iy = e.getY();
			}

			// Implements mouse drag moving
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

	public void draw() {
		Image img = mandelbrot();
		display.setIcon(new ImageIcon(img));
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
					color = palette[255];
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

	public static void main(String[] args) {
		JFrame frame = new JFrame("Mandelbrot");
		Mandelbrot fractal = new Mandelbrot(500, 1000);

		frame.getContentPane().add(fractal.getDisplay());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
