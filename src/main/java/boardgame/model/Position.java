package boardgame.model;

public record Position(int row, int col) {

    public Position moveTo(Direction direction){
        return new Position(row +direction.getRowChange(), col + direction.getColChange());
    }

    @Override
    public String toString(){return String.format("(%d,%d)", row, col);}
}
