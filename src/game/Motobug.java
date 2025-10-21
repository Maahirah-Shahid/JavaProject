package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the motobug enemy.
 */
public class Motobug extends Enemy implements ActionListener {
    /**
     * Shape of the motobug.
     */
    private static final Shape shape = new BoxShape(1,1, new Vec2(0,0));
    /**
     * Image for the motobug.
     */
    private static final BodyImage enemyImage = new BodyImage("data/Badniks/Badnik1.png",2.8f);
    /**
     * Timer for when the motobug switches directions.
     */
    private Timer timer;
    /**
     * Boolean variables declaring whether the motobug is walking left or right.
     */
    private boolean isWalkingLeft, isWalkingRight;
    /**
     * The remaining time it takes for the motobug to change direction.
     */
    private long remainingTime;
    /**
     * How long the motobug walks in one direction.
     */
    private long switchDuration=1000;

    /**
     * The constructor for the motobug class.
     * <p>
     * This method is used to initialise all the variables of the motobug class.
     *
     * @param  world the world in which the motobug spawns in.
     * @param  x the x-coordinate of the motobug.
     * @param  y the y-coordinate of the motobug.
     */
    public Motobug(World world, float x, float y) {
        super(world, x, y);
        this.addImage(enemyImage);
        //this.setAlwaysOutline(true);
        isWalkingLeft = false;
        isWalkingRight = true;
        remainingTime = switchDuration; //how long until the enemy switches directions
        timer = new Timer(125, this);
        timer.start();
    }

    /**
     * The method for walking left.
     * <p>
     * This method is used to declare the enemy as walking left so its image is flipped.
     */
    //method to declare enemy walking left so their image is flipped
    public void WalkingLeft(){
        if(isWalkingLeft==false) {
            this.removeAllImages();
            AttachedImage enemyImage2 = this.addImage(enemyImage);
            enemyImage2.flipHorizontal();
            this.startWalking(-5);
            isWalkingLeft = true;
            isWalkingRight = false;
        }
    }

    /**
     * The method for walking right.
     * <p>
     * This method is used to declare the enemy as walking right so its image is not flipped.
     */
    //method to declare enemy is walking right so their image is not flipped
    public void WalkingRight(){
        if(isWalkingRight==false) {
            this.removeAllImages();
            AttachedImage enemyImage2 = this.addImage(enemyImage);
            this.startWalking(5);
            isWalkingRight = true;
            isWalkingLeft = false;
        }
    }

    /**
     * The method for action performed during the timer.
     * <p>
     * This method is used to switch between walking left and right.
     *
     * @param  e the action event.
     */
    public void actionPerformed(ActionEvent e) {
        remainingTime -= 125; //reduces remaining time by the delay of the clock, meaning it is reduced every click

        if (remainingTime <= 0) { //once the switchDuration has past, enemy begins walking the other direction
            if (isWalkingLeft) {
                this.stopWalking();
                WalkingRight();
            } else {
                this.stopWalking();
                WalkingLeft();
            }
            remainingTime = switchDuration; //restarting the remainingTime
        }
    }

    /**
     * The method for destroying the enemy.
     * <p>
     * This method is used to destroy the enemy and end the timer when it is destroyed for better performance.
     */
    @Override
    public void destroy() {
        if (timer != null) {
            System.out.println("destroyed");
            timer.stop();
        }
        super.destroy();
    }
}
