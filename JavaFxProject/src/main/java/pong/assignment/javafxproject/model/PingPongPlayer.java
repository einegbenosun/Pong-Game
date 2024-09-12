package pong.assignment.javafxproject.model;

/**
 * Represents a player.
 */
public class PingPongPlayer {
    private String name; // Name of the player
    private int score; // Player's score

    /**
     * Constructor to initialize the player's name and score.
     *
     * @param name The name of the player.
     */
    public PingPongPlayer(String name) {
        this.name = name;
        this.score = 0; // Initialize score to 0 for a new player
    }

    /**
     * Getter method for the player's name.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for the player's score.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Increment the player's score by 1.
     */
    public void incrementScore() {
        score++;
    }

    /**
     * Reset the player's score to 0.
     */
    public void resetScore() {
        score = 0;
    }
}
