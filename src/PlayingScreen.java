import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class PlayingScreen extends Screen implements KeyListener {

    private String name = "Playing";
    private int level;
    private LevelInfo levelInfo;
    private CharacterInfo characterInfo;
    private Timer timer;
    private long lastTimeMs;
    private long startTimeMs;
    private GImage landscape;
    private GImage characterImage;
    private int landscapeY;
    private GCanvas road;
    private ArrayList<Vehicle> vehicles;
    private int passedVehicleCount;


    @Override
    public void show(HashMap<String, Object> params) {

        gg.getGCanvas().addKeyListener(this);

        level = (int) params.get("Level");
        levelInfo = LevelInfo.build(level);
        characterInfo = new CharacterInfo((Character) params.get("Character"), levelInfo.defaultLane);
        vehicles = new ArrayList<Vehicle>();
        passedVehicleCount = 0;

        MusicManager.getInstance().stopMusic(); //stop music once the player enters gameplay
        drawBackground();
        drawButtons();

        // Generated road
        GImage road = new GImage("media/images/playing/road.png");
        this.road = new GCanvas(road.getWidth(), road.getHeight());
        this.road.add(road, 0, 0);
        gg.getGCanvas().add(this.road, 74, 100);

        // Generated landscape map
        BufferedImage srcLandscape;
		try {
			srcLandscape = ImageIO.read(new File(levelInfo.landscapeFile));
		} catch (IOException e) {
			return;
		}
        BufferedImage landscape = new BufferedImage(srcLandscape.getWidth(), srcLandscape.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics g = landscape.getGraphics();
        g.drawImage(srcLandscape, 0, 0, null);
        g.drawImage(srcLandscape, 0, srcLandscape.getHeight(), null);
        g.dispose();
        this.landscape = new GImage(landscape);
        this.road.add(this.landscape, 0, 0);

        //level 2 images
        if (level == 2) {
            GImage additionalImage = new GImage("media/images/playing/Level2ability.png");
            additionalImage.setLocation(250, 33);
            gg.getGCanvas().add(additionalImage);
        }

        // init vehicle
        for (int i = 0; i < levelInfo.laneX.length; i++) {
            Vehicle v = Vehicle.random(-this.road.getHeight(), 0, i);
            vehicles.add(v);
            this.road.add(v.getImage(), levelInfo.laneX[i] - v.getImage().getWidth() / 2, v.getY());
        }

        // init character
        characterImage = characterInfo.getModel();
        this.road.add(characterImage,
                      levelInfo.laneX[levelInfo.defaultLane] - characterImage.getWidth() / 2,
                      characterInfo.getY());

        // 50 fps
        landscapeY = 0;
        lastTimeMs = System.currentTimeMillis();
        startTimeMs = lastTimeMs;
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
            }
        });
        timer.start();
    }

      //checks if level is complete based passed vehicle count
      private void checkLevelComplete() {

          if (passedVehicleCount >= levelInfo.requirement) {

              double elapsedTime = (System.currentTimeMillis() - startTimeMs) / 1000.0;

              HashMap<String, Object> params = new HashMap<>();
              params.put("Level", level);
              params.put("Character", characterInfo.getCharacter());
              params.put("Time", elapsedTime);
              gg.displayScreen("Complete", params); // `timer.stop();` can be called by this line
          }
      }


    private void updateAnimation() {
        long timerDelayMs = System.currentTimeMillis() - lastTimeMs;
        lastTimeMs = System.currentTimeMillis();

        // move landscape
        landscapeY += levelInfo.speed * timerDelayMs;

        // generate vehicle
        int laneNum = levelInfo.laneX.length;
        int[] laneMinY = new int[laneNum];
        for (int i = 0; i < laneNum; i++) {
            laneMinY[i] = (int) road.getHeight();
        }
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getY() < laneMinY[vehicle.getLane()]) {
                laneMinY[vehicle.getLane()] = vehicle.getY();
            }
        }
        for (int i = 0; i < laneNum; i++) {
            if (laneMinY[i] > 0) {
                Vehicle newVehicle = Vehicle.random((int) (-this.road.getHeight() / levelInfo.density), 0, i);
                newVehicle.move((int) -newVehicle.getImage().getHeight());
                vehicles.add(newVehicle);
                this.road.add(newVehicle.getImage(), levelInfo.laneX[i] - newVehicle.getImage().getWidth() / 2, newVehicle.getY());
            }
        }

        // move vehicle
        ArrayList<Integer> deletedVehiclesIndex = new ArrayList<Integer>();
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle v = vehicles.get(i);
            v.move((int) (levelInfo.speed * timerDelayMs));
            if (v.getY() > landscape.getHeight() / 2) {
                deletedVehiclesIndex.add(i);
            } else {
                int x = levelInfo.laneX[v.getLane()];
                v.getImage().setLocation(x - v.getImage().getWidth() / 2, v.getY());
            }
        }

        // delete passed vehicle
        for (int i = 0; i < deletedVehiclesIndex.size(); i++) {
            //System.out.println("remove " + passedVehicleCount);
            this.road.remove(vehicles.get((int)deletedVehiclesIndex.get(i)).getImage());
            vehicles.remove((int)deletedVehiclesIndex.get(i));
            passedVehicleCount++;
        }

        // draw Character
        int lane1X = levelInfo.laneX[characterInfo.getLane1()];
        int lane2X = levelInfo.laneX[characterInfo.getLane2()];
        this.characterImage.setLocation((lane1X + lane2X) / 2.0 - characterImage.getWidth() / 2, characterInfo.getY());

        while (landscapeY >= 0) {
            landscapeY = (int) (landscapeY - landscape.getHeight() / 2);
        }
        this.landscape.setLocation(0, landscapeY);

        detectCollision();
        checkLevelComplete();
    }

    private void detectCollision() {
        // collision detection
        for (Vehicle v : vehicles) {
            if (v.getImage().getBounds().intersects(this.characterImage.getBounds())) {
                // failed
                HashMap<String, Object> params = new HashMap<>();
                //params.put("Level", level);
                //params.put("Character", characterInfo.getCharacter());
                // TODO: Show failed screen
                gg.displayScreen("index", params);
            }
        }
    }

    private void drawBackground() {
        gg.add(new GImage("media/images/playing/bg.png", 0, 0));
        gg.add(new GImage("media/images/playing/livebg.png", 361, 32));
        gg.add(new GImage("media/images/playing/timebg.png", 576, 32));
        gg.add(new GImage("media/images/playing/passedbg.png", 786, 32));
    }

    private Void BackButtonClicked(Button button) {
        gg.displayScreen("index", null);
        return null;
    }

    private void drawButtons() {
        gg.add((new Button("media/images/playing/back.png", 40, 34)).clicked((Button b) -> {
            return BackButtonClicked(b);
        }));
    }

    @Override
    public String getName() {
        return name;
    }

	@Override
	protected void hide() {
		timer.stop();
        gg.getGCanvas().remove(road);
        gg.getGCanvas().removeKeyListener(this);
	}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (characterInfo == null) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (characterInfo.getLane1() != characterInfo.getLane2()) {
                    characterInfo.setLane2(characterInfo.getLane1());
                } else {
                    characterInfo.setLane1(Math.max(characterInfo.getLane1() - 1, 0));
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (characterInfo.getLane1() != characterInfo.getLane2()) {
                    characterInfo.setLane1(characterInfo.getLane2());
                } else {
                    characterInfo.setLane2(Math.min(characterInfo.getLane2() + 1, levelInfo.laneX.length - 1));
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}