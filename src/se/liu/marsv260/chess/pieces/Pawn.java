package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends Piece
{
    boolean firstMove = true;
    final int forwards;

    public Pawn(Color color, Point position, Board board) {
	super(color, Type.PAWN, position, board);
	forwards = color == Color.WHITE ? -1 : 1;
    }

    public Pawn(Color color, Point position, Board board, boolean firstMove) {
	this(color, position, board);
	this.firstMove = firstMove;
    }

    @Override public java.util.List<Point> getMoves() {
	final List<Point> deltas = Arrays.asList(new Point(0, forwards));
	List<Point> moves = stepBy(deltas, firstMove ? 2 : 1);

	final List<Point> deltasCapture = Arrays.asList(new Point(1, forwards), new Point(-1, forwards));
	moves.addAll(stepBy(deltasCapture, 1, this::captureAddTo));

	return moves;
    }

    @Override protected boolean addTo(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE || result == Board.MoveResult.WALL) {
	    return true;
	}
	moves.add(move);
	return false;
    }

    private boolean captureAddTo(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE) {
	    moves.add(move);
	}
	return true;
    }

    @Override public void moveTo(final Point position) {
	// TODO: Check for en passeant
	super.moveTo(position);
	firstMove = false;

	if ((forwards == 1 && position.y == board.getHeight() - 1) || (forwards == -1 && position.y == 0)) {
	    board.addPiece(new Queen(getColor(), position, board));
	    board.removePiece(this);
	}
    }
}
