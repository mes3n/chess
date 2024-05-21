package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Class for the Queen piece which is a subclass of Piece.
 */
public class Queen extends Piece
{
    /**
     * Constructor of the Queen class.
     *
     * @param color    the color of the created Queen.
     * @param position the position of the created Queen.
     * @param board    the board the Queen is placed on.
     */
    public Queen(ChessColor color, Point position, Board board) {
	super(color, Type.QUEEN, position, board);
    }

    /**
     * Returns the possible moves of Queen.
     *
     * @return List of Queens's available moves.
     */
    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas =
		Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1), new Point(-1, -1), new Point(1, 1),
			      new Point(1, -1), new Point(-1, 1));

	return stepBy(deltas, -1, checkForCheck);
    }
}
