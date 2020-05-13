import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * An ImagePanel is a Swing component that can display an OFImage.
 * It is constructed as a subclass of JPanel with the added functionality
 * of setting an OFImage that will be displayed on the surface of this
 * component.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0
 */
public class TextArea extends JTextArea
{
    // The current width and height of this panel
    private int width, height;

    // An internal image buffer that is used for painting. For
    // actual display, this image buffer is then copied to screen.
    private JTextArea textArea;

    /**
     * Create a new, empty ImagePanel.
     */
    public TextArea()
    {
        width = 360;    // arbitrary size for empty panel
        height = 240;
    	textArea = new JTextArea(width, height);
        textArea.setEditable(false);
    }
}
