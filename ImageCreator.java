import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class ImageCreator {
	
	private static BufferedImage icon;
	
	public static void main(String[] args)	{
		ApplicationFrame frame = new ApplicationFrame("Image Creator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		/*
		 * Attempts to use an icon
		 */
		try {
			icon = ImageIO.read(frame.getClass().getResource(
			"/icon.png"));
			frame.setIconImage(icon);
		} 
		catch (Exception e) {
			System.err.println("Cannot locate icon.png" +
			"\nProceeding without icon");
		}
		frame.setVisible(true);
	}
}
