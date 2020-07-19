import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegmentsArray;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null || points.length < 4) {
            throw new IllegalArgumentException();
        }

        Point[] duplicatePoints = Arrays.copyOf(points, points.length);

        Arrays.sort(duplicatePoints);

        Point prevPoint = duplicatePoints[points.length - 1];

        for (Point selectedPoint : duplicatePoints) {
            if (selectedPoint == null) {
                throw new IllegalArgumentException();
            }

            if (prevPoint.compareTo(selectedPoint) == 0) {
                throw new IllegalArgumentException();
            }

            prevPoint = selectedPoint;
        }

        double commonSlope = 0;
        boolean segmentExists = false;
        Point firstPoint = new Point(0, 0);
        Point lastPoint = new Point(0, 0);

        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        ArrayList<Point> savedPoints = new ArrayList<>();
        ArrayList<Double> savedSlope = new ArrayList<>();

        for (int i = 0; i < duplicatePoints.length - 3; i++) {
            for (int j = i + 1; j < duplicatePoints.length - 2; j++) {
                for (int k = j + 1; k < duplicatePoints.length - 1; k++) {
                    segmentExists = false;
                    for (int m = k + 1; m < duplicatePoints.length; m++) {
                        if (m == (k + 1)) {
                            if (duplicatePoints[i].slopeTo(duplicatePoints[j]) != duplicatePoints[i]
                                    .slopeTo(duplicatePoints[k])) {
                                break;
                            }

                            commonSlope = duplicatePoints[i].slopeTo(duplicatePoints[j]);
                        }

                        if (duplicatePoints[i].slopeTo(duplicatePoints[m]) == commonSlope) {
                            // StdOut.println("i " + i + " j " + j + " k " + k + " m " + m);
                            segmentExists = true;
                            firstPoint = duplicatePoints[i];
                            lastPoint = duplicatePoints[m];
                        }
                    }

                    boolean newSegment = false;

                    if (segmentExists) {
                        for (int segmentsFound = 0; segmentsFound < lineSegments.size();
                             segmentsFound++) {
                            if (savedPoints.get(segmentsFound) == lastPoint
                                    && savedSlope.get(segmentsFound) == commonSlope) {
                                // StdOut.println("duplicate");
                                newSegment = false;
                                break;
                            }

                            newSegment = true;
                        }

                        if ((lineSegments.size() == 0) || newSegment) {
                            lineSegments.add(new LineSegment(firstPoint, lastPoint));
                            // StdOut.println("ADDED " + firstPoint + " " + lastPoint);
                            savedPoints.add(lastPoint);
                            savedSlope.add(commonSlope);
                        }
                    }
                }
            }
        }

        lineSegmentsArray = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentsArray.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegmentsArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdOut.println(collinear.numberOfSegments());
        StdDraw.show();
    }
}
