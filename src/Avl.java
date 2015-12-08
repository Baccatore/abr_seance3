import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Avl {

	//	Variable de classe utilisée pour le comptage
	//	des comparaisons effectuées lors des opérations de 
	//	recherche.
	private static int nbComparaisons;	

	
	//	Classe interne utilisée pour définir les noeuds de l'arbre binaire 
	//	de recherche
	class Node  {
		String elt;		//	l'information-clé portée par chaque noeud
		Byte bal;		/*	Facteur de déséquilibre
						 *	0 -> racine d'un (ss-)arbre équilibré
						 *	1 -> racine d'un (ss-)arbre qui penche à gauche
						 * -1 -> racine d'un (ss-)arbre qui penche à droite
						 */
		Node gauche;	//	Accès à la racine du ss-arbre gauche
		Node droit;		//	Accès à la racine du ss-arbre droit
		
		public Node(String elt) {
			this.elt 	= new String(elt);
			this.bal 	= 0;
			this.gauche = this.droit = null;
		}
		
		public Node(String elt, Node g, Node d) {
			this.elt 	= new String(elt);
			this.bal 	= 0;
			this.gauche = g;
			this.droit 	= d;
		}

		@Override
		public String toString() {
			return "\"" + elt.replace('_', ' ') + "\"";
		}
	}
	

	
	// -------------------------------------------------------------------- //
	// 								Attribut(s)								//
	// -------------------------------------------------------------------- //
	private Node root;		// 	Accès au noeud racine de l'arbre binaire
							//	de recherche
	
	
	// -------------------------------------------------------------------- //
	// 								Constructeur(s)							//
	// -------------------------------------------------------------------- //

	public Avl() {
		root = null;
	}
	
	/**
	 * Constructeur par copie <i>profonde</i>
	 * 
	 * @param root
	 */
	public Avl(Avl a) {
		if (a == null)
			root = null;
		else
			root = avlCopy(a.root);
	}


	// -------------------------------------------------------------------- //
	// 						Getter()s/Setter()								//
	// -------------------------------------------------------------------- //

	/**
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}


	/**
	 * @param root the root to set
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
	// 						Méthodes publiques								//
	// -------------------------------------------------------------------- //


	public boolean isEmpty()		{return root == null;}
	public void insert(String e) 	{root = avlInsert(root, e);}
	public int size() 				{return avlNb(root);}
	public int nbLeafs() 			{return avlNbFeuilles(root);} 
	public int height() 			{return avlHauteur(root);}
	public String min()				{return avlMin(root);}
	public String max()				{return avlMax(root);}
	
	public boolean isMember(String e) 		{
		nbComparaisons = 0; 
		return avlFind(root, e) != null;
	}
	
	public boolean equals(Avl a) {
		return avlEquals(root, a.root);
	}

	
	public void infixePrint(String prompt) {
		System.out.print(prompt +" "); 
		avlInfixePrint(root); 
		System.out.println();
	}

	public void postfixePrint(String prompt){
		System.out.print(prompt +" "); 
		avlPostfixePrint(root); 
		System.out.println();
	}
	
	public void prefixePrint(String prompt){
		System.out.print(prompt +" "); 
		avlPrefixePrint_v3(root); 
		System.out.println();
	}

	public void largeurPrint(String prompt){
		System.out.print(prompt +" "); 
		avlLargeurPrint(root);
		System.out.println();
	}

	public void dotPrint(PrintWriter pw){
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
			avlDotPrint(root, pw);
		pw.println("}");
		pw.close();
	}	
	
	// -------------------------------------------------------------------- //
	// 						Méthodes privées								//
	// -------------------------------------------------------------------- //

	private boolean avlEquals(Node a, Node b){
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		if (! a.elt.equals(b.elt)) return false;
		return avlEquals(a.gauche, b.gauche) && avlEquals(a.droit, b.droit);
	}
	
	private Node avlCopy(Node a) {
		if (a == null) return a;
		return new Node (a.elt, avlCopy(a.gauche), avlCopy(a.droit));
		}

	
	private Node avlEquilibrer(Node a){
		if (a.bal == 2)			// penche très fort à gauche
			if (a.gauche.bal >= 0)
				return avlRotD(a);
			else {
				a.gauche = avlRotG(a.gauche);
				return avlRotD(a);
			}
		else if (a.bal == -2)	// penche très fort à droite
			if (a.droit.bal <= 0)
				return avlRotG(a);
			else {
				a.droit = avlRotD(a.droit);
				return avlRotG(a);
			}
		else
			return a;
	}
	
	private Node avlRotG(Node a){
		Node b = a.droit;
		byte aBal = a.bal, bBal = b.bal;
		
		a.droit = b.gauche;
		b.gauche = a;
		
		return b;
	}
	
	private Node avlRotD(Node a){
		Node b = a.gauche;
		byte aBal = a.bal, bBal = b.bal;
		
		a.gauche =b.droit;
		b.droit = a;
		return b;
	}
	
	// -------------------------------------------------------------------- //
	class AvlInfo {
		byte 	augmentation;
		Node	racine;
		
		public AvlInfo(byte augmentation, Node racine) {
			this.augmentation = augmentation;
			this.racine = racine;
		}
	}
	// -------------------------------------------------------------------- //
	
	private AvlInfo avlInsertInfo(Node p, String e){
		AvlInfo info;
		
		if (p == null)
			return new AvlInfo((byte)1, new Node(e));
		int test = p.elt.compareTo(e);
		if (test == 0)
			return new AvlInfo((byte) 0, p);
		
		if (test > 0)
			info = avlInsertInfo(p.gauche, e);
		else {
			info = avlInsertInfo(p.gauche, e);
			info.augmentation = (byte) -info.augmentation;
		}
		
		if (info.augmentation == 0)
			return info;
		
		p.bal = (byte) (p.bal + info.augmentation);
		info.racine = avlEquilibrer(info.racine);
		if (info.augmentation != 0)
			info.augmentation = 1;
			
		return info;
	}
	
	// -------------------------------------------------------------------- //
	private Node avlInsert(Node p, String e){
		return avlInsertInfo(p, e).racine;
	}
	

	
	// -------------------------------------------------------------------- //
	private static void avlInfixePrint(Node root){
		if (root != null) {
			avlInfixePrint(root.gauche);
			System.out.print(root + " ");
			avlInfixePrint(root.droit);
		}
	}

	// -------------------------------------------------------------------- //
	//	Version avec suppression de l'appel récursif terminal				//
	// -------------------------------------------------------------------- //
	private static void avlInfixePrint_v2(Node root){
		while (root != null) {
			avlInfixePrint(root.gauche);
			System.out.print(root + " ");
			root = root.droit;
		}
	}
	
	// -------------------------------------------------------------------- //
	//	Version avec suppression des appels récursifs 						//
	// -------------------------------------------------------------------- //
	private static void avlInfixePrint_v3(Node root){
		Stack<Node> lifo = new Stack<Node>();

		while (true) {
			if (root != null){
				lifo.push(root);
				root = root.gauche;
			}
			else if (! lifo.isEmpty()){
				root = lifo.pop();
				System.out.print(root + " ");
				root = root.droit;
			}
			else break;
		}
	}
	// -------------------------------------------------------------------- //
	private static void avlPostfixePrint(Node root){
		if (root != null) {
			avlPostfixePrint(root.gauche);
			avlPostfixePrint(root.droit);
			System.out.print(root + " ");
		}
	}

	// -------------------------------------------------------------------- //
	private static void avlPrefixePrint(Node root){
		if (root != null) {
			System.out.print(root + " ");
			avlPrefixePrint(root.gauche);
			avlPrefixePrint(root.droit);
		}
	}
	
	// -------------------------------------------------------------------- //
	private static void avlPrefixePrint_v2(Node root){
		while (root != null) {
			System.out.print(root + " ");
			avlPrefixePrint_v2(root.gauche);
			root = root.droit;
		}
	}
	
	private static void avlPrefixePrint_v3(Node root){
		Stack<Node> lifo = new Stack<Node>();

		while (true) {
			if (root != null){
				System.out.print(root + " ");
				lifo.push(root);
				root = root.gauche;
			}
			else if (! lifo.isEmpty()){
				root = lifo.pop();
				root = root.droit;
			}
			else break;
		}
	}
	
	
	private static void avlLargeurPrint(Node root){
		Queue<Node> fifo = new LinkedList<Node>();

		if (root == null) return;
		fifo.add(root);
		while (! fifo.isEmpty()) {
			root = fifo.remove();
			System.out.print(root + " ");
			if (root.gauche != null)
				fifo.add(root.gauche);
			if (root.droit != null)
				fifo.add(root.droit);
		}
	}

	// -------------------------------------------------------------------- //
	private static int avlHauteur(Node root){
		if (root == null) return -1;
		int h1 = avlHauteur(root.gauche);
		int h2 = avlHauteur(root.droit);
		return 1 + Math.max(h1, h2);
	}
	
	// -------------------------------------------------------------------- //
	private static int avlHauteur_v0(Node root){
		if (root == null) return -1;
		if (avlHauteur_v0(root.gauche) > avlHauteur_v0(root.droit))
			return 1 + avlHauteur_v0(root.gauche);
		return 1 + avlHauteur_v0(root.droit);
	}


	// -------------------------------------------------------------------- //
	private static int avlNb(Node root){
		if (root == null) return 0;
		return 1 + avlNb(root.gauche) + avlNb(root.droit);
	}
	
	// -------------------------------------------------------------------- //
	private static int avlNbFeuilles(Node root){
		if (root == null) return 0;
		if (root.gauche == null && root.droit == null)
			return 1;
		return avlNbFeuilles(root.gauche) + avlNbFeuilles(root.droit);
	}	
	
	
	// -------------------------------------------------------------------- //
	private static String avlMin(Node root){
		if (root == null) return null;
		for (; root.gauche != null; root = root.gauche);
		return "" + root;
	}
	
	// -------------------------------------------------------------------- //
	private static String avlMin_v2(Node root){
		if (root == null)
			return null;
		if (root.gauche == null)
			return "" + root;
		return avlMin_v2(root.gauche);
	}

	// -------------------------------------------------------------------- //
	private static String avlMax(Node root){
		if (root == null) return null;
		for (; root.droit != null; root = root.droit);
		return "" + root;
	}

	// -------------------------------------------------------------------- //
	private static String avlMax_v2(Node root){
		if (root == null)
			return null;
		if (root.droit == null)
			return "" + root;
		return avlMax_v2(root.droit);
	}
	
	// -------------------------------------------------------------------- //
	private static Node avlFind(Node root, String e){
		if (root == null) return null;
		int test = root.elt.compareTo(e);
		nbComparaisons++;
		if (test == 0) return root;
		if (test < 0) return avlFind(root.droit, e);
		return avlFind(root.gauche, e);
	} 
	
	// -------------------------------------------------------------------- //
	private static Node avlFind_v2(Node root, String e){
		while (root != null) {
	
		int test = root.elt.compareTo(e);
		nbComparaisons++;
		if (test == 0) return root;
		if (test < 0) 
			root = root.droit;
		else
			root = root.gauche;
		}
		return null;
	} 
	
	// -------------------------------------------------------------------- //
	private static String label(String mot){
		mot = mot.replace('\'', '_');
		mot = mot.replace('-' , '_');
		mot = mot.replace('é' , 'e');
		mot = mot.replace('ë' , 'e');
		mot = mot.replace('ê' , 'e');
		mot = mot.replace('è' , 'e');
		mot = mot.replace('à' , 'a');
		mot = mot.replace('â' , 'a');
		mot = mot.replace('ù' , 'u');
		mot = mot.replace('û' , 'u');
		mot = mot.replace('ï' , 'i');
		mot = mot.replace('É' , 'E');
		return mot;
	}
	
	// -------------------------------------------------------------------- //
	private void avlDotPrint(Node a, PrintWriter pw){
		
		// Parcours préfixe de l'Abr		
		if (a.droit == null && a.gauche == null)
			pw.printf("\t%s [label = \"{<c> %s | { <g> null | <d> null}}\"];\n", 
					label(a.elt), a.elt.replace('_', ' '));
		else if (a.droit == null)
			pw.printf("\t%s [label = \"{<c> %s | { <g>      | <d> null}}\"];\n", 
					label(a.elt), a.elt.replace('_', ' '));
		else if (a.gauche == null)	
			pw.printf("\t%s [label = \"{<c> %s | { <g> null | <d>     }}\"];\n", 
					label(a.elt), a.elt.replace('_', ' '));
		else
			pw.printf("\t%s [label = \"{<c> %s | { <g>      | <d>     }}\"];\n", 
					label(a.elt), a.elt.replace('_', ' '));
			

		if (a.gauche != null){
			pw.printf("\t%s : g -> %s;\n", label(a.elt), label(a.gauche.elt));
			avlDotPrint(a.gauche, pw);
		}
		
		if (a.droit != null){
			pw.printf("\t%s : d -> %s;\n", label(a.elt), label(a.droit.elt));
			avlDotPrint(a.droit, pw);
		}
	}
	// -------------------------------------------------------------------- //
}
