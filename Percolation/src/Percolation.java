/**
 * Created by Arthur on 2018/3/28.
 */

//import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private int head;
    private int tail;
    private int count;
    private byte[][] open;
    private WeightedQuickUnionUF UF;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("invalid input");
        this.N = n;
        this.head = 0;
        this.tail = this.N * this.N + 1;
        this.count = 0;
        this.UF = new WeightedQuickUnionUF((this.N * this.N) + 2);
        this.open = new byte[N][N];
    }

//    public static void main(String[] args) {
//        In input = new In(args[0]);
//        int N = input.readInt();
//        Percolation percolation = new Percolation(N);
//        boolean percolates = false;
//        while (!input.isEmpty()) {
//            int row = input.readInt();
//            int col = input.readInt();
//            percolation.open(row, col);
//            percolates = percolation.percolates();
//            if (percolates) break;
//        }
//
//        StdOut.println(percolation.numberOfOpenSites() + " open sites");
//        if (percolates) {
//            StdOut.println("percolates");
//        } else {
//            StdOut.println("does not percolate");
//        }
//    }

    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        this.count++;
        open[row-1][col-1] = 1;

        this.upUnion(row, col);
        this.downUnion(row, col);
        this.leftUnion(row, col);
        this.rightUnion(row, col);

    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return this.open[row - 1][col - 1] == 1;

    }

    public int numberOfOpenSites() {
        return this.count;
    }

    public boolean percolates() {
        return this.UF.connected(this.head, this.tail);
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return this.UF.connected(this.head, position(row, col));
    }

    private int position(int row, int col) {
        return (row-1) * N + (col-1) + 1;
    }

    private void validate(int row, int col) {
        if (row <= 0 || N < row)
            throw new IndexOutOfBoundsException("row index out of bounds");
        if (col <= 0 || N < col)
            throw new IndexOutOfBoundsException("column index out of bounds");
    }

    private void leftUnion(int row, int col) {
        if (col == 1) {
            return;
        }

        if (isOpen(row, col - 1)) {
            this.UF.union(position(row, col), position(row, col - 1));
        }
    }

    private void rightUnion(int row, int col) {
        if (col == this.N) {
            return;
        }
        if (isOpen(row, col + 1)) {
            this.UF.union(position(row, col), position(row, col + 1));
        }
    }

    private void upUnion(int row, int col) {
        if (row == 1) {
            this.UF.union(this.head, position(row, col));
            return;
        }

        if (isOpen(row - 1, col)) {
            this.UF.union(position(row, col), position(row - 1, col));
        }
    }

    private void downUnion(int row, int col) {
        if (row == this.N) {
            this.UF.union(this.tail, position(row, col));
            return;
        }

        if (isOpen(row + 1, col)) {
            this.UF.union(position(row, col), position(row + 1, col));
        }
    }


}
