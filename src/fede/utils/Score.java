package fede.utils;

import java.awt.Color;

import fede.Game;

public class Score {
	
	private static final int color = Game.foregroundColor.getRGB();
	private static int numbers[][] = getNumbers();
	public static int startPoint = 0;
	
	public static void renderOn(int pixels[], int score) {
		int digit_4 = score / 1000;
		score -= digit_4 * 1000;
		int digit_3 = score / 100;
		score -= digit_3 * 100;
		int digit_2 = score / 10;
		score -= digit_2 * 10;
		int digit_1 = score;
		
		renderScore(pixels, digit_4, digit_3, digit_2, digit_1);
	}
	
	private static void renderScore(int pixels[], int d4, int d3, int d2, int d1) {
		int digit_4[] = numbers[d4];
		int digit_3[] = numbers[d3];
		int digit_2[] = numbers[d2];
		int digit_1[] = numbers[d1];
		renderDigit(pixels, digit_4, 0);
		renderDigit(pixels, digit_3, 1);
		renderDigit(pixels, digit_2, 2);
		renderDigit(pixels, digit_1, 3);
	}

	private static void renderDigit(int[] pixels, int[] digit, int offset_x) {
		for(int i = 0; i < digit.length; i++) {
			int xx = i % 9;
			int yy = i / 9;
			if(digit[i] != 0)
				pixels[startPoint + offset_x * 11 + yy * Game.GAME_WIDTH + xx] = color;
		}
	}
	
	private static int[][] getNumbers() {
		int pixels[] = SpriteLoader.hi_score.pixels;
		int w = SpriteLoader.hi_score.width;
		int ret[][] = new int[10][9 * 11];
		for(int i = 0; i < ret.length; i++) {
			for(int j = 0; j < ret[i].length; ++j) {
				int xx = j % 9;
				int yy = j / 9;
				ret[i][j] = pixels[i * 9 + yy * w + xx];
			}
		}
		return ret;
	}
}
