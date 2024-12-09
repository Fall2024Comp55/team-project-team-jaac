import java.util.HashMap;

import acm.graphics.GImage;

public class LevelScreen extends Screen {

    private String name = "Level";
    private Character character;

	@Override
	public void show(HashMap<String, Object> params) {
		character = (Character) params.get("Character");
		drawBackground();
		drawButtons();
		drawStars();
		drawLocks();
		
	}

    private void drawBackground() {
		gg.add(new GImage("media/images/level/bg.png", 0, 0));
		gg.add(new GImage("media/images/level/title.png", 224, 41));
    }

    private Void BackButtonClicked(Button button) {
        gg.displayScreen("Select", null);
        return null;
    }
    
    private Void LevelButtonClicked(Button button, int level) {
    	// Determine if the level is locked
    	if (level > 1 && (PlayerData.getInstance().getBestScore(level - 1) == null)) {
    		return null; // is locked
    	}
    	
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("Level", level);
    	params.put("Character", character);
    	gg.displayScreen("Playing",  params);
    	
    	
    	return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/level/back.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));
		gg.add((new Button("media/images/level/level1.png", 236, 153)).clicked((Button b) -> { return LevelButtonClicked(b, 1); }));
		gg.add((new Button("media/images/level/level2.png", 236, 348)).clicked((Button b) -> { return LevelButtonClicked(b, 2); }));
		gg.add((new Button("media/images/level/level3.png", 236, 543)).clicked((Button b) -> { return LevelButtonClicked(b, 3); }));
    }
    
    private int calcStars(int level) {
		int stars = 6; //max number of stars
		long standardTimeMS; // ideal time to finish to get 6 stars
		PlayerData.LevelScore score = PlayerData.getInstance().getBestScore(level);
		
		if (level == 1) {
			standardTimeMS = 80000;
		}
		else if (level == 2) {
			standardTimeMS = 30000;
		}
		
		else{
			standardTimeMS = 20000;
		}
		
		long diff = score.timeMs - standardTimeMS;
		
		for (int i= 0; i < 6; i++) { // iterates through diff to see how many multiples of 5s
									 // the player took to complete the level
			if(diff >= 5000) {
				stars--;
				diff -= 5000;
			}
		}
		
		return stars;
	}
    
    private void drawStars() {
    	for (int level = 1; level <= 3; level++) {
    		
    		int light = calcStars(level);
   		 	int dark = 6 - light;
    		
    		int y = 202 + (level - 1) * 195;
    		int x = 564;
    		for (int i = 0; i < light; i++) {
        		gg.add(new GImage("media/images/level/star_light.png", x, y));
        		x += 68;
    		}
    		for (int i = 0; i < dark; i++) {
        		gg.add(new GImage("media/images/level/star_dark.png", x, y));
        		x += 68;
    		}
    	}
    }
    
    private void drawLocks() {
    	for (int level = 1; level <= 3; level++) {
    		if (level > 1 && (PlayerData.getInstance().getBestScore(level - 1) == null)) {
    			int y = 130 + (level - 1) * 195;
            	gg.add(new GImage("media/images/level/lock.png", 215, y));
            }
    	}
    }


    @Override
    public String getName() {
        return name;
    }

	@Override
	protected void hide() {
		// nothing to do
		
	}

}
