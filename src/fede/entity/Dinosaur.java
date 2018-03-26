package fede.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fede.Engine;
import fede.Game;

import static java.lang.Math.pow;

public class Dinosaur extends Entity {
	private Image sprite[];
	private int imagePointer;
	private static final int running_state_1 = 0/*, running_state_2 = 1*/, stand_or_jump_state = 2, lost_state = 3;
	
	// Character
	private static final int y0 = Game.y_floor + 10, x0 = 100;
	private static final int v0 = 10;
	private static final double grav = .5;
	private int t;
	private Behaviour behaviour;
	private List<HitBox> hitBoxes; 
	
	public Dinosaur() {
		sprite = new Image[4];

		for (int i = 0; i < sprite.length; i++)
			sprite[i] = takeSubimage(i);
		
		hitBoxes = new ArrayList<HitBox>();
		
		setInitialState();
	}

	public void setInitialState() {
		x = x0;
		y = y0;
		t = 0;
		image = sprite[stand_or_jump_state];
		this.run();
	}
	
	@Override
	public void render() {
		g.drawImage(image, x, y - image.getHeight(null), null);
	//	this.renderHitBoxes();
	}

	@Override
	public void update() {
		behaviour.behave();
		image = sprite[imagePointer];
	}

	private BufferedImage takeSubimage(int nthSubimage) {
		BufferedImage subimage = null;
		try {
			subimage = ImageIO.read(new File("res/Dinosaur.png")).getSubimage(nthSubimage * 40, 0, 40, 42);
		}
		catch(IOException e) {
			System.err.println("Couldn't open dinosaur spritesheet file");
			System.exit(-1);
		}
		return subimage;
	}
	
	/* Events that change behaviour */
	public void run() {
		imagePointer = running_state_1;
		behaviour = this::behaveAsRunning;
	}
	
	public void jump() {
		imagePointer = stand_or_jump_state;
		behaviour = this::behaveAsJumping;
	}
	
	/* Possible Behaviours */	
	private void behaveAsRunning() {
		imagePointer = (Engine.count / 6) % 2;
	}
	
	private void behaveAsJumping() {
		y = (int)(y0 - v0 * t + grav * pow(t, 2) / 2);
		t++;
		if(y > y0) {
			t = 0;
			y = y0;
			this.run();
		}
	}

	public void die() {
		image = sprite[lost_state];
	}
	
	@Override
	public List<HitBox> getHitBoxes() {
		hitBoxes.clear();
		HitBox headHitBox = new HitBox(x + 21, y - 42, 19, 14), bodyHitBox = new HitBox(x , y + 14 - 42, 28, 12), feetHitBox = new HitBox(x + 10, y + 26 - 42, 12, 16);
		hitBoxes.add(headHitBox);
		hitBoxes.add(bodyHitBox);
		hitBoxes.add(feetHitBox);
		
		return hitBoxes;
	}
}

interface Behaviour {
	abstract void behave();
}
