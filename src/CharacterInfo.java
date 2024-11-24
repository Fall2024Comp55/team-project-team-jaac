import acm.graphics.GImage;

enum Character {
	Steve, Gary, Nate
}

public class CharacterInfo {
	private int health;
	private Character character;
	private GImage model;
	private int lane1;
	private int lane2;
	private int y;

	public CharacterInfo(Character character, int lane) {
		this.character = character;
		this.lane1 = lane;
		this.lane2 = lane;
		this.y = 400;
		if (character == Character.Nate) {
			setHealth(4);
			setModel(new GImage("media/images/playing/CharacterAssets/Nature_nate1.png"));
		}
		else if (character == Character.Gary) {
			setHealth(3);
			setModel(new GImage("media/images/playing/CharacterAssets/GaryInGame2.png"));
		}
		else if (character == Character.Steve) {
			setHealth(3);
			setModel(new GImage("media/images/playing/CharacterAssets/SteveIngame1.png"));
		}
	}

	public Character getCharacter(){
		return character;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public GImage getModel() {
		return model;
	}

	private void setModel(GImage model) {
		this.model = model;
	}

	public int getLane1() {
		return lane1;
	}

	public void setLane1(int lane) {
		this.lane1 = lane;
	}

	public int getLane2() {
		return lane2;
	}

	public void setLane2(int lane) {
		this.lane2 = lane;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
