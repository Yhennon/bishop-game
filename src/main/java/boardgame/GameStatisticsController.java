package boardgame;

import boardgame.jdbi.StatisticsController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

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

    @FXML
    private void clearStatistics(ActionEvent event) throws IOException {
        Logger.info("Clearing statistics_table...");
        StatisticsController statisticsController = new StatisticsController();
        statisticsController.deleteStatistics();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/statistics.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
