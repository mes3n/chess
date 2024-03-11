package se.liu.marsv260.chess;

import javax.swing.*;
import java.awt.*;

public class Viewer
{
    public void show(final Display display) {
	JFrame frame = new JFrame("Chess");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	frame.setLayout(new BorderLayout());
	frame.add(display, BorderLayout.CENTER);

	JPanel panel = new JPanel(new BorderLayout());
//	panel.add(display.getLastMove(), BorderLayout.EAST);
	panel.add(display.getStatus(), BorderLayout.WEST);
	frame.add(panel, BorderLayout.SOUTH);

	frame.pack();
	frame.setVisible(true);
    }
}
