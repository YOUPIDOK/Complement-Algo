import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.*;

public class Graph {
    public static void main(String[] args) throws IOException {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Saisir le numéro de la question à afficher ( de 1 à 6 ), saisir un autre chiffre pour quitter.");
            System.out.print("Numéro de la question : ");
            String txt = userIn.readLine();
            System.out.println();
            int number = 7;
            if (isInt(txt)) {
                number = Integer.parseInt(txt);
            }
            if (number < 1 || number > 6) {
                System.out.println("Au revoir :)");
                System.exit(0);
            } else if (number == 1) {
                System.out.println("********* QUESTION 1 *********");
                System.out.println("********* Graph aléatoire *********");
                int[][] graphAleatoire = generateGraph(5);
                printGraph(graphAleatoire);
            } else if (number == 2) {
                System.out.println("\n********* QUESTION 2 *********");
                System.out.println("********* Graph connect *********");
                printGraph(readGraph("GraphConnect.txt"));
                System.out.println("IsConnect : " + isConnect(readGraph("GraphConnect.txt")));
                System.out.println("\n********* Graph non connect *********");
                printGraph(readGraph("GraphNonConnect.txt"));
                System.out.println("IsConnect : " + isConnect(readGraph("GraphNonConnect.txt")));
            } else if (number == 3) {
                System.out.println("\n********* QUESTION 3 *********");
                System.out.println("********* Djikstra pour tous les points : *********");
                System.out.println("Graph de départ : ");
                printGraph(readGraph("GraphConnexe2.txt"));
                System.out.println("\nPlus court chemin entre tous les points :");
                printGraph(getDistanceMinForAllPoints(readGraph("GraphConnexe2.txt")));
            } else if (number == 4) {
                System.out.println("\n********* QUESTION 4 *********");
                System.out.println("********* Graph complet *********");
                printGraph(readGraph("GraphComplet.txt"));

                System.out.println("\n********* Graph non complet *********");
                int[][] graphNonComplet = generateGraph(5);
                printGraph(graphNonComplet);
                System.out.println("Is complet : " + isComplet(graphNonComplet));

                System.out.println("\n********* Cycle *********");
                System.out.println("TRAJET TOTAL : " + cycle(readGraph("GraphComplet.txt"), 0));
            } else if (number == 5) {
                System.out.println("\n********* QUESTION 5 *********");
                System.out.println("********* Cycle optimal *********\n");
                printGraph(readGraph("GraphComplet.txt"));
                cycle2(readGraph("GraphComplet.txt"),0);
            } else if (number == 6) {

            }
            System.out.println("\n");
        }
    }

    public static boolean isInt(String chaine) {

        try {
            Integer.parseInt(chaine);
        } catch (NumberFormatException e){
            return false;
        }

        return true;
    }

    // Question 1
    public static int[][] readGraph(String fileName) throws IOException {
        // Méthode pour lire le contenu d'un fichier txt pour le transformer en tableau d'entier à 2 dimensions
        BufferedReader br = new BufferedReader(new FileReader(fileName)); // Pour lire le fichier
        String line = br.readLine(); // Première ligne du fichier
        int size = line.split(",").length; // Taille de la ligne
        int graph[][] = new int[size][size]; // Graph de départ vide
        int i = 0; // Index de départ pour la lecture

        while (line != null) { // Tant qu'il y a encore des lignes à lire
            String[] tab = line.split(","); // On créer un tableau des éléments de la ligne
            for (int j = 0; j < size; j++) { // Affectation des éléments de la ligne au nouveau graph
                graph[i][j] = Integer.parseInt(tab[j]);
            }
            i++;
            line = br.readLine();
        }

        br.close();// On ferme le reader
        return graph;// On retourne le tableau
    }

    public static int[][] generateGraph(int size) {
        // Méthode pour générer un graphe aléatoir
        int[][] graph = new int[size][size];// On crée un tabeau à deux dimensions de taille size

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j > i; j--) {
                if (Math.random() < 0.3) {// On met une valeur aléatoir en i par j et en j par i pour que le grpahe soit connexe
                    int value = (int) (Math.random() * 9);// On tire une valeur au sort entre 1 et 10 non compris
                    graph[i][j] = value;
                    graph[j][i] = value;
                }
            }
        }

        return graph;// On retourne le tableau
    }

    public static void printGraph(int[][] graph) {
        // Méthode pour afficher un graphe dans la console
        for (int i = 0; i < graph.length; i++) { // Parcours toutes les colonnes
            for (int j = 0; j < graph.length; j++) { // Parcours toutes les lignes
                System.out.print(graph[i][j] + " | "); // Affiche l'élément
            }
            System.out.println(""); // Affiche un espace pour la lisibilité
        }
    }

    // Question 2
    public static boolean isConnect(int[][] graph) {
        // Méthode pour déterminer si un graphe est connexe ou non
        Set<Integer> box = new HashSet<Integer>(); // Pour vérifier les points déja passé
        for (int i = 0; i < graph.length; i++) { // Parcours de toutes les aretes
            for (int j = 0; j < graph.length; j++) { // Parcours de toutes les aretes
                if (graph[i][j] != 0) { // Si l'arrete est pondéré
                    if (box.isEmpty()) { // Pour ajouter les premiers noeuds
                        box.add(i);
                        box.add(j);
                    } else {
                        if (box.contains(i) || box.contains(j)) { // Si on a déja trouvé un noeud et qu'il n'est pas dans la liste des noeuds viisités
                            box.add(i);
                            box.add(j);
                        }
                    }

                }

            }
        }
        if (box.size() == graph.length) {
            return true;
        }
        return false;
    }

    // Question 3
    public static int dijkstra(int[][] graph, int depart, int arrivee) {
        // Traiter le cas du cul de sac
        ArrayList<Integer> noeudsMarques = new ArrayList<Integer>();// Liste qui va contenir tous les noeuds déjà visités
        ArrayList<Integer> noeudsReperes = new ArrayList<Integer>();// Liste qui va contenir tous les noeuds dont on a déjà calculé la distance
        ArrayList<Integer> valsNoeudsReperes = new ArrayList<Integer>();// Liste qui va contenir toutes les distances déjà calculées
        ArrayList<Integer> longueursChemin = new ArrayList();// Liste qui va contenir la longueur des différents chemins calculés
        valsNoeudsReperes.add(graph[depart][depart]);// On associe au noeud 0 la valeur 0
        noeudsReperes.add(depart);// On ajoute le noeud 0 aux noeuds reperés
        while (noeudsMarques.size() != graph.length - 1) {
            // Tant qu'il reste des noeuds non marqués on continue
            int valSommetRef = Collections.min(valsNoeudsReperes);// On détermine quel sera le prochain sommet en fonction de la proximité (on prend le plus proche)
            int sommetRef = noeudsReperes.get(valsNoeudsReperes.indexOf(valSommetRef));// Le noeud le plus proche est désigné comme noeud réference
            for (int sommetDest = 0; sommetDest < graph[sommetRef].length; sommetDest++) {// On liste les noeuds non marqués reliés à sommetRef
                // On analyse dommetDest
                if (graph[sommetRef][sommetDest] != 0 && !noeudsMarques.contains(sommetDest)) {
                    if (noeudsReperes.indexOf(sommetDest) >= 0 && valsNoeudsReperes.get(noeudsReperes.indexOf(sommetDest)) >= 0) {
                        // S'il existe une connexion entre sommetRef et sommetDest (>=0 dans le tableau à deux dimensions) et qu'on a déjà une distance pour ce noeud, alors on regarde si on
                        // doit la mettre à, jour ou pas (c'est à dire, si par exemple on a 8 et que l'ancienne valeur étais 9 on met à jour de manière à avoir toujours la distance la plus courte)
                        valsNoeudsReperes.set(noeudsReperes.indexOf(sommetDest),Math.min(valsNoeudsReperes.get(noeudsReperes.indexOf(sommetDest)),valSommetRef + graph[sommetRef][sommetDest]));
                    } else {
                        // Il n'y pas encore de distance associée à ce noeud
                        // Ici, on vérifie que l'on n'est pas au bout du parcours
                        if (sommetDest == arrivee) {
                            // Si oui on ajoute le chemin à la liste des chemins
                            longueursChemin.add(valSommetRef + graph[sommetRef][sommetDest]);
                        } else {
                            // Sinon on met à jour le noeud analysé (sommetDest) noeuds repérés et on ajoute la nouvelle distance calculée
                            noeudsReperes.add(sommetDest);
                            valsNoeudsReperes.add(valSommetRef + graph[sommetRef][sommetDest]);
                        }

                    }

                }

            }
            // On finalise le noeud (sommetRef) et on le marque, on met aussi à jour les listes valsNoeudsRepres et noeudsReperes
            // pour ne pas avoir de noeuds déjà visiter
            valsNoeudsReperes.remove(noeudsReperes.indexOf(sommetRef));
            noeudsReperes.remove(noeudsReperes.indexOf(sommetRef));
            noeudsMarques.add(sommetRef);
        }

        return Collections.min(longueursChemin);
    }

    public static int[][] getDistanceMinForAllPoints(int[][] graph){
        // Méthode pour calculé toutes les distances entres les noeuds
        int size = graph.length; // Taille du graph
        int[][] tabDistances = new int[graph.length][graph.length]; // Matrice des distances
        for (int i = 0; i < size; i++) { // Parcours tous les couples noeud
            for (int j = size - 1; j > i; j--) { // Parcours tous les couples de noeud
                int value = dijkstra(graph,i,j); // Calcul de la distances mini entre les 2 noeuds
                tabDistances[i][j] = value; // Affectation de la valeur dans la matrice
                tabDistances[j][i] = value; // Affectation de la valeur dans la matrice
            }
        }
        return tabDistances;
    }


    // Question 4
    public static int cycle(int[][] graph,int depart){
        if(!isComplet(graph)){
            // Si le graphe n'est pas complet on n'éxecute pas l'algorithme
            return -1;
        }
        ArrayList<Integer> noeudsMarques = new ArrayList<Integer>();// Liste qui va contenir tous les noeuds déjà visités
        ArrayList<Integer> distances = new ArrayList<Integer>();// Liste qui va contenir toutes les distances déjà calculées
        ArrayList<Integer> sommetsDistances = new ArrayList<Integer>();;// Liste qui va contenir tous les noeuds dont on a déjà calculé la distance
        String[] a = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R"};// Cette liste de String sert juste à avoir un affichage dans la console plus propre
        int dist = 0;// On initialise la distance totale à 0
        noeudsMarques.add(depart);// On considère que le départ est déjà marqué(analysé)
        int sommetRef = depart;// On part du départ
        System.out.println("PARCOURS : "+dist+","+a[sommetRef]);
        while (noeudsMarques.size() != graph.length ) {
            // Tant qu'il reste des noeuds marqués
            for (int sommetDest = 0; sommetDest < graph[sommetRef].length; sommetDest++) {
                // On parcours les noeuds connectés à sommetRef qui ne sont pas marqués
                if (graph[sommetRef][sommetDest] != 0 && !noeudsMarques.contains(sommetDest)) {
                    // S'il existe une connexion entre sommetRef et sommetDest (>=0 dans le tableau à deux dimensions) et que le noeud n'est pas marqué on ajoute la distance à la liste des distances
                    distances.add(graph[sommetRef][sommetDest]);
                    sommetsDistances.add(sommetDest);
                }
            }
            int distChoisie = Collections.min(distances);// On prend la distance la plus petite
            dist += distChoisie;// On l'ajoute à la distance totale
            noeudsMarques.add(sommetRef);// On marque le noeud sommetRef
            sommetRef = sommetsDistances.get(distances.indexOf(distChoisie));// On désigne le prochain noeud réference grâce à la distance choisie précèdement
            distances.clear();// On vide les distances
            sommetsDistances.clear();// On vide les sommets
            System.out.println("PARCOURS : " + dist + "," + a[sommetRef]);
        }
        return dist + graph[sommetRef][depart];
    }

    public static boolean isComplet(int[][] graph){
        if(isConnect(graph)){ // Si le graph est connexe
            for(int i = 0;i<graph.length;i++){ // Parcours de la moitié du graph par symétrie avec la diagonal
                for (int j = graph.length - 1; j > i; j--) { // Parcours de la moitié du graph par symétrie avec la diagonal
                    if(graph[i][j]==0){ // Si le graph contient une arrete sans pondération
                        return false; // Alors on retourne faux
                    }
                }
            }
        }else{
            return false; // Retourne faux car le graph n'est pas connexe
        }
        return true; // Toutes les aretes possible sont pondérés
    }

    // Question 5
    public static ArrayList<Integer> cycle2(int[][] graph,int depart) {
        // Méthode pour trouver un cycle de distance optimal
        if(!isComplet(graph) && graph.length > 1){
            // Si le graphe n'est pas complet on n'éxecute pas l'algorithme
            return null;
        }
        int distance ; // Pour stocker la distance calaculé
        int graphSize = graph.length; // Taille du graph

        ArrayList<ArrayList<Integer>> arrangement = new ArrayList<>(); // Pour les stocker toutes les combinaisons possible
        ArrayList<Integer> nodeList; // Pour stocker les chemeins de noeud

        ArrayList<Integer> solution = new ArrayList<>(); // Pour stocker la solution
        int distanceMax = 0; // Pour stocker la distane max
        for (int i=0; i<graph.length; i++){ // Creaion d'une valeur max supérieur a celle maximum
            for(int j=0; j<graph.length; j++){
                distanceMax += graph[i][j]; // Addition des distances
            }
        }
        
        String txtForCombinaison = ""; // Creation du parametre pour getArrangement()
        for (int i = 0; i < graphSize; i++) {
            txtForCombinaison += String.valueOf(i);
        }
        ArrayList<String> listOfString = getArrangement(txtForCombinaison); // Tous les arrangement possible parmis le graph

        for (String txt : listOfString) { // Convertion d'un Arraylist<String> en Arraylist<Arraylist<Integer>>
            nodeList = new ArrayList<>(); // Creation d'une nouvelle liste vide
            for (int i=0; i<txt.length(); i++){ // Ajout de chaqque lettre de la string en int dans l'araylist
                nodeList.add(Integer.parseInt(String.valueOf(txt.charAt(i))));
            }
            if(nodeList.get(0) == depart){ // Ajout de tt les list de noeud créé dans arrangement
                arrangement.add(nodeList);
            }
        }

        for (ArrayList<Integer> nodePath : arrangement){ // Pour chaque liste de noeud
            distance = 0; // Remise a 0 de distance
            for(int i=1; i<graph.length; i++){ // Ajout de la distance entre chaque noeud a distance
                distance += graph[nodePath.get(i-1)][nodePath.get(i)];
//                System.out.print("| " + (i-1) + " => " + i + " : " + graph[nodePath.get(i-1)][nodePath.get(i)]);
            }
            distance += graph[nodePath.get(nodePath.size()-1)][depart] ; // Ajout de la distance de retour à distance
//            System.out.print( " == " + distance + " " + nodePath + "\n");
            if(distance < distanceMax){ // Si le chemin est plus court
                distanceMax = distance; // On sauvegarde la distance
                solution = nodePath; // On sauvegarde la liste de noeud
            }
        }

        if(!solution.isEmpty()){ // Si la liste n'est pas vide, affichage du resultat
            System.out.println("\nLa solution est " + solution + ", parcours de longueur : " + distanceMax);
        }
        return solution;

    }
    

    public static ArrayList<String> getArrangement(String str)
    {
        if (str.length() == 0) { //Si str est de taille 0
            ArrayList<String> empty = new ArrayList<>();
            empty.add("");
            return empty; // Retourne une liste vide
        }

        char ch = str.charAt(0); // Prend le premier caractere de str
        String subStr = str.substring(1); // Prend la sous chaine a partir du second caractere

        ArrayList<String> prevResult = getArrangement(subStr); // Recursif

        // Stocker les permutations générées dans res
        ArrayList<String> Res = new ArrayList<>();
        for (String val : prevResult) {
            for (int i = 0; i <= val.length(); i++) {
                Res.add(val.substring(0, i) + ch + val.substring(i));
            }
        }
        return Res;  // Returne res
    }

    public static int[][] generateGraphComplet(int size) {
        // Méthode pour générer un graphe aléatoire complet
        int[][] graph = new int[size][size];// On crée un tabeau à deux dimensions de taille size

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j > i; j--) {
                int value = (int) (Math.random() * 9) +1 ;// On tire une valeur au sort entre 1 et 10 non compris
                graph[i][j] = value;
                graph[j][i] = value;
            }
        }

        return graph;// On retourne le tableau
    }


}
