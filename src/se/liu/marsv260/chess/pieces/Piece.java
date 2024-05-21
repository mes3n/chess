package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Abstract class to represent a chess piece and its position on the board. Is a subclass of Entity.
 * <p>
 * Also includes methods for controlling the piece's position on the board.
 */
public abstract class Piece extends Entity
{
    private Point position;
    final protected Board board;

    /**
     * Base constructor of the Piece abstract class.
     *
     * @param color    the color of the created Piece.
     * @param type     the type of the created Piece.
     * @param position the position on the board of the created Piece.
     * @param board    the board the Piece is placed on.
     */
    protected Piece(final Color color, final Type type, final Point position, final Board board) {
	super(color, type);
	this.position = position;
	this.board = board;
    }

    /**
     * Returns the possible moves of a piece with regard to pins.
     *
     * @return List of Piece's available moves.
     */
    public List<Point> getMoves() {
	return getMoves(true);
    }

    /**
     * Returns the possible moves of a piece.
     *
     * @param checkForCheck whether or not to recursively look at possible checks.
     *
     * @return List of Piece's available moves.
     */
    abstract public List<Point> getMoves(boolean checkForCheck);

    /**
     * Attempts to move Piece to a position on the baord.
     * <p>
     * Also removes captured pieces from board.
     *
     * @param position position to attempt to move to.
     *
     * @return whether or not the Piece was actually moved.
     */
    public boolean moveTo(Point position) {
	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	if (this.position.equals(position) || !getMoves().contains(position)) {
	    logger.log(Level.FINE, "Failed with moving {0} to {1}", new Object[] { getClass(), position });
	    return false;
	}
	Piece piece = board.pieceAt(position);
	if (piece != null) {
	    if (getColor().equals(piece.getColor())) {
		return false;
	    } else {
		board.removePiece(piece);
	    }
	}
	this.position = position;
	logger.log(Level.FINE, "Moved {0} to {1}", new Object[] { getClass(), position });
	return true;
    }

    protected interface AdderFunction
    {
	boolean addToAndStop(List<Point> moves, Point move, Board.MoveResult result);
    }

    protected List<Point> stepBy(List<Point> deltas, final int len, AdderFunction adder, boolean checkForCheck) {
	List<Point> moves = new ArrayList<>();

	for (Point delta : deltas) {
	    Board.MoveResult result = Board.MoveResult.OK;
	    Point moveTo = new Point(position);
	    int c = 0;
	    do {
		moveTo.translate(delta.x, delta.y);
		result = board.verifyMove(this, moveTo, checkForCheck);
		if (adder.addToAndStop(moves, moveTo, result)) {
		    break;
		}
		c++;
	    } while (result == Board.MoveResult.OK && (c < len || len == -1));
	}

	return moves;
    }

    protected List<Point> stepBy(List<Point> deltas, final int len, boolean checkForCheck) {
	return stepBy(deltas, len, this::addToAndStop, checkForCheck);
    }

    protected boolean addToAndStop(List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.WALLED) {
	    return true;
	}
	if (result == Board.MoveResult.IN_CHECK) {
	    return true;
	}
	moves.add(new Point(move));
	if (result == Board.MoveResult.CAPTURE) {
	    return true;
	}
	return false;
    }

    /**
     * @return Piece's position.
     */
    public Point getPosition() {
	return position;
    }

    /**
     * Sets the position of a Piece.
     * <p>
     * Only used when manually moving Pieces to check for available moves.
     *
     * @param position position to set for Piece.
     */
    public void setPosition(final Point position) {
	this.position = position;
    }
}