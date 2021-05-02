package algs32;
import algs13.Queue;
import stdlib.*;

/* ***********************************************************************
 * A simple BST with int keys and no values
 *
 * Complete each function below.
 * Write each function as a separate recursive definition (do not use more than one helper per function).
 * Depth of root==0.
 * Height of leaf==0.
 * Size of empty tree==0.
 * Height of empty tree=-1.
 *
 * TODO: complete the functions in this file.
 * DO NOT change the Node class.
 * DO NOT change the name or type of any function (the first line of the function)
 *
 * Restrictions:
 *  - no loops, except in printLeftI (you cannot use "while" "for" etc...)
 *  - only one helper function per definition
 *  - no fields (static or non static)
 *************************************************************************/
public class MyIntSET {
	private Node root;
	private static class Node {
		public final int key;
		public Node left, right;
		public Node(int key) { this.key = key; }
	}

	public void printLeftI () {
		Node x = root;
		while (x != null) {
			StdOut.println(x.key);
			x = x.left;
		}
	}

	// the number of nodes in the tree
	public int size() {
		// TODO
		return 0;
	}

	// Recall the definitions of height and depth.
	// in the BST with level order traversal "41 21 61 11 31",
	//   node 41 has depth 0, height 2
	//   node 21 has depth 1, height 1
	//   node 61 has depth 1, height 0
	//   node 11 has depth 2, height 0
	//   node 31 has depth 2, height 0
	// height of the whole tree is the height of the root

	// the height of the tree
	public int height() {
		// TODO
		return 0;
	}

	// the number of nodes with odd keys
	public int sizeOdd() {
		// TODO
		return 0;
	}

	// The next three functions compute the size of the tree at depth k.
	// It should be the case that for any given k,
	//
	//   sizeAbove(k) + sizeAt(k) + sizeBelow(k) = size()
	//
	// The words "above" and "below" assume that the root is at the "top".
	//
	// Suppose we have with size N and height H (so max depth also H).
	// For such a tree, we expect
	//
	//   sizeAboveDepth (-1)  = 0
	//   sizeAtDepth    (-1)  = 0
	//   sizeBelowDepth (-1)  = N
	//
	//   sizeAboveDepth (0)   = 0
	//   sizeAtDepth    (0)   = 1
	//   sizeBelowDepth (0)   = N-1
	//
	//   sizeAboveDepth (H+1) = N
	//   sizeAtDepth    (H+1) = 0
	//   sizeBelowDepth (H+1) = 0
	//
	// the number of nodes in the tree, at exactly depth k
	// include node n if depth(n) == k
	public int sizeAtDepth(int k) {
		// TODO
		return 0;
	}

	// the number of nodes in the tree, "above" depth k (not including k)
	// include node n if depth(n) < k
	public int sizeAboveDepth(int k) {
		// TODO
		return 0;
	}

	// the number of nodes in the tree, "below" depth k (not including k)
	// include node n if depth(n) > k
	public int sizeBelowDepth(int k) {
		// TODO
		return 0;
	}

	// tree is perfect if for every node, size of left == size of right
	// hint: in the helper, return -1 if the tree is not perfect, otherwise return the size
	public boolean isPerfectlyBalancedS() {
		// TODO
		return false;
	}

	// tree is perfect if for every node, height of left == height of right
	// hint: in the helper, return -2 if the tree is not perfect, otherwise return the height
	public boolean isPerfectlyBalancedH() {
		// TODO
		return false;
	}

	// tree is odd-perfect if for every node, #odd descendant on left == # odd descendants on right
	// A node is odd if it has an odd key
	// hint: in the helper, return -1 if the tree is not odd-perfect, otherwise return the odd size
	public boolean isOddBalanced() {
		// TODO
		return false;
	}

	// tree is semi-perfect if every node is semi-perfect
	// A node with 0 children is semi-perfect.
	// A node with 1 child is NOT semi-perfect.
	// A node with 2 children is semi-perfect if (size-of-larger-child <= size-of-smaller-child * 3)
	// hint: in the helper, return -1 if the tree is not semi-perfect, otherwise return the size
	public boolean isSemiBalanced() {
		// TODO
		return false;
	}

	/*
	 * Mutator functions
	 * HINT: all of these are easier to write if the helper function returns Node, rather than void.
	 */

