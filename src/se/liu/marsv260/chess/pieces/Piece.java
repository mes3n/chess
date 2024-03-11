package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece
{
    private final Color color;
    private final Type type;

    private Point position;
    final protected Board board;

    public List<Point> getMoves() {
	return getMoves(true);
    }

    abstract public List<Point> getMoves(boolean checkForCheck);

    protected Piece(final Color color, final Type type, final Point position, final Board board) {
	this.color = color;
	this.type = type;
	this.position = position;
	this.board = board;
    }

    public boolean moveTo(Point position) {
	if (this.position.equals(position) || !getMoves().contains(position)) {
	    return false;
	}
	Piece piece = board.pieceAt(position);
	if (piece != null) {
	    if (color.equals(piece.getColor())) {
		return false;
	    } else {
		board.removePiece(piece);
	    }
	}
	this.position = position;
	return true;
    }

    protected interface AdderFunction
    {
	boolean addTo(List<Point> moves, Point move, Board.MoveResult result);
    }

    protected List<Point> stepBy(List<Point> deltas, final int len, AdderFunction adder, boolean checkForCheck) {
	List<Point> moves = new ArrayList<>();

	for (Point delta : deltas) {
	    Board.MoveResult result = Board.MoveResult.OK;
	    Point moveTo = new Point(position);
	    int c = 0;
	    do {
		moveTo.translate(delta.x, delta.y);
		result = board.verifyMove(this, moveTo, checkForCheck);
		if (adder.addTo(moves, moveTo, result)) {
		    break;
		}
		c++;
	    } while (result == Board.MoveResult.OK && (c < len || len == -1));
	}

	return moves;
    }

    protected List<Point> stepBy(List<Point> deltas, final int len, boolean checkForCheck) {
	return stepBy(deltas, len, this::addToAndStop, checkForCheck);
    }

    protected boolean addToAndStop(List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.IMPOSSIBLE) {
	    return true;
	}
	moves.add(new Point(move));
	if (result == Board.MoveResult.CAPTURE) {
	    return true;
	}
	return false;
    }

    public Color getColor() {
	return color;
    }

    public Type getType() {
	return type;
    }

    public Point getPosition() {
	return position;
    }

    public void setPosition(final Point position) {
	this.position = position;
    }

    public enum Color
    {
	WHITE(0), BLACK(1);

	public final int value;

	Color(int value) {
	    this.value = value;
	}
    }

    public enum Type
    {
	KING(0), QUEEN(1), BISHOP(2), HORSE(3), ROOK(4), PAWN(5);

	public final int value;

	Type(int value) {
	    this.value = value;
	}
    }

}
