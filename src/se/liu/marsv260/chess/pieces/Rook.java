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

    @Override public List<Point> getMoves() {
	return stepBy(DELTAS, -1);
    }

    @Override public void moveTo(final Point position) {
	super.moveTo(position);
	hasMoved = true;
    }

    public boolean getHasMoved() {
	return hasMoved;
    }
}
