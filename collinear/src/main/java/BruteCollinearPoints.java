import java.util.Comparator;

/**
 * Created by Arthur on 2018/4/6.
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int size;

    public BruteCollinearPoints(Point[] inPoints) {
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

        this.size = 0;
        segments = new LineSegment[2];
        this.checkCollinear(inPoints);
    }

    public int numberOfSegments() {
        return this.size;
    }

    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[this.size];
        for (int i = 0; i < this.size; i++) {
            segs[i] = segments[i];
        }
        return segs;
    }

    private void checkCollinear(Point[] points) {
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Comparator<Point> comp = points[m].slopeOrder();
                        if (0 == comp.compare(points[i], points[j]) && 0 == comp.compare(points[i], points[k])) {
                            addSegment(points[i], points[j], points[k], points[m]);
                        }
                    }
                }
            }
        }
    }

    private void addSegment(Point... points) {

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
        if (this.size == segments.length) {
            resizing(this.size * 2);
        }
        segments[this.size++] = new LineSegment(start, end);
    }

    private void resizing(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < segments.length; i++) {
            copy[i] = segments[i];
        }
        segments = copy;
    }

    private boolean less(Point p, Point q) {
        return p.compareTo(q) < 0;
    }

    private boolean more(Point p, Point q) {
        return p.compareTo(q) > 0;
    }

}
