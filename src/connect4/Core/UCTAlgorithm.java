package connect4.Core;

import connect4.Core.MonteCarloTree.Node;

import java.util.ArrayList;
import java.util.Random;

public class UCTAlgorithm {
    static double coefficient = 0.8;

    public static Node uctSearch(MonteCarloTree tree) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start <= 3000) {
            treePolicy(tree);
            double reward = defaultPolicy(tree);
            backPropagation(tree, reward);
        }
        return bestChild(tree, 0);
    }

    public static void treePolicy(MonteCarloTree tree) {
        while (!tree.game.checkVictory()) {
            if (tree.currentNode.isFullyExpanded()) {
                if (tree.currentNode.children.size() == 0) {
                    return;
                }
                tree.currentNode = bestChild(tree, 0.707);
            } else {
                expand(tree);
                return;
            }
            tree.game.play(tree.currentNode.move);
        }
    }

    public static void expand(MonteCarloTree tree) {
        int item = new Random().nextInt(tree.currentNode.possibleMoves.size());
        Position pos = tree.currentNode.possibleMoves.remove(item);
        tree.game.play(pos);
        tree.currentNode = tree.currentNode.addChild(pos);
    }

    public static Node bestChild(MonteCarloTree tree, double c) {
        double bestReward = -Double.MAX_VALUE;
        Node bestChild = null;
        for (Node child : tree.currentNode.children) {
            double reward = (child.reward / child.accessCounts) * (child.isThisPlayer ? 1 : -1) +
                    c * Math.sqrt(2 * Math.log(tree.currentNode.accessCounts) / child.accessCounts);

            if (reward > bestReward) {
                bestReward = reward;
                bestChild = child;
            }
        }

        return bestChild;
    }

    public static double defaultPolicy(MonteCarloTree tree) {
        int step = 0;
        GameState game = new GameState(tree.game);
        while (!game.checkVictory() && step <= 12) {
            ArrayList<Position> positions = game.getAvailablePositions();
            if (positions.size() == 0) {
                break;
            }
            game.play(positions.get(new Random().nextInt(positions.size())));
            step++;
        }

        if (!game.checkVictory()) {
            return 0.0;
        } else if (game.getOtherPlayer() != tree.thisPlayer) {
            if (step <= 2) {
                return -1000000.0;
            }
            return -1.0;
        } else {
            return 1.0;
        }

    }

    public static void backPropagation(MonteCarloTree tree, double reward) {
        while (tree.currentNode != tree.root) {
            tree.currentNode.accessCounts++;
            tree.currentNode.reward += reward;
            reward *= coefficient;
            tree.game.undo(tree.currentNode.move);
            tree.currentNode = tree.currentNode.parent;
        }
        tree.root.accessCounts++;
    }

}
