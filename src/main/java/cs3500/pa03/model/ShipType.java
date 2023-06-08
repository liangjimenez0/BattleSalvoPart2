package cs3500.pa03.model;

/**
 * Represents the possible ship types used in this program
 */
public enum ShipType {

  /**
   * ShipType can be either carrier, battleship, destroyer, or submarine
   */
  CARRIER(6), BATTLESHIP(5), DESTROYER(4), SUBMARINE(3);

  private final int size;

  /**
   * Constructor used for the enum ShipType
   *
   * @param size the size of the type of ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Returns the size of a ship type
   *
   * @return an integer that represents the size
   */
  public int getSize() {
    return this.size;
  }
}
