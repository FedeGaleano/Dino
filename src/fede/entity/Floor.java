package fede.entity;

import fede.Game;

public class Floor extends Entity {
	
	public static int Y = 190;
	
	public Floor() {
		y = Y;
	}
	
	@Override
	public void render() {
		g.drawLine(0, y, Game.CANVAS_WIDTH, y);
	}

	@Override
	public void update() {
		
	}
	
}
