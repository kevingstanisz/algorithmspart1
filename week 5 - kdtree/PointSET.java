/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> bruteForceSet;

    // construct an empty set of points
    public PointSET() {
        bruteForceSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return bruteForceSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return bruteForceSet.size();
    }

    private boolean validPoint(Point2D p) {
        if (p.x() < 0 || p.x() > 1 || p.y() < 0 || p.y() > 1) {
            return false;
        }

        return true;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!validPoint(p)) {
            throw new IllegalArgumentException();
        }

        bruteForceSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (!validPoint(p)) {
            throw new IllegalArgumentException();
        }

        return bruteForceSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D points : bruteForceSet) {
            points.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Stack<Point2D> pointsIn = new Stack<Point2D>();

        // might need to copy the rectangle so output doesn't change when Rec is later edited

        for (Point2D point : bruteForceSet) {
            if (rect.contains(point)) {
                pointsIn.push(point);
            }
        }

        return pointsIn;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (!validPoint(p)) {
            throw new IllegalArgumentException();
        }

        if (bruteForceSet.isEmpty()) {
            return null;
        }

        Point2D closestPoint = bruteForceSet.first();
        double closestDistance = -1;

        for (Point2D point : bruteForceSet) {
            if ((p.distanceSquaredTo(point) < closestDistance) || (closestDistance == -1)) {
                closestPoint = point;
                closestDistance = p.distanceSquaredTo(point);
            }
        }

        return closestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
