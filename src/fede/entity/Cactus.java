package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Cactus extends Entity {
	
	private static final int velocity = 5;
	public static final byte CACTUS_1 = 1, CACTUS_2 = 2, CACTUS_3 = 3, CACTUS_4 = 4, CACTUS_5 = 5, CACTUS_6 = 6;
	private int selectedCactus;
	
	private List<HitBox> hitBoxes;
	
	private static final int y0 = Floor.Y + 10, x0 = 650;
	
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
		x -= velocity;
	}
	
	@Override
	public void renderOn(int destinationBuffer[]) {
		g.drawImage(image, x, y - image.getHeight(null), null);
	//	this.renderHitBoxes();
	}
	
	@Override	
	public List<HitBox> getHitBoxes() {
		hitBoxes.clear();
		
		switch(selectedCactus) {
			case CACTUS_1: {
				int yBase = 32;
				HitBox leaves = new HitBox(x + 2, y - yBase, 67, 13), trunk = new HitBox(x + 6, y - (image.getHeight(null) - 2), 58, image.getHeight(null) - 2);
				hitBoxes.add(leaves);
				hitBoxes.add(trunk);
			}
				break;
			case CACTUS_2: {
				int yBase = 25;
				HitBox leaves = new HitBox(x, y - yBase, 47, 12), trunk = new HitBox(x + 6, y - image.getHeight(null), 38, image.getHeight(null));
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
		return new HitBox(x, y - image.getHeight(null), image.getWidth(null), image.getHeight(null));
	}
}
