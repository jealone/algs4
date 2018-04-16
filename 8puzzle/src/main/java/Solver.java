import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Arthur on 2018/4/15.
 */
public class Solver {

    private boolean solvable;
    private Node current;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Node> pq = new MinPQ<Node>();

        pq.insert(new Node(initial, null, 0, false));
        pq.insert(new Node(initial.twin(), null, 0, true));


        while (!pq.isEmpty()) {
            Node node = pq.delMin();

            if (!node.isTwin) {
                current = node;
            }

            if (node.board.isGoal()) {
                if (node.isTwin) {
                    solvable = false;
                } else {
                    solvable = true;
                }

                break;
            }

            for (Board nb : node.board.neighbors()) {
                if (node.prev == null || !nb.equals(node.prev.board)) {
                    pq.insert(new Node(nb, node, node.moves + 1, node.isTwin));
                }
            }

        }

    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (isSolvable()) {
            return current.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        ResizingArrayStack<Board> stack = new ResizingArrayStack<Board>();

        Node node = current;

        stack.push(node.board);

        while (node.prev != null) {
            node = node.prev;
            stack.push(node.board);
        }

        return stack;
    }

    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node prev;
        private final int moves;
        private final boolean isTwin;
        private final int priority;

        public Node(Board board, Node prev, int moves, boolean isTwin) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.isTwin = isTwin;
            this.priority = this.board.manhattan() + moves;
        }

        public int compareTo(Node that) {
            return this.priority - that.priority;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }


}
