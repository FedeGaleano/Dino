package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fede.Game;

public class Cloud extends Entity {

	private float virtual_x;
	public static float velocity = 0.25f;
	//x = 700
	public Cloud(int x, int y) {
		this.x = x;
		this.y = y;
		
		virtual_x = x;
		
		try {
			image = ImageIO.read(new File("res/cloud.png"));
			width = image.getWidth();
			height = image.getHeight();
			pixels = image.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Cloud(Graphics g, int x, int y) {
		this(x, y);
		this.setGraphics(g);
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
				destinationBuffer[pointInBuffer] = pixels[i];
		}
	}

	@Override
	public void update() {
		virtual_x -= velocity;
		x = (int)virtual_x;
	}

}
