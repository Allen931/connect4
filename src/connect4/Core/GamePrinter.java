package connect4.Core;

import java.io.PrintStream;

public class GamePrinter {
    private final PrintStream output;
    private final GameState game;

    public GamePrinter(PrintStream output, GameState game) {
        this.output = output;
        this.game = game;
    }

    void printGame() {
        if (game.lastPosition != null) {
            printInformation();
        }
        try {
            printBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        output.println(game.getCurrentPlayer().playerName);
        if (!game.getCurrentPlayer().isHuman()) {
            output.println("Thinking...");
        } else {
            output.println("Please enter x and y.");
        }
    }

    void printInformation() {
        output.printf("%s : (%d, %d)%n",
                game.getOtherPlayer().playerName,
                game.lastPosition.x,
                game.lastPosition.y);
    }

    void printBoard() throws Exception {
        for (int i = game.getRow() - 1; i >= 0; i--) {
            output.printf("%d", i);
            for (int j = 0; j < game.getColumn(); j++) {
                int piece = game.getBoard()[i][j];
                if (piece == 0) {
                    output.printf(" %c", '.');
                } else if (piece == 1) {
                    output.printf(" %c", '○');
                } else if (piece == 2) {
                    output.printf(" %c", '●');
                } else {
                    throw new Exception("Invalid piece.");
                }

            }
            output.println();
        }
        output.print(" ");
        for (int k = 0; k < game.getColumn(); k++) {
            output.printf(" %d", k);
        }
        output.println();
    }

    void printWin() {
        output.printf("WINNER: %s!", game.getOtherPlayer().playerName);
    }

    void printDraw() {
        output.println("Draw!");
    }

}
