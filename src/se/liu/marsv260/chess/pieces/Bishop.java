package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Bishop extends Piece
{
    private static final List<Point> DELTAS = Arrays.asList(new Point(-1, -1), new Point(1, 1), new Point(1, -1), new Point(-1, 1));

    public Bishop(Piece.Color color, Point position, Board board) {
	super(color, Piece.Type.BISHOP, position, board);
    }

    @Override public java.util.List<Point> getMoves() {
	return stepBy(DELTAS, -1);
    }
}
