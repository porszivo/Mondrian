package org.justclick.mki;

import java.awt.Graphics2D;

/**
 * Helperclass to represent a pair of coordinates
 *
 * @author Marcel Kiessling 1052167
 * @version 1.0
 */
class Pair {

    private int x;
    private int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void draw(Graphics2D g2) {
        g2.fillOval(x, y, 1, 1);
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object instanceof Pair) {
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