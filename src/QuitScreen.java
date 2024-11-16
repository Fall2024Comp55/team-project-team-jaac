import java.util.HashMap;

import acm.graphics.GImage;

public class QuitScreen extends Screen {

    private String name = "Quit";

	@Override
	public void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}

    private void drawBackground() {
        GImage image;
        image = new GImage("media/images/quit/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
		gg.add(new GImage("media/images/quit/StoreRoulette-Base.png", 275, 190));
		gg.add(new GImage("media/images/quit/UI-SectionTitle-Primary-Framed.png", 394, 167));

    }

    private Void YesButtonClicked(Button button)
    {
    	System.exit(0);
        return null;
    }

    private Void BackButtonClicked(Button button)
    {
        gg.displayScreen("index", null);
        return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/quit/Group 4557.png", 40, 34)).clicked((Button b) -> { return BackButtonClicked(b); }));
		gg.add((new Button("media/images/quit/yes.png", 280+138, 195+361)).clicked((Button b) -> { return YesButtonClicked(b); }));
		gg.add((new Button("media/images/quit/no.png", 280+350, 195+361)).clicked((Button b) -> { return BackButtonClicked(b); }));
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
