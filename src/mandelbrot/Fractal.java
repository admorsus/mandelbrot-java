package mandelbrot;

public interface Fractal {

	public double za(double a, double b);

	public double zb(double a, double b);
	
	///// FRACTALS

	// Mandelbrot Z'=Z^2+C
	public static Fractal mandelbrot = new Fractal() {

		@Override
		public double za(double a, double b) {
			return a * a - b * b;
		}

		@Override
		public double zb(double a, double b) {
			return 2 * a * b;
		}
	};

	// Mandelbrot Z'=Z^3+C
	public static Fractal mandelbrot_3 = new Fractal() {

		@Override
		public double za(double a, double b) {
			return Math.pow(a, 3) - 3 * a * b * b;
		}

		@Override
		public double zb(double a, double b) {
			return 3 * a * a * b - Math.pow(b, 3);
		}
	};
}
