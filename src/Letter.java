/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameihm;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author nassim
 */
public class Letter extends JLabel {
    
    private int speed;
    private boolean visible;
    private String letter;

    public Letter(int x, int y, int speed, String letter) {
        this.speed = speed;
        this.visible = true;
        this.letter = letter;
        
        this.setSize(140, 140);
        this.setIcon(new ImageIcon(getClass().getResource("/gameihm/images/100/"+letter+"1.png")));
        this.setLocation(x, y);
        
    }
    
    public String getLetter() {
        return letter;
    }
    
    public void updateLetter(String letter) {
        this.letter = letter;
        this.setIcon(new ImageIcon(getClass().getResource("/gameihm/images/100/"+letter+"1.png")));
    }
    
    public void move(int FPS) {
        this.setLocation(this.getLocation().x, this.getLocation().y+speed/FPS);
    }
    
    public void playAudio() {
        
        try {
            InputStream file = new FileInputStream("/home/nassim/NetBeansProjects/gameIhm/src/gameihm/sounds/letters/Letter " + letter + " - Phonics.wav");

            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(file);

            // play the audio clip with the audioplayer class
                        AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
    }
    
}
