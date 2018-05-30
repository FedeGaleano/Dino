package fede.entity;

import fede.Game;

public class Floor extends Entity {
	
	public static int Y = 185;
	private static int securityWIDTH = 100;
	
	public Floor() {
		y = Y;
		x = 0;
	}
	
	@Override
	public void renderOn(int destinationBuffer[]) {
		g.drawLine(0, y, Game.GAME_WIDTH + securityWIDTH, y);
		for(int i = x % Game.GAME_WIDTH; i < Game.GAME_WIDTH; i += 40) {
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
