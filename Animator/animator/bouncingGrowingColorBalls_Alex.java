package animator;

import java.awt.Color;

import acm.graphics.GCanvas;
import acm.graphics.GOval;
import acm.util.RandomGenerator;

/***Class to create an instance of colorful and growing balls
 ***Ball changes color each time and grows and shrinks as it moves****/

public class bouncingGrowingColorBalls_Alex implements Animatable {

	private static final double INITIAL_VELOCITY = 2;
	private static final int BALL_RADIUS = 5;

	private static final RandomGenerator rgen = RandomGenerator.getInstance();

	private double x;
	private double y;
	private GCanvas canvas;

	private double vx;
	private double vy;
	private GOval ball;
	
	private Color ballColor;
	private double sizeMultiplier;

	public bouncingGrowingColorBalls_Alex(double x, double y, GCanvas canvas) {
		this.x = x;
		this.y = y;
		this.canvas = canvas;

		double angle = rgen.nextDouble(0, 2 * Math.PI);

		this.vx = INITIAL_VELOCITY * INITIAL_VELOCITY * Math.cos(angle);
		this.vy = INITIAL_VELOCITY * INITIAL_VELOCITY * Math.sin(angle);
		this.ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		this.ball.setFilled(true);
		this.ballColor = rgen.nextColor();
		this.ball.setColor(ballColor);
		canvas.add(ball);
	}

	public static bouncingGrowingColorBalls_Alex getInstance(double x, double y, GCanvas canvas) {
		return new bouncingGrowingColorBalls_Alex(x,y,canvas);
	}

	public static String getName() {
		return "Random color bouncing ball with changing size";
	}

	public void heartbeat() {
		this.ball.move(this.vx, this.vy);
		if (atTop() || atBottom()) {
			this.vy = -this.vy;
		}
		if (atLeft() || atRight()) {
			this.vx = -this.vx;
		}
		this.ballColor = rgen.nextColor();
		this.ball.setColor(ballColor);
		this.sizeMultiplier = rgen.nextDouble(0.3,2);
		this.ball.setSize(2 * BALL_RADIUS * sizeMultiplier, 2 * BALL_RADIUS * sizeMultiplier);
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
