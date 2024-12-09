import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
    private GImage abilityImage;
    private int landscapeY;
    private GCanvas road;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Trap> traps;
    private int passedVehicleCount;
    private ArrayList<GImage> healthImages;
    private GLabel passedText;
    private GLabel timeDisplayLabel;
    private GLabel laneChangeTimeText;
    private GImage laneChangeTimebg;

    private long lastTakingUpLineStartTimeMs;
    private int lastLane;

    private long abilityStartTimeMs;
    
    private String trapImagePath;
    private String trapEffectPath;


    @Override
    public void show(HashMap<String, Object> params) {

        gg.getGCanvas().addKeyListener(this);

        level = (int) params.get("Level");
        levelInfo = LevelInfo.build(level);
        characterInfo = new CharacterInfo((Character) params.get("Character"), levelInfo.defaultLane);
        vehicles = new ArrayList<Vehicle>();
        traps = new ArrayList<>();
        passedVehicleCount = 0;
        lastTakingUpLineStartTimeMs = -1;
        
        // Set trap image path based on the level
        if (level == 2) {
            trapImagePath = "media/images/playing/Traps/TumbleWeed1.png"; // Trap image for level 2
        } else if (level == 3) {
            trapImagePath = "media/images/playing/Traps/RainPuddle2.png"; // Trap image for level 3
        }
        if (level == 2) {
            trapEffectPath = "media/images/playing/Traps/SandEffect2.png"; // Trap image for level 2
        } else if (level == 3) {
            trapEffectPath = "media/images/playing/Traps/RainEffect2.png"; // Trap image for level 3
        }

        MusicManager.getInstance().stopMusic(); //stop background music once the player enters gameplay
        drawBackground();
        drawButtons();

        // draw health image
        healthImages = new ArrayList<GImage>();

        if (characterInfo.getCharacter() == Character.Nate ) {
        	for (int i = 0; i < characterInfo.getHealth(); i++) {
                gg.add(new GImage("media/images/playing/live-dark.png", 368 + i * 50, 40));
                GImage light = new GImage("media/images/playing/live-light.png", 368 + i * 50, 40);
                healthImages.add(light);
                gg.add(light);
            }
        } else {
            for (int i = 0; i < characterInfo.getHealth(); i++) {
                gg.add(new GImage("media/images/playing/live-dark.png", 390 + i * 54, 40));
                GImage light = new GImage("media/images/playing/live-light.png", 390 + i * 54, 40);
                healthImages.add(light);
                gg.add(light);
            }
        }

        // draw passedText
        passedText = new GLabel("Passed  0/" + levelInfo.requirement, 812, 62);
        passedText.setFont("Arial-Bold-22");
        passedText.setColor(Color.WHITE);
		gg.add(passedText);

		// Time count text
        timeDisplayLabel = new GLabel("Time: 00.00.00", 590, 62);
        timeDisplayLabel.setFont("Arial-Bold-20");
        timeDisplayLabel.setColor(Color.WHITE);
        gg.add(timeDisplayLabel);
        
        // 3s Timer Remind bg
        laneChangeTimebg = new GImage("media/images/playing/timebg.png", 500, 744); //might need to adjust 
        laneChangeTimebg.setVisible(false);
        gg.add(laneChangeTimebg);
        
        // Lane Change: 3s Timer Remind
        laneChangeTimeText = new GLabel("", 515, 774);
        laneChangeTimeText.setFont("Arial-Bold-19");
        laneChangeTimeText.setColor(Color.WHITE);
        gg.add(laneChangeTimeText);

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

        // init ability
        switch (characterInfo.getCharacter()) {
            case Character.Steve:
                abilityImage = null;
                break;
            case Character.Gary:
                // Press space for 3 seconds to ignore and pass through all vehicles without taking damage
                abilityImage = new GImage("media/images/playing/GaryAbility.png", 250, 33);
                break;
            case Character.Nate:
                // Press space for 5 seconds to crash all vehicles without slowing down but taking damage
                // TODO: change image
                abilityImage = new GImage("media/images/playing/GaryAbility.png", 250, 33);
                break;
            default:
                break;
        }
        if (abilityImage != null) {
            gg.add(abilityImage);
            abilityImage.setVisible(false);
        }
        abilityStartTimeMs = -1;

        // init vehicle
        for (int i = 0; i < levelInfo.laneX.length; i++) {
            Vehicle v = Vehicle.random(
                -this.road.getHeight(),
                0, i,
                characterInfo.getCharacter() == Character.Steve ? 0 : levelInfo.densityOfAbility,
                levelInfo.densityOfHealth);
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

              long elapsedTimeMs = System.currentTimeMillis() - startTimeMs;

              HashMap<String, Object> params = new HashMap<>();
              params.put("Level", level);
              params.put("Character", characterInfo.getCharacter());
              params.put("Time", elapsedTimeMs);
              gg.displayScreen("Complete", params); // `timer.stop();` can be called by this line
          }
      }


    private void updateAnimation() {
        long timerDelayMs = System.currentTimeMillis() - lastTimeMs;
        lastTimeMs = System.currentTimeMillis();

        // Time count
        double elapsedTime = (System.currentTimeMillis() - startTimeMs) / 1000.0;
        if (timeDisplayLabel != null) {
        	timeDisplayLabel.setLabel(String.format("Time: %02d:%02d:%02d",
        		    (int)(elapsedTime / 60),
        		    (int)(elapsedTime % 60),
        		    (int)((elapsedTime * 100) % 100)
        		));
        }

        // check ability state
        if (abilityStartTimeMs > 0) {
            if (characterInfo.getCharacter() == Character.Gary && System.currentTimeMillis() - abilityStartTimeMs > 3000) {
                abilityStartTimeMs = -1;    // ability is over
            } else if (characterInfo.getCharacter() == Character.Nate && System.currentTimeMillis() - abilityStartTimeMs > 5000) {
                abilityStartTimeMs = -1;     // ability is over
            }
        }

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
                Vehicle newVehicle = Vehicle.random(
                    (int) (-this.road.getHeight() / levelInfo.density),
                    0, i,
                    characterInfo.getCharacter() == Character.Steve ? 0 : levelInfo.densityOfAbility,
                    levelInfo.densityOfHealth);
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
            Vehicle deletedVehicle = vehicles.get((int)deletedVehiclesIndex.get(i));
            this.road.remove(deletedVehicle.getImage());
            vehicles.remove((int)deletedVehiclesIndex.get(i));
            if (deletedVehicle.getType() != Vehicle.VehicleType.ABILITY && deletedVehicle.getType() != Vehicle.VehicleType.HEALTH) {
                passedVehicleCount++;
            }
        }

        // draw passedText
        passedText.setLabel("Passed  " + passedVehicleCount + "/" + levelInfo.requirement);

        // check for taking up line timeout
        if (lastTakingUpLineStartTimeMs > 0) {
        	
        	//3s timer remind
        	long remainingTime = 3000 - (System.currentTimeMillis() - lastTakingUpLineStartTimeMs);
        	if(remainingTime > 0) {
        		
        		int countdownNumber = (int)(remainingTime / 1000) + 1; 
        			
        		//3s timer display 
        		laneChangeTimeText.setLabel(String.format ("Time Remaining: " + countdownNumber)); 
            	laneChangeTimeText.setVisible(true);
            	laneChangeTimebg.setVisible(true);	
        	}
        	
            if (System.currentTimeMillis() - lastTakingUpLineStartTimeMs > 3000) {
                System.out.println("Timeout!!!");
                characterInfo.setLane1(lastLane);
                characterInfo.setLane2(lastLane);
                lastTakingUpLineStartTimeMs = -1;
                
                laneChangeTimeText.setVisible(false); //3s timer remind
                laneChangeTimeText.setLabel("");
                laneChangeTimebg.setVisible(false);
            }
        } else {
        	
        	laneChangeTimeText.setVisible(false);
        	laneChangeTimeText.setLabel("");
        	laneChangeTimebg.setVisible(false);
        }

        // draw Character
        int lane1X = levelInfo.laneX[characterInfo.getLane1()];
        int lane2X = levelInfo.laneX[characterInfo.getLane2()];
        this.characterImage.setLocation((lane1X + lane2X) / 2.0 - characterImage.getWidth() / 2, characterInfo.getY());

        while (landscapeY >= 0) {
            landscapeY = (int) (landscapeY - landscape.getHeight() / 2);
        }
        this.landscape.setLocation(0, landscapeY);

        if (!(characterInfo.getCharacter() == Character.Gary && abilityStartTimeMs >= 0)) {// Press space for 3 seconds to ignore and pass through all vehicles without taking damage
            detectCollision();
        }
        checkLevelComplete();
        
        //Generate traps
        if (level == 2 || level == 3) {
            for (int i = 0; i < levelInfo.laneX.length; i++) {
                if (Math.random() < 0.005) { // Adjust probability for trap generation
                    Trap newTrap = new Trap(
                        trapImagePath,
                        levelInfo.laneX[i] - 50, // Center the trap in the lane
                        (int) (-this.road.getHeight() / levelInfo.density),
                        i
                    );
                    traps.add(newTrap);
                    this.road.add(newTrap.getImage());
                }
            }
        }
        
        //Move traps
        for (int i = traps.size() - 1; i >= 0; i--) {
            Trap trap = traps.get(i);
            trap.move((int) (levelInfo.speed * timerDelayMs));
            if (trap.getY() > landscape.getHeight() / 2) {
                this.road.remove(trap.getImage());
                traps.remove(i);
            }
        }
        
    }
    
    
    

    private void detectCollision() {
    	//trap collision detection
    	for (int i = traps.size() - 1; i >= 0; i--) {
            Trap trap = traps.get(i);
            if (trap.getImage().getBounds().intersects(this.characterImage.getBounds())) {
                // Trigger fade-out effect
                createFadeOutEffect(trap.getImage().getX(), trap.getImage().getY());

                // Remove the trap
                this.road.remove(trap.getImage());
                traps.remove(i);
            }
    	}
                
    	
        // collision detection
        int removeIndex = -1;
        for (Vehicle v : vehicles) {
            if (v.getImage().getBounds().intersects(this.characterImage.getBounds())) {
                // happened
                removeIndex = vehicles.indexOf(v);

                if (v.getType() == Vehicle.VehicleType.HEALTH) {
                	if (characterInfo.getHealth() < healthImages.size()) {
                        healthImages.get(characterInfo.getHealth()).setVisible(true);
                        characterInfo.setHealth(characterInfo.getHealth() + 1);
                    }
                } else if (v.getType() == Vehicle.VehicleType.ABILITY) {
                    if (abilityImage != null) {
                        abilityImage.setVisible(true);
                    }
                } else {
                    if (!(characterInfo.getCharacter() == Character.Nate && abilityStartTimeMs >= 0)) {// Press space for 5 seconds to crash all vehicles without slowing down but taking damage
                        characterInfo.setHealth(characterInfo.getHealth() - 1);
                        healthImages.get(characterInfo.getHealth()).setVisible(false);
                    }
                }

                if (characterInfo.getHealth() <= 0) {
                	if (timer != null) {
                        timer.stop();
                    }
                	double elapsedTime = (System.currentTimeMillis() - startTimeMs) / 1000.0;
                    // failed
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("Level", level);
                    params.put("Character", characterInfo.getCharacter());
                    params.put("Time", elapsedTime);
                    // TODO: Show failed screen
                    gg.displayScreen("GameOverScreen", params);
                }

                break;
            }
        }

        if (removeIndex >= 0) {
            this.road.remove(vehicles.get(removeIndex).getImage());
            vehicles.remove(removeIndex);
        }  
        
    }
    
    private BufferedImage adjustImageAlpha(Image img, double alpha) {
        // Convert the Image to a BufferedImage
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Adjust the alpha for each pixel
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int argb = bufferedImage.getRGB(x, y);
                int a = (int) (((argb >> 24) & 0xFF) * alpha); // Scale alpha
                int rgb = argb & 0xFFFFFF; // Preserve RGB values
                bufferedImage.setRGB(x, y, (a << 24) | rgb);
            }
        }

        return bufferedImage;
    }
    
    private void createFadeOutEffect(double x, double y) {
        if (trapEffectPath == null) {
            return; // No fade-out effect if path is not set
        }
        
        // Calculate the center of the screen
        double centerX = gg.getGCanvas().getWidth() / 2.0;
        double centerY = gg.getGCanvas().getHeight() / 2.0;

        // Create the fade-out image at the collision location
        GImage fadeOutImage = new GImage(trapEffectPath, x, y);
        fadeOutImage.setSize(900, 450);
        fadeOutImage.setLocation(centerX + 50 - fadeOutImage.getWidth() / 2, centerY -100 - fadeOutImage.getHeight() / 2);
        this.road.add(fadeOutImage);
        fadeOutImage.sendToFront();
        

        // Timer to gradually fade out the image
        Timer fadeOutTimer = new Timer(50, new ActionListener() {
            private int fadeStep = 20; // Reduce opacity in steps

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fadeStep <= 0) {
                    gg.getGCanvas().remove(fadeOutImage); // Remove the image once it is fully faded
                    ((Timer) e.getSource()).stop(); // Stop the timer
                } else {
                	fadeOutImage.setImage(adjustImageAlpha(fadeOutImage.getImage(), fadeStep / 10.0));
                    fadeStep--;
                }
            }
        });

        fadeOutTimer.start();
    }
    
    
    
    

    private void drawBackground() {
        gg.add(new GImage("media/images/playing/bg.png", 0, 0));
        gg.add(new GImage("media/images/playing/livebg.png", 361, 32));
        gg.add(new GImage("media/images/playing/timebg.png", 576, 32));
        gg.add(new GImage("media/images/playing/passedbg.png", 786, 32));
        gg.add(new GImage("media/images/playing/timebutton.png", 730, 36));
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
	protected void hide() { //Ensure that timer.stop() is always called when the screen is hidden or gameplay ends
		if(timer != null) { // if statement added for the timer
			timer.stop();
		}
		
		if (laneChangeTimeText != null) {
			gg.remove(laneChangeTimeText);
		}
		
		if (laneChangeTimebg != null) {
			gg.remove(laneChangeTimebg);
		}

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
                    lastTakingUpLineStartTimeMs = -1;
                } else if (characterInfo.getLane1() - 1 >= 0){
                    lastLane = characterInfo.getLane1();
                    characterInfo.setLane1(characterInfo.getLane1() - 1);
                    lastTakingUpLineStartTimeMs = System.currentTimeMillis();
                }
                break;

            case KeyEvent.VK_RIGHT:
                if (characterInfo.getLane1() != characterInfo.getLane2()) {
                    characterInfo.setLane1(characterInfo.getLane2());
                    lastTakingUpLineStartTimeMs = -1;
                } else if (characterInfo.getLane2() + 1 < levelInfo.laneX.length) {
                    lastLane = characterInfo.getLane2();
                    characterInfo.setLane2(characterInfo.getLane2() + 1);
            		lastTakingUpLineStartTimeMs = System.currentTimeMillis();
                }
                break;

            case KeyEvent.VK_UP:
                characterInfo.setY(Math.max(10, characterInfo.getY() - 50));
                break;

            case KeyEvent.VK_DOWN:
                characterInfo.setY(Math.min((int)(landscape.getHeight() / 2 - characterImage.getHeight()) - 10, characterInfo.getY() + 50));
                break;

            case KeyEvent.VK_SPACE:
                if (abilityImage.isVisible()) {
                    abilityStartTimeMs = System.currentTimeMillis();
                    abilityImage.setVisible(false);

                    // Press space for 5 seconds to crash all vehicles without slowing down but taking damage
                    if (characterInfo.getCharacter() == Character.Nate) {
                        characterInfo.setHealth(characterInfo.getHealth() - 1);
                        healthImages.get(characterInfo.getHealth()).setVisible(false);
                    }
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