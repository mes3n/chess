package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class for the King piece which is a subclass of Piece.
 */
public class King extends Piece
{
    private boolean hasMoved = false;

    /**
     * Constructor of the King class.
     *
     * @param color    the color of the created King.
     * @param position the position of the created King.
     * @param board    the board the King is placed on.
     */
    public King(Color color, Point position, Board board) {
	super(color, Type.KING, position, board);
    }

    /**
     * Returns the possible moves of King.
     * <p>
     * Also checks if casteling is an available move.
     *
     * @return List of King's available moves.
     */
    @Override public List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas =
		Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1), new Point(-1, -1), new Point(1, 1),
			      new Point(1, -1), new Point(-1, 1));

	List<Point> moves = stepBy(deltas, 1, checkForCheck);

	if (checkForCheck && !board.inCheck(this)) {
	    final List<Point> castelDeltas = Arrays.asList(new Point(1, 0), new Point(-1, 0));
	    final List<Point> castle = stepBy(castelDeltas, -1, this::casteAddToAndStop, checkForCheck);
	    for (Point move : castle) {
		Piece rook = board.pieceAt(move);
		if (rook instanceof Rook && !((Rook) rook).getHasMoved()) {
		    moves.add(new Point(getPosition().x + (move.x < getPosition().x ? -2 : 2), getPosition().y));
		}
	    }
	}

	return checkForCheck ? moves.stream().filter(move -> !board.inCheck(this, move)).collect(Collectors.toList()) : moves;
    }

    private boolean casteAddToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.WALLED) {
	    moves.add(new Point(move));
	    return true;
	}
	return false;
    }

    /**
     * Attempts to move King to a position on the baord.
     * <p>
     * Also moves rook if the chosen move is casteling.
     *
     * @param position position to attempt to move to.
     *
     * @return whether or not King was actually moved.
     */
    @Override public boolean moveTo(final Point position) {
	Point oldPosition = new Point(getPosition());
	if (!super.moveTo(position)) {
	    return false;
	}

	if (Math.abs(position.x - oldPosition.x) == 2) {
	    Piece rook = board.pieceAt(new Point((position.x - oldPosition.x > 0 ? 7 : 0), getPosition().y));
	    if (rook instanceof Rook && !((Rook) rook).getHasMoved()) {
		Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.log(Level.FINE, "Casteled {0} with {1}", new Object[] { getClass(), rook.getClass() });
		rook.setPosition(new Point(getPosition().x + (position.x - oldPosition.x > 0 ? -1 : 1), getPosition().y));
	    } else {
		setPosition(oldPosition);
		return false;
	    }
	}
	hasMoved = true;
	return true;
    }

    /**
     * @return has King moved or not.
     */
    public boolean getHasMoved() {
	return hasMoved;
    }
}
