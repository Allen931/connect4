package connect4.GUI;

import connect4.Core.GameState;
import connect4.Core.Position;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.System.currentTimeMillis;

public class Controller implements EventHandler<MouseEvent> {
    private final GameState game;
    private final GameScenePrinter printer;
    private final Time time;
    private Position pos = null;

    /**
     * @source https://stackoverflow.com/questions/35512648/adding-a-timer-to-my-program-javafx
     * @source https://stackoverflow.com/questions/17758411/java-creating-a-new-thread
     * @source https://stackoverflow.com/questions/21083945/how-to-avoid-not-on-fx-application-thread-currentthread-javafx-application-th
     */
    public Controller(GameState game, Connect4Board board, Label playerName, Label playerTimer, Label playerState) {
        this.game = game;
        printer = new GameScenePrinter(new Alert(Alert.AlertType.INFORMATION), game, board);
        Timer timer = new Timer();
        time = new Time(currentTimeMillis());

        if (!game.getCurrentPlayer().isHuman()) {
            aiPlay();
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (this) {
                    if (pos != null) {
                        game.play(pos);
                        time.reset(currentTimeMillis());
                        pos = null;
                        Platform.runLater(printer::nextScene);
                        if (!game.getCurrentPlayer().isHuman()) {
                            aiPlay();
                        }
                    }
                }
                Platform.runLater(() -> {
                    time.update(currentTimeMillis());
                    playerName.setText(game.getCurrentPlayer().playerName);
                    if (game.getCurrentPlayer().isHuman()) {
                        playerState.setText("Your turn");
                    } else {
                        playerState.setText("thinking...");
                    }
                    playerTimer.setText(time.toString());
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 100);
    }

    private void aiPlay() {
        if (game.checkVictory() || game.checkDraw()) {
            return;
        }
        Thread aiThread = new Thread(() -> {
            if (game.getCurrentPlayer().mct == null) {
                game.getCurrentPlayer().createDecisionTree(game);
            }
            try {
                Position position = game.getCurrentPlayer().nextStep();
                synchronized (this) {
                    pos = position;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        aiThread.start();
    }

    @Override
    public void handle(MouseEvent event) {
        double cell = 90;
        if (game.getCurrentPlayer().isHuman()) {
            //get mouse coordinates
            double x = event.getX();
            double y = event.getY();

            int i = (int) ((x - 100) / cell);
            int j = 5 - (int) ((y - 100) / cell);

            Position position = new Position(i, j);
            synchronized (this) {
                if (game.checkPlaceable(position)) {
                    pos = position;
                }
            }
        }
    }

}