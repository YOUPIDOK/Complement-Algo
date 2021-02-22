import java.lang.Math;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Scanner;

public class Graph {
    static Scanner file = new Scanner(System.in);
    public static void main(String[] args){
        
        System.out.println("-- Generate Graph --"); 
        printGraph(generateGraph(4));

        System.out.println("\n-- Read Graph --");
        readGraph();

        System.out.println("\n-- Is connect --");
        int[][] graph = {{0,2,0,0,0,0},
                         {0,0,5,0,0,0},
                         {0,0,0,7,0,0},
                         {0,0,0,0,1,0},
                         {0,0,0,0,0,6},
                         {0,0,0,0,0,0}};
        isConnect2(graph);
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
        ArrayList<Integer> boite = new ArrayList<Integer>();
        System.out.println("ESSAI");
        printGraph(graph);
        for(int u = 0;u<3;u++){
            for(int i=0; i<graph.length; i++){
                for(int j=0; j<graph.length;j++){
                    if(graph[i][j] != 0){
                        if(boite.isEmpty()){
                            boite.add(i);
                        }else if(boite.contains(j) && !boite.contains(i)){
                            System.out.println("ANALYSE DE "+graph[i][j]);
                            System.out.println("AH ! "+i);
                            boite.add(i);
                        }
    
                    }
    
                }
            }

        }

        System.out.println(boite);
        return false;
    }

    public static boolean isConnect2(int[][] graph){
        printGraph(graph);

        Set<Integer> box = new HashSet<Integer>();
        boolean firstOperation = false;
        // for(int i=0; i<)
        

        return false;
    }



    //Question 3
    public static int[][] dijkstra(){

        return null;
    }

}