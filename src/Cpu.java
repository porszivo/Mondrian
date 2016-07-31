import java.awt.*;
import java.util.LinkedList;

public class CPU extends Player {

    private final Color color = Color.RED;
    private int x,y,s;
    public int d = 8;
    public final int diff = 150; /** Difficult level */

    public CPU() {
        super(Color.RED);
        this.s = 12;
        this.x = 100 - (s/2);
        this.y = 100 - (s/2);
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
    @Override
    public Pair calcMid() {
        int tX = (this.x+(this.s+1)/2)-1;
        int tY = (this.s+(s+1)/2)-1;

        return new Pair(tX, tY);
    }

    /**
     * @param g2
     */
    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval(x, y, s, s);
    }

    /**
     * @param g2
     * @param color
     */
    @Override
    public void setColor(Graphics2D g2, Color color) {
        g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(color);
    }


    /**
     * CPU movement differs from the Players movement
     * the CPU can move in all 8 directions and will change his direction
     * according to the players position.
     * This direction will be stored in the d variable of this class
     *
     * @param p
     */
    @Override
    public void changeDirection(Player p) {
        int r = (int)((Math.random()) * diff + 1);
        if(r==1) {
            /** Doing some math here
             *  the result is always a direction from 1 to 8
             */
            int dX = 5;
            int dY = 0;
            if(p.getX()>this.getX()) dX = 6;
            if(p.getX()<this.getX()) dX = 4;
            if(p.getY()>this.getY()) dY = -3;
            if(p.getY()<this.getY()) dY = 3;
            d = dX + dY;
        }
        this.move(d);
    }

    /**
     * This is the moveOperation, depending on which direction is pressed
     * It will also store the Pair of the last Coords in the path
     *
     * @param d     Direction:
     *              It is build like the numPad
     *              7**8**9
     *              4*****6
     *              1**2**3
     *
     **/
    @Override
    public void move(int d) {
        if(d == 1) {
            x = x-1;
            y = y+1;
        }
        if(d == 2) {
            y += 1;
        }
        if(d == 3) {
            x += 1;
            y += 1;
        }
        if(d == 4) {
            x -= 1;
        }
        if(d == 6) {
            x += 1;
        }
        if(d == 7) {
            x -= 1;
            y -= 1;
        }
        if(d == 8) {
            y -= 1;
        }
        if(d == 9) {
            x += 1;
            y -= 1;
        }
        this.d = d;

    }

    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }
    @Override
    public int getS() { return s; }
    @Override
    public int getD() { return d; }
    @Override
    public void setD(int d) { this.d = d; }

    @Override
    public LinkedList<Pair> getPath() {
        return null;
    }

    @Override
    public void clearPath() {    }

    @Override
    public boolean getTop() {
        return false;
    }

    @Override
    public boolean getRight() {
        return false;
    }

    @Override
    public void deleteLoop(Pair a) {    }


}