	// remove all subtrees with odd roots (if node is odd, remove it and its descendants)
	// A node is odd if it has an odd key
	// If the root is odd, then you should end up with the empty tree
	public void removeOddSubtrees () {
		// TODO
	}

	// remove all subtrees below depth k from the original tree
	public void removeBelowDepth(int k) {
		// TODO
	}

	// add a child with key=0 to all nodes that have only one child
	// (you do not need to retain symmetric order or uniqueness of keys, obviously)
	public void addZeroToSingles() {
		// TODO
	}

	// remove all leaves from the original tree
	// if you start with "41", then the result is the empty tree.
	// if you start with "41 21 61", then the result is the tree "41"
	// if you start with the BST "41 21 11  1", then the result is the tree "41 21 11"
	// if you start with the BST "41 21 61 11", then the result is the tree "41 21"
	// Hint: This requires that you check for "leafiness" before the recursive calls
	public void removeLeaves() {
		// TODO
	}

	// remove all nodes that have only one child by "promoting" that child
	// repeat this recursively as you go up, so the final result should have no nodes with only one child
	// if you start with "41", the tree is unchanged.
	// if you start with "41 21 61", the tree is unchanged.
	// if you start with the BST "41 21 11  1", then the result is the tree "1"
	// if you start with the BST "41 21 61 11", then the result is the tree "41 11 61"
	// Hint: This requires that you check for "singleness" after the recursive calls
	public void removeSingles() {
		// TODO
	}

	/* ***************************************************************************
	 * Some methods to create and display trees
	 *****************************************************************************/

