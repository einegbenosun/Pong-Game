package pong.assignment.javafxproject.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pong.assignment.javafxproject.controller.GameController;
import pong.assignment.javafxproject.model.PingPongBall;
import pong.assignment.javafxproject.model.PingPongPlayer;
import pong.assignment.javafxproject.model.PingPongRacket;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

/**
 * The main class responsible for setting up and running the Ping Pong game.
 */
public class PingPongGame extends Application {
    private GameController gameController;
    public static final int DEFAULT_WIDTH = 600;
    public static final int DEFAULT_HEIGHT = 400;

    private Text gameOverText;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ping Pong");
        Group root = new Group();
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.BLACK);

        // Initialize game components
        PingPongBall ball = new PingPongBall(Color.WHITE);
        PingPongRacket racket1 = new PingPongRacket(Color.BLUE, 10, 150);
        PingPongRacket racket2 = new PingPongRacket(Color.RED, DEFAULT_WIDTH - 20, 150);
        PingPongPlayer player1 = new PingPongPlayer("Player1");
        PingPongPlayer player2 = new PingPongPlayer("Player2");

        // Set up UI elements
        Text player1NameText = new Text(player1.getName());
        player1NameText.setFont(new Font(20));
        player1NameText.setFill(Color.WHITE);
        player1NameText.setX(50);
        player1NameText.setY(50);

        Text player2NameText = new Text(player2.getName());
        player2NameText.setFont(new Font(20));
        player2NameText.setFill(Color.WHITE);
        player2NameText.setX(DEFAULT_WIDTH - 150);
        player2NameText.setY(50);

        Text pauseText = new Text("Press SPACE to start");
        pauseText.setFont(new Font(20));
        pauseText.setFill(Color.WHITE);
        pauseText.setVisible(false);
        pauseText.setX((DEFAULT_WIDTH - pauseText.getBoundsInLocal().getWidth()) / 2);
        pauseText.setY(DEFAULT_HEIGHT / 2);

        gameOverText = new Text("");
        gameOverText.setFont(new Font(30));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setVisible(false);
        gameOverText.setX((DEFAULT_WIDTH - gameOverText.getBoundsInLocal().getWidth()) / 2);
        gameOverText.setY(100);

        // Create game controller
        gameController = new GameController(ball, racket1, racket2, player1, player2, pauseText, gameOverText);

        Text score1Text = new Text("0");
        score1Text.setFont(new Font(20));
        score1Text.setFill(Color.WHITE);
        score1Text.setX(250);
        score1Text.setY(50);

        Text score2Text = new Text("0");
        score2Text.setFont(new Font(20));
        score2Text.setFill(Color.WHITE);
        score2Text.setX(350);
        score2Text.setY(50);

        root.getChildren().addAll(gameOverText, pauseText, racket1.getNode(), racket2.getNode(), ball.getNode(), score1Text, score2Text);
        root.getChildren().addAll(player1NameText, player2NameText);

        // Setup game menu
        MenuBar menuBar = new MenuBar();
        setupGameMenu(menuBar);
        root.getChildren().add(menuBar);

        // Handle key events
        scene.setOnKeyPressed(gameController::handleKeyPress);

        // Handle window resizing
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            double ratioX = newWidth.doubleValue() / DEFAULT_WIDTH;
            ball.adjustPosition(ratioX, 1);
            racket2.adjustPosition(ratioX, 1);
        });

        // Animation Timer to update game state and redraw the ball
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameController.isGamePaused()) {
                    ball.updateBall();
                    gameController.updateGame();
                    score1Text.setText(String.valueOf(gameController.getPlayer1Score()));
                    score2Text.setText(String.valueOf(gameController.getPlayer2Score()));
                }
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Set up the game menu with "Exit" and "Restart Game".
     *
     * @param menuBar The MenuBar to which the game menu will be added.
     */
    private void setupGameMenu(MenuBar menuBar) {
        Menu gameMenu = new Menu("Game");
        MenuItem exitItem = new MenuItem("Exit");
        MenuItem restartItem = new MenuItem("Restart Game");
        restartItem.setOnAction(event -> {
            gameController.restartGame();
        });
        exitItem.setOnAction(event -> System.exit(0));
        gameMenu.getItems().addAll(exitItem, restartItem);
        menuBar.getMenus().add(gameMenu);
    }

    /**
     * The main entry point for the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
