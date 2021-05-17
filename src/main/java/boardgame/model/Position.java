package boardgame.model;

/**
 * A record class that represents a {@code Piece}'s position on the game board.
 */
public record Position(int row, int col) {

    /**
     * @param direction to move in.
     * @return A new {@code Position} for a {@code Piece} after moving in a {@code Direction}.
     */
    public Position moveTo(Direction direction){
        return new Position(row +direction.getRowChange(), col + direction.getColChange());
    }

    /**
     * @return The {@code Position} in a readable way.
     */
    @Override
    public String toString(){return String.format("(%d,%d)", row, col);}
}
