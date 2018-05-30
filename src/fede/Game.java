package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fede.entity.Cactus;
import fede.entity.Cloud;
import fede.entity.Dinosaur;
import fede.entity.Floor;
import fede.listener.GameOverListener;
import fede.listener.LevelListener;

import static fede.utils.FedeCollections.filter;
import static fede.utils.FedeCollections.any;

@SuppressWarnings("serial")
public class Game extends Canvas {

	public static final int GAME_HEIGHT = 200;
	public static final int GAME_WIDTH = 600;
	// Colors
	private static final Color foregroundColor = Color.decode("#535353");
	private static final Color backgroundColor = Color.decode("#F7F7F7");

	// Graphic tools
	private Graphics g;
	private BufferStrategy bufferStrategy;
	private BufferedImage image = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)(image.getRaster().getDataBuffer())).getData();
	public static int xOffset;
	public static int yOffset;
	private Behaviour renderer, updater;
	private LevelListener levelListener = new LevelListener(this);
	private GameOverListener gameOverListener = new GameOverListener(this);
	private Random random = new Random();

	// Sprites	
	private Dinosaur dino;
	private Floor floor = new Floor();
	private List<Cloud> clouds;
	private List<Cactus> cactuses, passedCactuses;
	private boolean gameOver = false;
	private BufferedImage gameOverImage;
	public Window window;
	private int score;
	private int distanceToLastCactus = 0, separationBetweenLastAndNextCactus = 70;
	private int distanceToLastCloud = 0, separationBetweenLastAndNextCloud = 350;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
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
		clouds = new ArrayList<Cloud>();
		renderer = this::renderLevel;
		updater = this::updateLevel;
	}

	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
		levelListener.start();
		dino.setGraphics(g);
		floor.setGraphics(g);
		xOffset = (this.getWidth() - GAME_WIDTH) / 2;
		yOffset = (this.getHeight() - GAME_HEIGHT) / 2;
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
		dino.renderOn(pixels);
		g.drawImage(image, xOffset, yOffset, null);
		cactuses.forEach(c -> c.renderOn(pixels));
		passedCactuses.forEach(c -> c.renderOn(pixels));
		floor.renderOn(pixels);
		clouds.forEach(c -> c.renderOn(pixels));
		renderScore();
		bufferStrategy.show();
	}
	
	private void renderGameOverScreen() {
		g.drawImage(gameOverImage, 200, 80, null);
		bufferStrategy.show();
	}
	
	private void renderScore() {
		window.appendText("score: " + score);
	}

	private void clearScreen() {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = backgroundColor.getRGB();
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
		
		if(distanceToLastCloud == separationBetweenLastAndNextCloud) {
			clouds.add(new Cloud(g, 700, random.between(0, 100)));
			separationBetweenLastAndNextCloud = random.between(350, 1050);
			distanceToLastCloud = 0;
		}
		++distanceToLastCloud;
		
		if(distanceToLastCactus == separationBetweenLastAndNextCactus) {
			cactuses.add(randomCactus());
			separationBetweenLastAndNextCactus = random.between(40, 120);
			distanceToLastCactus = 0;
		}
		++distanceToLastCactus;

		dino.update();
		floor.update();
		clouds.forEach(Cloud::update);
		cactuses.forEach(Cactus::update);
		passedCactuses.forEach(Cactus::update);
		filter(passedCactuses, this::cactusIsInsideBounds);
		filter(clouds, this::cloudIsInsideBounds);
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
	
	private Cactus randomCactus() {
		return new Cactus(g, random.between(1, 6));
	}
	
	private void updateGameOverScreen() {
		
	}

	// Utils
	private boolean cactusIsInsideBounds(Cactus cactus) {
		return cactus.getCoordinates().x > -100;
	}
	
	private boolean cloudIsInsideBounds(Cloud cloud) {
		return cloud.getCoordinates().x > -100;
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
		distanceToLastCactus = 0;
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

class Random {
	public int between(int min, int max) {
		return (int) Math.round(Math.random() * (max - min) + min);
	}
}