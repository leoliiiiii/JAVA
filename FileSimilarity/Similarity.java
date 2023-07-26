import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for comparing two files.
 * @author Zhihan Li
 * andrewID: zhihanli
 * The Java Collections Framework chosen here is HashMap because it enables it can store the mappings from words to their frequency and
 * retrieving frequency for a given word can be completed with constant time complexity.
 */
public class Similarity {
    /**
     * Keep track of number of lines.
     */
    private int lines;
    /**
     * Keep track of number of words.
     */
    private BigInteger words;
    /**
     * Mapping word to frequency.
     */
    private Map<String, BigInteger> freqMap;

    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String text) {
        return text.matches("[a-zA-Z]+");
    }

    public Similarity(String string) {
        lines = 0;
        words = BigInteger.ZERO;
        freqMap = new HashMap<String, BigInteger>();
        if (string != null && string.length() > 0) {
            String[] strArray = string.split("\\W");
            for (String w : strArray) {
                if (w != null && isWord(w)) {
                    w = w.toLowerCase();
                    words = words.add(BigInteger.ONE);
                    if (freqMap.containsKey(w)) {
                        freqMap.replace(w, freqMap.get(w).add(BigInteger.ONE));
                    } else {
                        freqMap.put(w, BigInteger.ONE);
                    }
                }
            }
        }
    }

    public Similarity(File file) {
        lines = 0;
        words = BigInteger.ZERO;
        freqMap = new HashMap<String, BigInteger>();
        if (file == null) {
            return;
        }
        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines += 1;
                String[] strArray = line.split("\\W");
                for (String w : strArray) {
                    if (w != null && isWord(w)) {
                        w = w.toLowerCase();
                        words = words.add(BigInteger.ONE);
                        if (freqMap.containsKey(w)) {
                            freqMap.replace(w, freqMap.get(w).add(BigInteger.ONE));
                        } else {
                            freqMap.put(w, BigInteger.ONE);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    //runs in constant time
    public int numOfLines() {
        return lines;
    }

    //runs in constant time
    public BigInteger numOfWords() {
        return words;
    }

    //runs in constant time
    public int numOfWordsNoDups() {
        return freqMap.size();
    }

    public double euclideanNorm() {
        BigInteger result = BigInteger.ZERO;
        if (freqMap.size() == 0) {
            return 0;
        }
        for (BigInteger freq : freqMap.values()) {
            result = result.add(freq.pow(2));
        }
        double norm = result.doubleValue();
        return Math.sqrt(norm);
    }

    /**
     * The running time complexity of dotProduct() is not quadratic here. In this implementation, we loop through every element in
     * freqMap and check whether each of them is in the map in the argument. The checking process takes only constant running time
     * (O(1)). So the total running time complexity of dotProduct() is just linear (O(n)), depending on the length of freqMap.
     * @param map map to perform dot product with
     * @return the calculated dot product
     */
    public double dotProduct(Map<String, BigInteger> map) {
        if (map == null || map.size() == 0 || freqMap.size() == 0) {
            return 0;
        }
        BigInteger result = BigInteger.ZERO;
        for (String k : freqMap.keySet()) {
            if (map.get(k) != null) {
                result = result.add(freqMap.get(k).multiply(map.get(k)));
            }
        }
        return result.doubleValue();
    }

    public double distance(Map<String, BigInteger> map) {
        if (map == null || map.size() == 0 || freqMap.size() == 0) {
            return Math.PI / 2;
        }
        if (freqMap.equals(map)) {
            return 0;
        }
        BigInteger mapNorm = BigInteger.ZERO;
        for (BigInteger b : map.values()) {
            mapNorm = mapNorm.add(b.pow(2));
        }
        return Math.acos(dotProduct(map) / (euclideanNorm() * Math.sqrt(mapNorm.doubleValue())));
    }
    //Using defensive copy of freqMap
    public Map<String, BigInteger> getMap() {
        Map<String, BigInteger> copyMap = new HashMap<String, BigInteger>();
        for (String k : freqMap.keySet()) {
            copyMap.put(k, freqMap.get(k));
        }
        return copyMap;
    }
}
