package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Class for the Horse piece which is a subclass of Piece.
 */
public class Horse extends Piece
{
    /**
     * Constructor of the Horse class.
     *
     * @param color    the color of the created Horse.
     * @param position the position of the created Horse.
     * @param board    the board the Horse is placed on.
     */
    public Horse(ChessColor color, Point position, Board board) {
	super(color, Type.HORSE, position, board);
    }

    /**
     * Returns the possible moves of Horse.
     *
     * @return List of Horses's available moves.
     */
    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas =
		Arrays.asList(new Point(2, -1), new Point(2, 1), new Point(-2, -1), new Point(-2, 1), new Point(1, -2), new Point(1, 2),
			      new Point(-1, -2), new Point(-1, 2));
	return stepBy(deltas, 1, checkForCheck);
    }
}
