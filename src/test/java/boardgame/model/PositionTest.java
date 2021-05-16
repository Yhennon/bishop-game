package boardgame.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {



    @Test
    void moveTo() {
        Position testPosition1 = new Position(0,0);
        assertEquals(new Position(-1,-1),testPosition1.moveTo(BishopDirection.UP_LEFT));

        Position testPosition2 = new Position(2,2);
        assertEquals(new Position(1,-1),testPosition2.moveTo(BishopDirection.DOWN_LEFT));

        Position testPosition3 = new Position(1,0);
        assertEquals(new Position(1,-1),testPosition3.moveTo(BishopDirection.DOWN_LEFT));

        Position testPosition4 = new Position(0,1);
        assertEquals(new Position(1,-1),testPosition4.moveTo(BishopDirection.DOWN_LEFT));

        Position testPosition5 = new Position(0,0);
        assertEquals(new Position(1,-1),testPosition5.moveTo(BishopDirection.UP3_LEFT3));

        Position testPosition6 = new Position(2,0);
        assertEquals(new Position(5,3),testPosition6.moveTo(BishopDirection.DOWN3_RIGHT3));







    }

    @Test
    void testToString() {
    }
}