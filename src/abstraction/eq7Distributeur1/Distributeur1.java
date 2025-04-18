package abstraction.eq7Distributeur1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.awt.Color;

import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IDistributeurChocolatDeMarque;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.appelDOffre.IAcheteurAO;
import abstraction.eqXRomu.clients.ClientFinal;
import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.IAcheteurContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eqXRomu.general.Variable;

public class Distributeur1 extends Distributeur1AcheteurAppelOffre implements IDistributeurChocolatDeMarque {
	
	// défi 1 et 2 par Alexiho
	protected Journal journal;  // Déclaration du journal
	// protected Map<ChocolatDeMarque, Variable> stocksChocolats; // Table de hachage pour stocker les quantités de chocolat
	//protected List<ChocolatDeMarque> chocolats;
	protected List<Double> prix;
	protected List<Double> capaciteDeVente;
	protected IAcheteurAO identity;
	protected List<Integer> successedSell = new ArrayList<Integer>();
	protected List<Double> priceProduct = new ArrayList<Double>();
	protected List<Double> requiredQuantities  = new ArrayList<Double>();
	protected int step = 0;
	protected String name = "HexaFridge";
	protected Color color = new Color(162, 207, 238);
	


    public Distributeur1() {
		super();
        
        this.journal = new Journal("Journal de EQ7", this); // Initialisation du journal
		for (int i=0; i<6; i++){
			successedSell.add(0);
			priceProduct.add(1000.0);
			requiredQuantities.add(0.0);
		}
		predictionsVentesPourcentage = Arrays.asList(3.6 , 3.6 , 5.0 , 3.6 , 3.6 , 3.6 , 3.6 , 7.0 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 3.6 , 13.0);
		/*
        this.stocksChocolats = new HashMap<>();
        
        // Initialisation des stocks à 0.0

		this.chocolats = new ArrayList<ChocolatDeMarque>();
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_HQ_BE, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_HQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_MQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_MQ, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_BQ_E, "Hexafridge", 50));
		this.chocolats.add(new ChocolatDeMarque(Chocolat.C_BQ, "Hexafridge", 50));
		*/
		this.prix = new ArrayList<Double>();
		this.capaciteDeVente = new ArrayList<Double>();

		for (int i=0; i<this.chocolats.size(); i++) {
			this.prix.add(10.0);
			this.capaciteDeVente.add(0.0);
			this.stocksChocolats.put(chocolats.get(i), new Variable(this.getNom()+"Stock"+chocolats.get(i).getNom(), this, 1000.0));
		}

		for (int i=0; i<this.chocolats.size(); i++) {
			this.capaciteDeVente.set(i, stocksChocolats.get(chocolats.get(i)).getValeur()/2);
		}
    }

	public double prix(ChocolatDeMarque choco) { // par Alexiho
		ChocolatDeMarque chocoM = new ChocolatDeMarque(choco.getChocolat(), "Villors", 50);
		int pos= (chocolats.indexOf(chocoM));
		if (pos<0) {
			return 8880.0;
		} else {
			//return prix.get(pos);
			double price = 1.08*this.priceProduct.get(pos) ;
			return price;
		}
	}

	public void next() // par Alexiho
	{
		List<Double> requiredQuantities = new ArrayList<>();
		Distributeur1Stock acteurStock = new Distributeur1Stock();
		int step = Filiere.LA_FILIERE.getEtape(); // Récupération du numéro de l'étape
		for (int i=0; i<6; i++){
			requiredQuantities.add(acteurStock.VolumetoBuy(chocolats.get(i),this.cryptogramme)*0.95);
		}
		
		if (step%8==0){
			//IAcheteurContratCadre acheteurContratCadre = new Distributeur1AcheteurContratCadre();
			this.next_cc();
			for (int i = 0 ; i<6 ; i++){
				requiredQuantities.set(i, requiredQuantities.get(i)/19);
			}}
		
		//IAcheteurAO acheteurAppelOffre = new Distributeur1AcheteurAppelOffre();
		this.next_ao();
		

		//ChocolatDeMarque produit = new ChocolatDeMarque(Chocolat.C_MQ, "Villors", 50); // Produit choisi
        //double quantiteAjoutee = 100.0; // 100 tonnes

		//capaciteDeVente.set(3, 100.0);

        //journal.ajouter("Étape " + etape + " : Ajout de " + quantiteAjoutee + " t de " + produit + " en rayon.");
		
		// définition des capacités de ventes

		for (int i=0; i<this.chocolats.size(); i++) {
			this.capaciteDeVente.set(i, stocksChocolats.get(chocolats.get(i)).getValeur()/2);
		}
	}

