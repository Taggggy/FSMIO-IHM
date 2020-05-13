import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransitionFunction <T1, T2>{
	private List<Transition<T1, T2>> transitions;

	public TransitionFunction( ) {
		this.transitions = new ArrayList<Transition<T1, T2>>();
	}

	public void addTransition(State orig, T1 input, T2 output, State dest){
		this.transitions.add(new Transition<T1, T2>(orig, new Tag<T1, T2>(input, output), dest));
	}
	
	// Retourne la transition correspondant a l'etat orig et a l'entree input
	public Transition<T1, T2> getTransition(State orig, T1 input) throws ParametresInvalides{
		boolean found = false;
		Transition<T1, T2> t = null;
		Iterator<Transition<T1, T2>> iter = this.transitions.iterator();
		while(iter.hasNext() && !found) 
		{
			t = iter.next();
			if(t.getOrig().equals(orig) && t.getTag().getInput().equals(input))
				found = true;
		}	
		if(found)
			return t;
		else throw new ParametresInvalides();
	}
	
	public List<Transition<T1,T2>> getTransitions() {
		return transitions;
	}
	
	public List<Transition<T1, T2>> getTransitionFromState(State orig) {
		List<Transition<T1,T2>> listeTransitions = new ArrayList<>();
		for(Transition<T1,T2> t : transitions)
		{
			if(t.getOrig().equals(orig))
				listeTransitions.add(t);
		}
		return listeTransitions;
	}
}
