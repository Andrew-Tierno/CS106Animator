package animator;
import acm.program.*;
import acm.graphics.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.*;
import com.eztech.util.*;

public class Animator extends GraphicsProgram {
	private ArrayList<JRadioButton> animationButtons;
	private ArrayList<Animatable> animationsOnScreen;
	private ButtonGroup buttons;
	private HashMap<String, Class<? extends Animatable>> animatableMap;
	String selected;
	
	private static final int DELAY = 10;
	
	public void init() {
		//Instantiate instance variables
		animationButtons = new ArrayList<JRadioButton>();
		animationsOnScreen = new ArrayList<Animatable>();
		animatableMap = new HashMap<>();
		buttons = new ButtonGroup();
		
		//Load all animatables into the program
		JavaClassFinder classFinder = new JavaClassFinder();
		List<Class<? extends Animatable>> classes = classFinder.findAllMatchingTypes(Animatable.class);
		classes.remove(Animatable.class);
		for (Class<? extends Animatable> c : classes) {
			String name = getName(c);
			animatableMap.put(name, c);
			JRadioButton rb = new JRadioButton(name);
			rb.setActionCommand(name);
			buttons.add(rb);
			animationButtons.add(rb);
		}
		
		//Set first to be selected
		if (buttons.getButtonCount() > 0) {
			selected = animationButtons.get(0).getActionCommand();
			animationButtons.get(0).setSelected(true);
		}
		
		//Add buttons to the screen
		Iterator<AbstractButton> iter = buttons.getElements().asIterator();
		while(iter.hasNext()) {
			AbstractButton b = iter.next();
			add(b, WEST);
		}
		System.out.println("Selected is " + selected);
		
		addActionListeners();
	}
	
	public void run() {
		while (true) {
			for (int i = animationsOnScreen.size() - 1; i >= 0; i--) {
				animationsOnScreen.get(i).heartbeat();
			}
			pause(DELAY);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		selected = e.getActionCommand();
		System.out.println("Selected is now " + selected);
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (this.contains(x, y)) {
			Animatable a = createAnimatable(selected, x, y);
			animationsOnScreen.add(a);
		}
	}
	
	private String getName(Class<? extends Animatable> c) {
		try {
			return (String)c.getDeclaredMethod("getName").invoke(null);
		} catch (Exception e) {
			System.out.println("Error in getName!");
			e.printStackTrace();
			return null;
		}
	}
	
	private Animatable createAnimatable(String name, double x, double y) {
		
		try {
			return (Animatable) animatableMap.get(name)
								.getDeclaredMethod("getInstance", double.class, double.class, GCanvas.class)
								.invoke(null, x, y, this.getGCanvas());
		} catch (Exception e) {
			System.out.println("Error in createAnimatable!");
			e.printStackTrace();
			return null;
		}
	}
}
