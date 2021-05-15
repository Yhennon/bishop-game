package boardgame.model;


import javafx.beans.property.ObjectProperty;

import java.util.*;

public class BoardGameModel {

    public static int BOARD_ROW_SIZE = 5;

    public static int BOARD_COLUMN_SIZE = 4;

    private final Piece[] pieces;

    // Kezdeti Board state
    public BoardGameModel() {
        this(new Piece(PieceType.BLACK, new Position(0, 0)),
                new Piece(PieceType.BLACK, new Position(0, 1)),
                new Piece(PieceType.BLACK, new Position(0, 2)),
                new Piece(PieceType.BLACK, new Position(0, 3)),
                new Piece(PieceType.BLUE, new Position(4, 0)),
                new Piece(PieceType.BLUE, new Position(4, 1)),
                new Piece(PieceType.BLUE, new Position(4, 2)),
                new Piece(PieceType.BLUE, new Position(4, 3)));
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
        // Feltöltjük a validMoves halmazt a megadott piecenumber minden szabályos lépésével.
        // Ezt letesztellni mainben,a blokklás részt!
        for (var direction : BishopDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    // Adott Piece-t a piecesből elmozdítani.(Positionjét)
    public void move(int pieceNumber, BishopDirection direction) {
        pieces[pieceNumber].moveTo(direction);
    }


    // Az összes piece pozicioját visszaadja egy listában. a lista elemei az elemek pozicioja(n x m)
    // a Controller selectablePositions listájának a feltöltésénél lesz hasznos, a modelben létrehozott 8 bábu poziciojanak listazasahoz
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>(pieces.length);
        for (var piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }


    // Position alapján megmondja,melyik Piece-ről van szó.

    //A selectablePositions feltöltésénél lesz hasznos, amikor a kiválaszott elemről a positionje alapján kell megmondani,hogy melyik pieceről van szó.(pontosan, hogy a pieces lista hanyadik indexe)
    // Emellett, a Controllerben a handleClickOnSquare-nél lesz még hasznos, paraméternek a selected változót fogja kapni,
    // ami a SELECT_FROM fázisban kiválasztott  piece lesz.(board->boardon egy square-> squaren a piece)
    // Az OptionalInt típus nagyon szép dolog,mert ha null referenciat adunk at, akkor nem exception lesz hanem empty Optionalt adunk át
    public OptionalInt getPieceNumber(Position position){
      for (int i = 0; i < pieces.length; i++) {
          if (pieces[i].getPosition().equals(position)){
              return OptionalInt.of(i);
          }
      }
      return OptionalInt.empty();
    }

    // a Model kiprinteléséhez
    // a piece toString() metodusa a piecet pedig PIECETYPE[(POZITIONSOR,POZITIONOSZLOP)] formaban adja vissza.
    @Override
    public String toString(){
        StringJoiner joiner = new StringJoiner(",","[","]");
        for (var piece : pieces){
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        System.out.println(model);
        model.move(0,BishopDirection.DOWN2_RIGHT2);
        System.out.println(model);
    }

}
