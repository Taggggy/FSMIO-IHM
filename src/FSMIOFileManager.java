import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

/**
 * ImageFileManager is a small utility class with static methods to load
 * and save images.
 * 
 * The files on disk can be in JPG or PNG image format. For files written
 * by this class, the format is determined by the constant IMAGE_FORMAT.
 * 
 * @author Michael Kolling and David J Barnes 
 * @version 2.0
 */
public class FSMIOFileManager
{
    // A constant for the image format that this writer uses for writing.
    // Available formats are "jpg" and "png".
    
    /**
     * Read an image file from disk and return it as an image. This method
     * can read JPG and PNG file formats. In case of any problem (e.g the file 
     * does not exist, is in an undecodable format, or any other read error) 
     * this method returns null.
     * 
     * @param imageFile  The image file to be loaded.
     * @return           The image object or null is it could not be read.
     */
    public static String loadFSMIO(String filename)
    {
    	StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            	content.append(line).append("\n");
            reader.close();
        }
        catch(FileNotFoundException exc) {
            return null;
        }
        catch(IOException exc) {
            return null;
        }
        return content.toString();
    }
}
