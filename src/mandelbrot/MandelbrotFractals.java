package mandelbrot;

public class MandelbrotFractals {
	////// FRACTAL DEFINITIONS /////

	// Mandelbrot set for Z'=Z^2+C
	public static final Fractal Mandelbrot = new Fractal("Mandelbrot_Z'=Z^2+C") {

		@Override
		public double zreal(double a, double b) {
			return a * a - b * b;
		}

		@Override
		public double zimag(double a, double b) {
			return 2 * a * b;
		}

		@Override
		public void setup() {
			x = -0.6;
			y = 0.25;
			zoom = 1.25;
		}
	};

	// Mandelbrot set for Z'=Z^3+C
	public static final Fractal Mandelbrot3 = new Fractal("Mandelbrot_Z'=Z^3+C") {

		@Override
		public double zreal(double a, double b) {
			return Math.pow(a, 3) - 3 * a * b * b;
		}

		@Override
		public double zimag(double a, double b) {
			return 3 * a * a * b - Math.pow(b, 3);
		}

		@Override
		public void setup() {
			x = 0;
			y = 0.25;
			zoom = 1.2;
		}
	};

	// Mandelbrot set for Z=Z^4+C
	public static final Fractal Mandelbrot4 = new Fractal("Mandelbrot_Z'=Z^4+C") {

		@Override
		public double zreal(double a, double b) {
			return Math.pow(a, 4) - 6 * a * a * b * b + Math.pow(b, 4);
		}

		@Override
		public double zimag(double a, double b) {
			return 4 * Math.pow(a, 3) * b - 4 * a * Math.pow(b, 3);
		}

		@Override
		public void setup() {
			x = -0.15;
			y = 0.20;
			zoom = 1.35;
		}
	};
}
