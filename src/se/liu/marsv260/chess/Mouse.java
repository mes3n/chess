package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Piece;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Mouse extends MouseAdapter
{
    private Piece selectedPiece = null;
    private final Board board;
    private final Display display;
    private final int tileSize;

    private List<Point> moves = new ArrayList<>();

    public Mouse(final Board board, final Display display, final int tileSize) {
	this.board = board;
	this.display = display;
	this.tileSize = tileSize;
    }

    @Override public void mouseReleased(final MouseEvent mouseEvent) {
	Point tile = new Point(mouseEvent.getX() / tileSize, mouseEvent.getY() / tileSize);
	if (selectedPiece == null) {
	    for (Piece piece : board.getPieces()) {
		if (tile.equals(piece.getPosition())) {
		    selectedPiece = piece;
		    moves = piece.getMoves();
		    break;
		}
	    }
	} else {
	    if (moves.contains(tile)) {
		selectedPiece.moveTo(tile);
	    }
	    selectedPiece = null;
	    moves.clear();
	}
	display.setMoveOptions(moves);
	display.repaint();
    }
}
