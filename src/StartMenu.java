import java.util.HashMap;

import acm.graphics.GImage;

public class StartMenu extends Screen {

    private String name = "index";

	@Override
	public void show(HashMap<String, Object> params) {
		drawBackground();
		drawButtons();
	}

    private void drawBackground() {
        GImage image;
        image = new GImage("media/images/index/bg.png", 0, 0);
        image.setSize(1200, 800);
		gg.add(image);
    }

    private Void PlayButtonClicked(Button button)
    {
        gg.displayScreen("level", null);
        return null;
    }

    private Void CreditsButtonClicked(Button button)
    {
        gg.displayScreen("credits", null);
        return null;
    }

    private Void QuitButtonClicked(Button button)
    {
        gg.displayScreen("quit", null);
        return null;
    }

    private void drawButtons() {
		gg.add((new Button("media/images/index/play.png", 397, 474)).clicked((Button b) -> { return PlayButtonClicked(b); }));
		gg.add((new Button("media/images/index/sco.png", 397, 549)).clicked((Button b) -> { return PlayButtonClicked(b); }));
		gg.add((new Button("media/images/index/cre.png", 397, 605)).clicked((Button b) -> { return CreditsButtonClicked(b); }));
		gg.add((new Button("media/images/index/opt.png", 400, 679)).clicked((Button b) -> { return PlayButtonClicked(b); }));
		gg.add((new Button("media/images/index/quit.png", 633, 679)).clicked((Button b) -> { return QuitButtonClicked(b); }));
    }


    @Override
    public String getName() {
        return name;
    }

}
