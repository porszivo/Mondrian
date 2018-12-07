/**
 * This class represents the player
 * Human and Cpu
 *
 * @author Marcel Kie�ling 1052167
 * @version 1.0
 */

import java.awt.*;
import java.util.LinkedList;

public class Human extends Player {

    private int x,y,s;
    public LinkedList<Pair> path;

    /* The current path, the human-player is on */

    /* Helpervariable to detect the last direction turn (vertical) */
    public boolean isRight;
    /* Helpervariable to detect the last direction turn (horizontal) */
    public boolean isTop;

    /**
     *  Player Class:
     *
     **/
    public Human() {
        super(Color.BLUE);
        this.s = 8;
        this.x = 0 - (s/2);
        this.y = 100 - (s/2);
        Pair middle = calcMid();
        path = new LinkedList<Pair>();
        path.add(middle);
        this.isRight = true;
        this.isTop = true;
    }

    /**
     * This is the moveOperation, depending on which direction is pressed
     * It will also store the Pair of the last Coords in the path
     *
     * @param d     Direction:
     *              It is build like the numPad
     *              ***8***  UP
     *              4*****6  LEFT - RIGHT
     *              ***2***  DOWN
     *
     **/
    @Override
    public void move(int d) {
        if(d == 8) {
            y -= 1;
            if(!isTop) isTop = true;
        }
        if(d == 2) {
            y += 1;
            if(isTop) isTop = false;
        }
        if(d == 6) {
            x += 1;
            if(!isRight) isRight = true;
        }
        if(d == 4) {
            x -= 1;
            if(isRight) isRight = false;
        }

        Pair middle = calcMid();
        path.add(new Pair(middle.x, middle.y));
        if(path.contains(middle)) deleteLoop(middle);
    }

    /**
     * Method to eliminate loops.
     * If a player hits a current path, the loop will be eliminated
     *
     * @param a Loop-Beginning - Ending-Point
     */
    @Override
    public void deleteLoop(Pair a) {
        int i = path.lastIndexOf(a);
        int j = path.indexOf(a);
        for(;i>j;i--) {
            path.remove(i);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, s, s);
    }

    /**
     * This function is to calculates the middle of the player
     *
     * @return  Pair    The middle pair of the shape
     */
    public Pair calcMid() {
        int tX = (this.x+(s+1)/2)-1;
        int tY = (this.y+(s+1)/2)-1;

        return new Pair(tX, tY);
    }

    /**
     * @param g2
     */
    public void draw(Graphics2D g2) {
        g2.fillOval(x, y, s, s);
        for(Pair p : path) {
            setColor(g2, Color.BLUE);
            p.draw(g2);
        }
    }

    /**
     * @param g2
     * @param color
     */
    public void setColor(Graphics2D g2, Color color) {
        g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(color);
    }

    @Override
    void changeDirection(Player p) {

    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getS() { return s; }

    @Override
    int getD() {
        return 0;
    }

    @Override
    void setD(int d) {

    }

    @Override
    public LinkedList<Pair> getPath() {
        return path;
    }

    @Override
    public void clearPath() {
        path.clear();
    }

    @Override
    public boolean getTop() {
        return isTop;
    }

    @Override
    public boolean getRight() {
        return isRight;
    }

}
