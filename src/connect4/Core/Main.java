package connect4.Core;

public class Main {

    public static void main(String[] args) {
        int[] gameArgs = {1, 1, 7, 6, 4};
        for (int i = 0; i < args.length; i++) {
            try {
                gameArgs[i] = Integer.parseInt(args[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Too many arguments");
                System.err.println("Please enter game arguments as " +
                        "\"Type of Player 1/Type of Player 2/Column/Row/Number Of Connect\"");
                System.exit(1);
            }
        }

        try {
            GamePlayer player1 = PlayerFactory.create(gameArgs[0], "Player1", 1);
            GamePlayer player2 = PlayerFactory.create(gameArgs[1], "Player2", 2);
            GameState game = new GameState(player1, player2, gameArgs[2], gameArgs[3], gameArgs[4]);
            GamePrinter printer = new GamePrinter(System.out, game);
            printer.printGame();
            while (true) {
                Position position = null;
                if (!game.getCurrentPlayer().isHuman() && game.getCurrentPlayer().mct == null) {
                        game.getCurrentPlayer().createDecisionTree(game);
                }

                try {
                    position = game.getCurrentPlayer().nextStep();
                    if (!game.checkPlaceable(position)) {
                        printer.printGame();
                        System.out.println("Invalid coordinates.");
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                if (position != null) {
                    game.play(position);
                }
                printer.printGame();
                if (game.checkVictory()) {
                    printer.printWin();
                    break;
                } else if (game.checkDraw()) {
                    printer.printDraw();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
