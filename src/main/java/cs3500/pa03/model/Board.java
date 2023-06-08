package cs3500.pa03.model;


/**
 * Represents a board used in the battleship game
 */
public class Board {

  private Coord[][] allCoords;


  /**
   * Updates the coordinates in the board to represent new coordinates
   * that are at the specified width and height
   *
   * @param width  an integer that represents the width of the board
   * @param height an integer that represents the height of the board
   */
  public void generateAllCoords(int width, int height) {
    this.allCoords = new Coord[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        allCoords[i][j] = new Coord(j, i);
      }
    }
  }

  /**
   * Gets the 2D array of coordinates that represent the board
   *
   * @return a 2D array of coordinates
   */
  public Coord[][] getCoords() {
    return this.allCoords;
  }

}
