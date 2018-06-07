package fede.entity;

import fede.Game;
import fede.utils.Random;

public class Ground extends Entity {
	
	public static int Y = 180;
	private Random random = new Random();
	private int upperLines[] = new int[Game.GAME_WIDTH], lowerLines[] = new int[Game.GAME_WIDTH];
	private final static int longerLineLength = 4;
	private int color = Game.foregroundColor.getRGB();
	public static float velocity = Game.initialVelocity;
	private int previous_x;
	private float virtual_x;

	private int surface_y;
	private int upper_y;
	private int lower_y;
	
	private int distanceToLastUpperLine = 0, separationBetweenLastAndNextUpperLine = (int)(random.between(1, 5) * velocity);
	private int distanceToLastLowerLine = 0, separationBetweenLastAndNextLowerLine = (int)(random.between(1, 5) * velocity);
	
	public Ground() {
		y = Y;
		x = 0;
		previous_x = x;
		virtual_x = x;

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
		
		velocity = Game.initialVelocity;	
	}
	
	@Override
	public void renderOn(int destinationBuffer[]) {
		int shift = - x + previous_x;
		System.out.println("x: " + x + " , prev_x: " + previous_x);
		System.out.println("shift: " + shift);
		
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
		distanceToLastUpperLine += shift;
		distanceToLastLowerLine += shift;
		
		if(shift > 0) {
			shiftBufferLeft(upperLines, shift);
			shiftBufferLeft(lowerLines, shift);
		}
		
		for(int i = 0; i < Game.GAME_WIDTH; ++i)
			destinationBuffer[surface_y + i] = color;
		
		for(int i = 0; i < upperLines.length; ++i)
			if(upperLines[i] != 0)
				destinationBuffer[upper_y + i] = upperLines[i];

		for(int i = 0; i < lowerLines.length; ++i)
			if(lowerLines[i] != 0)
				destinationBuffer[lower_y + i] = lowerLines[i];
		
		previous_x = x;
	}
	
	private void shiftBufferLeft(int buff[], int shift) {
		for (int i = 0; i < buff.length - shift; i++)
			buff[i] = buff[i + shift];
		for(int i = buff.length - 1; i > buff.length - 1 - shift; --i)
			buff[i] = 0;
	}
	
	@Override
	public void update() {
		virtual_x -= velocity;
		x = (int)virtual_x;
	}
	
}
