package cs3500.pa04;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ControllerImp;
import cs3500.pa03.model.Board;
import cs3500.pa04.client.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * The main entrypoint into the code. Given a host and port as parameters, the
   * client is run. If given no arguments, player and AI may play Battle Salvo game.
   * If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    Controller controller =
        new ControllerImp(input, output, new Random(), new Board(), new Board());

    try {

      if (args.length == 0) {
        controller.run();
      } else if (args.length == 2) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        runClient(host, port);
      }

    } catch (IOException e) {
      throw new RuntimeException();
    }

  }

  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port) throws IOException {

    Socket server = new Socket(host, port);
    Board aiBoard = new Board();
    Board playerBoard = new Board();
    Controller controller = new ProxyController(server, aiBoard, playerBoard, new Random());

    controller.run();
  }
}