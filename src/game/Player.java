package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * class to represent the main character
 */
public class Player extends Walker implements ActionListener {

    /**
     * The shape of the player.
     */
    private static final Shape playerShape = new BoxShape(1,1.5f, new Vec2(0.34f,-0.38f));
    /**
     * The initial image of the player.
     */
    private static final BodyImage playerImage  = new BodyImage("data/Sonic/SonicIdle0.png", 4);
    //private static final Spritesheet spritesheet = new Spritesheet();
    /**
     * The rings of the player.
     */
    private int rings;
    /**
     * The lives of the player.
     */
    private int lives;
    /**
     * The score of the player.
     */
    private int score;
    /**
     * The animation timer and spindash timer for the player.
     */
    private Timer timer, spindashTimer;
    /**
     * The current frame of the animation.
     */
    private int currentFrame = 0;
    /**
     * The frames of several different animations.
     */
    private BodyImage[] idleFrames, crouchFrames, jumpFrames, spindashFrames, spindashingFrames, walkFrames, jogFrames;
    /**
     * The frames of several different animations.
     */
    private BodyImage[] runFrames, sprintFrames;
    /**
     * The boolean to check whether the player is in idle mode.
     */
    private boolean idleOn = true;
    /**
     * The boolean to check whether the player is crouching.
     */
    private boolean crouchOn = false;
    /**
     * The boolean to check whether the player is jumping.
     */
    private boolean jumpOn = false;
    /**
     * The boolean to check whether the player is facing right.
     */
    private boolean facingRight;
    /**
     * The sound the player makes when jumping.
     */
    private SoundClip jumpSound;
    /**
     * The boolean to check whether the jump sound is played.
     */
    private boolean jumpSoundPlayed = false;
    /**
     * The boolean to check whether the player is holding down the space button.
     */
    private boolean spaceHeld = false;
    /**
     * The boolean to check whether the player is charging up a spindash.
     */
    private boolean spindashOn = false;
    /**
     * The boolean to check whether the player is in spindash mode or not.
     */
    private boolean spindashing = false;
    /**
     * The boolean to check whether the player is walking.
     */
    private boolean walkOn = false;
    /**
     * The boolean to check whether the players is jogging.
     */
    private boolean jogOn = false;
    /**
     * The boolean to check whether the player is running.
     */
    private boolean runOn = false;
    /**
     * The boolean to check whether the player is sprinting.
     */
    private boolean sprintOn = false;
    /**
     * The current speed of the player.
     */
    private float walkSpeed = 0; //current speed
    /**
     * The acceleration of the player.
     */
    private float acceleration = 0.3f; //how fast he builds up
    /**
     * The max speed the player can get.
     */
    private final float maxSpeed = 25f; //cap speed
    /**
     * The max acceleration the player can get.
     */
    private final float maxAcceleration = 1.5f;
    /**
     * The boolean to check whether the player is moving right.
     */
    private boolean movingRight = false;
    /**
     * The boolean to check whether the player is moving left.
     */
    private boolean movingLeft = false;
    /**
     * The movement timer.
     */
    private Timer movementTimer;
    /**
     * The number it takes for the player to stop his walking animation.
     */
    private final float walkThreshold = 2.5f;
    /**
     * The number it takes for the player to stop his jogging animation.
     */
    private final float jogThreshold = 9f;
    /**
     * The number it takes for the player to stop his sprinting animation.
     */
    private final float runThreshold = 20f;
    /**
     * The boolean to check whether the player is invincible.
     */
    private boolean isInvincible = false;
    /**
     * The invincibility timer.
     */
    private Timer invincibilityTimer;
    /**
     * The flash timer.
     */
    private Timer flashTimer;
    /**
     * The boolean to check whether the player is visible.
     */
    private boolean isVisible = true;
    /**
     * The boolean to check whether the player is in the void.
     */
    private boolean isInVoid = false;


