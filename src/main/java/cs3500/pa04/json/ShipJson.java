package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.Direction;

/**
 *  JSON format of this record:
 *  <p>
 *  <code>
 *  {
 *    "coord": "coord",
 *    "length": "length"
 *    "direction": "direction"
 *  }
 *  </code>
 *  </p>
 *
 * @param coord a Coord Json that represents the starting coord
 * @param length an integer that represents the length of a ship
 * @param direction a Direction that represents the direction of a ship
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") Direction direction
) {
}
