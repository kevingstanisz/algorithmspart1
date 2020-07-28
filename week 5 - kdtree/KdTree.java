/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node kdRoot;
    private int setSize;
    private Point2D storeClosest;

    // private node class to store left/bottom and right/top
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int treeLevel;

        private Node(Point2D p, Node parent) {
            this.p = p;
            this.lb = null;
            this.rt = null;


            if (parent == null) {
                this.treeLevel = 0;
                this.rect = new RectHV(0, 0, 1, 1);
            }
            else {
                this.treeLevel = parent.treeLevel + 1;
                if (parent.treeLevel % 2 == 0) {
                    if (p.x() < parent.p.x()) {
                        this.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                               parent.p.x(), parent.rect.ymax());
                    }
                    else {
                        this.rect = new RectHV(parent.p.x(), parent.rect.ymin(),
                                               parent.rect.xmax(), parent.rect.ymax());
                    }
                }
                else {
                    if (p.y() < parent.p.y()) {
                        this.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                               parent.rect.xmax(), parent.p.y());
                    }
                    else {
                        this.rect = new RectHV(parent.rect.xmin(), parent.p.y(),
                                               parent.rect.xmax(), parent.rect.ymax());
                    }
                }
            }
        }
    }

    // construct an empty set of points
    public KdTree() {
        kdRoot = null;
        setSize = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return setSize == 0;
    }

    // number of points in the set
    public int size() {
        return setSize;
    }

    private boolean validPoint(Point2D p) {
        if (p == null) {
            return false;
        }

        if (p.x() < 0 || p.x() > 1 || p.y() < 0 || p.y() > 1) {
            return false;
        }

        return true;
    }

    private boolean isLeftBottom(Point2D currentPoint, Node parentNode) {
        return ((parentNode.treeLevel % 2 == 0) && (currentPoint.x() < parentNode.p.x())) || (
                (parentNode.treeLevel % 2 == 1) && (currentPoint.y() < parentNode.p.y()));
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!validPoint(p)) {
            throw new IllegalArgumentException();
        }

        // need to check if it contains
        // StdOut.println("INSERTING");
        if (kdRoot == null || !contains(p)) {
            setSize++;
            kdRoot = insert(p, kdRoot);
        }
    }

    private Node insert(Point2D point, Node parentNode) {
        if (parentNode == null) {
            // StdOut.println("root created");
            return new Node(point, parentNode);
        }

        boolean leftBottom = isLeftBottom(point, parentNode);

        if (leftBottom) {
            // StdOut.println("exploring left subtree");
            if (parentNode.lb == null) {
                parentNode.lb = new Node(point, parentNode);
            }
            else {
                parentNode.lb = insert(point, parentNode.lb);
            }
        }
        else {
            // StdOut.println("exploring right subtree");
            if (parentNode.rt == null) {
                parentNode.rt = new Node(point, parentNode);
            }
            else {
                parentNode.rt = insert(point, parentNode.rt);
            }
        }

        return parentNode;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (!validPoint(p)) {
            throw new IllegalArgumentException();
        }

        return contains(p, kdRoot) != null;
    }

    private Node contains(Point2D p, Node currentNode) {
        if (currentNode == null) {
            return null;
        }

        if (currentNode.p.equals(p)) {
            return currentNode;
        }

        boolean leftBottom = isLeftBottom(p, currentNode);

        if (leftBottom) {
            return contains(p, currentNode.lb);
        }
        else {
            return contains(p, currentNode.rt);
        }
    }

    // draw all points to standard draw
    public void draw() {
        if (kdRoot != null) {
            draw(kdRoot);
        }
    }

    private void draw(Node currentNode) {
        StdDraw.setPenRadius(0.005);

        if (currentNode.treeLevel % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(currentNode.p.x(), currentNode.rect.ymin(), currentNode.p.x(),
                         currentNode.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(currentNode.rect.xmin(), currentNode.p.y(), currentNode.rect.xmax(),
                         currentNode.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        currentNode.p.draw();

        if (currentNode.lb != null) {
            draw(currentNode.lb);
        }

        if (currentNode.rt != null) {
            draw(currentNode.rt);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        Stack<Point2D> pointsIn = new Stack<Point2D>();

        return range(kdRoot, rect, pointsIn);
    }

    private Stack<Point2D> range(Node currentNode, RectHV rect, Stack<Point2D> pointsIn) {
        if (currentNode == null) return pointsIn;

        if (currentNode.rect.intersects(rect)) {
            if (rect.contains(currentNode.p)) {
                pointsIn.push(currentNode.p);
            }

            range(currentNode.lb, rect, pointsIn);
            range(currentNode.rt, rect, pointsIn);
        }

        return pointsIn;
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (!validPoint(p)) {
            throw new IllegalArgumentException();
        }

        if (kdRoot == null) {
            return null;
        }

        storeClosest = new Point2D(kdRoot.p.x(), kdRoot.p.y());
        return nearest(p, kdRoot, kdRoot.p, kdRoot.p.distanceSquaredTo(p));
    }

    private Point2D nearest(Point2D p, Node currentNode, Point2D closestPoint,
                            double closestDistance) {


        if (currentNode == null) {
            // StdOut.println(
            //         "RETURNING, x: " + closestPoint.x() + " y: " + closestPoint
            //                 .y());
            return storeClosest;
        }

        if (currentNode.rect.distanceSquaredTo(p) < closestDistance) {
            // StdOut.println("reaches here");

            if (currentNode.p.distanceSquaredTo(p) < storeClosest.distanceSquaredTo(p)) {
                // StdOut.println(
                //         "found something closer, x: " + currentNode.p.x() + " y: " + currentNode.p
                //                 .y());

                storeClosest = currentNode.p;
                if (isLeftBottom(p, currentNode)) {
                    nearest(p, currentNode.lb, currentNode.p, currentNode.p.distanceSquaredTo(p));
                    nearest(p, currentNode.rt, currentNode.p, currentNode.p.distanceSquaredTo(p));
                }
                else {
                    nearest(p, currentNode.rt, currentNode.p, currentNode.p.distanceSquaredTo(p));
                    nearest(p, currentNode.lb, currentNode.p, currentNode.p.distanceSquaredTo(p));
                }
            }
            else {
                // StdOut.println(
                //         "found something NOT closer, x: " + closestPoint.x() + " y: " + closestPoint
                //                 .y());
                if (isLeftBottom(p, currentNode)) {
                    nearest(p, currentNode.lb, closestPoint, closestDistance);
                    nearest(p, currentNode.rt, closestPoint, closestDistance);
                }
                else {
                    nearest(p, currentNode.rt, closestPoint, closestDistance);
                    nearest(p, currentNode.lb, closestPoint, closestDistance);
                }
            }


        }

        // StdOut.println(
        //         "RETURNING, x: " + closestPoint.x() + " y: " + closestPoint
        //                 .y());
        return storeClosest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            // StdOut.println(kdtree.contains(new Point2D(0.4, 0.7)));
        }

    }
}
