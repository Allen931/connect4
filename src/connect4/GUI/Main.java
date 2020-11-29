package connect4.GUI;

import connect4.Core.GamePlayer;
import connect4.Core.GameState;
import connect4.Core.PlayerFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<String> playerTypes = new ArrayList<>();
        int[] players = new int[2];
        playerTypes.add("Human");
        playerTypes.add("AI");

        ChoiceDialog<String> dialog0 = new ChoiceDialog<>("Human", playerTypes);
        dialog0.setTitle("Choose Player Type");
        dialog0.setHeaderText("Choose Player Type");
        dialog0.setContentText("Player 1:");

        Optional result = dialog0.showAndWait();
        if (result.isEmpty()) {
            System.exit(0);
        }

        ChoiceDialog<String> dialog1 = new ChoiceDialog<>("Human", playerTypes);
        dialog1.setTitle("Choose Player Type");
        dialog1.setHeaderText("Choose Player Type");
        dialog1.setContentText("Player 2:");

        Optional result1 = dialog1.showAndWait();
        if (result1.isEmpty()) {
            System.exit(0);
        }

        Optional[] results = new Optional[]{result, result1};

        for (int i = 0; i < results.length; i++) {
            if (results[i].get().equals("Human")) {
                players[i] = 1;
            } else if (results[i].get().equals("AI")) {
                players[i] = 2;
            } else {
                throw new Exception("Invalid Player Type");
            }
        }
        System.out.println(players[0]);
        System.out.println(players[0]);

        GamePlayer player1 = PlayerFactory.create(players[0], "Player1", 1);
        GamePlayer player2 = PlayerFactory.create(players[1], "Player2", 2);
        GameState game = new GameState(player1, player2, 7, 6, 4);
        Connect4Board board = new Connect4Board(game);

        Label playerName = new Label(game.getCurrentPlayer().playerName);
        Label playerTimer = new Label("00: 00");
        playerName.setFont(new Font("Comic Sans MS", 48));
        playerTimer.setFont(new Font("Comic Sans MS", 48));
        playerName.setTranslateX(800);
        playerName.setTranslateY(320);
        playerTimer.setTranslateX(800);
        playerTimer.setTranslateY(380);

        Group root = new Group();
        root.getChildren().addAll(playerName, playerTimer, board);

        board.setOnMouseClicked(new Controller(game, board, playerName, playerTimer));

        Scene scene = new Scene(root, 1030, 740);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Connect4");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
