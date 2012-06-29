import java.awt.*;
import java.io.*;

public class Dot implements Serializable {

	public static final long serialVersionUID = 1234567890;

	public Color graphicColor;
	public int x, y, width, height;

	/**
	 * Creates a Dot
	 * @param point1 Location of the Dot
	 * @param dotColor color of the Dot
	 * @param size diameter of the Dot
	 */
	public Dot(Point point1, Color dotColor, int size)	{
		x = point1.x - size / 2;
		y = point1.y - size / 2;
		graphicColor = dotColor;
		height = size;
		width = size;
	}

	/**
	 * Colors in a Dot on a Graphics page
	 * @param page Graphics where the Dot is drawn
	 */
	public void drawGraphic(Graphics page)	{
		page.setColor(graphicColor);
		page.fillOval(x, y, width, height);
	}

	/**
	 * Rotates the Dot about the center of the canvas
	 * @param clockwise Whether or not to be rotated clockwise
	 * @param container Canvas that contains the Dot
	 */
	private void rotate(boolean clockwise, ImagePanel container)	{
		int imageSize = container.getHeight();
		int distanceToOrigin = imageSize / 2;
		int relativeX = x - distanceToOrigin;
		int relativeY = y - distanceToOrigin;
		int mid = relativeX;
		if (clockwise)	{
			relativeX = -1 * relativeY;
			relativeY = mid;
		}
		else {
			relativeX = relativeY;
			relativeY =  -1 * mid;
		}
		x = relativeX + distanceToOrigin;
		y = relativeY + distanceToOrigin;
	}
	/**
	 * Public method to rotate Dot clockwise
	 * @param containerCanvas that contains the Dot
	 */
	public void rotateClockwise(ImagePanel container)	{
		rotate(true, container);
	}
	/**
	 * Public method to rotate the Dot counterclockwise
	 * @param container Canvas that contains the Dot
	 */
	public void rotateCCW(ImagePanel container)	{
		rotate(false, container);
	}
}
