import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

// This singleton class is used to store data about the player
// Maybe in the future this information will be stored in a file
// Use this class:
//   PlayerData.getInstance().method();
public class PlayerData implements Serializable {

    public static class LevelScore implements Serializable {
        public int star;
        public long timeMs;
        public int carPassed;
    }

	private HashMap<Integer, LevelScore> bestScores;

	private PlayerData() {
		// Read from file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"))) {
            PlayerData obj = (PlayerData)in.readObject();
            this.bestScores = obj.bestScores;
        } catch (IOException | ClassNotFoundException e) {
            bestScores = new HashMap<Integer, LevelScore>();
            //e.printStackTrace();
        }
	}

    public LevelScore getBestScore(int level) {
        return bestScores.get(level);
    }

    public void setBestScore(int level, LevelScore score) {
    	bestScores.put(level, score);

        // Save to file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private static class PlayerDataHolder {
        private static final PlayerData INSTANCE = new PlayerData();
    }

    public static PlayerData getInstance() {
        return PlayerDataHolder.INSTANCE;
    }
}
