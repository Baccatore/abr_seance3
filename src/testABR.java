import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

//------------------------------------------------------------------------ //
//			CLASSE DÉFINISSANT LE FILTRE DE SÉLECTION DE FICHIERS		   //
//------------------------------------------------------------------------ //
class TextFileFilter implements FileFilter {
	private final String[] okFileExt = { "txt", };

	@Override
	public boolean accept(File file) {
		for (String extension : okFileExt)
			if (file.getName().toLowerCase().endsWith(extension))
				return true;
		return false;
	}
}

//------------------------------------------------------------------------ //
public class testABR {

	// --------------------------------------------------------------------- //
	//						C O N S T A N T E S								 //
	// --------------------------------------------------------------------- //
	final static int TAILLE_MAX = 25;
	final static String DOT_CMD = "C:/Program Files (x86)/Graphviz2.38/bin/dot.exe";
	final static Scanner stdinput = new Scanner(System.in);
	final static String dossierDot = "./dot";
	final static String dossierTxt = "./txt";

	// --------------------------------------------------------------------- //
	//				V A R I A B L E S   D E   C L A S S E					 //
	// --------------------------------------------------------------------- //
	static File 	dirDot;		//	Dossier des fichiers .dot
	static File 	dirTxt;		//	Dossier des fichiers à utiliser
	static File[] 	filesTxt;	// 	Ensemble des fichiers à utiliser
	static String	signature;	// 	Chaîne d'identification


