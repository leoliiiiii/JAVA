/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 4: HashTable Implementation with linear probing.
 *
 * Andrew ID: zhihanli
 * @author Zhihan Li
 */
public class MyHashTable implements MyHTInterface {

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * The default initial capacity.
     */
    private static final int DEFAULTCAPACITY = 10;
    /**
     * Keep track of number of elements in the hash map.
     */
    private int eleCount;
    /**
     * Base number for calculating the hash code.
     */
    private static final int BASE = 27;
    /**
     * Deleted holder.
     */
    private static final DataItem DELETED = new DataItem("000");
    /**
     * Default load factor.
     */
    private static final double MAXLOAD = 0.5;
    /**
     * Keep track of the number of collisions.
     */
    private int numCollision;
    /**
     * ASCII code for the element before 'a'.
     */
    private static final int ASCIIHELPER = 96;

    // constructor with no initial capacity
    public MyHashTable() {
        this(DEFAULTCAPACITY);
    }
    // constructor with initial capacity
    public MyHashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Invalid initial capacity");
        } else {
            hashArray = new DataItem[initialCapacity];
            eleCount = 0;
            numCollision = 0;
        }
    }
    // TODO implement required methods

    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        int hashCode = 0;
        for (int i = 0; i < input.length(); i++) {
            hashCode  = ((hashCode * BASE) + (input.charAt(i) - ASCIIHELPER)) % hashArray.length;
        }
        return hashCode;
    }

    //helper function for checking prime numbers
    private boolean isPrime(int value) {
        for (int i = 2; i <= Math.sqrt(value); i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }
    //helper function for finding the next prime
    private int nextPrime(int value) {
        if (value < 2) {
            return 2;
        }
        while (!isPrime(value)) {
            value += 1;
        }
        return value;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     * Note: do not include the number of deleted spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {
        int newLength = nextPrime(hashArray.length * 2);
        StringBuilder b = new StringBuilder().append("Rehashing ").append(eleCount).append(" items, new length is ").append(newLength);
        System.out.println(b);
        numCollision = 0;
        DataItem[] helper = hashArray;
        hashArray = new DataItem[newLength];
        for (DataItem item : helper) {
            if (item != null && item != DELETED) {
                int hashValue = hashFunc(item.value);
                boolean collide = false;
                while (hashArray[hashValue] != null) {
                    if (hashFunc(item.value) == hashFunc(hashArray[hashValue].value)) {
                        collide = true;
                    }
                    hashValue++;
                    hashValue = hashValue % hashArray.length;
                }
                if (collide) {
                    numCollision += 1;
                }
                hashArray[hashValue] = item;
            }
        }
    }

    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        // TODO implement constructor and methods
        DataItem(String input) {
            value = input;
            frequency = 0;
        }

        private void addFreq() {
            frequency += 1;
        }
    }

    /**
     * Simple private helper method to validate a word.
     * @param text text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String text) {
        if (text == null) {
            return false;
        }
        return text.matches("[a-z]+");
    }

    @Override
    public void insert(String value) {
        if (isWord(value)) {
            int hashValue = hashFunc(value);
            //duplicate
            if (contains(value)) {
                int tracker = 0;
                while (hashArray[hashValue] != null) {
                    if (tracker >= hashArray.length) {
                        return;
                    }
                    if (hashArray[hashValue].value.equals(value)) {
                        hashArray[hashValue].addFreq();
                        return;
                    }
                    hashValue++;
                    hashValue = hashValue % hashArray.length;
                    tracker++;
                }
            }
            int tracker = 0;
            boolean collide = false;
            while (hashArray[hashValue] != null && hashArray[hashValue] != DELETED && tracker < hashArray.length) {
                //collision
                if (!hashArray[hashValue].value.equals(value) && hashFunc(value) == hashFunc(hashArray[hashValue].value)) {
                    collide = true;
                }
                hashValue++;
                hashValue = hashValue % hashArray.length;
                tracker++;
            }
            if (collide) {
                numCollision += 1;
            }
            DataItem item = new DataItem(value);
            hashArray[hashValue] = item;
            item.frequency = 1;
            eleCount += 1;
            if ((double) eleCount / hashArray.length > MAXLOAD) {
                rehash();
            }
        }
    }

    @Override
    public int size() {
        return eleCount;
    }

    @Override
    public void display() {
        StringBuilder b = new StringBuilder();
        for (DataItem item : hashArray) {
            if (item == null) {
                b.append("** ");
            } else if (item == DELETED) {
                b.append("#DEL# ");
            } else {
                b.append("[").append(item.value).append(", ").append(item.frequency).append("] ");
            }
        }
        String s = b.toString().trim();
        System.out.println(s);
    }

    @Override
    public boolean contains(String key) {
        if (!isWord(key)) {
            return false;
        }
        int hashValue = hashFunc(key);
        int tracker = 0;
        while (hashArray[hashValue] != null && tracker < hashArray.length) {
            if (hashArray[hashValue].value.equals(key)) {
                return true;
            }
            hashValue++;
            hashValue = hashValue % hashArray.length;
            tracker++;
        }
        return false;
    }

    @Override
    public int numOfCollisions() {
        return numCollision;
    }

    @Override
    public int hashValue(String value) {
        return hashFunc(value);
    }

    @Override
    public int showFrequency(String key) {
        if (!contains(key)) {
            return 0;
        }
        int hashValue = hashFunc(key);
        while (!hashArray[hashValue].value.equals(key)) {
            hashValue++;
            hashValue = hashValue % hashArray.length;
        }
        int freq = hashArray[hashValue].frequency;
        return freq;
    }

    @Override
    public String remove(String key) {
        if (isWord(key)) {
            int hashValue = hashFunc(key);
            int tracker = 0;
            while (hashArray[hashValue] != null && tracker < hashArray.length) {
                if (hashArray[hashValue].value.equals(key)) {
                    String temp = hashArray[hashValue].value;
                    hashArray[hashValue] = DELETED;
                    eleCount -= 1;
                    return temp;
                }
                hashValue++;
                hashValue = hashValue % hashArray.length;
                tracker++;
            }
            return null;
        }
        return null;
    }

}
