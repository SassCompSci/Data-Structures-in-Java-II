package algs32.kdtree;
import algs12.Point2D;
import algs32.kdtree.RectHV;
import java.util.ArrayList;
import java.util.List;
import stdlib.*;


/* a set of points implemented as kd-tree */
public class KdTree {

	private static class Node {
		public Point2D point; // the point
		public RectHV rect; // the axis-aligned rectangle corresponding to this
		public Node lb; // the left/bottom subtree
		public Node rt; // the right/top subtree
		public int size;
		public double x = 0;
		public double y = 0;
		public Node(Point2D p, RectHV rect, Node lb, Node rt) {
			super();
			this.point = p;
			this.rect = rect;
			this.lb = lb;
			this.rt = rt;
			x = p.x();
			y = p.y();
		}
		
	}
	private Node root = null;

	/* construct an empty set of points */
	public KdTree() {
	}

	/* is the set empty? */
	public boolean isEmpty() {
		return root == null;
	}
	
	public int size() {
        return rechnenSize(root);
    }

    private int rechnenSize(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.size;
        }
    }

	/* add the point p to the set (if it is not already in the set) */
	public void insert(Point2D p) {
		if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            root = insertInternal(p, root, 0);
            root.rect = new RectHV(0, 0, 1, 1);
        } else {
            root = insertInternal(p, root, 1);
        }
	}
	
	private Node insertInternal(Point2D pointToInsert, Node node, int level) {
        if (node == null) {
            Node newNode = new Node(pointToInsert, null, null, null);
            newNode.size = 1;
            return newNode;
        }
        if (level % 2 == 0) {//Horizontal partition line
            if (pointToInsert.y() < node.y) {//Traverse in bottom area of partition
                node.lb = insertInternal(pointToInsert, node.lb, level + 1);
                if(node.lb.rect == null){
                    node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                            node.rect.xmax(), node.y);
                }
            } else {//Traverse in top area of partition
                if (!node.point.equals(pointToInsert)) {
                    node.rt = insertInternal(pointToInsert, node.rt, level + 1);
                    if(node.rt.rect == null){
                        node.rt.rect = new RectHV(node.rect.xmin(), node.y,
                                node.rect.xmax(), node.rect.ymax());
                    }
                }
            }

        } else if (level % 2 != 0) {//Vertical partition line
            if (pointToInsert.x() < node.x) {//Traverse in left area of partition
                node.lb = insertInternal(pointToInsert, node.lb, level + 1);
                if(node.lb.rect == null){
                    node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                            node.x, node.rect.ymax());
                }
            } else {//Traverse in right area of partition
                if (!node.point.equals(pointToInsert)) {
                    node.rt = insertInternal(pointToInsert, node.rt, level + 1);
                    if(node.rt.rect == null){
                        node.rt.rect = new RectHV(node.x, node.rect.ymin(),
                                node.rect.xmax(), node.rect.ymax());
                    }
                }
            }
        }
        node.size = 1 + rechnenSize(node.lb) + rechnenSize(node.rt);
        return node;
    }

	/* does the set contain the point p? */
	public boolean contains(Point2D target) {
		return containsInternal(target, root, 1);
	}
	
	private boolean containsInternal(Point2D pointToSearch, Node node, int level) {
        if (node == null) {
            return false;
        }
        if (level % 2 == 0) {//Horizontal partition line
            if (pointToSearch.y() < node.y) {
                return containsInternal(pointToSearch, node.lb, level + 1);
            } else {
                if (node.point.equals(pointToSearch)) {
                    return true;
                }
                return containsInternal(pointToSearch, node.rt, level + 1);
            }
        } else {//Vertical partition line
            if (pointToSearch.x() < node.x) {
                return containsInternal(pointToSearch, node.lb, level + 1);
            } else {
                if (node.point.equals(pointToSearch)) {
                    return true;
                }
                return containsInternal(pointToSearch, node.rt, level + 1);
            }
        }

    }

	/* all points in the set that are inside the target rectangle */
	public Iterable<Point2D> range(RectHV target) {
		List<Point2D> resultList = new ArrayList<Point2D>();
        rangeInternal(root, target, resultList);
        return resultList;
	}
	
	private void rangeInternal(Node node, RectHV rect, List<Point2D> resultList) {
        if (node == null) {
            return;
        }
        if (node.rect.intersects(rect)) {
            if (rect.contains(node.point)) {
                resultList.add(node.point);
            }
            rangeInternal(node.lb, rect, resultList);
            rangeInternal(node.rt, rect, resultList);
        }

    }

	/* a nearest neighbor to target point; null if empty */
	public Point2D nearest(Point2D target) {
		if(root == null){
            return null;
        }
        Champion champion = new Champion(root.point,Double.MAX_VALUE);
        return nearestInternal(target, root, champion, 1).champion;
    }

    private Champion nearestInternal(Point2D targetPoint, Node node,
            Champion champion, int level) {
        if (node == null) {
            return champion;
        }
        double dist = targetPoint.distanceSquaredTo(node.point);
        int newLevel = level + 1;
        if (dist < champion.championDist) {
            champion.champion = node.point;
            champion.championDist = dist;
        }
        boolean goLeftOrBottom = false;
        //We will decide which part to be visited first, based upon in which part point lies.
        //If point is towards left or bottom part, we traverse in that area first, and later on decide
        //if we need to search in other part too.
        if(level % 2 == 0){
            if(targetPoint.y() < node.y){
                goLeftOrBottom = true;
            }
        } else {
            if(targetPoint.x() < node.x){
                goLeftOrBottom = true;
            }
        }
        if(goLeftOrBottom){
            nearestInternal(targetPoint, node.lb, champion, newLevel);
            Point2D orientationPoint = createOrientationPoint(node.x,node.y,targetPoint,level);
            double orientationDist = orientationPoint.distanceSquaredTo(targetPoint);
            //We will search on the other part only, if the point is very near to partitioned line
            //and champion point found so far is far away from the partitioned line.
            if(orientationDist < champion.championDist){
                nearestInternal(targetPoint, node.rt, champion, newLevel);
            }
        } else {
            nearestInternal(targetPoint, node.rt, champion, newLevel);
            Point2D orientationPoint = createOrientationPoint(node.x,node.y,targetPoint,level);
            //We will search on the other part only, if the point is very near to partitioned line
            //and champion point found so far is far away from the partitioned line.
            double orientationDist = orientationPoint.distanceSquaredTo(targetPoint);
            if(orientationDist < champion.championDist){
                nearestInternal(targetPoint, node.lb, champion, newLevel);
            }

        }
        return champion;
	}
    
    /**
     * Returns the point from a partitioned line, which can be directly used to calculate
     * distance between partitioned line and the target point for which neighbors are to be searched.
     * @param linePointX
     * @param linePointY
     * @param targetPoint
     * @param level
     * @return
     */
    private Point2D createOrientationPoint(double linePointX, double linePointY, Point2D targetPoint, int level){
        if(level % 2 == 0){
            return new Point2D(targetPoint.x(),linePointY);
        } else {
            return new Point2D(linePointX,targetPoint.y());
        }
    }

    private static class Champion{
        public Point2D champion;
        public double championDist;
        public Champion(Point2D c, double d){
            champion = c;
            championDist = d;
        }
    }

	/* draw all of the points to standard draw */
	/* for x-node, use red line to draw the division between left/right */
	/* for y-node, use blue line to draw the division between top/bottom */
	/* see the writeup for examples */
	public void draw() {
		StdDraw.clear();
		drawInternal(root, 1);
	}
	
	private void drawInternal(Node node, int level) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        node.point.draw();
        double sx = node.rect.xmin();
        double ex = node.rect.xmax();
        double sy = node.rect.ymin();
        double ey = node.rect.ymax();
        StdDraw.setPenRadius(0.01);
        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            sy = ey = node.y;
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            sx = ex = node.x;
        }
        StdDraw.line(sx, sy, ex, ey);
        drawInternal(node.lb, level + 1);
        drawInternal(node.rt, level + 1);
    }

	/*  draw the tree as a tree */
	public void drawTree() {
		// OPTIONAL
	}

	/*  draw the tree as a tree using graphviz */
	public void toGraphviz(String filename) {
		// OPTIONAL
	}


	/* some test code */
	private static void insert5Points (KdTree kdtree, double xoffset, double yoffset) {
		kdtree.insert (new Point2D (0.7 + xoffset, 0.2 + yoffset));
		kdtree.insert (new Point2D (0.5 + xoffset, 0.4 + yoffset));
		kdtree.insert (new Point2D (0.2 + xoffset, 0.3 + yoffset));
		kdtree.insert (new Point2D (0.4 + xoffset, 0.7 + yoffset));
		kdtree.insert (new Point2D (0.9 + xoffset, 0.6 + yoffset));
	}
	public static void main (String[] args) {
		KdTree kdtree = new KdTree ();
		insert5Points (kdtree, 0.0, 0.0); // after this there should be 5 points
		insert5Points (kdtree, 0.0, 0.0); // after doing it twice there should still be 5
		insert5Points (kdtree, 0.1, 0.0); // this should add 5 more points
		insert5Points (kdtree, 0.0, 0.1); // this should add 5 more points


		//        kdtree.insert (new Point2D(0.15, 0.18));
		//        kdtree.insert (new Point2D(0.86, 0.26));
		//        kdtree.insert (new Point2D(0.70, 0.11));
		//        kdtree.insert (new Point2D(0.16, 0.01));
		//        kdtree.insert (new Point2D(0.62, 0.95));
		//        kdtree.insert (new Point2D(0.98, 0.04));
		//        kdtree.insert (new Point2D(0.87, 0.79));
		//        kdtree.insert (new Point2D(0.83, 0.21));

		//        Point2D mid = new Point2D (0.5, 0.5);
		//        Point2D less = new Point2D (0.5, 0.4);
		//        Point2D more = new Point2D (0.5, 0.6);
		//        kdtree.insert (mid);
		//        kdtree.insert (less);
		//        kdtree.insert (more);
		//        StdOut.println (kdtree.contains (less));
		//        StdOut.println (kdtree.contains (more));
		//        StdOut.println (kdtree.range (new RectHV (0.5, 0.4, 0.5, 0.6)));
		//        StdOut.println (kdtree.nearest (less));
		//        StdOut.println (kdtree.nearest (more));

		StdOut.println (kdtree);
		// kdtree.toGraphviz ("g.png");
		// kdtree.drawTree ();
		kdtree.draw ();
	}
}
