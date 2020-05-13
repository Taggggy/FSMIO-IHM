import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.util.ArrayList;

/**
 * ImageViewer is the main class of the image viewer application. It builds and
 * displays the application GUI and initialises all other components.
 * 
 * To start the application, create an object of this class.
 * 
 * @author Michael Kolling and David J Barnes 
 * @version 1.0
 */
public class FSMIOStringViewer  
{
    // constants:
	private static final String VERSION = "1.0";
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    // fields:
    private JFrame frame;
    private TextArea textArea;
    private JLabel filenameLabel;
    private JLabel statusLabel;
    private FSMIOString currentFSMIO;
    private JMenuBar menubar;
    
    /**
     * Create an ImageViewer show it on screen.
     */
    public FSMIOStringViewer()
    {
        makeFrame();
    }


    // ---- implementation of menu functions ----
    
    /**
     * Open function: open a file chooser to select a new image file.
     * @throws Exception 
     */
    private void openFile() throws Exception
    {
        int returnVal = fileChooser.showOpenDialog(frame);

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // cancelled
        }
        File selectedFile = fileChooser.getSelectedFile();
        currentFSMIO = new FSMIOString(selectedFile.getName());
        textArea.setText(currentFSMIO.fsms.toString());
        
        if(currentFSMIO == null) {   // image file was not a valid image
            JOptionPane.showMessageDialog(frame,
                    "The file was not in a recognized FSMIO file format.",
                    "FSMIO Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        showFilename(selectedFile.getName());
        JOptionPane.showMessageDialog(frame, selectedFile.getName(), "File loaded", JOptionPane.INFORMATION_MESSAGE);
        showStatus("FSMIO Loaded. Current State: " + currentFSMIO.fsms.getInitialState());
        
        boolean addTransitionMenu = true;
        for(int i = 0; i < menubar.getMenuCount(); i++)
        {
        	if(menubar.getMenu(i).getText().equals("Transition"))
        		addTransitionMenu = false;
        }
        if(addTransitionMenu)
        	menubar.add(getOption());
        
        frame.pack();
    }


	/**
     * Close function: clase the current image.
     */
    private void close()
    {
        currentFSMIO = null;
        textArea.setText(null);
        showFilename(null);
        showStatus(VERSION);
        makeMenuBar(frame);
    }
    
    /**
     * Quit function: quit the application.
     */
    private void quit()
    {
        System.exit(0);
    }
    
    // ---- support methods ----

    /**
     * Display a file name on the appropriate label.
     */
    private void showFilename(String filename)
    {
        if(filename == null) {
            filenameLabel.setText("No FSMIO displayed.");
        }
        else {
            filenameLabel.setText("File: " + filename);
        }
    }
    
    /**
     * Display a status message in the frame's status bar.
     */
    private void showStatus(String text)
    {
        statusLabel.setText(text);
    }
    
    // ---- swing stuff to build the frame and all its components ----
    
    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame()
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
        frame = new JFrame("FSMIOStringViewer");
        Dimension size = new Dimension(screenSize.height / 2, screenSize.width / 2);
        frame.setPreferredSize(size);
        makeMenuBar(frame);
        
        Container contentPane = frame.getContentPane();
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(6, 6));
        
        filenameLabel = new JLabel();
        contentPane.add(filenameLabel, BorderLayout.NORTH);

        textArea = new TextArea();
        contentPane.add(textArea, BorderLayout.CENTER);
        textArea.append("No FSMIO. Open a .fsm file to load a FSMIO.\nThe content of the file will appear here.");
        
        statusLabel = new JLabel(VERSION);
        contentPane.add(statusLabel, BorderLayout.SOUTH);
        
        // building is done - arrange the components and show        
        showFilename(null);
        frame.pack();
        
        frame.setLocation(screenSize.width/2 - frame.getWidth()/2, screenSize.height/2 - frame.getHeight()/2);
        
        frame.setVisible(true);
    }
    
    /**
     * Create the main frame's menu bar.
     */
    private void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();


        menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        item = new JMenuItem("Open");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { 
                            	   try {
                            		   openFile();
                            	   } catch (Exception e1) {
                            		   e1.printStackTrace();
                            	   } }
                           	});
        menu.add(item);

        item = new JMenuItem("Close");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { close(); }
                           });
        menu.add(item);
        menu.addSeparator();
        
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);

    }
    
    private JMenu getOption() {
		JMenu menu = new JMenu("Transition");
		JMenuItem item;
		
		item = new JMenuItem("Reset");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								showStatus("Current State: " + currentFSMIO.fsms.getInitialState());
								currentFSMIO.fsms.reset();
							}
						});
		menu.add(item);
		
		ArrayList<String> inputs = new ArrayList<>();
		//Récupère la liste des inputs possibles
		for(Transition<String,String> transition : currentFSMIO.fsms.gettf().getTransitions())
		{
			boolean addTransition = true;
			for(String in : inputs)
			{
				if(transition.getTag().getInput().equals(in)) 
					addTransition = false;
			}
			if(addTransition)
				inputs.add(transition.getTag().getInput());
		}
		
		for(String in : inputs)
		{
			item = new JMenuItem(in);
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String out = currentFSMIO.fsms.doTransition(in);
						showStatus("New State: " + currentFSMIO.fsms.getCurrentState() + ". Output: " + out);
					} catch (Exception exc) {
						exc.printStackTrace();
					}
					
				}
			});
			menu.add(item);
		}
      
		return menu;
	}
}
