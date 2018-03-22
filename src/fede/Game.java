package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import fede.entity.Dinosaur;
import fede.entity.cactus.Cactus;
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
	private LevelListener levelListener = new LevelListener(this);
	private GameOverListener gameOverListener = new GameOverListener(this);

	// Sprites	
	private Dinosaur dino;
	private List<Cactus> cactuses, passedCactuses;
	private boolean gameOver = false;
	private BufferedImage gameOverImage;
	public Window window;
	public static final int y_floor = 190;
	private int score;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
		this.setBackground(backgroundColor);
		this.setForeground(foregroundColor);
		this.setFocusable(true);
		this.requestFocus();
		
		try {
			gameOverImage = ImageIO.read(new File("res/game-over.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		score = 0;
		dino = new Dinosaur();
		cactuses = new ArrayList<Cactus>();
		passedCactuses = new ArrayList<Cactus>();
		renderer = this::renderLevel;
		updater = this::updateLevel;
	}

	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
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
		passedCactuses.forEach(c -> c.render());
		drawFloor();
		drawScore();
		bufferStrategy.show();
	}
	
	private void renderGameOverScreen() {
		g.drawImage(gameOverImage, 200, 80, null);
		bufferStrategy.show();
	}
	
	private void drawFloor() {
		g.drawLine(0, y_floor, this.getWidth(), y_floor);
	}
	
	private void drawScore() {
		window.appendText("score: " + score);
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
		
		if(Engine.count % 50 == 0) {
			cactuses.add(generateCactus());
		}

		dino.update();
		cactuses.forEach(Cactus::update);
		passedCactuses.forEach(Cactus::update);
		filter(passedCactuses, this::cactusIsInsideBounds);
		if(any(cactuses, cactus -> cactus.collidesWith(dino))) {
			dino.die();
			gameOver = true;
			return;
		}
		cactuses.forEach(cactus -> {
			if(cactus.getDefaultHitBox().x0 + cactus.getDefaultHitBox().delta_x < dino.getDefaultHitBox().x0) /*If dino passed the cactus*/{
				score++;
				passedCactuses.add(cactus);
			};
		});
	//	filter(cactuses, cactus -> !(passedCactuses.contains(cactus)));
		passedCactuses.forEach(passedCactus -> {
			if(cactuses.contains(passedCactus)) cactuses.remove(passedCactus);
		});
	}
	
	private Cactus generateCactus() {
		return new Cactus(g);
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
		passedCactuses.clear();
		Engine.count = -1;
		score = 0;
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