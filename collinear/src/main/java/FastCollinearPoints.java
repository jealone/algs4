import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Arthur on 2018/4/6.
 */
public class FastCollinearPoints {
//    private final Point[] points;
    private Node[] nodes;
    private int size;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] inPoints) {
        if (inPoints == null) {
            throw new IllegalArgumentException();
        }

        for (Point p : inPoints) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < inPoints.length - 1; i++) {
            for (int j = i + 1; j < inPoints.length; j++) {
                if (0 == inPoints[i].compareTo(inPoints[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }

//        points = new Point[inPoints.length];

//        for (int i = 0; i < inPoints.length; i++) {
//            points[i] = inPoints[i];
//        }
        this.size = 0;
        nodes = new Node[10];

        this.checkCollinear(inPoints);

        segments = new LineSegment[this.size];
        for (int i = 0; i < this.size; i++) {
            Node node = nodes[i];
            segments[i] = new LineSegment(node.start, node.end);
        }
    }

    private class Node implements Comparable<Node> {

        public Point start;
        public Point end;
        public double slope;

        public Node(Point start, Point end) {
            this.start = start;
            this.end = end;
            this.slope = start.slopeTo(end);
        }

        public int compareTo(Node node) {
            int flag = Double.compare(this.slope, node.slope);
            if (0 == flag) {
                double slope1 = this.start.slopeTo(node.start);
                double slope2 = this.start.slopeTo(node.end);
                if (slope1 == Double.NEGATIVE_INFINITY || slope2 == Double.NEGATIVE_INFINITY) {
                    return 0;
                } else {
                    return Double.compare(slope1, slope2);
                }
            }
            return flag;
        }
    }

    public int numberOfSegments() {
        return this.size;
    }

    public LineSegment[] segments() {
        return this.segments;
    }

    private void checkCollinear(Point[] inPoints) {
        for (int i = 0; i < inPoints.length; i++) {
            int tmpPointsNum = 0;

            Point[] sortPoint = new Point[inPoints.length - 1];
            for (int j = 0; j < inPoints.length; j++) {
                if (i != j) {
                    sortPoint[tmpPointsNum++] = inPoints[j];
                }
            }

            Comparator<Point> comparator = inPoints[i].slopeOrder();
            Arrays.sort(sortPoint, comparator);
            int start = 0;
            int end = 0;
            for (int j = 0; j < sortPoint.length - 1; j++) {
                if (0 == comparator.compare(sortPoint[j], sortPoint[j + 1])) {
                    end = j + 1;
                } else {
                    if ((end - start) > 1) {
                        Point[] collinearPoints = new Point[end - start + 2];
                        collinearPoints[0] = inPoints[i];
                        for (int k = 0; k < collinearPoints.length - 1; k++) {
                            collinearPoints[k + 1] = sortPoint[start + k];
                        }
                        addSegment(collinearPoints);
                    }
                    start = j + 1;
                    end = j + 1;
                }
            }
            if ((end - start) > 1) {
                Point[] collinearPoints = new Point[end - start + 2];
                collinearPoints[0] = inPoints[i];
                for (int k = 0; k < collinearPoints.length - 1; k++) {
                    collinearPoints[k + 1] = sortPoint[start + k];
                }
                addSegment(collinearPoints);
            }
        }
    }

    private void addSegment(Point[] points) {

        Point start = points[0];
        Point end = points[0];

        for (int i = 1; i < points.length; i++) {
            if (less(points[i], start)) {
                start = points[i];
            }
        }

        for (int i = 1; i < points.length; i++) {
            if (more(points[i], end)) {
                end = points[i];
            }
        }

        Node node = new Node(start, end);

        if (0 != this.size) {
            for (int i = 0; i < this.size; i++) {
                if (0 == this.nodes[i].compareTo(node)) {
                    return;
                }
            }

        }


        if (this.size == this.nodes.length) {
            resizing(this.size * 2);
        }

        this.nodes[this.size++] = node;
    }

    private void resizing(int capacity) {
        Node[] copy = new Node[capacity];
        for (int i = 0; i < nodes.length; i++) {
            copy[i] = this.nodes[i];
        }
        this.nodes = copy;
    }

    private boolean less(Point p, Point q) {
        return p.compareTo(q) < 0;
    }

    private boolean more(Point p, Point q) {
        return p.compareTo(q) > 0;
    }
}
