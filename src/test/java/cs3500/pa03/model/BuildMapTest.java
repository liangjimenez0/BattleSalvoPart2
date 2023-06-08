package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class BuildMap and all respective methods
 */
class BuildMapTest {

  BuildMap buildMap;

  /**
   * Sets up variables before each test method in this class
   */
  @BeforeEach
  void setUp() {
    buildMap = new BuildMap();
  }

  /**
   * Tests the method buildMap() in that it builds a map according to the integers given
   */
  @Test
  void buildMap() {

    Map<ShipType, Integer> map = buildMap.buildMap(1, 2, 3, 4);

    assertEquals(1, map.get(ShipType.CARRIER));
    assertEquals(2, map.get(ShipType.BATTLESHIP));
    assertEquals(3, map.get(ShipType.DESTROYER));
    assertEquals(4, map.get(ShipType.SUBMARINE));

  }
}