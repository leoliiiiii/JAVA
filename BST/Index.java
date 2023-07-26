import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Index class for building binary search trees with input files and specified comparators.
 * @author Zhihan Li
 * andrewID: zhihanli
 */
public class Index {
    //Building a binary search tree based on alphabetic order from a file.
    public BST<Word> buildIndex(String fileName) {
        return buildIndex(fileName, null);
    }

    //Build a binary search tree based on the given comparator from a file.
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        BST<Word> bst = new BST<Word>(comparator);
        Scanner scanner = null;
        int lineIndex = 1;
        try {
            scanner = new Scanner(new File(fileName), "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] strArray = line.split("\\W");
                for (String w : strArray) {
                    if (w != null && isWord(w)) {
                        //if the comparator is an instance of the IgnoreCase Class
                        if (comparator instanceof IgnoreCase) {
                            w = w.toLowerCase();
                        }
                        Word word = new Word(w);
                        //if no such a node, perform the insertion.
                        if (bst.search(word) == null) {
                            bst.insert(word);
                            word.addToIndex(lineIndex);
                        } else {
                            Word existing = bst.search(word);
                            existing.setFrequency(existing.getFrequency() + 1);
                            existing.addToIndex(lineIndex);
                        }
                    }
                }
                lineIndex += 1;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return bst;
    }

    //Build a binary search tree from a list of Words.
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        BST<Word> bst = new BST<Word>(comparator);
        if (list != null && list.size() > 0) {
            for (Word word : list) {
                if (word != null && word.getWord() != null && isWord(word.getWord())) {
                    if (comparator instanceof IgnoreCase) {
                        word.setWord(word.getWord().toLowerCase());
                    }
                    bst.insert(word);
                }
            }
        }
        return bst;
    }

    //Sort the binary search tree first based on alphabetic order and then on frequency.
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        /*
         * Even though there should be no ties with regard to words in BST,
         * in the spirit of using what you wrote,
         * use AlphaFreq comparator in this method.
         */
        ArrayList<Word> byAlpha;
        if (tree.getRoot() == null) {
            byAlpha = new ArrayList<Word>();
            return byAlpha;
        }
        byAlpha = new ArrayList<Word>(tree.getNumberOfNodes());
        Iterator<Word> bstIterator = tree.iterator();
        while (bstIterator.hasNext()) {
            byAlpha.add(bstIterator.next());
        }
        Comparator<Word> alphaFreqComp = new AlphaFreq();
        byAlpha.sort(alphaFreqComp);
        return byAlpha;
    }

    //Sort the binary search tree based on frequency in descending order.
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        ArrayList<Word> byFreq;
        if (tree.getRoot() == null) {
            byFreq = new ArrayList<Word>();
            return byFreq;
        }
        byFreq = new ArrayList<Word>(tree.getNumberOfNodes());
        Iterator<Word> bstIterator = tree.iterator();
        while (bstIterator.hasNext()) {
            byFreq.add(bstIterator.next());
        }
        Comparator<Word> freqComp = new Frequency();
        byFreq.sort(freqComp);
        return byFreq;
    }

    //Return a list of Words with the highest frequency.
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        ArrayList<Word> result = new ArrayList<Word>();
        ArrayList<Word> byFreq = sortByFrequency(tree);
        int highestFreq = byFreq.get(0).getFrequency();
        Iterator<Word> freqIterator = byFreq.iterator();
        while (freqIterator.hasNext()) {
            Word curr = freqIterator.next();
            if (curr.getFrequency() >= highestFreq) {
                result.add(curr);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }
}
