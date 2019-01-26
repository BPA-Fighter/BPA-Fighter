/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
//TO DO LIST PELEASE ADD WHEN YOU HOP OFF
//Fix the Health bars to update on round reset
/**
 *
 * @author Time Crunchers
 *
 */
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gfx.Assets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import main.states.GameState;
import main.states.State;
import managers.KeyManager;

@SuppressWarnings({"unused", "serial"})
public class Game extends Canvas implements Runnable {

    public Timer counter;
    public int time = 6000;

    static String[] gArgs;

    // declare constants
    public static final String TITLE = "The Fight Of Time";
    public static final int WIDTH = 256;
    public static final int HEIGHT = 224;
    public static final int SCALE = 2;
    int choice = -1;
    public static boolean isNormal = false;
    public static boolean isHard = false;

    // tick variables
    public boolean running = false;
    public int tickCount = 0;

    // graphics
    private Graphics g;

    // states
    private State menuState;
    private State gameState;

    // input
    private KeyManager keyManager;

    // init jFrame
    private JFrame frame;

    // init scanner,and rNumberom
    private Scanner sc;
    private Random rNumber;

    // int map
    private int map = 0;

    public Game() {

        
        rNumber = new Random();

                GUI.main(gArgs);
        choice = GUI.getChoice();
        System.out.println("Choice: " + choice);
        
        if (choice == 0 || choice == 1) {
            if (choice == 0) {
               isNormal = true;
            } else if (choice == 1) {
                isHard = true;
            }
        
        int audioFileNumber = 1 + rNumber.nextInt(3);
     //   audioFileNumber = 2;
        System.out.println(audioFileNumber);
         String soundName;
        if (audioFileNumber == 1) {
             soundName = "/sound/OneLastDrink.wav";
           
        } else if (audioFileNumber == 2) {
             soundName = "/sound/snowMusic.wav";
           
        }
         else if (audioFileNumber == 3) {
             soundName = "/sound/FinalFight.wav";
         }  else { 
              soundName = "/sound/FinalFight.wav";
         }
        
        try {

                //  AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundName));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.err.println(e.getMessage());

            }
        
        // init frame properties
        frame = new JFrame(TITLE);
        frame.setSize(WIDTH * SCALE, HEIGHT * SCALE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);

        frame.setUndecorated(false);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        setFocusable(false);

        // user input
        keyManager = new KeyManager();
        frame.addKeyListener(keyManager);

        // pack everything
        frame.pack();
        }
    }

    public synchronized void start() throws IOException {

        
        // the program is running...
        running = true;

        // pre-load assets                
        Assets.init();

        // states
        gameState = new GameState(this);
        State.setState(gameState);
        // thread this class
        new Thread(this).start();
    }

    public synchronized void stop() {
        // if program is stopped, running is false
        running = false;
    }

    public void run() {

        // init vars
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        // while the program is running....
        while (running) {

            // get the current system time
            long now = System.nanoTime();
            // find delta by taking difference between now and last
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            // can render each frame...
            boolean canRender = true;

            // if ratio is greater than one, meaning...
            while (delta >= 1) {
                /* if the current time - last / n, where n
				   can be any real number is greater than 1
			    update the game...*/
                ticks++;
                tick();
                delta--;
                time();
                canRender = true;
            }

            // sleep program so that not to many frames are produced (reduce lag)
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // if can render...
            if (canRender) {
                // increment frames and render
                frames++;
                render();

            }

            // if one second has passed...
            if (System.currentTimeMillis() - lastTimer > 1000) {
                // increment last timer, output frames to user
                lastTimer += 1000;
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }

            //fileWritte();
        }

    }

    public void tick() {

        // update keyboard input
        keyManager.tick();

        // if current state exist, then update the game
        if (State.getState() != null) {

            // increment tick count and get state of program
            tickCount++;
            State.getState().tick();

        }
check(time);
    }

    public void render() {

        BufferStrategy bs = getBufferStrategy();

        // create a double buffering strategy
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // create temp white rect that fills screen
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        /* ALL DRAWING HERE */
        // init maps
        ImageIcon mainStage = new ImageIcon("/images/iceGuy_stage.png");

        // decision making: if player chooses a map (as def'd in start() method), draw that map 



        // if current state exist, then render		
        if (State.getState() != null) {
            tickCount++;
            State.getState().render(g);
        }

        /* ALL DRAWING HERE */
        g.dispose();
        bs.show();
    }

    public void time() {
         check(time); // this is to check if the timer has hit 0, if it has then it stops the timer
        time--;
        counter = new javax.swing.Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    
                    Thread.sleep(1000);
                } catch (Exception ex) {

                }
            }
        });
        System.out.println("Time " + time);
    }

    public void check(int time) {
        System.out.println("We made it hereeeeeeeeeeeeeeeeeeee");
        if (time <= 0) {
            System.out.println("And here as wellllllllllllllllllll");
            counter.stop(); // this is what stops the timer. Oh and by the way i hope the button is big enough for you xD
           time = 0; 
        }
    }
    
    public static String[] getArgs() {
        return gArgs;
    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();
        gArgs = args;
        game.start();
    }

    // GETTERS AND SETTERS
    /**
     * @description gets key presses of user
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * @description gets current game state
     */
    public State getGameState() {
        return gameState;
    }

    public int tickCounter() {
        return tickCount;
    }
    
    public int getTime(){
        return time;    
    }
    
    public void setTime(int newTime){
        time = newTime; 
    }
    
}
