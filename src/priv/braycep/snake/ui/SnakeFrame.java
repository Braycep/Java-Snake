package priv.braycep.snake.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SnakeFrame extends Frame {
    public static final int BLOCK_WIDTH = 15;
    public static final int BLOCK_HEIGHT = 15;
    public static final int ROW = 40;
    public static final int COL = 40;

    private SnakeFrame sf = null;
    private int size = 0;
    private int score = 0;
    private MyPaintThread paintThread = new MyPaintThread();
    private Snake snake = new Snake(this);
    private Food food = new Food();
    private boolean end = false;

    public void gameOver(){
        this.end = true;
    }

    public static void main(String[] args) {
        new SnakeFrame().launch();
    }

    public void launch(){
        sf = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.keyPressed(e);
            }
        });
        this.setTitle("贪吃蛇小游戏  -  By Braycep");
        this.setSize(ROW*BLOCK_HEIGHT,COL*BLOCK_WIDTH);
        this.setLocation(300,40);
        this.setBackground(Color.white);
        this.setResizable(false);
        this.setVisible(true);
        new Thread(paintThread).start();
    }

    /**
     * 绘制背景网格
     * @param g
     */
    @Override
    public void paint(Graphics g){
        //设置网格线的颜色
        g.setColor(Color.gray);
        for(int i = 0;i <= ROW;i++){
            g.drawLine(15,i*BLOCK_HEIGHT,COL*BLOCK_WIDTH - 15,i*BLOCK_HEIGHT);
        }
        for(int i = 0;i <= COL;i++){
            g.drawLine(i*BLOCK_WIDTH,30,i*BLOCK_WIDTH,ROW*BLOCK_HEIGHT - 15);
        }
    }

    public class MyPaintThread implements Runnable {
        @Override
        public void run(){
            while (true){
                if (end) {
                    if (JOptionPane.showConfirmDialog(sf,"你获得了"+score+"分，是否需要重新开始？","提示：",JOptionPane.YES_NO_OPTION) == 0){
                        sf.dispose();
                        new SnakeFrame().launch();
                    }else {
                        System.exit(0);
                    }
                    break;
                }else {
                    repaint();
                    snake.checkDead();
                }
            }
        }
    }

    private Image offScreenImage = null;
    @Override
    public void update(Graphics g){
        if (offScreenImage == null) {
            offScreenImage = this.createImage(ROW*BLOCK_HEIGHT,COL*BLOCK_WIDTH);
        }
        Graphics offg = offScreenImage.getGraphics();
        paint(offg);
        g.drawImage(offScreenImage,0,0,null);
        Color color = g.getColor();
        g.setColor(Color.red);
        g.drawString("当前分数："+score,15,42);
        g.setColor(color);
        if (snake.ateFood(food)){
            size++;
            if (size >= 10){
                score += size/10;
            }else {
                score += 1;
            }
        }
        food.draw(g);
        snake.draw(g);
    }

}
