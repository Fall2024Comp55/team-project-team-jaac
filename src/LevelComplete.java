import java.util.HashMap;

import acm.graphics.GImage;

public class LevelComplete extends Screen {
	

    private String name = "Complete";
    private int level;
    private LevelInfo levelInfo;
    private String character;
    private double completionTime;
    private int health;
    
	@Override
	public void show(HashMap<String, Object> params) {
		
		 level = (int) params.get("Level");
		 character = (String) params.get("Character");
		 completionTime = (double) params.get("Time");
		 levelInfo = LevelInfo.build(level);
		
		drawBackground();
		drawButtons();
	}

	private void drawBackground() {
        GImage image;
        image = new GImage("media/images/levelcomplete/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
		gg.add(new GImage("media/images/levelcomplete/LevelComplete.png", 275, 160));
		gg.add(new GImage("media/images/levelcomplete/LevelCompleteTitle.png", 398, 137));

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
        gg.displayScreen("Index", null);
        return null;
    }

    private Void NextButtonClicked(Button  button) {
    	
    	if (LevelInfo.build(level + 1) != null) {
    	HashMap<String, Object> params = new HashMap<>();
    	params.put("Level", level + 1);
    	params.put("Character", character);
    	gg.displayScreen("Playing", params);
    	
    	}
    	return null;
    } 
    
    private void drawButtons() {
		gg.add((new Button("media/images/levelcomplete/back.png", 40, 34)).clicked((Button b) -> {  return BackButtonClicked(b); }));
		gg.add((new Button("media/images/levelcomplete/back2.png", 418, 525)).clicked((Button b) -> { return BackButtonClicked(b); }));
		//gg.add((new Button("media/images/Complete/NextButton.png", 252+275, 544)).clicked((Button b) -> { return NextButtonClicked(b); }));
		//gg.add((new Button("media/images/levelcomplete/Frame.png", 629, 525)).clicked((Button b) -> { return NextButtonClicked(b); }));
		
		if (LevelInfo.build(level + 1) != null) {
	            gg.add((new Button("media/images/levelcomplete/NextButton.png", 629, 525))
	            		.clicked((Button b) -> { return NextButtonClicked(b); })); 
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