package com.example.fx1;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.*;
import java.util.List;

public class SplitScreen {
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    double tankSpeed = 4.0;
    final Direction[] leftTankDirection = {Direction.UP};
    final Direction[] rightTankDirection = {Direction.UP};
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private static Pane root;
    private static List<Bullet> bullets = new ArrayList<>();
    private static List<ImageView> breakableWalls = new ArrayList<>();
    private static List<ImageView> unbreakableWalls = new ArrayList<>();
    static Image tankUpImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankUp.png")));
    static Image tankUpImage2 = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Top.png")));
    static ImageView leftTank = new ImageView(tankUpImage2);
    static ImageView rightTank = new ImageView(tankUpImage);
    private long lastShootTimeLeftTank = 0;
    private long lastShootTimeRightTank = 0;
    private final long SHOOT_COOLDOWN = 500_000_000;
    Sound backgroundMusic = new Sound();
    Sound powerupmusic = new Sound();
    ProgressBar progressBarA = new ProgressBar(1.0);
    ProgressBar progressBarB = new ProgressBar(1.0);
    Stage stage;
    Stage stage3 = new Stage();
    Stage stage1 = new Stage();
    static int lefttankhits;
    static   int righttankhits;
    static int lefttankpowerup;
    static int righttankpowerup;
//    Timeline timeline = new Timeline();
    HBox centered;
    Stage leftStage;
    Stage rightStage;

    SplitScreen (){}
    SplitScreen (int lefttankhits, int righttankhits, int lefttankpowerup, int righttankpowerup,ProgressBar progressBarA,ProgressBar progressBarB){
        this.lefttankhits = lefttankhits;
        this.righttankhits = righttankhits;
        this.lefttankpowerup = lefttankpowerup;
        this.righttankpowerup = righttankpowerup;
        this.progressBarA = progressBarA;
        this.progressBarB = progressBarB;}
    double defaultWidth;
    double defaultHeight;
    public  void playScreen1(Stage stage2) {
//        lefttankhits=0;
//        righttankhits=0;
        stage = stage2;
        backgroundMusic.setFile(0);
        backgroundMusic.loop();
        root = new Pane();
        BorderPane borderPane = new BorderPane();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getBounds();
        defaultWidth = (bounds.getWidth())/2;
        defaultHeight = bounds.getHeight();
//        progressBarA.setMinHeight(15);
//        progressBarB.setMinHeight(15);
//        progressBarA.setMaxWidth(50);
//        progressBarB.setMaxWidth(50);
        progressBarA.setMinHeight(30);
        progressBarB.setMinHeight(30);
        Button map = new Button("Map \uD83D\uDDFA\uFE0F");
        map.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 15px; -fx-font-weight: bold; -fx-pref-width: 130px; -fx-pref-height: 30px; -fx-border-color: #36454F;");

        Button help = new Button("Help");
        help.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 15px; -fx-font-weight: bold; -fx-pref-width: 130px; -fx-pref-height: 30px; -fx-border-color: #36454F;");

        Button split = new Button("UnSplit Screen");
        split.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 15px; -fx-font-weight: bold; -fx-pref-width: 130px; -fx-pref-height: 30px; -fx-border-color: #36454F;");

        map.setOnAction(event -> {
            Pane mappane = new Pane();
            Scene scene1 = new Scene(mappane, 800, 420);
            Image background = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("img_2.png")));
            BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
            mappane.setBackground(new Background(backgroundImage));
            stage1 = new Stage();
            stage1.setScene(scene1);
            stage1.setTitle("Map");
            stage1.show();
        });
        help.setOnAction(event -> {
            Pane helppane = new Pane();
            Scene scene3 = new Scene(helppane, 550, 520);
            BackgroundFill backgroundFill = new BackgroundFill(Color.web("#0f2027"), CornerRadii.EMPTY, null);
            Background background = new Background(backgroundFill);
            helppane.setBackground(background);
            Text titleText = new Text("Help");
            titleText.setStyle("-fx-fill: #ffdd57;");
            titleText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
            Text helptext1 = new Text("\nRight Tank: (B)\n" +
                    "Shift+UP Arrow Key: To Move Up\n" +
                    "Shift+Down Arrow Key: To Move Down\n" +
                    "Shift+Left Arrow Key: To Move Left\n" +
                    "Shift+Right Arrow Key: To Move Right");
            helptext1.setStyle("-fx-fill: #ffdd57;");
            helptext1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            Text helptext2 = new Text("Left Tank: (A)\n" +
                    "W : To Move Up\n" +
                    "S : To Move Down\n" +
                    "A : To Move Left\n" +
                    "D : To Move Right");
            helptext2.setStyle("-fx-fill: #ffdd57;");
            helptext2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            Text helptext3 = new Text("For Shooting\n" +
                    "Tank A: (Press-1)\n" +
                    "Tank B: (Press-0)");
            helptext3.setStyle("-fx-fill: #ffdd57;");
            helptext3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            Text helptext4 = new Text("Press A(⚡) Button for Player A Power Ups(if available)\n" +
                    "Press B(⚡) Button for Player B Power Ups(if available)");
            helptext4.setStyle("-fx-fill: #ffdd57;");
            helptext4.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            VBox helpvbox = new VBox(15);
            helpvbox.getChildren().addAll(helptext1, helptext2, helptext3, helptext4);
            helpvbox.setAlignment(Pos.CENTER);  // Center the VBox contents
            BorderPane borderPane1 = new BorderPane();
            borderPane1.setTop(titleText);
            borderPane1.setCenter(helpvbox);
            BorderPane.setMargin(titleText, new Insets(20, 20, 10, 20));
            BorderPane.setMargin(helpvbox, new Insets(10, 20, 20, 20));
            borderPane1.setPrefSize(500, 520);
            titleText.setWrappingWidth(500);
            helpvbox.setPrefWidth(500);
            helppane.getChildren().add(borderPane1);
            stage3.setScene(scene3);
            stage3.setTitle("Help");
            stage3.show();});
        Button player1 = new Button(" A(⚡) "+lefttankpowerup);
        Button player2 = new Button(" B(⚡) "+righttankpowerup);
        powerupmusic.setFile(7);
        player1.setOnAction(event -> {
            if(lefttankpowerup>0){
                lefttankpowerup--;
                progressBarA.setProgress(1.0);
                lefttankhits=0;
                player1.setText(" A(⚡) " + lefttankpowerup);
                powerupmusic.play();
            }});
        player2.setOnAction(event -> {
            if(righttankpowerup>0){
                righttankpowerup--;
                progressBarB.setProgress(1.0);
                righttankhits=0;
                player2.setText(" B(⚡) " + righttankpowerup);
                powerupmusic.play();}});
