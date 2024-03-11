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

    @Override public List<Point> getMoves(boolean checkForCheck) {
	List<Point> moves = stepBy(DELTAS, 1, checkForCheck);

	// TODO: Might be able to castle out of check
	final List<Point> deltas = Arrays.asList(new Point(1, 0), new Point(-1, 0));
	final List<Point> castle = stepBy(deltas, -1, this::casteAddToAndStop, checkForCheck);
	for (Point move : castle) {
	    Piece rook = board.pieceAt(move);
	    if (rook instanceof Rook && !((Rook) rook).getHasMoved()) {
		moves.add(new Point(getPosition().x + (move.x < getPosition().x ? -2 : 2), getPosition().y));
	    }
	}

	return checkForCheck ? moves.stream().filter(move -> !board.inCheck(this, move)).collect(Collectors.toList()) : moves;
    }

    private boolean casteAddToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.IMPOSSIBLE) {
	    moves.add(new Point(move));
	    return true;
	}
	return false;
    }

    @Override public boolean moveTo(final Point position) {
	// TODO: Check for castle
	Point oldPosition = new Point(getPosition());
	if (!super.moveTo(position)) {
	    return false;
	}

	if (Math.abs(position.x - oldPosition.x) == 2) {
	    Piece rook = board.pieceAt(new Point((position.x - oldPosition.x > 0 ? 7 : 0), getPosition().y)); // TODO: Hardcoded
	    if (rook instanceof Rook && !((Rook) rook).getHasMoved()) {
		rook.setPosition(new Point(getPosition().x + (position.x - oldPosition.x > 0 ? -1 : 1), getPosition().y));
	    } else {
		setPosition(oldPosition);
		return false;
	    }
	}
	hasMoved = true;
	return true;
    }

    public boolean getHasMoved() {
	return hasMoved;
    }
}
