import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicManager {
	private static MusicManager instance; // Singleton instance
    private Clip clip;
    private FloatControl volumeControl;
    private int volume = 100;


private MusicManager() {
    try {
        File soundFile = new File("media/Background Music/MenuBackgroundMusic.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

        // Get volume control
        volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(volume); // Set initial volume

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}

public void setVolume(int volume) {
    this.volume = volume;
    if (volumeControl != null) {
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        float range = max - min;

        float newVolume = min + (range * volume / 100);
        volumeControl.setValue(newVolume);
    }
}



}

