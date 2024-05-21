package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Entity;
import se.liu.marsv260.chess.pieces.Pawn;
import se.liu.marsv260.chess.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class to represent the players deciding what moves to make and responsible for moving pieces on the baord.
 */
public class Player
{
    private Piece selectedPiece = null;
    private Entity.Color currentPlayer = Entity.Color.WHITE;
    private boolean gameOver = false;
    private Entity.Color winner = null;

    private final Board board;
    private List<Point> moves = new ArrayList<>();

    /**
     * Constructor of the Player class.
     *
     * @param board the board the Player will use.
     */
    public Player(final Board board) {
	this.board = board;
    }

    /**
     * @return the currently available moves.
     */
    public List<Point> getMoves() {
	return moves;
    }

    /**
     * @return whether or not the game is over.
     */
    public boolean isGameOver() {
	return gameOver;
    }

    /**
     * @return the winner of the game.
     */
    public Entity.Color getWinner() {
	return winner;
    }

    /**
     * @return the player whose move it is.
     */
    public Entity.Color getCurrentPlayer() {
	return currentPlayer;
    }

    private Entity.Color nextPlayer() {
	return currentPlayer == Entity.Color.WHITE ? Entity.Color.BLACK : Entity.Color.WHITE;
    }

    /**
     * Perform an action on the provided tile.
     * <p>
     * This can include selection a piece, moving a piece or unselecting a piece.
     *
     * @param tile the tile to act on.
     */
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

		Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.log(Level.FINE, "Game is finished. Winner is {0}", winner);
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
