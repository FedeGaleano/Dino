package fede;

import javax.swing.SwingUtilities;
/*
 * TODO List:
 * ----------
 * 
 *   TECHNICAL
 *   ---------
 * - In game::update, update hitboxes state instead of updating it's reference, it's too expensive
 * - Load dino raster from SpriteLoader
 * - Use sprite sheets
 * 
 *   GAMEPLAY
 *   --------
 * - Make dino be able to duck down
 * - Make dino be able to fire
 * - Let clouds and cactuses be able to spawn at any position
 * - Render score and highscore
 * 
 * */
public class Engine {
	
	private static final double millis_per_frame = 15;//original: 15

	public static int count = 0;
	public static int frames = 0;
	
	private static Game game;
	private static Thread mainLoopThread;
	private static volatile boolean running = false;
	public static Window window;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Engine::setGuiThenGoToMainLoopThread);
	}
	
	private static void setGuiThenGoToMainLoopThread() {
		window = new Window(game = new Game());
		window.onClose(Engine::finish);
		
		mainLoopThread = new Thread(Engine::mainLoop);
		mainLoopThread.start();
	}
	
	private static void mainLoop() {
		game.prepareCanvas();
		running = true;
		
		long millis = 0;
		
		long lastTime = now();
		boolean shouldRender = true;
		double framesLate = 0;
		
		while(running) {
			long startTime = now();
			long delta_time = startTime - lastTime;
			millis += delta_time; 
			framesLate += delta_time / millis_per_frame;
			lastTime = startTime;
			while(framesLate >= 1) {
				game.update();
				--framesLate;
				shouldRender = true;
			}
			if(shouldRender) {
				game.render();
				shouldRender = false;
				frames++;
			}
			if(millis >= 1000) {
				window.appendText("FPS: " + frames);
				millis -= 1000;
				frames = 0;
			}
			sleep(2);
			count++;
		}
	}
	
	private static long now() {
		return System.currentTimeMillis();
	}
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(Math.max(millis, 0));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void finish() {
		try {
			game.free();
			running = false;
			mainLoopThread.join();
			System.exit(0);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
