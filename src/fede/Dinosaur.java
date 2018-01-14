package fede;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Dinosaur {
	
	private Graphics g;
	private Image image;
	
	public Dinosaur() {
		try
		{
			image = ImageIO
					.read(new File("res/Dinosaur.png"))
					.getSubimage(0, 0, 40, 42);
		}
		catch(IOException e)
		{
			System.out.println("Couldn't open dinosaur spritesheet file");
		}
	}
	
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	public void render() {
		g.drawImage(image, 100, 100, null);
	}
}
