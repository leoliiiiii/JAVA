/*
 * hw4 Rectangle class
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class Rectangle extends Shape {
    /** width variable. */
    private double width;
    /** height variable. */
    private double height;
    /*
     * @param newWidth
     * @param newHeight
     */
    public Rectangle(double newWidth, double newHeight) {
        super();
        width = newWidth;
        height = newHeight;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public double getArea() {
        return width * height;
    }
    public double getPerimeter() {
        return 2 * (width + height);
    }
    @Override
    public String toString() {
        return "Rectangle " + String.format("%.3f", getArea()) + " " + String.format("%.3f", getPerimeter());
    }
}
