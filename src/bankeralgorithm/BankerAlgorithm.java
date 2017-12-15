package bankeralgorithm;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Mohamed ELGHAMRY and Mostafa HAZEM
 */
public class BankerAlgorithm {
    
    public static void main(String[] args) throws IOException {
        int allocate[][];
        int max[][];
        int need[][];
        int available[];
        int sequence[];
        int initAvailable[];
        int n; //number of processes
        int m; //number of resources
        int x;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        System.out.print("Enter number of resources: ");
        m = sc.nextInt();
        
        need = new int[n][m];
        max = new int[n][m];
        allocate = new int[n][m];
        available = new int[m];
        sequence = new int[n];
        
        
        System.out.println("Enter allocation matrix: ");
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                allocate[i][j] = sc.nextInt();

        System.out.println("Enter max matrix: ");
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                max[i][j] = sc.nextInt();

        System.out.println("Enter available: ");
            for(int j = 0; j < m; j++)
                available[j] = sc.nextInt();
        
        //calculate need
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                need[i][j] = max[i][j]-allocate[i][j];
            }
        }
        
        isSafe(available, allocate, n, m, need, sequence);
        
        System.out.println("Do you want to request more resources to a specific process?");
        System.out.print("[1]Yes /n [2]No: ");
        x = sc.nextInt();
        if(x == 1){
            request(available, allocate,max, need, n, m);
            boolean flag = isSafe(available, allocate, n, m, need, sequence);
        }
        sc.close();
    }
    
    public static void request(int[] available,int[][] allocate, int[][] max, int[][] need, int n, int m){
        int req[] = new int[m];
        int processNum,loop=1;
        
        while(loop>0){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter request: ");
            for(int i = 0; i < m; i++)
                req[i] = sc.nextInt();
            System.out.println("In which process you want to add those resources: ");
            processNum = sc.nextInt();
            boolean flag = true;
            for(int j = 0; j < m; j++) {
                if(available[j] < req[j]) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                for(int i = 0; i < m; i++)
                    allocate[processNum][i] += req[i];
                for(int i = 0; i < m; i++)
                    available[i] -= req[i];
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < m; j++){
                        need[i][j] = max[i][j]-allocate[i][j];
                    }
                }
                return;
            }
            else 
                System.out.println("There aren't enough available resources!");
                System.out.println("Please enter 1 to try again or a negative number to terminate: ");
                loop = sc.nextInt();
        }
        System.exit(0);
    }
    
    public static boolean isSafe(int[] available, int[][] allocate, int n, int m, int[][] need,int[] sequence) {
        boolean done[] = new boolean[n];
        int j = 0, p = 0;
        int av[] = new int [m];
        
        for(int i = 0; i < m; i++)
            av[i] = available[i];
        
        while(j < n) {
             boolean allocated = false;
             for(int i = 0; i < n; i++)
                 if(!done[i] && check(i, av, need, m)) {
                    for(int k = 0; k < m; k++)
                        av[k] = av[k] + allocate[i][k];
                    sequence[p++] = i;
                    allocated = done[i] = true;
                    j++;
                 }
            if(!allocated) break;
        }
        
        if(j == n){
            System.out.println("\nProcesses are safely allocated!");
            printSequence(sequence, n);
            return true;
        }
        else{
            System.out.println("\nProcesses can NOT be allocated safely *DEADLOCK* !");
            return false;
        }
    }
    
    private static boolean check(int i, int[] available, int[][] need, int m) {
        for(int j = 0; j < m; j++) {
            if(available[j] < need[i][j]) {
                return false;
            }
        }
        return true;
    }
    
    private static void printSequence(int[] sequence, int n){
        for(int i = 0; i < n; i++){
            System.out.println("Sequence of Allocation: ");
            System.out.println("Process" + sequence[i]);
        }
        for(int i = 0; i < n; i ++){
            sequence[i] = 0;
        }
    }
        
    
}