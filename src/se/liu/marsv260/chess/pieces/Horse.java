package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Horse extends Piece
{
    public Horse(Piece.Color color, Point position, Board board) {
	super(color, Type.HORSE, position, board);
    }

    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas =
		Arrays.asList(new Point(2, -1), new Point(2, 1), new Point(-2, -1), new Point(-2, 1), new Point(1, -2), new Point(1, 2),
			      new Point(-1, -2), new Point(-1, 2));
	return stepBy(deltas, 1, checkForCheck);
    }
}
