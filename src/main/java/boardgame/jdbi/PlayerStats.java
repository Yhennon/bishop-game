package boardgame.jdbi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class containing data of the game.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerStats {
    private int n_game;
    private int n_move;
}
