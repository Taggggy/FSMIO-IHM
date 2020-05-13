
public class SimpleFSMIO
{
	private SimpleFonctionTransition t;
	private String etatInit;
	private String etatCourant;
	private String etats[];
	private char entrees[];

	
	public SimpleFSMIO(String etats[], String etatInit, char entrees[])
	{
		t = new SimpleFonctionTransition(etats, entrees);
		this.etats = etats;
		this.entrees = entrees;
		this.etatCourant = this.etatInit = etatInit;
	}
	
	public void ajouterTransition(String etatOrig, char valEntree, String etatDest, int sortie)
	{
		boolean etatOrigValide = false;
		boolean etatDestValide = false;
		boolean entreeValide = false;
		
		for(int ie = 0; ie < this.etats.length && (!etatOrigValide || !etatDestValide); ie++)
		{
			if(etatOrig.equals(this.etats[ie])) etatOrigValide = true;
			if(etatDest.equals(this.etats[ie])) etatDestValide = true;
		}
		
		for(int i = 0; i < this.entrees.length && !entreeValide; i++)
		{
				entreeValide = (valEntree == this.entrees[i]);
		}
		
		if (etatOrigValide && etatDestValide && entreeValide)
		{
			this.t.ajouterTransition(etatOrig, valEntree, etatDest, sortie);
		}
	}
	
	public int faireTransition(char val)
	{
		// Normalement c'est inutile car getSortie et getEtatSuivant catch déjà l'exception
		// Mais vu les répétitions dans la nomenclature, nous préférerons ne prendre aucun risque
		int sortie = -1;

		try
		{
			sortie = this.t.getSortie(etatCourant, val);
			this.etatCourant = this.t.getEtatSuivant(etatCourant, val);
		}
		catch (Exception exception)
		{
			System.out.println("\n\n Exception " + exception);
			System.exit(0);
		}

		return sortie;
	}
	
	public void reset()
	{
		this.etatCourant = this.etatInit;
	}
	
	public String getEtatCourant()
	{
		return this.etatCourant;
	}
	
	public String getEtatSuivant(String s, char e)
	{
		String es = "";
		try
		{
			// this.t.getEtatSuivant(s, e)
			es = t.getEtatSuivant(s, e);
		}
		catch (Exception exception)
		{
			System.out.println("\n\n Exception " + exception);
			System.exit(0);
		}

		return es;
	}
	
	public int getSortie(String s, char e)
	{
		int es = 0;
		try
		{
			es = t.getSortie(s, e);
		}
		catch (Exception exception)
		{
			System.out.println("\n\n Exception " + exception);
			System.exit(0);
		}
		return es;
	}
}
