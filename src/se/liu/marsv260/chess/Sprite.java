package se.liu.marsv260.chess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.net.URL;

public class Sprite
{
    private BufferedImage spriteSheet = null;
    private final String path;
    private final int tileSize;

    public Sprite(final String path, final int tileSize) {
	this.path = path;
	this.tileSize = tileSize;
    }

    private BufferedImage loadSprite() {
	BufferedImage sprite = null;
	try {
	    final URL image = ClassLoader.getSystemResource(path);
	    sprite = ImageIO.read(image);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return sprite;
    }

    public BufferedImage getSprite(int x, int y) {
	if (spriteSheet == null) {
	    spriteSheet = loadSprite();
	}

	BufferedImage image = null;
	try {
	    image = spriteSheet.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
	} catch (RasterFormatException e) {
	    e.printStackTrace();
	}
	return image;
    }
}
