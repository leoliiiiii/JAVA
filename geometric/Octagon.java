/*
 * hw4 Octagon class
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class Octagon extends Shape {
    /** side variable. */
    private double side;
    /*
     * @param newSide
     */
    public Octagon(double newSide) {
        super();
        side = newSide;
    }
    public double getSide() {
        return side;
    }
    public double getArea() {
        return 2 * (1 + Math.pow(2, 0.5)) * Math.pow(side, 2);
    }
    public double getPerimeter() {
        return 8 * side;
    }
    @Override
    public String toString() {
        return "Octagon " + String.format("%.3f", getArea()) + " " + String.format("%.3f", getPerimeter());
    }
}
