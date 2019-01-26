/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * 
 */
public class WinScreen extends Application {
    
    static String[] gArgs;
    
    private static final Font FONT = Font.font("", FontWeight.BOLD, 18);
    private static final Font FONT2 = Font.font("", FontWeight.BOLD, 25);

    private VBox menuBox;
    private int currentItem = 0;

    private int messages = 0;
    
    int length = 256 * 2;
    int height = 224 * 2;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(length, height);

        Rectangle bg = new Rectangle(length, height);

        ContentFrame frame = new ContentFrame(createTitleContent());

        HBox hbox = new HBox(15, frame);
        hbox.setTranslateX(120);
        hbox.setTranslateY(50);

        menuBox = new VBox(10,
                new MenuItem("MAIN MENU"));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(length/3);
        menuBox.setTranslateY(height/3);

        getMenuItem(0).setActive(true);

        root.getChildren().addAll(bg, hbox, menuBox);
        return root;
    }

    private Node createTitleContent() {
        String title = "THE FIGHT OF TIME";
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);
        letters.setTranslateX(58);
        letters.setTranslateY(0);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT2);
            letter.setFill(Color.WHITE);
            letters.getChildren().add(letter);
        }

        return letters;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index);
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) {
            setAlignment(Pos.CENTER); 
            getChildren().addAll(content);
        }
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
            text.setFill(b ? Color.WHITE : Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }
    
    private static class TriCircle extends Parent {
        public TriCircle() {
            Shape shape1 = Shape.subtract(new Circle(5), new Circle(2));
            shape1.setFill(Color.RED);

            Shape shape2 = Shape.subtract(new Circle(5), new Circle(2));
            shape2.setFill(Color.BLUE);
            shape2.setTranslateX(5);

            Shape shape3 = Shape.subtract(new Circle(5), new Circle(2));
            shape3.setFill(Color.GREEN);
            shape3.setTranslateX(2.5);
            shape3.setTranslateY(-5);

            getChildren().addAll(shape1, shape2, shape3);

            setEffect(new GaussianBlur(2));
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                
                if (currentItem == 0) {
                    System.out.println("Back to main menu");
                    GUI.main(gArgs);
                    primaryStage.close();
                }
                getMenuItem(currentItem).activate();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });
        primaryStage.show();
    }
    public static void main(String[] args) {
        gArgs = args;
        launch(args);
    }
}
