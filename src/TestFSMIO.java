
public class TestFSMIO {
	public static void main(String[] args) throws Exception {
		String fichier = "FSMIO TP4.ser";
		State s1 = new State("s1");
		State s2 = new State("s2");
		State s3 = new State("s3");
		
		FSMIO<Character, Integer> auto = new FSMIO<>(s1);
		auto.addState(s2);
		auto.addState(s3);
		
		auto.addTransition(s1, 'a', 0, s1);
		auto.addTransition(s1, 'b', 0, s3);
		auto.addTransition(s2, 'a', 0, s1);
		auto.addTransition(s2, 'b', 1, s2);
		auto.addTransition(s3, 'a', 1, s2);
		auto.addTransition(s3, 'b', 1, s3);
		
		auto.saveObject(fichier);
		
		FSMIO<Character, Integer> testSerialisation = new FSMIO<>("FSMIO TP4.ser");
		
		String test = "abbaabbaaa";
		System.out.println("Test avec la chaine : " + test);
		for(int i = 0; i < test.length(); i++) {
			System.out.print(testSerialisation.doTransition(test.charAt(i)));
			System.out.println("/"+testSerialisation.getCurrentState());
		}
	}
}
