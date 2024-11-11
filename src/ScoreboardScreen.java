import java.util.HashMap;
import acm.graphics.GImage;
import acm.graphics.GLabel;

public class ScoreboardScreen extends Screen {

    private String name = "Scoreboard";

	@Override
	public void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}

    private void drawBackground() {
        GImage image;
        image = new GImage("media/images/scoreboard/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
		gg.add(new GImage("media/images/scoreboard/scoreboardRecord.png", 275, 108));
		gg.add(new GImage("media/images/scoreboard/Title.png", 394, 85));

    }

    private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/scoreboard/back.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));
		gg.add((new Button("media/images/scoreboard/back2.png", 252+275, 673)).clicked((Button b) -> { return BackButtonClicked(b); }));
    }

    private void displayScores() {
    	PlayerData playerdata = PlayerData.getInstance();
    	int yPosition = 150; 
    	
    	for(int level = 1; level <=3; level++) {
    		PlayerData.LevelScore score = playerdata.getBestScore(level);
    		
    		if(score != null) {
    			String timeText = String.valueOf(score.timeMs);
    			String carsPassedText = String.valueOf(score.carPassed);
    			
    			GLabel timeLabel = new GLabel(timeText, 400, yPosition); // will need to adjust later
    			GLabel carsPassedLabel = new GLabel(carsPassedText, 500, yPosition);
    			
    			gg.add(timeLabel);
    			gg.add(carsPassedLabel);
    		}
    	}
    }

    @Override
    public String getName() {
        return name;
    }
}
