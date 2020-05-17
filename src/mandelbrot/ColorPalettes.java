package mandelbrot;

public class ColorPalettes {

	public static int getColor(int r, int g, int b) {
		return (255 << 24) | (r << 16) | (g << 8) | b;
	}

	public static int[] RedPalette() {
		int[] palette = new int[256];
		int roffset = 24;
		int goffset = 0;
		int boffset = 16;
		for (int i = 0; i < 256; i++) {
			palette[i] = getColor(roffset, goffset, boffset);
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

	public static int[] BluePalette() {
		int[] palette = new int[256];
		int roffset = 16;
		int goffset = 0;
		int boffset = 24;
		for (int i = 0; i < 256; i++) {
			palette[i] = getColor(roffset, goffset, boffset);
			if (i < 64) {
				boffset += 3;
			} else if (i < 128) {
				roffset += 3;
			} else if (i < 192) {
				goffset += 3;
			}
		}
		return palette;
	}

	public static int[] GreenPalette() {
		int[] palette = new int[256];
		int roffset = 0;
		int goffset = 16;
		int boffset = 24;
		for (int i = 0; i < 256; i++) {
			palette[i] = getColor(roffset, goffset, boffset);
			if (i < 64) {
				goffset += 3;
			} else if (i < 128) {
				boffset += 3;
			} else if (i < 192) {
				roffset += 3;
			}
		}
		return palette;
	}

	public static int[] GreyPalette() {
		int[] palette = new int[256];
		int roffset = 0;
		int goffset = 16;
		int boffset = 24;
		for (int i = 0; i < 192; i++) {
			palette[i] = getColor(roffset++, goffset++, boffset++);
		}
		return palette;
	}

	public static int[] PurplePalette() {
		int[] palette = new int[256];
		int roffset = 16;
		int goffset = 0;
		int boffset = 24;
		for (int i = 0; i < 256; i++) {
			palette[i] = getColor(roffset, goffset, boffset);
			if (i < 64) {
				roffset += 1;
				boffset += 2.5;
			} else if (i < 128) {
				roffset += 2.5;
				goffset += 1;
			} else if (i < 192) {
				boffset += 1;
				goffset += 2.5;
			}
		}
		return palette;
	}

}
