package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Cactus extends Entity {
	private List<HitBox> hitBoxes;
	
	public Cactus() {
		try {
			image = ImageIO.read(new File("res/cactus4.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hitBoxes = new ArrayList<HitBox>();

		x = 650;
		y = 145;
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
		g.drawImage(image, x, y, null);
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
