import java.util.HashMap;
import acm.graphics.GImage;
import acm.graphics.GLabel;

public class ScoreboardScreen extends Screen {

    private String name = "Scoreboard";

	@Override
	public void show(HashMap<String, Object> params) {
		drawBackground();
	    displayScores();
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
    
    private String tempTime(long timeMs) {
        long seconds = timeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void displayScores() {
    	PlayerData playerdata = PlayerData.getInstance();
    	int yPosition = 289; 
    	int xPosition = 289; 
    	
    	for(int level = 1; level <=3; level++) {
    		PlayerData.LevelScore bestScores = playerdata.getBestScore(level);
    		
    		if(bestScores != null) {
    			String timeText = tempTime(bestScores.timeMs);
    			String carsPassedText = String.valueOf(bestScores.carPassed);
    			
    			GLabel timeLabel = new GLabel(timeText, 490, yPosition);
    			 timeLabel.setFont("Arial-Bold-20");
                 timeLabel.setColor(java.awt.Color.WHITE);
                 
    			GLabel carsPassedLabel = new GLabel(carsPassedText, 805, xPosition);
    			 carsPassedLabel.setFont("Arial-Bold-20");
                 carsPassedLabel.setColor(java.awt.Color.WHITE);
    			
    			gg.add(timeLabel);
    			gg.add(carsPassedLabel);
    			
    			yPosition += 156;
    			xPosition += 156;
    		}
    	}
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
	protected void hide() {
		
	}
}
