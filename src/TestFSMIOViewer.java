
public class TestFSMIOViewer {
	public static void main(String argv[]) {
		//Classe principale de test
		//Ouvre chaque version de l'application
		FSMIOStringViewer testString = new FSMIOStringViewer();
		FSMIOViewer<Character, Integer> testGenerique = new FSMIOViewer<>();
		FSMIOViewerEditable<Character, Integer> testGeneriqueEditable = new FSMIOViewerEditable<>();
	}
}
