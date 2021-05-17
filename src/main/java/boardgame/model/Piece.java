package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class represents a piece and its functions that can be put on the game board.
 */
public class Piece {
    private PieceType type;
    private ObjectProperty<Position> position = new SimpleObjectProperty<Position>();

    /**
     * Create a piece.
     * @param type is the {@code PieceType} of the {@code Piece}.
     * @param position is the {@code Position} of the {@code Piece}.
     */
    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * @return The {@code PieceType} of the {@code Piece}.
     */
    public PieceType getType() {
        return type;
    }

    /**
     *
     * @return The the {@code Position} of the {@code Piece}.
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * Move the {@code Piece} in a given {@code BishopDirection}.
     * @param direction the value of the {@code BishopDirection}.
     */
    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }

    /**
     * @return The position property of the {@code Piece}.
     */
    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * @return The {@code Piece} in a readable way.
     */
    @Override
    public String toString() {
        return type.toString() + position.get().toString();
    }

}
