import acm.graphics.GImage;

public class Trap {
    private GImage image;
    private int lane;

    public Trap(String imagePath, int x, int y, int lane) {
        this.image = new GImage(imagePath, x, y);
        this.lane = lane;
    }

    public GImage getImage() {
        return image;
    }

    public int getLane() {
        return lane;
    }

    public int getY() {
        return (int) image.getY();
    }

    public void move(int deltaY) {
        image.move(0, deltaY);
    }
}
