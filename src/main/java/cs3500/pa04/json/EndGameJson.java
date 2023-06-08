package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "result": "result",
 * "reason": "reason"
 * }
 * </code>
 * </p>
 *
 * @param result a game result for the Battle Salvo game
 * @param reason a string version for the reason why the game is over
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason
) {
}
