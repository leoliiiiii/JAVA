/*
 * Class defining the student object.
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class Student {
    /**
     * The first name field.
     */
    private String firstName = null;
    /**
     * The last name field.
     */
    private String lastName = null;
    /**
     * The andrewId field.
     */
    private String id = null;
    /**
     * The phone number field.
     */
    private String phone = null;
    public Student(String andrewId) {
        this.id = andrewId;
    }
    public String getAndrewId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phone;
    }
    public void setFirstName(String s) {
        this.firstName = s;
    }
    public void setLastName(String s) {
        this.lastName = s;
    }
    public void setPhoneNumber(String s) {
        this.phone = s;
    }
    public String toString() {
        return this.getFirstName() + " " + this.getLastName() + " (Andrew ID: " + this.getAndrewId()
        + ", Phone Number: " + this.getPhoneNumber() + ")";
    }
}
