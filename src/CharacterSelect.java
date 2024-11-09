import java.util.HashMap;

import acm.graphics.GImage;

public  class CharacterSelect extends Screen {
    private String name = "Select";
    private String character;
    
	@Override
	public String getName() {
		return name;
	}

	@Override
	protected void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}
	
	private void drawBackground() {
        GImage image;
        image = new GImage("media/images/index/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
    }

	// Will take players to the level select screen once select their character,
	// as well as update the character value
	private void CharacterOneClicked(Button button) {
		gg.displayScreen("level", null);
		character = "Steve";
		return;
	}
	
	private void CharacterTwoClicked(Button button) {
		gg.displayScreen("level", null);
		character = "Gary";
		return;
	}
	private void CharacterThreeClicked(Button button) {
		gg.displayScreen("level", null);
		character = "Nate";
		return;
	}
	
	private String GetCharacter() { //will be used by level to determine which character to display
		return character;
	}
	
	private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }
	
	private void drawButtons() {
		//To-Do: create buttons on Figma, will put them in media and implement draw buttons shortly
	}
}
