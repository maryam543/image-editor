import java.util.ArrayList;
import java.awt.*;
import java.io.*;

public class Image implements Serializable {
	public static final long serialVersionUID = 4692;
	private ArrayList<Dot> imageList;
	
	/**
	 * Creates an empty Image
	 */
	public Image()	{
		imageList = new ArrayList<Dot>();
	}
	/**
	 * Adds a new Dot to the Image
	 * @param g Dot to be added
	 */
	public void addGraphic(Dot g)	{
		imageList.add(g);
	}
	/**
	 * @return ArrayList<Dot> that defines the Image
	 */
	public ArrayList<Dot> getImageContents()	{
		return imageList;
	}
	/**
	 * Draws the entire Image
	 * @param page Where the Image is drawn
	 */
	public void draw(Graphics page)	{
		for (Dot element : imageList)	{
			element.drawGraphic(page);
		}
	}
	/**
	 * Rotates the entire Image clockwise
	 * @param container Canvas that contains the Image
	 */
	public void rotateClockWise(ImagePanel container)	{
		for (Dot element : imageList)
			element.rotateClockwise(container);
	}
	/**
	 * Rotates the entire Image counterclockwise
	 * @param container Canvas that contains the Image
	 */
	public void rotateCCW(ImagePanel container)	{
		for (Dot element : imageList)
			element.rotateCCW(container);
	}
}
