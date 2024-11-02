import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public abstract class Screen implements MouseListener {

	protected GraphicsGame gg;

	public Screen() {

	}

	// implement MouseListener
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

	public void init(GraphicsGame gg, HashMap<String, Object> params)
	{
		this.gg = gg;
		gg.getGCanvas().addMouseListener(this);
		show(params);
	}

	public void deinit()
	{
		gg.getGCanvas().removeMouseListener(this);
	}

	public abstract String getName();

	protected abstract void show(HashMap<String, Object> params);


}
