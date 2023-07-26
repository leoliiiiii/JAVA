/*
 * hw4 Circle class
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class Circle extends Shape {
    /** radius variable. */
    private double radius;
    /*
     * @param newRadius
     */
    public Circle(double newRadius) {
        super();
        radius = newRadius;
    }
    public double getRadius() {
        return radius;
    }
    public double getArea() {
        return Math.PI * radius * radius;
    }
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
    @Override
    public String toString() {
        return "Circle " + String.format("%.3f", getArea()) + " " + String.format("%.3f", getPerimeter());
    }
}
