package animator;
import java.awt.Color;

import acm.graphics.*;
import acm.util.RandomGenerator;

public class AnimatorYe implements Animatable{
	private static final double INITIAL_VELOCITY = 2;
	private static final int oval_RADIUS = 4;
	
	private static final RandomGenerator rg = RandomGenerator.getInstance();
	
	private double x;
	private double y;
	private GCanvas canvas;
	
	private double vx;
	private double vy;
	private GOval oval;
	
	public static AnimatorYe getInstance(double x, double y, GCanvas canvas) {
		return new AnimatorYe(x, y, canvas);
	}
	
	public AnimatorYe(double x, double y, GCanvas canvas) {
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		
		double angle = rg.nextDouble(0, 2 * Math.PI);
		
		this.vx = INITIAL_VELOCITY * INITIAL_VELOCITY * Math.cos(angle);
		this.vy = INITIAL_VELOCITY * INITIAL_VELOCITY * Math.sin(angle);
		this.oval = new GOval(x, y, 4 * rg.nextInt(5) * oval_RADIUS, 2 * rg.nextInt(5) * oval_RADIUS);

		this.oval.setFilled(true);
		Color color = rg.nextColor();
		this.oval.setColor(color);
		canvas.add(oval);
		
	}
	
	public static String getName() {
		return "AnimatorYe";
	}
	
	public void heartbeat() {
		this.oval.move(this.vx, this.vy);
		if (atTop() || atBottom()) {
			this.vy = -this.vy;
		}
		if (atLeft() || atRight()) {
			this.vx = -this.vx;
		}
	}
	
	private boolean atTop() {
		return this.oval.getY() <= 0;
	}

	private boolean atBottom() {
		return this.oval.getBottomY() >= this.canvas.getHeight();
	}
	
	private boolean atLeft() {
		return this.oval.getX() <= 0;
	}
	
	private boolean atRight() {
		return this.oval.getRightX() >= this.canvas.getWidth();
	}
}
