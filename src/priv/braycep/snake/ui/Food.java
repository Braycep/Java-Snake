package priv.braycep.snake.ui;

import java.awt.*;
import java.util.Random;

public class Food {
    private int row;
    private int col;

    private static final int BLOCK_WIDTH = SnakeFrame.BLOCK_WIDTH;
    private static final int BLOCK_HEIGHT = SnakeFrame.BLOCK_HEIGHT;

    private static final Random r = new Random();

    private Color color = Color.green;

    public Food(int row,int col){
        this.row = row;
        this.col = col;
    }

    public Food(){
        this(new Random().nextInt(37) + 2,new Random().nextInt(38) + 1);
    }

    public void reAppear(){
        this.row = new Random().nextInt(37) + 2;
        this.col = new Random().nextInt(38) + 1;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(color);
        g.fillRect(col*BLOCK_WIDTH,row*BLOCK_HEIGHT,BLOCK_WIDTH,BLOCK_HEIGHT);
        //System.out.println("Food: "+this.row+","+this.col);
        g.setColor(c);

        /*if (color == Color.red){
            color = Color.green;
        }else {
            color = Color.red;
        }*/
    }

    public Rectangle getRec(){
        return new Rectangle(col*BLOCK_WIDTH,row*BLOCK_HEIGHT,BLOCK_WIDTH,BLOCK_HEIGHT);
    }
}
