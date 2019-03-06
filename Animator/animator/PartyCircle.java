package animator;

import java.util.ArrayList;

import acm.graphics.*;

public class PartyCircle implements Animatable {
	private static final int SQUARE_SIZE = 10;
	private static final int CIRCLE_SIZE = 200;
	private static final int N_RECTS = 20;
	
	private static final double ROTATION_SPEED = 2 * Math.PI / 100;
	
	private double currOffset;
	private double centerX;
	private double centerY;
	private ArrayList<GRect> allRects;
	
	
	public PartyCircle(double x, double y, GCanvas canvas) {
		this.centerX = x;
		this.centerY = y;
		this.currOffset = 0;
		this.allRects = new ArrayList<GRect>(); 
		
		for (int i = 0; i < N_RECTS; i++) {
			this.allRects.add(new GRect(SQUARE_SIZE, SQUARE_SIZE));
		}
	}
	
	public static PartyCircle getInstance(double x, double y, GCanvas canvas) {
		return new PartyCircle(x, y, canvas);
	}
	
	public static String getName() {
		return "Party Circle";
	}
	
	public void heartbeat() {
		this.currOffset += ROTATION_SPEED;
		drawRects();
	}
	
	private void drawRects() {
		double currAngle = this.currOffset;
		for (int i = 0; i < this.allRects.size(); i++) {
			GRect curr = allRects.get(i);
			curr.setLocation(this.centerX - SQUARE_SIZE / 2, this.centerY - SQUARE_SIZE / 2);
			curr.movePolar(CIRCLE_SIZE, currAngle);
			currAngle += 2 * Math.PI / N_RECTS;
		}
	}
}
