package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Entity;
import se.liu.marsv260.chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class for drawing chess board with its pieces to screen. Subclass of JComponent.
 * <p>
 * Also provides an interface for listening to mouse moments and providing information.
 */
public class Display extends JComponent
{
    private final int tileSize;
    private Sprite spriteSheet;
    private final Board board;
    private final Player player;

    private JLabel status = new JLabel();

    /**
     * Constructor of the Display class
     *
     * @param board      the Board the Display should show.
     * @param player     the Player using the Display.
     * @param tileSize   the size of a tile in spritesheet.
     * @param spritePath the path to the spritesheet.
     */
    public Display(final Board board, final Player player, final int tileSize, final String spritePath) {
	this.board = board;
	this.player = player;
	this.tileSize = tileSize;

	spriteSheet = new Sprite(spritePath, tileSize);

	addMouseListener(new MouseAdapter()
	{
	    @Override public void mouseReleased(final MouseEvent me) {
		player.move(getBoardTile(me.getPoint()));
		repaint();
		updateStatus();

		Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.log(Level.FINE, "Mouse release was handeled on board tile {0}", getBoardTile(me.getPoint()));
	    }
	});

	status.setFont(new Font("Serif", Font.BOLD, 16));
	updateStatus();
    }

    /**
     * @return JLabel containing information such as current player.
     */
    public JLabel getStatus() {
	return status;
    }

    private void updateStatus() {
	if (player.isGameOver()) {
	    Entity.ChessColor winner = player.getWinner();
	    String message = switch (winner) {
		case null -> "both players draw.";
		default -> winner.toString().toLowerCase() + " wins.";
	    };
	    status.setText(message);
	    JOptionPane.showMessageDialog(this, message);
	    return;
	}
	status.setText(player.getCurrentPlayer().toString().toLowerCase() + " to move.");
    }

    private Point getBoardTile(final Point position) {
	return new Point((int) position.getX() / tileSize, (int) position.getY() / tileSize);
    }

    /**
     * @return preferred Dimension of Display.
     */
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
		    g2d.drawString(Character.toString(x + Integer.valueOf('a')), (x + 1) * tileSize - fontSize,
				   board.getHeight() * tileSize - margin);
		}
	    }
	}

	for (Piece piece : board.getPieces()) {
	    g2d.drawImage(spriteSheet.getSprite(piece.getType().value, piece.getColor().value), piece.getPosition().x * tileSize,
			  piece.getPosition().y * tileSize, null);
	}

	g2d.setColor(new Color(100, 200, 0, 100));
	final int scale = 8;
	final int r = tileSize / scale;
	for (Point point : player.getMoves()) {
	    g2d.fillOval((int) (tileSize * (point.x + 0.5)) - r, (int) (tileSize * (point.y + 0.5)) - r, 2 * r, 2 * r);
	}
    }
}
