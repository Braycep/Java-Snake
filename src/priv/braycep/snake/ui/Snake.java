package priv.braycep.snake.ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Snake {
    private static final int BLOCK_WIDTH = SnakeFrame.BLOCK_WIDTH;
    private static final int BLOCK_HEIGHT = SnakeFrame.BLOCK_HEIGHT;

    private Node head = null;
    private Node tail = null;

    private SnakeFrame sf;

    //取值：(2,0)~(38,37)
    //private Node node = new Node(new Random().nextInt(37) + 2,new Random().nextInt(38)/2,Direction.R);
    private Node node = new Node(2,0,Direction.R);

    private int size = 0;

    public Snake(SnakeFrame sf){
        head = node;
        tail = node;
        size++;
        this.sf = sf;
    }

    public void draw(Graphics g){
        move();
        if(head != null) {
            for (Node node = head;node != null;node = node.next){
                node.draw(g);
            }
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                if (head.dir != Direction.R){
                    head.dir = Direction.L;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (head.dir != Direction.L){
                    head.dir = Direction.R;
                }
                break;
            case KeyEvent.VK_UP:
                if (head.dir != Direction.D){
                    head.dir = Direction.U;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (head.dir != Direction.U){
                    head.dir = Direction.D;
                }
                break;
            default:
                break;
        }
        try{
            Thread.sleep(20);
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

    public void move(){
        addNodeInHead();
        deleteNodeInTail();
    }

    private void addNodeInHead() {
        Node node = null;
        switch (head.dir){
            case D:
                node = new Node(head.row+1,head.col,head.dir);
                break;
            case L:
                node = new Node(head.row,head.col-1,head.dir);
                break;
            case R:
                node = new Node(head.row,head.col+1,head.dir);
                break;
            case U:
                node = new Node(head.row-1,head.col,head.dir);
                break;
        }
        node.next = head;
        head.pre = node;
        head = node;
    }

    private void deleteNodeInTail() {
        Node node = tail.pre;
        tail = null;
        node.next = null;
        tail = node;
    }

    public Rectangle getRec(){
        return new Rectangle(head.col*BLOCK_WIDTH,head.row*BLOCK_HEIGHT,BLOCK_WIDTH,BLOCK_HEIGHT);
    }

    public boolean ateFood(Food food){
        if (this.getRec().intersects(food.getRec())){
            addNodeInHead();
            food.reAppear();
            return true;
        } else {
            return false;
        }
    }

    public void checkDead(){
        try{
            Thread.sleep(100);
        }catch (InterruptedException i){
            i.printStackTrace();
        }
        if ((head.row == 2 && head.dir == Direction.U) || (head.row == 38 && head.dir == Direction.D)){
            this.sf.gameOver();
        }
        if ((head.col == 1 && head.dir == Direction.L) || (head.col == 38 && head.dir == Direction.R)){
            this.sf.gameOver();
        }

        for (Node node = head.next;node != null;node = node.next){
            if (head.row == node.row && head.col == node.col){
                this.sf.gameOver();
            }
        }
    }

    public class Node {
        //节点位置坐标
        private int row;
        private int col;
        //方向
        private Direction dir = null;

        private Node pre = null;
        private Node next = null;

        public Node(int row, int col, Direction dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public void draw(Graphics g){
            Color c = g.getColor();
            g.setColor(Color.black);
            //填充节点颜色
            g.fillRect(col*BLOCK_WIDTH, row*BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
            g.setColor(c);
        }
    }
}
