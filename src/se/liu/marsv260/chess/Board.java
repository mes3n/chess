package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Entity;
import se.liu.marsv260.chess.pieces.King;
import se.liu.marsv260.chess.pieces.Piece;

import java.awt.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class to represent the chess board on which the pieces interact with each other.
 */
public class Board
{
    private final int width;
    private final int height;

    private List<Piece> pieces = new ArrayList<>();

    /**
     * Constrotor of Board class.
     *
     * @param width  the width of the created Baord.
     * @param height the height of the created Baord.
     */
    public Board(final int width, final int height) {
	this.width = width;
	this.height = height;
    }

    /**
     * Add a Piece to Board.
     *
     * @param piece piece to be added to Board.
     */
    public void addPiece(final Piece piece) {
	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	logger.log(Level.FINE, "Added {0} to board.", piece.getClass().getName());
	pieces.add(piece);
    }

    /**
     * Add multiple Pieces to Board.
     *
     * @param pieces list of pieces to be added to Board.
     */
    public void addPieces(final Piece... pieces) {
	Arrays.stream(pieces).forEach(this::addPiece);
    }

    /**
     * Removes Piece from Board.
     *
     * @param piece piece to be removed from Board.
     */
    public void removePiece(final Piece piece) {
	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	logger.log(Level.FINE, "Removed {0} from board.", piece.getClass().getName());
	pieces.remove(piece);
    }

    /**
     * Find the first Piece in Board matching a predicate.
     *
     * @param predicate predicate the Piece will match.
     *
     * @return the first Piece mathched by predicate.
     */
    public Piece pieceWith(final Predicate<Piece> predicate) {
	return pieces.stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Find Piece on a certain Board position.
     *
     * @param position the position to check for a piece.
     *
     * @return a piece on the provided position or null.
     */
    public Piece pieceAt(final Point position) {
	return pieceWith(piece -> position.equals(piece.getPosition()));
    }

    /**
     * Checks the result from moving a Piece to a certain position.
     *
     * @param piece         the piece to be moved.
     * @param position      the position to move to.
     * @param checkForCheck whether or not the check for pins.
     *
     * @return a MoveResult from the move.
     */
    public MoveResult verifyMove(final Piece piece, final Point position, boolean checkForCheck) {
	if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
	    return MoveResult.WALLED;
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
		return MoveResult.IN_CHECK;
	    }
	}
	if (moveToPiece == null) {
	    return MoveResult.OK;
	}
	return piece.getColor() == moveToPiece.getColor() ? MoveResult.WALLED : MoveResult.CAPTURE;
    }

    /**
     * Checks whether a certain color is in check.
     *
     * @param color the color to check for.
     *
     * @return whether or not the color is in check.
     */
    public boolean inCheck(Entity.Color color) {
	final King king = (King) pieceWith(piece -> piece instanceof King && color.equals(piece.getColor()));
	return inCheck(king);
    }

    /**
     * Checks whether a specific king is in check.
     *
     * @param king the king to check for.
     *
     * @return whether or not the king is in check.
     */
    public boolean inCheck(King king) {
	return pieceWith(piece -> !king.getColor().equals(piece.getColor()) && piece.getMoves(false).contains(king.getPosition())) != null;
    }


    /**
     * Checks whether a specific king is in check after moving to a certain position.
     *
     * @param king     the king to check for.
     * @param position the position to move to.
     *
     * @return whether or not the king is in check.
     */
    public boolean inCheck(King king, Point position) {
	Point oldPosition = new Point(king.getPosition());
	king.setPosition(position);
	boolean result = inCheck(king);
	king.setPosition(oldPosition);
	return result;
    }

    /**
     * @return Board's width.
     */
    public int getWidth() {
	return width;
    }

    /**
     * @return Board's height.
     */
    public int getHeight() {
	return height;
    }

    /**
     * @return pieces placed on Board.
     */
    public List<Piece> getPieces() {
	return pieces;
    }

    /**
     * Enumerator of different potioneal move results.
     */
    public enum MoveResult
    {
	CAPTURE, WALLED, IN_CHECK, OK
    }
}