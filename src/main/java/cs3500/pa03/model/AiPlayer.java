package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a single AI Player in the battleship game
 */
public class AiPlayer implements Player {

  private final String name;
  private final List<Coord> shotsTaken;
  private final List<Ship> listOfShips;
  private final Board aiBoard;
  private final Board playerBoard;
  private final Random random;
  private final List<Coord> coordsNotShot;
  private int shipsLeft;

  /**
   * Constructor used when creating an AI Player
   *
   * @param aiBoard     a board that represents an AI board
   * @param playerBoard a board that represents the player's board
   * @param random      a random used for testing
   */
  public AiPlayer(Board aiBoard, Board playerBoard, Random random) {
    this.name = "liangjimenez0";
    this.shotsTaken = new ArrayList<>();
    this.listOfShips = new ArrayList<>();
    this.aiBoard = aiBoard;
    this.playerBoard = playerBoard;
    this.random = random;
    this.coordsNotShot = new ArrayList<>();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    for (int i = 0; i < specifications.get(ShipType.CARRIER); i++) {
      listOfShips.add(new Ship(ShipType.CARRIER, new ArrayList<>(), true, random));
    }

    for (int i = 0; i < specifications.get(ShipType.BATTLESHIP); i++) {
      listOfShips.add(new Ship(ShipType.BATTLESHIP, new ArrayList<>(), true, random));
    }

    for (int i = 0; i < specifications.get(ShipType.DESTROYER); i++) {
      listOfShips.add(new Ship(ShipType.DESTROYER, new ArrayList<>(), true, random));
    }

    for (int i = 0; i < specifications.get(ShipType.SUBMARINE); i++) {
      listOfShips.add(new Ship(ShipType.SUBMARINE, new ArrayList<>(), true, random));
    }

    for (Ship s : listOfShips) {
      s.randomizeHorizontal();
      s.randomizePosition(listOfShips, width, height);
    }

    this.initializeAllShots(height, width);
    this.shipsLeft = listOfShips.size();

    return listOfShips;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {

    int size = shotsTaken.size();
    List<Coord> shots = new ArrayList<>();

    while (shotsTaken.size() != size + shipsLeft) {
      int x = random.nextInt(aiBoard.getCoords()[0].length);
      int y = random.nextInt(aiBoard.getCoords().length);
      Coord coord = new Coord(x, y);

      for (Coord c : coordsNotShot) {

        if (c.getPosX() == coord.getPosX() && c.getPosY() == coord.getPosY()
            && !shotsTaken.contains(coord)) {
          this.shotsTaken.add(coord);
          shots.add(coord);

          coordsNotShot.remove(c);
          break;
        }

      }

    }

    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    shipsLeft = 0;
    List<Coord> damage = new ArrayList<>();

    for (Ship ship : this.listOfShips) {
      for (Coord c : ship.getLocation()) {
        for (Coord shot : opponentShotsOnBoard) {
          if (shot.getPosX() == c.getPosX() && shot.getPosY() == c.getPosY()) {
            damage.add(shot);
          }
        }
      }
    }

    for (int i = 0; i < aiBoard.getCoords().length; i++) {
      for (int j = 0; j < aiBoard.getCoords()[i].length; j++) {
        for (Coord shots : opponentShotsOnBoard) {
          if (shots.getPosX() == aiBoard.getCoords()[i][j].getPosX()
              && shots.getPosY() == aiBoard.getCoords()[i][j].getPosY()) {
            aiBoard.getCoords()[i][j].changeMiss();
          }
        }
      }
    }

    for (Coord d : damage) {
      for (Ship s : listOfShips) {
        for (Coord c : s.getLocation()) {
          if (c.getPosX() == d.getPosX() && c.getPosY() == d.getPosY()) {
            c.changeHit();
          }
        }
      }
    }
    for (Ship s : listOfShips) {
      shipsLeft = shipsLeft + s.isShipSunk();
    }
    return damage;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

    for (Coord shots : shotsThatHitOpponentShips) {
      playerBoard.getCoords()[shots.getPosY()][shots.getPosX()].changeHit();
    }

  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {}

  private void initializeAllShots(int height, int width) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        coordsNotShot.add(new Coord(j, i));
      }
    }
  }
}