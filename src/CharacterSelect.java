import java.util.HashMap;

import acm.graphics.GImage;

public  class CharacterSelect extends Screen {
    private String name = "Select";
    
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
	private Void CharacterClicked(Button button, String character) {
		// character is Steve, Gary or Nate
		// deliver the character to levelScreen
		HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("Character", character);
		gg.displayScreen("Level", params);
		return null;
	}
		
	private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }
	
	private void drawButtons() {
		gg.add((new Button("media/images/level/back.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));

		//To-Do: create buttons on Figma, will put them in media and implement draw buttons shortly
		// parameters can be delivered between function or screen, please see "LevelScreen"
		//gg.add((new Button("media/images/select/Steve.png", 236, 153)).clicked((Button b) -> { return LevelButtonClicked(b, "Steve"); }));

	}
}
