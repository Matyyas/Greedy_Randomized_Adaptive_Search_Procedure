/*
* Nom de classe : Element
*
* Description : ŽlŽment qui doit �tre placŽ dans une bo”te
*
* Version : 1.0
*
* Date : 04/02/2013
*
* Auteur : Chams LAHLOU
*/

public class Element implements java.lang.Comparable {	// on va utiliser les comparateurs Java 
	private int numero; // identificateur de l'ŽlŽment
	private int taille;	// taille de l'ŽlŽment

	// constructeur par dŽfaut (crŽe un ŽlŽment fictif)
	public Element() {
		numero = 0;
		taille = 0;
	}
	
	// constructeur 2
	public Element(int numero, int taille) {
		this.numero = numero;
		this.taille = taille;
	}
	public Element(Element e){
		this.numero=e.getNumero();
		this.taille=e.getTaille();
	}

	public int getNumero() {
		return numero;
	}

	public int getTaille() {
		return taille;
	}

    /* on a besoin de dŽfinir un ordre pour utiliser les fonctions 
    * "sort()" et "reverseOrder()" de Java.
    * On utilise la taille des ŽlŽments pour les comparer
    */
    public int compareTo(Object obj) {
    	int nombre1 = ((Element) obj).getTaille();
    	int nombre2 = taille;
    	if (nombre1 > nombre2) {
    		return -1;
    	}
    	else if (nombre1 == nombre2) {
    		return 0;
    	}
    	else {
    		return 1;
    	}
    }

}
