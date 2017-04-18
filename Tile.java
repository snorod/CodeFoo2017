import java.util.List;
import java.util.LinkedList;

/**
 * Class that defines a Tile object, with x/y coordinates and a number value.
 *
 * @author Stephen Norod
 */
public class Tile implements Comparable<Tile> {

    private int x;
    private int y;
    private int value;
    private boolean visited;
    private List<Tile> neighbors;

    /**
     * Constructor for the class.
     *
     * @param x the x coordinate of the Tile.
     * @param y the y coordinate of the Tile.
     * @param value the number on the Tile.
     */
    public Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        neighbors = new LinkedList<Tile>();
    }

    /**
     * @return an integer representing the x coordinate of the Tile.
     */
    public int getX() {
        return x;
    }

    /**
     * @return an integer representing the y coordinate of the Tile.
     */
    public int getY() {
        return y;
    }

    /**
     * @return an integer representing the number on the Tile.
     */
    public int getValue() {
        return value;
    }

    /**
     * @return a boolean representing whether or not the Tile has been visited while iterating.
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * @return a list of tiles representing the Tile's neighbors.
     */
    public List<Tile> getNeighbors() {
        return neighbors;
    }

    /**
     * @param other the neighboring Tile to be added.
     */
    public void setNeighbor(Tile other) {
        this.neighbors.add(other);
    }

    /**
     * @param tOrF whether or not the Tile has been visited while iterating.
     */
    public void setVisited(boolean tOrF) {
        this.visited = tOrF;
    }

    @Override
    public int compareTo(Tile other) {
        if (value == other.value) {
            return x - other.x + y - other.y;
        }
        return value - other.value;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        hash = 31 * hash + value;
        return hash;
    }
}
