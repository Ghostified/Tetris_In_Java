package GameManager;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent.Type;

public class Sound {

 
    Clip musicClip;
    URL url [] = new URL [10];

    //            FileInputStream fin = new FileInputStream("C:\\Users\\allan.branson\\Downloads\\testout.txt");


    public Sound () {

        url[0] = getClass().getResource("/resourcesFolder/Tetris (NES) Music.wav");
        url[1] = getClass().getResource("/resourcesFolder/delete line.wav");
        url[2] = getClass().getResource("/resourcesFolder/gameover.wav");
        url[3] = getClass().getResource("/resourcesFolder/rotation.wav");
        url[4] = getClass().getResource("/resourcesFolder/touch floor.wav");
    }
    public void play(int i , boolean music) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url [i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(ais);
            clip.addLineListener( new LineListener() {

                public void update (LineEvent event) {
                    if (event.getType() == Type.STOP) {
                        clip.close();
                    }
                }
            });

            ais.close();
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loop () {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop  () {
        musicClip.stop();
        musicClip.close();
    }
}