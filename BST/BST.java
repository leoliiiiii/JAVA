import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * Class for binary search tree structure.
 * @author Zhihan Li
 * andrewID zhihanli
 * @param <T>
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * Private field for the root of the binary search tree.
     */
    private Node<T> root;
    /**
     * Private field for the input comparator that is used to construct the binary search tree.
     */
    private Comparator<T> comparator;

    /**
     *  Constructor without the comparator.
     */
    public BST() {
        this(null);
    }

    /**
     * Constructor with the comparator used for constructing the bineary search tree.
     * @param comp
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * Getter method for the comparator used.
     * @return the comparator used for the binary search tree.
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * Getter method for the root of the tree.
     * @return the root of the tree.
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * Calculating the horizontal height of the tree.
     * @return the height.
     */
    public int getHeight() {
        return heightCalculator(root);
    }

    /**
     * Private helper function for calculating the BST height using recursion.
     * Base case: when the current node is null, the total height does not to be incremented.
     * Recursive case: add 1 for the edge linking to the current node and recursively go to the next node.
     * If there is no left child, only calculate the height of the right child sub-tree.
     * If there is no right child, only calculate the height of the left child sub-tree.
     * If both left and right children are present, take the larger one.
     * @param n the current node n
     * @return the height of the current node
     */
    private int heightCalculator(Node<T> n) {
        if (n == null) {
            return 0;
        }
        //no children
        if (n.left == null && n.right == null) {
            return 0;
        }
        if (n.left == null) {
            return 1 + heightCalculator(n.right);
        }
        if (n.right == null) {
            return 1 + heightCalculator(n.left);
        } else {
            return 1 + Math.max(heightCalculator(n.left), heightCalculator(n.right));
        }
    }

    /**
     * Counting the number of nodes in the tree.
     * @return the number of nodes.
     */
    public int getNumberOfNodes() {
        return numNodeCounter(root);
    }

    /**
     * Private helper function for calculating the number of nodes using recursion.
     * Base case: if the current node, no need to make increment on the total number.
     * Recursive case: if the current node is null, add 1 more to the total number and go to count the left and right children.
     * @param n the current node n
     * @return the number of the nodes in the current subtree.
     */
    private int numNodeCounter(Node<T> n) {
        if (n == null) {
            return 0;
        }
        return 1 + numNodeCounter(n.left) + numNodeCounter(n.right);
    }

    /**
     * Check whether the specified object is in the tree.
     * Return the object if it is in the tree. Otherwise, return null.
     */
    @Override
    public T search(T toSearch) {
        if (toSearch == null) {
            return null;
        }
        return searchHelper(root, toSearch);
    }

    /**
     * Private helper function for searching using recursion.
     * Base case: if the current node is null, we have reached the end and didn't find the specified value.
     * Recursive case: if the current node is the one we want are looking for, return its stored data; if the current node is
     * smaller than toSearch according to the given comparator, recursively go to the right child; otherwise, go to the left child.
     * @param n the current node n
     * @param toSearch the value we are searching for
     * @return the value if it is in the tree or null if it is not
     */
    private T searchHelper(Node<T> n, T toSearch) {
        if (n == null) {
            return null;
        }
        if (comparator() == null) {
            if (n.data.compareTo(toSearch) == 0) {
                return n.data;
            }
            if (n.data.compareTo(toSearch) < 0) {
                return searchHelper(n.right, toSearch);
            } else {
                return searchHelper(n.left, toSearch);
            }
        } else {
            if (comparator.compare(n.data, toSearch) == 0) {
                return n.data;
            }
            if (comparator.compare(n.data, toSearch) < 0) {
                return searchHelper(n.right, toSearch);
            } else {
                return searchHelper(n.left, toSearch);
            }
        }
    }

    /**
     * Insert the specified value into the binary search tree.
     * @param toInsert the value to insert.
     */
    @Override
    public void insert(T toInsert) {
        if (toInsert == null) {
            return;
        }
        if (root == null) {
            root = new Node<T>(toInsert);
        }
        insertHelper(root, root, false, toInsert);
    }

    /**
     * Private Helper function for insertion.
     * Base case: if the current node is null, we have reached position appropriate to insert the new value. Then, perform the insertion.
     * Recursive case: if the current node store the save value as toInsert, preserve the original node and do nothing; if the current
     * node is smaller than toSearch according to the given comparator, recursively go to the right child; otherwise, go to the left child.
     * @param parent the parent node
     * @param curr the current node
     * @param isLeft whether the current node is the left child of the parent node
     * @param toInsert the value to be inserted
     */
    private void insertHelper(Node<T> parent, Node<T> curr, boolean isLeft, T toInsert) {
        if (curr == null) {
            if (isLeft) {
                parent.left = new Node<T>(toInsert);
                return;
            } else {
                parent.right = new Node<T>(toInsert);
                return;
            }
        }
        if (comparator() == null) {
            if (curr.data.compareTo(toInsert) == 0) {
                return;
            }
            if (curr.data.compareTo(toInsert) < 0) {
                insertHelper(curr, curr.right, false, toInsert);
            } else {
                insertHelper(curr, curr.left, true, toInsert);
            }
        } else {
            if (comparator.compare(curr.data, toInsert) == 0) {
                return;
            }
            if (comparator.compare(curr.data, toInsert) < 0) {
                insertHelper(curr, curr.right, false, toInsert);
            } else {
                insertHelper(curr, curr.left, true, toInsert);
            }
        }
    }

    /**
     * Return an iterator that performs in-order traversal over the binary search tree.
     */
    @Override
    public Iterator<T> iterator() {
        return new BstIterator();
    }

    //Private nested class for iterator over BST.
    private class BstIterator implements Iterator<T> {
        /**
         * Utilize the LIFO characteristics of Stack for in-order traversal.
         */
        private Stack<Node<T>> sortHelper;
        /**
         * Keep track of the current node.
         */
        private Node<T> curr;
        BstIterator() {
            sortHelper = new Stack<Node<T>>();
            curr = root;
            while (curr != null) {
                sortHelper.push(curr);
                curr = curr.left;
            }
        }

        //Check whether there is a next item in the iterator.
        @Override
        public boolean hasNext() {
            return !sortHelper.empty();
        }

        //Return the next item in the iterator.
        @Override
        public T next() {
            Node<T> nextNode = sortHelper.pop();
            //when there is not left, go to right
            curr = nextNode.right;
            while (curr != null) {
                sortHelper.push(curr);
                curr = curr.left;
            }
            return nextNode.data;
        }
    }

    //Private nested class for constructing nodes in the tree.
    private static class Node<T> {
        /**
         * Private field for storing data of the node.
         */
        private T data;
        /**
         * Private field for storing the reference to the left child.
         */
        private Node<T> left;
        /**
         * Private field for storing the reference to the right child.
         */
        private Node<T> right;

        //Constructor without left and right children.
        Node(T d) {
            this(d, null, null);
        }

        //Constructor with left and right children specified.
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