//        Text text = new Text("WALL BREAK");
//        text.setFont(new Font(10));
//        text.setFill(Color.web("#ffdd57"));
//        text.setX(defaultWidth);
//        text.setY(0);
//        KeyValue moveKeyValue = new KeyValue(text.yProperty(), 230);
//        KeyFrame moveKeyFrame = new KeyFrame(Duration.seconds(3), moveKeyValue);
//        KeyValue opacityKeyValue = new KeyValue(text.opacityProperty(), 0); // Set opacity to 0
//        KeyFrame opacityKeyFrame = new KeyFrame(Duration.seconds(3), opacityKeyValue);
//        timeline.getKeyFrames().addAll(moveKeyFrame, opacityKeyFrame);
//        root.getChildren().add(text);
        Button unmute=new Button("\uD83D\uDD0A");
        unmute.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: #36454F;");
        Button mute=new Button("\uD83D\uDD07");
        mute.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: #36454F;");

       mute.setOnAction(event -> {
            backgroundMusic.stop();
        });
        unmute.setOnAction(event -> {
            backgroundMusic.loop();
        });
        player1.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: #36454F;");
        player2.setStyle("-fx-background-color: #0f2027; -fx-text-fill: #ffdd57; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: #36454F;");

        HBox box1=new HBox(player1,progressBarA);
        box1.setSpacing(20);
        box1.setAlignment(Pos.TOP_LEFT);
        HBox btnHBox = new HBox(map, help,split, unmute, mute);
        btnHBox.setSpacing(45);
        btnHBox.setAlignment(Pos.TOP_CENTER);

        HBox box2=new HBox(player2,progressBarB);
        box2.setSpacing(20);
        box2.setAlignment(Pos.TOP_RIGHT);
        centered=new HBox(box1,btnHBox,box2);
        centered.setSpacing(105);

        borderPane.setTop(centered);
        borderPane.setId("borderid");
        split.setOnAction(event -> {
            backgroundMusic.stop();
            GamePanel gamePanel = new GamePanel(lefttankhits, righttankhits, lefttankpowerup,  righttankpowerup, progressBarA, progressBarB);
            leftStage.close();
            rightStage.close();
            gamePanel.playScreen(stage);
        });
        Image unbreakableVerticalWallImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("unbreakablewall.png")));
        Image unbreakableHorizontalWallImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("unbreakablewall.png")));
        Image breakableVerticalWallImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("img.png")));
        Image breakableHorizontalWallImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("img_1.png")));
        Image tankDownImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankBottom.png")));
        Image tankLeftImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankLeft.png")));
        Image tankRightImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankRight.png")));
        Image bulletImage = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("BulletPic.jpg")));
        Image tankDownImage2 = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Bottom.png")));
        Image tankLeftImage2 = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Left.png")));
        Image tankRightImage2 = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Right.png")));
        ImageView[] walls = {
                createWall(unbreakableHorizontalWallImage, defaultWidth, 30, 0, 0, false),
                createWall(unbreakableHorizontalWallImage, defaultWidth, 30, 0, defaultHeight-100, false),
                createWall(breakableHorizontalWallImage, 150, 30, 13.5, 260, true),
                createWall(unbreakableHorizontalWallImage, 150, 30, 475, 260, false),
                createWall(unbreakableHorizontalWallImage, 200, 30, 225, 540, false),
                createWall(breakableHorizontalWallImage, 110, 30, 115, 400, true),
                createWall(breakableHorizontalWallImage, 205, 30, 320, 400, true),
                createWall(unbreakableVerticalWallImage, 15, 900, 0, 0, false),
                createWall(unbreakableVerticalWallImage, 15, 900, defaultWidth-15, 0, false),
                createWall(unbreakableVerticalWallImage, 15, 300, 100, 400, false),
                createWall(unbreakableVerticalWallImage, 15, 300, 425, 400, false),
                createWall(unbreakableVerticalWallImage, 15, 350, 225, 200, false),
                createWall(breakableVerticalWallImage, 15, 200, 400, 80, true),
                createWall(unbreakableVerticalWallImage, 15, 150, 305, 280, false)
        };
        leftTank.setFitWidth(25);
        leftTank.setFitHeight(45);
        rightTank.setFitWidth(45);
        rightTank.setFitHeight(45);
        leftTank.setLayoutX(50);
        leftTank.setLayoutY(defaultHeight - 150);
        rightTank.setLayoutX(defaultWidth - 150);
        rightTank.setLayoutY(defaultHeight - 150);
