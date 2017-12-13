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
        int available[][];
        int n; //number of processes
        int m; //number of resources

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        n = sc.nextInt();
        System.out.print("Enter number of resources: ");
        m = sc.nextInt();
        
        need = new int[n][m];
        max = new int[n][m];
        allocate = new int[n][m];
        available = new int[1][m];

        System.out.println("Enter allocation matrix: ");
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                allocate[i][j] = sc.nextInt();

        System.out.println("Enter max matrix: ");
        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                max[i][j] = sc.nextInt();

        System.out.println("Enter available matrix: ");
            for(int j = 0; j < m; j++)
                available[0][j] = sc.nextInt();

        sc.close();

        //calculate need
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                need[i][j] = max[i][j]-allocate[i][j];
            }
        }
        
        isSafe(available, allocate, n, m, need);
    }
    
    public static void isSafe(int[][] available, int[][] allocate, int n, int m, int[][] need) {
        boolean done[] = new boolean[n];
        int j = 0;

        while(j < n) {
             boolean allocated = false;
             for(int i = 0; i < n; i++)
                 if(!done[i] && check(i, available, need, m)) {
                    for(int k = 0; k < m; k++)
                        available[0][k] = available[0][k] + allocate[i][k];
                    System.out.println("Allocated process: P" + i);
                    allocated = done[i] = true;
                    j++;
                 }
            if(!allocated) break;
        }
        if(j == n)
            System.out.println("\nProcesses are safely allocated!");
        else
            System.out.println("\nProcesses can NOT be allocated safely!");
    }
    
    private static boolean check(int i, int[][] available, int[][] need, int m) {
        for(int j = 0; j < m; j++) {
            if(available[0][j] < need[i][j]) {
                return false;
            }
        }
        return true;
    }
    
}