package boardgame.model;


import javafx.beans.property.ObjectProperty;

import java.util.*;

public class BoardGameModel {

    public static int BOARD_ROW_SIZE = 5;

    public static int BOARD_COLUMN_SIZE = 4;

    private final Piece[] pieces;

    public int moveCount = 0;

    public int gamesWon = 0;

    private enum Player {
        PLAYER1,
        PLAYER2;

        public Player alter() {
            return switch (this) {
                case PLAYER1 -> PLAYER2;
                case PLAYER2 -> PLAYER1;
            };
        }
    }

    private Player nextPlayer;
    private boolean goalState;

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

    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (!isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_ROW_SIZE
                && 0 <= position.col() && position.col() < BOARD_COLUMN_SIZE;
    }

    public int getPieceCount() {
        return pieces.length;
    }

    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

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

    public Set<BishopDirection> getValidMoves(int pieceNumber) {
        EnumSet<BishopDirection> validMoves = EnumSet.noneOf(BishopDirection.class);
        for (var direction : BishopDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public void move(int pieceNumber, BishopDirection direction) {
        pieces[pieceNumber].moveTo(direction);
        goalState = checkWinCondition();
        nextPlayer = nextPlayer.alter();
    }

    public boolean isGoalStateReached() {
        return goalState;
    }

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

    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>(pieces.length);
        for (var piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }

    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void incrementMoveCount() {
        moveCount++;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void incrementGamesWon() {
        gamesWon++;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }
}
