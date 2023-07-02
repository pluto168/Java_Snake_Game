package com.commany;

import org.w3c.dom.css.CSSStyleDeclaration;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    public static  final  int CELL_SIZE = 20;
    public static  int width = 400;
    public static   int height = 400;
    public static int row = height / CELL_SIZE;
    public static int column = width / CELL_SIZE;
    private Snake snake;

    public Main(){
        snake = new Snake();
    }

    @Override
    public void  paintComponent(Graphics g){
        //draw a black background
        g.fillRect(0,0,width,height);
        snake.drawSnake(g);
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width,height);
    }
    public static void main(String[] args) {
        JFrame windows = new JFrame("Snake Game");
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setContentPane(new Main());
        windows.pack();
        windows.setLocationRelativeTo(null);
        windows.setVisible(true);
        windows.setResizable(false);
    }
}
