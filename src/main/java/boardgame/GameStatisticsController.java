package boardgame;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.tinylog.Logger;

public class GameStatisticsController {

    @FXML
    TextFlow statisticsTextFlow;

    @FXML
    private void initialize() {
        boardgame.jdbi.StatisticsController statisticsController = new boardgame.jdbi.StatisticsController();
        int[] ngames = statisticsController.getNGames();

        int[] nmoves = statisticsController.getNMoves();

        for (int i = 0; i < ngames.length; i++) {
            statisticsTextFlow.getChildren().add(new Text((i + 1) + " . #Játék : " + ngames[i] + " Lépések száma : " + nmoves[i] + "\n"));

        }
    }

    @FXML
    private void exitGame(ActionEvent event) {
        Logger.info("Exitting game...");
        Platform.exit();
    }
}
