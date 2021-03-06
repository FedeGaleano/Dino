package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import fede.entity.Cactus;
import fede.entity.Cloud;
import fede.entity.Dinosaur;
import fede.entity.Ground;
import fede.listener.GameOverListener;
import fede.listener.LevelListener;
import fede.utils.Random;
import fede.utils.Score;
import fede.utils.SpriteLoader;

@SuppressWarnings("serial")
public class Game extends Canvas {

	public static final int GAME_HEIGHT = 200;
	public static final int GAME_WIDTH = 600;
	// Colors
	public static final int foregroundColor = 0x535353;
	public static final int backgroundColor = 0xF7F7F7;
	public static final int canvas_Color = Color.DARK_GRAY.getRGB();
	
	public static final float initialVelocity = 4.5f;//original: 5
	public static float distance = 0;

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
	private Ground ground = new Ground();
	private List<Cloud> clouds;
	private List<Cactus> cactuses, passedCactuses;
	private boolean gameOver = false;

	//Game over screen
	private int gameOverWidth = SpriteLoader.gameOver.width;
	private int gameOverHeight = SpriteLoader.gameOver.height;
	private int gameOverPixels[] = SpriteLoader.gameOver.pixels;
	
	public Window window;
	private int score;
	private float distanceToLastCactus = 0, separationBetweenLastAndNextCactus = 200;
	private float distanceToLastCloud = 0, separationBetweenLastAndNextCloud = 10;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(new Color(canvas_Color));
		this.setForeground(new Color(foregroundColor));
		this.setFocusable(true);
		this.requestFocus();
		
		score = 0;
		dino = new Dinosaur();
		cactuses = new ArrayList<Cactus>();
		passedCactuses = new ArrayList<Cactus>();
		clouds = new ArrayList<Cloud>();
		clouds.add(new Cloud(100, 100));
		clouds.add(new Cloud(150, 10));
		clouds.add(new Cloud(350, 60));
		clouds.add(new Cloud(550, 20));
		Score.startPoint = 10 * GAME_WIDTH + 500;
		renderer = this::renderLevel;
		updater = this::updateLevel;
	}

	public void prepareCanvas() {
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
		
		g.drawImage(image, xOffset, yOffset, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		bufferStrategy.show();
	}

	public void update() {
		updater.behave();
	}
	
	// Renderers
	private void renderLevel() {
		this.clearScreen();
		clouds.forEach(c -> c.renderOn(pixels));
		ground.renderOn(pixels);
		cactuses.forEach(c -> c.renderOn(pixels));
		passedCactuses.forEach(c -> c.renderOn(pixels));
		dino.renderOn(pixels);
		renderScore();
	}
	
	private void renderGameOverScreen() {
		int x0 = (GAME_WIDTH - gameOverWidth) / 2;
		int y0 = (int)((GAME_HEIGHT - gameOverHeight) * 0.6);
		
		for(int i = 0; i < gameOverPixels.length; i++) {
			int x = i % gameOverWidth;
			int y = i / gameOverWidth;
			if(gameOverPixels[i] != 0)
				pixels[y0 * GAME_WIDTH + x0 + y * GAME_WIDTH + x] = foregroundColor;
		}
	}
	
	private void renderScore() {
		Score.renderOn(pixels, (int)distance / 30);
	}

	private void clearScreen() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = backgroundColor;
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
			separationBetweenLastAndNextCactus = random.between(250, 600);
			distanceToLastCactus = 0;
		}
		distanceToLastCactus += Cactus.velocity;

		dino.update();
		ground.update();
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
		
		if(Engine.count > 0 && Engine.count % 1000 == 0) {
			Cactus.velocity+=.5f;
			Ground.velocity+=.5f;
			System.out.println("\nfaster: " + Cactus.velocity + "\n");
		}
		distance += Ground.velocity;
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
		ground.setInitialState();
		cactuses.clear();
		passedCactuses.clear();
		Cactus.velocity = initialVelocity;
		Engine.count = 0;
		score = 0;
		distance = 0;
		distanceToLastCactus = 0;
		updater = this::updateLevel;
		renderer = this::renderLevel;
		
		gameOver = false;
		
		gameOverListener.stop();
		levelListener.start();
	}
	
	public void makeDinosaurDuckDown() {
		dino.duck();
	}
	
	public void makeDinosaurStand() {
		dino.stand();
	}
}

interface Behaviour {
	public abstract void behave();
}