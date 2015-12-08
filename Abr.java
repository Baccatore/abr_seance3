import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Abr {

	// Variable de classe utilisée pour le comptage
	// des comparaisons effectuées lors des opérations de
	// recherche.
	private static int nbComparaisons;

	// Classe interne utilisée pour définir les noeuds de l'arbre binaire
	// de recherche
	class Node {
		String elt; // L'information-clé portée par chaque neoud
		Node gauche; // Accès au noeud-racine du ss-arbre gauche
		Node droit; // Accès au noeud-racine du ss-arbre droit

		public Node(String elt) { // 1er constructeur de noeud
			this.elt = new String(elt);
			this.gauche = this.droit = null;
		}

		public Node(String elt, Node g, Node d) { // 2nd constructeur de noeud
			this.elt = new String(elt);
			this.gauche = g;
			this.droit = d;
		}

		@Override // Méthode utilisée lors de l'affichage d'un noeud
		public String toString() {
			return "\"" + elt.replace('_', ' ') + "\"";
		}
	}

	// -------------------------------------------------------------------- //
	// Attribut(s) //
	// -------------------------------------------------------------------- //
	private Node root; // Accès au noeud racine de l'arbre binaire
						// de recherche

	// -------------------------------------------------------------------- //
	// Constructeur(s) //
	// -------------------------------------------------------------------- //

	public Abr() { // Constructeur
		root = null;
	}

	/**
	 * Constructeur par copie <i>profonde</i>
	 * 
	 * @param root
	 */
	public Abr(Abr a) {
		if (a == null)
			root = null;
		else
			root = abrCopy(a.root);
	}

	// -------------------------------------------------------------------- //
	// Getter()s/Setter() //
	// -------------------------------------------------------------------- //
	/**
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(Node root) {
		this.root = root;
	}

	/**
	 * @return the nbComparaisons
	 */
	public static int getNbComparaisons() {
		return nbComparaisons;
	}

	// -------------------------------------------------------------------- //
	// M É T H O D E S P U B L I Q U E S //
	// -------------------------------------------------------------------- //
	public boolean isEmpty() {
		return root == null;
	}

	public void insert(String e) {
		root = abrInsert_v2(root, e);
	}

	public int size() {
		return abrNb(root);
	}

	public int nbLeafs() {
		return abrNbFeuilles(root);
	}

	public int height() {
		return abrHauteur(root);
	}

	public String min() {
		return abrMin(root);
	}

	public String max() {
		return abrMax(root);
	}

	public boolean isMember(String e) {
		nbComparaisons = 0;
		return abrFind(root, e) != null;
	}

	public boolean equals(Abr a) {
		return abrEquals(root, a.root);
	}

	public void infixePrint(String prompt) {
		System.out.print(prompt + " ");
		abrInfixePrint(root);
		System.out.println();
	}

	public void postfixePrint(String prompt) {
		System.out.print(prompt + " ");
		abrpostfixePrint(root);
		System.out.println();
	}

	public void prefixePrint(String prompt) {
		System.out.print(prompt + " ");
		abrprefixePrint_v3(root);
		System.out.println();
	}

	public void largeurPrint(String prompt) {
		System.out.print(prompt + " ");
		abrLargeurPrint(root);
		System.out.println();
	}

	public void dotPrint(PrintWriter pw) {
		pw.println("digraph abr {\n\tbgcolor = grey90;");
		pw.println("\tnode [");
		pw.println("\t\tfontname = Consolas, ");
		pw.println("\t\tfontsize = 14, ");
		pw.println("\t\tfontcolor = \"#ee1010\", ");
		pw.println("\t\tpenwidth = 2, ");
		pw.println("\t\tshape = record, ");
		pw.println("\t\tcolor = blue, ");
		pw.println("\t\tfillcolor = lightblue1, ");
		pw.println("\t\tstyle = \"rounded, filled\"");
		pw.println("\t\twidth = 2");
		pw.println("\t];\n");

		pw.println("\tedge [color = \"#ee1010\", penwidth = 2, arrowsize = 1];\n");

		if (root == null)
			pw.println();
		else
			abrDotPrint(root, pw);
		pw.println("}");
		pw.close();
	}

	// -------------------------------------------------------------------- //
	// M É T H O D E S P R I V É E S //
	// -------------------------------------------------------------------- //

	// -------------------------------------------------------------------- //
	/**
	 * Fonction qui compare le contenu de deux arbres binaire
	 * 
	 * @param a
	 *            Accès à la racine du 1er arbre
	 * @param b
	 *            Accès à la racine du 2nd arbre
	 * @return Vrai si la forme et le contenu des deux arbres sont les mêmes
	 */
	private boolean abrEquals(Node a, Node b) {
		/* À compléter */
		return false;
	}

	// -------------------------------------------------------------------- //
	/**
	 * Fonction qui crée une copie "profonde" d'un arbre
	 * 
	 * @param a
	 *            Accès à la racine de l'arbre initial
	 * @return Accès à la racine de la copie de l'arbre initial
	 */
	private Node abrCopy(Node a) {
		/* À compléter */
		return a; /* À modifier */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Fonction d'insertion d'un élément dans un arbre binaire. L'insertion
	 * n'est pas réalisée sir l'élément est déjà présent
	 * 
	 * Version récursive
	 * 
	 * @param p
	 *            Accès à la racine de l'arbre
	 * @param e
	 *            L'élément à insérer
	 * @return Accès à la racine de l'arbre après insertion
	 */
	private Node abrInsert(Node p, String e) {
		if (p == null) {
			return new Node(e, null, null);
		}
		int test = p.elt.compareTo(e);
		if (test > 0) {
			p.gauche = abrInsert(p.gauche, e);
		} else {
			p.droit = abrInsert(p.droit, e);
		}
		return p;
	}

	// -------------------------------------------------------------------- //
	/**
	 * Fonction d'insertion d'un élément dans un arbre binaire. L'insertion
	 * n'est pas réalisée sir l'élément est déjà présent
	 * 
	 * Version itérative
	 * 
	 * @param p
	 *            Accès à la racine de l'arbre
	 * @param e
	 *            L'élément à insérer
	 * @return Accès à la racine de l'arbre après insertion
	 */
	private Node abrInsert_v2(Node p, String e) {
		Node newNode = new Node(e, null, null);
		if (p == null)
			return newNode;

		while (true) {
			if (p.elt.compareTo(e) > 0) {
				if (p.gauche == null) {
					p.gauche = newNode;
					break;
				}
				p = p.gauche;
			} else {
				if (p.droit == null) {
					p.droit = newNode;
					break;
				}
				p = p.droit;
			}
		}
		return root;
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage infixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version récursive
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrInfixePrint(Node root) {
		if (root != null) {
			abrInfixePrint(root.gauche);
			System.out.print(root + " ");
			abrInfixePrint(root.droit);
		}
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage infixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version avec suppression de l'appel récursif terminal
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrInfixePrint_v2(Node root) {
		/* À compléter */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage infixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version avec suppression des appels récursifs
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrInfixePrint_v3(Node root) {
		Stack<Node> lifo = new Stack<Node>();

		/* À compléter */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage postfixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version récursive
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrpostfixePrint(Node root) {
		/* À compléter */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage préfixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version récursive
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrprefixePrint(Node root) {
		if (root != null) {
			System.out.print(root + " ");
			abrprefixePrint(root.gauche);
			abrprefixePrint(root.droit);
		}
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage préfixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version avec suppression de l'appel récursif terminal
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrprefixePrint_v2(Node root) {
		/* À compléter */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage préfixe du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version avec suppression des appels récursifs
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrprefixePrint_v3(Node root) {
		Stack<Node> lifo = new Stack<Node>();

		/* À compléter */

	}

	// -------------------------------------------------------------------- //
	/**
	 * Affichage en largeur du contenu de l'arbre binaire défini par sa racine
	 * 
	 * Version itérative
	 * 
	 * @param root
	 *            Accès au noeud racine de l'arbre
	 */
	private static void abrLargeurPrint(Node root) {
		Queue<Node> fifo = new LinkedList<Node>();

		/* À compléter */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Calcul de la hauteur d'un arbre binaire
	 * 
	 * !!! Version inefficace !!!
	 * 
	 * @param root
	 *            Accès à la racine de l'arbre binaire
	 * @return hauteur de l'arbre
	 */
	private static int abrHauteur(Node root) {
		if (root == null)
			return -1;
		if (abrHauteur(root.gauche) > abrHauteur(root.droit))
			return 1 + abrHauteur(root.gauche);
		return 1 + abrHauteur(root.droit);
	}

	// -------------------------------------------------------------------- //
	/**
	 * Calcul de la hauteur d'un arbre binaire
	 * 
	 * @param root
	 *            Accès à la racine de l'arbre binaire
	 * @return hauteur de l'arbre
	 */
	private static int abrHauteur_v1(Node root) {
		if (root == null)
			return -1;
		return 1 + abrHauteur(root.droit) + abrHauteur(root.gauche);
	}

	// -------------------------------------------------------------------- //
	/**
	 * Calcul du nombre de noeuds d'un arbre binaire
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @return Le nombre de ses noeuds
	 */
	private static int abrNb(Node root) {
		if (root == null)
			return 0;
		return 1 + abrNb(root.gauche) + abrNb(root.droit);
	}

	// -------------------------------------------------------------------- //
	/**
	 * Calcul du nombre de feuilles d'un arbre binaire
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @return Nombre de feuilles
	 */
	private static int abrNbFeuilles(Node root) {
		if (root == null)
			return 0;
		if (root.gauche == null && root.droit == null)
			return 1;
		return abrNbFeuilles(root.gauche) + abrNbFeuilles(root.droit);
	}

	// -------------------------------------------------------------------- //
	/**
	 * Retoune le plus petit élément présent dans un arbre binaire
	 * 
	 * Version récursive
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @return Le plus petit élément (null si arbre vide)
	 */
	private static String abrMin(Node root) {
		if (root == null)
			return "null";
		while (true) {
			if (root.gauche == null)
				return root.elt;
			root = root.gauche;
		}
	}

	// -------------------------------------------------------------------- //
	/**
	 * Retoune le plus petit élément présent dans un arbre binaire
	 * 
	 * Version itérative
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @return Le plus petit élément (null si arbre vide)
	 */
	private static String abrMin_v2(Node root) {
		if (root.gauche == null)
			return root.elt;
		return abrMin_v2(root.gauche);
	}

	// -------------------------------------------------------------------- //
	/**
	 * Retoune le plus grand élément présent dans un arbre binaire
	 * 
	 * Version récursive
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @return Le plus grand élément (null si arbre vide)
	 */
	private static String abrMax(Node root) {
		if (root == null)
			return "null";
		while (true) {
			if (root.droit == null)
				return root.elt;
			root = root.droit;
		}
	}

	// -------------------------------------------------------------------- //
	/**
	 * Retoune le plus grand élément présent dans un arbre binaire
	 * 
	 * Version itérative
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @return Le plus grand élément (null si arbre vide)
	 */
	private static String abrMax_v2(Node root) {
		if (root.droit == null)
			return root.elt;
		return abrMin_v2(root.droit);
	}

	// -------------------------------------------------------------------- //
	/**
	 * Recherche du noeud contenant un élément donné
	 * 
	 * Version récursive
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @param e
	 *            L'élément recherché
	 * @return null si l'élément recherché est absent, le noeud contenant
	 *         l'élément recherché
	 */
	private static Node abrFind(Node root, String e) {
		int test = root.elt.compareTo(e);
		if (test > 0)
			abrFind(root.gauche, e);
		else if (test < 0)
			abrFind(root.droit, e);
		else
			return root;
		return null;
	}

	// -------------------------------------------------------------------- //
	/**
	 * Recherche du noeud contenant un élément donné
	 * 
	 * Version itérative
	 * 
	 * @param root
	 *            Racine de l'arbre binaire
	 * @param e
	 *            L'élément recherché
	 * @return null si l'élément recherché est absent, le noeud contenant
	 *         l'élément recherché
	 */
	private static Node abrFind_v2(Node root, String e) {
		/* À compléter */
		return null; /* À modifier */
	}

	// -------------------------------------------------------------------- //
	/**
	 * Crée un label - au sens GraphViz - permettant d'identifier un noeud (pas
	 * de caractères nationaux, pas d'apostrophes, pas de trait d'union, etc.
	 * 
	 * @param mot
	 *            mot à modifier
	 * @return le nouveau mot
	 */
	private static String label(String mot) {
		mot = mot.replace('\'', '_');
		mot = mot.replace('-', '_');
		mot = mot.replace('é', 'e');
		mot = mot.replace('ë', 'e');
		mot = mot.replace('ê', 'e');
		mot = mot.replace('è', 'e');
		mot = mot.replace('à', 'a');
		mot = mot.replace('â', 'a');
		mot = mot.replace('ù', 'u');
		mot = mot.replace('û', 'u');
		mot = mot.replace('ï', 'i');
		mot = mot.replace('É', 'E');
		return mot;
	}

	// -------------------------------------------------------------------- //
	/**
	 * Génération de la description du fichier .dot de l'arbre dont la racine
	 * est donnée
	 * 
	 * @param a
	 *            racine de l'arbre
	 * @param pw
	 *            accès au fichier de représentation dot à compl
	 */
	private void abrDotPrint(Node a, PrintWriter pw) {

		/* À compléter */
	}
}
// ------------------------------------------------------------- THE END --- //
