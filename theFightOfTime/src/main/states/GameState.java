
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import entities.IceGuy;
import entities.Mafia;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.GUI;
import main.Game;
import main.WinScreen;

public class GameState extends State {

    String winner = "";
    
    // init iceGuy, mafia
    private IceGuy iceGuy;
    private Mafia mafia;
    public int iceGuyRoundWins = 0;
    public int mafiaRoundWins = 0;

    // constructor
    public GameState(Game game) {
        super(game);
        // create instance of iceGuy, mafia 
        iceGuy = new IceGuy(game, 60, 280);
        mafia = new Mafia(game, 224 * 2, 280);

    }

    @Override
    public void tick() {
        // update hitboxes, attack boxes
        iceGuy.getAttackBounds();
        iceGuy.getHitBounds();

        mafia.getAttackBounds();
        mafia.getHitBounds();

        // update entire instance
        iceGuy.tick();
        mafia.tick();

    }

    @Override
    public void render(Graphics g) {

           

        // print ui for iceGuy
        g.setColor(Color.yellow);
        double percentIceGuy = iceGuy.getHealth() / 100.0;
        g.fillRect(61, 19, (int) (173 * percentIceGuy), 11);
        // print ui for mafia
        g.setColor(Color.yellow);
        double percentMafia = mafia.getHealth() / 100.0;
        g.fillRect(99 + 29 + 144, 19, (int) (173 * percentMafia), 11);
        
        //Magic
        

        
        // render instances
        iceGuy.render(g);
        mafia.render(g);

//        mafia.render(g
//        g.setColor(Color.BLACK);
        // end game
        if (mafia.getHealth() <= 0) {
            //increases the amount of round wins for IceGuy
            if (mafia.getHealth() <= 0) {
            iceGuyRoundWins += 1;
            } else if(game.getTime() <=0 && (mafia.getHealth() > iceGuy.getHealth())){
                iceGuyRoundWins ++; 
            }
            if (iceGuyRoundWins == 2) {
            // win screen
            winner = "iceGuy";
            WinScreen.main(winner);
            //game.setTime(90);
            }
            try {
            Game game = new Game();
            if (Game.isNormal) {
                Mafia.health = 100;
                IceGuy.health = 100;
            } else if (Game.isHard) {
                Mafia.health = 300;
                IceGuy.health = 300;
            } else {
                Mafia.health = 100;
                IceGuy.health = 100;
            }
            // print ui for iceGuy
            g.setColor(Color.yellow);
            percentIceGuy = iceGuy.getHealth() / 100.0;
            g.fillRect(61, 19, (int) (173 * percentIceGuy), 11);
            // print ui for mafia
            g.setColor(Color.yellow);
            percentMafia = mafia.getHealth() / 100.0;
            g.fillRect(99 + 29 + 144, 19, (int) (173 * percentMafia), 11);
            game.start();
            } catch (Exception e) {
                
            }
        } else if (iceGuy.getHealth() <= 0) {
            //increases the amount of round wins for Mafia
            if (iceGuy.getHealth() <= 0) {
            mafiaRoundWins += 1;
            } else if(game.getTime() <=0 && (iceGuy.getHealth() > mafia.getHealth())){
                mafiaRoundWins ++; 
            }
            if (mafiaRoundWins == 2) {
            // win screen
            winner = "mafia";
            WinScreen.main(winner);
            //game.setTime(90);
            }
            try {
            Game game = new Game();
            if (Game.isNormal) {
                IceGuy.health = 100;
                Mafia.health = 100;
            } else if (Game.isHard) {
                IceGuy.health = 300;
            } else {
                IceGuy.health = 100;
                Mafia.health = 100;
            }
            // print ui for iceGuy
            g.setColor(Color.yellow);
            percentIceGuy = iceGuy.getHealth() / 100.0;
            g.fillRect(61, 19, (int) (173 * percentIceGuy), 11);
            // print ui for mafia
            g.setColor(Color.yellow);
            percentMafia = mafia.getHealth() / 100.0;
            g.fillRect(99 + 29 + 144, 19, (int) (173 * percentMafia), 11);
            game.start();
            } catch (Exception e) {
                
            }
        
        }    
    }


    public  void fileWrite() {
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
            bufferedWriter.write("Ice guy;s health: " + iceGuy.getHealth());
            bufferedWriter.write("Mafia guys health: " + mafia.getHealth());
            bufferedWriter.write("Current tick: " + game.tickCounter());
            bufferedWriter.write("Round time: " + game.getTime()); // for when we have or timer or when I find it.
            //bufferedWriter.write("Total time: " + game.totalTimeRan());
            bufferedWriter.write("Ice guy's wins: "); // for when we have the win counter or for when I find it. 

            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file '" + fileName + "'");
        } catch (Exception ex) {
            System.err.println(ex);
        }
// Make sure to use the above catch statements!

    }

    // GETTERS AND SETTERS:
    public Rectangle getIceGuyHitBounds() {
        return iceGuy.getHitBounds();
    }

    public Rectangle getIceGuyAttackBounds() {
        return iceGuy.getAttackBounds();
    }

    public Rectangle getMafiaHitBounds() {
        return mafia.getHitBounds();
    }

    public Rectangle getMafiaAttackBounds() {
        return mafia.getAttackBounds();
    }

    public int getIceGuyX() {
        return iceGuy.getIceGuyX();
    }

    public int getMafiaX() {
        return mafia.getMafiaX();
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public void music() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
