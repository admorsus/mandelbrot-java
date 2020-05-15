package mandelbrot;

public abstract class Fractal {
	double x;
	double y;
	double zoom;
	String name;

	public abstract double zreal(double a, double b);

	public abstract double zimag(double a, double b);

	public abstract void setup();
	
	public Fractal() {
		setup();
	}
}
