
import java.util.ArrayList;
import java.io.*;


            // IMPORTANT A LIRE

        // Il y a trois public static void main parmi les programmes donnés

        // Le Premice.java répond à la question 3 
        // Le FSMIO.java répond à la question 4
        // Le FSMIOString.java répond à la question 9

        // Les autres fichiers donnés ne sont pas des exécutables


public class Premice
{
    public static void main(String args[])
    {
        String nomEtats[] = new String[3];
        char entrees[] = new char[2];
        nomEtats[0] = "s1";
        nomEtats[1] = "s2";
        nomEtats[2] = "s3";

        entrees[0] = 'a';
        entrees[1] = 'b';

        SimpleFSMIO auto = new SimpleFSMIO(nomEtats, "s1", entrees);
        auto.ajouterTransition("s1", 'a', "s1", 0);
        auto.ajouterTransition ("s1", 'b', "s3", 0);
        auto.ajouterTransition ("s2", 'a', "s1", 0);
        auto.ajouterTransition ("s2", 'b', "s2", 1);
        auto.ajouterTransition ("s3", 'a', "s2", 1);
        auto.ajouterTransition ("s3", 'b', "s3", 1);

        // Mot sur lequel on teste l'automate : "abbaabbaaa"
        
        String etat_actuel = "s1";
        String etat_suivant = auto.getEtatSuivant(etat_actuel, 'a');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'a') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'b');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'b') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'b');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'b') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'a');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'a') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'a');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'a') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'b');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'b') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'b');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'b') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'a');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'a') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'a');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'a') + "\n\n");

        etat_actuel = etat_suivant;
        etat_suivant = auto.getEtatSuivant(etat_actuel, 'a');
        System.out.println("Etat actuel : " + etat_actuel);
        System.out.println("Etat suivant : " + etat_suivant);
        System.out.println("Valeur de sortie : " + auto.getSortie(etat_actuel, 'a') + "\n\n");
    }
}