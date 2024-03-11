package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece
{
    private boolean firstMove = true;
    private boolean canBeEnPassant = false;
    private final int forwards;

    public Pawn(Color color, Point position, Board board) {
	super(color, Type.PAWN, position, board);
	forwards = color == Color.WHITE ? -1 : 1;
    }

    public Pawn(Color color, Point position, Board board, boolean firstMove) {
	this(color, position, board);
	this.firstMove = firstMove;
    }

    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas = Arrays.asList(new Point(0, forwards));
	List<Point> moves = stepBy(deltas, firstMove ? 2 : 1, checkForCheck);

	final List<Point> deltasCapture = Arrays.asList(new Point(1, forwards), new Point(-1, forwards));
	moves.addAll(stepBy(deltasCapture, 1, this::captureAddToAndStop, checkForCheck));

	final List<Point> enPassantCapture = Arrays.asList(new Point(1, 0), new Point(-1, 0));
	moves.addAll(stepBy(enPassantCapture, 1, this::enPassantAddToAndStop, checkForCheck));

	return moves;
    }

    @Override protected boolean addToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE || result == Board.MoveResult.IMPOSSIBLE) {
	    return true;
	}
	moves.add(new Point(move));
	return false;
    }

    private boolean captureAddToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE) {
	    moves.add(new Point(move));
	}
	return true;
    }

    private boolean enPassantAddToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE) {
	    Piece piece = board.pieceAt(move);
	    if (piece instanceof Pawn && ((Pawn) piece).getCanBeEnPassant()) {
		moves.add(new Point(move.x, move.y + forwards));
	    }
	}
	return true;
    }

    public boolean getCanBeEnPassant() {
	return canBeEnPassant;
    }

    public void resetEnPassant() {
	canBeEnPassant = false;
    }

    @Override public boolean moveTo(final Point position) {
	Point oldPosition = new Point(getPosition());
	if (!super.moveTo(position)) {
	    return false;
	}
	// Capture has been made
	if (Math.abs(position.x - oldPosition.x) == 1) {
	    Piece piece = board.pieceAt(new Point(position.x, oldPosition.y));
	    if (piece instanceof Pawn && !getColor().equals(piece.getColor()) && ((Pawn) piece).getCanBeEnPassant()) {
		board.removePiece(piece);
	    }
	}
	if (position.y - oldPosition.y == 2 * forwards) {  // True if moved two steps forwards
	    canBeEnPassant = true;
	}
	firstMove = false;

	// Promote pawn
	if ((forwards == 1 && position.y == board.getHeight() - 1) || (forwards == -1 && position.y == 0)) {
	    board.addPiece(new Queen(getColor(), getPosition(), board));
	    board.removePiece(this);
	}
	return true;
    }
}
