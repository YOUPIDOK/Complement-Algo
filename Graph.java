import java.lang.Math;
import java.util.Scanner;

public class Graph {

    public static void main(String[] args){
        printGraph(generateGraph(10));
        readGraph();
    }

    //Question 1
    public static void readGraph() {
        Scanner c = new Scanner(System.in);
        while(c.hasNextLine()){
            System.out.println(c.nextLine());
        }
    }

    public static int[][] generateGraph(int size) {
        int[][] graph = new int[size][size];

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                graph[i][j] = (int) (Math.random()*9);
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
        
        return false;
    }

    //Question 3
    public static int[][] dijkstra(){

        return null;
    }

}