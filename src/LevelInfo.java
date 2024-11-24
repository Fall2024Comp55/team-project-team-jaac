
public class LevelInfo {
	public final String landscapeFile;
	public final int[] laneX;
	public final double speed;
	public final double density; // the density of vehicle
	public final int requirement; // victory after passing so many cars
	public final int defaultLane;


	public LevelInfo(String landscapeFile, int[] laneX, double speed, double density, int requirement, int defaultLane) {
		this.landscapeFile = landscapeFile;
		this.laneX = laneX;
		this.speed = speed;
		this.density = density;
		this.requirement = requirement;
		this.defaultLane = defaultLane;
	}

	public static LevelInfo build(int level) {
		LevelInfo info = null;
		if (level == 1) {
			info = new LevelInfo(
				"media/images/playing/level1landscape.png",
				new int[] { 225, 530, 850 },
				0.1, 1, 30, 1);
		} else if (level == 2)
		{
			info = new LevelInfo(
				"media/images/playing/level2landscape.png",
				new int[] { 180, 410, 640, 875 },
				0.2, 1.5, 45, 2);
		} else if (level == 3) { // Add Level 3 here
	        info = new LevelInfo(
	                "media/images/playing/level2landscape.png",
	                new int[] { 150, 350, 550, 750, 950 }, // More lanes for increased difficulty
	                0.3, 1.6, 60, 2); // Faster speed, higher density, and more vehicles to pass
	        }

		return info;
	}


}
