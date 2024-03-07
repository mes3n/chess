package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class King extends Piece
{
    private static final List<Point> DELTAS =  // TODO: Similar to Queen movements
	    Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1), new Point(-1, -1), new Point(1, 1),
			  new Point(1, -1), new Point(-1, 1));

    private boolean hasMoved = false;

    public King(Color color, Point position, Board board) {
	super(color, Type.KING, position, board);
    }

    public King(Color color, Point position, Board board, boolean hasMoved) {
	this(color, position, board);
	this.hasMoved = hasMoved;
    }

    @Override public List<Point> getMoves() {
	List<Point> moves = stepBy(DELTAS, 1);
	return moves.stream().filter(move -> !board.inCheck(move, getColor())).collect(Collectors.toList());
//	return moves;  // TODO: King should not be able to step into check
    }

    @Override public void moveTo(final Point position) {
	// TODO: Check for castle
	super.moveTo(position);
	hasMoved = true;
    }

    public boolean getHasMoved() {
	return hasMoved;
    }
}
