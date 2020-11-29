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
                Position position;
                if (game.currentPlayer.isHuman()) {
                    try {
                        position = game.currentPlayer.nextStep();
                        if (!game.checkPlaceable(position)) {
                            printer.printGame();
                            System.out.println("Invalid coordinates.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid coordinates.");
                        continue;
                    }
                } else {
                    if (game.currentPlayer.mct == null) {
                        game.currentPlayer.createDecisionTree(game);
                    }
                    position = game.currentPlayer.nextStep();
                }

                if (position != null) {
                    game.play(position);
                }
                printer.printGame();
                if (game.checkVictory()) {
                    break;
                }
            }
            System.out.printf("WINNER: %s!", game.getOtherPlayer().playerName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
