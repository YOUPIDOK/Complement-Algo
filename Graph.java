import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.*;

public class Graph {
    public static void main(String[] args) throws IOException {
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("Saisir le numéro de la question à afficher ( de 1 à 6 ), saisir un autre chiffre pour quitter.");
            System.out.print("Numéro de la question : ");
            String txt = userIn.readLine();
            System.out.println();
            int number = 7;
            if(isInt(txt)){
                number = Integer.parseInt(txt);
            }
            if(number < 1 || number > 6){
                System.out.println("Au revoir :)");
                System.exit(0);
            }else if(number == 1){
                System.out.println("********* QUESTION 1 *********");
                System.out.println("********* Graph aléatoire *********");
                int[][] graphAleatoire = generateGraph(5);
                printGraph(graphAleatoire);
            }else if(number == 2){
                System.out.println("\n********* QUESTION 2 *********");
                System.out.println("********* Graph connect *********");
                printGraph(readGraph("GraphConnect.txt"));
                System.out.println("IsConnect : " + isConnect(readGraph("GraphConnect.txt")));
                System.out.println("\n********* Graph non connect *********");
                printGraph(readGraph("GraphNonConnect.txt"));
                System.out.println("IsConnect : " + isConnect(readGraph("GraphNonConnect.txt")));
            }else if(number == 3){
                System.out.println("\n********* QUESTION 3 *********");
                System.out.println("********* Djikstra pour tous les points : *********");
                System.out.println("Graph de départ : ");
                printGraph(readGraph("GraphConnexe2.txt"));
                System.out.println("\nPlus court chemin entre tous les points :");
                printGraph(getDistanceMinForAllPoints(readGraph("GraphConnexe2.txt")));
            }else if(number == 4){
                System.out.println("\n********* QUESTION 4 *********");
                System.out.println("********* Graph complet *********");
                printGraph(readGraph("GraphComplet.txt"));
                System.out.println("Is complet : " + isComplet(readGraph("GraphComplet.txt")));

                System.out.println("\n********* Graph non complet *********");
                int[][] graphNonComplet = generateGraph(5);
                printGraph(graphNonComplet);
                System.out.println("Is complet : " + isComplet(graphNonComplet));

                System.out.println("\n********* Cycle *********");
                System.out.println("TRAJET TOTAL : "+cycle(readGraph("GraphComplet.txt"), 0));
            }else if(number == 5){

            }else if(number == 6){

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
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        int size = line.split(",").length;
        int graph[][] = new int[size][size];
        int i = 0;

        while (line != null) {
            String[] tab = line.split(",");
            for (int j = 0; j < size; j++) {
                graph[i][j] = Integer.parseInt(tab[j]);
            }
            i++;
            line = br.readLine();
        }

        br.close();
        return graph;
    }

    public static int[][] generateGraph(int size) {
        int[][] graph = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j > i; j--) {
                if (Math.random() < 0.3) {
                    int value = (int) (Math.random() * 9);
                    graph[i][j] = value;
                    graph[j][i] = value;
                }
            }
        }

        return graph;
    }

    public static void printGraph(int[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                System.out.print(graph[i][j] + " | ");
            }
            System.out.println("");
        }
    }

    // Question 2
    public static boolean isConnect(int[][] graph) {
        Set<Integer> box = new HashSet<Integer>();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] != 0) {
                    if (box.isEmpty()) {
                        box.add(i);
                        box.add(j);
                    } else {
                        if (box.contains(i) || box.contains(j)) {
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
        ArrayList<Integer> noeudsMarques = new ArrayList<Integer>();
        ArrayList<Integer> noeudsReperes = new ArrayList<Integer>();
        ArrayList<Integer> valsNoeudsReperes = new ArrayList<Integer>();
        ArrayList<Integer> longueursChemin = new ArrayList();
        valsNoeudsReperes.add(graph[depart][depart]);
        noeudsReperes.add(depart);
        while (noeudsMarques.size() != graph.length - 1) {

            int valSommetRef = Collections.min(valsNoeudsReperes);
            int sommetRef = noeudsReperes.get(valsNoeudsReperes.indexOf(valSommetRef));
            for (int sommetDest = 0; sommetDest < graph[sommetRef].length; sommetDest++) {
                if (graph[sommetRef][sommetDest] != 0 && !noeudsMarques.contains(sommetDest)) {
                    if (noeudsReperes.indexOf(sommetDest) >= 0 && valsNoeudsReperes.get(noeudsReperes.indexOf(sommetDest)) >= 0) {
                        valsNoeudsReperes.set(noeudsReperes.indexOf(sommetDest),Math.min(valsNoeudsReperes.get(noeudsReperes.indexOf(sommetDest)),valSommetRef + graph[sommetRef][sommetDest]));
                    } else {
                        if (sommetDest == arrivee) {
                            longueursChemin.add(valSommetRef + graph[sommetRef][sommetDest]);
                        } else {
                            noeudsReperes.add(sommetDest);
                            valsNoeudsReperes.add(valSommetRef + graph[sommetRef][sommetDest]);
                        }

                    }

                }

            }

            valsNoeudsReperes.remove(noeudsReperes.indexOf(sommetRef));
            noeudsReperes.remove(noeudsReperes.indexOf(sommetRef));
            noeudsMarques.add(sommetRef);
        }

        return Collections.min(longueursChemin);
    }

    public static int[][] getDistanceMinForAllPoints(int[][] graph){
        int size = graph.length;
        int[][] tabDistances = new int[graph.length][graph.length];
        for (int i = 0; i < size; i++) {
            for (int j = size - 1; j > i; j--) {
                int value = dijkstra(graph,i,j);
                tabDistances[i][j] = value;
                tabDistances[j][i] = value;
            }
        }
        return tabDistances;
    }


    // Question 4
    // Dans l'algorithme ci-dessous, dès qu'on voit une arête on l'emprunte
    public static int cycle(int[][] graph,int depart){
        ArrayList<Integer> noeudsMarques = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<Integer>();
        ArrayList<Integer> sommetsDistances  = new ArrayList<Integer>();
        String[] a = {"A","B","C","D","E","F","G","H","I","J"};
        int dist = 0;
        noeudsMarques.add(depart);
        int sommetRef = depart;
        System.out.println("DISTANCE : "+dist+","+a[sommetRef]);
        while (noeudsMarques.size() != graph.length ) {
            for (int sommetDest = 0; sommetDest < graph[sommetRef].length; sommetDest++) {
                if (graph[sommetRef][sommetDest] != 0 && !noeudsMarques.contains(sommetDest)) {
                    distances.add(graph[sommetRef][sommetDest]);
                    sommetsDistances.add(sommetDest);
                }
            }
            int distChoisie = Collections.min(distances);
            dist += distChoisie;
            noeudsMarques.add(sommetRef);
            sommetRef = sommetsDistances.get(distances.indexOf(distChoisie));
            distances.clear();
            sommetsDistances.clear();
            System.out.println("DISTANCE : "+dist+","+a[sommetRef]);
        }
        // Ici l'algorithme va retourner 230 alors que le minimum vaut 190
        return dist + graph[sommetRef][depart];
    }
    public static boolean isComplet(int[][] graph){
        if(isConnect(graph)){
            for(int i = 0;i<graph.length;i++){
                for (int j = graph.length - 1; j > i; j--) {
                    if(graph[i][j]==0){
                        return false;
                    }
                }
            }
        }else{
            return false;
        }
        return true;
    }
}