//        root.getChildren().addAll(borderPane);
        Scene topscene = new Scene(borderPane, defaultWidth*2,30);
//        stage.setX(0);
//        stage.setY(0);
        stage.setScene(topscene);
        stage.setMaximized(true);
        stage.show();
        root.getChildren().addAll(walls);
        root.getChildren().addAll(leftTank, rightTank);
        Image background = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("/com/example/fx1/tile4.jpg")));
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        root.setBackground(new Background(backgroundImage));
        Scene scene1 = new Scene(root,defaultWidth , defaultHeight);
        scene1.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene1.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(defaultWidth,defaultHeight);
            }
        };
        gameLoop.start();
        leftStage = new Stage();
        leftStage.setScene(scene1);
        leftStage.setTitle("A-Tank");
//     leftStage.setAlwaysOnTop(true);
        stage.setOnShowing(event -> {
           leftStage.requestFocus();
        });
        scene1.setOnMouseClicked(event -> leftStage.requestFocus());

        leftStage.setX(0);
        leftStage.setY(70);
       leftStage.initModality(Modality.APPLICATION_MODAL);
       leftStage.initOwner(stage);
        leftStage.initModality(Modality.NONE);
        createMirrorStage(leftStage, root);
        stage.setOnCloseRequest(event -> {
            if (leftStage != null) {
               leftStage.close();
            }
            if (rightStage!= null) {
                rightStage.close();
            }
            if (stage3!= null) {
               stage3.close();
            }

            if (stage1!= null) {
                stage1.close();
            }

        });



        stage1.setOnCloseRequest(event -> {

            if (stage3!= null) {
                stage3.close();
            }


        });

        stage3.setOnCloseRequest(event -> {

            if (stage1!= null) {
                stage1.close();
            }


        });






       leftStage.initStyle(StageStyle.UNDECORATED);

        leftStage.show();
