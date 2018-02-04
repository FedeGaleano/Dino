package fede;

import javax.swing.SwingUtilities;

public class Engine {
	
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
			game.render();
			game.update();
			sleep(15);
			count++;
		}
	}
	
	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
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
