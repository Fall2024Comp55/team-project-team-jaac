import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicManager {
	private static MusicManager instance; // Singleton instance
    public Clip clip; // made public
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


public static MusicManager getInstance() {
    if (instance == null) {
        instance = new MusicManager();
    }
    return instance;
}

private Clip loadMusic(String filePath) {
    try {
        File soundFile = new File(filePath);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip newClip = AudioSystem.getClip();
        newClip.open(audioStream);

        return newClip;
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
        return null;
    }
}



private void playMusic(String filePath) {
    stopMusic(); // Stop any currently playing music
    clip = loadMusic(filePath);
    if (clip != null) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        setVolume(volume); // Apply the current volume setting
    }
}

public void playMenuMusic() {
    playMusic("media/Background Music/MenuBackgroundMusic.wav");
}

public void playLevel1Music() {
    playMusic("media/Background Music/Jaguar XJ220 - Car Repair Menu Theme [Amiga].wav");
}

public void playLevel2Music() {
    playMusic("media/Background Music/EZ Rollers - Breakbeat Generation (Instrumental).wav");
}

public void playLevel3Music() {
    playMusic("media/Background Music/Teriyaki Boyz - Tokyo Drift Instrumental.wav");
}

public void stopMusic() {
    if (clip != null && clip.isRunning()) {
        clip.stop();
        clip.close();
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

public int getVolume() {
    return volume;
}


}

