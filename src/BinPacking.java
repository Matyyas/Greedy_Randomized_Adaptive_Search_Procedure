/*
* Nom de classe : BinPacking
*
* Description : donnŽes et algorithmes pour un probl�me de bin-packing
*
* Version : 1.0
*
* Date : 04/02/2013
*
* Auteur : Chams LAHLOU
*/

import java.util.*;
import java.io.*; // pour les acc�s aux fichiers

public class BinPacking {
	private ListeElements Elements;		// ŽlŽments ˆ placer dans les bo”tes
	private int capacite;				// capacitŽ des bo”tes
	private int valeurSolution;			// valeur d'une solution
	private ListeBoites solution;		// liste des bo”tes d'une solution
	
	// constructeur par dŽfaut
	public BinPacking() {
		capacite = 0;
		Elements = null;
		valeurSolution = 0;
		solution = null;
	}

	// constructeur 2 (probl�me avec une bo”te vide au dŽpart)
	public BinPacking(final int capacite) {
		this.capacite = capacite;
		Elements = new ListeElements();
		valeurSolution = 1;
		solution = new ListeBoites();
		solution.ajouterBoite(capacite);
	}

	// constructeur 3 : ˆ partir d'un fichier de donnŽes
	public BinPacking(final String s) {
		System.out.println("lecture du fichier " + s);
    	try {
    		Scanner scanner = new Scanner(new FileReader(s));
    		
    		// lecture de la capacitŽ des bo”tes
    		if (scanner.hasNextInt()) {
    			capacite = scanner.nextInt();
    		}

    		valeurSolution = 1;
    		solution = new ListeBoites();
    		solution.ajouterBoite(capacite);

    		Elements = new ListeElements();

    		// lecture des tailles des ŽlŽments
    		while (scanner.hasNextInt()) {
    			ajouterElement(scanner.nextInt());
    		}
    		
    		scanner.close();
    	}
    	catch (IOException e) {
    		System.err.println("Erreur : " + e.getMessage()) ;
    		System.exit(2) ;
    	}
    }

	public final int getNbElements() {
		return Elements.getNbElements();
	}
		
	public final int getNbBoites() {
		return solution.getNbBoites();
	}
		
	public final int getCapacite() {
		return capacite;
	}
	
	public final ListeElements getElements() {
		return Elements;
	}
		
	// renvoie l'ŽlŽment d'indice i
	public final Element getElement(final int i) {
		return Elements.getElement(i);	
	}

	public final ListeBoites getSolution() {
		return solution;
	}

	// renvoie la bo”te d'indice i
	public final Boite getBoite(final int i) {
		return solution.getBoite(i);
	}
	
	// fixe une solution et sa valeur
	public final void setSolution(final ListeBoites lb) {
		ListeElements l = new ListeElements();
		valeurSolution = borneInf(lb, l);
		solution = lb.copie();
	}
	
	// ajoute un nouvel ŽlŽment au probl�me
	public final void ajouterElement(final int taille) {
		Element e = new Element(getNbElements()+1, taille);
		Elements.ajouterElement(e);
	}

	// affiche le contenu des bo”tes
	public final void afficherSolution() {
		int n = getNbBoites();
        for (int i=1; i <= n; i++) {
        	Boite b = getBoite(i);
        	b.afficher();
        	System.out.println();
        }
    }

	// affiche les ŽlŽments : (numŽro, taille)
	public final void afficherElements() {
		int n = getNbElements();
        for (int i = 1; i <= n; i++) {
        	Element e = getElement(i);
        	System.out.print("(" + e.getNumero() + "," + e.getTaille() + ")  ");
        }
    }

	// EXO 3
	// place l'ŽlŽment numŽro i dans la bo”te numŽro j
	public final void placerElement(final int i, final int j) {
		this.getBoite(j).ajouterElement(this.getElement(i));
	}
	
	// EXO 4
	// calcul d'une borne infŽrieure connaissant une liste de bo”tes
	// et une liste d'ŽlŽments ˆ placer
	// Remarque : si la liste est vide on obtient une valeur exacte de la solution
	public final int borneInf(final ListeBoites lb, final ListeElements le) {
		int aret=0;
		int sum=0;
		int compare=0;
		if(le.getNbElements()==0){
			aret=0;
		}
		else{
			lb.trierPlacesLibresCroissantes();
			for(int i=1;i<le.getNbElements()+1;i++){
				sum+=le.getElement(i).getTaille();
			}
			do{
				aret++;
				if(aret < lb.getNbBoites()+1){
					sum=sum-lb.getBoite(aret).getPlaceLibre();
				}
				else{
					sum=sum-capacite;
				}
			} while(sum>0);
		}
		return aret;
	}

	// EXO 5
	// Algorithme First-Fit appliquŽ ˆ une liste d'ŽlŽments passŽe en param�tre
	// renvoie la liste de bo”tes remplies
	public final ListeBoites algoFirstFit(final ListeElements l) {
		ListeBoites aret = new ListeBoites();
		aret.ajouterBoite(this.capacite);
		boolean test;
		int numBoite;
		for(int i=1;i<l.getNbElements()+1;i++){
			test=true;
			numBoite=1;
			do{
				if(l.getElement(i).getTaille()<aret.getBoite(numBoite).getPlaceLibre()){
					aret.getBoite(numBoite).ajouterElement(l.getElement(i));
					test=false;
				}
				else{
					if(numBoite==aret.getNbBoites()){
						test=false;
						aret.ajouterBoite(this.capacite);
						i--;
					}
					else{
						numBoite++;
					}
				}
			}while (test);
		}
		return aret;
	}

