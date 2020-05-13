
/*
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
*/

import java.util.*;
import java.io.*;

// A FSMIO with Strings in the Tag reading the automaton description from a file
public class FSMIOString
{
	FSMIO<String, String> fsms;

	// Constructor : Reading a FSMIO description from a file
	// The file contains one transition per line
	// Every line has the following syntax :
	// statenameorig input output statenamedest
	// The first transition is supposed to start from the initial state 
	public FSMIOString (String filename)
	{
		BufferedReader fileR;
		try
		{
			fileR = new BufferedReader(new FileReader(filename));
			// System.out.println("\n\n Fichier bien ouvert \n\n");
			String line = fileR.readLine();
			// System.out.println("\n\n Première ligne bien lue \n\n");
			String [] t;
			State orig, dest;
			t = readTransition(line);
			// System.out.println("\n\n Première transition bien lue \n\n");
			orig = new State(t[0]);
			this.fsms = new FSMIO<String, String>(orig);
			// System.out.println("\n\n Premier ajout à this.fsms ok \n\n");
			// int compteur_ligne = 0;
			// System.out.println("\n\n Ligne " + String.valueOf(compteur_ligne) + " ok : " + line + " \n\n");
			// compteur_ligne++;
			while(line != null && !line.equals(""))
			{
				t = readTransition(line);
				orig = new State(t[0]);
				dest = new State(t[3]);
				this.fsms.addState(orig);
				this.fsms.addState(dest);
				this.fsms.addTransition(orig, t[1], t[2], dest);
				line = fileR.readLine();
				// System.out.println("\n\n Ligne " + String.valueOf(compteur_ligne) + " ok : " + line + " \n\n");
				// compteur_ligne++;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (Exception exception)
		{
			System.out.println("Exception " + exception);
			System.exit(0);
		}
	}

	// returns an array of the four words describing a transition
	private String[] readTransition(String line)
	{
		String word[] = new String[4];
		int cword = 0;
		int pos = 0;
		while (pos < line.length() && cword <=3)
		{
			word[cword] = new String();
			while (pos < line.length() && (line.charAt(pos) == ' ' || line.charAt(pos) == '\t'))
			{
				pos++;
			}
			while (pos < line.length() && line.charAt(pos) != ' ' && line.charAt(pos) != '\t')
			{ 
				word[cword] += line.charAt(pos++);
			}
			cword++;
		}
		return word;
	}


	public FSMIO<String, String> getFSM()
	{
		return this.fsms;
	}

	public String toString()
	{
		return this.fsms.toString();
	}


	public static void main(String args[])
	{
		String filename = "automate.txt";
		System.out.println("\n\n Filename ok \n\n");
		FSMIOString fsmio_string = new FSMIOString(filename);
		System.out.println("\n\n FSMIOString creation ok \n\n");

		String output;

		try
		{
			output = fsmio_string.getFSM().doTransition("a");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("b");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("b");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("a");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("a");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("b");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("b");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("a");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("a");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = fsmio_string.getFSM().doTransition("a");
			System.out.println("Etat actuel : " + fsmio_string.getFSM().getCurrentState());
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");
		}
		catch (Exception exception)
		{
			System.out.println("Exception " + exception);
			System.exit(0);
		}
	}
}
