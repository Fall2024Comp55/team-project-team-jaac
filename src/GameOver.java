import java.util.HashMap;
import java.io.File;
import java.io.IOException;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GObject;
import java.awt.Color;


public class GameOver extends Screen {

    private String name = "GameOverScreen";
    private int level;
    private Character character;
    private double completionTime;

    @Override
    public void show(HashMap<String, Object> params) {
        level = (int) params.get("Level");
        character = (Character) params.get("Character");
        completionTime = (double) params.get("Time");

        drawBackground();
        drawBackButton();
        drawtryButton();
    }

    private void drawBackground() {
    	GImage background = new GImage("media/images/credits/bg.png", 0, 0); 
        background.setSize(1200, 800);
        gg.add(background);
        gg.add(new GImage("media/images/options/StoreRoulette-Base.png", 275, 160));
        GImage gameOverTitle = new GImage("media/images/GameOver/GameOver Frame.png", 400, 160);
        gameOverTitle.setSize(406,60);
        gg.add(gameOverTitle);
        gg.add(new GImage("media/images/GameOver/RedCircle.png", 510, 265));
        gg.add(new GImage("media/images/GameOver/BrokenHeart.png", 550, 300));
        gg.add(new GImage("media/images/GameOver/Ga me ov er.png", 404, 280));
        
        
        
    }
    
    private void drawBackButton() {
        gg.add(new Button("media/images/level/back.png",  40, 34).clicked((Button b) -> {
            backToMainMenu();
            return null;
        }));
        gg.add((new Button("media/images/credits/back2.png", 252+160, 514)).clicked((Button b) -> { backToMainMenu(); return null;}));
        
    }

    private Void backToMainMenu() {
        gg.displayScreen("index", null); // Return to Start Menu
        return null;
    }
    
    private void drawtryButton() {
    	gg.add((new Button("media/images/GameOver/TryAgain.png", 630, 514))
                .clicked((Button b) -> {
                    return RestartLevelButtonClicked(b);
                }));
    }
    
    private Void RestartLevelButtonClicked(Button button) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("Level", level);
        params.put("Character", character);
        gg.displayScreen("Playing", params); // Restart the current level
        return null;
    }
    
    @Override
    public String getName() {
        return name;
    }

	@Override
	protected void hide() {
		
	}
}
