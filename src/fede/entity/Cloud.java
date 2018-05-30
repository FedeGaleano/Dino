package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cloud extends Entity {

	private float virtual_x;
	//x = 700
	public Cloud(int x, int y) {
		this.x = x;
		this.y = y;
		
		virtual_x = x;
		
		try {
			image = ImageIO.read(new File("res/cloud.png"));
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
	public void renderOn(int pixels[]) {
		g.drawImage(image, x, y, null);
	}

	@Override
	public void update() {
		virtual_x -= 0.25;
		x = (int)virtual_x;
	}

}
