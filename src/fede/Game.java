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
import fede.utils.Random;

@SuppressWarnings("serial")
public class Game extends Canvas {

	public static final int GAME_HEIGHT = 200;
	public static final int GAME_WIDTH = 600;
	// Colors
	public static final Color foregroundColor = Color.decode("#535353");
	public static final Color backgroundColor = Color.decode("#F7F7F7");
	public static final Color canvas_Color = Color.DARK_GRAY;

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
	private float distanceToLastCactus = 0, separationBetweenLastAndNextCactus = 200;
	private float distanceToLastCloud = 0, separationBetweenLastAndNextCloud = 10;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(canvas_Color);
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
		clouds.add(new Cloud(100, 100));
		clouds.add(new Cloud(150, 10));
		clouds.add(new Cloud(350, 60));
		clouds.add(new Cloud(550, 20));
		renderer = this::renderLevel;
		updater = this::updateLevel;
	}

	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
		levelListener.start();
		xOffset = (this.getWidth() - GAME_WIDTH) / 2;
		yOffset = (this.getHeight() - GAME_HEIGHT) / 2;
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
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
		clouds.forEach(c -> c.renderOn(pixels));
		cactuses.forEach(c -> c.renderOn(pixels));
		passedCactuses.forEach(c -> c.renderOn(pixels));
		dino.renderOn(pixels);
	//	renderScore();
		floor.renderOn(pixels);
		g.drawImage(image, xOffset, yOffset, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
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
			clouds.add(new Cloud(700, random.between(0, 100)));
			separationBetweenLastAndNextCloud = random.between(87, 262);
			distanceToLastCloud = 0;
		}
		distanceToLastCloud += Cloud.velocity;
		
		if(distanceToLastCactus >= separationBetweenLastAndNextCactus) {
			cactuses.add(randomCactus());
			separationBetweenLastAndNextCactus = random.between(200, 600);
			distanceToLastCactus = 0;
		}
		distanceToLastCactus += Cactus.velocity;

		dino.update();
		floor.update();
		clouds.forEach(Cloud::update);
		cactuses.forEach(Cactus::update);
		passedCactuses.forEach(Cactus::update);
		
		if(passedCactuses.size() > 0) {
			Cactus frontPassedCactus = passedCactuses.get(0);
			if(!cactusIsInsideBounds(frontPassedCactus)) passedCactuses.remove(frontPassedCactus);
		}
		
		if(clouds.size() > 0) {
			Cloud frontCloud = clouds.get(0);
			if(!cloudIsInsideBounds(frontCloud)) clouds.remove(frontCloud);
		}

		if(cactuses.size() > 0) {
			Cactus frontCactus = cactuses.get(0);
			if(frontCactus.collidesWith(dino)) {
				dino.die();
				gameOver = true;
				return;
			}
			
			if(frontCactus.getDefaultHitBox().x0 + frontCactus.getDefaultHitBox().delta_x < dino.getDefaultHitBox().x0) /*If dino passed the cactus*/{
				score++;
				passedCactuses.add(frontCactus);
				cactuses.remove(frontCactus);
			};
		}
	}
	
	private Cactus randomCactus() {
		return new Cactus(random.between(Cactus.CACTUS_1, Cactus.CACTUS_6));
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
		floor.setInitialState();
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