package connect4.GUI;

import connect4.Core.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Connect4Board extends Pane {
    public Canvas canvas;
    public GraphicsContext gc;
    public GameState game;
    double cell;


    public void setGameState(GameState game) {
        this.game = game;
    }

    public Connect4Board(GameState game) {
        this.game = game;
        cell = 90;
        drawPane();
        drawChess();
        getChildren().add(canvas);

    }

    public void drawPane() {
        canvas = new Canvas(cell * 7 + 400, cell * 6 + 200);
        gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //draw the panel
        gc.setStroke(Color.BLACK);

        for (int i = 0; i < game.getBoard().getColumn(); i++) {
            for (int j = 0; j < game.getBoard().getRow(); j++) {
                gc.strokeRect(100 + i * cell, 100 + cell * j, cell, cell);//set up one rectangle
            }
        }
    }

    public void drawChess() {
        int[][] board = game.getBoard().getBoard();
        for (int i = 0; i < game.getBoard().getColumn(); i++)
            for (int j = 0; j < game.getBoard().getRow(); j++) {
                if (board[j][i] == 1) {
                    gc.setFill(Color.BLACK);
                    gc.fillOval(100 + i * cell, canvas.getHeight() - 100 - (j + 1) * cell, cell, cell);
                } else if (board[j][i] == 2) {
                    gc.setFill(Color.WHITE);
                    gc.fillOval(100 + i * cell, canvas.getHeight() - 100 - (j + 1) * cell, cell, cell);
                    gc.strokeOval(100 + i * cell, canvas.getHeight() - 100 - (j + 1) * cell, cell, cell);
                }
            }

    }
}
