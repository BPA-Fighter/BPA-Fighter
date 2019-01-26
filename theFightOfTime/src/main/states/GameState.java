
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

public class GameState extends State {

    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);

    private VBox menuBox;

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

        // render instances
        iceGuy.render(g);
        mafia.render(g);

        g.setColor(Color.BLACK);
        // end game
        if (mafia.getHealth() <= 0) {
            //increases the amount of round wins for IceGuy
            iceGuyRoundWins += 1;
            if (iceGuyRoundWins == 2) {
            // win screen
            String iceGuyWin = "ICEGUY WINS THE GAME!";
            g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);            g.setColor(Color.WHITE);
            int width = g.getFontMetrics().stringWidth(iceGuyWin);
            g.drawString(iceGuyWin, Game.WIDTH - width / 2, Game.HEIGHT);
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
            iceGuy.render(g);
            mafia.render(g);
            game.start();
            } catch (Exception e) {
                
            }
        } else if (iceGuy.getHealth() <= 0) {
            //increases the amount of round wins for Mafia
            mafiaRoundWins += 1;
            if (mafiaRoundWins == 2) {
            // win screen
            
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
            iceGuy.render(g);
            mafia.render(g);
            game.start();
            } catch (Exception e) {
                
            }
        }
        
        // when the clock hits 0
        if(game.getTime() <= 0){
        if (mafia.getHealth() < iceGuy.getHealth()) {
            //increases the amount of round wins for IceGuy
            iceGuyRoundWins += 1;
            if (iceGuyRoundWins == 2) {
            // win screen
            String iceGuyWin = "ICEGUY WINS THE GAME!";
            g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);
            g.setColor(Color.WHITE);
            int width = g.getFontMetrics().stringWidth(iceGuyWin);
            g.drawString(iceGuyWin, Game.WIDTH - width / 2, Game.HEIGHT);
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
            iceGuy.render(g);
            mafia.render(g);
            game.start();
            } catch (Exception e) {
                
            }
        } else if (iceGuy.getHealth() < mafia.getHealth()) {
            //increases the amount of round wins for Mafia
            mafiaRoundWins += 1;
            if (mafiaRoundWins == 2) {
            // win screen
            String mafiaWin = "MAFIA WINS THE GAME!";
            g.fillRect(0, 0, Game.WIDTH * 2, Game.HEIGHT * 2);
            g.setColor(Color.WHITE);
            int width = g.getFontMetrics().stringWidth(mafiaWin);
            g.drawString(mafiaWin, Game.WIDTH - width / 2, Game.HEIGHT);
            menuBox = new VBox(10,
                    new MenuItem("Main Menu"));
            menuBox.setAlignment(Pos.TOP_CENTER);
            menuBox.setTranslateX(Game.WIDTH / 3);
            menuBox.setTranslateY(Game.HEIGHT / 3);
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
            iceGuy.render(g);
            mafia.render(g);
            game.start();
            } catch (Exception e) {
                
            }
        }
        
        }
    }

    @Override
    public void music() {
        // empty for now because Java 8 API issues on school computers
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

    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }

    private static class MenuItem extends HBox {

        private TriCircle c1 = new TriCircle(), c2 = new TriCircle();
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(c1, text, c2);
            setActive(false);
            setOnActivate(() -> System.out.println(name + " activated"));
        }

        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? javafx.scene.paint.Color.WHITE : javafx.scene.paint.Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null) {
                script.run();
            }
        }
    }

    private static class TriCircle extends Parent {

        public TriCircle() {
            Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
            shape1.setFill(javafx.scene.paint.Color.RED);

            Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
            shape2.setFill(javafx.scene.paint.Color.BLUE);
            shape2.setTranslateX(5);

            Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
            shape3.setFill(javafx.scene.paint.Color.GREEN);
            shape3.setTranslateX(2.5);
            shape3.setTranslateY(-5);

            getChildren().addAll(shape1, shape2, shape3);

            setEffect(new GaussianBlur(2));
        }
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

}
