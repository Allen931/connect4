package connect4.Core;

public abstract class GamePlayer {
    int piece;
    public String playerName;
    public MonteCarloTree mct = null;

    public GamePlayer(String name, int piece) {
        playerName = name;
        this.piece = piece;
    }

    public abstract Position nextStep() throws Exception;

    public abstract MonteCarloTree createDecisionTree(GameState game);

    public abstract boolean isHuman();

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

}
