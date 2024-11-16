import java.util.HashMap;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GObject;
import java.awt.Color;

public class OptionsMenu extends Screen{
	private String name = "Options";

    private GLabel volumeLabel;
    private GLabel volumeValue;
    private GRect volumeBar;
    private int volume = 100; // Default volume set to 100

    @Override
    public void show(HashMap<String, Object> params) {
        drawBackground();
        drawVolumeControl();
        drawControls();
        drawBackButton();
    }

    private void drawBackground() {
        GImage background = new GImage("media/images/credits/bg.png", 0, 0); 
        background.setSize(1200, 800);
        gg.add(background);
        gg.add(new GImage("media/images/options/StoreRoulette-Base.png", 275, 160));
        gg.add(new GImage("media/images/options/UI-SectionTitle-Primary-Framed.png", 394, 137));
        gg.add(new GImage("media/images/options/Group 14315.png", 311,210));
        gg.add(new GImage("media/images/options/Card Title.png",320, 225));
        
    }
    


    private void drawVolumeControl() {
        // Volume Label
//        volumeLabel = new GLabel("MASTER VOLUME", 380, 305);
//        volumeLabel.setFont("Arial-Bold-24");
//        gg.add(volumeLabel);
        

        // Volume Value Display
        volumeValue = new GLabel(String.valueOf(volume), 798, 238);
        volumeValue.setFont("Arial-Bold-22");
        volumeValue.setColor(Color.WHITE);
        gg.add(volumeValue);


        // Volume increase and decrease buttons
        gg.add(new Button("media/images/options/vol_down.png", 750, 215).clicked((Button b) -> {
            decreaseVolume();
            return null;
        }));
        gg.add(new Button("media/images/options/vol_up.png", 850, 215).clicked((Button b) -> {
            increaseVolume();
            return null;
        }));
    }

    private void decreaseVolume() {
        if (volume > 0) {
            volume -= 10;
            volumeValue.setLabel(String.valueOf(volume));
            //volumeBar.setSize(volume * 3, 20); // Scale the bar size with volume
        }
    }

    private void increaseVolume() {
        if (volume < 100) {
            volume += 10;
            volumeValue.setLabel(String.valueOf(volume));
            //volumeBar.setSize(volume * 3, 20); // Scale the bar size with volume
        }
    }

    private void drawControls() {
        // Control Labels
    	GLabel mLeft = new GLabel("Move Left", 382, 312);
    	mLeft.setColor(Color.WHITE);  // Set the color to white
    	mLeft.setFont("SansSerif-16");
    	gg.add(mLeft);
    	
    	GLabel mRight = new GLabel("Move Right", 765, 390);
    	mRight.setColor(Color.WHITE);
    	mRight.setFont("SansSerif-16");
    	gg.add(mRight);
    	
    	GLabel plusSpeed = new GLabel("Increase Speed", 765, 322);
    	plusSpeed.setColor(Color.WHITE); 
    	plusSpeed.setFont("SansSerif-16");
    	gg.add(plusSpeed);
    	
    	GLabel deductSpeed = new GLabel("Deduct Speed", 348, 390);
    	deductSpeed.setColor(Color.WHITE);  
    	deductSpeed.setFont("SansSerif-16");
    	gg.add(deductSpeed);
    	
    	GLabel activateSpecial = new GLabel("Activate Special Ability", 717, 480);
    	activateSpecial.setColor(Color.WHITE);  
    	activateSpecial.setFont("SansSerif-16");
    	gg.add(activateSpecial);
    	
    	

        // Control Buttons (using placeholder arrow images)
        // Arrow left
        gg.add(new GImage("media/images/options/arrow_left.png", 501, 355));
        gg.add(new GImage("media/images/options/Line 7.png", 474, 308));// line for arrow left
        
        
        //Arrow right
        gg.add(new GImage("media/images/options/arrow_right.png", 634, 355));
        gg.add(new GImage("media/images/options/Line 5.png", 704, 385));// line for arrow right
        
        //Arrow up
        gg.add(new GImage("media/images/options/arrow_up.png", 568, 285));
        gg.add(new GImage("media/images/options/Line 4.png", 645, 315)); // line for arrow up
        
        //Arrow down
        gg.add(new GImage("media/images/options/arrow_down.png", 470, 310));
        gg.add(new GImage("media/images/options/Line 9.png", 472, 430));// line for arrow down
        
        //Space bar
        gg.add(new GImage("media/images/options/space_bar_ability.png", 411, 452)); // Special ability button
        gg.add(new GImage("media/images/options/Line 8.png", 655, 472));// line Special ability
    }

    private void drawBackButton() {
        gg.add(new Button("media/images/level/back.png",  40, 34).clicked((Button b) -> {
            backToMainMenu();
            return null;
        }));
        gg.add((new Button("media/images/credits/back2.png", 252+275, 544)).clicked((Button b) -> { backToMainMenu(); return null;}));
    }

    private Void backToMainMenu() {
        gg.displayScreen("index", null); // Return to Start Menu
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

	@Override
	protected void hide() {
		// // noting to do
		
	}

}
