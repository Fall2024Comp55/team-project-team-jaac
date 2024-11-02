import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Function;

import acm.graphics.GImage;

public class Button extends GImage implements MouseListener {
	private Function<Button, Void> func = null;

	public Button(String name, double x, double y) {
		super(name, x, y);
		this.addMouseListener(this);
	}

	// set call function when clicked
	public Button clicked(Function<Button, Void> func)
	{
		this.func = func;
		return this;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (func != null) {
			func.apply(this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
