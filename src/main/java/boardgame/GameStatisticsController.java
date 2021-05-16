package boardgame;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameStatisticsController {

    @FXML
    TextFlow statisticsTextFlow;

    @FXML
    private void initialize(){
        boardgame.jdbi.StatisticsController statisticsController = new boardgame.jdbi.StatisticsController();
        int[] ngames = statisticsController.getNGames();

        int[] nmoves = statisticsController.getNMoves();

        for (int i = 0; i< ngames.length;i++){
            statisticsTextFlow.getChildren().add(new Text((i+1)+" . #Játék : "+ngames[i]+ " Lépések száma : "+nmoves[i]+"\n"));

        }
    }
}
