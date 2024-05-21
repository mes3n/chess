package se.liu.marsv260.chess.pieces;

/**
 * Entity class representing a basic chess piece.
 * <p>
 * Contains information about the piece's color and type, e.g. a white rook.
 */
public class Entity
{
    private final ChessColor color;
    private final Type type;

    /**
     * Constructor of the Enity class.
     *
     * @param color the color of the created Entity.
     * @param type  the type of the created Entity.
     */
    public Entity(final ChessColor color, final Type type) {
	this.color = color;
	this.type = type;
    }

    /**
     * @return Entity's ChessColor.
     */
    public ChessColor getColor() {
	return color;
    }

    /**
     * @return Entity's Type.
     */
    public Type getType() {
	return type;
    }

    /**
     * Enumerator of an Entitiy's possible Colors. Entities can be black or white.
     */
    public enum ChessColor
    {
	WHITE(0), BLACK(1);

	/**
	 * value is used by spritesheet to find the correct tile.
	 */
	public final int value;

	ChessColor(int value) {
	    this.value = value;
	}
    }

    /**
     * Enumerator of an Entity's possible Types. Entities can be a king, queen, bishop, horse, rook or pawn.
     */
    public enum Type
    {
	KING(0), QUEEN(1), BISHOP(2), HORSE(3), ROOK(4), PAWN(5);

	/**
	 * value is used by spritesheet to find the correct tile.
	 */
	public final int value;

	Type(int value) {
	    this.value = value;
	}
    }
}
