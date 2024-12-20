import java.awt.Color;
import java.util.HashMap;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import java.awt.Font;
import acm.graphics.GCanvas;

public class LevelComplete extends Screen {


    private String name = "Complete";
    private int level;
    private LevelInfo levelInfo;
    private Character character;
    private long completionTimeMs;
    

    private void drawLevelCompleteTitle() {
        gg.add(new GImage("media/images/levelcomplete/LevelCompleteTitle.png", 398, 137));
    }

	@Override
	public void show(HashMap<String, Object> params) {

		level = (int) params.get("Level");
		character = (Character) params.get("Character");
		completionTimeMs = (long) params.get("Time");
		levelInfo = LevelInfo.build(level);
		
		// Refresh best score
		PlayerData.LevelScore bestls = PlayerData.getInstance().getBestScore(level);
		PlayerData.LevelScore ls = new PlayerData.LevelScore();
		ls.timeMs = completionTimeMs;
		ls.carPassed = levelInfo.requirement;
		ls.star = 3;	// TODO
		if (bestls == null || bestls.timeMs > ls.timeMs) {
			PlayerData.getInstance().setBestScore(level, ls);
		}

		drawBackground();
		drawEachLevelComplete();
		drawStars(level);
		drawLevelCompleteTitle();
		drawButtons();
	}

	private void drawBackground() {
        GImage image;
        image = new GImage("media/images/levelcomplete/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
		
    }
	
	private String Time (long timeMs) {
	    long totalSeconds = timeMs / 1000;
	    long minutes = totalSeconds / 60;
	    long seconds = totalSeconds % 60;
	    long milliseconds = (timeMs % 1000) / 10; 

	    return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
	}

	private void drawEachLevelComplete() {
		int imageX = 275;
		int imageY = 160;

		GImage levelimage = null;
		switch(level) {
			case 1:
				levelimage = new GImage("media/images/levelcomplete/complete1NoStars.png", imageX, imageY);
				break;
			case 2:
				levelimage = new GImage("media/images/levelcomplete/complete2NoStars.png", imageX, imageY);
				break;
			case 3:
				levelimage = new GImage("media/images/levelcomplete/complete3NoStars.png", imageX, imageY);
				break;
			}
		
				gg.add(levelimage);
		
				String Time = Time(completionTimeMs);
				GLabel timeLabel = new GLabel(Time, 463, 458);
				timeLabel.setFont("Arial-Bold-20");
		        timeLabel.setColor(Color.WHITE);
				gg.add(timeLabel);
	
			    String carsPassed = levelInfo.requirement + "/" + levelInfo.requirement;
			    GLabel carsPassedLabel = new GLabel(carsPassed, 735, 458);  
			    carsPassedLabel.setFont("Arial-Bold-20");
			    carsPassedLabel.setColor(Color.WHITE);
			    gg.add(carsPassedLabel);
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
			if(diff >= 2500) {
				stars--;
				diff -= 2500;
			}
		}
		
		return stars;
	}
	
	 private void drawStars(int level) {
    	
		 int light = calcStars(level);
		 int dark = 6 - light;
    		
    		int y = 127+210;
    		int x = 172+225;
    		for (int i = 0; i < light; i++) {
        		gg.add(new GImage("media/images/level/star_light.png", x, y));
        		x += 68;
    		}
    		for (int i = 0; i < dark; i++) {
        		gg.add(new GImage("media/images/level/star_dark.png", x, y));
        		x += 68;
    		}
    	}
     

    private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }

    private Void NextButtonClicked(Button button) {

    	if (LevelInfo.build(level + 1) != null) {
    	HashMap<String, Object> params = new HashMap<>();
    	params.put("Level", level + 1);
    	params.put("Character", character);
    	gg.displayScreen("Playing", params);

    	}
    	return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/levelcomplete/back.png", 40, 34))
				.clicked((Button b) -> {  return BackButtonClicked(b); }));

		if (level == 3) {
			gg.add((new Button("media/images/levelcomplete/back2.png", 537, 525)) 
					.clicked((Button b) -> { return BackButtonClicked(b); }));
		} else {
			gg.add((new Button("media/images/levelcomplete/back2.png", 418, 525))
					.clicked((Button b) -> { return BackButtonClicked(b); }));

		 if (level == 1 || level == 2)  {
	            gg.add((new Button("media/images/levelcomplete/NextButton.png", 629, 525))
	            		.clicked((Button b) -> { return NextButtonClicked(b); }));

	        gg.add((new Button("media/images/levelcomplete/Frame.png", 658, 532))
	        		.clicked((Button b) -> { return NextButtonClicked(b); }));
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