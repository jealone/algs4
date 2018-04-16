import edu.princeton.cs.algs4.ResizingArrayStack;

/**
 * Created by Arthur on 2018/4/15.
 */
public class Board {

    private final int[][] grid;

    private final int hammingCache;
    private final int manhattanCache;
    private final int dimension;

    private int blankRow;
    private int blankCol;


    public Board(int[][] blocks) {
        int hCache = 0;
        int mCache = 0;

        if (blocks.length < 2) {
            throw new IllegalArgumentException();
        }

        if (blocks[0].length != blocks.length) {
            throw new IllegalArgumentException();
        }

        dimension = blocks.length;

        grid = new int[dimension][dimension];
        int row, col;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = blocks[i][j];

                if (grid[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                } else if (grid[i][j] != j + (i * dimension) + 1) {
                    hCache++;
                    row = (grid[i][j] - 1) / dimension;
                    col = (grid[i][j] - 1) % dimension;
                    mCache += ((row > i ? row - i : i - row) + (col > j ? col - j : j - col));
                }

            }
        }
        hammingCache = hCache;
        manhattanCache = mCache;
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hammingCache;
    }

    public int manhattan() {
        return manhattanCache;
    }

    public boolean isGoal() {
        return hammingCache == 0;
    }

    public Board twin() {
        int[][] copy = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                copy[i][j] = grid[i][j];
            }
        }

        int row1, row2;

        int col1 = (blankCol + 1) % dimension;
        if (col1 == 0) {
            row1 = (blankRow + 1) % dimension;
        } else {
            row1 = blankRow;
        }

        int col2 = (col1 + 1) % dimension;

        if (col2 == 0) {
            row2 = (row1 + 1) % dimension;
        } else {
            row2 = row1;
        }

        swap(copy, row1, col1, row2, col2);

        return new Board(copy);
    }


    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (that.dimension() != this.dimension()) {
            return false;
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (that.grid[i][j] != this.grid[i][j]) {
                    return false;
                }
            }
        }

        return true;

    }

    public Iterable<Board> neighbors() {
        ResizingArrayStack<Board> st = new ResizingArrayStack<Board>();
        if (isIllegal(blankRow + 1, blankCol)) {
            st.push(new Board(swap(blankRow + 1, blankCol, blankRow, blankCol)));

        }

        if (isIllegal(blankRow - 1, blankCol)) {
            st.push(new Board(swap(blankRow - 1, blankCol, blankRow, blankCol)));

        }

        if (isIllegal(blankRow, blankCol + 1)) {

            st.push(new Board(swap(blankRow, blankCol + 1, blankRow, blankCol)));
        }

        if (isIllegal(blankRow, blankCol - 1)) {
            st.push(new Board(swap(blankRow, blankCol - 1, blankRow, blankCol)));
        }

        return st;
    }


    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append(dimension + "\n");

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format(" %2d", grid[i][j]));
            }
            s.append("\n");
        }


        return s.toString();
    }

    private int[][] swap(int row1, int col1, int row2, int col2) {

        int[][] copy = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                copy[i][j] = grid[i][j];
            }
        }

        swap(copy, row1, col1, row2, col2);

        return copy;
    }

    private void swap(int[][] a, int row1, int col1, int row2, int col2) {
        int tmp = a[row1][col1];
        a[row1][col1] = a[row2][col2];
        a[row2][col2] = tmp;
    }

    private boolean isIllegal(int row, int col) {
        if (row < 0 || row >= dimension) {
            return false;
        }
        if (col < 0 || col >= dimension) {
            return false;
        }
        return true;
    }




}
