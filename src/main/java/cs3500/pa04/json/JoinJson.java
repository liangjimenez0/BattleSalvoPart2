package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.GameType;

/**
 *  JSON format of this record:
 *  <p>
 *  <code>
 *  {
 *    "name": "name",
 *    "game-type": "gameType"
 *  }
 *  </code>
 *  </p>
 *
 * @param name a string that represents the username
 * @param gameType a game type that represents the type of game being played
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType
) {
}
