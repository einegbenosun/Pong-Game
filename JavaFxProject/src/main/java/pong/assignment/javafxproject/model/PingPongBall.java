package pong.assignment.javafxproject.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pong.assignment.javafxproject.view.PingPongGame;

import java.util.Random;

/**
 * Represents the Ping Pong ball in the game.
 */
public class PingPongBall {

    private final Circle ball; // The circle representing the ball
    private double speedX = 3; // Horizontal speed of the ball
    private double speedY = 3; // Vertical speed of the ball
    private int bounceCount = 0; // Counter to track the number of bounces
    private final double initialX = 300; // Initial x-coordinate of the ball
    private final double initialY = 200; // Initial y-coordinate of the ball
    private final Random rand = new Random(); // Random number generator

    /**
     * Initializes the ball with a given color.
     *
     * @param color The color of the ball.
     */
    public PingPongBall(Color color) {
        ball = new Circle(initialX, initialY, 10);
        ball.setFill(color);
    }

    /**
     * Returns the circle representing the ball.
     *
     * @return The circle.
     */
    public Circle getNode() {
        return ball;
    }

    /**
     * Updates the position of the ball based on its current speed and handles boundary collisions.
     */
    public void updateBall() {
        ball.setCenterX(ball.getCenterX() + speedX);
        ball.setCenterY(ball.getCenterY() + speedY);
        // Reverse vertical direction if hitting top or bottom boundary
        if (ball.getCenterY() <= ball.getRadius() || ball.getCenterY() >= PingPongGame.DEFAULT_HEIGHT - ball.getRadius()) {
            speedY = -speedY;
        }
    }

    /**
     * Adjusts the position of the ball when the window is resized.
     *
     * @param ratioX is the ratio of the new window width to the default width.
     * @param ratioY is the ratio of the new window height to the default height.
     */
    public void adjustPosition(double ratioX, double ratioY) {
        ball.setCenterX(initialX * ratioX);
        ball.setCenterY(initialY * ratioY);
    }

    /**
     * Checks if the ball is colliding with a given PingPongRacket object.
     *
     * @param racket The racket to check for collision with.
     * @return True if the ball is colliding with the racket.
     */
    public boolean isColliding(PingPongRacket racket) {
        return ball.getBoundsInParent().intersects(racket.getNode().getBoundsInParent());
    }

    /**
     * Changes the horizontal speed of the ball to bounce off a surface.
     */
    public void bounceHorizontal() {
        speedX = -speedX;
    }

    /**
     * Resets the position of the ball to its initial position and sets its speed to random values.
     */
    public void resetPosition() {
        ball.setCenterX(initialX);
        ball.setCenterY(initialY);
        // Set random initial speed in x and y directions
        speedX = rand.nextBoolean() ? 3 : -3;
        speedY = rand.nextBoolean() ? 3 : -3;
    }

    /**
     * Increases the speed of the ball after a certain number of bounces.
     */
    public void increaseSpeed() {
        // Increase speed by 10%
        speedX *= 1.1;
        speedY *= 1.1;
    }

    /**
     * Resets the speed of the ball and the bounce count.
     */
    public void resetSpeed() {
        speedX = 3;
        speedY = 3;
        bounceCount = 0;
    }

    /**
     * Increases the count for every time the ball is colliding with a racket.
     */
    public void onRacketCollision() {
        bounceCount++;
        // If the ball has bounced 5 times, increase its speed
        if (bounceCount >= 5) {
            increaseSpeed();
            bounceCount = 0; // Reset the count after increasing speed
        }
    }
}
