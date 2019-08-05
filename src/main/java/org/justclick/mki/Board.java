package org.justclick.mki;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board extends JPanel implements KeyListener, ActionListener {

    private final int WIDTH = 200;
    private final int HEIGHT = 200;
    private boolean isRunning;
    private boolean victory;

    private List<Player> player;
    private Timer timer = new Timer(1000 / 60, this);
    /**
     * if a area is complete, the playerpath will stored here
     **/
    private LinkedList<Pair> path = new LinkedList<>();

    /**
     * This are the filled Pair of coordinates
     **/
    private List<Pair> raster;

    private Player p;
    private Player c;

    Board() {
        super();
        player = new ArrayList<>();
        setBackground(Color.WHITE);
        timer.start();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);
        addKeyListener(this);
        setFocusable(true);
        isRunning = true;
        victory = false;
        this.addPlayer(new Human());
        this.addPlayer(new Cpu());
        raster = new ArrayList<>();
        p = player.get(0);
        c = player.get(1);
    }

    private void addPlayer(Player p) {
        player.add(p);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLines((Graphics2D) g);
        drawAreas((Graphics2D) g);
        drawPlayer((Graphics2D) g);
        if (!isRunning) drawGameOver((Graphics2D) g);
        if (victory) gameWon((Graphics2D) g);
    }

    private void drawAreas(Graphics2D g) {
        for (Pair p : raster) {
            setColor(g, Color.YELLOW);
            p.draw(g);
        }
    }

    private void drawLines(Graphics2D g) {
        for (Pair p : path) {
            setColor(g, Color.BLUE);
            p.draw(g);
        }
    }

    private void drawPlayer(Graphics2D g) {
        for (Player p : player) {
            setColor(g, p.getColor());
            p.draw(g);
        }
    }

    private void drawGameOver(Graphics2D g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.RED);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
        timer.stop();
    }

    private void gameWon(Graphics2D g) {
        String msg = "You WON! Score: " + (raster.size() * 100) / (WIDTH * HEIGHT) + "%";
        Font small = new Font("Helvetica", Font.BOLD, 16);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.GREEN);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
        timer.stop();
    }

    private void setColor(Graphics2D g2, Color color) {
        g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        g2.setPaint(color);
    }

    /**
     * Collision-Check: Between player and cpu
     * Check is between the borders of both objects
     */
    private void checkDirectHit() {
        if (c.getBounds().intersects(p.getBounds()) && !path.contains(p.calcMid())) {
            isRunning = false;
        }
    }

    /**
     * Collision-Check: Between Cpu and Border
     */
    private void cpuCollitionCheck() {
        int s = c.getS();
        int d = c.getD();

        if (c.getX() < 0) {
            if (d == 7) d = 9;
            else if (d == 1) d = 3;
            else d = 10 - d;
        }
        if (c.getX() + s > WIDTH) {
            if (d == 3) d = 1;
            else if (d == 9) d = 7;
            else d = 10 - d;
        }
        if (c.getY() < 0) {
            if (d == 7) d = 1;
            else if (d == 9) d = 3;
            else d = 10 - d;
        }
        if (c.getY() + s > HEIGHT) {
            if (d == 3) d = 9;
            else if (d == 1) d = 7;
            else d = 10 - d;
        }
        c.move(d);
    }

    /**
     * fillField:
     * Get the direction in the 2,4,5,6,8 pattern.
     * Depending on what d is given to this method and the last directionchange,
     * the floodfill parameters will be calculated and the Player-Path will be copied
     * to the Board-Path and the Player-Path will be cleared.
     * In case the filled area is bigger than 80%, after the floodfill, the game is over
     * and the player wins.
     *
     * @param d direction (2,4,5,6,8)
     */
    private void fillField(int d) {
        Pair mid = p.calcMid();
        int tX = mid.getX();
        int tY = mid.getY();
        /* The following 4 cases are for Player-Border-Collisions */
        if (d == 4) {
            if (p.getTop()) tY = mid.getY() + 1;
            else tY = mid.getY() - 1;
        }
        if (d == 6) {
            if (p.getTop()) tY = mid.getY() + 1;
            else tY = mid.getY() - 1;
        }
        if (d == 8) {
            if (p.getRight()) tX = mid.getX() - 1;
            else tY = mid.getX() + 1;
        }
        if (d == 2) {
            if (p.getRight()) tX = mid.getX() - 1;
            else tX = mid.getX() + 1;
        }

        /* 5: Is when the Player hits an already filled area */
        if (d == 5) {
            if (p.getRight()) tX--;
            else tX++;
            if (p.getTop()) tY++;
            else tY--;
        }

        /* The following 4 rows will move every Pair from the Player-Path to the Board-Path */
        this.path.addAll(p.getPath());
        p.clearPath();

        floodFill(tX, tY);

        /* Game is over when the filled Area is greater or equal than 80% */
        if (WIDTH * HEIGHT * 0.8 <= raster.size() + path.size()) {
            victory = true;
        }
    }

    /**
     * floodFill to add the coordinates to the raster list, iterative
     *
     * @param x coord x
     * @param y coord y
     */
    private void floodFill(int x, int y) {
        LinkedList<Pair> stack = new LinkedList<>();
        stack.add(new Pair(x, y));
        while (!stack.isEmpty()) {
            Pair xy = stack.pop();
            if (!raster.contains(xy)) raster.add(xy);
            x = xy.getX();
            y = xy.getY();
            if (x > 0 && !path.contains(new Pair(x - 1, y)) && !raster.contains(new Pair(x - 1, y)))
                stack.push(new Pair(x - 1, y));
            if (x < WIDTH - 1 && !path.contains(new Pair(x + 1, y)) && !raster.contains(new Pair(x + 1, y)))
                stack.push(new Pair(x + 1, y));
            if (y > 0 && !path.contains(new Pair(x, y - 1)) && !raster.contains(new Pair(x, y - 1)))
                stack.push(new Pair(x, y - 1));
            if (y < HEIGHT - 1 && !path.contains(new Pair(x, y + 1)) && !raster.contains(new Pair(x, y + 1)))
                stack.push(new Pair(x, y + 1));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * keyPressed-Method:
     * In the keyPressed-Method is most of the Playermovement logic.
     * Depending on the Arrow-Key, there is a board-board-check, so the Player cannot move out of the Board
     * If the ball hits a boarder, the fillField-Method will be called with the last used direction
     * *8*
     * 4*6
     * *2*
     * The next two lines check, if the Player is on a line and will move into a filled area OR if the player is moving backwards on the same path
     * If that is not the case, then the player.move-Method will be called
     * <p>
     * After the movement is done: If the player-mid is on a path, the fillField-Method will be called,
     * because that should lead to a complete path.
     * EDIT: !path.contains(mid) (mid is the last Pair where the Player was at): Is needed to detect if the player is just moving on the path
     *
     * @param e key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Pair mid = p.calcMid();
        if (e.getKeyCode() == KeyEvent.VK_UP && mid.getY() >= 0) {
            if (mid.getY() == 0) fillField(8);
            Pair n = new Pair(mid.getX() + 1, mid.getY() - 1);
            if (!raster.contains(n)) p.move(8);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && mid.getY() <= HEIGHT - 1) {
            if (mid.getY() >= HEIGHT - 1) fillField(2);
            Pair n = new Pair(mid.getX(), mid.getY() + 1);
            if (!raster.contains(n)) p.move(2);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && mid.getX() >= 0) {
            if (mid.getX() == 0) fillField(4);
            Pair n = new Pair(mid.getX() - 1, mid.getY());
            if (!raster.contains(n)) p.move(4);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && mid.getX() <= WIDTH - 1) {
            if (mid.getX() >= WIDTH - 1) fillField(6);
            Pair n = new Pair(mid.getX() + 1, mid.getY());
            if (!raster.contains(n)) p.move(6);
        }
        Pair xy = p.calcMid();
        if (path.contains(xy) && !path.contains(mid)) {
            fillField(5);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            //cpuPathHit();
            cpuCollitionCheck();
            checkDirectHit();
            c.changeDirection(p);
        }
        repaint();
    }
}
