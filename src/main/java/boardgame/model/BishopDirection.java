package boardgame.model;

public enum BishopDirection implements Direction {

    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1),

    UP2_LEFT2(-2, -2),
    UP2_RIGHT2(-2, 2),
    DOWN2_LEFT2(2, -2),
    DOWN2_RIGHT2(2, 2),

    UP3_LEFT3(-3, -3),
    UP3_RIGHT3(-3, 3),
    DOWN3_LEFT3(3, -3),
    DOWN3_RIGHT3(3, 3);

    private int rowChange;
    private int colChange;

    private BishopDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    @Override
    public int getRowChange() {
        return rowChange;
    }

    @Override
    public int getColChange() {
        return colChange;
    }

    public static BishopDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

}
