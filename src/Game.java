/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameihm;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nassim
 */
public class Game {
    
    public final int FPS = 60;
    private JPanel screen;
    private int score;
    private int level;
    private int life;
    private boolean running;
    private boolean paused;
    private boolean started;
    private Vector<Letter> letters;
    private String goal;
    private Thread thread;
    
    //view
    private JLabel scoreView;
    private JLabel lifeView;
    private JLabel targetView;
    

    public Game(JPanel screen, JLabel scoreView, JLabel lifeView, JLabel targetView) {
        this.screen = screen;
        this.score = 0;
        this.level = 1;
        this.running = false;
        this.life = 5;
        started = false;
        this.paused = false;
        
        letters = new Vector();
        
        //view
        this.lifeView = lifeView;
        this.scoreView = scoreView;
        this.targetView = targetView;
        
        
        thread = new Thread() {
            @Override
            public void run() {
                gameLoop(); //To change body of generated methods, choose Tools | Templates.
            }   
        };
    }
    
    
    
    public void gameLoop() {
        
        //while(true)                         //preventing thread from die
        while (true) {
            if(!(life != 0 && !paused))
                continue;
            
            for(Letter letter : letters) {
                letter.move(1);
                
                if(letter.getLetter().equals(goal) && letter.getLocation().y >= 540) {
                    //running = false;
                    life--;
                    
                    if(life == 0) {
                    System.out.println("letter => " + letter.getLetter() + "   " + life);
                    screen.removeAll();
                    screen.add(new JLabel("TRY AGAIN"));
                    screen.repaint();
                    screen.revalidate();
                    }
                }
                
                
                // if it's not the goal, then desapire and get a new one
                if(letter.getLocation().y >= 540) {
                    letter.setLocation(letter.getLocation().x, -10);
                    letter.updateLetter(Character.toString((char)getRandomNumber()));
                    letter.setLocation(letter.getLocation().x, screen.getY() + 10);
                }
  
            } 
            
            //updating the screen
            lifeView.setText("Life " + Integer.toString(life));
            scoreView.setText("Score: " + Integer.toString(score));
            targetView.setText(goal);
            
            
            screen.repaint();
            screen.revalidate();
            
            
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
        //running = false;
    }
    
    public void init() {
        Letter a, b, c, d;
        goal = "A";
        
        int panelX = 135;
        int panelY = screen.getY();
        
        score = level = 0;
        life = 5;
        running = false;
        paused = false;
        
        screen.removeAll();
        screen.repaint();
        screen.revalidate();
           
        
        a = new Letter(panelX+10, panelY+10, 3, "A");
        a.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                testLetter(a);
            }
            
        }) ;
        letters.add(a);
        
        
        b = new Letter(panelX+160, panelY+10, 4, "B");
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                testLetter(b);
            }
            
        });
        letters.add(b);
        
        
        c = new Letter(panelX+320, panelY+10, 3, "C");
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                testLetter(c);
            }
        }) ;
        letters.add(c);
                
        
        d = new Letter(panelX+490, panelY+10, 2, "D");
        d.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                testLetter(d);
            }
        });
        letters.add(d);
        
        screen.add(a);
        screen.add(b);
        screen.add(c);
        screen.add(d);

    }
    
    public void start() {

        
        init();
        running = true;
        if(!started) {
            started = !started;
            thread.start();
        }
        
    }
    
    public void pause() {
        
        paused = !paused;
        if(paused)
            thread.suspend();
        else
            thread.resume();
    }
    
    
    
    //utility methodes
    
    private void testLetter(Letter letter) {
        if((life == 0 || paused) && !running)
                    return;
                
                if(letter.getLetter().equals(goal)) {
                    letter.playAudio();
                    score++;
                    goal = Character.toString((char)getRandomNumber());
                    //a.updateLetter(Character.toString((char)getRandomNumber()));
                    letter.setLocation(letter.getLocation().x, 0);
                } else
                    life--;
    }
    
    private static int getRandomNumber() {
		Random r = new Random();
		return r.nextInt((90 - 65) + 1) + 65;
	}
    
    
    
    
    
}
