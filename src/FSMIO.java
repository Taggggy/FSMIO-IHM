import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FSMIO<T1, T2> implements Serializable{
	private List<State> states;
	private TransitionFunction<T1, T2> tf ;
	private State currentState;	
	private State initialState;

	// Constructors
	public FSMIO(List<State> states, State init) {
		this.states = states;
		this.tf = new TransitionFunction<T1, T2>();
		this.currentState = this.initialState = init;
	}

	public FSMIO(State init){
		this.currentState = this.initialState = init;
		this.states = new ArrayList<State>();
		this.states.add(init);
		this.tf = new TransitionFunction<T1, T2>();
	}	
	
	public FSMIO(String filename) {
		ObjectInputStream ois = null;
		
		try {
			final FileInputStream fichier = new FileInputStream(filename);
			ois = new ObjectInputStream(fichier);
			FSMIO<T1,T2> auto = (FSMIO<T1,T2>) ois.readObject();
			this.states = auto.states;
			this.tf = auto.tf;
			this.currentState = auto.currentState;
			this.initialState = auto.initialState;
		} catch(final java.io.IOException e) {
			e.printStackTrace();
		} catch(final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		  try {
			  if (ois !=null) {
				 ois.close();
			  }
			 } catch(final IOException ex) {
				 ex.printStackTrace();
			 }
		}
	}

	public void addTransition(State orig, T1 input, T2 output, State dest) throws Exception{
		boolean containsOrig = false;
		boolean containsDest = false;
		for(State s : states)
		{
			if(s.getName().equals(orig.getName())) containsOrig = true;
			if(s.getName().equals(dest.getName())) containsDest = true;
		}
		if(containsOrig && containsDest)
			tf.addTransition(orig, input, output, dest);
		else throw new ParametresInvalides();
	}
	
	public boolean addState(State s) throws Exception{		
		boolean done = false;
		if(!this.states.contains(s)){
			this.states.add(s);
			done = true;
			
			if(initialState == null)
				initialState = s;
		}
		else throw new ParametresInvalides();
		return done;
	}

	public void reset(){
		currentState = initialState;
	}

	public T2 doTransition(T1 input) throws Exception{	
		try
		{
			Transition<T1, T2> nt = tf.getTransition(currentState, input); 
			currentState = nt.getDest();
			return nt.getTag().getOutput();
		} catch(ParametresInvalides e)
		{
			throw e;
		}
	}
	
	public TransitionFunction<T1,T2> gettf()
	{
		return tf;
	}
	
	public List<Transition<T1, T2>> getTransitionFromState(State orig) {
		return tf.getTransitionFromState(orig);
	}

	public String toString(){
		String toReturn = new String("");
		for(State s : states) {
			//System.out.println(toReturn);
			//System.out.println(s);
			if(!toReturn.contains(s.toString()))
				toReturn += s.toString() + " ";
		}
		toReturn += "]\n\n" + tf.toString();
		return "States : [" + toReturn;
	}
	
	public List<State> getStates()
	{
		return states;
	}
	
	public State getInitialState()
	{
		return initialState;
	}
	
	public State getCurrentState()
	{
		return currentState;
	}
	
	public void saveObject(String filename)
	{
		ObjectOutputStream oos = null;
		
		try{
			FileOutputStream fichier = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(this);
			oos.flush();
			} catch(final java.io.IOException e) {
				e.printStackTrace();
			} finally {
			  try {
				  if(oos != null)
				  {
					  oos.flush();
					  oos.close();
				  }
			   } catch(final IOException ex) {
				   ex.printStackTrace();
				   }
			}
	}
	
	public void removeState(State s)
	{
		states.remove(s);
		for(Iterator<State> it = states.iterator(); it.hasNext();)
		{
			State state = it.next();
			if(state.getName().equals(s.getName()) || state.getName().equals(s.getName()))
				it.remove();
		}
		for(Iterator<Transition<T1,T2>> it = tf.getTransitions().iterator(); it.hasNext();)
		{
			Transition<T1,T2> t = it.next();
			if(t.getOrig().getName().equals(s.getName()) || t.getDest().getName().equals(s.getName()))
				it.remove();
		}
		if(s.getName().equals(initialState.getName()) && states.size() != 0)
			initialState = states.get(0);
		else if(s.getName().equals(initialState.getName()))
			initialState = null;
		reset();
	}
	
	public void removeTransition(State orig, Tag<T1,T2> tag, State dest)
	{
		tf.removeTransition(orig, tag, dest);
	}
}