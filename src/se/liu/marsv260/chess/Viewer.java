package se.liu.marsv260.chess;

import javax.swing.*;
import java.awt.*;

public class Viewer
{
    public void show(final Board board) {
	JFrame frame = new JFrame("Chess");
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	Display display = new Display(board, 64, "images/spritesheet.png");
	Mouse mouse = new Mouse(board, display, 64);

	display.addMouseListener(mouse);
	frame.add(display);

	frame.pack();
	frame.setVisible(true);
    }
}
