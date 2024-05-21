package se.liu.marsv260.chess.pieces;

import se.liu.marsv260.chess.Board;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Class for the Pawn piece which is a subclass of Piece.
 */
public class Pawn extends Piece
{
    private boolean firstMove = true;
    private boolean canBeEnPassant = false;
    private final int forwards;

    private static final int FIRST_MOVE_LENGTH = 2;

    /**
     * Constructor of the Pawn class.
     *
     * @param color    the color of the created Pawn.
     * @param position the position of the created Pawn.
     * @param board    the board the Pawn is placed on.
     */
    public Pawn(ChessColor color, Point position, Board board) {
	super(color, Type.PAWN, position, board);
	forwards = color == ChessColor.WHITE ? -1 : 1;
    }

    /**
     * Returns the possible moves of Pawn.
     * <p>
     * Also checks if en passant or capture is an available move.
     *
     * @return List of Pawn's available moves.
     */
    @Override public java.util.List<Point> getMoves(boolean checkForCheck) {
	final List<Point> deltas = Arrays.asList(new Point(0, forwards));
	List<Point> moves = stepBy(deltas, firstMove ? FIRST_MOVE_LENGTH : 1, checkForCheck);

	final List<Point> deltasCapture = Arrays.asList(new Point(1, forwards), new Point(-1, forwards));
	moves.addAll(stepBy(deltasCapture, 1, this::captureAddToAndStop, checkForCheck));

	final List<Point> enPassantCapture = Arrays.asList(new Point(1, 0), new Point(-1, 0));
	moves.addAll(stepBy(enPassantCapture, 1, this::enPassantAddToAndStop, checkForCheck));

	return moves;
    }

    @Override protected boolean addToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE || result == Board.MoveResult.WALLED) {
	    return true;
	}
	if (result == Board.MoveResult.IN_CHECK) {
	    return false;
	}
	moves.add(new Point(move));
	return false;
    }

    private boolean captureAddToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE) {
	    moves.add(new Point(move));
	}
	return true;
    }

    private boolean enPassantAddToAndStop(final List<Point> moves, final Point move, final Board.MoveResult result) {
	if (result == Board.MoveResult.CAPTURE) {
	    Piece piece = board.findPieceAt(move);
	    if (Type.PAWN.equals(piece.getType()) && ((Pawn) piece).getCanBeEnPassant()) {
		moves.add(new Point(move.x, move.y + forwards));
	    }
	}
	return true;
    }

    /**
     * @return whether or not Pawn can be captured en passant.
     */
    public boolean getCanBeEnPassant() {
	return canBeEnPassant;
    }

    /**
     * Resets Pawn to so that it cannot be captured en passat next move.
     */
    public void resetEnPassant() {
	canBeEnPassant = false;
    }

    /**
     * Attempts to move Pawn to a position on the baord.
     * <p>
     * Custom handeling of en passant captures.
     * <p>
     * Also allows Pawn to autopromote to Queen if the opposite rank has been reached.
     *
     * @param position position to attempt to move to.
     *
     * @return whether or not Pawn was actually moved.
     */
    @Override public boolean moveTo(final Point position) {
	final Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	Point oldPosition = new Point(getPosition());
	if (!super.moveTo(position)) {
	    return false;
	}
	// Capture has been made en passant
	if (Math.abs(position.x - oldPosition.x) == 1) {
	    Piece piece = board.findPieceAt(new Point(position.x, oldPosition.y));
	    if (Type.PAWN.equals(piece.getType()) && !getColor().equals(piece.getColor()) && ((Pawn) piece).getCanBeEnPassant()) {
		logger.log(Level.FINE, "{0} captured {1} en passant", new Object[] { getClass(), piece.getClass() });
		board.removePiece(piece);
	    }
	}
	if (position.y - oldPosition.y == FIRST_MOVE_LENGTH * forwards) {  // True if moved two steps forwards
	    canBeEnPassant = true;
	}
	firstMove = false;

	// Promote pawn
	if ((forwards == 1 && position.y == board.getHeight() - 1) || (forwards == -1 && position.y == 0)) {
	    logger.log(Level.FINE, "{0} reached the last rank", getClass());
	    board.addPiece(new Queen(getColor(), getPosition(), board));
	    board.removePiece(this);
	}
	return true;
    }
}
