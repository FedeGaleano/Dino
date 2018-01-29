package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fede.entity.Cactus;
import fede.entity.Dinosaur;
import fede.listener.GameOverListener;
import fede.listener.LevelListener;

import static fede.utils.FedeCollections.filter;
import static fede.utils.FedeCollections.any;

@SuppressWarnings("serial")
public class Game extends Canvas {

	// Colors
	private static final Color foregroundColor = Color.decode("#535353");
	private static final Color backgroundColor = Color.decode("#F7F7F7");

	// Graphic tools
	private Graphics g;
	private BufferStrategy bufferStrategy;
	private Behaviour renderer, updater;
	private LevelListener levelListener;
	private GameOverListener gameOverListener;

	// Sprites	
	private Dinosaur dino;
	private List<Cactus> cactuses;
	private boolean gameOver = false;
	private BufferedImage gameOverImage;
	public static final int y_floor = 190;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
		this.setBackground(backgroundColor);
		this.setForeground(foregroundColor);
		
		try {
			gameOverImage = ImageIO.read(new File("res/game-over.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dino = new Dinosaur();
		cactuses = new ArrayList<Cactus>();
		renderer = this::renderLevel;
		updater = this::updateLevel;
	}

	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
		
		levelListener = new LevelListener(Engine.window, this);
		gameOverListener = new GameOverListener(Engine.window, this);
		
		levelListener.start();
		dino.setGraphics(g);
	}

	public void render() {
		renderer.behave();
	}

	public void update() {
		updater.behave();
	}
	
	// Renderers
	private void renderLevel() {
		this.clearScreen();
		dino.render();
		cactuses.forEach(c -> c.render());
		drawFloor();

		bufferStrategy.show();
	}
	
	private void renderGameOverScreen() {
		g.drawImage(gameOverImage, 200, 80, null);
		bufferStrategy.show();
	}
	
	private void drawFloor() {
		g.drawLine(0, y_floor, this.getWidth(), y_floor);
	}

	private void clearScreen() {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	//Updaters
	private void updateLevel() {
		if(gameOver) {
			updater = this::updateGameOverScreen;
			renderer = this::renderGameOverScreen;
			levelListener.stop();
			gameOverListener.start();
			return;
		}
		
		if(Engine.count % 200 == 0) {
			cactuses.add(new Cactus(g));
		}

		dino.update();
		cactuses.forEach(Cactus::update);
		filter(cactuses, this::cactusIsInsideBounds);
		if(any(cactuses, cactus -> cactus.collidesWith(dino))) {
			dino.die();
			gameOver = true;
		}
	}
	
	private void updateGameOverScreen() {
		
	}

	// Utils
	private boolean cactusIsInsideBounds(Cactus cactus) {
		return cactus.getCoordinates().x > -100;
	}
	
	public void free() {
		g.dispose();
	}
	
	public void makeDinosaurJump() {
		dino.jump();
	}
	
	public void restart() {
		dino.setInitialState();
		cactuses.clear();
		Engine.count = -1;
		updater = this::updateLevel;
		renderer = this::renderLevel;
		
		gameOver = false;
		
		gameOverListener.stop();
		levelListener.start();
	}
}

interface Behaviour {
	public abstract void behave();
}