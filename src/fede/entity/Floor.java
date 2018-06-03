package fede.entity;

import fede.Game;
import fede.utils.Random;

public class Floor extends Entity {
	
	public static int Y = 180;
	private Random random = new Random();
	private int upperLines[] = new int[Game.GAME_WIDTH], lowerLines[] = new int[Game.GAME_WIDTH];
	private final static int longerLineLength = 4;
	private int color = Game.foregroundColor.getRGB();
	private static int velocity = Cactus.velocity;

	private int surface_y;
	private int upper_y;
	private int lower_y;
	
	private int distanceToLastUpperLine = 0, separationBetweenLastAndNextUpperLine = random.between(1, 5) * velocity;
	private int distanceToLastLowerLine = 0, separationBetweenLastAndNextLowerLine = random.between(1, 5) * velocity;
	
	public Floor() {
		y = Y;
		x = 0;

		surface_y = Game.GAME_WIDTH * y;
		upper_y = Game.GAME_WIDTH * (y + 7);
		lower_y = Game.GAME_WIDTH * (y + 10);
		
		setInitialState();
		
	}

	public void setInitialState() {
		
		for(int i = 0; i < Game.GAME_WIDTH; ++i)
			upperLines[i] = lowerLines[i] = 0;
		
		for (int i = 0; i < Game.GAME_WIDTH - longerLineLength; i += random.between(longerLineLength + 1, longerLineLength + 70))
			for (int j = i; j < i + random.between(1, longerLineLength); j++)
				upperLines[j] = color;
		
		for (int i = 0; i < Game.GAME_WIDTH - longerLineLength; i += random.between(longerLineLength + 1, longerLineLength + 70))
			for (int j = i; j < i + random.between(1, longerLineLength); j++)
				lowerLines[j] = color;
	}
	
	@Override
	public void renderOn(int destinationBuffer[]) {
		int upperOffset = distanceToLastUpperLine - separationBetweenLastAndNextUpperLine;
		int lowerOffset = distanceToLastLowerLine - separationBetweenLastAndNextLowerLine;
		
		if(upperOffset >= 0) {
			for(int i = (upperLines.length - 1)/*last array position*/ - upperOffset; i > upperLines.length - 1 - upperOffset - random.between(1, longerLineLength); --i)
				upperLines[i] = color;
			
			separationBetweenLastAndNextUpperLine = random.between(1, 70);
			distanceToLastUpperLine = 0;
		}
		else if(lowerOffset >= 0) {/*else: prevent lower and upper lines to render in the same iteration for them not to be very close to each other*/
			for(int i = lowerLines.length - 1 - lowerOffset; i > lowerLines.length - 1 - lowerOffset - random.between(1, longerLineLength); --i)
				lowerLines[i] = color;
			
			separationBetweenLastAndNextLowerLine = random.between(1, 70);
			distanceToLastLowerLine = 0;
		}
		distanceToLastUpperLine += velocity;
		distanceToLastLowerLine += velocity;
		
		shiftBufferLeft(upperLines);
		shiftBufferLeft(lowerLines);
		
		for(int i = 0; i < Game.GAME_WIDTH; ++i)
			destinationBuffer[surface_y + i] = color;
		
		for(int i = 0; i < upperLines.length; ++i)
			if(upperLines[i] != 0)
				destinationBuffer[upper_y + i] = upperLines[i];

		for(int i = 0; i < lowerLines.length; ++i)
			if(lowerLines[i] != 0)
				destinationBuffer[lower_y + i] = lowerLines[i];
	}
	
	private void shiftBufferLeft(int buff[]) {
		for (int i = 0; i < buff.length - velocity; i++)
			buff[i] = buff[i + velocity];
		for(int i = buff.length - 1; i > buff.length - 1 - velocity; --i)
			buff[i] = 0;
	}
	
	@Override
	public void update() {
		x -= velocity;
	}
	
}
