package cs3500.pa03.controller;

import java.io.IOException;

/**
 * Represents the Controller interface which is responsible for running the Battleship game
 */
public interface Controller {

  /**
   * Takes in the player's system input and delegates them to classes to run the game of battleship
   */
  void run() throws IOException;
}
