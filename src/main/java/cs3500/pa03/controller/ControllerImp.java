package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.BuildMap;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameOver;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.MockPlayer;
import cs3500.pa03.model.MockPlayerNoShots;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Viewer;
import cs3500.pa03.view.ViewerImp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a ControllerImp class which is responsible for running the Battleship game
 */
public class ControllerImp implements Controller {

  private final Readable input;
  private final Appendable output;
  Viewer viewer;
  Scanner scanner;
  Player userPlayer;
  Player aiPlayer;
  Board playerBoard;
  Board opponentBoard;
  GameOver gameOver;
  Random random;

  /**
   * Constructor to represent an instance of ControllerImpl
   *
   * @param input         A readable input given by the user
   * @param output        An appendable output that is appended to throughout the method
   * @param random        A random used for testing
   * @param playerBoard   A board that represents the player's board
   * @param opponentBoard A board that represents the AI's board
   */
  public ControllerImp(Readable input, Appendable output, Random random, Board playerBoard,
                       Board opponentBoard) {
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.viewer = new ViewerImp(output);
    this.scanner = new Scanner(input);
    this.userPlayer = new ManualPlayer(playerBoard, opponentBoard, random);
    this.aiPlayer = new AiPlayer(opponentBoard, playerBoard, random);
    this.playerBoard = playerBoard;
    this.opponentBoard = opponentBoard;
    this.gameOver = new GameOver();
    this.random = random;
  }

  /**
   * Constructor to represent an instance of ControllerImpl used
   * for testing if the player wins
   *
   * @param input         A readable input given by the user
   * @param output        An appendable output that is appended to throughout the method
   * @param playerBoard   A board that represents the player's board
   * @param opponentBoard A board that represents the AI's board
   */
  public ControllerImp(Readable input, Appendable output, Board playerBoard,
                       Board opponentBoard) {
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.viewer = new ViewerImp(output);
    this.scanner = new Scanner(input);
    this.playerBoard = playerBoard;
    this.opponentBoard = opponentBoard;
    this.userPlayer = new ManualPlayer(playerBoard, opponentBoard, new Random());
    this.aiPlayer = new MockPlayer(playerBoard, opponentBoard);
    this.gameOver = new GameOver();
    this.random = new Random(1);
  }

  /**
   * Constructor to represent an instance of ControllerImpl used
   * for testing if the player loses
   *
   * @param input         A readable input given by the user
   * @param output        An appendable output that is appended to throughout the method
   * @param playerBoard   A board that represents the player's board
   * @param opponentBoard A board that represents the AI's board
   * @param random        A random used for testing
   */
  public ControllerImp(Readable input, Appendable output, Board playerBoard,
                       Board opponentBoard, Random random) {
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.viewer = new ViewerImp(output);
    this.scanner = new Scanner(input);
    this.userPlayer = new MockPlayerNoShots();
    this.aiPlayer = new AiPlayer(opponentBoard, playerBoard, random);
    this.playerBoard = new Board();
    this.opponentBoard = new Board();
    this.gameOver = new GameOver();
    this.random = random;
  }

  /**
   * Constructor to represent an instance of ControllerImpl used
   * for testing if the player and AI tie
   *
   * @param input  A readable input given by the user
   * @param output An appendable output that is appended to throughout the method
   */
  public ControllerImp(Readable input, Appendable output) {
    this.input = Objects.requireNonNull(input);
    this.output = Objects.requireNonNull(output);
    this.viewer = new ViewerImp(output);
    this.scanner = new Scanner(input);
    this.userPlayer = new MockPlayerNoShots();
    this.aiPlayer = new MockPlayerNoShots();
    this.playerBoard = new Board();
    this.opponentBoard = new Board();
    this.gameOver = new GameOver();
    this.random = new Random(1);
  }

  /**
   * Takes in the player's system input and delegates them to classes to run the game of battleship
   *
   * @throws IOException throws an IO exception if an error occurs
   */
  @Override
  public void run() throws IOException {
    viewer.welcomePage();

    int boardHeight = scanner.nextInt();
    int boardWidth = scanner.nextInt();

    while (!correctDimensions(boardWidth, boardHeight)) {
      boardHeight = scanner.nextInt();
      boardWidth = scanner.nextInt();
    }

    viewer.fleetSelection(Math.min(boardWidth, boardHeight));

    int carrier = scanner.nextInt();
    int battleship = scanner.nextInt();
    int destroyer = scanner.nextInt();
    int submarine = scanner.nextInt();
    int size = Math.min(boardWidth, boardHeight);

    while (!correctFleetSize(carrier, battleship, destroyer, submarine, size)) {
      carrier = scanner.nextInt();
      battleship = scanner.nextInt();
      destroyer = scanner.nextInt();
      submarine = scanner.nextInt();
    }

    Map<ShipType, Integer> shipsOnBoard =
        new BuildMap().buildMap(carrier, battleship, destroyer, submarine);
    playerBoard.generateAllCoords(boardWidth, boardHeight);
    opponentBoard.generateAllCoords(boardWidth, boardHeight);

    List<Ship> userShips = userPlayer.setup(boardHeight, boardWidth, shipsOnBoard);
    List<Ship> aiShips = aiPlayer.setup(boardHeight, boardWidth, shipsOnBoard);

    while (gameOver.isGameOver(userShips, aiShips) == null) {
      makeShots(opponentBoard, playerBoard, userShips, boardWidth, boardHeight);
    }

    viewer.endPage(gameOver.isGameOver(userShips, aiShips));
  }

