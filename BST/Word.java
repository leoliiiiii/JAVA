import java.util.Set;
import java.util.TreeSet;

/**
 * Class featuring a word tracking its frequency and appearing positions.
 * @author Zhihan Li
 * andrewID: zhihanli
 */
public class Word implements Comparable<Word> {
    /**
     * Private field storing the string value.
     */
    private String word;
    /**
     * Private field storing all lines of appearance.
     */
    private Set<Integer> index;
    /**
     * Private field storing frequency of appearance.
     */
    private int frequency;

    /**
     * Constructor for Word object.
     * @param input
     */
    public Word(String input) {
        index = new TreeSet<Integer>();
        if (input != null) {
            word = input;
            frequency = 1;
        }
    }

    /**
     * Overriding the compareTo method from Comparable Interface based on alphabetic order.
     */
    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.getWord());
    }

    /**
     * Modify the stored string value.
     * @param newWord
     */
    public void setWord(String newWord) {
        word = newWord;
    }

    /**
     * Getter method for stored string value.
     * @return the stored String value.
     */
    public String getWord() {
        //protective copy
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            b.append(word.charAt(i));
        }
        return b.toString();
    }

    /**
     * Setter method for frequency.
     * @param freq
     */
    public void setFrequency(int freq) {
        frequency = freq;
    }

    /**
     * Getter method for frequency.
     * @return stored frequency of the given Word object.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Storing the lines where the given String value appears in.
     * @param line
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }

    /**
     * Getter method for lines of appearances.
     * @return a TreeSet storing all lines of appearances of a given word.
     */
    public Set<Integer> getIndex() {
        //protective copy
        Set<Integer> indexCopy = new TreeSet<Integer>();
        for (Integer ele : index) {
            indexCopy.add(ele);
        }
        return indexCopy;
    }

    /**
     * Overriding the toString method.
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder().append(getWord()).append(" ").append(getFrequency()).append(" [");
        for (Integer ele : index) {
            b.append(ele).append(", ");
        }
        b.delete(b.length() - 2, b.length());
        b.append("]");
        return b.toString();
    }
}
