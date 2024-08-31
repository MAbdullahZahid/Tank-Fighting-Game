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

public class GameEnd {
    public void gameEnd(Stage stage,String win,int lefttankpowerup,int righttankpowerup){
        Sound backgroundMusic = new Sound();
        backgroundMusic.setFile(6); // Assuming Music.wav is the first sound

            backgroundMusic.loop1();


        StackPane root = new StackPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(100, 100, 100, 100));
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        String buttonStyle =   "-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 25px; -fx-font-weight: bold; -fx-pref-width: 200px; -fx-pref-height: 30px; -fx-border-color: #36454F";
        String buttonStyle1 =   "-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 25px; -fx-font-weight: bold; -fx-pref-width: 420px; -fx-pref-height: 50px; -fx-border-color: #36454F";


        Text title = new Text("Game Over");
        title.setStyle("-fx-fill: #36454F;");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
        BorderPane.setAlignment(title, Pos.CENTER);
        borderPane.setTop(title);

        Button Win = new Button("Player "+ win +" Win ⭐⭐⭐");
        Win.setStyle(buttonStyle1);


        Button newGame = new Button("Replay");
        Button close = new Button("Close ❌");

        newGame.setStyle(buttonStyle);
        close.setStyle(buttonStyle);
        hBox.getChildren().addAll(newGame, close);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(Win, hBox);

        borderPane.setCenter(vBox);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/fx1/homebg.jpg")));
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        borderPane.setBackground(new Background(backgroundImage));
        root.getChildren().add(borderPane);

        newGame.setOnAction(e -> {
            backgroundMusic.stop();
            GamePanel play = new GamePanel(lefttankpowerup,righttankpowerup);
            play.playScreen(stage);});
        close.setOnAction(e -> {
            stage.close();
        });

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        double defaultWidth = bounds.getWidth();
        double defaultHeight = bounds.getHeight();
        Scene scene = new Scene(root, defaultWidth, defaultHeight);
        stage.setScene(scene);
      stage.show();
    }
}


