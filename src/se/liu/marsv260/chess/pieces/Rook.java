package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;

import java.util.Arrays;
import java.util.List;

/**
 * Class for the Rook piece which is a subclass of Piece.
 */
public class Rook extends Piece
{
    private boolean hasMoved = false;

    /**
     * Constructor of the Rook class.
     *
     * @param color    the color of the created Rook.
     * @param position the position of the created Rook.
     * @param board    the board the Rook is placed on.
     */
    public Rook(Color color, Point position, Board board) {
	super(color, Type.ROOK, position, board);
    }

    /**
     * Constructor of the Rook class.
     *
     * @param color    the color of the created Rook.
     * @param position the position of the created Rook.
     * @param board    the board the Rook is placed on.
     * @param hasMoved whether or not the Rook has moved.
     */
    public Rook(Color color, Point position, Board board, boolean hasMoved) {
	this(color, position, board);
	this.hasMoved = hasMoved;
    }

    /**
     * Returns the possible moves of Rook.
     *
     * @return List of Rook's available moves.
     */
    @Override public List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas = Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1));
	return stepBy(deltas, -1, checkForCheck);
    }

    /**
     * Attempts to move Rook to a position on the baord.
     *
     * @param position position to attempt to move to.
     *
     * @return whether or not Rook was actually moved.
     */
    @Override public boolean moveTo(final Point position) {
	if (!super.moveTo(position)) {
	    return false;
	}
	hasMoved = true;
	return true;
    }

    /**
     * @return has Rook moved or not.
     */
    public boolean getHasMoved() {
	return hasMoved;
    }
}
