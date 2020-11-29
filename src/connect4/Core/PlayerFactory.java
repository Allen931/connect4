package connect4.Core;

public class PlayerFactory {
    public static GamePlayer create(int type, String name, int piece) throws Exception {
        return switch (type) {
            case 1 -> new HumanPlayer(name, piece);
            case 2 -> new AIPlayer(name, piece);
            default -> throw new Exception("Invalid Player Type");
        };
    }
}
