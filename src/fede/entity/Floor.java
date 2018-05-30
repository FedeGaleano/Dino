package fede.entity;

import fede.Game;

public class Floor extends Entity {
	
	public static int Y = 190;
	private static int securityWIDTH = 100;
	
	public Floor() {
		y = Y;
		x = 0;
	}
	
	@Override
	public void renderOn(int pixels[]) {
		g.drawLine(0, y, Game.CANVAS_WIDTH + securityWIDTH, y);
		for(int i = x % Game.CANVAS_WIDTH; i < Game.CANVAS_WIDTH; i += 40) {
			renderLittleLine(i, y);
			renderLittleLine(i + 20, y + 5);
		}
	}

	private void renderLittleLine(int x, int y) {
		g.drawLine(x, y + 5, x + 3, y + 5);
	}
	
	@Override
	public void update() {
		x -= 5;
	}
	
}
