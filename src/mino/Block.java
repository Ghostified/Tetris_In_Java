package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle {

    public int x, y;
    public static final int SIZE = 30; // Create a 30x30 block
    public Color c;

    public Block (Color c) {
        this.c = c;
    }

    public void draw (Graphics2D g2) {
        int marging =2;
        g2.setColor(c);
        g2.fillRect(x +marging, y + marging, SIZE- (marging*2), SIZE- (marging*2));
    }
}
