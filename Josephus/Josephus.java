import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 2 Solve Josephus problem using different data structures
 * and different algorithms and compare running times.
 *
 *Out of the three methods implemented below, I would use the third one, playWithLLAt, because it is faster.
 *For each iteration, the first two methods will need to perform pooling for (rotation) times and adding for (rotation - 1) times.
 *However, the third method playWithLLAt only needs to perform removing once as it keep tracks of the index to remove.
 *
 * Andrew ID: zhihanli
 * @author Zhihan Li
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        Queue<Integer> container = new ArrayDeque<Integer>(size);
        if (size <= 0 | rotation <= 0) {
            throw new RuntimeException("Invalid Input!");
        }
        for (int i = 0; i < size; i++) {
            container.add(i + 1);
        }
        while (container.size() > 1) {
            for (int i = 0; i < rotation - 1; i++) {
                int head = container.poll();
                container.add(head);
            }
            container.remove();
        }
        return container.peek();
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        Queue<Integer> container = new LinkedList<Integer>();
        if (size <= 0 | rotation <= 0) {
            throw new RuntimeException("Invalid Input!");
        }
        for (int i = 0; i < size; i++) {
            container.add(i + 1);
        }
        while (container.size() > 1) {
            for (int i = 0; i < rotation - 1; i++) {
                int head = container.poll();
                container.add(head);
            }
            container.remove();
        }
        return container.peek();
    };

    /**
     * Uses LinkedList class to find the survivor's position.
     *
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find and remove a person to be executed in the circle
     *
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
        // TODO your implementation here
        List<Integer> container = new LinkedList<Integer>();
        if (size <= 0 | rotation <= 0) {
            throw new RuntimeException("Invalid Input!");
        }
        for (int i = 0; i < size; i++) {
            container.add(i + 1);
        }
        int head = (rotation - 1) % container.size();
        while (container.size() > 1) {
            container.remove(head);
            head = (head + rotation - 1) % container.size();
        }
        return container.get(head);
    }

}
