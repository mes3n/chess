package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Bishop;
import se.liu.marsv260.chess.pieces.Horse;
import se.liu.marsv260.chess.pieces.King;
import se.liu.marsv260.chess.pieces.Pawn;
import se.liu.marsv260.chess.pieces.Piece;
import se.liu.marsv260.chess.pieces.Queen;
import se.liu.marsv260.chess.pieces.Rook;

import java.awt.*;

public class Main
{
    private static Board setupBoard() {
	Board board = new Board(8, 8);
	board.addPieces(new Rook(Piece.Color.WHITE, new Point(0, 7), board), new Horse(Piece.Color.WHITE, new Point(1, 7), board),
			new Bishop(Piece.Color.WHITE, new Point(2, 7), board), new Queen(Piece.Color.WHITE, new Point(3, 7), board),
			new King(Piece.Color.WHITE, new Point(4, 7), board), new Bishop(Piece.Color.WHITE, new Point(5, 7), board),
			new Horse(Piece.Color.WHITE, new Point(6, 7), board), new Rook(Piece.Color.WHITE, new Point(7, 7), board),

			new Rook(Piece.Color.BLACK, new Point(0, 0), board), new Horse(Piece.Color.BLACK, new Point(1, 0), board),
			new Bishop(Piece.Color.BLACK, new Point(2, 0), board), new Queen(Piece.Color.BLACK, new Point(3, 0), board),
			new King(Piece.Color.BLACK, new Point(4, 0), board), new Bishop(Piece.Color.BLACK, new Point(5, 0), board),
			new Horse(Piece.Color.BLACK, new Point(6, 0), board), new Rook(Piece.Color.BLACK, new Point(7, 0), board));
	for (int i = 0; i < 8; i++) {
	    board.addPiece(new Pawn(Piece.Color.WHITE, new Point(i, 6), board));
	    board.addPiece(new Pawn(Piece.Color.BLACK, new Point(i, 1), board));
	}
	return board;
    }

    public static void main(String[] args) {
	Board board = new Board(8, 8);
	board.addPieces(new King(Piece.Color.WHITE, new Point(0, 0), board), new Rook(Piece.Color.BLACK, new Point(2, 3), board));

	Viewer viewer = new Viewer();
	viewer.show(board);
    }
}
