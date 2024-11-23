
public class LevelInfo {
	public final String landscapeFile;
	public final int[] laneX;
	public final double speed;
	public final double density; // the density of vehicle
	public final int requirement; // victory after passing so many cars


	public LevelInfo(String landscapeFile, int[] laneX, double speed, double density, int requirement) {
		this.landscapeFile = landscapeFile;
		this.laneX = laneX;
		this.speed = speed;
		this.density = density;
		this.requirement = requirement;
	}

	public static LevelInfo build(int level) {
		LevelInfo info = null;
		if (level == 1) {
			info = new LevelInfo(
				"media/images/playing/level1landscape.png",
				new int[] { 225, 530, 850 },
				0.1, 1, 30);
		} else if (level == 2)
		{
			info = new LevelInfo(
				"media/images/playing/level2landscape.png",
				new int[] { 180, 410, 640, 875 },
				0.2, 1.5, 45);
		}

		return info;
	}


}
