package fede;

import javax.swing.SwingUtilities;

public class Engine {
	
	private static Game game;
	private static Thread mainLoopThread;
	private static boolean running = false;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Engine::setGuiThenGoToMainLoopThread);
	}
	
	private static void setGuiThenGoToMainLoopThread() {
		new Window(game = new Game())
			.onClose(Engine::finish);
		
		mainLoopThread = new Thread(Engine::mainLoop);
		mainLoopThread.start();
	}
	
	private static void mainLoop() {
		game.activate();
		running = true;
		
		while(running) {
			game.render();
			game.update();
			sleep(100);
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
	
	private static void finish() {
		try {
			game.over();
			running = false;
			mainLoopThread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
