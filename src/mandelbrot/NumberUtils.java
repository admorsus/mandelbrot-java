package mandelbrot;

public class NumberUtils {
	
	public static String formatDouble (double x, double z) {
		Integer nzdigits = 2 + new String(Integer.toString((int) z)).length();
		String formatter = "%." + nzdigits.toString() + "g%n";
		return String.format(formatter, x);
	}
}
