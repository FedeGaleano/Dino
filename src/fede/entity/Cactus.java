package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fede.Game;

public class Cactus extends Entity {
	
	public static final byte CACTUS_1 = 1, CACTUS_2 = 2, CACTUS_3 = 3, CACTUS_4 = 4, CACTUS_5 = 5, CACTUS_6 = 6;
	private int selectedCactus;
	
	private List<HitBox> hitBoxes;
	
	private static final int y0 = Game.y_floor + 10, x0 = 650;
	
	public Cactus(int selectedCactus) {
		this.selectedCactus = selectedCactus;
		
		try {
			image = ImageIO.read(new File("res/cactus" + selectedCactus + ".png"));
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
		x -= 5;
	}
	
	@Override
	public void render() {
		g.drawImage(image, x, y - image.getHeight(null), null);
	//	this.renderHitBoxes();
	}
	
	@Override	
	public List<HitBox> getHitBoxes() {
		hitBoxes.clear();
		
		switch(selectedCactus) {
			case CACTUS_1: {
				int yBase = 36;
				HitBox leaves = new HitBox(x, y - yBase, 70, 19), trunk = new HitBox(x + 6, y - image.getHeight(null), 58, image.getHeight(null));
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_2: {
				int yBase = 25;
				HitBox leaves = new HitBox(x, y - yBase, 49, 12), trunk = new HitBox(x + 5, y - image.getHeight(null), 38, image.getHeight(null));
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_3: {
				int yBase = 25;
				HitBox leaves = new HitBox(x, y - yBase, 15, 12), trunk = new HitBox(x + 5, y - image.getHeight(null), 5, image.getHeight(null));
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
				HitBox leaves = new HitBox(x, y - yBase, 32, 12), trunk = new HitBox(x + 5, y - image.getHeight(null), 23, image.getHeight(null));
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_6: {
				HitBox trunk = new HitBox(x + 7, y - 45, 28, 45), leaves = new HitBox(x, y + 10 - 45, 42, 17);
				hitBoxes.add(trunk);
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
		return new HitBox(x, y - image.getHeight(null), image.getWidth(null), image.getHeight(null));
	}
}
