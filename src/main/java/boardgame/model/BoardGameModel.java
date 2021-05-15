package boardgame.model;


import javafx.beans.property.ObjectProperty;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class BoardGameModel {

    public static int BOARD_ROW_SIZE = 5;

    public static int BOARD_COLUMN_SIZE = 4;

    private Piece[] pieces;

    // Kezdeti Board state
    public BoardGameModel() {
        this(new Piece(PieceType.BLACK, new Position(0, 0)),
                new Piece(PieceType.BLACK, new Position(0, 1)),
                new Piece(PieceType.BLACK, new Position(0, 2)),
                new Piece(PieceType.BLACK, new Position(0, 3)),
                new Piece(PieceType.WHITE, new Position(4, 0)),
                new Piece(PieceType.WHITE, new Position(4, 1)),
                new Piece(PieceType.WHITE, new Position(4, 2)),
                new Piece(PieceType.WHITE, new Position(4, 3)));
    }

    // Szükséges a paraméter nélküli konstruktorhoz.
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

    // A megadott position a lehetséges n x m mátrixban van e.
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

    // a positionProperty() kicsit összezavaró. ez egy "getter", egy Piece Positionjét adja vissza.
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    // Szabályos lépés vizsgálata
    public boolean isValidMove(int pieceNumber, BishopDirection direction) {
        // Valid-e a Piece száma. (a pieces nevü Piece[] listában számon van tartva az összes Piece, 0-7 ig. Ha nem ezek közötti a pieceNumber,akkor valami baki van.
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }

        // Ezután, megadott piece-el lépünk a megadott irányba. Megvizsgáljuk, hogy továbbra is a boardon maradunk e.
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (!isOnBoard(newPosition)) {
            return false;
        }

        // Ezután, az ütközést vizsgáljuk meg. Ha a piece (amivel lépnük) új pozíciója megegyezik bármelyik másik piece pozíciójával, akkor nem valid a lépés,mert az a pozíció már "blokkolva van".
        for (var piece : pieces) {
            if (piece.getPosition().equals(newPosition)) {
                return false;
            }
        }

        return true;
    }

    // Használjunk matematikai halmazt,mert akkor fixen nem lesz többször ugyanaz a move a lehetséges lépésekben.
    public Set<BishopDirection> getValidMoves(int pieceNumber) {
        //kinullázzuk a validMoves halmazt
        EnumSet<BishopDirection> validMoves = EnumSet.noneOf(BishopDirection.class);
        //todo
        return null;
    }


}