    /**
     * The constructor for the Player class.
     * <p>
     * This method is used to initialise all of the Player class' key variables.
     *
     * @param  world the world in which the player spawns in.
     */
    public Player(World world) {
        super(world, playerShape);
        idleFrames = new BodyImage[8];
        crouchFrames = new BodyImage[4];
        jumpFrames = new BodyImage[13];
        spindashFrames = new BodyImage[8];
        spindashingFrames = new BodyImage[3];
        walkFrames = new BodyImage[8];
        jogFrames = new BodyImage[8];
        runFrames = new BodyImage[8];
        sprintFrames = new BodyImage[8];
        loadIdleFrames();
        loadCrouchFrames();
        loadJumpFrames();
        loadSpindashFrames();
        loadSpindashingFrames();
        loadWalkFrames();
        loadJogFrames();
        loadRunFrames();
        loadSprintFrames();
        timer = new Timer(125, this);
        timer.start();
        this.addImage(playerImage);
        rings = 0;
        lives = 3;
        score = 0;
        this.setAlwaysOutline(false);
        facingRight = true;
    }

    /**
     * A method to load the idle frames into an array.
     * <p>
     * This method is used in the instructor class to load all the idle frame images from the data file, into the idleFrames array.
     */
    public void loadIdleFrames() {
        for (int i = 0; i < idleFrames.length; i++) {
            idleFrames[i] = new BodyImage("data/Sonic/SonicIdle" + i + ".png", 4); //loads all frames for the animation with the number in the variable i
        }
    }

    /**
     * A method to load the crouch frames into an array.
     * <p>
     * This method is used in the instructor class to load all the crouch frame images from the data file, into the crouchFrames array.
     */
    public void loadCrouchFrames() {
        for (int i = 0; i < crouchFrames.length; i++) {
            crouchFrames[i] = new BodyImage("data/Sonic/SonicCrouch" + i + ".png", 4);
        }
    }

    /**
     * A method to load the jump frames into an array.
     * <p>
     * This method is used in the instructor class to load all the jump frame images from the data file, into the jumpFrames array.
     */
    public void loadJumpFrames() {
        for (int i = 0; i < jumpFrames.length; i++) {
            jumpFrames[i] = new BodyImage("data/Sonic/SonicJump" + i + ".png", 4);
        }
    }

    /**
     * A method to load the spindash frames into an array.
     * <p>
     * This method is used in the instructor class to load all the spindash frame images from the data file, into the spindashFrames array.
     */
    public void loadSpindashFrames() {
        for (int i = 0; i < spindashFrames.length; i++) {
            spindashFrames[i] = new BodyImage("data/Sonic/SonicSpindash" + i + ".png", 4);
        }
    }

    /**
     * A method to load the spindashing frames into an array.
     * <p>
     * This method is used in the instructor class to load all the spindashing frame images from the data file, into the spindashingFrames array.
     */
    public void loadSpindashingFrames() {
        for (int i = 0; i < spindashingFrames.length; i++) {
            spindashingFrames[i] = new BodyImage("data/Sonic/SonicSpindashing" + i + ".png", 4);
        }
    }

    /**
     * A method to load the walk frames into an array.
     * <p>
     * This method is used in the instructor class to load all the walk frame images from the data file, into the walkFrames array.
     */
    public void loadWalkFrames() {
        for (int i = 0; i < walkFrames.length; i++) {
            walkFrames[i] = new BodyImage("data/Sonic/SonicWalk" + i + ".png", 4);
        }
    }

    /**
     * A method to load the jog frames into an array.
     * <p>
     * This method is used in the instructor class to load all the jog frame images from the data file, into the jogFrames array.
     */
    public void loadJogFrames() {
        for (int i = 0; i < jogFrames.length; i++) {
            jogFrames[i] = new BodyImage("data/Sonic/SonicJog" + i + ".png", 4);
        }
    }

    /**
     * A method to load the run frames into an array.
     * <p>
     * This method is used in the instructor class to load all the run frame images from the data file, into the runFrames array.
     */
    public void loadRunFrames() {
        for (int i = 0; i < runFrames.length; i++) {
            runFrames[i] = new BodyImage("data/Sonic/SonicRun" + i + ".png", 4);
        }
    }

    /**
     * A method to load the sprint frames into an array.
     * <p>
     * This method is used in the instructor class to load all the sprint frame images from the data file, into the sprintFrames array.
     */
    public void loadSprintFrames() {
        for (int i = 0; i < sprintFrames.length; i++) {
            sprintFrames[i] = new BodyImage(("data/Sonic/SonicSprint" + i + ".png"), 4);
        }
    }

