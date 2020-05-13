
import java.util.ArrayList;
import java.io.*;


public class SimpleFonctionTransition
{
	private ArrayList<Character> entrees;
	private ArrayList<Integer> sorties;

	private ArrayList<String> nomsEtats;
	private ArrayList<String> etatsOrigine;
	private ArrayList<String> etatsDestination;

	public SimpleFonctionTransition(String nomsEtats[], char entrees[])
	{
		int index;
		
		this.entrees = new ArrayList<Character>();
		
		for (index = 0; index < entrees.length; index++)
		{
			this.entrees.add(entrees[index]);
		}
		this.sorties = new ArrayList<Integer>();

		this.nomsEtats = new ArrayList<String>();
		for (index = 0; index < nomsEtats.length; index++)
		{
			this.nomsEtats.add(nomsEtats[index]);
		}
		this.etatsOrigine = new ArrayList<String>();
		this.etatsDestination = new ArrayList<String>();
	}
	
	public void ajouterTransition(String etatOrig, char entree, String etatDest, int sortie)
	{
		this.entrees.add(entree);
		this.sorties.add(sortie);
		this.etatsOrigine.add(etatOrig);
		this.etatsDestination.add(etatDest);
	}
	
	public String getEtatSuivant(String etatOrig, char entree) throws Exception
	{
		int index = 0;
		String to_return = "";
		boolean is_found = false;

		while (index < this.etatsOrigine.size())
		{
			if (this.etatsOrigine.get(index).equals(etatOrig) && this.entrees.get(index) == entree)
			{
				to_return = this.etatsDestination.get(index);
				is_found = true;
			}

			index++;
		}

		if (is_found == false)
		{
			// Déclencher une Exception paramètres donnés incorrects
			throw new Exception(" Paramètres donnés incorrects dans getEtatSuivant \n\n");
		}
		else
		{
			return to_return;
		}
	}
	
	public int getSortie(String etatOrig, char entree) throws Exception
	{
		int index = 0;
		int to_return = -1;
		boolean is_found = false;

		while (is_found == false && index < this.entrees.size())
		{
			if (this.etatsOrigine.get(index).equals(etatOrig) && this.entrees.get(index) == entree)
			{
				to_return = this.sorties.get(index);
				is_found = true;
			}

			index++;
		}

		if (is_found == false)
		{
			// Déclencher une Exception paramètres donnés incorrects
			throw new Exception(" Paramètres donnés incorrects dans getSortie \n\n");
		}
		else
		{
			return to_return;
		}
	}
}