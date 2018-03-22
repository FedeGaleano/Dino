package fede.entity.cactus;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fede.Game;
import fede.entity.Entity;
import fede.entity.HitBox;

public class Cactus extends Entity {
	
	private List<HitBox> hitBoxes;
	
	private static final int y0 = Game.y_floor + 10, x0 = 650;
	
	public Cactus() {
		try {
			image = ImageIO.read(new File("res/cactus4.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hitBoxes = new ArrayList<HitBox>();

		x = x0;
		y = y0;
	}
	
	public Cactus(Graphics g) {
		this();
		this.setGraphics(g);
	}
	
	@Override
	public void update() {
		x -= 5;
	}
	
	@Override
	public void render() {
		g.drawImage(image, x, y - image.getHeight(null), null);
	}
	
	@Override
	public List<HitBox> getHitBoxes() {
		hitBoxes.clear();
		HitBox trunk = new HitBox(x + 7, y, 5, 45), leaves = new HitBox(x, y + 10, 20, 17);
		hitBoxes.add(trunk);
		hitBoxes.add(leaves);
		return hitBoxes;
	}
}
