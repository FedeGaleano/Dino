package fede.entity;

import java.awt.Color;

import fede.Game;
import fede.utils.Bitmap;
import fede.utils.SpriteLoader;

public class Cloud extends Entity {

	private static final int color = Color.decode("#929090").getRGB();
	private float virtual_x;
	public static float velocity = 0.25f;
	//x = 700
	public Cloud(int x, int y) {
		this.x = x;
		this.y = y;
		
		virtual_x = x;
		
		Bitmap bitmap = SpriteLoader.cloud;
		
		width = bitmap.width;
		height = bitmap.height;
		pixels = bitmap.pixels;
	}
	
	@Override
	public void renderOn(int destinationBuffer[]) {
		if(x + width < 0 || x >= Game.GAME_WIDTH) return;
		int initialPoint = y * Game.GAME_WIDTH + x;
		
		for (int i = 0; i < pixels.length; i++) {
			int delta_x = i % width;
			int delta_y = i / width;
			int pointInBuffer = initialPoint + delta_y * Game.GAME_WIDTH + delta_x;
			if(x + delta_x >= 0 && x + delta_x < Game.GAME_WIDTH && pointInBuffer < destinationBuffer.length && pixels[i] != 0)
				destinationBuffer[pointInBuffer] = color;
		}
	}

	@Override
	public void update() {
		virtual_x -= velocity;
		x = (int)virtual_x;
	}

}
