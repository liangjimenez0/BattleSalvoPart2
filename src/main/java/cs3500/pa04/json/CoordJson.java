package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  JSON format of this record:
 *  <p>
 *  <code>
 *  {
 *    "x": "x",
 *    "y": "y"
 *  }
 *  </code>
 *  </p>
 *
 * @param x an integer that represents the x coordinate
 * @param y an integer that represents the y coordinate
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {
}
