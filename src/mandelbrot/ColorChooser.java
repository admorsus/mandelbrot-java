package mandelbrot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

public class ColorChooser {

	JComboBox box;

	public ColorChooser(Mandelbrot display) {

		Method[] methodArr = ColorPalettes.class.getDeclaredMethods();
		
		List<Method> paletteList = new ArrayList<Method>();
		List<String> nameList = new ArrayList<String>();

		for (int i = 0; i < methodArr.length; i++) {
			if (methodArr[i].getName().contains("Palette")) {
				paletteList.add(methodArr[i]);
				nameList.add(methodArr[i].getName().replaceAll("(?!^)([A-Z])", " $1"));
			}
		}

		box = new JComboBox(nameList.toArray());

		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Method m = paletteList.get(box.getSelectedIndex());
				try {
					int[] palette = (int[]) m.invoke(ColorPalettes.class);
					display.setPalette(palette);
					display.draw();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
