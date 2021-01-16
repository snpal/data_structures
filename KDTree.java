import java.util.List;

public class KDTree {

    private Node root;

    private class Node implements Comparable<Node> {
        private Point p;
        private boolean horizontal;
        private Node leftChild;
        private Node rightChild;

        Node(Point p, boolean horizontal) {
            this.p = p;
            this.horizontal = horizontal;
        }

        @Override
        public int compareTo(Node N) {
            if (N == null) {
                throw new NullPointerException();
            }
            return comparePoints(p, N.p, horizontal);
        }
    }

    private class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (other.getClass() != this.getClass()) {
                return false;
            }
            Point otherPoint = (Point) other;
            return getX() == otherPoint.getX() && getY() == otherPoint.getY();
        }
    }

    public KDTree(List<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            root = add(points.get(i), root, true);
        }
    }

    private Node add(Point p, Node N, boolean horizontal) {
        if (N == null) {
            return new Node(p, horizontal);
        }
        if (p.equals(N.p)) {
            return N;
        }
        int compared = comparePoints(p, N.p, N.horizontal);
        if (compared < 0) {
            N.leftChild = add(p, N.leftChild, !horizontal);
        } else if (compared >= 0) {
            N.rightChild = add(p, N.rightChild, !horizontal);
        }
        return N;

    }

    private int comparePoints(Point A, Point B, boolean horizontal) {
        if (horizontal) {
            return Double.compare(A.getX(), B.getX());
        } else {
            return Double.compare(A.getY(), B.getY());
        }
    }

    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Node nearestNode = nearestHelper(root, goal, root);
        return nearestNode.p;
    }

    private Node nearestHelper(Node N, Point goal, Node best) {
        if (N == null) {
            return best;
        }
        if (Point.distance(N.p, goal) < Point.distance(best.p, goal)) {
            best = N;
        }

        Node goodSide;
        Node badSide;

        if (N.compareTo(new Node(goal, true)) > 0) {
            goodSide = N.leftChild;
            badSide = N.rightChild;
        } else {
            goodSide = N.rightChild;
            badSide = N.leftChild;
        }

        best = nearestHelper(goodSide, goal, best);

        if (badSide != null) {
            Point badSideNearest;
            if (N.horizontal) {
                badSideNearest = new Point(N.p.getX(), goal.getY());
            } else {
                badSideNearest = new Point(goal.getX(), N.p.getY());
            }
            if (distance(badSideNearest, goal) <= distance(best.p, goal)) {
                best = nearestHelper(badSide, goal, best);
            }
        }
        return best;
    }

    private double distance(Point p1, Point p2) {
        return Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2);
    }

    public static void main(String[] args) {

    }
}
