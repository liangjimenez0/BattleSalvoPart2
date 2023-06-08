package cs3500.pa03.view;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.util.List;

/**
 * Represents the ViewerImp class that outputs data from the controller to the user
 * through the console
 */
public class ViewerImp implements Viewer {

  private final Appendable output;

  /**
   * Represents the output that will be show to the user via the console
   *
   * @param output An appendable element
   */
  public ViewerImp(Appendable output) {
    this.output = output;
  }

  /**
   * Displays the welcome page and asks the user for board dimensions
   *
   * @throws IOException throws an error if output does not exist
   */
  @Override
  public void welcomePage() throws IOException {
    output.append("Hello! Welcome to the OOD BattleSalvo Game!").append(System.lineSeparator());
    output.append("Please enter a valid height and width below:").append(System.lineSeparator());
    output.append("------------------------------------------------------")
        .append(System.lineSeparator());
  }

  /**
   * Outputs to user if they input the wrong dimensions into the system
   *
   * @throws IOException throws an error if output does not exist
   */
  public void welcomePageInvalid() throws IOException {
    output.append("------------------------------------------------------")
        .append(System.lineSeparator());
    output.append(
            "Uh oh! You've entered invalid dimensions. Please remember that the height and width")
        .append(System.lineSeparator());
    output.append("of the game must be in the range (6, 15), inclusive. Try again!")
        .append(System.lineSeparator());
    output.append("------------------------------------------------------")
        .append(System.lineSeparator());
  }

  /**
   * Ask the users for their fleet selection in order of [Carrier, Battleship, Destroyer, Submarine]
   * with a size of the smaller length of the board
   *
   * @param size an integer that represents the size of the fleet selection
   * @throws IOException throws an error if output does not exist
   */
  @Override
  public void fleetSelection(int size) throws IOException {
    output.append(
            "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].")
        .append(System.lineSeparator());
    output.append("Remember your fleet size may not exceed size ").append(Integer.toString(size))
        .append(System.lineSeparator());
    output.append("------------------------------------------------------")
        .append(System.lineSeparator());
  }

  /**
   * Outputs to user if they input the wrong fleet size into the system
   *
   * @param size an integer that represents the size of the fleet selection
   * @throws IOException throws an error if output does not exist
   */
  public void fleetSelectionInvalid(int size) throws IOException {
    output.append("Uh Oh! You've entered invalid fleet sizes.").append(System.lineSeparator());
    fleetSelection(size);
  }

  /**
   * Outputs the AI's board based on what the user can see
   * and updates the hit or misses on the board based on the opponent's shots
   *
   * @param aiBoard a board that represents the ai's board
   * @param width   an integer that represents the width of the board
   * @param height  an integer that represents the height of the board
   * @throws IOException throws an error if output does not exist
   */
  @Override
  public void opponentBoard(Board aiBoard, int width, int height) throws IOException {
    output.append(System.lineSeparator()).append("Opponent Board Data:")
        .append(System.lineSeparator());

    String[][] board = new String[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = "-";
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coord currentCoord = aiBoard.getCoords()[i][j];
        if (currentCoord.getIsHit()) {
          board[i][j] = "H";
        } else if (currentCoord.getIsMiss()) {
          board[i][j] = "M";
        }
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        output.append(board[i][j]).append(" ");
      }
      output.append(System.lineSeparator());
    }

    output.append(System.lineSeparator());
  }

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
  @Override
  public void playerBoard(Board playerBoard, List<Ship> userShips, int width, int height)
      throws IOException {
    output.append("Your Board:").append(System.lineSeparator());
    String[][] board = new String[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = "-";
      }
    }
    for (Ship s : userShips) {
      for (Coord c : s.getLocation()) {
        if (s.getShipType() == ShipType.CARRIER) {
          board[c.getPosY()][c.getPosX()] = "C";
        } else if (s.getShipType() == ShipType.DESTROYER) {
          board[c.getPosY()][c.getPosX()] = "D";
        } else if (s.getShipType() == ShipType.BATTLESHIP) {
          board[c.getPosY()][c.getPosX()] = "B";
        } else if (s.getShipType() == ShipType.SUBMARINE) {
          board[c.getPosY()][c.getPosX()] = "S";
        }
      }
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Coord currentCoord = playerBoard.getCoords()[i][j];
        if (currentCoord.getIsHit()) {
          board[i][j] = "H";
        } else if (currentCoord.getIsMiss()) {
          board[i][j] = "M";
        }
      }
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        output.append(board[i][j]).append(" ");
      }
      output.append(System.lineSeparator());
    }
  }

  /**
   * Outputs to the players the amount of shots they can play each round
   *
   * @param amountOfShots the integer that represents the number of shots that can be shot
   * @throws IOException throws an error if output does not exist
   */
  @Override
  public void shotsAmount(int amountOfShots) throws IOException {
    output.append(System.lineSeparator()).append("Please Enter ")
        .append(String.valueOf(amountOfShots)).append(" Shots:")
        .append(System.lineSeparator());
    output.append("------------------------------------------------------")
        .append(System.lineSeparator());
  }

  /**
   * Outputs to the player the result of the battleship game
   *
   * @param result the decided result of the game the player just played
   * @throws IOException throws an error if output does not exist
   */
  @Override
  public void endPage(GameResult result) throws IOException {

    if (result.equals(GameResult.WIN)) {
      output.append(System.lineSeparator())
          .append("Congratulations! You won this game of battleship");
    } else if (result.equals(GameResult.LOSE)) {
      output.append(System.lineSeparator()).append("Oh no! You lost this game of battleship");
    } else if (result.equals(GameResult.TIED)) {
      output.append(System.lineSeparator())
          .append("Looks like you and your opponent tied for this game of battleship");
    }

  }

  /**
   * Outputs to the players if they input an invalid shot
   *
   * @param amountOfShots the integer that represents the number of shots that can be shot
   * @throws IOException throws an error if output does not exist
   */
  @Override
  public void shotsInvalid(int amountOfShots) throws IOException {
    output.append(System.lineSeparator()).append("Uh oh! You entered invalid inputs");
    output.append(System.lineSeparator()).append("Please Enter ")
        .append(String.valueOf(amountOfShots)).append(" Shots:")
        .append(System.lineSeparator());
    output.append("------------------------------------------------------")
        .append(System.lineSeparator());
  }
}
