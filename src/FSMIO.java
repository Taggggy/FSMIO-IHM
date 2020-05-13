import java.util.ArrayList;
import java.util.List;

public class FSMIO<T1, T2> {
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

	public void addTransition(State orig, T1 input, T2 output, State dest) throws Exception{
		if(states.contains(orig) && states.contains(dest))
			tf.addTransition(orig, input, output, dest);
		else throw new ParametresInvalides();
	}
	public boolean addState(State s) throws Exception{		
		boolean done = false;
		if(!this.states.contains(s)){
			this.states.add(s);
			done = true;
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
		return tf.toString();
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
}