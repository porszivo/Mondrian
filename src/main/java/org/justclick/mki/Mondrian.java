package org.justclick.mki;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Mondrian extends JFrame {

    public Mondrian() {
        Board board = new Board();
        add(board);
        setResizable(false);
        pack();
        setTitle("Mondrian");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Mondrian window = new Mondrian();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            window.repaint();
        });

    }
}
