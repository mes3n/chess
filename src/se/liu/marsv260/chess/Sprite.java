package se.liu.marsv260.chess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class for loading sprites as a BufferedImage.
 * <p>
 * Can also be used to load specific sprites from a spritesheet.
 */
public class Sprite
{
    private BufferedImage spriteSheet = null;
    private final String path;
    private final int tileSize;

    /**
     * Constructor of the Sprite class.
     *
     * @param path     the path to the sprite sheet which should be loaded.
     * @param tileSize the tilesize for each sprite in the spritesheet.
     */
    public Sprite(final String path, final int tileSize) {
	this.path = path;
	this.tileSize = tileSize;
    }

    private BufferedImage loadSprite() {
	final Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	try {
	    final URL image = ClassLoader.getSystemResource(path);
	    if (image == null) {
		logger.log(Level.SEVERE, "Could not find image at path {0}", path);
		return null;
	    } else {
		return ImageIO.read(image);
	    }
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, "Could not load image at {0}", path);
	    logger.log(Level.SEVERE, "Exception was thrown:", ex);
	    return null;
	}
    }

    /**
     * Get a specific sprite from the sprite sheet as a BufferedImage.
     *
     * @param x the x-coordinate of the sprite's tile.
     * @param y the y-coordinate of the sprite's tile.
     *
     * @return a BufferedImage of the chosen sprite.
     */
    public BufferedImage getSprite(int x, int y) {
	if (spriteSheet == null) {
	    spriteSheet = loadSprite();
	}
	if (spriteSheet == null) {
	    return null;
	}

	try {
	    return spriteSheet.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
	} catch (RasterFormatException ex) {
	    Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	    logger.log(Level.WARNING, "Specified tile at {0}, {1} is outside of image", new Object[] { x, y });
	    logger.log(Level.WARNING, "Exception was thrown:", ex);
	    return null;
	}
    }
}
