package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class Board and all respective methods
 */
class BoardTest {

  Board board;

  /**
   * Sets up variables before each test method in this class
   */
  @BeforeEach
  void setUp() {
    board = new Board();
  }

  /**
   * Tests the method generateAllCoords() in that it generates a 2D array according to the width
   * and height
   */
  @Test
  void generateAllCoords() {

    board.generateAllCoords(5, 6);

    assertEquals(6, board.getCoords().length);
    assertEquals(5, board.getCoords()[0].length);
    assertEquals(0, board.getCoords()[0][0].getPosX());
    assertEquals(0, board.getCoords()[0][0].getPosY());
  }

  /**
   * Tests the method getCoords() in that it returns the board's 2D array of coordinates
   */
  @Test
  void getCoords() {

    assertTrue(Objects.isNull(board.getCoords()));

    board.generateAllCoords(2, 3);

    assertEquals(3, board.getCoords().length);
    assertEquals(2, board.getCoords()[1].length);
  }
}