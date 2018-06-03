package fede;

import javax.swing.SwingUtilities;

public class Engine {
	
	private static final double millis_per_frame = 15;

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
		
	/*	while(running) {
			long startTime = now();
			game.update();
			game.render();
			long processingTime = now() - startTime;
			sleep((long)millis_per_frame - processingTime);
		//	System.out.println("processing time: " + processingTime + " and slept: " + (millis_per_frame - processingTime));
			if(processingTime > millis_per_frame) System.out.println("DAMN " + processingTime);
			count++;
		} */
		
		long lastTime = now();
		boolean shouldRender = true;
		double framesLate = 0;
		
		while(running) {
			long startTime = now();
			framesLate += (startTime - lastTime) / millis_per_frame;
			lastTime = startTime;
			while(framesLate >= 1) {
				if(framesLate >= 2) System.out.println("damn nigga: " + framesLate + "\n");
				game.update();
				--framesLate;
				shouldRender = true;
			}
			if(shouldRender) {
				game.render();
				shouldRender = false;
			}
//			long processingTime = now() - startTime;
//			sleep(millis_per_frame - processingTime);
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
