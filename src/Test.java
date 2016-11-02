/*
* Nom de classe : Test
*
* Description :
*
* Version : 1.0
*
* Date : 04/02/2013
*
* Auteur : Chams LAHLOU
*/

public class Test {

	public static void main(final String[] args)
    {
		// test de la classe Element
		Element e1 = new Element(1,25);
		Element e2 = new Element(2,8);
		
		int t1 = e1.getTaille();
		int t2 = e2.getTaille();
		
		System.out.println("Taille de l'élément " + e1.getNumero() + " : " + t1);
		System.out.println("Taille de l'élément " + e2.getNumero() + " : " + t2);
		
		// test de la classe Boite
		Boite b1 = new Boite(1, 25);
		Boite b2 = new Boite(2, 25);
		
		b1.ajouterElement(e1);
		b1.ajouterElement(e2);
		b1.afficher();
		
		// test de la classe BinPacking
		
/*		// lecture + affichage
		System.out.println();
		BinPacking BP = new BinPacking("essai.txt");
		BP.afficherSolution();
		
		// listeElements + tri
		ListeElements l1 = BP.getElements();
		l1.afficher();
		ListeElements l2 = l1.copie();
		l2.trierTaillesDecroissantes();
		l2.afficher();
		l1.afficher();
		ListeElements l3 = l2.copie();
		l3.afficher();*/
		
		// algo first-fit
//		ListeBoites lb1 = BP.algoFirstFit(BP.getElements()); 
//		System.out.print("First-Fit : nombre de bo”tes : " + BP.valeur(lb1));

		// algo best-fit
//		ListeBoites lb1 = BP.algoBestFit(BP.getElements()); 
//		System.out.print("Best-Fit : nombre de bo”tes : " + BP.valeur(lb1));
		
		// listeBoites
/*		lb1.afficher();
		System.out.println();
		ListeBoites lb2 = lb1.copie();
		lb2.afficher();
*/		
		// Evaluation/SŽparation
		System.out.println();
		BinPacking BP2 = new BinPacking("HARD0.txt");
		ListeBoites lb1 = BP2.algoBestFit(BP2.getElements());
		BP2.setSolution(lb1);
		System.out.println("Solution obtenue avec l'algorithme FirstFit");
		BP2.afficherSolution();
		BP2.algoSeparationEvaluation();
		System.out.println("Solution obtenu avec l'algorithme Séparation-Evaluation");
		BP2.afficherSolution();//*/
    }
}