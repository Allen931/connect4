package connect4.GUI;

import connect4.Core.GameState;
import javafx.scene.control.Alert;

public class GameScenePrinter {
    Alert alert;
    GameState game;
    Connect4Board board;

    public GameScenePrinter(Alert alert, GameState game, Connect4Board board) {
        this.alert = alert;
        this.game = game;
        this.board = board;
    }

    public void nextScene() {
        board.drawChess();
        if (game.checkVictory()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connect4");
            alert.setHeaderText("Information");
            alert.setContentText("WINNER: " + game.getOtherPlayer().playerName);
            alert.showAndWait();
            System.exit(0);
        } else if (game.checkDraw()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connect4");
            alert.setHeaderText("Information");
            alert.setContentText("Draw");
            alert.showAndWait();
            System.exit(0);
        }
    }

}
