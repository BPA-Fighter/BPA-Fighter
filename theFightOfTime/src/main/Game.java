/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;
//TO DO LIST PELEASE ADD WHEN YOU HOP OFF
// Johsua: 
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
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gfx.Assets;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Timer;
import main.states.GameState;
import main.states.State;
import managers.KeyManager;

@SuppressWarnings({ "unused", "serial" })
public class Game extends Canvas implements Runnable {
	
    
    
	// declare constants
	public static final String TITLE  = "The Fight Of Time";
	public static final int    WIDTH  = 256;
	public static final int    HEIGHT = 224;
	public static final int    SCALE  = 2;
	
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
	
	// init scanner,and random
	private Scanner sc;
	private Random rand; 
	
	// int map
	private int map = 0;
        int time; 
	
	public Game() {
	
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
		
	/**
	 * @see ex_1.png in project dir for explanation of game updating
	 * @see ex_2.png [...] 							of game state managing
	 */

	public synchronized void start() throws IOException {
		
		// the program is running...
		running = true;		
		
		// pre-load assets                
		Assets.init();
			
		// states
		gameState = new GameState(this);
		State.setState(gameState);
		
		// print map selection
		System.out.println("===========================");		
		System.out.println("WELCOME! MAP SELECTION...");		
		System.out.println("1. Forest\n2. Main\n3. IceGuy");		
		System.out.println("===========================");		

		// instantiate scanner, and randomizer and init vars
		sc = new Scanner(System.in);	
		rand = new Random();
		int choice = 0;
		
		// ask user for input
		System.out.println("\nWould you like to select your map or choose a random map?");
		
		// while user doesn't make valid choice, keep asking for choice between map/random
		do {
			try {
				System.out.println("Please enter 1 (to select) or 2 (for random).");
				choice = sc.nextInt();
			} catch (Exception e) {
				System.err.println("That is not an integer, warrior.");
				sc = new Scanner(System.in);
			}
		} while (choice < 1 || choice > 2);
		
		// if choice is 1, then choose map
		if(choice == 1) {
			System.out.println("What map would you like?");
			do {
				try {
					// output to user
					System.out.println("Please enter an integer from 1-3.");
					// store var in map
					map = sc.nextInt();
									
				} catch(Exception e) {
					System.err.println("That is not an integer, warrior.");
					sc = new Scanner(System.in);
				}
				
				System.out.println(map);
				
			} while (map < 1 || map > 3);
		// if choice is 2, then randomly select a map
		} else if (choice == 2) {
			map = rand.nextInt(3) + 1;
		}
		
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
		ImageIcon temp1Stage    = new ImageIcon("/images/temp1.png");
		ImageIcon temp2Stage   = new ImageIcon("/images/temp2.png");
		ImageIcon temp3Stage = new ImageIcon("theFightOfTime/images/temp3.png");
		
		
		// decision making: if player chooses a map (as def'd in start() method), draw that map 
		if (map == 1) {
                    System.out.println("Stage 1");
			g.drawImage(temp1Stage.getImage(), -900, -220, temp1Stage.getIconWidth() * 2, temp1Stage.getIconHeight() * 2,null);
                       
                }
		if (map == 2) {
                    System.out.println("Stage 2");
			g.drawImage(temp2Stage.getImage(), -67, -67, null);
                }
		if (map == 3) {
                    System.out.println("Stage 3");
			g.drawImage(temp3Stage.getImage(), -212, 30, temp3Stage.getIconWidth() * Game.SCALE, temp3Stage.getIconHeight() * Game.SCALE,null);
                }
		
		// if current state exist, then render		
		if (State.getState() != null) {		
			tickCount++;
			State.getState().render(g);	
		}
					
		/* ALL DRAWING HERE */
		
		g.dispose();
		bs.show();
	}	
	
	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.start();
	}
	
        
        // set up a timer to call this from regularly
        public static void fileWritte() {
            String fileName = "outputFile.txt"; // File you want to write to (will overwrite file)
try {
    // During testing inside NetBeans, the output txt file will save in the build Folder inside the
    // Project Folder inside the BPA ID Folder.  When testing the .jar, it will save in the location
    // of the .jar file
File jarFile = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
fileName = jarFile.getParent() + File.separator + fileName;  // File.separator is the same as a "/"

    FileWriter fileWriter = new FileWriter(fileName);
    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); // Wraps FileWriter to write strings
    // Write whatever you need here...but newlines are not automatic
    bufferedWriter.write("Hello");
    bufferedWriter.write(" World.");
    bufferedWriter.newLine(); // write() does not automatically add newline
    bufferedWriter.write("This is writing");
    bufferedWriter.write(" text to the file.");
    bufferedWriter.close();  // Always close files!!
    fileWriter.close();
} catch (IOException ex) {
    System.out.println("Error writing to file '" + fileName + "'");
} catch (Exception ex) {
    System.err.println(ex);
}
// Make sure to use the above catch statements!

        }
        
	// GETTERS AND SETTERS
	
	/**
	 * @description 
	 * 	   gets key presses of user
	*/
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	/**
	 * @description 
	 * 	   gets current game state
	*/
	public State getGameState() {
		return gameState;
	}
}