	public List<String> getMarquesChocolat() { // par Alexiho
		List<String> marques = new ArrayList<String>();
		marques.add("Hexafridge");
		return marques;
	}

	public double quantiteEnVente(ChocolatDeMarque choco, int crypto) { // par Alexiho
		if (crypto!=this.cryptogramme && false) {
			journal.ajouter("Quelqu'un essaye de me pirater !");
			return 0.0;
		} else {
			//int pos= (chocolats.indexOf(choco));
			ChocolatDeMarque chocoM = new ChocolatDeMarque(choco.getChocolat(), "Villors", 50);
			int pos= (chocolats.indexOf(chocoM));
			//journal.ajouter("pos : " + pos + "chocoval : " + this.getStock(chocoM).getValeur() + "capa : " + capaciteDeVente.get(pos));
			if (pos<0) {
				return 0.0;
			} else {
				return Math.min(capaciteDeVente.get(pos), this.getStock(chocoM).getValeur());
			}
		}
	}

	// On met 10% de ce tout ce qu'on met en vente (on pourrait mettre l'accente sur
	// un produit a promouvoir mais il s'agit ici d'un exemple simpliste
	public double quantiteEnVenteTG(ChocolatDeMarque choco, int crypto) { // par Alexiho
		if (crypto!=this.cryptogramme && false) {
			journal.ajouter("Quelqu'un essaye de me pirater !");
			return 0.0;
		} else {
			//int pos= (chocolats.indexOf(choco));
			ChocolatDeMarque chocoM = new ChocolatDeMarque(choco.getChocolat(), "Villors", 50);
			int pos= (chocolats.indexOf(chocoM));
			if (pos<0) {
				return 0.0;
			} else {
				return Math.min(capaciteDeVente.get(pos), this.getStock(chocoM).getValeur())/10.0;
			}
		}
	}

	public void vendre(ClientFinal client, ChocolatDeMarque choco, double quantite, double montant, int crypto) { // par Alexiho
		int pos= (chocolats.indexOf(choco));
		if (pos>=0) {
			this.getStock(choco).retirer(this, quantite);
		}
	}

	public Variable getStock(ChocolatDeMarque c) { // par Alexiho
		return this.stocksChocolats.get(c);
	}

	public Map<ChocolatDeMarque, Variable> getStocksChocolats() { // par Alexiho
		return this.stocksChocolats;
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) { // par Alexiho
		if (this.cryptogramme==cryptogramme) {
			for (ChocolatDeMarque c : this.stocksChocolats.keySet()) {
				if (c.equals(p)) {
					return this.stocksChocolats.get(c).getValeur();
				}
			}
			return 0;
		} else {
			return 0;
		}
	}

	public void notificationRayonVide(ChocolatDeMarque choco) {
		journal.ajouter(" Aie... j'aurais du mettre davantage de "+choco.getNom()+" en vente");
	}

	public void notificationRayonVide(ChocolatDeMarque choco, int crypto)
	{
		journal.ajouter(" Aie... j'aurais du mettre davantage de "+choco.getNom()+" en vente");
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() { // par Alexiho
		List<Journal> res=new ArrayList<Journal>();
		res.add(journal);
		return res;
	}
	
}