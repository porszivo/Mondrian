/**
 * This class represents the abstract player class
 *
 * @author Marcel Kieﬂling 1052167
 * @version 1.0
 */

import java.awt.*;
import java.util.LinkedList;

public abstract class Player {

    Color color;

    /**
     *  Player Class
     *
     **/
    public Player(Color color) {
        this.color = color;
    }

    public abstract Rectangle getBounds();

    /**
     * This function is to calculates the middle of the player
     *
     * @return  Pair    The middle pair of the shape
     */
    public abstract Pair calcMid();

    /**
     * @param g2
     */
    public abstract void draw(Graphics2D g2);

    /**
     * @param g2
     * @param color
     */
    public abstract void setColor(Graphics2D g2, Color color);

    abstract void changeDirection(Player p);

    public abstract void move(int i);

    public abstract int getX();
    public abstract int getY();
    public abstract int getS();
    public Color getColor() { return color; }

    abstract int getD();

    abstract void setD(int d);

    public abstract LinkedList<Pair> getPath();
    public abstract void clearPath();

    public abstract boolean getTop();
    public abstract boolean getRight();

    public abstract void deleteLoop(Pair a);
}
