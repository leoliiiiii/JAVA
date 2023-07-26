/*
 * hw4 sorting shapes
 * @author Zhihan Li (zhihanli@andrew.cmu.edu)
 */
public class ShapeSortTest {
    public static void main(String[] args) {
        String[] asc = ascSort(args);
        Shape[] ascShapes = new Shape[args.length];
        for (int i = 0; i < asc.length; i++) {
            ascShapes[i] = ShapeSortTest.shapeCreator(asc[i]);
        }
        for (int i = 0; i < ascShapes.length; i++) {
            System.out.println(ascShapes[i]);
        }
        String[] desc = descSort(asc);
        Shape[] descShapes = new Shape[args.length];
        for (int i = 0; i < desc.length; i++) {
            descShapes[i] = ShapeSortTest.shapeCreator(desc[i]);
        }
        for (int i = 0; i < descShapes.length; i++) {
            System.out.println(descShapes[i]);
        }
    }
    static Shape shapeCreator(String input) {
        int size = Integer.parseInt(input.substring(1));
        if (input.substring(0, 1).equals("C")) {
            Circle c = new Circle(size);
            return c;
        } else if (input.substring(0, 1).equals("S")) {
            Square s = new Square(size);
            return s;
        } else if (input.substring(0, 1).equals("H")) {
            Hexagon h = new Hexagon(size);
            return h;
        } else {
            Octagon o = new Octagon(size);
            return o;
        }
    }
    static String[] ascSort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (ShapeSortTest.shapeCreator(args[j]).getArea() < ShapeSortTest.shapeCreator(args[i]).getArea()) {
                    String temp = args[i];
                    args[i] = args[j];
                    args[j] = temp;
                }
            }
        }
        return args;
    }
    static String[] descSort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (ShapeSortTest.shapeCreator(args[j]).getPerimeter() > ShapeSortTest.shapeCreator(args[i]).getPerimeter()) {
                    String temp = args[i];
                    args[i] = args[j];
                    args[j] = temp;
                }
            }
        }
        return args;
    }
}
