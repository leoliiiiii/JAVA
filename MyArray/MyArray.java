/**
 * MyArray class that implements arrays without importing any Java Collection frameworks.
 * @author Zhihan Li (zhihanli)
 */
public class MyArray {
    /**
     * Keep track of the number of elements in the array.
     */
    private int eleCount;
    /**
     * Save the underlying String array.
     */
    private String[] myStrArray;
    /**
     * The default capacity used to initialize a new String array.
     */
    private static final int DEFAULTINICAPACITY = 10;
    /**
     * The first threshold for validating a word.
     */
    private static final int VALIDONE = 65;
    /**
     * The second threshold for validating a word.
     */
    private static final int VALIDTWO = 90;
    /**
     * The third threshold for validating a word.
     */
    private static final int VALIDTHREE = 97;
    /**
     * The fourth threshold for validating a word.
     */
    private static final int VALIDFOUR = 122;

    public MyArray() {
        this(DEFAULTINICAPACITY);
    }

    public MyArray(int initialCapacity) {
        myStrArray = new String[initialCapacity];
        eleCount = 0;
    }

    public void add(String text) {
        if (validate(text)) {
            //If the number of elements exceeds the current capacity, create a new String[]
            if (eleCount == myStrArray.length) {
                int newCapacity;
                //doubling up capacity
                if (myStrArray.length == 0) {
                    newCapacity = 1;
                } else {
                    newCapacity = myStrArray.length * 2;
                }
                String[] newStrArray = new String[newCapacity];
                for (int i = 0; i < myStrArray.length; i++) {
                    newStrArray[i] = myStrArray[i];
                }
                myStrArray = newStrArray;
            }
            //adding
            myStrArray[eleCount] = text;
            eleCount += 1;
        }
    }

    private boolean validate(String text) {
        if (text == null) {
            return false;
        }
        if (text.equals("")) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            int code  = text.charAt(i) + 0;
            if ((code < VALIDONE) | (code > VALIDTWO & code < VALIDTHREE) | (code > VALIDFOUR)) {
                return false;
            }
        }
        return true;
    }

    public boolean search(String key) {
        if (key == null) {
            return false;
        } else {
            for (int i = 0; i < eleCount; i++) {
                if (myStrArray[i].equals(key)) {
                    return true;
                }
            }
            return false;
        }
    }

    public int size() {
        return eleCount;
    }

    public int getCapacity() {
        return myStrArray.length;
    }

    public void display() {
        String result = "";
        for (int i = 0; i < eleCount; i++) {
            result = result + myStrArray[i] + " ";
        }
        result = result.trim();
        System.out.println(result);
    }

    public void removeDups() {
        //setting duplicates to null; iterating only through fill-in elements instead of the array length
        for (int i = 0; i < eleCount - 1; i++) {
            String thisWord = myStrArray[i];
            if (thisWord != null) {
                for (int j = i + 1; j < eleCount; j++) {
                    if (myStrArray[j] != null) {
                        if (myStrArray[j].equals(thisWord)) {
                            myStrArray[j] = null;
                        }
                    }
                }
            }
        }
        //removing the null
        int naCount = 0;
        int left = 0;
        int right;
        while (left < eleCount) {
            if (myStrArray[left] == null) {
                naCount += 1;
                right = left + 1;
                while (right < eleCount) {
                    if (myStrArray[right] != null) {
                        right++;
                    } else {
                        break;
                    }
                }
                if (right > left + 1) {
                    for (int k = left + 1; k < right; k++) {
                        myStrArray[k - naCount] = myStrArray[k];
                    }
                }
                left = right;
            } else {
                left++;
            }
        }
        eleCount -= naCount;
    }
}
