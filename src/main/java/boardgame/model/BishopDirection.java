package boardgame.model;

/**
 * An enum class that represents the possible directions.
 */
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

    /**
     * Constructor of the BishopDirection enum class.
     * @param rowChange set the rowChange to this value.
     * @param colChange set the colChange to this value.
     */
    private BishopDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * @return The rowChange value.
     */
    @Override
    public int getRowChange() {
        return rowChange;
    }

    /**
     * @return The colChange value.
     */
    @Override
    public int getColChange() {
        return colChange;
    }

    /**
     * @param rowChange the give change in rows.
     * @param colChange the give change in columns.
     * @return Which direction has has the same rowChange and colChange as the parameters.
     */
    public static BishopDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

}
