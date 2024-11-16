import java.util.HashMap;

import acm.graphics.GImage;

public class CreditsScreen extends Screen {

    private String name = "Credits";

	@Override
	public void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}

    private void drawBackground() {
        GImage image;
        image = new GImage("media/images/credits/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
		gg.add(new GImage("media/images/credits/StoreRoulette-Base.png", 275, 160));
		gg.add(new GImage("media/images/credits/UI-SectionTitle-Primary-Framed.png", 394, 137));

    }

    private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/credits/back.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));
		gg.add((new Button("media/images/credits/back2.png", 252+275, 544)).clicked((Button b) -> { return BackButtonClicked(b); }));
    }


    @Override
    public String getName() {
        return name;
    }

	@Override
	protected void hide() {
		// noting to do
		
	}

}
