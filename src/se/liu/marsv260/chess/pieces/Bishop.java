package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Class for the Bishop piece which is a subclass of Piece.
 */
public class Bishop extends Piece
{
    /**
     * Constructor of the Bishop class.
     *
     * @param color    the color of the created Bishop.
     * @param position the position of the created Bishop.
     * @param board    the board the Bishop is placed on.
     */
    public Bishop(ChessColor color, Point position, Board board) {
	super(color, Entity.Type.BISHOP, position, board);
    }

    /**
     * Returns the possible moves of Bishop.
     *
     * @return List of Bishops's available moves.
     */
    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas = Arrays.asList(new Point(-1, -1), new Point(1, 1), new Point(1, -1), new Point(-1, 1));
	return stepBy(deltas, -1, checkForCheck);
    }
}
