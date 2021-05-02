package algs32;

import java.io.File;

import algs13.Queue;

import stdlib.*;

/* ***********************************************************************
 * A simple BST with int keys
 * 
 * Recall that:
 *   Depth of root==0.
 *   Height of leaf==0.
 *   Size of empty tree==0.
 *   Height of empty tree=-1.
 * 
 * TODO: complete the functions in this file.
 *
 * Restrictions:
 *  - DO NOT change the Node class.
 *  - DO NOT change the first line of any function: name, parameters, types.
 *  - you may add new functions, but don't delete anything
 *  - functions must be recursive, except printLeftI
 *  - no loops, except in printLeftI (you cannot use "while" "for" etc...)
 *  - each function must have exactly one recursive helper function, which you add
 *  - each function must be independent --- do not call any function other than the helper
 *  - no fields (variables declared outside of a function)
 *************************************************************************/
public class MyIntSET {
	private Node root;
	
	private static class Node {
		public final int key;
		public Node left, right;
		
		public Node(int key) { 
			this.key = key; 
		}

		private void removeHalfNodes(Node parent) {
			if ((this.left == null && this.right != null) || (this.left != null && this.right == null)) {
				if (parent.left == this) {
					parent.left = this.left != null ? this.left : this.right;
					parent.left.removeHalfNodes(parent);
				}

				if (parent.right == this) {
					parent.right = this.left != null ? this.left : this.right;
					parent.right.removeHalfNodes(parent);
				}
			} else {
				if (this.left != null)
					this.left.removeHalfNodes(this);

				if (this.right != null)
					this.right.removeHalfNodes(this);
			}
			
		}
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
    	return (size(root));
    }

    private int size(Node root) {
        if (root == null)
        	return 0;
        else {
        	return (size(root.left) + (size(root.right)) + 1);
        }

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
        return (height(root));
    }

