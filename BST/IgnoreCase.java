import java.util.Comparator;

/**
 * Comparator based on alphabetic order and not case sensitive.
 * @author Zhihan Li
 * andrewID: zhihanli
 */
public class IgnoreCase implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().compareToIgnoreCase(o2.getWord());
    }

}
