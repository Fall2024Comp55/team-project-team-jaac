import acm.graphics.GImage;

enum Character {
	Steve, Gary, Nate
}
public class CharacterInfo {
	private int health;
	private Character character;
	private GImage model;
	
	public void setCharacter(Character Input) {
		character = Input;
		if (character == Character.Nate) {
			setHealth(4);
			//To-do Model and info
		}
		else if (character == Character.Gary) {
			setHealth(3);
			setModel(new GImage("media/images/playing/CharacterAssets/SteveInGame1.png"));
		}
		else if (character == Character.Steve) {
			setHealth(3);
			setModel(new GImage("media/images/playing/CharacterAssets/GaryInGame2.png"));
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

	public void setModel(GImage model) {
		this.model = model;
	}
	
	
}
