import java.util.Comparator;

/**
 * Comparator based on frequency in descending order.
 * @author Zhihan Li
 * andrewID: zhihanli
 */
public class Frequency implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }

}
