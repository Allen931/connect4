package connect4.Core;

public class AIPlayer extends GamePlayer {
    public MonteCarloTree mct = null;

    public AIPlayer(String name, int piece) {
        super(name, piece);
    }

    @Override
    public Position nextStep() {
        if (mct == null) {
            return null;
        }
        if (mct.game.lastPosition != null) {
            for (MonteCarloTree.Node child : mct.currentNode.children) {
                if (mct.game.lastPosition.equals(child.move)) {
                    mct.root = child;
                    mct.currentNode = child;
                    break;
                }
            }
        }
        MonteCarloTree.Node node = UCTAlgorithm.uctSearch(mct);
        mct.root = node;
        mct.currentNode = mct.root;
        return node.move;
    }

    public MonteCarloTree createDecisionTree(GameState game) {
        mct = new MonteCarloTree(game, this);
        mct.createRoot();
        return mct;
    }


    @Override
    public boolean isHuman() {
        return false;
    }
}
