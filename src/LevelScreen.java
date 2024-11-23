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
    
    private void drawStars() {
    	for (int level = 1; level <= 3; level++) {
    		PlayerData.LevelScore score = PlayerData.getInstance().getBestScore(level);
    		int light = 0, dark = 0;
    		if (score == null) {
    			dark = 6;
    		} else {
    			light = score.star;
    			dark = 6 - light;
    		}
    		
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
