package com.commany;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
public class Main extends JPanel implements KeyListener {
    public static  final  int CELL_SIZE = 20;
    public static  int width = 400;
    public static   int height = 400;
    public static int row = height / CELL_SIZE;
    public static int column = width / CELL_SIZE;
    private Snake snake;
    private  Fruit fruit;
    private  Timer t;
    private int speed = 100;   //0.1秒
    private static String direction;
    private boolean allowKeyPress;

    public Main(){
        snake = new Snake();
        fruit = new Fruit();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                repaint();
            }
        },0, speed);
        direction = "Right";
        addKeyListener(this);
        allowKeyPress = true;
    }

    @Override
    public void  paintComponent(Graphics g){
        //System.out.println("We are calling paint component...");
        //draw a black background
        g.fillRect(0,0,width,height);
        snake.drawSnake(g);
        fruit.drawFruit(g);

        //remove snake tail and put it in head
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;

        //right, x += CELL_SIZE
        //left, x -= CELL_SIZE
        //down, y += CELL_SIZE
        //up, y -= CELL_SIZE
        if (direction.equals("Left")){
            snakeX -= CELL_SIZE;
        } else if (direction.equals("Up")) {
            snakeY -= CELL_SIZE;
        } else if (direction.equals("Right")) {
            snakeX += CELL_SIZE;
        } else if (direction.equals("Down")) {
            snakeY += CELL_SIZE;
        }
        Node newHead = new Node(snakeX,snakeY);
        snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
        snake.getSnakeBody().add(0, newHead);

        allowKeyPress = true;
        requestFocusInWindow();
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        if(allowKeyPress){
            if(e.getKeyCode() == 37 && !direction.equals("Right")){
                direction = "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            }else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }
            allowKeyPress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
