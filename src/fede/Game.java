package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

@SuppressWarnings("serial")
public class Game extends Canvas {

	// Colors
	private static final Color foregroundColor = Color.decode("#535353");
	private static final Color backgroundColor = Color.decode("#F7F7F7");

	// Graphic tools
	private Graphics g;
	private BufferStrategy bufferStrategy;

	// Sprites	
	private Dinosaur dino;
	private Cactus cactus;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
		this.setBackground(backgroundColor);
		this.setForeground(foregroundColor);
		dino = new Dinosaur();
		cactus = new Cactus();
	}

	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
		dino.setGraphics(g);
		cactus.setGraphics(g);
	}

	public void render() {
		this.clearScreen();
		
		dino.render();
		cactus.render();
		drawFloor();

		bufferStrategy.show();
	}

	private void drawFloor() {
		g.drawLine(0, 180, 600, 180);
	}

	private void clearScreen() {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	public void update() {
		dino.update();
		cactus.update();
	}

	public void over() {
		g.dispose();
	}
	
	public void makeDinosaurJump() {
		dino.jump();
	}
}
