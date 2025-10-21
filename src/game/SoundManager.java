package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Class for the sound manager.
 */
public class SoundManager {
    /**
     * All the different soundclip variables used in the game.
     */
    private static SoundClip collectSound, springSound, spindashChargeSound, spindashReleaseSound, breakSound, takeDamageSound;

    /**
     * The constructor for the SoundManager class.
     * <p>
     * This method is used to initialise all the variables in the SoundManager class.
     */
    public SoundManager() {
        collectSound = springSound = spindashChargeSound = spindashReleaseSound = breakSound = takeDamageSound = null;
        try {
            springSound = new SoundClip("data/SoundEffects/SpringSound.wav");
            collectSound = new SoundClip("data/SoundEffects/CoinCollectSound.wav");
            spindashChargeSound = new SoundClip("data/SoundEffects/SonicSpindashCharge.wav");
            spindashReleaseSound = new SoundClip("data/SoundEffects/SpindashReleased.wav");
            breakSound = new SoundClip("data/SoundEffects/BreakSound.wav");
            takeDamageSound = new SoundClip("data/SoundEffects/TakeDamageSound.wav");
            // Open an audio input stream             // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println("Error:" + e);
        }
    }

    /**
     * The method to get collectSound.
     * <p>
     * This method is used to retrieve the collectSound from this class.
     *
     * @return  returns the collectSound variable.
     */
    public SoundClip getCollectSound() {
        return collectSound;
    }

    /**
     * The method to get springSound.
     * <p>
     * This method is used to retrieve the springSound from this class.
     *
     * @return  returns the springSound variable.
     */
    public SoundClip getSpringSound() {
        return springSound;
    }

    /**
     * The method to get spindashChargeSound.
     * <p>
     * This method is used to retrieve the spindashChargeSound from this class.
     *
     * @return  returns the spindashChargeSound variable.
     */
    public SoundClip getSpindashChargeSound() {
        return spindashChargeSound;
    }

    /**
     * The method to get spindashReleaseSound.
     * <p>
     * This method is used to retrieve the spindashReleaseSound from this class.
     *
     * @return  returns the spindashReleaseSound variable.
     */
    public SoundClip getSpindashReleaseSound() {
        return spindashReleaseSound;
    }

    /**
     * The method to get breakSound.
     * <p>
     * This method is used to retrieve the breakSound from this class.
     *
     * @return  returns the breakSound variable.
     */
    public SoundClip getBreakSound() {
        return breakSound;
    }

    /**
     * The method to get takeDamageSound.
     * <p>
     * This method is used to retrieve the takeDamageSound from this class.
     *
     * @return  returns the takeDamageSound variable.
     */
    public SoundClip getTakeDamageSound() {
        return takeDamageSound;
    }
}
