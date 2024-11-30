import java.util.HashMap;

import acm.graphics.GImage;

public class GameOver extends Screen {

    private String name = "GameOver";
    private int level;
    private Character character;

    @Override
    public void show(HashMap<String, Object> params) {
        level = (int) params.get("Level");
        character = (Character) params.get("Character");

        drawBackground();
        drawGameOverTitle();
        drawButtons();
    }

    private void drawBackground() {
        GImage background = new GImage("media/images/gameover/bg.png", 0, 0);
        background.setSize(1200, 800);
        gg.add(background);
    }
    
    private void drawGameOverTitle() {
        GImage gameOverTitle = new GImage("media/images/gameover/GameOverTitle.png", 400, 100);
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
