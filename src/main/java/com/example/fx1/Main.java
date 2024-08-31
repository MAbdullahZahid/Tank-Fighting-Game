package com.example.fx1;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Home.home(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
