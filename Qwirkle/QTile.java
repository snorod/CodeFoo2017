public class QTile implements Comparable<QTile> {
    private int x;
    private int y;
    private String color;
    private String shape;
    public QTile(int x, int y, String color, String shape) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.shape = shape;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String getColor() {
        return color;
    }
    public String getShape() {
        return shape;
    }
    @Override
    public int compareTo(QTile other) {
        if (color.equals(other.color) && shape.equals(other.shape)) {
            return 0;
        }
        return (x - other.x) + (y - other.y);
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        //hash = 31 * hash + value;
        return hash;
    }
}
