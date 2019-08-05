package org.justclick.mki;

import java.awt.*;
import java.util.LinkedList;

public class Cpu extends Player {

    private int x, y, s;
    private int d = 8;

    Cpu() {
        super(Color.RED);
        this.s = 12;
        this.x = 100 - (s / 2);
        this.y = 100 - (s / 2);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, s, s);
    }

    /**
     * This function is to calculates the middle of the player
     *
     * @return Pair    The middle pair of the shape
     */
    @Override
    public Pair calcMid() {
        int tX = (this.x + (this.s + 1) / 2) - 1;
        int tY = (this.s + (s + 1) / 2) - 1;

        return new Pair(tX, tY);
    }

    /**
     * @param g2 graphics2d
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval(x, y, s, s);
    }

    /**
     * @param g2    graphics2d
     * @param color color to be set
     */
    @Override
    public void setColor(Graphics2D g2, Color color) {
        g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(color);
    }


    /**
     * Cpu movement differs from the Players movement
     * the Cpu can move in all 8 directions and will change his direction
     * according to the players position.
     * This direction will be stored in the d variable of this class
     *
     * @param player player to change direction
     */
    @Override
    public void changeDirection(Player player) {
        int diff = 150;
        int r = (int) ((Math.random()) * diff + 1);
        if (r == 1) {
            // the result is always a direction from 1 to 8
            int dX = 5;
            int dY = 0;
            if (player.getX() > this.getX()) dX = 6;
            if (player.getX() < this.getX()) dX = 4;
            if (player.getY() > this.getY()) dY = -3;
            if (player.getY() < this.getY()) dY = 3;
            d = dX + dY;
        }
        this.move(d);
    }

    /**
     * This is the moveOperation, depending on which direction is pressed
     * It will also store the Pair of the last Coords in the path
     *
     * @param d Direction:
     *          It is build like the numPad
     *          7**8**9
     *          4*****6
     *          1**2**3
     **/
    @Override
    public void move(int d) {
        switch (d) {
            case 1:
                x -= 1;
                y += 1;
                break;
            case 2:
                y += 1;
                break;
            case 3:
                x += 1;
                y += 1;
                break;
            case 4:
                x -= 1;
                break;
            case 6:
                x += 1;
                break;
            case 7:
                x -= 1;
                y -= 1;
                break;
            case 8:
                y -= 1;
                break;
            case 9:
                x += 1;
                y -= 1;
                break;
        }
        this.d = d;

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getS() {
        return s;
    }

    @Override
    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    @Override
    public LinkedList<Pair> getPath() {
        return null;
    }

    @Override
    public void clearPath() {
    }

    @Override
    public boolean getTop() {
        return false;
    }

    @Override
    public boolean getRight() {
        return false;
    }

    @Override
    public void deleteLoop(Pair a) {
    }


}
