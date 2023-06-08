package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.client.ProxyController;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EmptyJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private CoordJson coordJson;
  private List<CoordJson> coordJsonList;
  private VolleyJson volleyJson;
  private Board board1;
  private Board board2;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());

    coordJson = new CoordJson(0, 0);
    coordJsonList = new ArrayList<>();
    coordJsonList.add(coordJson);
    volleyJson = new VolleyJson(coordJsonList);


    board1 = new Board();
    board1.generateAllCoords(6, 6);
    board2 = new Board();
    board2.generateAllCoords(6, 6);
  }

  /**
   * Check that the server returns a MessageJson with JoinJson when given the request to join server
   */
  @Test
  public void testJoin() {
    JoinJson joinJson = new JoinJson("liangjimenez0", GameType.SINGLE);
    JsonNode jsonNode = createSampleMessage("join", joinJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));
    System.out.print("node " + List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, new Board(), new Board(), new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("join", JoinJson.class);
  }

  /**
   * Check that the server returns a MessageJson with FleetJson when given the SetupJson request
   */
  @Test
  public void testSetUp() {
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.BATTLESHIP, 4);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.CARRIER, 2);
    map.put(ShipType.SUBMARINE, 3);
    SetupJson setUpJson = new SetupJson(10, 10, map);
    JsonNode jsonNode = createSampleMessage("setup", setUpJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, new Board(), new Board(), new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("setup", FleetJson.class);
  }

  /**
   * Check that the server returns a MessageJson with VolleyJson when given the request to take
   * shots
   */
  @Test
  public void testTakeShots() {
    EmptyJson emptyJson = new EmptyJson();
    JsonNode jsonNode = createSampleMessage("take-shots", emptyJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, new Board(), new Board(), new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("take-shots", VolleyJson.class);
  }

  /**
   * Check that the server returns a MessageJson with VolleyJson when given the request to report
   * damage
   */
  @Test
  public void testReportDamage() {
    JsonNode jsonNode = createSampleMessage("report-damage", volleyJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.controller = new ProxyController(socket, board1, board2, new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("report-damage", VolleyJson.class);
  }

  /**
   * Check that the server returns a MessageJson with EmptyJson when given the request to
   * successful hits
   */
  @Test
  public void testSuccessfulHits() {
    JsonNode jsonNode = createSampleMessage("successful-hits", volleyJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, board1, board2, new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("successful-hits", EmptyJson.class);
  }

  /**
   * Check that the server returns a MessageJson with EmptyJson when given the request to end game
   * with a win
   */
  @Test
  public void testEndGameWin() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You won!");
    JsonNode jsonNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    Board board1 = new Board();
    board1.generateAllCoords(6, 6);
    Board board2 = new Board();
    board2.generateAllCoords(6, 6);

    try {
      this.controller = new ProxyController(socket, board1, board2, new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("end-game", EmptyJson.class);
  }

  /**
   * Check that the server returns a MessageJson with EmptyJson when given the request to end game
   * with a tie
   */
  @Test
  public void testEndGameTie() {
    EndGameJson endGameJson = new EndGameJson(GameResult.TIED, "You tied!");
    JsonNode jsonNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, board1, board2, new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("end-game", EmptyJson.class);
  }

  /**
   * Check that the server returns a MessageJson with EmptyJson when given the request to end game
   * with a loss
   */
  @Test
  public void testEndGameLose() {
    EndGameJson endGameJson = new EndGameJson(GameResult.LOSE, "You lost!");
    JsonNode jsonNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.controller = new ProxyController(socket, board1, board2, new Random(1));
    } catch (IOException e) {
      fail();
    }

    this.controller.run();
    responseToClass("end-game", EmptyJson.class);
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class and asserts that the method
   * name matches the expected method name
   *
   * @param name     a string that represents the name of the Message Json
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(String name,
                                   @SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      MessageJson json = jsonParser.readValueAs(MessageJson.class);
      new ObjectMapper().convertValue(json.arguments(), classRef);
      assertEquals(name, json.messageName());
    } catch (IOException | IllegalArgumentException e) {
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
