import java.util.HashMap;

import acm.graphics.GImage;

public class PlayingScreen extends Screen {

    private String name = "Playing";

	@Override
	public void show(HashMap<String, Object> params) {
		
		int level = (int)params.get("Level");
		System.out.println(level);
		// TODO: draw something about the level
		
		drawBackground();
		drawButtons();
	}

    private void drawBackground() {
		gg.add(new GImage("media/images/playing/bg.png", 0, 0));
    }

    private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/playing/back.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));
    }


    @Override
    public String getName() {
        return name;
    }

}