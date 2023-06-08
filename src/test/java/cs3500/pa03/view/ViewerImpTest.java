package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewerImpTest {

  Viewer viewer;
  Appendable output;

  /**
   * Sets up the variables before each test
   */
  @BeforeEach
  void setUp() {
    output = new StringBuilder();
    viewer = new ViewerImp(output);
  }

  @Test
  void welcomePage() throws IOException {
    viewer.welcomePage();

    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """, output.toString());
  }

  @Test
  void welcomePageInvalid() throws IOException {
    viewer.welcomePageInvalid();

    assertEquals("""
        ------------------------------------------------------
        Uh oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range (6, 15), inclusive. Try again!
        ------------------------------------------------------
        """, output.toString());

  }

  @Test
  void fleetSelection() throws IOException {
    viewer.fleetSelection(2);

    assertEquals(
        """
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
            Remember your fleet size may not exceed size 2
            ------------------------------------------------------
            """, output.toString());
  }

  @Test
  void fleetSelectionInvalid() throws IOException {
    viewer.fleetSelectionInvalid(3);

    assertEquals("""
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember your fleet size may not exceed size 3
        ------------------------------------------------------
        """, output.toString());
  }

  @Test
  void opponentBoardInitial() throws IOException {
    Board aiBoard = new Board();
    aiBoard.generateAllCoords(2, 2);

    aiBoard.getCoords()[0][0].changeHit();
    aiBoard.getCoords()[1][1].changeMiss();

    viewer.opponentBoard(aiBoard, 2, 2);

    assertEquals("""

        Opponent Board Data:
        H -\s
        - M\s

        """, output.toString());
  }

  @Test
  void playerBoard() throws IOException {
    Board playerBoard = new Board();
    playerBoard.generateAllCoords(6, 6);

    ArrayList<Coord> coords = new ArrayList<>();
    Coord c1 = new Coord(0, 0);
    Coord c2 = new Coord(1, 1);
    coords.add(c1);
    coords.add(c2);


    ArrayList<Coord> coords2 = new ArrayList<>();
    ArrayList<Coord> coords3 = new ArrayList<>();
    ArrayList<Coord> coords4 = new ArrayList<>();
    Coord c3 = new Coord(2, 2);
    Coord c4 = new Coord(3, 3);
    Coord c5 = new Coord(4, 4);
    coords2.add(c3);
    coords3.add(c4);
    coords4.add(c5);

    Ship ship = new Ship(ShipType.CARRIER, coords, true, new Random(1));
    Ship ship2 = new Ship(ShipType.BATTLESHIP, coords2, true, new Random(1));
    Ship ship3 = new Ship(ShipType.DESTROYER, coords3, true, new Random(1));
    Ship ship4 = new Ship(ShipType.SUBMARINE, coords4, true, new Random(1));

    ArrayList<Ship> ships = new ArrayList<>();
    ships.add(ship);
    ships.add(ship2);
    ships.add(ship3);
    ships.add(ship4);

    playerBoard.getCoords()[0][1].changeHit();
    playerBoard.getCoords()[0][2].changeMiss();

    viewer.playerBoard(playerBoard, ships, 6, 6);

    assertEquals("""
        Your Board:
        C H M - - -\s
        - C - - - -\s
        - - B - - -\s
        - - - D - -\s
        - - - - S -\s
        - - - - - -\s
        """, output.toString());
  }

  @Test
  void shotsAmount() throws IOException {
    viewer.shotsAmount(5);

    assertEquals("""

        Please Enter 5 Shots:
        ------------------------------------------------------
        """, output.toString());
  }

  @Test
  void endPageWin() throws IOException {
    viewer.endPage(GameResult.WIN);

    assertEquals("\n"
        + "Congratulations! You won this game of battleship", output.toString());
  }

  @Test
  void endPageLose() throws IOException {
    viewer.endPage(GameResult.LOSE);

    assertEquals("\n" + "Oh no! You lost this game of battleship", output.toString());
  }

  @Test
  void endPageTied() throws IOException {
    viewer.endPage(GameResult.TIED);

    assertEquals("\n"
        + "Looks like you and your opponent tied for this game of battleship", output.toString());
  }

  @Test
  void shotsInvalid() throws IOException {
    viewer.shotsInvalid(5);

    assertEquals("""

        Uh oh! You entered invalid inputs
        Please Enter 5 Shots:
        ------------------------------------------------------
        """, output.toString());
  }
}