	// Do not modify "put"
	public void put(int key) { root = put(root, key); }
	private Node put(Node n, int key) {
		if (n == null) return new Node(key);
		if      (key < n.key) n.left  = put(n.left,  key);
		else if (key > n.key) n.right = put(n.right, key);
		return n;
	}
	// This is what contains looks like
	public boolean contains(int key) { return contains(root, key); }
	private boolean contains(Node n, int key) {
		if (n == null) return false;
		if      (key < n.key) return contains(n.left,  key);
		else if (key > n.key) return contains(n.right, key);
		return true;
	}
	// Do not modify "copy"
	public MyIntSET copy () {
		MyIntSET that = new MyIntSET ();
		for (int key : levelOrder())
			that.put (key);
		return that;
	}
	// Do not modify "levelOrder"
	public Iterable<Integer> levelOrder() {
		Queue<Integer> keys = new Queue<>();
		Queue<Node> queue = new Queue<>();
		queue.enqueue(root);
		while (!queue.isEmpty()) {
			Node n = queue.dequeue();
			if (n == null) continue;
			keys.enqueue(n.key);
			queue.enqueue(n.left);
			queue.enqueue(n.right);
		}
		return keys;
	}
	// Do not modify "toString"
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int key: levelOrder())
			sb.append (key + " ");
		return sb.toString ();
	}
	// You may modify "toGraphviz" if you wish
	public void toGraphviz(String filename) {
		GraphvizBuilder gb = new GraphvizBuilder ();
		toGraphviz (gb, null, root);
		gb.toFileUndirected (filename, "ordering=\"out\"");
	}
	private static void toGraphviz (GraphvizBuilder gb, Node parent, Node n) {
		if (n == null) { gb.addNullEdge (parent); return; }
		gb.addLabeledNode (n, Integer.toString (n.key));
		if (parent != null) gb.addEdge (parent, n);
		toGraphviz (gb, n, n.left);
		toGraphviz (gb, n, n.right);
	}

	// You may modify "drawTree" if you wish
	public void drawTree() {
		if (root != null) {
			StdDraw.setPenColor (StdDraw.BLACK);
			StdDraw.setCanvasSize(1200,700);
			drawTree(root, .5, 1, .25, 0);
		}
	}
	private static void drawTree (Node n, double x, double y, double range, int depth) {
		int CUTOFF = 10;
		StdDraw.text (x, y, Integer.toString (n.key));
		StdDraw.setPenRadius (.007);
		if (n.left != null && depth != CUTOFF) {
			StdDraw.line (x-range, y-.08, x-.01, y-.01);
			drawTree (n.left, x-range, y-.1, range*.5, depth+1);
		}
		if (n.right != null && depth != CUTOFF) {
			StdDraw.line (x+range, y-.08, x+.01, y-.01);
			drawTree (n.right, x+range, y-.1, range*.5, depth+1);
		}
	}
	/* ***************************************************************************
	 *  Test client
	 *  You can modify any of these methods, if you wish
	 *****************************************************************************/
	// create a tree from a string
	public static MyIntSET fromString (String ints) {
		MyIntSET set = new MyIntSET ();
		for (String s : ints.split (" "))
			try { set.put (Integer.parseInt (s)); } catch (NumberFormatException e) {}
		return set;
	}
	public static void show(MyIntSET set, String filename) {
		// comment out the next line to stop drawing the trees
		//set.drawTree (); StdDraw.show (2000);
		set.toGraphviz ("x" + exampleCount + "-" + filename + ".png");
	}
	static int exampleCount = 0;
	public static void test(String s) {
		exampleCount++;
		MyIntSET set = MyIntSET.fromString(s);
		StdOut.println ("###############################################################");
		StdOut.format ("tree: %s\n", set); show(set, "original");

		//        StdOut.format ("size()                 = %d\n", set.size());
		//        StdOut.format ("height()               = %d\n", set.height());
		//        StdOut.format ("sizeOdd()              = %d\n", set.sizeOdd());
		//        StdOut.format ("sizeAtDepth(2)         = %d\n", set.sizeAtDepth(2));
		//        StdOut.format ("sizeAboveDepth(2)      = %d\n", set.sizeAboveDepth(2));
		//        StdOut.format ("sizeBelowDepth(2)      = %d\n", set.sizeBelowDepth(2));
		//        StdOut.format ("isPerfectlyBalancedS() = %b\n", set.isPerfectlyBalancedS());
		//        StdOut.format ("isPerfectlyBalancedH() = %b\n", set.isPerfectlyBalancedH());
		//        StdOut.format ("isOddBalanced()        = %b\n", set.isOddBalanced());
		//        StdOut.format ("isSemiBalanced()       = %b\n", set.isSemiBalanced());
		//
		//        MyIntSET tmp;
		//        tmp = set.copy(); tmp.removeOddSubtrees(); StdOut.format ("removeOddSubtrees()  %s\n", tmp); show(tmp, "removeOddSubtrees");
		//        tmp = set.copy(); tmp.removeBelowDepth(2); StdOut.format ("removeBelowDepth(2)  %s\n", tmp); show(tmp, "removeBelowDepth2");
		//        tmp = set.copy(); tmp.removeLeaves();      StdOut.format ("removeLeaves()       %s\n", tmp); show(tmp, "removeLeaves");
		//        tmp = set.copy(); tmp.removeSingles();     StdOut.format ("removeSingles()      %s\n", tmp); show(tmp, "removeSingles");
		//        tmp = set.copy(); tmp.addZeroToSingles();  StdOut.format ("addZeroToSingles()   %s\n", tmp); show(tmp, "addZeroToSingles");
	}
	public static void hw0 () {
		MyIntSET set = MyIntSET.fromString("41 21 61 11 31");
		StdOut.format ("tree: %s\n", set);
		set.toGraphviz ("x.png");
		set.printLeftI ();
	}
	public static void hw1and2 () {
		// Note that tall trees will not visualize correctly in the Java graphics window
		// For tall trees, look at the graphviz output, or just draw it yourself.
		test ("41 21 61 11 31");
		test ("41 21 61 11 31 51 71 111");
		//        test ("101 41 21 61 11 31 51 71 111");
		//        test ("101 41 21 61 11 31 51 71 141 121 161 111 131 151 171 ");
		//        test ("101 40 20 61 11 31 51 71 140 120 161 111 131 151 171 ");
		//        test ("90 30 100 10 81 20 40 60 50 70 62 63 64");
		//        test ("40 20 61 10 30 50 70");
		//        test ("41 21 61 11 30 51 70");
		//        test ("");
		//        test ("1");
		//        test ("90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");
	}
	public static void playground () {
		Trace.drawStepsOfMethod ("printLeftI");
		Trace.showNullFields (true);
		Trace.drawSteps ();
		Trace.run ();

		MyIntSET set1 = MyIntSET.fromString ("41 21 61 11 31");
		set1.printLeftI ();
		//        set1.toGraphviz ("x.png");
		//        MyIntSET set2 = MyIntSET.fromString ("41 21 61 11 31 51 71 111");
		//        set2.toGraphviz ("y.png");
	}
	public static void main(String[] args) {
		//hw0 ();
		playground();
		//hw1and2 ();
	}
}
