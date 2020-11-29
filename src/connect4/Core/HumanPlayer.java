package connect4.Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HumanPlayer extends GamePlayer {

    public HumanPlayer(String name, int piece) {
        super(name, piece);
    }

    @Override
    public Position nextStep() throws Exception {
        int[] position = new int[2];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String lines = br.readLine();
        String[] ints = lines.trim().split("\\s+");

        int i = 0;
        do {
            position[i] = Integer.parseInt(ints[i]);
            i++;
        } while (i < ints.length);

        if (i != 2) {
            throw new Exception();
        }
        return new Position(position[0], position[1]);
    }

    @Override
    public MonteCarloTree createDecisionTree(GameState game) {
        return null;
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
