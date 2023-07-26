/**
 * Implementation of the sorted linked list using only recursion.
 * @author Zhihan Li (zhihanli)
 */
public class SortedLinkedList implements MyListInterface {
    /**
     * Private field for the head node.
     */
    private Node head;
    public SortedLinkedList() {
        head = null;
    }

    public SortedLinkedList(String[] unsorted) {
        this();
        constructorHelper(unsorted, 0);
    }

    /**
     * Helper function for the constructor above using recursion.
     * @param unsorted
     * @param index
     * Base case: when you have traversed the entire list and the index is out of bound for the input array,
     * which means the parameter index is larger than the very last index of the input array, unsorted.length - 1.
     * Recursive case: when you are still within the bound of the input array, recursively call constructorHelper by adding 1
     * to the index parameter to reach the next element in the input array.
     */
    private void constructorHelper(String[] unsorted, int index) {
        if (index > unsorted.length - 1) {
            return;
        }
        add(unsorted[index]);
        constructorHelper(unsorted, index + 1);
    }

    private static class Node {
        /**
         * Private field for data of a node.
         */
        private String data;
        /**
         * Private field pointing to the next node.
         */
        private Node next;
        Node(String s, Node n) {
            data = s;
            next = n;
        }
    }

    @Override
    public void add(String value) {
        if (value == null) {
            return;
        }
        if (!isWord(value)) {
            return;
        }
        if (isEmpty()) {
            head = new Node(value, null);
        }
        if (contains(value)) {
            return;
        }
        if (head.data.compareTo(value) > 0) {
            head = new Node(value, head);
            return;
        }
        addHelper(value, null, head);
    }

    /**
     * Helper function for add using recursion.
     * @param value
     * @param prev
     * @param curr
     * Base case 1: when you have traversed the entire array and none of the elements is larger than the one to be inserted,
     * then you insert it at the very end.
     * Base case 2: when you find the first element that is larger than the one to be inserted,
     * then you insert it right after this element.
     * Recursive case: If there are still elements in the input array, and the current one is smaller than the one to be inserted,
     * then recursively call addHelper to check the next node.
     */
    private void addHelper(String value, Node prev, Node curr) {
        if (curr == null) {
            prev.next = new Node(value, null);
            return;
        }
        if (curr.data.compareTo(value) > 0) {
            Node newNode = new Node(value, curr);
            prev.next = newNode;
            return;
        }
        addHelper(value, curr, curr.next);
    }

    //check valid words
    private boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }

    @Override
    public int size() {
        return countNode(this.head);
    }

    /**
     * Helper function for counting the number of nodes in the linked list using recursion.
     * @param n
     * @return number of nodes in the linked list
     * Base case: when the current node is null, which means you have traversed the entire linked list
     * or the linked list is empty. There is no additional node, so return 0.
     * Recursive case: as long as the current node is not null, you add 1 to account for the current node and recursively
     * call countNode to count the next node.
     */
    private int countNode(Node n) {
        if (n == null) {
            return 0;
        }
        return 1 + countNode(n.next);
    }

    @Override
    public void display() {
        String s = displayHelper(new StringBuilder().append("["), head).toString();
        System.out.println(s);
    }

    /**
     * Helper function for display using recursion.
     * @param b
     * @param n
     * @return a StringBuilder containing all data in the linked list
     * Base case 1: when the linked list is empty or when you have traversed the entire linked list and there is no additional nodes,
     * ends the StringBuilder wit "]".
     * Recursive case 1: when you are at the very last node, add only the data but not the comma to the StringBuilder, and then
     * recursively call displayHelper to get the end notation "]".
     * Recursive case 2: As long as the current node is not the last one, you add the current data, a comma and space to the
     * StringBuilder, and then recursively call displayHelper to add the data of the next node.
     */
    private StringBuilder displayHelper(StringBuilder b, Node n) {
        if (n == null) {
            return b.append("]");
        }
        if (n != null && n.next == null) {
            return displayHelper(b.append(n.data), n.next);
        }
        return displayHelper(b.append(n.data).append(", "), n.next);
    }

    @Override
    public boolean contains(String key) {
        return search(key, head);
    }

    /**
     * Helper function for checking existence using recursion.
     * @param key
     * @param n
     * @return whether the given key exists in the linked list (boolean value)
     * Base case 1: if the linked list is empty or you have traversed the entire linked list, but you didn't find the key, return false.
     * If you reach a node that is larger than the key, and you didn't find the key in the previous nodes, then it is unlikely to find
     * the key among the rest of the nodes as the linked list is sorted in ascending order. So return false.
     * Base case 2: if the key is found, return true.
     * Recursive case: recursively call search to check the next node.
     */
    private boolean search(String key, Node n) {
        if (n == null || n.data.compareTo(key) > 0) {
            return false;
        }
        if (n.data.equals(key)) {
            return true;
        }
        return search(key, n.next);
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String removeFirst() {
        if (isEmpty()) {
            return null;
        }
        String temp = head.data;
        head = head.next;
        return temp;
    }

    @Override
    public String removeAt(int index) {
        if (index < 0 || index >= size()) {
            throw new RuntimeException("Invalid Index!");
        }
        return removeHelper(index, null, head);
    }

    /**
     * Helper function for removeAt using recursion.
     * @param index
     * @param prev
     * @param curr
     * @return the data of the node removed at the given position
     * Base case 1: remove the very first node in the linked list.
     * Base case 2: when you have reached the specified position, remove that node.
     * Recursive case: you haven't reached the specified position, then keep traversing the linked list with recursion.
     */
    private String removeHelper(int index, Node prev, Node curr) {
        if (prev == null && index == 0) {
            return removeFirst();
        }
        if (prev != null && index == 0) {
            String temp = curr.data;
            prev.next = curr.next;
            return temp;
        }
        return removeHelper(index - 1, curr, curr.next);
    }
}
