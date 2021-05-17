package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopDirectionTest {

    @Test
    void getRowChange() {
        assertEquals(-1, BishopDirection.UP_LEFT.getRowChange());

        assertEquals(-1, BishopDirection.UP_RIGHT.getRowChange());

        assertEquals(1, BishopDirection.DOWN_LEFT.getRowChange());

        assertEquals(1, BishopDirection.DOWN_RIGHT.getRowChange());

        assertEquals(-2, BishopDirection.UP2_LEFT2.getRowChange());

        assertEquals(-2, BishopDirection.UP2_RIGHT2.getRowChange());

        assertEquals(2, BishopDirection.DOWN2_LEFT2.getRowChange());

        assertEquals(2, BishopDirection.DOWN2_RIGHT2.getRowChange());

        assertEquals(-3, BishopDirection.UP3_LEFT3.getRowChange());

        assertEquals(-3, BishopDirection.UP3_RIGHT3.getRowChange());

        assertEquals(3, BishopDirection.DOWN3_LEFT3.getRowChange());

        assertEquals(3, BishopDirection.DOWN3_RIGHT3.getRowChange());

    }

    @Test
    void getColChange() {
        assertEquals(-1, BishopDirection.UP_LEFT.getColChange());

        assertEquals(1, BishopDirection.UP_RIGHT.getColChange());

        assertEquals(-1, BishopDirection.DOWN_LEFT.getColChange());

        assertEquals(1, BishopDirection.DOWN_RIGHT.getColChange());

        assertEquals(-2, BishopDirection.UP2_LEFT2.getColChange());

        assertEquals(2, BishopDirection.UP2_RIGHT2.getColChange());

        assertEquals(-2, BishopDirection.DOWN2_LEFT2.getColChange());

        assertEquals(2, BishopDirection.DOWN2_RIGHT2.getColChange());

        assertEquals(-3, BishopDirection.UP3_LEFT3.getColChange());

        assertEquals(3, BishopDirection.UP3_RIGHT3.getColChange());

        assertEquals(-3, BishopDirection.DOWN3_LEFT3.getColChange());

        assertEquals(3, BishopDirection.DOWN3_RIGHT3.getColChange());
    }

    @Test
    void of() {
        assertEquals(BishopDirection.UP_LEFT, BishopDirection.of(-1, -1));

        assertEquals(BishopDirection.UP_RIGHT, BishopDirection.of(-1, 1));

        assertEquals(BishopDirection.DOWN_LEFT, BishopDirection.of(1, -1));

        assertEquals(BishopDirection.DOWN_RIGHT, BishopDirection.of(1, 1));

        assertEquals(BishopDirection.UP2_LEFT2, BishopDirection.of(-2, -2));

        assertEquals(BishopDirection.UP2_RIGHT2, BishopDirection.of(-2, 2));

        assertEquals(BishopDirection.DOWN2_LEFT2, BishopDirection.of(2, -2));

        assertEquals(BishopDirection.DOWN2_RIGHT2, BishopDirection.of(2, 2));

        assertEquals(BishopDirection.UP3_LEFT3, BishopDirection.of(-3, -3));

        assertEquals(BishopDirection.UP3_RIGHT3, BishopDirection.of(-3, 3));

        assertEquals(BishopDirection.DOWN3_LEFT3, BishopDirection.of(3, -3));

        assertEquals(BishopDirection.DOWN3_RIGHT3, BishopDirection.of(3, 3));

    }

}