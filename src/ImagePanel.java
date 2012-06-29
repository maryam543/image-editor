import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class ImagePanel extends JPanel {

	private DrawListener listener;
	private Point point1, point2;
	private Color backgroundColor, penColor;
	private Dimension canvasSize;
	private Image picture;	
	private int penSize;
	private String filename;


	public ImagePanel()	{
		point1 = null;
		point2 = null;
		backgroundColor = Color.white;
		penColor = Color.black;
		listener = new DrawListener();
		canvasSize = new Dimension(300,300);
		penSize = 3;
		picture = new Image();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		setPreferredSize(canvasSize);

		repaint();
	}

	/**
	 * Changes background color
	 * @param newColor new background color
	 */
	public void changeBackgroundColor(Color newColor)	{
		backgroundColor = newColor;
	}
	
	/**
	 * Changes pen color
	 * @param newColor new pen color
	 */
	public void changePenColor(Color newColor)	{
		penColor = newColor;
	}
	
	/**
	 * @return Current pen color
	 */
	public Color getPenColor()	{
		return penColor;
	}
	
	/**
	 * @return the Image on the canvas
	 */
	protected Image getImage()	{
		return picture;
	}
	
	/**
	 * Changes the size of each dot the pen makes
	 * @param size new size of each dot
	 */
	public void changePenSize(int size)	{
		penSize = size;
	}

	/**
	 * Clears the canvas to a new color
	 * @param background the color that the canvas will be changed to
	 */
	public void newCanvas(Color background)	{
		picture = new Image();
		filename = null;
		backgroundColor = background;
		repaint();
	}

	/**
	 * Opens a new canvas from the file
	 * @param fname location of the new cavnas
	 */
	public void openCanvas(String fname)	{
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(fname));
			Image newFile = (Image)ois.readObject();
			picture = newFile;
			filename = fname;
			repaint();
			paintChildren(this.getGraphics());
			ois.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "COULD NOT OPEN " +
					"FILE\nSELECT A COMPATIBLE FILE");
		}
	}

	/**
	 * Saves the canvas to current filename
	 */
	public void saveCanvas()	{
		try {
			ObjectOutputStream ois = new ObjectOutputStream(
					new FileOutputStream(filename));
			ois.writeObject(picture);
			ois.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Saves the canvas to a new filename
	 * @param file new file location for the canvas
	 */
	public void saveCanvasAs(String file)	{
		try {
			ObjectOutputStream ois = new ObjectOutputStream(
					new FileOutputStream(file));
			ois.writeObject(picture);
			filename = file;
			ois.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * @return file location for the current canvas
	 */
	public String getFilename()	{
		return filename;
	}
	
	/**
	 * Rotates the canvas clockwise
	 */
	public void rotateImageClockwise()	{
		picture.rotateClockWise(this);
		repaint();
	}
	
	/**
	 * rotates the canvas counterclockwise
	 */
	public void rotateImageCCW()	{
		picture.rotateCCW(this);
		repaint();
	}

	/**
	 * Creates a new dot at the cursor's location
	 * @return new Dot at current cursor location
	 */
	private Dot createShape()	{
		Dot shape = null;
		if (point1 != null)	{
			if (point2 == null)	{
				shape = new Dot(point1, penColor, penSize);
			}
			else {
				shape = new Dot(point2, penColor, penSize);
			}
		}
		return shape;
	}

	/**
	 * Redraws the entire canvas
	 */
	public void paintComponent(Graphics page)	{
		super.paintComponent(page);
		setBackground(backgroundColor);
		Dot shape = createShape();
		if (shape != null)	{
			picture.addGraphic(shape);
		}
		picture.draw(page);
	}

	/**
	 * 
	 * @author rogerskw
	 *
	 */
	private class DrawListener implements MouseListener, 
	MouseMotionListener	{
		public void mouseMoved(MouseEvent e)	{

		}
		public void mouseClicked(MouseEvent e)	{
			point1 = e.getPoint();
			point2 = e.getPoint();
			repaint();
		}
		public void mousePressed(MouseEvent e)	{

		}
		public void mouseEntered(MouseEvent e)	{

		}
		public void mouseReleased(MouseEvent e)	{
			point1 = null;
			point2 = null;
		}
		public void mouseDragged(MouseEvent e)	{
			point2 = e.getPoint();
			Dot pixel = new Dot(point2, penColor, penSize);
			picture.addGraphic(pixel);
			repaint();
		}
		public void mouseExited(MouseEvent e)	{

		}
	}
}
