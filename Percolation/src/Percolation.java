import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int grid;
    private final int head;
    private final int tail;
    private int count;
    private byte[][] open;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufTop;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("invalid input");
        this.grid = n;
        this.head = 0;
        this.tail = this.grid * this.grid + 1;
        this.count = 0;
        this.uf = new WeightedQuickUnionUF((this.grid * this.grid) + 2);
        this.ufTop = new WeightedQuickUnionUF((this.grid * this.grid) + 1);
        this.open = new byte[grid][grid];
    }

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
        return this.uf.connected(this.head, this.tail);
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return this.ufTop.connected(this.head, position(row, col));
    }

    private int position(int row, int col) {
        return (row-1) * grid + (col-1) + 1;
    }

    private void validate(int row, int col) {
        if (row <= 0 || grid < row)
            throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || grid < col)
            throw new IllegalArgumentException("column index out of bounds");
    }

    private void leftUnion(int row, int col) {
        if (col == 1) {
            return;
        }

        if (isOpen(row, col - 1)) {
            this.uf.union(position(row, col), position(row, col - 1));
            this.ufTop.union(position(row, col), position(row, col - 1));
        }
    }

    private void rightUnion(int row, int col) {
        if (col == this.grid) {
            return;
        }
        if (isOpen(row, col + 1)) {
            this.uf.union(position(row, col), position(row, col + 1));
            this.ufTop.union(position(row, col), position(row, col + 1));
        }
    }

    private void upUnion(int row, int col) {
        if (row == 1) {
            this.uf.union(this.head, position(row, col));
            this.ufTop.union(this.head, position(row, col));
            return;
        }

        if (isOpen(row - 1, col)) {
            this.uf.union(position(row, col), position(row - 1, col));
            this.ufTop.union(position(row, col), position(row - 1, col));
        }
    }

    private void downUnion(int row, int col) {
        if (row == this.grid) {
            this.uf.union(this.tail, position(row, col));
            return;
        }

        if (isOpen(row + 1, col)) {
            this.uf.union(position(row, col), position(row + 1, col));
            this.ufTop.union(position(row, col), position(row + 1, col));
        }
    }


}
