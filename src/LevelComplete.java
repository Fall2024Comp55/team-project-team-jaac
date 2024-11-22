import java.util.HashMap;

import acm.graphics.GImage;

public class LevelComplete extends Screen {
	

    private String name = "Complete";
    private int level;
    private LevelInfo levelInfo;
    private String character;
    private double completionTime;
    //private int health;
    
    private void drawLevelCompleteTitle() {
        gg.add(new GImage("media/images/levelcomplete/LevelCompleteTitle.png", 398, 137));
    }
    
	@Override
	public void show(HashMap<String, Object> params) {
		
		 level = (int) params.get("Level");
		 character = (String) params.get("Character");
		 completionTime = (double) params.get("Time");
		 levelInfo = LevelInfo.build(level);
		
		drawBackground();
		drawEachLevelComplete();
		drawLevelCompleteTitle();
		drawButtons();
	}
	
	private void drawBackground() {
        GImage image;
        image = new GImage("media/images/levelcomplete/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
    }
	
	private void drawEachLevelComplete() {
		int imageX = 275;
		int imageY = 160;
				
		GImage levelimage = null;
		switch(level) {
			case 1: 
				levelimage = new GImage("media/images/levelcomplete/LevelComplete.png", imageX, imageY);
				break;
			case 2: 
				levelimage = new GImage("media/images/levelcomplete/Level2complete.png", imageX, imageY);
				break;
			case 3: 
				levelimage = new GImage("media/images/levelcomplete/Level3complete.png", imageX, imageY);
				break;
			}		
				gg.add(levelimage);	
		}

	
/*	 private String scores() {
	        int points = 0;
	        points += health * 3;
	        if (completionTime < 30) points += 3;
	        else if (completionTime < 45) points += 2;
	        else if (completionTime < 60) points += 1;
	        return character;
	        
	 } */

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
			gg.add((new Button("media/images/levelcomplete/back2.png", 257, 361)) //might need to adjust this later 
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