	// EXO 6
	// Algorithme Best-Fit appliquŽ ˆ une liste d'ŽlŽments passŽe en param�tre
	// renvoie la liste des bo”tes remplies
	public final ListeBoites algoBestFit(final ListeElements l) {
		ListeBoites aret = new ListeBoites();
		aret.ajouterBoite(this.capacite);
		boolean test;
		int numBoite;
		int index;
		double bestOccupation;
		double occupation;
		for(int i=1;i<l.getNbElements()+1;i++){
			occupation=0;
			bestOccupation=0;
			numBoite=0;
			test=true;
			index=1;
			do{
				occupation= ((double)l.getElement(i).getTaille())/((double)aret.getBoite(index).getPlaceLibre());
				if(occupation <=1 && occupation > bestOccupation){
					bestOccupation = occupation;
					numBoite=index;
					if(index == aret.getNbBoites()){
						test=false;
						aret.getBoite(index).ajouterElement(l.getElement(i));
					}
					else{
						index++;
					}
				}
				else{
					if(index==aret.getNbBoites()){
						if(numBoite==0){
							test=false;
							aret.ajouterBoite(this.capacite);
							i--;
						}
						else{
							aret.getBoite(numBoite).ajouterElement(l.getElement(i));
							test=false;
							
						}
					}
					else{
						index++;
					}
				}
			}while (test);
		}
		return aret;
		
	}

	
	// procŽdure de recherche par Žvaluation et sŽparation de Eilon et Christofides 1971
	// (appel initial)
	public final void algoSeparationEvaluation() {
		ListeElements le = getElements().copie();
		le.trierTaillesDecroissantes();
		ListeBoites lb = new ListeBoites();
		lb.ajouterBoite(capacite);
		algoSEP(lb, le);
	}

	// EXO 7
	// lBoites = liste des bo”tes utilisŽes
	// lElements = liste des ŽlŽments ˆ placer
	// (appels suivants = partie rŽcursive pour la recherche en profondeur)
	public final void algoSEP(ListeBoites lBoites, final ListeElements lElements) {
		lBoites.trierPlacesLibresCroissantes();
		Element temp;
		ListeBoites save;
		if(lElements.getNbElements()>1){
			for(int i=1;i<lBoites.getNbBoites()+1;i++){
				if(lBoites.getBoite(i).getPlaceLibre()>=lElements.getElement(1).getTaille()){
					save=lBoites.copie();
					lBoites.getBoite(i).ajouterElement(lElements.getElement(1));
					temp=new Element(lElements.getElement(1));
					lElements.supprimerElement(1);
					if(this.borneInf(lBoites,lElements) < this.getSolution().getNbBoites()){
						algoSEP(lBoites,lElements);
					}
					
					lElements.ajouterElement(temp);
					lElements.trierTaillesDecroissantes();
					lBoites=save;
					//nbBoites=lBoites.getNbBoites();
				}
				
			}
			//nbBoites++;
			save=lBoites.copie();
			lBoites.ajouterBoite(capacite);
			lBoites.getBoite(lBoites.getNbBoites()).ajouterElement(lElements.getElement(1));
			temp=new Element(lElements.getElement(1));
			lElements.supprimerElement(1);
			if(this.borneInf(lBoites,lElements) < this.getSolution().getNbBoites()){
				algoSEP(lBoites,lElements);
			}
			lElements.ajouterElement(temp);
			lElements.trierTaillesDecroissantes();
			//lBoites.getBoite(lBoites.getNbBoites()).supprimerElement(temp.getNumero());
			lBoites=save;
			//nbBoites=lBoites.getNbBoites();
		}
		else{
			
				for(int i=1;i<lBoites.getNbBoites()+1;i++){
					
					if(lBoites.getBoite(i).getPlaceLibre()>=lElements.getElement(1).getTaille()){
							save=lBoites.copie();
							lBoites.getBoite(i).ajouterElement(lElements.getElement(1));
							temp=new Element(lElements.getElement(1));
							lElements.supprimerElement(1);
							if(this.getSolution().getNbBoites()+1>lBoites.getNbBoites()){
								this.setSolution(lBoites);
							}
							lElements.ajouterElement(temp);
							lElements.trierTaillesDecroissantes();
							lBoites=save;
						
					}
					
				}
				save=lBoites.copie();
				lBoites.ajouterBoite(capacite);
				lBoites.getBoite(lBoites.getNbBoites()).ajouterElement(lElements.getElement(1));
				temp=new Element(lElements.getElement(1));
				lElements.supprimerElement(1);
				if(this.getSolution().getNbBoites()+1>lBoites.getNbBoites()){
					this.setSolution(lBoites);
				}
				lElements.ajouterElement(temp);
				lElements.trierTaillesDecroissantes();
				//lBoites.getBoite(lBoites.getNbBoites()).supprimerElement(temp.getNumero());
				lBoites=save;
			
		}		
	}
	public void transpose(){
		
	}
}