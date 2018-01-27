package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import fede.entity.Cactus;
import fede.entity.Dinosaur;

import static fede.utils.FedeCollections.filter;
import static fede.utils.FedeCollections.any;
import static javax.swing.SwingUtilities.invokeLater;

@SuppressWarnings("serial")
public class Game extends Canvas {

	// Colors
	private static final Color foregroundColor = Color.decode("#535353");
	private static final Color backgroundColor = Color.decode("#F7F7F7");

	// Graphic tools
	private Graphics g;
	private BufferStrategy bufferStrategy;
	private Behaviour renderer, updater;

	// Sprites	
	private Dinosaur dino;
	private List<Cactus> cactuses;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
		this.setBackground(backgroundColor);
		this.setForeground(foregroundColor);
		dino = new Dinosaur();
		cactuses = new ArrayList<Cactus>();
		renderer = this::renderLevel;
		updater = this::updateLevel;
	}

	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
		g.setFont(new Font("a font", Font.BOLD, 24));
		dino.setGraphics(g);
	}

	public void render() {
		renderer.behave();
	}

	private void renderLevel() {
		this.clearScreen();
		
		dino.render();
		cactuses.forEach(c -> c.render());
		drawFloor();

		bufferStrategy.show();
	}
	
	private void drawFloor() {
		g.drawLine(0, 180, this.getWidth(), 180);
	}

	private void clearScreen() {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	public void update() {
		updater.behave();
	}
	
	private void updateLevel() {
		if(Engine.count % 50 == 0) {
			cactuses.add(new Cactus(g));
		}

		dino.update();
		cactuses.forEach(c -> c.update());
		filter(cactuses, this::cactusIsInsideBounds);
		if(any(cactuses, c -> c.getHitBox().collidesWith(dino.getHitBox()))) {
			this.switchToGameOverScreen();
		}
	}

	private boolean cactusIsInsideBounds(Cactus cactus) {
		return cactus.getCoordinates().x > -100;
	}
	
	public void over() {
		g.dispose();
	}
	
	public void makeDinosaurJump() {
		dino.jump();
	}
	
	private void switchToGameOverScreen() {
		renderer = this::renderGameOverScreen;
		updater = this::updateGameOverScreen;
	}
	
	private void renderGameOverScreen() {
		this.clearScreen();
		g.drawString("GAME OVER", 200, 100);
		bufferStrategy.show();
	}
	
	private void updateGameOverScreen() {
		
	}
}

interface Behaviour {
	public abstract void behave();
}