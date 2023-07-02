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
    public static  int width = 800;
    public static   int height = 800;
    public static int row = height / CELL_SIZE;
    public static int column = width / CELL_SIZE;
    private Snake snake;
    private  Fruit fruit;
    private  Timer t;
    private int speed = 100;   //0.1秒
    private static String direction;
    private boolean allowKeyPress;
    private int score;

    public Main(){

        reset();
        //snake = new Snake();
        //fruit = new Fruit();
        //direction = "Right";
        addKeyListener(this);
        //allowKeyPress = true;
    }

    private  void setTimer(){
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                repaint();
            }
        },0, speed);
    }
    private void reset(){
        score = 0;
        if(snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        setTimer();
    }

    @Override
    public void  paintComponent(Graphics g){
        //check if the snake bites itself
        ArrayList<Node> snake_body = snake.getSnakeBody();
        Node head = snake_body.get(0);
        for(int i = 1; i < snake_body.size(); i++){
            if(snake_body.get(i).x == head.x && snake_body.get(i).y == head.y){
                allowKeyPress = false;   //遊戲結束
                t.cancel();
                t.purge();
                int response = JOptionPane.showOptionDialog(this, "Game Over!!! Would you like ti start over?", "Game Over."
                        ,JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null ,null ,JOptionPane.YES_OPTION);
                switch (response){
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }

        //System.out.println("We are calling paint component...");
        //draw a black background
        g.fillRect(0,0,width,height);
        fruit.drawFruit(g);
        snake.drawSnake(g);


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

        //check if the snake eats the fruit
        if(snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()){
            //System.out.println("Eating the fruit!!!"); //測試有沒有吃到水果
            //1. set fruit to a new location
            fruit.setNewLocation(snake);
            //2. drawFruit
            fruit.drawFruit(g);
            //3. score++

        }else{
            snake.getSnakeBody().remove(snake.getSnakeBody().size() - 1);
        }

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
