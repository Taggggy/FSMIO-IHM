import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.util.ArrayList;

/**
 * FSMIOViewerEditable est la classe principale de l'application
 * Elle met en place le GUI de l'application et initialise tous les composants
 * Elle permet de d�rouler un FSMIO<T1,T2> � partir d'un fichier s�rialis�
 * Elle permet aussi de cr�er/modifier un FSMIO en rajoutant/supprimant des �tats/transitions
 * 
 * Pour d�marrer l'application, cr�er une instance de cette classe
 * 
 * @author LECOMTE Benjamin / RIBIERE Matthieu / MANCEAU Thibaut / DAUTREY Marin / LORIN Vincent
 * @version 3.0
 */
public class FSMIOViewerEditable<T1,T2>
{
    // Constantes:
	private static final String VERSION = "3.0";
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    // Champs:
    private JFrame frame;
    private JTextArea textArea;
    private JLabel filenameLabel;
    private JLabel statusLabel;
    private FSMIO<T1,T2> currentFSMIO = null;
    private JMenuBar menubar;
    
    /**
     * Cr�er l'application FSMIOViewerEditable
     */
    public FSMIOViewerEditable()
    {
        makeFrame();
    }


    /**
     * Fonction d'ouverture: Ouvre le choix de fichier pour ouvrir un fichier s�rialis�
     * @throws Exception 
     */
    private void openFile() throws Exception
    {
        int returnVal = fileChooser.showOpenDialog(frame); //Fen�tre de choix du fichier

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // annul�
        }
        File selectedFile = fileChooser.getSelectedFile(); //Choix du fichier
        currentFSMIO = new FSMIO<T1,T2>(selectedFile.getName()); //Instanciation d'un FSMIO avec le nom du fichier
        textArea.setText(currentFSMIO.toString()); //Affichage du FSMIO

        if(currentFSMIO == null) {   // Le fichier ne contient pas un FSMIO
            JOptionPane.showMessageDialog(frame,
                    "The file was not in a recognized FSMIO file format.",
                    "FSMIO Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        showFilename(selectedFile.getName()); //Affichage du nom du fichier en haut
        JOptionPane.showMessageDialog(frame, selectedFile.getName(), "File loaded", JOptionPane.INFORMATION_MESSAGE); //Popup de confirmation
        showStatus("FSMIO Loaded. Current State: " + currentFSMIO.getInitialState()); //Affichage du statut / �tat initial
        
        boolean addTransitionMenu = true; //True s'il faut ajouter le menu de Transition
        								  //False s'il est d�j� affich�
        for(int i = 0; i < menubar.getMenuCount(); i++)
        {
        	if(menubar.getMenu(i).getText().equals("Transition"))
        		addTransitionMenu = false;
        }
        if(addTransitionMenu)
        	menubar.add(getOption()); //Ajout du menu Transition dans la barre des menus
        
        frame.pack();
    }


    /**
     * Fonction de fermeture: ferme le FSMIO ouvert pr�cedemment
     */
    private void close()
    {
    	//R�initialise tous les composants
        currentFSMIO = null;
        textArea.setText(null);
        showFilename(null);
        showStatus(VERSION);
        makeMenuBar(frame);
    }
    
    /**
     * Fonction de fin : ferme l'application
     */
    private void quit()
    {
        System.exit(0);
    }
    
    /**
     * Affiche le nom du fichier dans la barre d'�tat
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
     * Affiche un message dans la barre de statut
     */
    private void showStatus(String text)
    {
        statusLabel.setText(text);
    }
    
    /**
     * Cr�er la fen�tre Swing et son contenu
     */
    private void makeFrame()
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
        frame = new JFrame("FSMIOViewerEditable");
        Dimension size = new Dimension(screenSize.width / 2, screenSize.height / 2); //Taille de la fen�tre
        frame.setPreferredSize(size);
        makeMenuBar(frame); //Initialisation de la barre des menus
      
        //Placement de chaque �l�ments
        Container contentPane = frame.getContentPane();
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(6, 6));
        
        filenameLabel = new JLabel(); //Barre d'�tat
        contentPane.add(filenameLabel, BorderLayout.NORTH);

        textArea = new JTextArea(); //Texte principal au milieu de la fen�tre
        textArea.setEditable(false);
        contentPane.add(textArea, BorderLayout.CENTER);
        textArea.append("No FSMIO. Open a .ser file to load a FSMIO.\nThe content of the file will appear here.");
        
        statusLabel = new JLabel(VERSION); //Barre de statut
        contentPane.add(statusLabel, BorderLayout.SOUTH);
        
     // Construction termin� - affichage des composants         
        showFilename(null);
        frame.pack();
        
        frame.setLocation(screenSize.width/2 - frame.getWidth()/2, screenSize.height/2 - frame.getHeight()/2);
        
