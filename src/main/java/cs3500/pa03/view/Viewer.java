package cs3500.pa03.view;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import java.io.IOException;
import java.util.List;

/**
 * Represents a Viewer interface which is responsible for outputting things into the console
 * based on user input
 */
public interface Viewer {

  /**
   * Displays the welcome page and asks the user for board dimensions
   *
   * @throws IOException throws an error if output does not exist
   */
  void welcomePage() throws IOException;

  /**
   * Outputs to user if they input the wrong dimensions into the system
   *
   * @throws IOException throws an error if output does not exist
   */
  void welcomePageInvalid() throws IOException;

  /**
   * Ask the users for their fleet selection in order of [Carrier, Battleship, Destroyer, Submarine]
   * with a size of the smaller length of the board
   *
   * @param size an integer that represents the size of the fleet selection
   * @throws IOException throws an error if output does not exist
   */
  void fleetSelection(int size) throws IOException;

  /**
   * Outputs to user if they input the wrong fleet size into the system
   *
   * @param size an integer that represents the size of the fleet selection
   * @throws IOException throws an error if output does not exist
   */
  void fleetSelectionInvalid(int size) throws IOException;

  /**
   * Outputs the AI's board based on what the user can see
   * and updates the hit or misses on the board based on the opponent's shots
   *
   * @param aiBoard a board that represents the ai's board
   * @param width   an integer that represents the width of the board
   * @param height  an integer that represents the height of the board
   * @throws IOException throws an error if output does not exist
   */
  void opponentBoard(Board aiBoard, int width, int height) throws IOException;

  /**
   * Outputs the player's board based on the fleet indicated by the user
   * and updates the hit or misses on the board based on the opponent's shots
   *
   * @param playerBoard a board that represents the player's board
   * @param userShips   a list of the ships on the player's board
   * @param width       an integer that represents the width of the board
   * @param height      an integer that represents the height of the board
   * @throws IOException throws an error if output does not exist
   */
  public void playerBoard(Board playerBoard, List<Ship> userShips, int width, int height)
      throws IOException;

  /**
   * Outputs to the players the amount of shots they can play each round
   *
   * @param amountOfShots the integer that represents the number of shots that can be shot
   * @throws IOException throws an error if output does not exist
   */
  void shotsAmount(int amountOfShots) throws IOException;

  /**
   * Outputs to the player the result of the battleship game
   *
   * @param result the decided result of the game the player just played
   * @throws IOException throws an error if output does not exist
   */
  void endPage(GameResult result) throws IOException;

  /**
   * Outputs to the players if they input an invalid shot
   *
   * @param amountOfShots the integer that represents the number of shots that can be shot
   * @throws IOException throws an error if output does not exist
   */
  void shotsInvalid(int amountOfShots) throws IOException;
}
