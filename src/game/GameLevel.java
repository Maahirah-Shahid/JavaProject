package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

/**
 * The base class for all game levels.
 */
public abstract class GameLevel extends World{

    /**
     * The variable for the player.
     */
    private Player player;
    /**
     * the variable for the game
     */
    private Game game;

    /**
     * The constructor for the Game Level class.
     * <p>
     * This method is used to initialise all the variables of the game level class.
     */
    public GameLevel(Game game){
        super();

        // make the character
        player = new Player(this);
        PlayerCollisions pickup = new PlayerCollisions(player, game);
        player.addCollisionListener(pickup);
        player.setGravityScale(2.5f);
        System.out.println(player.getGravityScale());
        this.game = game;
    }

    /**
     * The method used to get the player.
     * <p>
     * This method is used to get the player variable from any game level.
     * 
     * @return  this method returns the player variable of the game level class.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * The method used to get the game.
     * <p>
     * This method is used to get the game variable from any game level.
     * 
     * @return  this method returns the game variable of the game level class.
     */
    public Game getGame() { return game; }

    /**
     * The required blueprint method for every class that extends from game level.
     * <p>
     * This method is required by all classes that inherit from this one. It will establish whether or not the level is complete.
     */
    public abstract boolean isComplete();

    /**
     * The required blueprint method for every class that extends from game level.
     * <p>
     * This method is required by all classes that inherit from this one. It will get the background of each class.
     */
    public abstract Image getBackground();

    /**
     * The required blueprint method for every class that extends from game level.
     * <p>
     * This method is required by all classes that inherit from this one. It will get the level sound of each level.
     */
    public abstract SoundClip getLevelSound();

    /**
     * The required blueprint method for every class that extends from game level.
     * <p>
     * This method is required by all classes that inherit from this one. It will get the level number of each level.
     */
    public abstract int getLevelNumber();
}
