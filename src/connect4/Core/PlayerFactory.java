package connect4.Core;

public class PlayerFactory {
    public static GamePlayer create(int type, String name, int piece) throws Exception {
        // support Java 11
        switch (type) {
            case 1:
                return new HumanPlayer(name, piece);
            case 2:
                return new AIPlayer(name, piece);
            default:
                throw new Exception("Invalid Player Type");
        }
        // Java 14
//        return switch (type) {
//            case 1 -> new HumanPlayer(name, piece);
//            case 2 -> new AIPlayer(name, piece);
//            default -> throw new Exception("Invalid Player Type");
//        };
    }
}
