package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.Position;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class BishopGameController {
    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private GridPane board;

    @FXML
    private void initialize() {
        createBoard();
        //TODO
    }

    // A board minden mezőjéhez hozzáadni egy squaret(ami egy stackpane, amire majd így pakolhatjuk a bábut)
    // a createSquare()-t hasznalja fel
    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    // square generálás
    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        // add square styling
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void handleMouseClick(MouseEvent mouseEvent) {
        //TODO
    }

    private void createPieces() {
        //ehhez már kell a model
    }
}
