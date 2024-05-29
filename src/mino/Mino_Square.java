package mino;

import java.awt.Color;

public class Mino_Square extends Mino{

    public Mino_Square () {

        create(Color.yellow);
    }

    public void setXY (int x, int y) {
        //o o 
        //o o

        b[0]. x = x;
        b[0] .y = y;
        b[1] .x = b[0] .x ;
        b[1] .y = b[0] . y + Block.SIZE;
        b[2] .x = b[0] .x  + Block.SIZE;
        b[2] .y = b[0] .y;
        b[3] .x = b[0] .x +  Block.SIZE;
        b[3] .y = b[0] .y  + Block.SIZE; 

    }

    //The square Block does not need to be rotated

    public void getDirection1 () {

        //updateXY(1);
    }
    public void getDirection2 () {

        //updateXY(2);
    }
    public void getDirection3 () {

        //updateXY(3);
    }
    public void getDirection4 () {

        //updateXY(4);
    }

}
