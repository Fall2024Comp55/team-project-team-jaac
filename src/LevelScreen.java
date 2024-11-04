import java.util.HashMap;

import acm.graphics.GImage;

public class LevelScreen extends Screen {

    private String name = "Level";

	@Override
	public void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}

    private void drawBackground() {
        GImage image;
        image = new GImage("media/images/level/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
		gg.add(new GImage("media/images/level/title.png", 224, 41));
		gg.add(new GImage("media/images/level/level1updated.png", 350, 41));
		gg.add(new GImage("media/images/level/level2updated.png", 350, 190));
		gg.add(new GImage("media/images/level/level3updated.png", 350, 340));
    }

    private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/level/back.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));
		//gg.add((new Button("media/images/level/back2.png", 252+275, 544)).clicked((Button b) -> { return BackButtonClicked(b); }));
    }


    @Override
    public String getName() {
        return name;
    }

}
