import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.*;

public class Graph {
    public static void main(String[] args) throws IOException {
        System.out.println("Graph connect : ");
        System.out.println("IsConnect : " + isConnect(readGraph("GraphConnect.txt")));

        System.out.println("\nGraph non connect : ");
        System.out.println("IsConnect : " + isConnect(readGraph("GraphNonConnect.txt")));

        System.out.println("\nGraph aléatoire : ");
        System.out.println("IsConnect : " + isConnect(generateGraph(10)));

        System.out.println("\nDjikstra : ");
        System.out.println("IsConnect : " + isConnect(readGraph("Graph2.txt")));
        System.out.println(dijkstra(readGraph("Graph2.txt"), 0, readGraph("Graph2.txt").length - 1));
        // int[][] distance = plusCourtChemin(readGraph("Dijkstra.txt"));
        // System.out.println("Distance : ");
        // printGraph(distance);

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
        printGraph(graph);

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

    // if(noeudsReperes.size()==1)

    // {
    // // On a que le noeud de départ
    // sommetRef = depart;
    // }else
    // {
    // // On choisit parmis les valeurs non marquées la plus petite
    // sommetRef = Collections.min(noeudsReperes);
    // }

    public static int dijkstra(int[][] graph, int depart, int arrivee) {
        ArrayList<Integer> noeudsMarques = new ArrayList<Integer>();
        ArrayList<Integer> noeudsReperes = new ArrayList<Integer>();
        ArrayList<Integer> valsNoeudsReperes = new ArrayList<Integer>();
        ArrayList<Integer> longueursChemin = new ArrayList();
        valsNoeudsReperes.add(graph[depart][depart]);
        noeudsReperes.add(depart);
        int valSommetRef = -1;
        int sommetRef = -1;
        while (noeudsMarques.size() != graph.length - 1) {

            valSommetRef = Collections.min(valsNoeudsReperes);
            sommetRef = noeudsReperes.get(valsNoeudsReperes.indexOf(valSommetRef));
            for (int sommetDest = 0; sommetDest < graph[sommetRef].length; sommetDest++) {
                if (graph[sommetRef][sommetDest] != 0 && !noeudsMarques.contains(sommetDest)) {
                    System.out.println("->" + sommetRef + "," + graph[sommetRef][sommetDest] + "," + sommetDest);
                    if (noeudsReperes.indexOf(sommetDest) >= 0
                            && valsNoeudsReperes.get(noeudsReperes.indexOf(sommetDest)) >= 0) {
                        valsNoeudsReperes.set(noeudsReperes.indexOf(sommetDest),
                                Math.min(valsNoeudsReperes.get(noeudsReperes.indexOf(sommetDest)),
                                        valSommetRef + graph[sommetRef][sommetDest]));
                    } else {
                        if (sommetDest == arrivee) {
                            System.out.println("AJOUT" + (valSommetRef + graph[sommetRef][sommetDest]));
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
    // int size = graph.length;
    // int[][] distance = new i

    // for (int j = size - 1; j > i; j-
    // int dist = plusCourtCheminEntreDeuxN
    // distance[i][j] = dist;
    // distance[j][i] = dist;
    // }
    // }

    // }

    // graph){
    // int dis
    // for(int i = 0;i<
    // if(graph[sommetRef][i]!=0 && (dist
    // distMin = graph[sommetRef][i];
    // }
    // }
    // r
    // }

    // sommetDest,int[][] graph){
    // int sommetRefTemp = sommet
    // ArrayList<Integer> noeudsAnaly
    // ArrayList<Integer> noeudsAAnalyser = new ArrayList<Integer>()
    // ArrayList<Integer> valsChemins = new ArrayList();
    // int valChemin = 0;
    // noeudsAAnalyser.ad
    // int i = 0;
    // boolean co
    // while(!noeudsAAnalyser.i

    // valsChemins.add(valChemin);
    // valChemin = valChemin -
    // graph[sommetRefTemp][no
    // // noeudsAnalyses.add(sommetRefTemp);
    // // sommetRefTemp = noeudsAAnalyser.ge
    // // noeudsAAnalyser.remove(0);
    // }
    // i
    // // On est en train d'analyser un noeud et on découvre une arêt
    // noeudsAAnalyser.add(i);
    // if(!counted){
    // valChemin +=
    // counted = true;
    // }
    // }
    // i
    // // On a finit d'analys
    // i = 0;
    // noeuds
    // noeudsAAnalyser.remove(sommetRefTe
    // sommetRefTemp = noeudsAAnalyser.get(0)
    // counted = false;
    // }else{
    // i++;
    // }

    // r
    // }

    // if(!isConnect(graph)) return null;
    // int[][] distance = new int[graph.l
    // for(int i=0; i<graph.length; i++){
    // int[] line = dijkstra(graph, i);
    // for(int j=0; j<graph.length; j++
    // distance[i][j] = line[j];
    // }
    // }

    // }

    // int[] line = new int[graph.length];
    // Set<Integer> visite = new HashSet<I

    // line = graph
    // visite.add(sommet);

    // int sommet = choiceSommet(line, visi
    // }

    // }

    // int min = 0;
    // int index;

    // if(!line.contains(
    // }
    // }

}
