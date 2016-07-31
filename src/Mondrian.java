import javax.swing.*;
import java.awt.*;

public class Mondrian extends JFrame {

    private Board board;

    public Mondrian() {
        board = new Board();
        add(board);
        setResizable(false);
        pack();
        setTitle("Mondrian");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }



    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Mondrian window = new Mondrian();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                window.repaint();
            }
        });

    }
}
