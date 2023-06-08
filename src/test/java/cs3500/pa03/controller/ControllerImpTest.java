package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Board;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the class ControllerImp and all respective methods
 */
class ControllerImpTest {

  String string;
  Readable input;
  Appendable output;
  String welcomeMessage;
  String welcomeMessageInvalid;
  String fleetSelection;
  String fleetSelectionInvalid;
  String winner;
  String tied;
  String loser;

  /**
   * Sets up variables before each test method in this class
   *
   * @throws IOException Throws an IOException if an error occurs
   */
  @BeforeEach
  void setUp() {
    output = new StringBuilder();
    welcomeMessage = "Hello! Welcome to the OOD BattleSalvo Game!";
    welcomeMessageInvalid =
        "Uh oh! You've entered invalid dimensions. Please remember that the height and width";
    fleetSelection =
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].";
    fleetSelectionInvalid = "Uh Oh! You've entered invalid fleet sizes.";
    winner = "Congratulations! You won this game of battleship";
    loser = "Oh no! You lost this game of battleship";
    tied = "Looks like you and your opponent tied for this game of battleship";
  }

  /**
   * Tests the run() method when the Battleship game ends in a tie
   *
   * @throws IOException Throws an IOException if an error occurs
   */
  @Test
  void runTied() throws IOException {
    string = "8 8" + System.lineSeparator() + "1 1 1 1" + System.lineSeparator();
    input = new StringReader(string);
    Controller controller =
        new ControllerImp(input, output);

    assertEquals("", output.toString());
    controller.run();
    assertTrue(output.toString().contains(welcomeMessage));
    assertTrue(output.toString().contains(fleetSelection));
    assertTrue(output.toString().contains(tied));
  }

  /**
   * Tests the run() method when the Battleship game ends in a player win
   *
   * @throws IOException Throws an IOException if an error occurs
   */
  @Test
  void runPlayerWins() throws IOException {
    string = "8 8" + System.lineSeparator() + "1 1 1 1" + System.lineSeparator() + "0 0"
        + System.lineSeparator() + "1 1" + System.lineSeparator() + "2 2" + System.lineSeparator()
        + "3 3" + System.lineSeparator();
    input = new StringReader(string);
    Controller controller =
        new ControllerImp(input, output, new Board(), new Board());

    assertEquals("", output.toString());
    controller.run();
    assertTrue(output.toString().contains(welcomeMessage));
    assertTrue(output.toString().contains(fleetSelection));
    assertTrue(output.toString().contains(winner));
  }

  /**
   * Tests the run() method when the Battleship game ends in a player loss
   *
   * @throws IOException Throws an IOException if an error occurs
   */
  @Test
  void runPlayerLoses() throws IOException {
    string = "20 20" + System.lineSeparator() + "8 8" + System.lineSeparator() + "5 5 5 5"
        + System.lineSeparator() + "1 1 1 1" + System.lineSeparator();
    input = new StringReader(string);
    Controller controller =
        new ControllerImp(input, output, new Board(), new Board(), new Random(1));

    assertEquals("", output.toString());
    controller.run();
    assertTrue(output.toString().contains(welcomeMessage));
    assertTrue(output.toString().contains(welcomeMessageInvalid));
    assertTrue(output.toString().contains(fleetSelection));
    assertTrue(output.toString().contains(fleetSelectionInvalid));
    assertTrue(output.toString().contains(loser));
  }


}