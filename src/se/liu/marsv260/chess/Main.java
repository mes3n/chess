package se.liu.marsv260.chess;

import se.liu.marsv260.chess.pieces.Bishop;
import se.liu.marsv260.chess.pieces.Entity;
import se.liu.marsv260.chess.pieces.Horse;
import se.liu.marsv260.chess.pieces.King;
import se.liu.marsv260.chess.pieces.Pawn;
import se.liu.marsv260.chess.pieces.Queen;
import se.liu.marsv260.chess.pieces.Rook;

import java.awt.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main class and entry point of chess program.
 */
public class Main
{
    private static Board setupBoard() {
	Board board = new Board(8, 8);
	board.addPieces(new Rook(Entity.Color.WHITE, new Point(0, 7), board), new Horse(Entity.Color.WHITE, new Point(1, 7), board),
			new Bishop(Entity.Color.WHITE, new Point(2, 7), board), new Queen(Entity.Color.WHITE, new Point(3, 7), board),
			new King(Entity.Color.WHITE, new Point(4, 7), board), new Bishop(Entity.Color.WHITE, new Point(5, 7), board),
			new Horse(Entity.Color.WHITE, new Point(6, 7), board), new Rook(Entity.Color.WHITE, new Point(7, 7), board),

			new Rook(Entity.Color.BLACK, new Point(0, 0), board), new Horse(Entity.Color.BLACK, new Point(1, 0), board),
			new Bishop(Entity.Color.BLACK, new Point(2, 0), board), new Queen(Entity.Color.BLACK, new Point(3, 0), board),
			new King(Entity.Color.BLACK, new Point(4, 0), board), new Bishop(Entity.Color.BLACK, new Point(5, 0), board),
			new Horse(Entity.Color.BLACK, new Point(6, 0), board), new Rook(Entity.Color.BLACK, new Point(7, 0), board));
	for (int i = 0; i < 8; i++) {
	    board.addPiece(new Pawn(Entity.Color.WHITE, new Point(i, 6), board));
	    board.addPiece(new Pawn(Entity.Color.BLACK, new Point(i, 1), board));
	}

	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	logger.log(Level.INFO, "Board was created with default setup");

	return board;
    }

    private static void startLogfile(String path) {
	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	logger.setLevel(Level.ALL);

	try {
	    FileHandler fileHandler = new FileHandler(path);
	    fileHandler.setFormatter(new SimpleFormatter());
	    fileHandler.setLevel(Level.FINE);
	    logger.addHandler(fileHandler);
	} catch (IOException ex) {
	    logger.log(Level.SEVERE, "Could not open logfile.");
	    logger.log(Level.SEVERE, "Exception was thrown:", ex);
	}
    }

    /**
     * Entry point of chess program.
     *
     * @param args
     */
    public static void main(String[] args) {
	startLogfile("chess.log");
	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

	logger.log(Level.INFO, "Starting chess");

	Board board = setupBoard();
	Player player = new Player(board);
	Display display = new Display(board, player, 64, "images/spritesheet.png");

	Viewer viewer = new Viewer();
	viewer.show(display);
    }
}
