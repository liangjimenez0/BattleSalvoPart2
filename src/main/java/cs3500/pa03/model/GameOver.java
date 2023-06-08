package cs3500.pa03.model;

import java.util.List;

/**
 * Represents a GameOver class that is responsible for determining when a game is over
 */
public class GameOver {

  /**
   * Outputs the game result when a game is over or null if the game is not over
   *
   * @param userShips     a list of all the player's ships
   * @param aiShips       a list of all the AI's ships
   * @return the game result when a game is over and null if it is not over
   */
  public GameResult isGameOver(List<Ship> userShips, List<Ship> aiShips) {

    boolean playerWins = true;
    boolean userWins = true;

    for (Ship s : userShips) {
      if (s.isShipSunk() == 1) {
        playerWins = false;
        break;
      }
    }

    for (Ship s : aiShips) {
      if (s.isShipSunk() == 1) {
        userWins = false;
        break;
      }
    }

    if (playerWins && userWins) {
      return GameResult.TIED;
    } else if (playerWins) {
      return GameResult.LOSE;
    } else if (userWins) {
      return GameResult.WIN;
    } else {
      return null;
    }
  }

}
