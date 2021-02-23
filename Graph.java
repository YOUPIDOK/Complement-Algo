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

        System.out.println("\nPlus court chemin : ");
        int[][] distance = plusCourtChemin(readGraph("Dijkstra.txt"));
        System.out.println("Distance : ");
        printGraph(distance);

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

    // Question 3
    // public static int[][] plusCourtChemin2(int[][] graph) {
    //     if(!isConnect(graph)) return null;
    //     int size = graph.length;
    //     int[][] distance = new int[graph.length][graph.length];

    //     for (int i = 0; i < size; i++) {
    //         for (int j = size - 1; j > i; j--) {
    //             int dist = plusCourtCheminEntreDeuxNoeuds(i, j, graph);
    //             distance[i][j] = dist;
    //             distance[j][i] = dist;
    //         }
    //     }

    //     return distance;
    // }

    public static int plusCourteAretePartantDunNoeud(int sommetRef,int[][] graph){
        int distMin = 0;
        for(int i = 0;i<graph.length;i++){
            if(graph[sommetRef][i]!=0 && (distMin == 0 || graph[sommetRef][i]<distMin)){
                distMin = graph[sommetRef][i];
            }
        }
        return distMin;
    }
    
    // public static int[] getValsCheminsEntreDeuxNoeud(int sommetRef,int sommetDest,int[][] graph){
    //     int sommetRefTemp = sommetRef;
    //     ArrayList noeudsAnalyses = new ArrayList();
    //     ArrayList noeudsAAnalyser = new ArrayList();
    //     noeudsAAnalyser.add(sommetRef);
    //     int i = 0; 
    //     while(!noeudsAAnalyser.isEmpty()){
    //         if(graph[sommetRefTemp][i]!=0 && !noeudsAnalyses.contains(i)){   
    //             noeudsAAnalyser.add(i);
    //         }
    //         if(i==graph.length-1){
    //             i = 0;
    //             noeudsAAnalyser.remove(sommetRefTemp);
    //             noeudsAnalyses.add(i);
    //         }
    //     
    //     i++;
    //     }

    // }
    

    public static int[][] plusCourtChemin(int[][] graph){
        if(!isConnect(graph)) return null;
        int[][] distance = new int[graph.length][graph.length];
        for(int i=0; i<graph.length; i++){
            int[] line = dijkstra(graph, i);
            for(int j=0; j<graph.length; j++){
                distance[i][j] = line[j];
            }
        }
        
        return distance;
    }

    public static int[] dijkstra(int[][] graph,int sommet){
        int[] line = new int[graph.length];
        Set<Integer> visite = new HashSet<Integer>();

        //1ere Etape
        line = graph[sommet];
        visite.add(sommet);

        for(int i=0; i<graph.length-1; i++){
            int sommet = choiceSommet(line, visite);
        }

        return line;
    }

    public static int choiceSommet(int[] line,Set<Integer> viste){
        int min = 0;
        int index;

        for(int i : line){
            if(!line.contains(i) && )            
        }
    }
    
}   
