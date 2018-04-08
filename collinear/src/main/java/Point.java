import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

/**
 * Created by Arthur on 2018/4/6.
 */
public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        if (x < 0 || x > Short.MAX_VALUE || y < 0 || y > Short.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public int compareTo(Point that) {
        if (y < that.y) {
            return -1;
        } else if (y == that.y && x < that.x) {
            return -1;
        } else if (y == that.y && x == that.x) {
            return 0;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else {
            return (double) (that.y - y) / (double) (that.x - x);
        }
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator(this);
    }

    private class SlopeComparator implements Comparator<Point> {

        private final Point p;

        public SlopeComparator(Point invokePoint) {
            this.p = invokePoint;

        }

        public int compare(Point o1, Point o2) {
            return Double.compare(this.p.slopeTo(o1), this.p.slopeTo(o2));

        }

    }

}
