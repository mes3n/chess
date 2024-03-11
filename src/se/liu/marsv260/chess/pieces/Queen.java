package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Queen extends Piece
{
    private static final List<Point> DELTAS =  // TODO: Similar to King movements
	    Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1), new Point(-1, -1), new Point(1, 1),
			  new Point(1, -1), new Point(-1, 1));

    public Queen(Color color, Point position, Board board) {
	super(color, Type.QUEEN, position, board);
    }

    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	return stepBy(DELTAS, -1, checkForCheck);
    }
}
