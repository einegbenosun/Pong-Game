package pong.assignment.javafxproject.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import pong.assignment.javafxproject.model.PingPongBall;
import pong.assignment.javafxproject.model.PingPongPlayer;
import pong.assignment.javafxproject.model.PingPongRacket;
import pong.assignment.javafxproject.view.PingPongGame;

/**
 * The controller class used to manage the game logic.
 */
public class GameController {
    private final PingPongBall ball; // The ball object
    private final PingPongRacket racket1; // Player 1's racket
    private final PingPongRacket racket2; // Player 2's racket
    private final PingPongPlayer player1; // Player 1
    private final PingPongPlayer player2; // Player 2

    private boolean gamePaused = true; // Flag to indicate if the game is paused
    private boolean gameEnded = false; // Flag to indicate if the game has ended
    private Text pauseText; // Text to display when the game is paused
    private Text gameOverText; // Text to display when the game ends

    /**
     * Initializes the game controller with necessary components.
     *
     * @param ball         The ball object.
     * @param racket1      Player 1's racket.
     * @param racket2      Player 2's racket.
     * @param player1      Player 1.
     * @param player2      Player 2.
     * @param pauseText    Text to display when the game is paused.
     * @param gameOverText Text to display when the game ends.
     */
    public GameController(PingPongBall ball, PingPongRacket racket1, PingPongRacket racket2, PingPongPlayer player1,
                          PingPongPlayer player2, Text pauseText, Text gameOverText) {
        this.ball = ball;
        this.racket1 = racket1;
        this.racket2 = racket2;
        this.player1 = player1;
        this.player2 = player2;

        this.pauseText = pauseText;
        this.gameOverText = gameOverText;
    }

    /**
     * Handles key presses during the game.
     *
     * @param event The KeyEvent object representing the key press event.
     */
    public void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        // Toggle game pause state with SPACE key
        if (keyCode == KeyCode.SPACE) {
            if (gameEnded) {
                restartGame();
            } else {
                gamePaused = !gamePaused;
                pauseText.setVisible(gamePaused);
            }
        } else if (!gamePaused && !gameEnded) {
            // Move rackets based on key presses
            switch (keyCode) {
                case W:
                    racket1.moveUp();
                    break;
                case S:
                    racket1.moveDown(PingPongGame.DEFAULT_HEIGHT);
                    break;
                case UP:
                    racket2.moveUp();
                    break;
                case DOWN:
                    racket2.moveDown(PingPongGame.DEFAULT_HEIGHT);
                    break;
            }
        }
    }

    /**
     * Updates the game state.
     */
    public void updateGame() {
        if (!gamePaused && !gameEnded) {
            // Handle ball collisions with rackets
            handleBallRacketCollisions();

            // Update scores and check for game end conditions
            updateScoresAndCheckGameEnd();
        }
    }

    /**
     * Handles collisions between the ball and rackets.
     */
    private void handleBallRacketCollisions() {
        if (ball.isColliding(racket1) && ball.getNode().getCenterX() < racket1.getNode().getX() + racket1.getNode().getWidth()) {
            ball.bounceHorizontal();
            ball.onRacketCollision();
        }

        if (ball.isColliding(racket2) && ball.getNode().getCenterX() > racket2.getNode().getX()) {
            ball.bounceHorizontal();
            ball.onRacketCollision();
        }
    }

    /**
     * Updates scores and checks for game end conditions.
     */
    private void updateScoresAndCheckGameEnd() {
        if (ball.getNode().getCenterX() < 0) {
            player2.incrementScore();
            resetGame();
        } else if (ball.getNode().getCenterX() > PingPongGame.DEFAULT_WIDTH) {
            player1.incrementScore();
            resetGame();
        }
        if (player1.getScore() == 5 || player2.getScore() == 5) {
            playerWon();
            gameEnded = true;
        }
    }

    /**
     * Handles player winning the game.
     */
    private void playerWon() {
        String message = "";
        if (player1.getScore() == 5) {
            message = "Player 1 wins!";
        } else if (player2.getScore() == 5) {
            message = "Player 2 wins!";
        }
        System.out.println(message); // Print to console
        gameOverText.setText(message); // Display in a text node
        gameOverText.setVisible(true); // Make the game-over text visible
        gamePaused = true;
    }

    /**
     * Resets the game state.
     */
    private void resetGame() {
        ball.resetPosition();
        ball.resetSpeed();
        racket1.resetPosition();
        racket2.resetPosition();
    }

    /**
     * Gets the score of Player 1.
     *
     * @return The score of Player 1.
     */
    public int getPlayer1Score() {
        return player1.getScore();
    }

    /**
     * Gets the score of Player 2.
     *
     * @return The score of Player 2.
     */
    public int getPlayer2Score() {
        return player2.getScore();
    }

    /**
     * Checks if the game is paused.
     *
     * @return True if the game is paused, false otherwise.
     */
    public boolean isGamePaused() {
        return gamePaused;
    }

    /**
     * Restarts the game.
     */
    public void restartGame() {
        // Reset player scores
        player1.resetScore();
        player2.resetScore();

        // Reset game state
        resetGame();

        // Hide game over text
        gameOverText.setVisible(false);

        // Reset game ended flag
        gameEnded = false;
        gamePaused = true;
    }
}

