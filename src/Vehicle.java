import java.util.Random;

import acm.graphics.GImage;

public class Vehicle {
    public enum VehicleType {
        CAR1, CAR2, TRUCK1, TRUCK2;
    }

    private VehicleType type;
    private int lane;
    private int y;
    private GImage image;

    public Vehicle(VehicleType type, int lane, int y) {
        this.type = type;
        this.lane = lane;
        this.y = y;

        switch (type) {
            case CAR1:
                image = new GImage("media/images/playing/car1.png");
                break;
            case CAR2:
                image = new GImage("media/images/playing/car2.png");
                break;
            case TRUCK1:
                image = new GImage("media/images/playing/truck1.png");
                break;

            case TRUCK2:
                image = new GImage("media/images/playing/truck2.png");
                break;
            default:
                break;
        }
    }

    public VehicleType getType() {
        return type;
    }

    public GImage getImage() {
        return image;
    }

    public int getLane() {
        return lane;
    }

    public int getY() {
        return y;
    }

    public void move(int moveY) {
        this.y += moveY;
    }

    public static Vehicle random(int minY, int maxY, int lane) {
        Random random = new Random();
        return new Vehicle(VehicleType.values()[random.nextInt(VehicleType.values().length)], lane, random.nextInt(maxY - minY + 1) + minY);
    }
}