    /**
     * A method to animate the player.
     * <p>
     * This method is used cycle through the frames of animation of the player.
     *
     * @param e the action event taking place.
     */
    public void actionPerformed(ActionEvent e) {
        if (crouchOn==true) { //if the crouch is on, the player will crouch
            if (currentFrame < crouchFrames.length-1) { //currentFrame will be iterated by one every tick of the clock until it reaches the last frame
                currentFrame = (currentFrame + 1) % crouchFrames.length; //% makes sure currentFrames stays in range
            }
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(crouchFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(crouchFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,0));
            }
        } else if (idleOn==true) { //if the player is idle, the idle animation will play
            currentFrame = (currentFrame + 1) % idleFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(idleFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(idleFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,0));
            }
        } else if (jumpOn==true) { //if the player is jumping, the jump animation plays
            if (!jumpSoundPlayed) {
                jumpSound = null;
                try {
                    jumpSound = new SoundClip("data/SoundEffects/JumpSound.wav");
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException Ex) {
                    System.out.println("Error:" + Ex);
                }
                if (jumpSound != null) {
                    jumpSound.play();
                }
                jumpSoundPlayed = true;
            }
            if (currentFrame < jumpFrames.length-1) {
                currentFrame = (currentFrame + 1) % jumpFrames.length;
            }
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(jumpFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(jumpFrames[currentFrame]);
                sprite.flipHorizontal();
            }
        } else if(spindashOn==true) {
            currentFrame = (currentFrame + 1) % spindashFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(spindashFrames[currentFrame]);
                sprite.setOffset(new Vec2(0,-0.5f));
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(spindashFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,-0.5f));
            }
        } else if (spindashing==true) {
            currentFrame = (currentFrame + 1) % spindashingFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(spindashingFrames[currentFrame]);
                sprite.setOffset(new Vec2(0,-0.8f));
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(spindashingFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,-0.8f));
            }
        } else if (walkOn==true) {
            currentFrame = (currentFrame + 1) % walkFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(walkFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(walkFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,0));
            }
        } else if (jogOn==true) {
            currentFrame = (currentFrame + 1) % jogFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(jogFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(jogFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,0));
            }
        } else if (runOn==true) {
            currentFrame = (currentFrame + 1) % runFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(runFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(runFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,0));
            }
        } else if (sprintOn==true) {
            currentFrame = (currentFrame + 1) % sprintFrames.length;
            if (!getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(sprintFrames[currentFrame]);
            } else if (getFacingRight()) {
                this.removeAllImages();
                AttachedImage sprite = this.addImage(sprintFrames[currentFrame]);
                sprite.flipHorizontal();
                sprite.setOffset(new Vec2(-0.65f,0));
            }
        }
        if (this.getPosition().y<-3) {
            isInVoid = true;
        } else {
            isInVoid = false;
        }
    }

    /**
     * A method to start the crouch animation.
     * <p>
     * This method is used change the boolean of crouchOn to true.
     */
    public void startCrouch() { //declares the crouch to start, while also declaring idleOn as false so animation doesn't overlap
        if (crouchOn==false) {
            crouchOn = true;
            idleOn = false;
            currentFrame = 0;
        }
    }

    /**
     * A method to stop the crouch animation.
     * <p>
     * This method is used change the boolean of crouchOn to false.
     */
    public void stopCrouch() {
        if (crouchOn==true) {
            crouchOn = false;
            idleOn = true;
            currentFrame = 0;
        }
    }

    /**
     * A method to start the jump animation.
     * <p>
     * This method is used change the boolean of jumpOn to true.
     */
    public void startJump() {
        if (jumpOn==false) {
            jumpOn = true;
            idleOn = false;
            crouchOn = false;
            currentFrame = 0;
        }
    }

    /**
     * A method to stop the jump animation.
     * <p>
     * This method is used change the boolean of jumpOn to false.
     */
    public void stopJump() {
        if (jumpOn==true) {
            jumpOn = false;
            jumpSoundPlayed = false;
            idleOn = true;
            currentFrame = 0;
        }
    }

    /**
     * A method to start the spindash charge animation.
     * <p>
     * This method is used change the boolean of spindashOn to true.
     */
    public void startSpindash() {
        if (spindashOn==false) {
            spindashOn = true;
            idleOn = false;
            crouchOn = false;
            currentFrame = 0;
        }
    }

    /**
     * A method to stop the spindash charge animation.
     * <p>
     * This method is used change the boolean of spindashOn to false and to give the player speed.
     */
    public void stopSpindash() {
        if (spindashOn==true) {
            if (getFacingRight()) {
                this.setLinearVelocity(new Vec2(30,0));
            } else {
                this.setLinearVelocity(new Vec2(-30,0));
            }
            spindashOn = false;
            if (spindashing==false) {
                this.inSpindash();
            }
            crouchOn = false;
            currentFrame = 0;
        }
    }

    /**
     * A method to start the spindashing animation.
     * <p>
     * This method is used change the boolean of spindashing to true and then false after a set amount of time.
     */
    public void inSpindash() {
        spindashing = true;
        currentFrame = 0;
        if (spindashTimer!=null && spindashTimer.isRunning()) {
            spindashTimer.stop();
        }
        //is supposed to end spindashing when a certain amount of time has passed
        spindashTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spindashing = false;
                idleOn = true;
                spindashTimer.stop();
            }
        });
        spindashTimer.setRepeats(false);
        spindashTimer.start();
    }

    /**
     * A method to start the acceleration of the player.
     * <p>
     * This method is used to build up speed the longer the player holds left or right.
     */
    public void startAccelerationTimer() {
        if (movementTimer != null && movementTimer.isRunning()) {
            movementTimer.stop();
        }
        acceleration = 0.3f;

        if (spindashing==true) {
            walkSpeed = this.getLinearVelocity().x;
        }

        movementTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceleration = Math.min(acceleration + 0.2f, maxAcceleration);
                if (movingRight) {
                    walkSpeed = Math.min(walkSpeed + acceleration, maxSpeed);
                    startWalking(walkSpeed);
                } else if (movingLeft) {
                    walkSpeed = Math.max(walkSpeed - acceleration, -maxSpeed);
                    startWalking(walkSpeed);
                } else {
                    walkSpeed = 0;
                    stopWalking();
                }
                //System.out.println(walkSpeed);
                updateMovementAnimation();
            }
        });
        movementTimer.start();
    }

    /**
     * A method to switch between movement animations.
     * <p>
     * This method is used change between each movement animation Sonic is in depending on his speed.
     */
    public void updateMovementAnimation() {
        walkOn = jogOn = runOn = sprintOn = false;

        float absSpeed = Math.abs(walkSpeed);

        if (absSpeed > 0 && absSpeed <= walkThreshold) {
            walkOn = true;
            idleOn = false;
        } else if (absSpeed > walkThreshold && absSpeed <= jogThreshold) {
            jogOn = true;
            idleOn = false;
        } else if (absSpeed > jogThreshold && absSpeed <= runThreshold) {
            runOn = true;
            idleOn = false;
        } else if (absSpeed > runThreshold) {
            sprintOn = true;
            idleOn = false;
        }
    }

    /**
     * A method to stop the acceleration of the player.
     * <p>
     * This method is used to revert Sonic to his idle mode after the play stops moving him.
     */
    public void stopAcceleration() {
        movingLeft = movingRight = false;
        walkOn = jogOn = runOn = sprintOn = false;
        if (!jumpOn) {
            idleOn = true;
        }
        acceleration = 0.3f;
        walkSpeed = 0;
        stopWalking();
        if (movementTimer != null) {
            movementTimer.stop();
        }
    }

    /**
     * A method to cause the player to take damage.
     * <p>
     * This method is used to take away the rings of the player if damaged and give them temporary invincibility.
     */
    public void takeDamage() {
        if (!isInvincible) {
            if (facingRight) {
                this.setLinearVelocity(new Vec2(-10, 15));
            } else {
                this.setLinearVelocity(new Vec2(10, 15));
            }
            System.out.println("Sonic took damage!");
            if (rings > 0) {
                int scattered = Math.min(rings, 10); // limit how many get dropped
                for (int i = 0; i < scattered; i++) {
                    ScatteredRings ring = new ScatteredRings(getWorld(), getPosition().x, getPosition().y);
                    float angle = (float)(Math.random() * Math.PI * 2);
                    float speed = 15 + (float)(Math.random() * 5);
                    Vec2 velocity = new Vec2((float)Math.cos(angle) * speed, (float)Math.sin(angle) * speed);
                    ring.setLinearVelocity(velocity);
                }
                rings = 0;
            } else {
                decreaseLives();
            }
            startInvincibility(2000);
        }
    }

    /**
     * A method to start the invincibility of the player.
     * <p>
     * This method is used to start the invincibility of the player and flash his sprite on the screen to show it has started.
     *
     * @param duration the time in which the player is invincible.
     */
    public void startInvincibility(int duration) {
        isInvincible = true;

        flashTimer = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isVisible) {
                    removeAllImages();
                    AttachedImage sprite = addImage(new BodyImage("data/Sonic/SonicInvisible.png"));
                    isVisible = false;
                } else {
                    isVisible = true;
                }
            }
        });
        flashTimer.start();

        invincibilityTimer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isInvincible = false;
                flashTimer.stop();
                isVisible = true;
                System.out.println("Sonic is no longer invincible.");
            }
        });
        invincibilityTimer.setRepeats(false);
        invincibilityTimer.start();
    }

    /**
     * A method to get rings.
     * <p>
     * This method is used to get the current ring count of the player.
     *
     * @return returns the current ring count of the player.
     */
    public int getRings() {
        return rings;
    }

    /**
     * A method to set the rings of the player.
     * <p>
     * This method is used to set the rings of the player to the variable passed into it.
     *
     * @param rings the rings the player will have.
     */
    public void setRings(int rings) {
        this.rings = rings;
    }

    /**
     * A method to get lives.
     * <p>
     * This method is used to get the current amount of lives from the player.
     *
     * @return returns the current amount of lives the player has.
     */
    public int getLives() {
        return lives;
    }

    /**
     * A method to decrease lives of the player.
     * <p>
     * This method is used to decrease the lives count of the player by one.
     */
    public void decreaseLives() {
        this.lives = lives-1;
    }

    /**
     * A method to check if jump is on.
     * <p>
     * This method returns the jumpOn variable so the game can check whether the player is jumping or not.
     *
     * @return returns the jumpOn variable.
     */
    public boolean isJumpOn() {
        return jumpOn;
    }

    /**
     * A method to get the facingRight variable.
     * <p>
     * This method is used to check if the player is facing right or not.
     *
     * @return returns whether or not the player is facing right.
     */
    public boolean getFacingRight() {
        return facingRight;
    }

    /**
     * A method to set facing right.
     * <p>
     * This method is used to change the facingRight variable to whatever is passed.
     *
     * @param  facingRight the boolean this.facingRight is changed to.
     */
    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    /**
     * A method to check if the player is crouching.
     * <p>
     * This method is used to check whether the player is crouching.
     *
     * @return  returns whether or not the player is crouching.
     */
    public boolean isCrouching() {return crouchOn;}

    /**
     * A method to check if the player is holding space.
     * <p>
     * This method is used to check whether the player is holding space.
     *
     * @return  returns whether or not the player is holding space.
     */
    public boolean isSpaceHeld() {
        return spaceHeld;
    }

    /**
     * A method to check if the player is spindashing.
     * <p>
     * This method is used to check whether the player is spindashing.
     *
     * @return  returns whether or not the player is spindashing.
     */
    public boolean isSpindashing() {
        return spindashing;
    }

    /**
     * A method to set spaceHeld.
     * <p>
     * This method is used to set spaceHeld to whatever is passed.
     *
     * @param  spaceHeld the boolean this.spaceHeld is changed to.
     */
    public void setSpaceHeld(boolean spaceHeld) {
        this.spaceHeld = spaceHeld;
    }

    /**
     * A method to set movingLeft.
     * <p>
     * This method is used to set movinLeft to whatever is passed.
     *
     * @param  movingLeft the boolean this.movingLeft is changed to.
     */
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * A method to set movingRight.
     * <p>
     * This method is used to set movingRight to whatever is passed.
     *
     * @param  movingRight the boolean this.movingRight is changed to.
     */
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * A method to get score.
     * <p>
     * This method is used to get the current score of the player.
     *
     * @return  returns the current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * A method to update score.
     * <p>
     * This method is used to update score to the number passed.
     *
     * @param  score the number this.score is changed to.
     */
    public void updateScore(int score) {
        this.score = score;
    }

    /**
     * A method to update lives.
     * <p>
     * This method is used to update lives to the number passed.
     *
     * @param  lives the number this.lives is changed to.
     */
    public void updateLives(int lives) {
        this.lives = lives;
    }
}
