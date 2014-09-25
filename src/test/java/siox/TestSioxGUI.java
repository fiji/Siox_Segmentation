package siox;

import fiji.Debug;

/**
 * Simple test to launch the plugin
 * 
 * @author Ignacio Arganda-Carreras
 *
 */
public class TestSioxGUI 
{
	/**
	 * Main method to test and debug the SIOX
	 * Segmentation GUI
	 *  
	 * @param args
	 */
	public static void main( final String[] args )
	{		
		// Call the plugin with empty arguments (this
		// will pop up an Open dialog)		
		Debug.run("SIOX: Simple Interactive Object Extraction", "");
	}

}
