import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegmentsArray;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
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
        ArrayList<Point> savedFirstPoints = new ArrayList<>();
        ArrayList<Point> savedLastPoints = new ArrayList<>();
        ArrayList<Double> savedSlope = new ArrayList<>();

        double prevSlope = 0;
        double currentSlope = 0;
        int numberOfPoints = 0;
        boolean lineEdited = false;
        Point lastGoodPoint = new Point(0, 0);

        for (Point selectedPoint : points) {
            Arrays.sort(duplicatePoints, selectedPoint.slopeOrder());

            for (int j = 0; j < points.length; j++) {
                currentSlope = selectedPoint.slopeTo(duplicatePoints[j]);

                if (j == 0 || (currentSlope != prevSlope) || (j == points.length - 1)) {
                    if ((j == points.length - 1) && (currentSlope == prevSlope)) {
                        lastGoodPoint = duplicatePoints[j];
                        numberOfPoints++;
                    }

                    if (numberOfPoints >= 2) {
                        Point newFirstPoint;
                        Point newLastPoint;

                        for (int segmentsFound = 0; segmentsFound < lineSegments.size();
                             segmentsFound++) {
                            // StdOut.println("ADD? : " + selectedPoint + " with slope " + prevSlope
                            //                        + " compared to " + savedFirstPoints
                            //         .get(segmentsFound) + " with slope " + savedSlope
                            //         .get(segmentsFound) + " compared " + selectedPoint
                            //         .slopeTo(savedFirstPoints.get(segmentsFound)));

                            if (((selectedPoint.slopeTo(savedFirstPoints.get(segmentsFound))
                                    == savedSlope.get(segmentsFound))
                                    || (selectedPoint.slopeTo(savedFirstPoints.get(segmentsFound))
                                    == Double.NEGATIVE_INFINITY)) && (
                                    savedSlope.get(segmentsFound) == prevSlope)) {

                                // StdOut.println("CHANGED");

                                if (selectedPoint.compareTo(savedFirstPoints.get(segmentsFound))
                                        < 0) {
                                    newFirstPoint = selectedPoint;
                                }
                                else {
                                    newFirstPoint = savedFirstPoints.get(segmentsFound);
                                }

                                if (selectedPoint.compareTo(savedLastPoints.get(segmentsFound))
                                        > 0) {
                                    newLastPoint = selectedPoint;
                                }
                                else {
                                    newLastPoint = savedLastPoints.get(segmentsFound);
                                }

                                lineSegments.set(segmentsFound,
                                                 new LineSegment(newFirstPoint, newLastPoint));
                                savedFirstPoints.set(segmentsFound, newFirstPoint);
                                savedLastPoints.set(segmentsFound, newLastPoint);

                                lineEdited = true;
                            }
                        }

                        if (!lineEdited) {
                            // StdOut.println("ADDED");

                            if (selectedPoint.compareTo(lastGoodPoint) > 0) {
                                newFirstPoint = lastGoodPoint;
                                newLastPoint = selectedPoint;
                            }
                            else {
                                newFirstPoint = selectedPoint;
                                newLastPoint = lastGoodPoint;
                            }

                            lineSegments.add(new LineSegment(newFirstPoint, newLastPoint));
                            savedFirstPoints.add(newFirstPoint);
                            savedLastPoints.add(newLastPoint);
                            savedSlope.add(prevSlope);
                        }
                        else {
                            lineEdited = false;
                        }
                    }

                    numberOfPoints = 0;
                    prevSlope = selectedPoint.slopeTo(duplicatePoints[j]);
                }
                else {
                    lastGoodPoint = duplicatePoints[j];
                    numberOfPoints++;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
