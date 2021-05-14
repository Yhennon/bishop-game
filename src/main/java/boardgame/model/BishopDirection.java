package boardgame.model;

public enum BishopDirection implements Direction{

    UP_LEFT(-1,-1),
    UP_RIGHT(-1,1),
    DOWN_LEFT(1,-1),
    DOWN_RIGHT(1,1);

    private int rowChange;
    private int colChange;

    private BishopDirection(int rowChange,int colChange){
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

    public static void main(String[] args) {
        System.out.println(of(1, 1));
    }

}
