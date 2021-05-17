package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void getType() {
        PieceType testPieceType1 = PieceType.WHITE;
        PieceType testPieceType2 = PieceType.BLACK;
        Position testPosition = new Position(0, 0);

        Piece testPiece1 = new Piece(testPieceType1, testPosition);
        Piece testPiece2 = new Piece(testPieceType2, testPosition);

        assertEquals(PieceType.WHITE, testPiece1.getType());
        assertEquals(PieceType.BLACK, testPiece2.getType());
    }

    @Test
    void getPosition() {
        PieceType testPieceType = PieceType.WHITE;
        Position testPosition1 = new Position(3, 2);
        Position testPosition2 = new Position(0, 2);

        Piece testPiece1 = new Piece(testPieceType, testPosition1);
        Piece testPiece2 = new Piece(testPieceType, testPosition2);

        assertEquals(new Position(3, 2), testPiece1.getPosition());
        assertEquals(new Position(0, 2), testPiece2.getPosition());

    }

    @Test
    void testToString() {
        PieceType testPieceType = PieceType.WHITE;
        Position testPosition = new Position(3, 2);
        Piece testPiece1 = new Piece(testPieceType, testPosition);

        assertEquals("WHITE(3,2)", testPiece1.toString());
    }
}