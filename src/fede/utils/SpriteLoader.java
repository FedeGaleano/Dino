package fede.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteLoader {
	
	public static final Bitmap cactus1  = getBitmap("res/cactus1.png");
	public static final Bitmap cactus2  = getBitmap("res/cactus2.png");
	public static final Bitmap cactus3  = getBitmap("res/cactus3.png");
	public static final Bitmap cactus4  = getBitmap("res/cactus4.png");
	public static final Bitmap cactus5  = getBitmap("res/cactus5.png");
	public static final Bitmap cactus6  = getBitmap("res/cactus6.png");
	public static final Bitmap dinosaur = getBitmap("res/Dinosaur.png");
	public static final Bitmap cloud    = getBitmap("res/cloud.png");
	public static final Bitmap hi_score = getBitmap("res/hi-score.png");
	
	private static Bitmap getBitmap(String path) {
		
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(new File(path));
		}
		catch (IOException e)
		{
			System.err.println("Couldn't load image");
			e.printStackTrace();
		}
		
		return new Bitmap(getImageRaster(image), image.getWidth(), image.getHeight());
	}
	
	private static int[] getImageRaster(BufferedImage image) {
		return image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
	}
}
