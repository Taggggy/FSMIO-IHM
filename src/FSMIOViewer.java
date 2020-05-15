import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.File;
import java.util.ArrayList;

/**
 * FSMIOViewer est la classe principale de l'application
 * Elle met en place le GUI de l'application et initialise tous les composants
 * Elle permet de dérouler un FSMIO<T1,T2> à partir d'un fichier sérialisé
 * 
 * Pour démarrer l'application, créer une instance de cette classe
 * 
 * @author LECOMTE Benjamin / RIBIERE Matthieu / MANCEAU Thibaut / DAUTREY Marin / LORIN Vincent
 * @version 2.0
 */
public class FSMIOViewer<T1,T2>
{
	//Constantes:
	private static final String VERSION = "2.0";
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    // Champs:
    private JFrame frame;
    private JTextArea textArea;
    private JLabel filenameLabel;
    private JLabel statusLabel;
    private FSMIO<T1,T2> currentFSMIO;
    private JMenuBar menubar;
    
    /**
     * Créer l'application FSMIOViewer
     */
    public FSMIOViewer()
    {
        makeFrame();
    }


    /**
     * Fonction d'ouverture: Ouvre le choix de fichier pour ouvrir un fichier sérialisé
     * @throws Exception 
     */
    private void openFile() throws Exception
    {
        int returnVal = fileChooser.showOpenDialog(frame); //Fenêtre de choix du fichier

        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;  // annulé
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
        showStatus("FSMIO Loaded. Current State: " + currentFSMIO.getInitialState()); //Affichage du statut / état initial
        
        boolean addTransitionMenu = true; //True s'il faut ajouter le menu de Transition
        								  //False s'il est déjà affiché
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
     * Fonction de fermeture: ferme le FSMIO ouvert précedemment
     */
    private void close()
    {
    	//Réinitialise tous les composants
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
     * Affiche le nom du fichier dans la barre d'état
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
     * Créer la fenêtre Swing et son contenu
     */
    private void makeFrame()
    {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	
        frame = new JFrame("FSMIOViewer");
        Dimension size = new Dimension(screenSize.width / 2, screenSize.height / 2); //Taille de la fenêtre
        frame.setPreferredSize(size);
        makeMenuBar(frame); //Initialisation de la barre des menus
                
        //Placement de chaque éléments
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout(6, 6));
        
        filenameLabel = new JLabel(); //Barre d'état
        contentPane.add(filenameLabel, BorderLayout.NORTH);

        textArea = new JTextArea(); //Texte principal au milieu de la fenêtre
        textArea.setEditable(false);
        contentPane.add(textArea, BorderLayout.CENTER);
        textArea.append("No FSMIO. Open a .ser file to load a FSMIO.\nThe content of the file will appear here.");
        
        statusLabel = new JLabel(VERSION); //Barre de statut
        contentPane.add(statusLabel, BorderLayout.SOUTH);
        
        // Construction terminé - affichage des composants            
        showFilename(null);
        frame.pack();
        
        frame.setLocation(screenSize.width/2 - frame.getWidth()/2, screenSize.height/2 - frame.getHeight()/2);
        
        frame.setVisible(true);
    }
    
    /**
     * Création de la barre des menus
     */
    private void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();


        menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // Création du menu File
        menu = new JMenu("File");
        menubar.add(menu);
        
        //Création du sous-menu Open
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

        // Création du menu Close
        item = new JMenuItem("Close");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, SHORTCUT_MASK)); //Raccourci CTRL+W
            item.addActionListener(new ActionListener() {
            					//Lorsqu'on appuie, ferme le fichier
                               public void actionPerformed(ActionEvent e) { close(); }
                           });
        menu.add(item);
        menu.addSeparator();
        
        //Création du sous-menu Quit
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK)); //Raccourci CTRL+Q
            item.addActionListener(new ActionListener() {
            					//Lorsqu'on appuie, ferme l'application
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);

    }
    
    private JMenu getOption() {
    	//Création du menu Transition
    	JMenu menu = new JMenu("Transition");
    	JMenuItem item;
    			
    	//Création du sous-menu Reset
		item = new JMenuItem("Reset");
		item.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								showStatus("Current State: " + currentFSMIO.getInitialState());
								//Reset le FSMIO et le statut affiché
								currentFSMIO.reset();
							}
						});
		menu.add(item);
		
		ArrayList<T1> inputs = new ArrayList<>();
		//Récupère la liste des inputs possibles
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
		
		//Création des sous-menus pour chaque entrée du FSMIO
		for(T1 in : inputs)
		{
			item = new JMenuItem(in.toString());
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						//Effectue la transition associée à l'entrée et modifie le statut
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
}
