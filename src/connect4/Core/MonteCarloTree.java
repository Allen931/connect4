package connect4.Core;

import java.util.ArrayList;
import java.util.List;

public class MonteCarloTree {
    Node root = null;
    Node currentNode = null;
    final GameState game;
    GamePlayer thisPlayer;

    public MonteCarloTree(GameState game, GamePlayer player) {
        this.game = new GameState(game);
        thisPlayer = player;
    }

    public void createRoot() {
        root = new Node();
        currentNode = root;
        root.isThisPlayer = false;
    }

    public class Node {
        Node parent = null;
        List<Node> children = new ArrayList<>();
        Position move;
        ArrayList<Position> possibleMoves;
        int possibleMovesNumber;
        double reward;
        int accessCounts;
        boolean isThisPlayer;

        private Node() {
            accessCounts = 1;
            possibleMoves = game.getPlaceablePositions();
            possibleMovesNumber = possibleMoves.size();
        }

        private Node(Node parent, Position move) {
            this.parent = parent;
            accessCounts = 1;
            this.move = move;
            isThisPlayer = !parent.isThisPlayer;
            possibleMoves = game.getPlaceablePositions();
            possibleMovesNumber = possibleMoves.size();
        }

        public Node addChild(Position move) {
            Node child = new Node(this, move);
            children.add(child);
            return child;
        }

        public boolean isFullyExpanded() {
            return children.size() == possibleMovesNumber;
        }

    }

}
