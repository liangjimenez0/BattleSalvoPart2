package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "coordinates": "coordinates"
 * }
 * </code>
 * </p>
 *
 * @param coordinates a list of coord json
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<CoordJson> coordinates
) {
}
