package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Main game entry point
 */
public class Game {

    /**
     * The current level the player is on.
     */
    private GameLevel level;
    /**
     * The view of the game.
     */
    private GameView view;
    /**
     * The controller for the player.
     */
    private PlayerController controller;
    /**
     * The sound manager of the game
     */
    private SoundManager soundManager;
    /**
     * The high score handler of the game
     */
    private HighScoreHandler scoreHandler;
    /**
     * The panel showcasing all the buttons of the game
     */
    private SecondPanel spanel;
    //private JFrame debugView;

    /** Initialise a new Game. */
    public Game() {

        //1. make an empty game world
        soundManager = new SoundManager();
        scoreHandler = new HighScoreHandler("scores.txt");
        spanel = new SecondPanel(this);

        level = new Level1(this);
        level.getLevelSound().play();

        //3. make a view to look into the game world
        // UserView view = new UserView(world, 500, 500);
        view = new GameView(level, 720, 480, level.getPlayer(),this);

        controller = new PlayerController(level.getPlayer(), this);
        view.addKeyListener(controller);

        GiveFocus gFocus = new GiveFocus();
        view.addMouseListener(gFocus);

        spanel = new SecondPanel(this);


        //4. create a Java window (frame) and add the game
        //   view to it
        final JFrame frame = new JFrame("City Game");
        frame.add(view);
        frame.add(spanel.getMainPanel(), BorderLayout.SOUTH);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        //optional: uncomment this to make a debugging view
         //debugView = new DebugViewer(level, 720, 480);

        // start our game world simulation!
        level.start();
    }

    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }

    /**
     * The method to transition between levels.
     * <p>
     * This method is used to change the current level to the next one by re-initialising the variable 'level'
     */
    public void goToNextLevel(){
        if (level instanceof Level1){
            int score = level.getPlayer().getScore();
            int lives = level.getPlayer().getLives();
            //debugView.dispose();
            ((Level1) level).setComplete();
            level.stop();
            level = new Level2(this);
            //level now refer to the new level
            view.setWorld(level);
            //debugView = new DebugViewer(level, 720, 480);
            controller.updatePlayer(level.getPlayer());
            view.updatePlayer(level.getPlayer());
            level.getPlayer().updateScore(score);
            level.getPlayer().updateLives(lives);
            level.start();
        }
        else if (level instanceof Level2){
            int score = level.getPlayer().getScore();
            int lives = level.getPlayer().getLives();
            //debugView.dispose();
            ((Level2) level).setComplete();
            level.stop();
            level = new Level3(this);
            view.setWorld(level);
            //debugView = new DebugViewer(level, 720, 480);
            controller.updatePlayer(level.getPlayer());
            view.updatePlayer(level.getPlayer());
            level.getPlayer().updateScore(score);
            level.getPlayer().updateLives(lives);
            level.start();
        }
        else if (level instanceof Level3){
            gameOver();
            level.stop();
            System.out.println("Well done! Game complete.");
            System.exit(0);
        }
    }

    /**
     * The method to end the game.
     * <p>
     * This method is used to end the game with either a victory or defeat depending on whether or not you reach the end of level 3 while still having lives.
     */
    public void gameOver() {
        level.getPlayer().setGravityScale(0);
        level.getPlayer().setLinearVelocity(new Vec2(0, 0));
        String playerName = JOptionPane.showInputDialog("Enter your name:");
        try {
            scoreHandler.writeHighScore(playerName, getLevel().getPlayer().getScore());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        if (level.getPlayer().getLives()==0) {
            System.exit(0);
        }
    }

    /**
     * The method to retrieve the current game level.
     * <p>
     * This method is used to get the current level of the game.
     * 
     * @return  this method returns the current level of the game.
     */
    public GameLevel getLevel() {
        return level;
    }

    /**
     * The method to retrieve the sound manager.
     * <p>
     * This method is used to get the sound manager of the game so that the sounds can be played after their respective events.
     * 
     * @return  this method returns the sound manager of the game.
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }

    /**
     * The method to retrieve the score handler.
     * <p>
     * This method is used to get the score handler of the game.
     *
     * @return  this method returns the score handler of the game.
     */
    public HighScoreHandler getScoreHandler() {
        return scoreHandler;
    }

    /**
     * The method to load the game level.
     * <p>
     * This method is used to load the level that the player had saved after clicking the save button.
     *
     * @param  levelNo the number of the level needed to be loaded in.
     * @param  score the current score of the player.
     * @param  rings the current number of rings the player has.
     * @param  lives the current number of lives the player has.
     */
    public void loadGame(int levelNo, int score, int rings, int lives) {
        level.getLevelSound().stop();
        level.getPlayer().destroy();
        level.stop();
        if (levelNo==1) {
            level = new Level1(this);
        } else if (levelNo==2) {
            level = new Level2(this);
        } else if (levelNo==3) {
            level = new Level3(this);
        }
        view.setWorld(level);
        controller.updatePlayer(level.getPlayer());
        view.updatePlayer(level.getPlayer());
        level.getPlayer().updateScore(score);
        level.getPlayer().setRings(rings);
        level.getPlayer().updateLives(lives);
        level.start();
    }
}
