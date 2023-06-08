package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "width": "width",
 * "height": "height"
 * "fleet-spec": "fleetSpec"
 * }
 * </code>
 * </p>
 *
 * @param width     an integer that represents the width of the board
 * @param height    an integer that represents the height of the board
 * @param fleetSpec a map that represents the fleet spec
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec
) {
}
