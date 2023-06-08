package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.json.CoordJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class Coord and all respective methods
 */
class CoordTest {

  Coord coord1;
  Coord coord2;
  Coord coord3;

  /**
   * Sets up variables before each test method in this class
   */
  @BeforeEach
  void setUp() {
    coord1 = new Coord(0, 0);
    coord2 = new Coord(1, 1);
    coord3 = new Coord(2, 2);
  }

  /**
   * Tests the getX() method in that it returns the X coordinate of the Coord
   */
  @Test
  void getX() {
    assertEquals(0, coord1.getPosX());
    assertEquals(1, coord2.getPosX());
    assertEquals(2, coord3.getPosX());

  }

  /**
   * Tests the getY() method in that it returns the Y coordinate of the Coord
   */
  @Test
  void getY() {
    assertEquals(0, coord1.getPosY());
    assertEquals(1, coord2.getPosY());
    assertEquals(2, coord3.getPosY());
  }

  /**
   * Tests the getIsHit() method in that it returns the hit value of the Coord
   */
  @Test
  void getIsHit() {
    assertFalse(coord1.getIsHit());
    assertFalse(coord2.getIsHit());

    coord3.changeHit();

    assertTrue(coord3.getIsHit());
  }

  /**
   * Tests the getIsMiss() method in that it returns the miss value of the Coord
   */
  @Test
  void getIsMiss() {
    assertFalse(coord1.getIsMiss());
    assertFalse(coord2.getIsMiss());

    coord3.changeMiss();

    assertTrue(coord3.getIsMiss());
  }

  /**
   * Tests the changeHit() method in that it changes the value of the hit
   */
  @Test
  void changeHit() {
    assertFalse(coord1.getIsHit());
    assertFalse(coord2.getIsHit());

    coord1.changeHit();
    coord2.changeHit();

    assertTrue(coord1.getIsHit());
    assertTrue(coord2.getIsHit());
  }

  /**
   * Tests the changeMiss() method in that it changes the value of the miss
   */
  @Test
  void changeMiss() {
    assertFalse(coord1.getIsMiss());
    assertFalse(coord2.getIsMiss());

    coord1.changeMiss();
    coord2.changeMiss();

    assertTrue(coord1.getIsMiss());
    assertTrue(coord2.getIsMiss());
  }

  /**
   * Tests the coordJson() method in that it converts a coord object into a coord Json
   */
  @Test
  void coordJson() {
    CoordJson coord1Json = new CoordJson(0, 0);
    CoordJson coord2Json = new CoordJson(1, 1);
    CoordJson coord3Json = new CoordJson(2, 2);

    assertEquals(coord1Json, coord1.coordJson());
    assertEquals(coord2Json, coord2.coordJson());
    assertEquals(coord3Json, coord3.coordJson());
  }
}