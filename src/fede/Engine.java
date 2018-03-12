package fede;

import javax.swing.SwingUtilities;

public class Engine {
	
	private static final int millis_per_frame = 15;

	public static int count = 0;
	
	private static Game game;
	private static Thread mainLoopThread;
	private static boolean running = false;
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
		game.activate();
		running = true;
		
		while(running) {
			long startTime = now();
			game.render();
			game.update();
			long processingTime = now() - startTime;
			sleep(millis_per_frame - processingTime);
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