//        rightStage.show();





        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Bullet bullet : new ArrayList<>(bullets)) {
                    bullet.move();
                    checkBulletCollision(bullet);
                }
            }
        };
        timer.start();


    }



    public void createMirrorStage(Stage primaryStage, Pane primaryRoot) {
         rightStage = new Stage();
        Pane secondaryRoot = new Pane();
        ImageView mirrorView = new ImageView();
        secondaryRoot.getChildren().add(mirrorView);
        Scene secondaryScene = new Scene(secondaryRoot, defaultWidth, defaultHeight);
      rightStage.initModality(Modality.APPLICATION_MODAL);
        rightStage.initOwner(stage);

        rightStage.setScene(secondaryScene);
//        rightStage.showAndWait();
        rightStage.setTitle("B-Tank");
     rightStage.initStyle(StageStyle.UNDECORATED);
        rightStage.initModality(Modality.NONE);
        secondaryScene.setOnMouseClicked(event -> primaryStage.requestFocus());

       rightStage.setX(defaultWidth);
        rightStage.setY(70);
       rightStage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            primaryRoot.getChildren().forEach(node -> {
                if ("borderid".equals(node.getId())) {
                    node.setVisible(false);
                }
            });

            // Take snapshot
            WritableImage snapshot = primaryRoot.snapshot(new SnapshotParameters(), null);

            // Restore visibility of excluded elements
            primaryRoot.getChildren().forEach(node -> {
                if ("borderid".equals(node.getId())) {
                    node.setVisible(true);
                }
            });

            // Set snapshot to ImageView
            mirrorView.setImage(snapshot);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }





    private void update(double defaultWidth, double defaultHeight) {
        long now = System.nanoTime();

        if (pressedKeys.contains(KeyCode.W)) {
            leftTankDirection[0] = updateDirection(leftTankDirection[0], KeyCode.W);
            moveTank(leftTank, leftTankDirection[0], tankSpeed, defaultWidth, defaultHeight, rightTank);
        }
        if (pressedKeys.contains(KeyCode.S)) {
            leftTankDirection[0] = updateDirection(leftTankDirection[0], KeyCode.S);
            moveTank(leftTank, leftTankDirection[0], tankSpeed, defaultWidth, defaultHeight, rightTank);
        }
        if (pressedKeys.contains(KeyCode.A)) {
            leftTankDirection[0] = updateDirection(leftTankDirection[0], KeyCode.A);
            moveTank(leftTank, leftTankDirection[0], tankSpeed, defaultWidth, defaultHeight, rightTank);
        }
        if (pressedKeys.contains(KeyCode.D)) {
            leftTankDirection[0] = updateDirection(leftTankDirection[0], KeyCode.D);
            moveTank(leftTank, leftTankDirection[0], tankSpeed, defaultWidth, defaultHeight, rightTank);
        }

        if (pressedKeys.contains(KeyCode.UP)) {
            rightTankDirection[0] = updateDirection(rightTankDirection[0], KeyCode.UP);
            moveTank(rightTank, rightTankDirection[0], tankSpeed, defaultWidth, defaultHeight, leftTank);
        }
        if (pressedKeys.contains(KeyCode.DOWN)) {
            rightTankDirection[0] = updateDirection(rightTankDirection[0], KeyCode.DOWN);
            moveTank(rightTank, rightTankDirection[0], tankSpeed, defaultWidth, defaultHeight, leftTank);
        }
        if (pressedKeys.contains(KeyCode.LEFT)) {
            rightTankDirection[0] = updateDirection(rightTankDirection[0], KeyCode.LEFT);
            moveTank(rightTank, rightTankDirection[0], tankSpeed, defaultWidth, defaultHeight, leftTank);
        }
        if (pressedKeys.contains(KeyCode.RIGHT)) {
            rightTankDirection[0] = updateDirection(rightTankDirection[0], KeyCode.RIGHT);
            moveTank(rightTank, rightTankDirection[0], tankSpeed, defaultWidth, defaultHeight, leftTank);
        }
        if (pressedKeys.contains(KeyCode.DIGIT1) && now - lastShootTimeLeftTank >= SHOOT_COOLDOWN) {
            shoot(leftTank, leftTankDirection[0], tankSpeed);
            lastShootTimeLeftTank = now;
        }
        if (pressedKeys.contains(KeyCode.DIGIT0) && now - lastShootTimeRightTank >= SHOOT_COOLDOWN) {
            shoot(rightTank, rightTankDirection[0], tankSpeed);
            lastShootTimeRightTank = now;
        }
    }
    private static ImageView createWall(Image image, double width, double height, double x, double y, boolean breakable) {
        ImageView wall = new ImageView(image);
        wall.setFitWidth(width);
        wall.setFitHeight(height);
        wall.setLayoutX(x);
        wall.setLayoutY(y);
        if (breakable) {
            breakableWalls.add(wall);
        } else {
            unbreakableWalls.add(wall);
        }
        return wall;
    }

    private static Direction updateDirection(Direction currentDirection, KeyCode code) {
        switch (code) {
            case W: case UP:
                return Direction.UP;
            case S: case DOWN:
                return Direction.DOWN;
            case A: case LEFT:
                return Direction.LEFT;
            case D: case RIGHT:
                return Direction.RIGHT;
            default:
                return currentDirection;
        }
    }
    private static void moveTank(ImageView tank, Direction direction, double tankSpeed, double defaultWidth, double defaultHeight, ImageView otherTank) {
        Direction collisionDirection = checkCollision(tank, direction, tankSpeed, otherTank);
        if (collisionDirection != null) {
            // If there's a collision, don't move the tank, just return
            return;
        }
        // No collision, proceed to move the tank
        double tankX = tank.getLayoutX();
        double tankY = tank.getLayoutY();

        if (tank == leftTank) {
            switch (direction) {
                case UP:
                    if (tankY - tankSpeed >= 0) {
                        tank.setLayoutY(tankY - tankSpeed);
                        leftTank.setFitWidth(25);
                        leftTank.setFitHeight(45);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Top.png"))));
                    }
                    break;
                case DOWN:
                    if (tankY + tankSpeed + tank.getFitHeight() <= defaultHeight) {
                        tank.setLayoutY(tankY + tankSpeed);
                        leftTank.setFitWidth(25);
                        leftTank.setFitHeight(45);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Bottom.png"))));
                    }
                    break;
                case LEFT:
                    if (tankX - tankSpeed >= 0) {
                        leftTank.setFitWidth(45);
                        leftTank.setFitHeight(25);
                        tank.setLayoutX(tankX - tankSpeed);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Left.png"))));
                    }
                    break;
                case RIGHT:
                    if (tankX + tankSpeed + tank.getFitWidth() <= defaultWidth) {
                        leftTank.setFitWidth(45);
                        leftTank.setFitHeight(25);
                        tank.setLayoutX(tankX + tankSpeed);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tank2Right.png"))));
                    }
                    break;
            }
        }

        if (tank == rightTank) {
            switch (direction) {
                case UP:
                    if (tankY - tankSpeed >= 0) {
                        tank.setLayoutY(tankY - tankSpeed);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankUp.png"))));
                    }
                    break;
                case DOWN:
                    if (tankY + tankSpeed + tank.getFitHeight() <= defaultHeight) {
                        tank.setLayoutY(tankY + tankSpeed);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankBottom.png"))));
                    }
                    break;
                case LEFT:
                    if (tankX - tankSpeed >= 0) {
                        tank.setLayoutX(tankX - tankSpeed);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankLeft.png"))));
                    }
                    break;
                case RIGHT:
                    if (tankX + tankSpeed + tank.getFitWidth() <= defaultWidth) {
                        tank.setLayoutX(tankX + tankSpeed);
                        tank.setImage(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("tankRight.png"))));
                    }
                    break;
            }
        }
    }


    private static Direction checkCollision(ImageView tank, Direction direction, double tankSpeed, ImageView otherTank) {
        double nextX = tank.getLayoutX();
        double nextY = tank.getLayoutY();
        switch (direction) {
            case UP:
                nextY -= tankSpeed;
                break;
            case DOWN:
                nextY += tankSpeed;
                break;
            case LEFT:
                nextX -= tankSpeed;
                break;
            case RIGHT:
                nextX += tankSpeed;
                break;
        }

        // Create a Bounds object representing the tank's next position
        Bounds tankBounds = new BoundingBox(nextX, nextY, tank.getFitWidth(), tank.getFitHeight());

        // Check for collision with unbreakable walls
        for (ImageView wall : unbreakableWalls) {
            if (tankBounds.intersects(wall.getBoundsInParent())) {
                return direction; // Collision detected, return the same direction
            }
        }

        // Check for collision with breakable walls
        for (ImageView wall : breakableWalls) {
            if (tankBounds.intersects(wall.getBoundsInParent())) {
                return direction; // Collision detected, return the same direction
            }
        }// Check for collision with the other tank
        if (tankBounds.intersects(otherTank.getBoundsInParent())) {
            return direction; // Collision detected, return the same direction
        }

        return null; // No collision, return null to indicate free movement
    }

    private static void shoot(ImageView tank, Direction direction, double tankSpeed) {
        double bulletSpeed = tankSpeed * 5;
        Bullet bullet = new Bullet(tank, direction, tankSpeed);
        bullets.add(bullet);
        root.getChildren().add(bullet.getImageView());
        Sound backgroundMusic = new Sound();
        backgroundMusic.setFile(1);
        backgroundMusic.play();
    }

    private void checkBulletCollision(Bullet bullet) {
        for (ImageView wall : unbreakableWalls) {
            if (bullet.getImageView().getBoundsInParent().intersects(wall.getBoundsInParent())) {
                root.getChildren().remove(bullet.getImageView());
                bullets.remove(bullet);
                Sound backgroundMusic = new Sound();
                backgroundMusic.setFile(5); // Unbreakable wall collision sound
                backgroundMusic.play();
                return;
            }
        }

        List<ImageView> wallsToRemove = new ArrayList<>();
        for (ImageView wall : breakableWalls) {
            if (bullet.getImageView().getBoundsInParent().intersects(wall.getBoundsInParent())) {
                wallsToRemove.add(wall);
            }
        }
        for (ImageView wall : wallsToRemove) {
            root.getChildren().remove(wall);
            breakableWalls.remove(wall);
        }
        if (!wallsToRemove.isEmpty()) {
//            timeline.play();
            root.getChildren().remove(bullet.getImageView());
            bullets.remove(bullet);
            Sound backgroundMusic = new Sound();
            backgroundMusic.setFile(2); // Breakable wall collision sound
            backgroundMusic.play();
            return;
        }
        if (bullet.getImageView().getBoundsInParent().intersects(leftTank.getBoundsInParent())) {
            root.getChildren().remove(bullet.getImageView());
            bullets.remove(bullet);
            handleTankHit(leftTank, progressBarA);
        } else if (bullet.getImageView().getBoundsInParent().intersects(rightTank.getBoundsInParent())) {
            root.getChildren().remove(bullet.getImageView());
            bullets.remove(bullet);
            handleTankHit(rightTank, progressBarB);
        }
    }
    private  void handleTankHit(ImageView tank, ProgressBar progressBar) {
        Sound backgroundMusic1 = new Sound();
        backgroundMusic1.setFile(3);
        backgroundMusic1.play();
        showOverlayImage(tank);
        if (tank == leftTank) {
            if (++lefttankhits < 5) {
                double progress = Math.abs((double) (5 - lefttankhits) / 5);
                Platform.runLater(() -> {
                    try {
                        progressBarA.setProgress(progress);
                        progressBarA.layout();

                    } catch (Exception e) {
                        System.err.println("Exception while updating Progress Bar A: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }
        if (tank == rightTank) {
            if (++righttankhits < 5) {

                double progress1 = Math.abs((double) (5 - righttankhits) / 5);
                Platform.runLater(() -> {
                    try {
                        progressBarB.setProgress(progress1);
                        progressBarB.layout();

                    } catch (Exception e) {
                        System.err.println("Exception while updating Progress Bar B: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }
        if(lefttankhits>=5){
            GameEnd gameEnd = new GameEnd();
            backgroundMusic1.stop();
            backgroundMusic.stop();

            lefttankhits=0;
            righttankhits=0;
            righttankpowerup++;
            leftStage.close();
            rightStage.close();
            gameEnd.gameEnd(stage, "B",lefttankpowerup,righttankpowerup);
        }
        if(righttankhits>=5){
            GameEnd gameEnd = new GameEnd();
            backgroundMusic.stop();
            backgroundMusic1.stop();
            righttankhits=0;
            lefttankhits=0;
            lefttankpowerup++;
            leftStage.close();
            rightStage.close();
            gameEnd.gameEnd(stage, "A",lefttankpowerup,righttankpowerup);
        }}
    private static void showOverlayImage(ImageView tank) {
        ImageView overlay = new ImageView(new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("bomb_2.gif")))); // Replace "overlay.png" with your image
        overlay.setFitWidth(tank.getFitWidth());
        overlay.setFitHeight(tank.getFitHeight());
        overlay.setLayoutX(tank.getLayoutX());
        overlay.setLayoutY(tank.getLayoutY());
        root.getChildren().add(overlay);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> root.getChildren().remove(overlay));
        pause.play();
    }
    private static class Bullet {
        private final ImageView imageView;
        private final Direction direction;
        private final double speed=10.0;

        Bullet(ImageView tank, Direction direction, double speed) {
            Image image = new Image(Objects.requireNonNull(GamePanel.class.getResourceAsStream("BulletPic.jpg")));
            this.imageView = new ImageView(image);
            this.imageView.setFitWidth(20);
            this.imageView.setFitHeight(20);
            this.direction = direction;
//            this.speed = 10.0;
            switch (direction) {
                case UP:
                    this.imageView.setLayoutX(tank.getLayoutX() + tank.getFitWidth() / 2 - this.imageView.getFitWidth() / 2);
                    this.imageView.setLayoutY(tank.getLayoutY() - this.imageView.getFitHeight());
                    break;
                case DOWN:
                    this.imageView.setLayoutX(tank.getLayoutX() + tank.getFitWidth() / 2 - this.imageView.getFitWidth() / 2);
                    this.imageView.setLayoutY(tank.getLayoutY() + tank.getFitHeight());
                    break;
                case LEFT:
                    this.imageView.setLayoutX(tank.getLayoutX() - this.imageView.getFitWidth());
                    this.imageView.setLayoutY(tank.getLayoutY() + tank.getFitHeight() / 2 - this.imageView.getFitHeight() / 2);
                    break;
                case RIGHT:
                    this.imageView.setLayoutX(tank.getLayoutX() + tank.getFitWidth());
                    this.imageView.setLayoutY(tank.getLayoutY() + tank.getFitHeight() / 2 - this.imageView.getFitHeight() / 2);
                    break;
            }
        }
        public ImageView getImageView() {
            return imageView;
        }
        public void move() {
            switch (direction) {
                case UP:
                    imageView.setLayoutY(imageView.getLayoutY() - speed);
                    break;
                case DOWN:
                    imageView.setLayoutY(imageView.getLayoutY() + speed);
                    break;
                case LEFT:
                    imageView.setLayoutX(imageView.getLayoutX() - speed);
                    break;
                case RIGHT:
                    imageView.setLayoutX(imageView.getLayoutX() + speed);
                    break;
            }
        }
    }}


