package mino;
import GameManager.*;


import java.awt.Color;
import java.awt.Graphics2D;


//super class for all Tetro Minos
public class Mino {

    public Block b [] = new Block[4];
    public Block tempB [] = new Block[4];
    int autoDropCounter = 0;
    public int direction  = 1;  //There are four directions for each mino (1,2,3,4)
    boolean leftCollision,  rightCollisison, bottomCollisison; //prevent  frame collision 
    public boolean active = true; //to activate or deativate a mino when it hits the bottom of the frame
     public boolean deactivating;
    int deactivateCounter = 0;



    public void create (Color c ) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB [0] = new Block(c);
        tempB [1] = new Block(c);
        tempB [2] = new Block(c);
        tempB [3] = new Block(c);
    }

    public void setXY (int x, int y) {}
    public void updateXY (int direction) {

        //Check for collission before rotation
        checkRotationCollisison();

        if (leftCollision == false && rightCollisison == false && bottomCollisison == false) {

            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }

    //These methods are used to get the direction of the mino
    public void getDirection1 () {}
    public void getDirection2 () {}
    public void getDirection3 () {}
    public void getDirection4 () {}

    public void checkMovementCollisison () {

        //prevent collision

        leftCollision  = false;
        rightCollisison  = false;
        bottomCollisison  = false;

        //call the static block collision
        checkStaticCollision();

        //Check Frame collisionb
        //Left wall
        for (int i =0; i < b.length; i++) {
            if (b[i].x == PlayManager.left_x) {
                leftCollision = true;
            }
        }

        //Right Wall
        for (int i =0; i < b.length; i++) {
            if (b[i].x + Block.SIZE == PlayManager.right_x){
                rightCollisison = true;
            }
        }

        //Bottom Floor
        for (int i = 0; i < b.length; i++) {
            if (b[i].y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollisison = true;
            }
        }

    }
    //Method to check collision withthe frame during mino movement
    public void checkRotationCollisison () {

        leftCollision = false;
        rightCollisison = false;
        bottomCollisison = false;

        //call the static block collision
        checkStaticCollision();

        //left wall 
        for (int i =0; i < b.length; i++) {
            if (tempB [i] .x < PlayManager.left_x) {
                leftCollision = true;
            }
        }

        //right wall
        for (int i = 0; i < b.length; i++) {
            if (tempB [i] .x  + Block.SIZE > PlayManager.right_x) {
                rightCollisison = true;
            }
        }

        //bottom collision
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollisison = true;
            }
        }

    }

    //collision check for the ststic blocks 
    private void checkStaticCollision () {
        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {

            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            //check Down
            for (int ii = 0; ii < b.length; ii++) {
                if (b[ii].y + Block.SIZE == targetY && b[ii]. x == targetX){
                    bottomCollisison = true;
                }
            }

            //Check left
            for (int ii =0; ii< b.length; ii++) {
                if (b[ii].x - Block.SIZE == targetX && b[ii].y == targetY){
                    bottomCollisison = true;
                }
            }

            //Check right
            for (int ii =0; ii < b.length; ii++) {
                if (b[ii].x + Block.SIZE == targetX && b[ii].y == targetY) {
                    rightCollisison = true;
                }
            }
        }
    }


    public void update () {

        if (deactivating) {
            deactivating();
        }

        //move the mino left, right, down or up based on the key pressed
        if (KeyHandler.upPressesd) {

            //handles minos direction change
            switch ( direction) {
                case 1 : getDirection2();  break;
                case 2 : getDirection3(); break ;
                case 3 : getDirection4(); break;
                case 4 : getDirection1(); break;
            } 
            KeyHandler.upPressesd = false ;
        }

        checkMovementCollisison();
        //checkStaticCollision();

        if (KeyHandler.downPressed) {

            //If the down key is pressed the mino should move downwards by one block 
            //If the mino 's bottom is not hitting, it can go down 
            if (bottomCollisison == false) {
                b[0] . y += Block.SIZE;
                b[1] . y += Block.SIZE;
                b[2] . y += Block.SIZE;
                b[3] . y += Block.SIZE;
    
                //when moved down, reset the auto drop counter
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }


        if (KeyHandler.leftPressed) {

            if (leftCollision == false ) { //check first for left collision

                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }

            KeyHandler.leftPressed = false;
        
        }
        if (KeyHandler.rightPressed) {

            //check first for collision with the right wall 
            if (rightCollisison == false) {

                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

        //Method to prevent to activate a block 
        if (bottomCollisison ) {
            deactivating = true;
        }
        else {
        autoDropCounter++;     //drop the mino every 60 frames
        if (autoDropCounter == PlayManager.dropInterval) { // Use the comparison operator '==' instead of '='
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            autoDropCounter = 0;
            
        }
        }

    }

    //method to deactivate mino if it reaches the bottom]
    private void deactivating () {
        deactivateCounter ++;

        //wait for 45 fps untill deactivate
        if (deactivateCounter == 45) {

            deactivateCounter =0;
            checkMovementCollisison(); // check if the bottom is still hitting

            //if the bottom is still hiiting  after 45 fps, deactivate the mino
            if (bottomCollisison) {
                active = false;
            }
        }
    }

    public void draw (Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + margin, b[0].y +margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[1].x + margin, b[1].y  + margin,Block.SIZE - (margin * 2),Block.SIZE - (margin * 2));
        g2.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE -  (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[3].x + margin, b[3].y + margin,Block.SIZE - ( margin * 2),Block.SIZE -  (margin * 2));
    }
    

}
