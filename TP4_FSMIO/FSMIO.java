
import java.util.*;
import java.io.*;

public class FSMIO<T1, T2>
{
	private List<State> states;
	private TransitionFunction<T1, T2> tf ;
	private State currentState;	
	private State initialState;

	// Constructors
	public FSMIO(List<State> states, State init)
	{
		this.states = states;
		this.tf = new TransitionFunction<T1, T2>();
		this.currentState = this.initialState = init;
	}

	public FSMIO(State init)
	{
		this.currentState = this.initialState = init;
		this.states = new ArrayList<State>();
		this.states.add(init);
		this.tf = new TransitionFunction<T1, T2>();
	}

	// Fonction rajoutée pour la bonne utilisation de FSMIOString
	public State getCurrentState()
	{
		return this.currentState;
	}

	// On veut déclencher une Exception, pas en capturer une !!
	public void addTransition(State orig, T1 input, T2 output, State dest) throws Exception
	{
		if( (!this.states.contains(orig)) || (!this.states.contains(dest)) )
		{
			// Déclencher une Exception états non existants 
			throw new Exception(" Etats non existants Exception \n\n");
		}
		else
		{
			tf.addTransition(orig, input, output, dest);
		}
	}

	public boolean addState(State s)
	{
		boolean done = false;
		if(!this.states.contains(s))
		{
			this.states.add(s);
			done = true;
		}
		return done;
	}

	public void reset()
	{
		currentState = initialState;
	}




	// public int faireTransition(char val)
	// {
	// 	int sortie = this.t.getSortie(etatCourant, val);
	// 	this.etatCourant = this.t.getEtatSuivant(etatCourant, val);
	// 	return sortie;
	// }

	// nt est une Transition(State eorig, Tag<T1, T2> t, State edest)
	// Avec Tag<T1, T2> t : T1 input; T2 output;
	public T2 doTransition(T1 input) throws Exception
	{
		T2 output;
		//Transition<T1, T2> nt = new Transition(new State("useless"), new Tag<T1, T2>(input, ???), new State("pythonCmieux"));
		// Meilleur moyen de l'initialiser vide sans se prendre la tête 
		Transition<T1, T2> nt = null;

		// Si besoin, la méthode tf.getTransition déclenche l'exception Transition Inexistante
		try
		{
			nt = tf.getTransition(currentState, input);			
		}
		catch (Exception exception)
		{
			System.out.println("Exception " + exception);
			System.exit(0);
		}
		
		output = nt.getTag().getOutput();
		this.currentState = nt.getDest();
		// System.out.println(" New current state : " + this.currentState);

		return output;
	}

	public String toString()
	{
		return tf.toString();
	}


	public static void main(String args[])
	{
		State state_1 = new State("s1");
		State state_2 = new State("s2");
		State state_3 = new State("s3");
		List<State> auto_states = new ArrayList<State>();

		auto_states.add(state_1);
		auto_states.add(state_2);
		auto_states.add(state_3);

		FSMIO<Character, Integer> auto = new FSMIO(auto_states, state_1);
		// La fonction qui executera les transitions sera auto.tf

		// Creation puis ajout de toutes les transitions 
		try
		{
			auto.addTransition(state_1, 'a', 0, state_1);
			auto.addTransition(state_1, 'b', 0, state_3);
			auto.addTransition(state_2, 'a', 0, state_1);
			auto.addTransition(state_2, 'b', 1, state_2);
			auto.addTransition(state_3, 'a', 1, state_2);
			auto.addTransition(state_3, 'b', 1, state_3);
		}
		catch (Exception exception)
		{
			System.out.println("Exception " + exception);
			System.exit(0);
		}


		// Execution de l'automate sur le mot "abbaabbaaa"

		Integer output;
		
		try
		{
			output = auto.doTransition('a');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('b');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('b');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('a');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('a');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('b');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('b');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('a');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('a');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");

			output = auto.doTransition('a');
			System.out.println("Etat actuel : " + auto.currentState);
			System.out.println("Sortie obtenue : " + String.valueOf(output) + "\n\n");
		}
		catch (Exception exception)
		{
			System.out.println("Exception " + exception);
			System.exit(0);
		}
	}
}