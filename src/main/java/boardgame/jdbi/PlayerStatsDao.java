package boardgame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(PlayerStats.class)
public interface PlayerStatsDao {

    @SqlUpdate("""
            CREATE TABLE statistics_table (
                n_game INTEGER PRIMARY KEY,
                n_move INTEGER NOT NULL
            )
            """
        )
    void createTable();

    @SqlUpdate("INSERT INTO statistics_table VALUES (:n_game,:n_move)")
    void insertStats(@Bind("n_game") int n_game,@Bind("n_move") int n_move);

    @SqlQuery("SELECT n_game FROM statistics_table ORDER BY n_move")
    int[] showNGame();

    @SqlQuery("SELECT n_move FROM statistics_table ORDER BY n_move")
    int[] showNMove();

    @SqlQuery("Select count(n_game) FROM statistics_table")
    int countId();
}
