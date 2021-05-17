package boardgame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * An interface providing SQL statements for the database.
 */
@RegisterBeanMapper(PlayerStats.class)
public interface PlayerStatsDao {

    /**
     * This SQL statement creates the statistics_table table with two columns, n_game and n_move.
     */
    @SqlUpdate("""
            CREATE TABLE statistics_table (
                n_game INTEGER PRIMARY KEY,
                n_move INTEGER NOT NULL
            )
            """
    )
    void createTable();

    /**
     * This SQL statement inserts an n_game and an n_move into the statistics_table table.
     * @param n_game is the #number of the game.
     * @param n_move is the #number of moves that were required to reach goal state.
     */
    @SqlUpdate("INSERT INTO statistics_table VALUES (:n_game,:n_move)")
    void insertStats(@Bind("n_game") int n_game, @Bind("n_move") int n_move);

    /**
     * @return The n_game column of the statistics_table table, ordered by n_move column.
     */
    @SqlQuery("SELECT n_game FROM statistics_table ORDER BY n_move")
    int[] showNGame();

    /**
     * @return The n_move column of the statistics_table table, ordered by n_move column.
     */
    @SqlQuery("SELECT n_move FROM statistics_table ORDER BY n_move")
    int[] showNMove();

    /**
     * @return The index of the played game from the statistics_table table.
     */
    @SqlQuery("Select count(n_game) FROM statistics_table")
    int countId();

    /**
     * Deletes everything from the statistics_table table.
     */
    @SqlUpdate("delete from statistics_table")
    void deleteStatistics();
}
