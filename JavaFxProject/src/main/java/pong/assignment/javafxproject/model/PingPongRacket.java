package pong.assignment.javafxproject.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a racket.
 */
public class PingPongRacket {
    private final Rectangle racket; // Rectangle representing the racket
    private final double initialX; // Initial x position of the racket
    private final double initialY; // Initial y position of the racket
    private static final double WIDTH = 10; // Width of the racket
    private static final double HEIGHT = 70; // Height of the racket

    /**
     * Constructor initializes the racket with a given color, initial x, and y positions.
     *
     * @param color The color of the racket.
     * @param x     The initial x position of the racket.
     * @param y     The initial y position of the racket.
     */
    public PingPongRacket(Color color, double x, double y) {
        this.initialX = x;
        this.initialY = y;
        this.racket = new Rectangle(x, y, WIDTH, HEIGHT); // Create a rectangle for the racket
        this.racket.setFill(color); // Set the color of the racket
    }

    /**
     * Getter method for accessing the racket.
     *
     * @return The rectangle representing the racket.
     */
    public Rectangle getNode() {
        return racket;
    }

    /**
     * Move the racket up if it's not at the top boundary.
     */
    public void moveUp() {
        if (racket.getY() > 0) {
            racket.setY(racket.getY() - 10); // Adjust the value as needed
        }
    }

    /**
     * Move the racket down if it's not at the bottom boundary.
     *
     * @param sceneHeight The height of the scene.
     */
    public void moveDown(double sceneHeight) {
        if (racket.getY() + HEIGHT + 10 <= sceneHeight) {
            racket.setY(racket.getY() + 10); // Adjust the value as needed
        }
    }

    /**
     * Adjust the position and size of the racket when the window is resized.
     *
     * @param ratioX The ratio by which the width of the scene has changed.
     * @param ratioY The ratio by which the height of the scene has changed.
     */
    public void adjustPosition(double ratioX, double ratioY) {
        racket.setX(initialX * ratioX);
        racket.setY(initialY * ratioY);
        racket.setWidth(WIDTH * ratioX);
        racket.setHeight(HEIGHT * ratioY);
    }

    /**
     * Reset the position of the racket to its initial position.
     */
    public void resetPosition() {
        racket.setX(initialX);
        racket.setY(initialY);
    }
}
