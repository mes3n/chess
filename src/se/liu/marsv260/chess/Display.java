package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Display extends JComponent
{
    private final int tileSize;
    private Sprite spriteSheet;
    private final Board board;

    private List<Point> moveOptions = new ArrayList<>();

    public Display(final Board board, final int tileSize, final String spritePath) {
	this.board = board;
	this.tileSize = tileSize;

	spriteSheet = new Sprite(spritePath, tileSize);
    }

    void setMoveOptions(List<Point> moveOptions) {
	this.moveOptions = moveOptions;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(board.getWidth() * tileSize, board.getHeight() * tileSize);
    }

    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	final int fontSize = tileSize / 4;
	final int margin = 4;
	final Font font = new Font("Serif", Font.BOLD, fontSize);
	g2d.setFont(font);

	for (int x = 0; x < board.getWidth(); x++) {
	    for (int y = 0; y < board.getHeight(); y++) {
		g2d.setColor((x + y) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
		g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
		g2d.setColor((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
		if (x == 0) {
		    g2d.drawString(Integer.toString(board.getHeight() - y), margin, y * tileSize + fontSize + margin);
		}
		if (y == board.getHeight() - 1) {
		    g2d.drawString(Character.toString(x + (int) 'a'), (x + 1) * tileSize - fontSize, board.getHeight() * tileSize - margin);
		}
	    }
	}

	for (Piece piece : board.getPieces()) {
	    g2d.drawImage(spriteSheet.getSprite(piece.getType().value, piece.getColor().value), piece.getPosition().x * tileSize,
			  piece.getPosition().y * tileSize, null);
	}

	g2d.setColor(new Color(100, 200, 0, 100));
	final int r = tileSize / 8;
	for (Point point : moveOptions) {
	    g2d.fillOval((int) ((double) tileSize * ((double) point.x + 0.5)) - r, (int) ((double) tileSize * ((double) point.y + 0.5)) - r, 2*r, 2*r);
	}
    }
}