	// --------------------------------------------------------------------- //
	//			A R B R E   B I N A I R E   D E   R E C H E R C H E			 //
	// --------------------------------------------------------------------- //
	
	
	// --------------------------------------------------------------------- //
	/**
	 * Création d'un arbre binaire de recherche avec les mots de longueur
	 * len provenant du fichier fileName
	 * 
	 * @param fileName	Nom du fichier contenant les mots à insérer dans
	 * 					l'arbre binaire 
	 * @param len		Longueur des mots à insérer
	 * @return			l'arbre binaire de recherche créé
	 * 
	 */
	static Abr createAbr(String fileName, int len) {
		System.out.println("Création d'un arbre binaire de recherche " +
							"contenant les mots d'un fichier");

		Abr arbre = new Abr();
		Scanner sc = null;
		
		File fichier = new File(dossierTxt + "/" +fileName);

		try {
			sc = new Scanner(fichier);
		} catch (FileNotFoundException e) {
			System.err.println("Impossible d'ouvrir le fichier " + fileName);
		}

		// ------------------------------------------------------------------ //
		System.out.print("Lecture du fichier " + fileName + " ... ");
		int nb;
		for (nb = 0; sc.hasNext();) {
			String mot = sc.next();
			if (mot.length() == len) {
				arbre.insert(mot);
				nb++;
			}
		}
		System.out.print("terminée. Il y a " + nb );
		if (nb < 2)
			System.out.println(" mot.\n");
		else
			System.out.println(" mots.\n");
		
		sc.close();
		return arbre;
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Affichage de diverses propriétes d'un arbre binaire de recherche :
	 * 
	 * @param a		l'arbre binaire
	 */
	static void displayPropAbr(Abr a){
		long start, stop;

		System.out.println("Affichages des propriétés d'un arbre binaire de recherche");
		System.out.println("Nombre de noeuds   = " + a.size());
		System.out.println("Nombre de feuilles = " + a.nbLeafs());
		start = System.nanoTime();
		System.out.print("Hauteur de l'arbre = " + a.height());
		stop = System.nanoTime();
		System.out.format("\t(%4.1f ms)\n", (stop - start) / 1000000.);

		System.out.println("Racine de l'arbre  = " + a.getRoot());
		System.out.println("Min de l'arbre     = " + a.min());
		System.out.println("Max de l'arbre     = " + a.max());
		System.out.println();
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Affichages infixe, préfixe et postfixe et affichage en largeur 
	 * d'un arbre binaire.
	 * 
	 * L'affichage n'est réalisé que si le nombre de ses noeuds est
	 * inférieur ou égal à SEUIL
	 * 
	 * @param a		l'arbre binaire à afficher
	 */
	static void displayAbr (Abr a) {
		System.out.println("Affichages du contenu d'un arbre binaire de recherche");
		if (a.size() <= TAILLE_MAX) {
		a.infixePrint("Affichage infixe   :");
		a.prefixePrint("Affichage préfixe  :");
		a.postfixePrint("Affichage postfixe :");
		a.largeurPrint ("Affichage largeur  :" );
		}
		else
			System.out.println("Pas d'affichage pour des arbres de plus " 
								+ TAILLE_MAX + " noeuds");
		System.out.println();
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Tests de recherche de mots dans un arbre binaire de recherche
	 * 
	 * @param a		l'arbre dans lequel la recherche est effectuée
	 */
	static void searchTestAbr(Abr a){
		System.out.println("Test des opérations de recherche " + 
						   "dans un arbre binaire");
		stdinput.reset();
		String mot;
		System.out.print("Recherche de ? (. pour arrêter) ");
		while (true) {
			//mot = stdinput.next().toUpperCase().trim();
			mot = stdinput.next().trim();
			if (mot.equals(".")) break;

			System.out.print(mot);
			if (a.isMember(mot)) {
				System.out.print(" est présent dans l'arbre binaire. ");
				System.out.println("(" + Abr.getNbComparaisons() +")");
			}
			else {
				System.out.print(" est absent de l'arbre binaire. ");
				System.out.println("(" + Abr.getNbComparaisons() +")");
			}
			System.out.print("Recherche de ? (. pour arrêter) ");
		}	
		System.out.println();
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Création du fichier de description au format .dot et création
	 * de l'image au format .png d'un arbre binaire
	 * 
	 * @param a			l'arbre binaire
	 * @param fileName	Nom du fichier d'où proviennent les mots
	 * 					contenus dans l'arbre binaire.
	 */
	static void	creerDotAbr(Abr a, String fileName){
		System.out.println("Création de la représentation graphique " +
							"d'une arbre binaire de recherche");
		int longueurMots = 0;
		String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
		if (a != null)
			longueurMots = ("" + a.getRoot()).length() - 2;
		String dotName = dossierDot + "/abr_" + tokens[0] + "-" + longueurMots + ".dot";
		String pngName = "abr_" + tokens[0] + "-" + longueurMots +  ".png";

		File fdot = new File(dotName);
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fdot)));
			a.dotPrint(pw);
		} catch (IOException exception) {
			System.out.println("Erreur : " + exception.getMessage());
		}

		String[] commande = {DOT_CMD, "-Tpng", dotName, "-o " + pngName };

		try {
			Runtime.getRuntime().exec(commande);
			System.out.println("Image de l'arbre (" + pngName +") ... créée.\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Test de la copie d'un arbre binaire de recherche
	 * 
	 * @param a		l'arbre initial
	 * @return		la copie de l'arbre
	 */
	static Abr testCopyAbr(Abr a){
		System.out.println("Test de la copie d'un arbre binaire de recherche");
		Abr b = new Abr(a);
		displayPropAbr(b);
		displayAbr(b);
		return b;
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Test de l'égalité de deux arbres binaires de recherche
	 * 
	 * @param a		un premier arbre binaire de recherche
	 * @param b		un second arbre binaire de recherche
	 */
	static void testEgalAbr(Abr a, Abr b){
		System.out.println("Test l'égalité de deux arbres binaires de recherche");
		if (a.equals(b)){
			System.out.println("L'arbre original et sa copie sont égaux.");
			System.out.println("Ajout du mot \"Aaa\" dans la copie");
			b.insert("Aaa");
			System.out.println("Nombre de noeuds   = " + b.size());
			if (b.size() < TAILLE_MAX)
				b.infixePrint("Affichage infixe   :");
			
			System.out.print("L'arbre original et sa copie augmentée d'un mot ");
			if (!a.equals(b))
				System.out.println("ne sont plus égaux.");
			else 
				System.out.println("sont toujours égaux.");
			
		}
		else
			System.out.println("L'arbre original et sa copie ne sont pas égaux.");
		System.out.println();		
	}


	// --------------------------------------------------------------------- //
	/**
	 * Série de tests sur un arbre binaire de recherche
	 * 
	 * @param f			la référence au fichier contenant les mots à
	 * 					insérer dans l'arbre binaire
	 * @param longueurMots	Longueur des mots à insérer dans un arbre binaire
	 * 
	 */
	static void testAbr(File f, int longueurMots)  {
		String fileName = f.getName();

		// 1) Création de l'arbre binaire
		Abr a = createAbr(fileName, longueurMots);

		// 2) Affichage de ses propriétés de base
		displayPropAbr(a);
		
		// 3) Affichage du contenu de l'arbre
		// si sa taille est inférieure ou égale à TAILLE_MAX
		displayAbr (a);
		

		// 	4) Recherche de la présence d'un mot/prénom
		searchTestAbr(a);

		//	5)	Création del'image de l'ABR aux formats .dot et .png
		creerDotAbr(a, fileName);
		
		//	6)	Création d'une copie d'un ABR
		Abr b = testCopyAbr(a);
		
		//	7)	Test de l'égalité de deux ABR
		testEgalAbr(a, b);
	

		epilogue("[Fin de la série de tests sur ABR]");
	}

	
	// --------------------------------------------------------------------- //
	/**
	 * Fonction appelée au debut de la fonction principale.
	 * 
	 * 		Crée la signature de l'utilisateur
	 * 		Crée le dossier où sont rangés les fichiers de mots à traiter, 
	 * 		ou récupère la liste de ces fichiers si le répertoire existe
	 * 		déjà.
	 * 		Crée le dossier où seront créés les fichiers .dot
	 */
	static void prologue() throws UnknownHostException {
		
		//	Création de la chaîne d'identification
		signature = System.getProperty("user.name");
		InetAddress addr = InetAddress.getLocalHost();
		signature += "\t" + addr.getHostAddress() + "\t" + addr.getHostName() + "\t   ";
		
		
		// Accès au dossier contenant les fichiers de mots à traiter
		dirTxt = new File(dossierTxt);
		if (!dirTxt.exists()) {
			if (dirTxt.mkdir()) {
				System.out.println("Dossier \"" + dossierTxt + "\" créé.");
				System.out.println("N'oubliez pas d'y copier les " +
									"fichiers de mots et de prénoms");
			}
		}
		
		//	Récupération de la liste des fichiers .txt
		filesTxt = dirTxt.listFiles(new TextFileFilter());
		
		//	Accès au dossier destiné à recevoir les fichiers de description 
		//	des ABR au format .dot 
		dirDot = new File(dossierDot);
		if (!dirDot.exists()) {
			if (dirDot.mkdir()) {
				System.out.println("Dossier \"" + dossierDot + "\" créé.");
			}
		}
	}
	
	// --------------------------------------------------------------------- //
	/**
	 * Affiche le texte défini comme paramètre puis la signature de 
	 * l'utiliseur puis la date et l'heure
	 * 
	 * @param msg		Message à afficher
	 */
	static void epilogue(String msg)  {
		System.out.print("\n" + msg + "\n" + signature);
		Date maintenant = new Date();
		DateFormat fullDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		System.out.println(fullDateFormat.format(maintenant) + "\n");	
	}

	// --------------------------------------------------------------------- //
	//					F O N C T I O N   P R I N C I P A L E				 //
	// --------------------------------------------------------------------- //
	public static void main(String[] args)  {
		try {
			prologue();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		int i, numero;
		while (true) {
			System.out.println("Liste des fichiers disponibles ");
			for (i = 1; i <= filesTxt.length; i++){
				System.out.printf("%2d) --> %s", i, filesTxt[i - 1].getName());
				if ((i % 3) == 0)
					System.out.println();
				else
					System.out.print("\t");
			}
			if ((i % 3) != 1)
				System.out.println();
			
			do {
				System.out.print("Quel est le numéro du fichier à traiter " + 
								 "(0 pour arrêter) ? ");
				stdinput.reset();
				numero = stdinput.nextInt();
				if (numero < 0 || numero >= i)
					System.out.println("Numéro incorrect. Recommencez");
			} while (numero < 0 || numero >= i);
			if (numero == 0) break;
			System.out.print("Longueur des mots à traiter ? ");
			int len = stdinput.nextInt();

			testAbr(filesTxt[numero - 1], len);
			
		}	
		epilogue("[That's all folks!]");
	}
}
// ------------------------------------------------------------ THE END --- //
