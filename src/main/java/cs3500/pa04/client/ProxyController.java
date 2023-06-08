package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa04.GameType;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EmptyJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.ShipJson;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController implements Controller {

  private final Socket server;
  private final Board aiBoard;
  private final Board playerBoard;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private final InputStream in;
  private final PrintStream out;
  private JsonNode jsonResponse;
  private MessageJson response;
  List<Ship> listOfShips;

  /**
   * Constructs an instance of a ProxyController
   *
   * @param server      the socket connection to the server
   * @param aiBoard     the instance of one aiBoard
   * @param playerBoard the instance of a playerBoard
   * @param random      a random for testing
   * @throws IOException throws an exception if an error occurs
   */
  public ProxyController(Socket server, Board aiBoard, Board playerBoard, Random random)
      throws IOException {
    this.server = server;
    this.aiBoard = aiBoard;
    this.playerBoard = playerBoard;
    this.player = new AiPlayer(aiBoard, playerBoard, random);
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    System.out.println(message);

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Parses the given message arguments as a EndGameJSON type, tells the user that the game is over
   * , and then serializes the player's response to the server.
   *
   * @param arguments the Json representation of a EndGameJson
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);

    player.endGame(endGameJson.result(), endGameJson.reason());

    JsonNode node = JsonUtils.serializeRecord(new EmptyJson());

    response = new MessageJson("end-game", node);

    jsonResponse = JsonUtils.serializeRecord(response);

    this.out.println(jsonResponse);
  }

  /**
   * Parses the given message arguments as a VolleyJSON type, tells the user what was successfully
   * hit, and then serializes the player's response to the server.
   *
   * @param arguments the Json representation of a VolleyJson
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    List<Coord> coords = convertCoords(arguments);

    player.successfulHits(coords);

    JsonNode node = JsonUtils.serializeRecord(new EmptyJson());

    response = new MessageJson("successful-hits", node);

    jsonResponse = JsonUtils.serializeRecord(response);

    this.out.println(jsonResponse);
  }

  /**
   * Parses the given message arguments as a VolleyJSON type, report which
   * shots hit a ship on this player's board, and then serializes the player's
   * response to the server.
   *
   * @param arguments the Json representation of a VolleyJson
   */
  private void handleReportDamage(JsonNode arguments) {

    List<Coord> coords = convertCoords(arguments);

    List<Coord> damage = player.reportDamage(coords);
    List<CoordJson> coordJsons = new ArrayList<>();

    for (Coord c : damage) {
      coordJsons.add(c.coordJson());
    }

    VolleyJson volleyJson = new VolleyJson(coordJsons);
    JsonNode node = JsonUtils.serializeRecord(volleyJson);
    response = new MessageJson("report-damage", node);

    jsonResponse = JsonUtils.serializeRecord(response);

    this.out.println(jsonResponse);

  }

  /**
   * Converts the given arguments into a list of Coords so that it can be read by methods
   *
   * @param arguments the Json representation of a VolleyJson
   * @return a list of converted coordinates
   */
  private List<Coord> convertCoords(JsonNode arguments) {
    VolleyJson volley = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> coords = new ArrayList<>();

    for (CoordJson c : volley.coordinates()) {
      Coord coord = new Coord(c.x(), c.y());
      coords.add(coord);
    }

    return coords;
  }

  /**
   * Produces the shots that a player takes on the opponent, and then serializes the player's
   * response to the server.
   */
  private void handleTakeShots() {

    List<Coord> coords = player.takeShots();
    List<CoordJson> coordJsons = new ArrayList<>();

    for (Coord c : coords) {
      coordJsons.add(c.coordJson());
    }

    VolleyJson volleyJson = new VolleyJson(coordJsons);
    JsonNode node = JsonUtils.serializeRecord(volleyJson);
    response = new MessageJson("take-shots", node);

    jsonResponse = JsonUtils.serializeRecord(response);

    this.out.println(jsonResponse);
  }

  /**
   * Parses the given message arguments as a SetupJSON type, set up each of the players boards
   * , and then serializes the player's response to the server.
   *
   * @param arguments the Json representation of a SetupJson
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupJson = this.mapper.convertValue(arguments, SetupJson.class);

    int width = setupJson.width();
    int height = setupJson.height();

    playerBoard.generateAllCoords(width, height);
    aiBoard.generateAllCoords(width, height);
    listOfShips = player.setup(height, width, setupJson.fleetSpec());

    List<ShipJson> listOfShipJson = new ArrayList<>();

    for (Ship s : listOfShips) {
      listOfShipJson.add(s.shipJson());
    }

    FleetJson fleetJson = new FleetJson(listOfShipJson);

    JsonNode node = JsonUtils.serializeRecord(fleetJson);
    response = new MessageJson("setup", node);

    jsonResponse = JsonUtils.serializeRecord(response);

    this.out.println(jsonResponse);
  }

  /**
   * Report to the player to join the server, and then serializes the player's
   * response to the server.
   */
  private void handleJoin() {
    JoinJson joinNode = new JoinJson("liangjimenez0", GameType.SINGLE);
    JsonNode node = JsonUtils.serializeRecord(joinNode);

    response = new MessageJson("join", node);

    jsonResponse = JsonUtils.serializeRecord(response);

    this.out.println(jsonResponse);
  }
}
