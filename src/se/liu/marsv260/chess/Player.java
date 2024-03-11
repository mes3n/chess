package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Pawn;
import se.liu.marsv260.chess.pieces.Piece;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player
{
    private Piece selectedPiece = null;
    private Piece.Color currentPlayer = Piece.Color.WHITE;
    private boolean gameOver = false;
    private Piece.Color winner = null;

    private final Board board;
    private List<Point> moves = new ArrayList<>();

    public Player(final Board board) {
	this.board = board;
    }

    public Player(final Board board, Piece.Color currentPlayer) {
	this(board);
	this.currentPlayer = currentPlayer;
    }

    public List<Point> getMoves() {
	return moves;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public Piece.Color getWinner() {
	return winner;
    }

    public Piece.Color getCurrentPlayer() {
	return currentPlayer;
    }

    private Piece.Color nextPlayer() {
	return currentPlayer == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
    }

    public void move(final Point tile) {
	if (gameOver) {
	    return;
	}
	Piece selection = board.pieceWith(piece -> tile.equals(piece.getPosition()) && currentPlayer.equals(piece.getColor()));

	board.getPieces().forEach(piece -> {
	    if (piece instanceof Pawn && currentPlayer.equals(piece.getColor())) {
		((Pawn) piece).resetEnPassant();
	    }
	});

	if (selectedPiece != null && selectedPiece.moveTo(tile)) {
	    final List<Piece> pieceCpy = new ArrayList<>(board.getPieces());
	    if (pieceCpy.stream().noneMatch(piece -> nextPlayer().equals(piece.getColor()) && !piece.getMoves().isEmpty())) {
		winner = board.inCheck(nextPlayer()) ? currentPlayer : null;
		gameOver = true;
	    }

	    currentPlayer = nextPlayer();
	    selection = null;
	}

	selectedPiece = selection == selectedPiece ? null : selection;
	switch (selectedPiece) {
	    case null -> moves.clear();
	    default -> moves = selectedPiece.getMoves();
	}
    }
}
