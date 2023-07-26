import java.util.Comparator;

/**
 * Comparator based first on alphabetic order and then frequency.
 * @author Zhihan Li
 * andrewID: zhihanli
 */
public class AlphaFreq implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        int alphabeticOrder = o1.getWord().compareTo(o2.getWord());
        if (alphabeticOrder != 0) {
            return alphabeticOrder;
        }
        return Integer.compare(o1.getFrequency(), o2.getFrequency());
    }

}
