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
                    mct.root.parent = null;
                    mct.currentNode = child;
                    break;
                }
            }
        }
        MonteCarloTree.Node node = UCTAlgorithm.uctSearch(mct);
        mct.root = node;
        mct.root.parent = null;
        mct.currentNode = mct.root;
        return node.move;
    }

    public void createDecisionTree(GameState game) {
        mct = new MonteCarloTree(game, this);
        mct.createRoot();
    }


    @Override
    public boolean isHuman() {
        return false;
    }
}
