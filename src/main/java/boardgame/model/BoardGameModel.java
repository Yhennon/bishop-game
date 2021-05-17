package boardgame.model;


import javafx.beans.property.ObjectProperty;

import java.util.*;

/**
 *  The class that represents the game model.
 */
public class BoardGameModel {


    public static int BOARD_ROW_SIZE = 5;

    public static int BOARD_COLUMN_SIZE = 4;

    private final Piece[] pieces;

    public int moveCount = 0;


    /**
     * The enum representing two types of players.
     */
    //CHECKSTYLE:OFF
    private enum Player {
        PLAYER1,
        PLAYER2;

        /**
         * The Player enum's only method, switches between the two players.
         * @return the other PLAYER. For PLAYER1 return PLAYER2. For PLAYER2 return PLAYER1.
         */
        public Player alter() {
            return switch (this) {
                case PLAYER1 -> PLAYER2;
                case PLAYER2 -> PLAYER1;
            };
        }
    }

    private Player nextPlayer;

    private boolean goalState;

    /**
     * Creates the pieces on their positions. Sets the Player to PLAYER1. Initializes goal state.
     */
    public BoardGameModel() {
        this(new Piece(PieceType.BLACK, new Position(0, 0)),
                new Piece(PieceType.BLACK, new Position(0, 1)),
                new Piece(PieceType.BLACK, new Position(0, 2)),
                new Piece(PieceType.BLACK, new Position(0, 3)),
                new Piece(PieceType.WHITE, new Position(4, 0)),
                new Piece(PieceType.WHITE, new Position(4, 1)),
                new Piece(PieceType.WHITE, new Position(4, 2)),
                new Piece(PieceType.WHITE, new Position(4, 3)));
        this.nextPlayer = Player.PLAYER1;
        this.goalState = false;
    }

    /**
     * Required for the no params constructor.
     * @param pieces
     */
    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    /**
     * Checks the pieces.
     * @param pieces the given pieces list.
     */
    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (!isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    /**
     * Checks if a given {@code Position} is within the board's limits.
     * @param position The given {@code Position}.
     * @return true if given {@code Position} is valid, false if not.
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_ROW_SIZE
                && 0 <= position.col() && position.col() < BOARD_COLUMN_SIZE;
    }

    /**
     * Getter for returning the class's number of pieces.
     * @return the length of the pieces list.
     */
    public int getPieceCount() {
        return pieces.length;
    }

    /**
     * Function that returns the {@code PieceType} of a given piece in the pieces list.
     * @param pieceNumber the index of the piece in the pieces list.
     * @return the {@code PieceType} of the pieceNumber.
     */
    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    /**
     * Function that returns the {@code Position} of a given piece in the pieces list.
     * @param pieceNumber the index of the piece in the pieces list.
     * @return the {@code Position} of the pieceNumber.
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    /**
     * Provides access for the {@code Position} property.
     * @param pieceNumber the index of the piece in the pieces list.
     * @return the property of the pieceNumber.
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    /**
     * This function checks if the given piece is able to move in a given direction.
     * @param pieceNumber the index of the piece in the pieces list.
     * @param direction the direction of the piece to
     * @return true if the new {@code Position} of the piece is valid, meaning that the piece stays on board, or is not trying to move to a position that is already taken by another Piece and is an existing piece. Otherwise return false.
     */
    public boolean isValidMove(int pieceNumber, BishopDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (!isOnBoard(newPosition)) {
            return false;
        }
        for (var piece : pieces) {
            if (piece.getPosition().equals(newPosition)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function returns all the valid moves of a piece.
     * @param pieceNumber the index of the piece in the pieces list.
     * @return an EnumSet with the possible {@code BishopDirection}s of the given piece.
     */
    public Set<BishopDirection> getValidMoves(int pieceNumber) {
        EnumSet<BishopDirection> validMoves = EnumSet.noneOf(BishopDirection.class);
        for (var direction : BishopDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     *  This method moves the given piece in a given {@code BishopDirection}.
     * @param pieceNumber the index of the piece in the pieces list.
     * @param direction the {@code BishopDirection} to move the piece in.
     */
    public void move(int pieceNumber, BishopDirection direction) {
        pieces[pieceNumber].moveTo(direction);
        goalState = checkWinCondition();
        nextPlayer = nextPlayer.alter();
    }

    /**
     * This function returns whether the game has reached it's goal state.
     * @return false by default. The goal state is checked by the {@code checkWinCondition()}.
     */
    public boolean isGoalStateReached() {
        return goalState;
    }

    /**
     * This function checks if the win condition is met.
     * @return true if all the pieces with BLACK {@code PieceType} are in the last row, and all the pieces with WHITE {@code PieceType} are in the first row. Return false otherwise.
     */
    public boolean checkWinCondition() {
        for (var piece : pieces) {
            switch (piece.getType()) {
                case BLACK -> {
                    if (piece.getPosition().row() != BOARD_ROW_SIZE - 1) {
                        return false;
                    }
                }
                case WHITE -> {
                    if (piece.getPosition().row() != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This function returns every pieces position as an ArrayList.
     * @return the List of {@code Position}s of the pieces in the pieces list.
     */
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>(pieces.length);
        for (var piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }

    /**
     * @param position the {@code Position} to search a piece by.
     * @return The piece whose {@code Position} is the same as the given {@code Position}.
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * @return The number of moves that were taken to reach the goal state.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * This method increments the number of moves that were needd to reach goal state.
     */
    public void incrementMoveCount() {
        moveCount++;
    }

    /**
     * @return The game modell in a readable way.
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }
}
