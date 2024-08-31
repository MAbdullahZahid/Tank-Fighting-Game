
package com.example.fx1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class Home {

    public static void home(Stage stage) {
        Sound backgroundMusic = new Sound();
        backgroundMusic.setFile(0);
        backgroundMusic.loop();
        StackPane root = new StackPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(90, 20, 20, 20));

        Text title = new Text("Tank Game");
        title.setStyle("-fx-fill: #36454F;");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
        BorderPane.setAlignment(title, Pos.CENTER);
        borderPane.setTop(title);


        Button startButton = new Button("Start Game");
        String buttonStyle = "-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 25px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 40px;  -fx-border-color: #36454F;";
        startButton.setStyle(buttonStyle);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        borderPane.setCenter(startButton);

        startButton.setOnAction(e -> {

            GamePanel play = new GamePanel();
            backgroundMusic.stop();
           GamePanel play1 = new GamePanel();
           play1.playScreen(stage);

        });

        // Icon
        try {
            Image icon = new Image(Objects.requireNonNull(Home.class.getResourceAsStream("/com/example/fx1/homebg.jpg")));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Error loading icon: " + e.getMessage());
        }

        // Background Image
        Image image = new Image(Objects.requireNonNull(Home.class.getResourceAsStream("/com/example/fx1/homebg.jpg")));
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(backgroundImage));
        root.getChildren().add(borderPane);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        double defaultWidth = bounds.getWidth();
        double defaultHeight = bounds.getHeight();

        // Set scene dimensions
        Scene scene = new Scene(root, defaultWidth, defaultHeight);
        stage.setScene(scene);
        stage.setTitle("Tank Game");
        stage.show();
    }
}

