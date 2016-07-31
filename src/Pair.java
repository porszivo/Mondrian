/**
 * Helperclass to represent a pair of coordinates
 *
 * @author Marcel Kieﬂling 1052167
 * @version 1.0
 */
import java.awt.*;

class Pair {

    public int x,y;
    
    /**
     * @param x
     * @param y
     */    
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics2D g2) {
        g2.fillOval(x,y,1,1);
    }

    @Override
    public boolean equals(Object object)
    {
        boolean isEqual= false;

        if (object != null && object instanceof Pair)
        {
            isEqual = (this.getX() == ((Pair) object).getX() && this.getY() == ((Pair) object).getY());
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.getX() + this.getY() + hashCode();
    }

    @Override
    public String toString() {
        return "(" + this.x + "|" + this.y + ")";
    }

}