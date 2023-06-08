package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.Direction;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.ShipJson;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class Ship and all respective methods
 */
class ShipTest {

  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;
  Coord c5;
  Coord c6;
  ArrayList<Coord> coordList1;
  ArrayList<Coord> coordList2;
  ArrayList<Coord> coordList3;
  Ship playerShip1;
  Ship playerShip2;
  Ship playerShip3;
  List<Ship> playerShips;

  /**
   * Sets up variables before each test method in this class
   */
  @BeforeEach
  void setUp() {

    c1 = new Coord(0, 0);
    c2 = new Coord(1, 1);
    c3 = new Coord(2, 2);
    c4 = new Coord(3, 3);
    c5 = new Coord(4, 4);
    c6 = new Coord(5, 5);

    coordList1 = new ArrayList<>();
    coordList2 = new ArrayList<>();
    coordList3 = new ArrayList<>();

    coordList1.add(c1);
    coordList1.add(c2);
    coordList2.add(c3);
    coordList2.add(c4);
    coordList3.add(c5);
    coordList3.add(c6);

    playerShip1 = new Ship(ShipType.CARRIER, coordList1, true, new Random(2));
    playerShip2 = new Ship(ShipType.DESTROYER, coordList2, true, new Random(3));
    playerShip3 = new Ship(ShipType.SUBMARINE, coordList3, true, new Random(5));

    playerShips = new ArrayList<>();

    playerShips.add(playerShip1);
    playerShips.add(playerShip2);
    playerShips.add(playerShip3);
  }

  /**
   * Tests the randomizeHorizontal() method in that it randomizes whether a ship is horizontal
   */
  @Test
  void randomizeHorizontal() {

    playerShip1.randomizeHorizontal();
    playerShip2.randomizeHorizontal();

    assertFalse(playerShip1.getHorizontal());
    assertFalse(playerShip2.getHorizontal());
    assertTrue(playerShip3.getHorizontal());

  }

  /**
   * Tests the randomizePosition() method in that it randomizes the location of a ship
   */
  @Test
  void randomizePosition() {

    playerShip1.randomizePosition(playerShips, 10, 10);
    playerShip2.randomizePosition(playerShips, 8, 8);
    playerShip3.randomizePosition(playerShips, 9, 9);

    for (Coord c : playerShip1.getLocation()) {
      assertTrue(c.getPosX() < 10);
      assertTrue(c.getPosY() < 10);
      assertTrue(c.getPosX() >= 0);
      assertTrue(c.getPosY() >= 0);

      for (Coord coord : playerShip2.getLocation()) {
        assertTrue(coord.getPosX() != c.getPosX() || coord.getPosY() != c.getPosY());
      }
    }

    for (Coord c : playerShip2.getLocation()) {
      assertTrue(c.getPosX() < 8);
      assertTrue(c.getPosY() < 8);
      assertTrue(c.getPosX() >= 0);
      assertTrue(c.getPosY() >= 0);

      for (Coord coord : playerShip1.getLocation()) {
        assertTrue(coord.getPosX() != c.getPosX() || coord.getPosY() != c.getPosY());
      }
    }

    for (Coord c : playerShip3.getLocation()) {
      assertTrue(c.getPosX() < 9);
      assertTrue(c.getPosY() < 9);
      assertTrue(c.getPosX() >= 0);
      assertTrue(c.getPosY() >= 0);

      for (Coord coord : playerShip2.getLocation()) {
        assertTrue(coord.getPosX() != c.getPosX() || coord.getPosY() != c.getPosY());
      }
    }
  }

  /**
   * Tests the getLocation() method in that it returns the location of the ship
   */
  @Test
  void getLocation() {
    assertEquals(c1, playerShip1.getLocation().get(0));
    assertEquals(c3, playerShip2.getLocation().get(0));
    assertEquals(c5, playerShip3.getLocation().get(0));
  }

  /**
   * Tests the getShipType() method in that it returns the ship type of the ship
   */
  @Test
  void getShipType() {
    assertEquals(ShipType.CARRIER, playerShip1.getShipType());
    assertEquals(ShipType.DESTROYER, playerShip2.getShipType());
    assertEquals(ShipType.SUBMARINE, playerShip3.getShipType());
  }

  /**
   * Tests the shipJson() method that it correctly converts a ship object to a ship json
   */
  @Test
  void shipJson() {
    CoordJson c1Json = new CoordJson(0, 0);
    CoordJson c3Json = new CoordJson(2, 2);
    playerShip2 = new Ship(ShipType.DESTROYER, coordList2, false, new Random(3));

    ShipJson ship1Json = new ShipJson(c1Json, 6, Direction.HORIZONTAL);

    ShipJson ship2Json = new ShipJson(c3Json, 4, Direction.VERTICAL);

    assertEquals(ship1Json, playerShip1.shipJson());
    assertEquals(ship2Json, playerShip2.shipJson());
  }

  /**
   * Tests the isShipSunk() method in that it determines if a ship has sunk or not and
   * returns the correct integer associated with it
   */
  @Test
  void isShipSunk() {
    assertEquals(1, playerShip1.isShipSunk());

    for (Coord c : playerShip1.getLocation()) {
      c.changeHit();
    }

    assertEquals(0, playerShip1.isShipSunk());
  }
}