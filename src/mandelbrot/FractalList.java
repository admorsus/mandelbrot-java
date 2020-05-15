package mandelbrot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class FractalList {
	
	private JComboBox<String> combo;
	
	public FractalList(Mandelbrot display) {
		//this.display = display;
		
		Field[] fields = Fractal.class.getFields();
		String[] names = new String[fields.length];
		
		for (int i = 0; i < fields.length; i++) {
			if (Modifier.isStatic(fields[i].getModifiers())) {
				Fractal fractal;
				try {
					fractal = (Fractal)fields[i].get(Fractal.class);
					names[i] = fractal.name;
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		combo = new JComboBox<String>(names);
		
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = (String) combo.getItemAt(combo.getSelectedIndex());
				for (Field field : fields) {
					try {
						Fractal iter = (Fractal) field.get(Fractal.class);
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
	
	public JComboBox<String> getCombo() {
		return combo;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		FractalList l = new FractalList(new Mandelbrot(0, 0));
		f.getContentPane().add(l.combo);
		f.pack();
		f.setVisible(true);
		
	}
}


