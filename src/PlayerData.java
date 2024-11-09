import java.util.HashMap;

// This singleton class is used to store data about the player
// Maybe in the future this information will be stored in a file
// Use this class:
//   PlayerData.getInstance().method();
public class PlayerData {

    public class LevelScore {
        public int star;
        public int timeMs;
        public int carPassed;
    }

	private HashMap<Integer, LevelScore> bestScores;

	private PlayerData() {
		// Read from file?
        bestScores = new HashMap<Integer, LevelScore>();
	}

    public LevelScore getBestScore(int level) {
        return bestScores.get(bestScores);
    }

    public void setBestScore(int level, LevelScore score) {
    	bestScores.put(level, score);
    }

	private static class PlayerDataHolder {
        private static final PlayerData INSTANCE = new PlayerData();
    }

    public static PlayerData getInstance() {
        return PlayerDataHolder.INSTANCE;
    }
}