        frame.setVisible(true);
    }
    
    /**
     * Cr�ation de la barre des menus
     */
    private void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();


        menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
     // Cr�ation du menu File
        menu = new JMenu("File");
        menubar.add(menu);
        
        //Cr�ation du sous-menu Open
        item = new JMenuItem("Open");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, SHORTCUT_MASK)); //Raccourci CTRL+O
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { 
                            	   try {
                            		   openFile(); //Lorsqu'on appuie, ouvre un fichier
                            	   } catch (Exception e1) {
                            		   e1.printStackTrace();
                            	   } }
                           	});
        menu.add(item);

        // Cr�ation du menu Close
        item = new JMenuItem("Close");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK)); //Raccourci CTRL+W
            item.addActionListener(new ActionListener() {
            					//Lorsqu'on appuie, ferme le fichier
                               public void actionPerformed(ActionEvent e) { close(); }
                           });
        menu.add(item);
        menu.addSeparator();
        
        //Cr�ation du sous-menu Quit
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK)); //Raccourci CTRL+Q
            item.addActionListener(new ActionListener() {
            					//Lorsqu'on appuie, ferme l'application
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);
        
       createFSMIO(); //Cr�ation du menu Edit

    }
    
    private JMenu getOption() {
    	//Cr�ation du menu Transition
    	JMenu menu = new JMenu("Transition");
    	JMenuItem item;
    			
    	//Cr�ation du sous-menu Reset
		item = new JMenuItem("Reset");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								showStatus("Current State: " + currentFSMIO.getInitialState());
								//Reset le FSMIO et le statut affich�
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
		
		//Cr�ation des sous-menus pour chaque entr�e du FSMIO
		for(T1 in : inputs)
		{
			item = new JMenuItem(in.toString());
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						//Effectue la transition associ�e � l'entr�e et modifie le statut
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
    	boolean addEditMenu = true; //True s'il faut ajouter le menu Edit
		  							//False s'il est d�j� affich�
        for(int i = 0; i < menubar.getMenuCount(); i++)
        {
        	if(menubar.getMenu(i).getText().equals("Edit"))
        		addEditMenu = false;
        }
        if(!addEditMenu)
        	return;
        
        //Cr�ation du menu Edit
    	JMenu menu = new JMenu("Edit");
    	menubar.add(menu);
    	JMenuItem item;
    	
    	//Cr�ation du sous-menu Add State
    	item = new JMenuItem("Add State");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//Lorsqu'on appuie, ouvre une fen�tre demandant le nom de l'�tat � rajouter
						        State input = new State(JOptionPane.showInputDialog(frame, "Input a state name", "Add state", JOptionPane.INFORMATION_MESSAGE));
						        try {
									currentFSMIO.addState(input); //rajoute l'�tat au FSMIO
								} catch (NullPointerException exc) {
									currentFSMIO = new FSMIO<T1,T2>(input); //S'il n'y avait pas de FSMIO, en cr�e un avec l'�tat indiqu� comme �tat initial
								} catch (Exception e1) {
									e1.printStackTrace();
								}
						        textArea.setText(currentFSMIO.toString()); //Affiche le FSMIO
							}
						});
		menu.add(item);
		
		//Cr�ation du sous-menu Remove State
		item = new JMenuItem("Remove State");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//Lorsqu'on appuie, ouvre une fen�tre demandant le nom de l'�tat � supprimer
								State input = new State(JOptionPane.showInputDialog(frame, "Input a state name", "Remove state", JOptionPane.INFORMATION_MESSAGE));
								currentFSMIO.removeState(input); //Supprime l'�tat et affiche le FSMIO
								textArea.setText(currentFSMIO.toString());
							}
						});
		menu.add(item);
		
		//Cr�ation du sous-menu Add Transition
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
								
								//Affiche une fen�tre demandant les 4 composantes de la transition
								JOptionPane.showConfirmDialog(frame, panel);
								
								State orig = new State(originField.getText());
								State dest = new State(destinationField.getText());
						        try {
						        	//Cr�e la transition demand�e
									currentFSMIO.addTransition(orig, (T1)inputField.getText(), (T2)outputField.getText(), dest);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
						        textArea.setText(currentFSMIO.toString()); //Affiche le FSMIO
							}
						});
		menu.add(item);
		
		//Cr�ation du sous-menu Remove Transition
		item = new JMenuItem("Remove Transition");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String[] choices = new String[100];
								//Ajoute toutes les transitions existantes dans un tableau de String
								for(int i = 0; i < currentFSMIO.gettf().getTransitions().size(); i++)
								{
									choices[i] = currentFSMIO.gettf().getTransitions().get(i).toString();
								}
								JPanel panel = new JPanel();
								JComboBox cb = new JComboBox(choices);
								panel.add(cb);
								
								JOptionPane.showMessageDialog(frame, panel, "Remove Transition", JOptionPane.INFORMATION_MESSAGE);
								//Ouvre une fen�tre demandant de choisir une transition parmis celles existantes
								int index = cb.getSelectedIndex();
								currentFSMIO.removeTransition(index); //supprime la transition choisie et affiche le FSMIO
								textArea.setText(currentFSMIO.toString());
							}
						});
		menu.add(item);
		menu.addSeparator();
		
		//Cr�ation du sous-menu Confirm & save
		item = new JMenuItem("Confirm & save");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//Demande le nom d'un fichier et enregistre le FSMIO s�rialis� dans ce fichier
								String filename = JOptionPane.showInputDialog(frame, "Input a file name", "Save", JOptionPane.INFORMATION_MESSAGE);
								currentFSMIO.saveObject(filename);
								
								//Ouvre le FSMIO enregistr� normalement
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
							}
						});
		menu.add(item);
		
		return;
    }
}
