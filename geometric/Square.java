/*
 * hw4 Square class
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class Square extends Rectangle {
    /** side variable. */
    private double side;
    /*
     * @param newSide
     */
    public Square(double newSide) {
        super(newSide, newSide);
        side = newSide;
    }
    public double getSide() {
        return side;
    }
    @Override
    public double getArea() {
        return side * side;
    }
    @Override
    public double getPerimeter() {
        return 4 * side;
    }
    public String toString() {
        return "Square " + String.format("%.3f", getArea()) + " " + String.format("%.3f", getPerimeter());
    }
}
