package connect4.Core;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int connectNumber;
    private final int column;
    private final int row;
    private final int[] availableY;
    private final int[][] board;

    public Board(int column, int row, int connect) {
        board = new int[row][column];
        this.column = column;
        this.row = row;
        availableY = new int[column];
        connectNumber = connect;
    }


    /**
     * @source https://stackoverflow.com/questions/5617016/how-do-i-copy-a-2-dimensional-array-in-java
     */
    public Board(Board originalBoard) {
        board = Arrays.stream(originalBoard.board).map(int[]::clone).toArray(int[][]::new);
        column = originalBoard.column;
        row = originalBoard.row;
        availableY = new int[column];
        System.arraycopy(originalBoard.availableY, 0, availableY, 0, column);
        connectNumber = originalBoard.connectNumber;
    }

    void placePiece(GamePlayer player, int x, int y) {
        board[y][x] = player.piece;
        availableY[x]++;
    }

    void deletePiece(int x, int y) {
        board[y][x] = 0;
        availableY[x]--;
    }

    boolean checkPlaceable(int x, int y) {
        if (x < 0 || x >= column || y < 0 || y >= row) {
            return false;
        } else return y == availableY[x];
    }

    boolean checkVictory(int x, int y) {
        int piece = board[y][x];
        if (piece == 0) {
            return false;
        }
        return checkVertical(piece, x, y) || checkHorizontal(piece, x, y) || checkDiagonal(piece, x, y);
    }

    ArrayList<Position> getAvailablePositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i < column; i++) {
            if (availableY[i] < row) {
                positions.add(new Position(i, availableY[i]));
            }
        }
        return positions;
    }

    boolean isFull() {
        for (int i : availableY) {
            if (i < row) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVertical(int piece, int x, int y) {
        int count = 1;
        count += countConnected(piece, x, y, 0, -1);
        return count >= 4;
    }

    private boolean checkHorizontal(int piece, int x, int y) {
        int count = 1;
        count += countConnected(piece, x, y, 1, 0);
        count += countConnected(piece, x, y, -1, 0);
        return count >= 4;
    }

    private boolean checkLeftDiagonal(int piece, int x, int y) {
        int count = 1;
        count += countConnected(piece, x, y, -1, 1);
        count += countConnected(piece, x, y, 1, -1);
        return count >= 4;
    }

    private boolean checkDiagonal(int piece, int x, int y) {
        return checkLeftDiagonal(piece, x, y) || checkRightDiagonal(piece, x, y);
    }

    private boolean checkRightDiagonal(int piece, int x, int y) {
        int count = 1;
        count += countConnected(piece, x, y, 1, 1);
        count += countConnected(piece, x, y, -1, -1);
        return count >= 4;
    }

    private int countConnected(int piece, int x, int y, int directionX, int directionY) {
        int count = 0;
        x += directionX;
        y += directionY;
        while (x >= 0 && x < column && y >= 0 && y < row) {
            if (board[y][x] != piece) {
                break;
            } else {
                count++;
                if (count >= 4) {
                    break;
                }
            }
            x += directionX;
            y += directionY;
        }
        return count;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int[][] getBoard() {
        return board;
    }
}
