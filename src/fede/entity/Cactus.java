package fede.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fede.Game;

public class Cactus extends Entity {
	
	public static final int velocity = 5;//original: 5
	public static final byte CACTUS_1 = 1, CACTUS_2 = 2, CACTUS_3 = 3, CACTUS_4 = 4, CACTUS_5 = 5, CACTUS_6 = 6;
	private int selectedCactus;
	private int width;
	private int height;
	
	private List<HitBox> hitBoxes;
	
	private static final int y0 = Floor.Y + 10, x0 = 650;
	
	public Cactus(int selectedCactus) {
		this.selectedCactus = selectedCactus;
		
		try {
			image = ImageIO.read(new File("res/cactus" + selectedCactus + ".png"));
			width = image.getWidth();
			height = image.getHeight();
			this.pixels = image.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hitBoxes = new ArrayList<HitBox>();

		x = x0;
		y = y0;
	}
	
	public Cactus(Graphics g, int selectedCactus) {
		this(selectedCactus);
		this.setGraphics(g);
	}
	
	@Override
	public void update() {
		x -= velocity;
	}
	
	@Override
	public void renderOn(int destinationBuffer[]) {
		if(x + width < 0 || x >= Game.GAME_WIDTH) return;
		int initialPoint = (y - height) * Game.GAME_WIDTH + x;
		
		for (int i = 0; i < pixels.length; i++) {
			int delta_x = i % width;
			int delta_y = i / width;
			int pointInBuffer = initialPoint + delta_y * Game.GAME_WIDTH + delta_x;
			if(x + delta_x >= 0 && x + delta_x < Game.GAME_WIDTH && pointInBuffer < destinationBuffer.length && pixels[i] != 0)
				destinationBuffer[pointInBuffer] = pixels[i];
		}
		
	//	this.renderHitBoxes();
	}
	
	@Override	
	public List<HitBox> getHitBoxes() {
		hitBoxes.clear();
		
		switch(selectedCactus) {
			case CACTUS_1: {
				int yBase = 32;
				HitBox leaves = new HitBox(x + 2, y - yBase, 67, 13), trunk = new HitBox(x + 6, y - (height - 2), 58, height - 2);
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_2: {
				int yBase = 25;
				HitBox leaves = new HitBox(x, y - yBase, 47, 12), trunk = new HitBox(x + 6, y - height, 38, height);
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_3: {
				int yBase = 25;
				HitBox leaves = new HitBox(x, y - yBase, 15, 12), trunk = new HitBox(x + 5, y - height, 5, height);
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_4: {
				HitBox trunk = new HitBox(x + 7, y - 45, 5, 45), leaves = new HitBox(x, y + 10 - 45, 20, 17);
				hitBoxes.add(trunk);
				hitBoxes.add(leaves);
			}
				break;
			case CACTUS_5: {
				int yBase = 25;
				HitBox leaves = new HitBox(x, y - yBase, 32, 12), trunk = new HitBox(x + 5, y - height, 23, height);
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_6: {
				int height = 45;
				HitBox trunk1 = new HitBox(x + 7, y - height, 5, height), trunk2 = new HitBox(x + 29, y - height, 5, height), leaves = new HitBox(x, y + 12 - height, 42, 15);
				hitBoxes.add(trunk1);
				hitBoxes.add(trunk2);
				hitBoxes.add(leaves);
			}
				break;
			default:
				throw new RuntimeException("Not a valid Cactus");
		}
		return hitBoxes;
	}
	
	@Override
	public HitBox getDefaultHitBox() {
		return new HitBox(x, y - height, width, height);
	}
}
