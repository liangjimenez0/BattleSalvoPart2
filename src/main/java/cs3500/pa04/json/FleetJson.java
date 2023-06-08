package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *  JSON format of this record:
 *  <p>
 *  <code>
 *  {
 *    "fleet": "listOfShips",
 *  }
 *  </code>
 *  </p>
 *
 * @param listOfShips a list of ship json that represent a fleet
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> listOfShips
) {
}
