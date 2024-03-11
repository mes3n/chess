package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.King;
import se.liu.marsv260.chess.pieces.Piece;

import java.awt.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Board
{
    private final int width;
    private final int height;

    private List<Piece> pieces = new ArrayList<>();

    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
    }

    public void addPiece(final Piece piece) {
	pieces.add(piece);
    }

    public void addPieces(final Piece... pieces) {
	this.pieces.addAll(Arrays.stream(pieces).toList());
    }

    public void removePiece(final Piece piece) {
	pieces.remove(piece);
    }

    public Piece pieceWith(final Predicate<Piece> predicate) {
	return pieces.stream().filter(predicate).findFirst().orElse(null);
    }

    public Piece pieceAt(final Point position) {
	return pieceWith(piece -> position.equals(piece.getPosition()));
    }

    public MoveResult verifyMove(final Piece piece, final Point position, boolean checkForCheck) {
	if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
	    return MoveResult.IMPOSSIBLE;
	}
	final Piece moveToPiece = pieceAt(position);
	if (checkForCheck) {
	    if (moveToPiece != null && !piece.getColor().equals(moveToPiece.getColor())) {
		removePiece(moveToPiece);
	    }
	    final Point oldPositon = new Point(piece.getPosition());
	    piece.setPosition(position);
	    boolean isInCheck = inCheck(piece.getColor());
	    piece.setPosition(oldPositon);
	    if (moveToPiece != null && !piece.getColor().equals(moveToPiece.getColor())) {
		addPiece(moveToPiece);
	    }
	    if (isInCheck) {
		return MoveResult.IMPOSSIBLE;
	    }
	}
	if (moveToPiece == null) {
	    return MoveResult.OK;
	}
	return piece.getColor() == moveToPiece.getColor() ? MoveResult.IMPOSSIBLE : MoveResult.CAPTURE;
    }

    public boolean inCheck(Piece.Color color) {
	final King king = (King) pieceWith(piece -> piece instanceof King && color.equals(piece.getColor()));
	return pieceWith(piece -> !king.getColor().equals(piece.getColor()) && piece.getMoves(false).contains(king.getPosition())) != null;
    }

    public boolean inCheck(King king, Point position) {
	Point oldPosition = new Point(king.getPosition());
	king.setPosition(position);
	boolean result = pieceWith(piece -> !king.getColor().equals(piece.getColor()) && piece.getMoves(false).contains(position)) != null;
	king.setPosition(oldPosition);
	return result;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public List<Piece> getPieces() {
	return pieces;
    }

    public enum MoveResult
    {
	CAPTURE, IMPOSSIBLE, OK
    }
}