import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * class Grasp
 * Classe d'éxécution de la méthode Grasp implémentée pour le cours de RO2.
 */
public class Grasp {
	public BinPacking bp;
	public int borneInf;
	public int soluce;
	public int capacite;
	public Grasp(){}
	public int count;

	public Grasp(BinPacking bp){
		this.bp = bp;
		this.borneInf = bp.borneInf(new ListeBoites(),bp.getElements());
		this.bp.setSolution(this.bp.algoFirstFit(this.bp.getElements()));
		this.soluce = this.bp.getNbBoites();	
		this.capacite = this.bp.getCapacite();
	}
	/*
	 * grasp() fait appel à la méta-heuristique Grasp
	 * @param alpha : Critère de sélection
	 */
	public void grasp(double alpha) {
		count = 0;
		bp.setSolution(bp.algoBestFit(bp.getElements()));
		long debut = System.currentTimeMillis(); 
		long fin = System.currentTimeMillis();

		while (fin < debut + 60000) {
			ListeBoites lb = this.construction(alpha);
			this.recherche_local(lb);
			if(lb.getNbBoites()<this.soluce){
				this.soluce=lb.getNbBoites();
				this.bp.setSolution(lb);
			}
			fin = System.currentTimeMillis();
		}
	}
	/*
	 * Permet de construire une solution
	 * @param alpha : Critère de sélection
	 * @return lb : liste de boites solution
	 * 
	 */
	public ListeBoites construction(double alpha){
		ListeElements le = bp.getElements().copie();
		ListeElements le2 = le.copie();
		ListeBoites lb = new ListeBoites();
		lb.ajouterBoite(capacite);
		List<Element> rcl = new ArrayList<Element>();
		/*List<Integer> index = new ArrayList<Integer>();
		for(int i = 1; i < le.getNbElements()+1;i++){
			index.add(i);
		}
		Collections.shuffle(index);*/
		int nb = 0;
		le2.trierTaillesDecroissantes();
		for(int i = 1; i < le.getNbElements(); i++){
			lb.getBoite(1).ajouterElement(le2.getElement(i));
			Element e = le2.getElement(i);
			le.supprimerElement(e);
			if(e.getTaille() >= le2.getElement(1).getTaille() - alpha*(le2.getElement(1).getTaille() - le2.getElement(le2.getNbElements()).getTaille())&& nb < 70){
				nb++;
				rcl.add(e);			
			}
			le.ajouterElement(e);
			lb.getBoite(1).supprimerElement(e.getNumero());
		}
		int random = (int) (Math.random() * rcl.size());
		Element retenu = rcl.get(random);		
		le.supprimerElement(retenu);
		lb.getBoite(1).ajouterElement(retenu);
		count++;
		this.construction_recursif(lb,le,alpha);
		le=le2;
		return lb;
	}
	public void recherche_local(ListeBoites lb){
		lb.trierPlacesLibresDecroissantes();
		for(int i=2;i<lb.getNbBoites();i++){// je prends une boite i différente de la boite 1
			for(int j=1;j<lb.getBoite(i).getNbElements();j++){// pour chacun de ses éléments, je commence par en sélectionner un
				for(int m=i+1;m<lb.getNbBoites();m++){// je regarde dans les autres boites que la 1 et que la i -> modifier les conditions de la boucle
					if(lb.getBoite(i).getElement(j).getTaille()<=lb.getBoite(m).getPlaceLibre()){ // si je peux ajouter cet element
						lb.getBoite(m).ajouterElement(lb.getBoite(i).getElement(j));
						lb.getBoite(i).supprimerElement(lb.getBoite(i).getElement(j).getNumero());
						
						for(int k=1;k<=lb.getBoite(1).getNbElements();k++){// une fois ajouter je regarde si je peux vider un des éléments de la boite 1 dans la boite i
							if(lb.getBoite(1).getElement(k).getTaille()<=lb.getBoite(i).getPlaceLibre()){
								lb.getBoite(i).ajouterElement(lb.getBoite(1).getElement(k));
								lb.getBoite(1).supprimerElement(lb.getBoite(1).getElement(k).getNumero());
								
							}
						}

					}
				}
				
			}
		}


	}
	public void construction_recursif(ListeBoites lb, ListeElements le,double alpha){
		ListeElements le2 = le.copie();
		List<Element> rcl = new ArrayList<Element>();
		/*List<Integer> index = new ArrayList<Integer>();
		/*for(int i = 1; i < le.getNbElements()+1;i++){
			index.add(i);
		}
		Collections.shuffle(index);*/
		int nb = 0;
		le2.trierTaillesDecroissantes();
		for(int i =1; i < le2.getNbElements()+1; i++){
			Element e = le2.getElement(i);
			if(e.getTaille() >= le2.getElement(1).getTaille() - alpha*(le2.getElement(1).getTaille() - le2.getElement(le2.getNbElements()).getTaille())&& nb < 70){
				nb++;
				rcl.add(e);
			}
		}
		int random = (int) (Math.random() * rcl.size());
		Element retenu = rcl.get(random);
		le.supprimerElement(retenu);
		count++;
		this.ajouter_best_fit(retenu,lb);
		if(le.getNbElements()!=1){
			this.construction_recursif(lb,le,alpha);
		}
		else{
			le.trierTaillesDecroissantes();
			this.ajouter_best_fit(le.getElement(1), lb);
		}
	}

	public void ajouter_best_fit(Element e, ListeBoites lb){
		double occupation=0;
		double bestOccupation=0;
		int numBoite=0;
		boolean test=true;
		int index=1;
		do{
			occupation= ((double)e.getTaille())/((double)lb.getBoite(index).getPlaceLibre());
			if(occupation <=1 && occupation > bestOccupation){
				bestOccupation = occupation;
				numBoite=index;
				if(index == lb.getNbBoites()){
					test=false;
					lb.getBoite(index).ajouterElement(e);
				}
				else{
					index++;
				}
			}
			else{
				if(index==lb.getNbBoites()){
					if(numBoite==0){
						test=false;
						lb.ajouterBoite(capacite);
						lb.getBoite(index+1).ajouterElement(e);
					}
					else{
						lb.getBoite(numBoite).ajouterElement(e);
						test=false;

					}
				}
				else{
					index++;
				}
			}
		}while (test);
	}
	public static void main(String[] args) {
		Grasp test = new Grasp(new BinPacking("HARD3.txt"));
		/*ListeBoites soluce = test.construction(0.2);
		soluce.trierPlacesLibresCroissantes();
		soluce.afficher();
		System.out.println("Solution du FirstFit : "+test.soluce);
		System.out.println("Solution du Grasp : "+soluce.getNbBoites());*/


		///*
		test = new Grasp(new BinPacking("HARD3.txt"));
		/*ListeElements le = test.bp.getElements().copie();
		ListeElements le2 = le.copie();
		ListeBoites lb = new ListeBoites();
		lb.ajouterBoite(test.capacite);
		for(int i = 1; i < le.getNbElements() + 1; i++){
			Element e = le2.getElement(i);
			lb.getBoite(1).ajouterElement(e);
			le.supprimerElement(e);
			System.out.println(test.bp.borneInf(lb, le));
			le.ajouterElement(e);
			lb.getBoite(1).supprimerElement(i);
			System.out.println();
			lb.afficher();

		}
		//*/
		test.grasp(1);
		test.bp.afficherSolution();
		System.out.println();
		System.out.println("Solution du Grasp après 10 minutes : "+test.soluce);
		//*/

	}
}
