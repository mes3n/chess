package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Piece;

import java.awt.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

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

    public void addPieces(final Piece ...pieces) {
	this.pieces.addAll(Arrays.stream(pieces).toList());
    }

    public void removePiece(final Piece piece) {
	pieces.remove(piece);
    }

    public MoveResult checkMove(final Point position, Piece.Color color) {
	if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
	    return MoveResult.WALL;
	}
	for (Piece piece : pieces) {
	    if (position.equals(piece.getPosition())) {
		return color == piece.getColor() ? MoveResult.WALL : MoveResult.CAPTURE;
	    }
	}
	return MoveResult.OK;
    }

    public boolean inCheck(final Point position, Piece.Color color) {
	for (Piece piece : pieces) {
	    if (!color.equals(piece.getColor())) {
		if (piece.getMoves().contains(position)) {
		    return true;
		}
	    }
	}
	return false;
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

    public enum MoveResult {
	CAPTURE, WALL, OK
    }
}