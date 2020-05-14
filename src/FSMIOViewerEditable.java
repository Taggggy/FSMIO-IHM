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
public class FSMIOViewerEditable<T1,T2>
{
    // constants:
	private static final String VERSION = "3.0";
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    // fields:
    private JFrame frame;
    private TextArea textArea;
    private JLabel filenameLabel;
    private JLabel statusLabel;
    private FSMIO<T1,T2> currentFSMIO = null;
    private JMenuBar menubar;
    
    /**
     * Create an ImageViewer show it on screen.
     */
    public FSMIOViewerEditable()
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
        currentFSMIO = new FSMIO<T1,T2>(selectedFile.getName());
        textArea.setText(currentFSMIO.toString());

        if(currentFSMIO == null) {   // image file was not a valid image
            JOptionPane.showMessageDialog(frame,
                    "The file was not in a recognized FSMIO file format.",
                    "FSMIO Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        showFilename(selectedFile.getName());
        JOptionPane.showMessageDialog(frame, selectedFile.getName(), "File loaded", JOptionPane.INFORMATION_MESSAGE);
        showStatus("FSMIO Loaded. Current State: " + currentFSMIO.getInitialState());
        
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
    	
        frame = new JFrame("FSMIOViewerEditable");
        Dimension size = new Dimension(screenSize.width / 2,screenSize.height / 2);
        frame.setPreferredSize(size);
        makeMenuBar(frame);
        
        Container contentPane = frame.getContentPane();
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(6, 6));
        
        filenameLabel = new JLabel();
        contentPane.add(filenameLabel, BorderLayout.NORTH);

        textArea = new TextArea();
        contentPane.add(textArea, BorderLayout.CENTER);
        textArea.append("No FSMIO. Open a .ser file to load a FSMIO.\nThe content of the file will appear here.");
        
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
        
       createFSMIO();

    }
    
    private JMenu getOption() {
		JMenu menu = new JMenu("Transition");
		JMenuItem item;
		
		item = new JMenuItem("Reset");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								showStatus("Current State: " + currentFSMIO.getInitialState());
								currentFSMIO.reset();
							}
						});
		menu.add(item);
		
		ArrayList<T1> inputs = new ArrayList<>();
		//R�cup�re la liste des inputs possibles
		for(Transition<T1,T2> transition : currentFSMIO.gettf().getTransitions())
		{
			boolean addTransition = true;
			for(T1 in : inputs)
			{
				if(transition.getTag().getInput().equals(in)) 
					addTransition = false;
			}
			if(addTransition)
				inputs.add(transition.getTag().getInput());
		}
		
		for(T1 in : inputs)
		{
			item = new JMenuItem(in.toString());
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						T2 out = currentFSMIO.doTransition(in);
						showStatus("New State: " + currentFSMIO.getCurrentState() + ". Output: " + out);
					} catch (ParametresInvalides exc) {
						showStatus("Invalid Transition");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			});
			menu.add(item);
		}
      
		return menu;
	}
    
    private void createFSMIO()
    {
    	boolean addTransitionMenu = true;
        for(int i = 0; i < menubar.getMenuCount(); i++)
        {
        	if(menubar.getMenu(i).getText().equals("Edit"))
        		addTransitionMenu = false;
        }
        if(!addTransitionMenu)
        	return;
        
    	JMenu menu = new JMenu("Edit");
    	menubar.add(menu);
    	JMenuItem item;
    	
    	item = new JMenuItem("Add State");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
						        State input = new State(JOptionPane.showInputDialog(frame, "Input a state name", "Add state", JOptionPane.INFORMATION_MESSAGE));
						        try {
									currentFSMIO.addState(input);
								} catch (NullPointerException exc) {
									currentFSMIO = new FSMIO<T1,T2>(input);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
						        textArea.setText(currentFSMIO.toString());
							}
						});
		menu.add(item);
		
		item = new JMenuItem("Remove State");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								State input = new State(JOptionPane.showInputDialog(frame, "Input a state name", "Remove state", JOptionPane.INFORMATION_MESSAGE));
								currentFSMIO.removeState(input);
								textArea.setText(currentFSMIO.toString());
							}
						});
		menu.add(item);
		
		item = new JMenuItem("Add Transition");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JPanel panel = new JPanel();
								JTextField originField = new JTextField(10);
								JTextField destinationField = new JTextField(10);
								JTextField inputField = new JTextField(10);
								JTextField outputField = new JTextField(10);
								
								panel.add(new JLabel("Origin State : "));
								panel.add(originField);
								panel.add(new JLabel("Destination State : "));
								panel.add(destinationField);
								panel.add(new JLabel("Tag input : "));
								panel.add(inputField);
								panel.add(new JLabel("Tag output : "));
								panel.add(outputField);
								
								JOptionPane.showConfirmDialog(frame, panel);
								
								State orig = new State(originField.getText());
								State dest = new State(destinationField.getText());
						        try {
									currentFSMIO.addTransition(orig, (T1)inputField.getText(), (T2)outputField.getText(), dest);
					
								} catch (Exception e1) {
									e1.printStackTrace();
								}
						        textArea.setText(currentFSMIO.toString());
							}
						});
		menu.add(item);
		
		item = new JMenuItem("Remove Transition");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String[] choices = new String[100];
								for(int i = 0; i < currentFSMIO.gettf().getTransitions().size(); i++)
								{
									choices[i] = currentFSMIO.gettf().getTransitions().get(i).toString();
								}
								JPanel panel = new JPanel();
								JComboBox cb = new JComboBox(choices);
								panel.add(cb);
								
								JOptionPane.showMessageDialog(frame, panel, "Remove Transition", JOptionPane.INFORMATION_MESSAGE);
								int index = cb.getSelectedIndex();
								currentFSMIO.removeTransition(index);
								
								
								textArea.setText(currentFSMIO.toString());
							}
						});
		menu.add(item);
		menu.addSeparator();
		
		item = new JMenuItem("Confirm & save");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String filename = JOptionPane.showInputDialog(frame, "Input a file name", "Save", JOptionPane.INFORMATION_MESSAGE);
								currentFSMIO.saveObject(filename);
								
								showFilename(filename);
						        JOptionPane.showMessageDialog(frame, filename, "File loaded", JOptionPane.INFORMATION_MESSAGE);
						        showStatus("FSMIO Loaded. Current State: " + currentFSMIO.getInitialState());
						        
						        boolean addTransitionMenu = true;
						        for(int i = 0; i < menubar.getMenuCount(); i++)
						        {
						        	if(menubar.getMenu(i).getText().equals("Transition"))
						        		addTransitionMenu = false;
						        }
						        if(addTransitionMenu)
						        	menubar.add(getOption());
						        //+ VERIFIER COHERENCE (COMPLETUDE ? DETERMINISME ?)
							}
						});
		menu.add(item);
		
		return;
    }
}