  /**
   * Determines if the dimensions given by the user are within the range and
   * outputs the appropriate message
   *
   * @param width  the width of the board as indicated by the user
   * @param height the height of the board as indicated by the user
   * @return a boolean value of whether the initial values were correct
   */
  private boolean correctDimensions(int width, int height)
      throws IOException {
    boolean correctDimensions = true;

    if (!(width >= 6 && height >= 6 && width <= 15 && height <= 15)) {
      viewer.welcomePageInvalid();
      correctDimensions = false;
    }

    return correctDimensions;
  }

  /**
   * Determines if the fleet size given by the user adds up to the size necessary
   * and outputs the appropriate message
   *
   * @param carrier    the number of carriers as indicated by the user
   * @param battleship the number of battleships as indicated by the user
   * @param destroyer  the number of destroyers as indicated by the user
   * @param submarine  the number of submarines as indicated by the user
   * @param size       the necessary size of the fleet size
   * @return a boolean value of whether the initial values were correct
   */
  private boolean correctFleetSize(int carrier, int battleship, int destroyer, int submarine,
                                   int size) throws IOException {
    boolean correctFleetSize = true;

    if (!(carrier + battleship + destroyer + submarine <= size) || !(carrier > 0)
        || !(battleship > 0) || !(destroyer > 0) || !(submarine > 0)) {
      viewer.fleetSelectionInvalid(size);
      correctFleetSize = false;
    }

    return correctFleetSize;
  }

  /**
   * Occurs each time until the game is over, processes the shots from both the player and ai
   * and updates the game state and board accordingly
   *
   * @param opponentBoard a board that represents the AI's board
   * @param playerBoard   a board that represents the player's board
   * @param userShips     a list of ships on the player's board
   * @param boardWidth    an integer that represents the width of the board
   * @param boardHeight   an integer that represents the height of the board
   * @throws IOException throws an IO exception if an error occurs
   */
  private void makeShots(Board opponentBoard, Board playerBoard, List<Ship> userShips,
                         int boardWidth, int boardHeight)
      throws IOException {
    viewer.opponentBoard(opponentBoard, boardWidth, boardHeight);
    viewer.playerBoard(playerBoard, userShips, boardWidth, boardHeight);
    int shipsLeft = 0;
    for (Ship s : userShips) {
      shipsLeft = shipsLeft + s.isShipSunk();
    }
    viewer.shotsAmount(shipsLeft);
    int x = 0;
    int y = 0;
    List<Coord> allCoords = new ArrayList<>();
    for (int i = 0; i < shipsLeft; i++) {
      x = scanner.nextInt();
      y = scanner.nextInt();
      Coord coord = new Coord(x, y);

      allCoords.add(coord);
    }
    while (!validShots(boardWidth, boardHeight, allCoords, shipsLeft)) {
      allCoords = new ArrayList<>();
      for (int i = 0; i < shipsLeft; i++) {
        x = scanner.nextInt();
        y = scanner.nextInt();
        Coord coord = new Coord(x, y);
        allCoords.add(coord);
      }
    }
    userPlayer.takeShots().addAll(allCoords);
    List<Coord> userShots = userPlayer.takeShots();
    List<Coord> aiShots = aiPlayer.takeShots();
    List<Coord> damageToUser = userPlayer.reportDamage(aiShots);
    List<Coord> damageToAi = aiPlayer.reportDamage(userShots);
    userPlayer.successfulHits(damageToAi);
    aiPlayer.successfulHits(damageToUser);
  }

  /**
   * Determines if the shots inputted by the user are valid and
   * updates the console if invalid
   *
   * @param boardWidth  an integer that represents the width of the board
   * @param boardHeight an integer that represents the height of the board
   * @param coord       a list of Coord that represent the player's shots
   * @return whether the shots inputted by the user are valid
   * @throws IOException throws an IO exception if an error occurs
   */
  private boolean validShots(int boardWidth,
                             int boardHeight, List<Coord> coord,
                             int shipsLeft)
      throws IOException {
    boolean correctShots = true;

    for (Coord c : coord) {
      if (!(c.getPosX() >= 0) || !(c.getPosX() < boardWidth)
          || !(c.getPosY() >= 0) || !(c.getPosY() < boardHeight)) {
        viewer.shotsInvalid(shipsLeft);
        correctShots = false;
      }
    }

    return correctShots;
  }

}
