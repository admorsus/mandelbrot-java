package mandelbrot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class FractalChooser {

	JComboBox<String> box;

	public FractalChooser(Mandelbrot display) {

		Field[] fields = MandelbrotFractals.class.getFields();
		String[] names = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			if (Modifier.isStatic(fields[i].getModifiers())) {
				Fractal fractal;
				try {
					fractal = (Fractal) fields[i].get(Fractal.class);
					names[i] = fractal.name;
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}

		box = new JComboBox<String>(names);

		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = (String) box.getItemAt(box.getSelectedIndex());
				for (Field field : fields) {
					try {
						Fractal iter = (Fractal) field.get(MandelbrotFractals.class);
						if (selected.equals(iter.name)) {
							display.fractal = iter;
							display.setDefaultCoords();
							display.draw();
						}
					} catch (IllegalArgumentException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		FractalChooser l = new FractalChooser(new Mandelbrot(0, 0));
		f.getContentPane().add(l.box);
		f.pack();
		f.setVisible(true);

	}
}
