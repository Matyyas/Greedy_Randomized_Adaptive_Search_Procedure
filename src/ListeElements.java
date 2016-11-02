/*
* Nom de classe : ListeElements
*
* Description : liste d'éléments
*
* Version : 1.0
*
* Date : 04/02/2013
*
* Auteur : Chams LAHLOU
*/

import java.util.*;

public class ListeElements {
	private List<Element> liste;	// liste d'éléments
	
	// constructeur par défaut (liste vide)
	public ListeElements() {
		liste = new ArrayList<Element>();	// création d'une liste vide	
	}
	
	public int getNbElements() {
		return liste.size();
	}

	// renvoie l'élément d'indice i
	public Element getElement(int i) {
		return liste.get(i-1);	// les listes démarrent à l'indice 0 mais les éléments à l'indice 1
	}
	
	public void ajouterElement(Element e) {
		liste.add(e);
	}
	
	// supprime l'élément d'indice i
	public void supprimerElement(int i) {
		liste.remove(i-1);	// les indices commencent à 0 dans une liste
	}
	public void supprimerElement(Element e){
		boolean test=true;
		for(int i = 0; i < this.getNbElements() && test; i++){
			if(liste.get(i).getNumero() == e.getNumero()){
				liste.remove(i);
				test = false;
			}
		}
	}
	
	public ListeElements copie() {
		ListeElements l = new ListeElements();
		int n = getNbElements();
		for (int i = 1; i <= n; i++) {
			Element e = getElement(i);
			Element e2 = new Element(e.getNumero(), e.getTaille());
			l.ajouterElement(e2);
		}
		return l;
	}
	
	// trie la liste selon les tailles décroissantes
    public void trierTaillesDecroissantes() {
        Collections.sort(liste, Collections.reverseOrder());
    }

	public void afficher() {
    	System.out.print("( ");
    	int n = getNbElements();
        for (int i = 1; i <= n; i++) {
        	Element e = getElement(i);
        	System.out.print(e.getNumero() + ",");
        }
    	System.out.print(")");
	}
}
