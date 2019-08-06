package org.justclick.mki;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

/**
 * This class represents the abstract player class
 *
 * @author Marcel Kiessling 1052167
 * @version 1.0
 */
public abstract class Player {

    private Color color;

    /**
     * Player Class
     **/
    Player(Color color) {
        this.color = color;
    }

    public abstract Rectangle getBounds();

    /**
     * This function is to calculates the middle of the player
     *
     * @return Pair    The middle pair of the shape
     */
    public abstract Pair calcMid();

    public abstract void draw(Graphics2D g2);

    public abstract void setColor(Graphics2D g2, Color color);

    abstract void changeDirection(Player p);

    public abstract void move(int i);

    public abstract int getX();

    public abstract int getY();

    public abstract int getS();

    Color getColor() {
        return color;
    }

    abstract int getD();

    public abstract List<Pair> getPath();

    public abstract void clearPath();

    public abstract boolean getTop();

    public abstract boolean getRight();

    public abstract void deleteLoop(Pair a);
}
