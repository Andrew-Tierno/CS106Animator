package animator;

import java.awt.Color;

import acm.graphics.*;
import acm.util.RandomGenerator;

public class BouncingBall implements Animatable {
	
	private static final double INITIAL_VELOCITY = 2;
	private static final int BALL_RADIUS = 5;
	
	private static final RandomGenerator rgen = RandomGenerator.getInstance();
	
	private double x;
	private double y;
	private GCanvas canvas;
	
	private double vx;
	private double vy;
	private GOval ball;
	
	public BouncingBall(double x, double y, GCanvas canvas) {
		this.x = x;
		this.y = y;
		this.canvas = canvas;
		
		double angle = rgen.nextDouble(0, 2 * Math.PI);
		
		this.vx = INITIAL_VELOCITY * INITIAL_VELOCITY * Math.cos(angle);
		this.vy = INITIAL_VELOCITY * INITIAL_VELOCITY * Math.sin(angle);
		this.ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		this.ball.setFilled(true);
		this.ball.setColor(Color.BLUE);
		canvas.add(ball);
	}
	
	public static BouncingBall getInstance(double x, double y, GCanvas canvas) {
		return new BouncingBall(x, y, canvas);
	}
	public void heartbeat() {
		this.ball.move(this.vx, this.vy);
		if (atTop() || atBottom()) {
			this.vy = -this.vy;
		}
		if (atLeft() || atRight()) {
			this.vx = -this.vx;
		}
	}
	
	public static String getName() {
		return "Bouncing Ball";
	}
	
	private boolean atTop() {
		return this.ball.getY() <= 0;
	}

	private boolean atBottom() {
		return this.ball.getBottomY() >= this.canvas.getHeight();
	}
	
	private boolean atLeft() {
		return this.ball.getX() <= 0;
	}
	
	private boolean atRight() {
		return this.ball.getRightX() >= this.canvas.getWidth();
	}
}
