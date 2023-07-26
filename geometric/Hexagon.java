/*
 * hw4 Hexagon class
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class Hexagon extends Shape {
    /** side variable. */
    private double side;
    /*
     * @newSide
     */
    public Hexagon(double newSide) {
        super();
        side = newSide;
    }
    public double getSide() {
        return side;
    }
    public double getArea() {
        return 0.5 * 3 * Math.pow(3, 0.5) * Math.pow(side, 2);
    }
    public double getPerimeter() {
        return 6 * side;
    }
    @Override
    public String toString() {
        return "Hexagon " + String.format("%.3f", getArea()) + " " + String.format("%.3f", getPerimeter());
    }
}
