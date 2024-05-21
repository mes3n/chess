package se.liu.marsv260.chess;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class for creating a window which contains the Display and meta information.
 */
public class Viewer
{
    /**
     * Creates a JFrame and fills it with Display and status.
     *
     * @param display the Display Viewer should show
     */
    public void show(final Display display) {
	JFrame frame = new JFrame("Chess");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	frame.setLayout(new BorderLayout());
	frame.add(display, BorderLayout.CENTER);

	JPanel panel = new JPanel(new BorderLayout());
	panel.add(display.getStatus(), BorderLayout.WEST);
	frame.add(panel, BorderLayout.SOUTH);

	frame.pack();
	frame.setVisible(true);

	Logger logger = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
	logger.log(Level.INFO, "Created viewer window");
    }
}