    private int height(Node root) {
        if (root == null)
        	return 0;
        else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

	// the number of nodes with odd keys
    public int sizeOdd() {
        return (sizeOdd(root));
    }

    private int sizeOdd(Node root) {
        if (root == null)
        	return 0;
        else if (size(root.left) % 2 != 0 || size(root.right) % 2 != 0) {
            return (size(root.left) + (size(root.right)) + 1);
        } else {
            return 1;
        }

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
        return sizeAtDepth(root, k, 0);
    }

    public int sizeAtDepth(Node root, int depth, int level) {

        if (root == null) {
        	return 0;
        }

        if (level == depth) {
            return 1;
        }

        if (depth != level) {
            ++level;
        }

        return (sizeAtDepth(root.left, depth, level) + sizeAtDepth(root.right, depth, level));

    }

	// the number of nodes in the tree, "above" depth k (not including k)
	// include node n if depth(n) < k
	
    public int sizeAboveDepth(int k) {
        return sizeAboveDepth(root, k, 0);
    }

    public int sizeAboveDepth(Node root, int depth, int level) {
        if (root == null) {
        	return 0;
        }
        if (level == depth) {
        	return 1;
        }

        return (sizeAtDepth(level + 1) + sizeAboveDepth(root.right, depth - 1, level + 1));
    }

	// the number of nodes in the tree, "below" depth k (not including k)
	// include node n if depth(n) > k
	
    public int sizeBelowDepth(int k) {
        return sizeBelowDepth(root, k);
    }

    public int sizeBelowDepth(Node root, int currentLevel) {
        if (root == null) {
        	return 0;
        }

        if (currentLevel <  0) {
            return (sizeAtDepth(0) + sizeBelowDepth(root, 0));
        }
        if (sizeAtDepth(currentLevel) == 0) {
            return 0;
        }
       
        return (sizeAtDepth(currentLevel + 1) + sizeBelowDepth(root.right, currentLevel + 1));
    }

	// tree is perfect if for every node, size of left == size of right
	// hint: in the helper, return -1 if the tree is not perfect, otherwise return the size
	
    public boolean isPerfectlyBalancedS() {
        if(isPerfectlyBalancedS(root) == -1)
        	return false;
        else{return true;}
    }

    public int isPerfectlyBalancedS(Node root) {
        if(root == null)
        	return 0;
        if(isPerfectlyBalancedS(root.left) == -1 || isPerfectlyBalancedS(root.right) == -1)
            return -1;
        else if (isPerfectlyBalancedS(root.left) == isPerfectlyBalancedS(root.right))
            return 1 + isPerfectlyBalancedS(root.left) + isPerfectlyBalancedS(root.right);
        return -1;
}

	// tree is perfect if for every node, height of left == height of right
	// hint: in the helper, return -2 if the tree is not perfect, otherwise return the height
	
    public boolean isPerfectlyBalancedH() {
        if(isPerfectlyBalancedH(root) == -2)
        	return false;
        else{return true;}
    }

    public int isPerfectlyBalancedH(Node root) {
        if(root == null)
        	return 0;
        if (isPerfectlyBalancedH(root.left) == isPerfectlyBalancedH(root.right))
            return 1 + + isPerfectlyBalancedH(root.left) + isPerfectlyBalancedH(root.right);
        else {
            return -2;
        }
    }

	// tree is odd-perfect if for every node, #odd descendant on left == # odd descendants on right
	// A node is odd if it has an odd key
	// hint: in the helper, return -1 if the tree is not odd-perfect, otherwise return the odd size
    public boolean isOddBalanced() {
        if(isOddBalanced(root) == -1)
        	return false;
        else{return true;}
    }

    public int isOddBalanced(Node root) {
        if(root == null)
        	return 0;
        if(isOddBalanced(root.left) == -1 || isOddBalanced(root.right) == -1)
            return -1;
        else if ((isOddBalanced(root.left) == isOddBalanced(root.right)) && ((root.left == null && root.right != null) || ((root.left != null) == (root.right == null))))
            return 1 + isOddBalanced(root.left) + isOddBalanced(root.right);
        return -1;
    }

	// tree is semi-perfect if every node is semi-perfect
	// A node with 0 children is semi-perfect.
	// A node with 1 child is NOT semi-perfect.
	// A node with 2 children is semi-perfect if (size-of-larger-child <= size-of-smaller-child * 3)
	// hint: in the helper, return -1 if the tree is not semi-perfect, otherwise return the size
    public boolean isSemiBalanced() {
        if(isSemiBalanced(root) == -1)
        	return false;
        else{return true;}
    }

    public int isSemiBalanced(Node root) {
        if(root == null)
        	return 0;
        if (isPerfectlyBalancedH(root.left) == isPerfectlyBalancedH(root.right))
            return 1 + + isPerfectlyBalancedH(root.left) + isPerfectlyBalancedH(root.right);
        else {
            return -2;
        }
    }

	/*
	 * Mutator functions
	 * HINT: all of these are easier to write if the helper function returns Node, rather than void.
	 */

	// remove all subtrees with odd roots (if node is odd, remove it and its descendants)
	// A node is odd if it has an odd key
	// If the root is odd, then you should end up with the empty tree
    public void removeOddSubtrees() {
        removeOddSubtrees(root);
    }

    public void removeOddSubtrees(Node currentNode) {
        if (currentNode == null)
        {
        	return;
        }
        if ((currentNode.key) %2 != 0 ){
            currentNode = null;
            return;
        }
        removeOddSubtrees(currentNode.left);
        removeOddSubtrees(currentNode.right);
    }

	// remove all subtrees below depth k from the original tree
	public void removeBelowDepth(int k) {
		removeDepthHelper(root, 0, k);
	}
	
	private void removeDepthHelper(Node node, int currentDepth, int k) {
		if (node == null) return;
		if (currentDepth == k) {
			node = null;
			return;
		}
		removeDepthHelper(node.left, currentDepth + 1, k);
		removeDepthHelper(node.right, currentDepth + 1, k);
	}

	// add a child with key=0 to all nodes that have only one child
	// (you do not need to retain symmetric order or uniqueness of keys, obviously)
	public void addZeroToSingles() {
		addZeroToSinglesHelper(root);
	}
	
	public void addZeroToSinglesHelper(Node node) {
		if (node == null) return;
		if (node.left != null && node.right != null) return;
		if (node.left == null && node.right == null) return;
		Node x = new Node(0);
		if (node.left == null) node.left = x;
		else node.right = x;

		addZeroToSinglesHelper(node.left);
		addZeroToSinglesHelper(node.right);
	}

	// remove all leaves from the original tree
	// if you start with "41", then the result is the empty tree.
	// if you start with "41 21 61", then the result is the tree "41"
	// if you start with the BST "41 21 11  1", then the result is the tree "41 21 11"
	// if you start with the BST "41 21 61 11", then the result is the tree "41 21"
	// Hint: This requires that you check for "leafiness" before the recursive calls
	public void removeLeaves() {
		if (root == null){
		    return;
		}
		if (root.left != null && isLeaf(root.left)){
		    root.left = null;
		} else {
		    deleteLeaves(root.left);
		}
		if (root.right != null && isLeaf(root.right)){
		    root.right = null;
		} else {
		    deleteLeaves(root.right);
		}
	}

	// remove all nodes that have only one child by "promoting" that child
	// repeat this recursively as you go up, so the final result should have no nodes with only one child
	// if you start with "41", the tree is unchanged.
	// if you start with "41 21 61", the tree is unchanged.
	// if you start with the BST "41 21 11  1", then the result is the tree "1"
	// if you start with the BST "41 21 61 11", then the result is the tree "41 11 61"
	// Hint: This requires that you check for "singleness" after the recursive calls
	public void removeSingles() {
		root.removeHalfNodes(root);
	}
	
	

	
	/* ***************************************************************************
	 * Some methods to create and display trees
	 * DO NOT MODIFY THESE METHODS
	 *****************************************************************************/
	// Add one element to a tree, in BST order
	public void put(int key) { root = put(root, key); }
	private static Node put(Node n, int key) {
		if (n == null) return new Node(key);
		if      (key < n.key) n.left  = put(n.left,  key);
		else if (key > n.key) n.right = put(n.right, key);
		return n;
	}
	// Test for equality
	public static boolean areEquals (MyIntSET a, MyIntSET b) { return areEquals(a.root, b.root); }
	private static boolean areEquals (Node x, Node y) {
		if (x == null) {
			return (y == null);
		} else {
			return (y != null) && x.key == y.key && areEquals (x.left, y.left) && areEquals (x.right, y.right);
		}		
	}
	// Create a tree from a string
	public static MyIntSET fromString (String ints) {
		MyIntSET set = new MyIntSET ();
		for (String s : ints.split (" "))
			try { set.put (Integer.parseInt (s)); } catch (NumberFormatException e) {}
		return set;
	}
	// Return the values of the tree in level order
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
	// String representation of the tree
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int key: levelOrder())
			sb.append (key + " ");
		return sb.toString ();
	}
	// Graphical representation of the tree
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

	/* ***************************************************************************
	 *  Test client
	 *  You can modify any of these methods, if you wish
	 *****************************************************************************/
	private static int exampleCount = 0;
	private static void show(MyIntSET set, String filename) {
		set.toGraphviz ("x" + exampleCount + "-" + filename + ".png");
		exampleCount++;
	}
	private static void runOn(String s) {
		MyIntSET set = MyIntSET.fromString(s);
		StdOut.println ("###############################################################");
		StdOut.format ("tree: %s\n", set); show(set, "original");

		StdOut.format ("size()                 = %d\n", set.size());
		StdOut.format ("height()               = %d\n", set.height());
		StdOut.format ("sizeOdd()              = %d\n", set.sizeOdd());
		StdOut.format ("sizeAtDepth(2)         = %d\n", set.sizeAtDepth(2));
		StdOut.format ("sizeAboveDepth(2)      = %d\n", set.sizeAboveDepth(2));
		StdOut.format ("sizeBelowDepth(2)      = %d\n", set.sizeBelowDepth(2));
		StdOut.format ("isPerfectlyBalancedS() = %b\n", set.isPerfectlyBalancedS());
		StdOut.format ("isPerfectlyBalancedH() = %b\n", set.isPerfectlyBalancedH());
		StdOut.format ("isOddBalanced()        = %b\n", set.isOddBalanced());
		StdOut.format ("isSemiBalanced()       = %b\n", set.isSemiBalanced());

		set = MyIntSET.fromString(s); set.removeOddSubtrees(); StdOut.format ("removeOddSubtrees()  %s\n", set); show(set, "removeOddSubtrees");
		set = MyIntSET.fromString(s); set.removeBelowDepth(2); StdOut.format ("removeBelowDepth(2)  %s\n", set); show(set, "removeBelowDepth2");
		set = MyIntSET.fromString(s); set.removeLeaves();      StdOut.format ("removeLeaves()       %s\n", set); show(set, "removeLeaves");
		set = MyIntSET.fromString(s); set.removeSingles();     StdOut.format ("removeSingles()      %s\n", set); show(set, "removeSingles");
		set = MyIntSET.fromString(s); set.addZeroToSingles();  StdOut.format ("addZeroToSingles()   %s\n", set); show(set, "addZeroToSingles");
	}
	private static void run () {
		//runOn ("41 21 61 11 31");
		//runOn ("41 21 61 11 31 51 71 111");
		//runOn ("101 41 21 61 11 31 51 71 111");
		//runOn ("101 41 21 61 11 31 51 71 141 121 161 111 131 151 171 ");
		//runOn ("101 40 20 61 11 31 51 71 140 120 161 111 131 151 171 ");
		//runOn ("90 30 100 10 81 20 40 60 50 70 62 63 64");
		//runOn ("40 20 61 10 30 50 70");
		//runOn ("41 21 61 11 30 51 70");
		//runOn ("");
		//runOn ("1");
		//runOn ("90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testSize (5, "41 21 61 11 31");
		testSize (8, "41 21 61 11 31 51 71 111");
		testSize (9, "101 41 21 61 11 31 51 71 111");
		testSize (15, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testSize (11, "101 41 21 61 11 31 51 71 141 121 161");
		testSize (9, "101 41 21 61 11 31 51 71 141");
		testSize (15, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testSize (13, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testSize (7, "40 20 61 10 30 50 70");
		testSize (7, "41 21 61 11 30 51 70");
		testSize (0, "");
		testSize (1, "1");
		testSize (35, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testHeight (2, "41 21 61 11 31");
		testHeight (3, "41 21 61 11 31 51 71 111");
		testHeight (3, "101 41 21 61 11 31 51 71 111");
		testHeight (3, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testHeight (3, "101 41 21 61 11 31 51 71 141 121 161");
		testHeight (3, "101 41 21 61 11 31 51 71 141");
		testHeight (3, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testHeight (8, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testHeight (2, "40 20 61 10 30 50 70");
		testHeight (2, "41 21 61 11 30 51 70");
		testHeight (-1, "");
		testHeight (0, "1");
		testHeight (5, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testSizeOdd (5, "41 21 61 11 31");
		testSizeOdd (8, "41 21 61 11 31 51 71 111");
		testSizeOdd (9, "101 41 21 61 11 31 51 71 111");
		testSizeOdd (15, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testSizeOdd (11, "101 41 21 61 11 31 51 71 141 121 161");
		testSizeOdd (9, "101 41 21 61 11 31 51 71 141");
		testSizeOdd (11, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testSizeOdd (2, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testSizeOdd (1, "40 20 61 10 30 50 70");
		testSizeOdd (5, "41 21 61 11 30 51 70");
		testSizeOdd (0, "");
		testSizeOdd (1, "1");
		testSizeOdd (0, "2");
		testSizeOdd (11, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testSizeAtDepth (2, 2, "41 21 61 11 31");
		testSizeAtDepth (4, 2, "41 21 61 11 31 51 71 111");
		testSizeAtDepth (2, 2, "101 41 21 61 11 31 51 71 111");
		testSizeAtDepth (8, 3, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testSizeAtDepth (0, 4, "101 41 21 61 11 31 51 71 141 121 161");
		testSizeAtDepth (2, 2, "101 41 21 61 11 31 51 71 141");
		testSizeAtDepth (4, 2, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testSizeAtDepth (2, 3, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testSizeAtDepth (4, 2, "40 20 61 10 30 50 70");
		testSizeAtDepth (2, 1, "41 21 61 11 30 51 70");
		testSizeAtDepth (0, 2, "");
		testSizeAtDepth (0, 2, "1");
		testSizeAtDepth (4, 5, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testSizeAboveDepth (3, 2, "41 21 61 11 31");
		testSizeAboveDepth (3, 2, "41 21 61 11 31 51 71 111");
		testSizeAboveDepth (3, 2, "101 41 21 61 11 31 51 71 111");
		testSizeAboveDepth (7, 3, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testSizeAboveDepth (11, 4, "101 41 21 61 11 31 51 71 141 121 161");
		testSizeAboveDepth (3, 2, "101 41 21 61 11 31 51 71 141");
		testSizeAboveDepth (3, 2, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testSizeAboveDepth (5, 3, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testSizeAboveDepth (3, 2, "40 20 61 10 30 50 70");
		testSizeAboveDepth (1, 1, "41 21 61 11 30 51 70");
		testSizeAboveDepth (0, 2, "");
		testSizeAboveDepth (1, 2, "1");
		testSizeAboveDepth (31, 5, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testSizeBelowDepth (0, 2, "41 21 61 11 31");
		testSizeBelowDepth (1, 2, "41 21 61 11 31 51 71 111");
		testSizeBelowDepth (4, 2, "101 41 21 61 11 31 51 71 111");
		testSizeBelowDepth (0, 3, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testSizeBelowDepth (0, 4, "101 41 21 61 11 31 51 71 141 121 161");
		testSizeBelowDepth (4, 2, "101 41 21 61 11 31 51 71 141");
		testSizeBelowDepth (8, 2, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testSizeBelowDepth (6, 3, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testSizeBelowDepth (0, 2, "40 20 61 10 30 50 70");
		testSizeBelowDepth (4, 1, "41 21 61 11 30 51 70");
		testSizeBelowDepth (0, 2, "");
		testSizeBelowDepth (0, 2, "1");
		testSizeBelowDepth (0, 5, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testIsPerfectlyBalancedS (false, "41 21 61 11 31");
		testIsPerfectlyBalancedS (false, "41 21 61 11 31 51 71 111");
		testIsPerfectlyBalancedS (false, "101 41 21 61 11 31 51 71 111");
		testIsPerfectlyBalancedS (true, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testIsPerfectlyBalancedS (false, "101 41 21 61 11 31 51 71 141 121 161");
		testIsPerfectlyBalancedS (false, "101 41 21 61 11 31 51 71 141");
		testIsPerfectlyBalancedS (true, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testIsPerfectlyBalancedS (false, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testIsPerfectlyBalancedS (true, "40 20 61 10 30 50 70");
		testIsPerfectlyBalancedS (true, "41 21 61 11 30 51 70");
		testIsPerfectlyBalancedS (true, "");
		testIsPerfectlyBalancedS (true, "1");
		testIsPerfectlyBalancedS (false, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testIsPerfectlyBalancedH (false, "41 21 61 11 31");
		testIsPerfectlyBalancedH (false, "41 21 61 11 31 51 71 111");
		testIsPerfectlyBalancedH (false, "101 41 21 61 11 31 51 71 111");
		testIsPerfectlyBalancedH (true, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testIsPerfectlyBalancedH (false, "101 41 21 61 11 31 51 71 141 121 161");
		testIsPerfectlyBalancedH (false, "101 41 21 61 11 31 51 71 141");
		testIsPerfectlyBalancedH (true, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testIsPerfectlyBalancedH (false, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testIsPerfectlyBalancedH (true, "40 20 61 10 30 50 70");
		testIsPerfectlyBalancedH (true, "41 21 61 11 30 51 70");
		testIsPerfectlyBalancedH (true, "");
		testIsPerfectlyBalancedH (true, "1");
		testIsPerfectlyBalancedH (false, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testIsOddBalanced (false, "41 21 61 11 31");
		testIsOddBalanced (false, "41 21 61 11 31 51 71 111");
		testIsOddBalanced (false, "101 41 21 61 11 31 51 71 111");
		testIsOddBalanced (true, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testIsOddBalanced (false, "101 41 21 61 11 31 51 71 141 121 161");
		testIsOddBalanced (false, "101 41 21 61 11 31 51 71 141");
		testIsOddBalanced (false, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testIsOddBalanced (false, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testIsOddBalanced (false, "40 20 61 10 30 50 70");
		testIsOddBalanced (false, "41 21 61 11 30 51 70");
		testIsOddBalanced (true, "");
		testIsOddBalanced (true, "1");
		testIsOddBalanced (false, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testIsSemiBalanced (true, "41 21 61 11 31");
		testIsSemiBalanced (false, "41 21 61 11 31 51 71 111");
		testIsSemiBalanced (false, "101 41 21 61 11 31 51 71 111");
		testIsSemiBalanced (true, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testIsSemiBalanced (true, "101 41 21 61 11 31 51 71 141 121 161");
		testIsSemiBalanced (false, "101 41 21 61 11 31 51 71 141");
		testIsSemiBalanced (true, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testIsSemiBalanced (false, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testIsSemiBalanced (true, "40 20 61 10 30 50 70");
		testIsSemiBalanced (true, "41 21 61 11 30 51 70");
		testIsSemiBalanced (true, "");
		testIsSemiBalanced (true, "1");
		testIsSemiBalanced (true, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testRemoveOddSubtrees ("", "41 21 61 11 31");
		testRemoveOddSubtrees ("", "101 41 21 61 11 31 51 71 141");
		testRemoveOddSubtrees ("100 40 140 20 120", "100 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testRemoveOddSubtrees ("90 30 100 10 20", "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testRemoveOddSubtrees ("40 20 10 30", "40 20 61 10 30 50 70");
		testRemoveOddSubtrees ("", "41 21 61 11 30 51 70");
		testRemoveOddSubtrees ("", "");
		testRemoveOddSubtrees ("", "1");
		testRemoveOddSubtrees ("0", "0");
		testRemoveOddSubtrees ("90 30 100 10 80 20 40 12 60 62", "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testRemoveBelowDepth ("41 21 61 11 31", 2, "41 21 61 11 31");
		testRemoveBelowDepth ("41 21 61 11 31 51 71", 2, "41 21 61 11 31 51 71 111");
		testRemoveBelowDepth ("101 41 111 21 61", 2, "101 41 21 61 11 31 51 71 111");
		testRemoveBelowDepth ("101 41 141 21 61 121 161", 2, "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testRemoveBelowDepth ("101 41 141 21 61 121 161 11 31 51 71", 4, "101 41 21 61 11 31 51 71 141 121 161");
		testRemoveBelowDepth ("101 41 141 21 61", 2, "101 41 21 61 11 31 51 71 141");
		testRemoveBelowDepth ("101 40 140 20 61 120 161", 2, "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testRemoveBelowDepth ("90 30 100 10 81 20 40", 3, "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testRemoveBelowDepth ("40 20 61 10 30 50 70", 2, "40 20 61 10 30 50 70");
		testRemoveBelowDepth ("41 21 61", 1, "41 21 61 11 30 51 70");
		testRemoveBelowDepth ("", 2, "");
		testRemoveBelowDepth ("1", 2, "1");
		testRemoveBelowDepth ("90 30 100 10 80 95 105 5 20 40 85 93 97 103 110", 3, "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testAddZeroToSingles ("41 21 61 11 0 ", "41 21 61 11");
		testAddZeroToSingles ("41 21 61 11 0 51 71 ", "41 21 61 11 51 71");
		testAddZeroToSingles ("90 30 100 10 81 0 20 40 0 0 60 50 70 62 0 0 63 0 64 ", "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testAddZeroToSingles ("40 20 61 10 30 50 70 ", "40 20 61 10 30 50 70");
		testAddZeroToSingles ("", "");
		testAddZeroToSingles ("1 ", "1");
		testAddZeroToSingles ("90 30 100 10 80 95 105 5 20 40 85 93 97 103 110 2 6 12 21 35 60 82 86 92 94 96 98 102 104 106 111 34 36 45 62 ", "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testRemoveLeaves ("41 21", "41 21 61 11 31");
		testRemoveLeaves ("41 21 61 71", "41 21 61 11 31 51 71 111");
		testRemoveLeaves ("101 41 21 61", "101 41 21 61 11 31 51 71 111");
		testRemoveLeaves ("101 41 141 21 61 121 161", "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testRemoveLeaves ("101 41 141 21 61", "101 41 21 61 11 31 51 71 141 121 161");
		testRemoveLeaves ("101 41 21 61", "101 41 21 61 11 31 51 71 141");
		testRemoveLeaves ("101 40 140 20 61 120 161", "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testRemoveLeaves ("90 30 10 81 40 60 70 62 63", "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testRemoveLeaves ("40 20 61", "40 20 61 10 30 50 70");
		testRemoveLeaves ("41 21 61", "41 21 61 11 30 51 70");
		testRemoveLeaves ("", "");
		testRemoveLeaves ("", "1");
		testRemoveLeaves ("90 30 100 10 80 95 105 5 20 40 85 93 97 103 110 35 60", "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		testRemoveSingles ("101 41 141 21 61 121 161 11 31 51 71 111 131 151 171", "101 41 21 61 11 31 51 71 141 121 161 111 131 151 171");
		testRemoveSingles ("101 41 141 21 61 121 161 11 31 51 71", "101 41 21 61 11 31 51 71 141 121 161");
		testRemoveSingles ("101 41 141 21 61 11 31 51 71", "101 41 21 61 11 31 51 71 141");
		testRemoveSingles ("101 40 140 20 61 120 161 11 31 51 71 111 131 151 171", "101 40 20 61 11 31 51 71 140 120 161 111 131 151 171");
		testRemoveSingles ("90 30 100 20 60 50 64", "90 30 100 10 81 20 40 60 50 70 62 63 64");
		testRemoveSingles ("40 20 61 10 30 50 70", "40 20 61 10 30 50 70");
		testRemoveSingles ("41 21 61 11 30 51 70", "41 21 61 11 30 51 70");
		testRemoveSingles ("", "");
		testRemoveSingles ("1", "1");
		testRemoveSingles ("90 30 100 10 80 95 105 5 20 40 85 93 97 103 110 2 6 12 21 35 60 82 86 92 94 96 98 102 104 106 111 34 36 45 62", "90 30 100 10 80 20 40 60 5 85 35 95 105 2 6 110 103 104 102 93 97 96 82 86 12 21 45 62 106 111 92 94 98 34 36");

		StdOut.println ("Finished Tests");

	}
	private static void testPrintLeftI () {
		//Trace.drawStepsOfMethod ("printLeftI");
		//Trace.drawSteps ();
		//Trace.run ();
		MyIntSET set1 = MyIntSET.fromString("41 21 61 11 31");
		StdOut.format ("tree: %s\n", set1);
		set1.toGraphviz ("x.png");
		set1.printLeftI ();
		//MyIntSET set2 = MyIntSET.fromString ("41 21 61 11 31 51 71 111");
		//set2.toGraphviz ("y.png");
	}
	public static void main(String[] args) {
		//testPrintLeftI ();
		run ();
	}
	private static void testSize (int expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		int actual = set.size ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed size(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed size(%s): Expecting (%d) Actual (%d)\n", tree, expected, actual);
		}
	}
	private static void testHeight (int expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		int actual = set.height ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed height(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed height(%s): Expecting (%d) Actual (%d)\n", tree, expected, actual);
		}
	}
	private static void testSizeOdd (int expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		int actual = set.sizeOdd ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed sizeOdd(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed sizeOdd(%s): Expecting (%d) Actual (%d)\n", tree, expected, actual);
		}
	}
	private static void testSizeAtDepth (int expected, int depth, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		int actual = set.sizeAtDepth (depth);
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed sizeAtDepth(%d,%s): Tree modified\n", depth, tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed sizeAtDepth(%d,%s): Expecting (%d) Actual (%d)\n", depth, tree, expected, actual);
		}
	}
	private static void testSizeAboveDepth (int expected, int depth, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		int actual = set.sizeAboveDepth (depth);
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed sizeAboveDepth(%d,%s): Tree modified\n", depth, tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed sizeAboveDepth(%d,%s): Expecting (%d) Actual (%d)\n", depth, tree, expected, actual);
		}
	}
	private static void testSizeBelowDepth (int expected, int depth, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		int actual = set.sizeBelowDepth (depth);
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed sizeBelowDepth(%d,%s): Tree modified\n", depth, tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed sizeBelowDepth(%d,%s): Expecting (%d) Actual (%d)\n", depth, tree, expected, actual);
		}
	}
	private static void testIsPerfectlyBalancedS (boolean expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		boolean actual = set.isPerfectlyBalancedS ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed isPerfectlyBalancedS(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed isPerfectlyBalancedS(%s): Expecting (%b) Actual (%b)\n", tree, expected, actual);
		}
	}
	private static void testIsPerfectlyBalancedH (boolean expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		boolean actual = set.isPerfectlyBalancedH ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed isPerfectlyBalancedH(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed isPerfectlyBalancedH(%s): Expecting (%b) Actual (%b)\n", tree, expected, actual);
		}
	}
	private static void testIsOddBalanced (boolean expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		boolean actual = set.isOddBalanced ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed isOddBalanced(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed isOddBalanced(%s): Expecting (%b) Actual (%b)\n", tree, expected, actual);
		}
	}
	private static void testIsSemiBalanced (boolean expected, String tree) {
		MyIntSET set = MyIntSET.fromString(tree);
		boolean actual = set.isSemiBalanced ();
		if (! areEquals (set, MyIntSET.fromString(tree))) {
			StdOut.format ("Failed isSemiBalanced(%s): Tree modified\n", tree);
		}
		if (expected != actual) {
			StdOut.format ("Failed isSemiBalanced(%s): Expecting (%b) Actual (%b)\n", tree, expected, actual);
		}
	}
	private static void testRemoveOddSubtrees (String expected, String tree) {
		MyIntSET actual = MyIntSET.fromString(tree);
		actual.removeOddSubtrees ();		
		if (! areEquals (actual, MyIntSET.fromString(expected))) {
			StdOut.format ("Failed removeOddSubtrees(%s): Expecting (%s) Actual (%s)\n", tree, expected, actual);
			StdOut.format ("See graphviz output on your Desktop\n", tree, expected, actual);
			show (actual, "actual");
			show (MyIntSET.fromString(expected), "expected");
		}
	}	
	private static void testRemoveBelowDepth (String expected, int depth, String tree) {
		MyIntSET actual = MyIntSET.fromString(tree);
		actual.removeBelowDepth (depth);		
		if (! areEquals (actual, MyIntSET.fromString(expected))) {
			StdOut.format ("Failed removeBelowDepth(%d,%s): Expecting (%s) Actual (%s)\n", depth, tree, expected, actual);
			StdOut.format ("See graphviz output on your Desktop\n", tree, expected, actual);
			show (actual, "actual");
			show (MyIntSET.fromString(expected), "expected");
		}
	}
	private static void testRemoveSingles (String expected, String tree) {
		MyIntSET actual = MyIntSET.fromString(tree);
		actual.removeSingles ();		
		if (! areEquals (actual, MyIntSET.fromString(expected))) {
			StdOut.format ("Failed removeSingles(%s): Expecting (%s) Actual (%s)\n", tree, expected, actual);
			StdOut.format ("See graphviz output on your Desktop\n", tree, expected, actual);
			show (actual, "actual");
			show (MyIntSET.fromString(expected), "expected");
		}
	}
	private static void testRemoveLeaves(String expected, String tree) {
		MyIntSET actual = MyIntSET.fromString(tree);
		actual.removeLeaves();		
		if (! areEquals (actual, MyIntSET.fromString(expected))) {
			StdOut.format ("Failed removeLeaves(%s): Expecting (%s) Actual (%s)\n", tree, expected, actual);
			StdOut.format ("See graphviz output on your Desktop\n", tree, expected, actual);
			show (actual, "actual");
			show (MyIntSET.fromString(expected), "expected");
		}
	}
	private static void testAddZeroToSingles(String expected, String tree) {
		MyIntSET actual = MyIntSET.fromString(tree);
		actual.addZeroToSingles ();
		if (! expected.equals (actual.toString ())) {
			StdOut.format ("Failed removeLeaves(%s): Expecting (%s) Actual (%s)\n", tree, expected, actual);
			StdOut.format ("See graphviz output on your Desktop\n", tree, expected, actual);
			show (actual, "actual");
			show (MyIntSET.fromString(expected), "expected");
		}
	}

}
