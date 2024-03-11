package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;

import java.util.Arrays;
import java.util.List;

public class Rook extends Piece
{
    private static final List<Point> DELTAS = Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1));
    private boolean hasMoved = false;

    public Rook(Color color, Point position, Board board) {
	super(color, Type.ROOK, position, board);
    }

    public Rook(Color color, Point position, Board board, boolean hasMoved) {
	this(color, position, board);
	this.hasMoved = hasMoved;
    }

    @Override public List<Point> getMoves(boolean checkForCheck) {
	return stepBy(DELTAS, -1, checkForCheck);
    }

    @Override public boolean moveTo(final Point position) {
	if (!super.moveTo(position)) {
	    return false;
	}
	hasMoved = true;
	return true;
    }

    public boolean getHasMoved() {
	return hasMoved;
    }
}
