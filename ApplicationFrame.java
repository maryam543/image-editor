import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ApplicationFrame extends JFrame {

	ImagePanel canvas;
	JPanel backgroundPanel;	
	WindowActionListener winListener;

	public ApplicationFrame(String name)	{
		super(name);
		canvas = new ImagePanel();
		backgroundPanel = new JPanel();
		addMenuBar();
		backgroundPanel.add(canvas);
		add(backgroundPanel);
		winListener = new WindowActionListener();
		addWindowListener(winListener);
		setPreferredSize(new Dimension(500, 500));
	}

	/**
	 * Adds the complete JMenuBar to the application
	 */
	private void addMenuBar()	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createPenMenu());
		menuBar.add(createPageMenu());
		setJMenuBar(menuBar);
	}
	/**
	 * Creates a JMenu with the typical items found in a File menu
	 * @return File JMenu
	 */
	private JMenu createFileMenu()	{
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(createNewMenuItem());
		fileMenu.add(createOpenMenuItem());
		fileMenu.add(createSaveMenuItem());
		fileMenu.add(createSaveAsMenuItem());
		fileMenu.add(createExitMenuItem());
		return fileMenu;
	}
	/**
	 * Creates a JMenuItem that clears the canvas to white
	 * @return New JMenuItem
	 */
	private JMenuItem createNewMenuItem()	{
		JMenuItem item = new JMenuItem("New");
		class MenuItemListener implements ActionListener	{
			public void actionPerformed(ActionEvent event)	{
				canvas.newCanvas(Color.WHITE);
			}
		}
		MenuItemListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	/**
	 * Creates a JMenuItem that opens a canvas
	 * @return Open JMenuItem
	 */
	private JMenuItem createOpenMenuItem()	{
		JMenuItem item = new JMenuItem("Open File...");
		class MenuItemListener implements ActionListener	{
			public void actionPerformed(ActionEvent event)	{
				JFileChooser d = new JFileChooser();
				int ret = d.showOpenDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					canvas.openCanvas(d.getSelectedFile().getAbsolutePath());
				}
			}
		}
		MenuItemListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	/**
	 * Creates a JMenuItem that saves the canvas to its current filename.
	 * If none exits, executes saveAs
	 * @return Save JMenuItem
	 */
	private JMenuItem createSaveMenuItem()	{
		JMenuItem item = new JMenuItem("Save");
		class MenuItemListener implements ActionListener	{
			public void actionPerformed(ActionEvent event)	{
				save();
			}
		}
		MenuItemListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	/**
	 * Creates a JMenuItem that saves the canvas to a new file
	 * @return Save As... JMenuItem
	 */
	private JMenuItem createSaveAsMenuItem()	{
		JMenuItem item = new JMenuItem("Save As...");
		class MenuItemListener implements ActionListener	{
			public void actionPerformed(ActionEvent event)	{
				saveAs();
			}
		}
		MenuItemListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	/**
	 * If a filename exists, saves to that file, otherwise, executes SaveAs
	 */
	private void save()	{
		String fname = canvas.getFilename();
		if (fname == null)
			saveAs();
		else
			canvas.saveCanvas();
	}
	/**
	 * Saves the canvas to a new file
	 */
	private void saveAs()	{
		JFileChooser d = new JFileChooser();
		int ret = d.showSaveDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			canvas.saveCanvasAs(d.getSelectedFile().getAbsolutePath());
		}
	}
	/**
	 * Creates a JMenuItem that exits the program
	 * @return Exit JMenuItem
	 */
	private JMenuItem createExitMenuItem()	{
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener	{
			public void actionPerformed(ActionEvent event)	{
				exit();
			}
		}
		MenuItemListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	/**
	 * Exits out of the application.  Prompts for a save
	 */
	private void exit()	{
		int ans;
		if (canvas.getImage().getImageContents().size() != 0)	{
			ans = JOptionPane.showConfirmDialog(null, 
					"Unsaved progress may be lost!" +
			"\nDo you want to save before exiting?");
			if (ans == 0)	{
				save();
			}
			if (ans != 2)
				System.exit(-1);
		}
		else
			System.exit(-1);
	}
	/**
	 * Creates a JMenu with options that alter the pen
	 * @return Pen JMenu
	 */
	private JMenu createPenMenu()	{
		JMenu penMenu = new JMenu("Pen");
		penMenu.add(createPenSizeMenuItem());
		penMenu.add(createColorMenu("pen color"));
		return penMenu;
	}
	/**
	 * Creates a JMenuItem that alters the size of the pen
	 * @return Pen Size JMenuItem
	 */
	private JMenuItem createPenSizeMenuItem()	{
		JMenuItem penSize = new JMenuItem("Pen Size");
		class ItemActionListener implements ActionListener	{
			public void actionPerformed(ActionEvent e)	{
				String sizeStr = JOptionPane.showInputDialog(
				"Input an integer (Default: 3)");
				if (sizeStr != null)	{
					try{
						int size = Integer.parseInt(sizeStr);
						canvas.changePenSize(size);
					}catch(Exception excep)	{
						JOptionPane.showMessageDialog(null, 
						"MUST INPUT AN INTEGER");
					}
				}
			}
		}
		ItemActionListener listener = new ItemActionListener();
		penSize.addActionListener(listener);
		return penSize;

	}
	/**
	 * Creates a JMenu to change the color of either the pen or the background
	 * @param arg "pen color" if for the pen JMenu or "background" for the background
	 * @return Color JMenu
	 */
	private JMenu createColorMenu(String arg)	{
		JMenu color = new JMenu("Color");
		ArrayList<JMenuItem> colList = createColors(arg);
		for (JMenuItem col : colList)	{
			color.add(col);
		}
		return color;
	}
	/**
	 * Creates a list of Color JMenuItems that change a color
	 * @param arg "pen color" if for the pen JMenu or "background" for the background
	 * @return List of JMenuItems for each color
	 */
	private ArrayList<JMenuItem> createColors(String arg)	{
		ArrayList<JMenuItem> ans = new ArrayList<JMenuItem>();
		final String isFor = arg;
		final String[] itemNames = {"Black", "White", "Blue", "Gray", 
				"Green", "Yellow", "Red"};
		final Color[] itemColors = {Color.black, Color.white, 
				Color.blue, Color.gray, Color.green, Color.yellow, 
				Color.red};
		for (int i = 0 ; i < itemNames.length ; i++)	{
			JMenuItem d = new JMenuItem(itemNames[i]);
			final int x = i;
			class ColorListener implements ActionListener	{
				public void actionPerformed(ActionEvent e)	{
					if (isFor.equalsIgnoreCase("pen color"))
						canvas.changePenColor(itemColors[x]);
					else 
						canvas.changeBackgroundColor(itemColors[x]);
				}
			}
			ColorListener listener = new ColorListener();
			d.addActionListener(listener);
			ans.add(d);
		}
		return ans;
	}
	/**
	 * Creates a JMenu with options to alter the page
	 * @return Page JMenu
	 */
	private JMenu createPageMenu()	{
		JMenu pageMenu = new JMenu("Page");
		pageMenu.add(createColorMenu("background"));
		pageMenu.add(createSizeJMenuItem());
		pageMenu.add(createRotateMenu());
		pageMenu.add(createClearToCurrentPenColorMenuItem());
		return pageMenu;
	}
	/**
	 * Creates JMenuItem to change the size of the canvas
	 * @return Change Size JMenuItem
	 */
	private JMenuItem createSizeJMenuItem()	{
		JMenuItem sizeMenu = new JMenuItem("Change Size");
		class ItemActionListener implements ActionListener	{
			public void actionPerformed(ActionEvent e)	{
				String newSize = JOptionPane.showInputDialog(
						"New size? (Default: 300)\n(note the image will " +
				"always be square)");
				if (newSize != null)	{
					try{
						int dim = Integer.parseInt(newSize);
						canvas.setPreferredSize(new Dimension(dim, dim));
					}catch(Exception except)	{
						JOptionPane.showMessageDialog(null, 
						"MUST INPUT AN INTEGER");
					}
				}
			}
		}
		ItemActionListener listener = new ItemActionListener();
		sizeMenu.addActionListener(listener);

		return sizeMenu;
	}
	/**
	 * Creates a JMenu for the rotation actions
	 * @return Rotate JMenu
	 */
	private JMenu createRotateMenu()	{
		JMenu rotate = new JMenu("Rotate");
		rotate.add(createRotateClockwiseMenuItem());
		rotate.add(createRotateCCWMenuItem());
		return rotate;

	}
	/**
	 * Creates a JMenuItem that rotates the canvas clockwise
	 * @return Clockwise JMenuItem
	 */
	private JMenuItem createRotateClockwiseMenuItem()	{
		JMenuItem rotateMenu = new JMenuItem("Clockwise");
		class ItemActionListener implements ActionListener	{
			public void actionPerformed(ActionEvent e)	{
				canvas.rotateImageClockwise();
			}
		}
		ItemActionListener listener = new ItemActionListener();
		rotateMenu.addActionListener(listener);

		return rotateMenu;
	}
	/**
	 * Creates a JMenuItem that rotates the canvas counterclockwise
	 * @return Counter-clockwise JMenuItem
	 */
	private JMenuItem createRotateCCWMenuItem()	{
		JMenuItem rotateMenu = new JMenuItem("Counter-Clockwise");
		class ItemActionListener implements ActionListener	{
			public void actionPerformed(ActionEvent e)	{
				canvas.rotateImageCCW();
			}
		}
		ItemActionListener listener = new ItemActionListener();
		rotateMenu.addActionListener(listener);
		return rotateMenu;
	}
	/**
	 * Creates JMenuItem that clears the canvas to the current drawing color
	 * @return Clear to Pen Color JMenuItem
	 */
	private JMenuItem createClearToCurrentPenColorMenuItem()	{
		JMenuItem item = new JMenuItem("Clear To Pen Color");
		class MenuItemListener implements ActionListener	{
			public void actionPerformed(ActionEvent event)	{
				canvas.newCanvas(canvas.getPenColor());
			}
		}
		MenuItemListener listener = new MenuItemListener();
		item.addActionListener(listener);
		return item;
	}
	/**
	 * Ensures that the user does not exit without saving accidentally
	 * @author rogerskw
	 *
	 */
	private class WindowActionListener implements WindowListener	{
		public void windowActivated(WindowEvent e)	{}
		public void windowClosed(WindowEvent e)	{}
		public void windowClosing(WindowEvent e)	{
			exit();	
		}
		public void windowDeactivated(WindowEvent e)	{}
		public void windowDeiconified(WindowEvent e)	{}
		public void windowIconified(WindowEvent e)	{}
		public void windowOpened(WindowEvent e)	{}

	}
}
