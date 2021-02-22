import java.lang.Math;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Scanner;

public class Graph {
    static Scanner file = new Scanner(System.in);
    public static void main(String[] args){
        
        System.out.println("-- Generate Graph --\n"); 
        printGraph(generateGraph(4));

        System.out.println("\n\n-- Read Graph --\n");
        readGraph();

        System.out.println("\n\n-- Is connect --");
        int[][] graphConnect = {{0,2,0,0,0,0},
                                {0,0,5,0,0,0},
                                {0,0,0,7,2,0},
                                {0,0,0,0,0,0},
                                {0,0,0,0,0,6},
                                {0,0,0,0,0,0}};
        System.out.println("Graph connect : ");
        System.out.println(isConnect(graphConnect));

        int[][] graphNonConnect = {{0,2,3,0,0,0},
                                   {0,0,5,3,0,0},
                                   {0,0,0,7,0,0},
                                   {0,0,0,0,0,0},
                                   {0,0,0,0,0,6},
                                   {0,0,0,0,0,0}};
        System.out.println("\nGraph non connect : ");
        System.out.println(isConnect(graphNonConnect));
    }

    //Question 1
    public static void readGraph() {
        int numLigne = 0;
        String ligne[] = file.nextLine().split(",");
        int graph [][] = new int[ligne.length][ligne.length];

        while(file.hasNextLine()){
            for(int numColonne = 0;numColonne < ligne.length;numColonne++){
                graph[numLigne][numColonne] = Integer.parseInt(ligne[numColonne]);
            }
            numLigne++;
            ligne = file.nextLine().split(",");
        }

        printGraph(graph);
    }

    public static int[][] generateGraph(int size) {
        int[][] graph = new int[size][size];

        for(int i=0; i<size; i++){
            for(int j=size-1; j>i; j--){
                int value = (int) (Math.random()*9);
                graph[i][j] = value;
            }    
        }

        return graph;
    }

    public static void printGraph(int[][] graph){
        for(int i=0; i<graph.length; i++){
            for(int j=0; j<graph.length; j++){
                System.out.print(graph[i][j] + " | ");
            }
            System.out.println("");
        }
    }

    //Question 2
    public static boolean isConnect(int[][] graph){
        Set<Integer> box = new HashSet<Integer>();
        printGraph(graph);

        for(int i=0; i<graph.length; i++){
            for(int j=0; j<graph.length; j++){
                if(graph[i][j] != 0){
                    if(box.isEmpty()){
                        box.add(i);
                        box.add(j);
                    }else{
                        if(box.contains(i) || box.contains(j)){
                            box.add(i);
                            box.add(j);
                        }
                    }
                    
                }
                
            }
        }
        
        if(box.size() == graph.length){
            return true;
        }
        return false;
    }



    //Question 3
    public static int[][] dijkstra(int[][] graph){
        int[][] distance = new int[graph.length][graph.length];
        
        return distance;
    }

}