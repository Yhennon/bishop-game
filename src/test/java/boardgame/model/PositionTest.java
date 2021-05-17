package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position testPosition;

    @BeforeEach
    void init() {
        testPosition = new Position(0, 0);
    }

    @Test
    void moveTo() {
        assertEquals(new Position(-1, -1), testPosition.moveTo(BishopDirection.UP_LEFT));

        assertEquals(new Position(-1, 1), testPosition.moveTo(BishopDirection.UP_RIGHT));

        assertEquals(new Position(1, -1), testPosition.moveTo(BishopDirection.DOWN_LEFT));

        assertEquals(new Position(1, 1), testPosition.moveTo(BishopDirection.DOWN_RIGHT));

        assertEquals(new Position(-2, -2), testPosition.moveTo(BishopDirection.UP2_LEFT2));

        assertEquals(new Position(-2, 2), testPosition.moveTo(BishopDirection.UP2_RIGHT2));

        assertEquals(new Position(2, -2), testPosition.moveTo(BishopDirection.DOWN2_LEFT2));

        assertEquals(new Position(2, 2), testPosition.moveTo(BishopDirection.DOWN2_RIGHT2));

        assertEquals(new Position(-3, -3), testPosition.moveTo(BishopDirection.UP3_LEFT3));

        assertEquals(new Position(-3, 3), testPosition.moveTo(BishopDirection.UP3_RIGHT3));

        assertEquals(new Position(3, -3), testPosition.moveTo(BishopDirection.DOWN3_LEFT3));

        assertEquals(new Position(3, 3), testPosition.moveTo(BishopDirection.DOWN3_RIGHT3));
    }

    @Test
    void testToString() {
        assertEquals("(0,0)", testPosition.toString());

        Position testPosition1 = new Position(2, 3);
        assertEquals("(0,1)", testPosition1.moveTo(BishopDirection.UP2_LEFT2).toString());

        Position testPosition2 = new Position(1, 0);
        assertEquals("(4,3)", testPosition2.moveTo(BishopDirection.DOWN3_RIGHT3).toString());


    }
}