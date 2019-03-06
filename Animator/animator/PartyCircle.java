package animator;

import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.*;
import stanford.cs106.util.RandomGenerator;

public class PartyCircle implements Animatable {
	private static final int SQUARE_SIZE = 10;
	private static final int CIRCLE_SIZE = 100;
	private static final int N_RECTS = 20;
	
	private static final double BASE_ROTATION_SPEED = 2 * Math.PI / 10;
	private static final RandomGenerator rgen = RandomGenerator.getInstance();
	
	private double currOffset;
	private double centerX;
	private double centerY;
	private ArrayList<GRect> allRects;
	private GCanvas canvas;
	private double rotationSpeed;
	
	
	public PartyCircle(double x, double y, GCanvas canvas) {
		this.centerX = x;
		this.centerY = y;
		this.currOffset = 0;
		this.allRects = new ArrayList<GRect>(); 
		this.canvas = canvas;
		
		for (int i = 0; i < N_RECTS; i++) {
			GRect r = new GRect(SQUARE_SIZE, SQUARE_SIZE);
			r.setFilled(true);
			r.setColor(Color.RED);
			this.allRects.add(r);
			this.canvas.add(r);
		}
		
		this.rotationSpeed = BASE_ROTATION_SPEED;
		if (rgen.nextBoolean()) {
			this.rotationSpeed *= -1;
		}
	}
	
	public static PartyCircle getInstance(double x, double y, GCanvas canvas) {
		return new PartyCircle(x, y, canvas);
	}
	
	public static String getName() {
		return "Party Circle";
	}
	
	public void heartbeat() {
		this.currOffset += this.rotationSpeed;
		drawRects();
	}
	
	private void drawRects() {
		double currAngle = this.currOffset;
		for (int i = 0; i < this.allRects.size(); i++) {
			GRect curr = allRects.get(i);
			curr.setLocation(this.centerX - SQUARE_SIZE / 2, this.centerY - SQUARE_SIZE / 2);
			curr.movePolar(CIRCLE_SIZE, currAngle);
			currAngle += 360.0 / N_RECTS;
		}
	}
}
