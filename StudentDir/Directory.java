/*
 * Class defining the student directory.
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Directory {
    /**
     * Hashmap mapping andrewID to student.
     */
    private Map<String, Student> byID = null;
    /**
     * Hashmap mapping first name to student.
     */
    private Map<String, List<Student>> byfName = null;
    /**
     * Hashmap mapping last name to student.
     */
    private Map<String, List<Student>> bylName = null;
    public Directory() {
        byID = new HashMap<String, Student>();
        byfName = new HashMap<String, List<Student>>();
        bylName = new HashMap<String, List<Student>>();
    }
    public void addStudent(Student s) throws IllegalArgumentException {
        if (s == null) {
            throw new IllegalArgumentException("Invalid Andrew ID");
        } else {
            Student sCopy = new Student(s.getAndrewId());
            sCopy.setFirstName(s.getFirstName());
            sCopy.setLastName(s.getLastName());
            sCopy.setPhoneNumber(s.getPhoneNumber());
            if (this.byID.containsKey(sCopy.getAndrewId())) {
                throw new IllegalArgumentException("Invalid Andrew ID");
            } else {
                this.byID.put(sCopy.getAndrewId(), sCopy);
                if (this.byfName.containsKey(sCopy.getFirstName())) {
                    this.byfName.get(sCopy.getFirstName()).add(sCopy);
                } else {
                    List<Student> thisfName = new ArrayList<Student>();
                    thisfName.add(sCopy);
                    this.byfName.put(sCopy.getFirstName(), thisfName);
                }
                if (this.bylName.containsKey(sCopy.getLastName())) {
                    this.bylName.get(sCopy.getLastName()).add(sCopy);
                } else {
                    List<Student> thislName = new ArrayList<Student>();
                    thislName.add(sCopy);
                    this.bylName.put(sCopy.getLastName(), thislName);
                }
            }
        }
    }
    public void deleteStudent(String andrewId) throws IllegalArgumentException {
        if (andrewId == null) {
            throw new IllegalArgumentException("Invalid Andrew ID");
        } else {
            if (!this.byID.containsKey(andrewId)) {
                throw new IllegalArgumentException("Invalid Andrew ID");
            } else {
                Student s = this.byID.get(andrewId);
                this.byID.remove(andrewId);
                if (this.byfName.get(s.getFirstName()).size() > 1) {
                    this.byfName.get(s.getFirstName()).remove(s);
                } else {
                    this.byfName.remove(s.getFirstName());
                }
                if (this.bylName.get(s.getLastName()).size() > 1) {
                    this.bylName.get(s.getLastName()).remove(s);
                } else {
                    this.bylName.remove(s.getLastName());
                }
            }
        }
    }
    public Student searchByAndrewId(String andrewId) {
        if (andrewId == null) {
            throw new IllegalArgumentException("Invalid Andrew ID");
        } else {
            if (this.byID.containsKey(andrewId)) {
                Student original = this.byID.get(andrewId);
                Student copy = new Student(andrewId);
                copy.setFirstName(original.getFirstName());
                copy.setLastName(original.getLastName());
                copy.setPhoneNumber(original.getPhoneNumber());
                return copy;
            } else {
                return null;
            }
        }
    }
    public List<Student> searchByFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("Invalid Andrew ID");
        } else {
            if (this.byfName.containsKey(firstName)) {
                List<Student> original = this.byfName.get(firstName);
                List<Student> copy = new ArrayList<Student>();
                for (Student s : original) {
                    Student sCopy = new Student(s.getAndrewId());
                    sCopy.setFirstName(s.getFirstName());
                    sCopy.setLastName(s.getLastName());
                    sCopy.setPhoneNumber(s.getPhoneNumber());
                    copy.add(sCopy);
                }
                return copy;
            } else {
                List<Student> empty = new ArrayList<Student>();
                return empty;
            }
        }
    }
    public List<Student> searchByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Invalid Andrew ID");
        } else {
            if (this.bylName.containsKey(lastName)) {
                List<Student> original = this.bylName.get(lastName);
                List<Student> copy = new ArrayList<Student>();
                for (Student s : original) {
                    Student sCopy = new Student(s.getAndrewId());
                    sCopy.setFirstName(s.getFirstName());
                    sCopy.setLastName(s.getLastName());
                    sCopy.setPhoneNumber(s.getPhoneNumber());
                    copy.add(sCopy);
                }
                return copy;
            } else {
                List<Student> empty = new ArrayList<Student>();
                return empty;
            }
        }
    }
    public int size() {
        return this.byID.size();
    }
}
