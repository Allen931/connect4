package connect4.Core;

import java.util.ArrayList;

public class GameState {
    private Board board;
    private GamePlayer player1, player2;
    private GamePlayer currentPlayer;
    Position lastPosition = null;

    public GameState(GamePlayer player1, GamePlayer player2, int column,
                     int row, int connectNumber) {
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        board = new Board(column, row, connectNumber);
    }

    public GameState(GameState game) {
        player1 = game.player1;
        player2 = game.player2;
        currentPlayer = game.currentPlayer;
        board = new Board(game.board);
        lastPosition = game.lastPosition;
    }

    public void play(Position position) {
        if (position == null) {
            return;
        }
        board.placePiece(currentPlayer, position.x, position.y);
        lastPosition = position;
        currentPlayer = getOtherPlayer();
    }

    void undo(Position position) {
        board.deletePiece(position.x, position.y);
        currentPlayer = getOtherPlayer();
    }

    ArrayList<Position> getAvailablePositions() {
        return board.getAvailablePositions();
    }

    public boolean checkPlaceable(Position position) {
        if (position == null) {
            return false;
        }
        return board.checkPlaceable(position.x, position.y);
    }

    public boolean checkVictory() {
        if (lastPosition == null) {
            return false;
        }
        return board.checkVictory(lastPosition.x, lastPosition.y);
    }

    public boolean checkDraw() {
        return board.isFull();
    }

    public GamePlayer getOtherPlayer() {
        return currentPlayer.equals(player1) ? player2 : player1;
    }

    public Board getBoard() {
        return board;
    }

    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
