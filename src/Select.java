import java.util.HashMap;

import acm.graphics.GImage;

public  class Select extends Screen {
    private String name = "Select";
    

	@Override
	protected void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}
	
	private void drawBackground() {
        GImage image;
        image = new GImage("media/images/credits/bg.png", 0, 0);
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
		
		//Select Title
		gg.add((new Button("media/images/select/Select-Title.png", 205, 39)).clicked((Button b) -> { return null; }));

		//Steve assets
		gg.add((new Button("media/images/select/Steve1.png", 93, 153)).clicked((Button b) -> { return CharacterClicked(b, "Steve"); }));
		gg.add((new Button("media/images/select/SteveIcon1.png", 138, 192)).clicked((Button b) -> { return CharacterClicked(b, "Steve"); }));
		
		//Gary assets
		gg.add((new Button("media/images/select/Gary1.png", 447, 153)).clicked((Button b) -> { return CharacterClicked(b, "Gary"); }));
		gg.add((new Button("media/images/select/GaryIcon1.png", 487, 192)).clicked((Button b) -> { return CharacterClicked(b, "Gary"); }));

		
		//Nate assets
		gg.add((new Button("media/images/select/Nate1.png", 798, 153)).clicked((Button b) -> { return CharacterClicked(b, "Nate"); }));
		gg.add((new Button("media/images/select/NateIcon1.png", 833, 192)).clicked((Button b) -> { return CharacterClicked(b, "Nate"); }));

	}
	
	@Override
	public String getName() {
		return name;
	}
}
