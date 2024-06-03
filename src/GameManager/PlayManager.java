package GameManager;

// Purpose: Handling the basic game elements

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.Random;

import mino.Block;
import mino.Mino;
import mino.Mino_Bar;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_Square;
import mino.Mino_T;
import mino.Mino_Z1;
import mino.Mino_Z2;

public class PlayManager {

    //main play area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    //Initialize next mino
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList <Block> staticBlocks = new ArrayList<>();

    //others
    public static int dropInterval = 60; //mino drops every 60 frames or 1 second 
    boolean gameOver;

    //Effect 
    boolean effectCounterOn;
    int effectCounter;
    ArrayList <Integer> effectY = new ArrayList <> ();

    //Scores  and levels
    int level = 1;
    int lines;
    int scores;


    //constructor 
    public PlayManager () {
        //main play area frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);  //1280/2 - 360/2 = 460
        right_x = left_x + WIDTH; //460 + 360 = 820
        top_y = 50;
        bottom_y = top_y + HEIGHT; //50 + 600 = 650

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        //set the satrting mino
        currentMino = pickMino ();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }

    private Mino pickMino () {
        //Pick a random mino
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0: mino = new Mino_L1 (); break;
            case 1 : mino = new Mino_L2(); break;
            case 2: mino = new Mino_Square(); break;
            case 3: mino = new Mino_Bar(); break;
            case 4 : mino = new Mino_T(); break;
            case 5 : mino = new Mino_Z1() ; break;
            case 6 : mino = new Mino_Z2(); break;
        }
        return mino;
    }

    public void update () {

        //Check if currrentMino is active 
        if (currentMino.active == false ) {
            //if the current mino is not active , put it in the static blocks
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            //Check if the game is over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y)  {
                //this means the ccurrent mino has collided with the bblock and can not move at all
                //its xy is similar to the the next mino
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.soundEffect.play(2, false);
            }

            currentMino.deactivating = false;
            
            //replace the current mino with the next mino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            //when mino becomes check if lines can be deleted
            checkDelete();
        }
        else {
            currentMino.update();
        }
    }

    private void  checkDelete() {
        //if the number of blocks in a line = 12 , we can then delete the lines

        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {

            //scan the play area for static blocks and increase the block count
            for (int i =0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i). y == y) {
                    //Increase the count if there is a ststic block
                    blockCount++;
                }
            }
            x += Block.SIZE;

            if (x == right_x) {

                //if the block count  is equal to 12 then the line is full of blocks 
                //so we can delete the line
                if (blockCount == 12) {

                    //wWhen we delete a line, set the effectcounter to true
                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() -1; i > -1; i-- ) {
                        //remove all blocks from the line
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount ++;
                    lines++;

                    //increas ethe drop speed
                    //if the line scores hits a certain number , increase the drop speed
                    //1 is the fastest
                    if (lines % 5 ==0  && dropInterval > 1 ) {
                        level ++;
                        if (dropInterval > 5) {
                            dropInterval -= 10;
                        } else  {
                            dropInterval -= 1;
                        }
                    }


                    //given a line has been deleted , all blocks above must be moved down tby one line
                    for (int i = 0;  i < staticBlocks.size(); i++) {
                        //if a block is above thr current y, move it down by a block size
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        //Add the score  based on the number of lines deleted
        if (lineCount > 0) {
            GamePanel.soundEffect.play(1, false);
            int singleLineScore = 10 * level;
            scores += singleLineScore * lineCount;
        }
    }

    public void draw (Graphics2D g2) {
        //Draw the main play area Frame
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y -4, WIDTH + 8, HEIGHT + 8);

        //Draw next minor fRAME
        //waiting area for Tetro mino
        int x = right_x + 100;
        int y = bottom_y  - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont (new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        //Draw the score Frame
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x , y); y += 70;
        g2.drawString("LINES: " + lines, x, y); y += 70;
        g2.drawString("SCORE: " + scores, x, y);

        //draw the current Mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }

        //Draw the next mino
        nextMino.draw(g2);

        //Draw the static blocks
        for (int i =0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }

        //Draw the effect when lines are deleted 
        if (effectCounterOn) {
            effectCounter++;

            //set color
            g2.setColor(Color.pink);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }

            //When the timer hits 10 frames, reset everything on the scanned array 
            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter =0;
                effectY.clear();
            }
        }


        //draw a pause feature
        g2.setColor(Color.YELLOW);
        g2.setFont(g2.getFont().deriveFont(50f));

        //Game over text on the NEXT  panel
        if (gameOver) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("GAME OVER ", x, y);
        }

        //Pause  Game key handler and text
        else if (KeyHandler.pausedPressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }


        //Draw the game Title
        x = 35;
        y = top_y + 320;
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Times New Roman", Font.ITALIC,60));
        g2.drawString("Simple Tetris", x + 20, y);


    }
}
