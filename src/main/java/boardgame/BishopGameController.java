package boardgame;

import boardgame.model.BishopDirection;
import boardgame.model.BoardGameModel;
import boardgame.model.Position;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.tinylog.Logger;

import java.io.FileWriter;
import java.io.IOException;
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
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
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

    // Bekér egy sor és egy oszlopot, készít ezokkal az indexekkel egy StackPanet, és ere rárak egy SetOnMouseClicked "eseményt".
    // majd a a board elkészítéséhez használom fel.
    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    // Akkor hívódik meg, ha valamelyik square-re rákattintunk.
    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        var square = (StackPane) mouseEvent.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.info("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected).getAsInt();
                    var direction = BishopDirection.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.info("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    deselectSelectedPosition();
                    alterSelectionPhase();

                    model.incrementMoveCount();
                    System.out.println(model.getMoveCount());

                    if (model.isGoalStateReached()){
                        model.incrementGamesWon();
                        System.out.println("GoalState reached.");
                        System.out.println("Games won #times:");
                        System.out.println(model.getGamesWon());
//                        writeJSON(model.getGamesWon(), model.getMoveCount());

                    }
                }
            }
        }
    }

    //paramba
//    private void writeJSON(int gamesWon,int moveCount){
//        JSONObject statistics = new JSONObject();
//        statistics.put("#Number", gamesWon);
//        statistics.put("#OfMoves",moveCount);
//
//        try (FileWriter file = new FileWriter("statistics.json")) {
//            file.append(statistics.toJSONString());
////            file.flush();
//            System.out.println(statistics.toString());
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

    // Beállítjuk a selected változót egy positionre, majd meghívjuk a showSelectedPosition metodust, h lá
    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    // selected positionon lévő stackpanet megfogjuk ésbeállítjuk  a selected css stílust.
    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    // Beállítjuk a selected változót nullára, és a selected css stílust leszedjük. természetesen
    // előbb kell leszedni a csst, mert ha már null a selected,nem tudunk vele mit csinálni :)
    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    // megcseréljük a selectionPhase értékét .
    // eltüntetjük a kijelöléseket.
    // ujraszámoljuk a selectablePosition listát.
    // újra megjelenítjük a kijelöléseket.
    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    // selectionPhasetől függően megtöltjük a selectablePositions listát. Természetesen minden feltöltés előtt kinullázzuk.

    // FROM állapotban az összes, a boardon lévő bábu pozicioja selectable.
    // TO állapotban a selected bábu lehetséges movejait listázzuk.
    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getPiecePositions());
            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected).getAsInt();
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    //ua mint a showSelectedPosition csak remove a stylet.
    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    // A model minden bábujának positionjéhez hozzárendel egy listenert.
    // Elkészíti a bábukat, beállítja a színüket a "PieceType" juk alapján.(Ugy van megadva az enumok neve, hogy a name() fvel lekérve egy legit javafx es Color legyen)
    // utolso sor: getSquare el megfogjuk az adott positionon lévő stackpanet, és hozzáadjuk a positionon lévő bábu gyermekeihez
    private void createPieces() {
        System.out.println(model.getPieceCount());
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);

        }
    }

    //innen

    // A háromszög elkészítése
    private Circle createPiece(Color color) {
        var piece = new Circle(30);
        piece.setFill(color);
        piece.setStrokeWidth(2);
//        piece.setStroke(color
//        piece.setFill(color);
//        piece.setScaleX(0.5);
//        piece.setScaleY(0.5);
//        piece.setScaleZ(0.5);
//        piece.setTranslateX(-5);
        return piece;
    }

    // Egy square-t szeretnénk visszakapni.Emlékeztető: a squarejeink a GridPane-ünk gyermekeien(sorxoszlop) lévő StackPane-ek.
    // Hogy melyik squaret szeretnénk megfogni, azt egy piece positionje alapjan nezzuk.
    // Röviden tehát megadunk egy positiont, és azon a positionön lévő StackPane-t(square-t) fogjuk meg.
    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    // A bábu grafikus elmozgatása a gridpanen. + logolás
    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.info("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }

    @FXML
    private void showStatisticsScene(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/statistics.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
