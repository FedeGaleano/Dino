package fede;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Dinosaur {

	private Graphics g;
	private Image image;
	private Image[] sprite;
	private int index = 0;

	private static final int running_state_1 = 0, running_state_2 = 1, normal_state = 2, lost_state = 3;

	public Dinosaur() {
		sprite = new Image[4];

		try {
			for (int i = 0; i < sprite.length; i++)
				sprite[i] = take(i);

			index = running_state_1;
			image = sprite[index];
		}
		catch (IOException e) {
			System.out.println("Couldn't open dinosaur spritesheet file");
		}
	}

	private BufferedImage take(int nthImage) throws IOException {
		return ImageIO
				.read(new File("res/Dinosaur.png"))
				.getSubimage(nthImage * 40, 0, 40, 42);
	}

	public void setGraphics(Graphics g) {
		this.g = g;
	}

	public void render() {
		g.drawImage(image, 100, 100, null);
	}

	public void update() {
		index ^= 1;
		image = sprite[index];
	}
}
