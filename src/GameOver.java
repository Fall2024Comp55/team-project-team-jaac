import java.util.HashMap;

import acm.graphics.GImage;

public class GameOver extends Screen {

    private String name = "GameOver";
    private int level;
    private Character character;
    private double completionTime;

    @Override
    public void show(HashMap<String, Object> params) {
        level = (int) params.get("Level");
        character = (Character) params.get("Character");
        completionTime = (double) params.get("Time");

        drawBackground();
        drawGameOverTitle();
        drawButtons();
    }

    private void drawBackground() {
    	GImage image;
        image = new GImage("media/images/levelcomplete/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
    }
    
    private void drawGameOverTitle() {
        GImage gameOverTitle = new GImage("media/images/GameOver/GameOver Frame.png", 275, 160);
        gg.add(gameOverTitle);
    }
    
    private void drawButtons() {
    
    }
    
    @Override
    public String getName() {
        return name;
    }

	@Override
	protected void hide() {
		
	}
}
