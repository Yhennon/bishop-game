package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

public class StatisticsController {
    private static Jdbi jdbi;

    public StatisticsController() {
        this.jdbi = Jdbi.create("jdbc:h2:file:~/.bishop-game24/leaderboard", "as", "");
        this.jdbi.installPlugin(new SqlObjectPlugin());
        this.jdbi.installPlugin(new H2DatabasePlugin());
        try {
            jdbi.withExtension(PlayerStatsDao.class, dao -> {
                dao.createTable();
                return true;
            });
        } catch (Exception e) {
            Logger.info("Table already exists.");
        }
    }

    public static Jdbi getJdbiInstance() {
        if (jdbi == null) {
            new StatisticsController();
        }
        return jdbi;
    }

    public static void insertNGame(int ngame, int nmove) {
        jdbi.withExtension(PlayerStatsDao.class, dao -> {
            dao.insertStats(ngame,nmove);
            return true;
        });
    }

    public static int[] getNGames() {
        int [] nGamesScore = jdbi.withExtension(PlayerStatsDao.class, dao -> dao.showNGame());
        return nGamesScore;
    }

    public static int[] getNMoves() {
        int [] nMovesScore = jdbi.withExtension(PlayerStatsDao.class, dao -> dao.showNMove());
        return nMovesScore;
    }

    public static int getAllGameCount() {
        int allGameCount = jdbi.withExtension(PlayerStatsDao.class, dao -> dao.countId());
        return allGameCount;
    }


}
