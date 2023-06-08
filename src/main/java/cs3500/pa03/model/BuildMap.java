package cs3500.pa03.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a class BuildMap that is responsible for mapping the ship type to integer based
 * on the given numbers
 */
public class BuildMap {

  /**
   * Builds a hash map with the ship type as the key and the given value that corresponds with it
   *
   * @param carrier    the number of carriers on the board
   * @param battleship the number of battleships on the board
   * @param destroyer  the number of destroyers on the board
   * @param submarine  the number of submarines on the board
   * @return a hash map with the given values that corresponds to the ship type
   */
  public Map<ShipType, Integer> buildMap(int carrier, int battleship, int destroyer,
                                         int submarine) {
    Map<ShipType, Integer> shipsOnBoard = new HashMap<>();

    shipsOnBoard.put(ShipType.CARRIER, carrier);
    shipsOnBoard.put(ShipType.BATTLESHIP, battleship);
    shipsOnBoard.put(ShipType.DESTROYER, destroyer);
    shipsOnBoard.put(ShipType.SUBMARINE, submarine);

    return shipsOnBoard;
  }

}
