import java.util.Scanner;

public class WorstFit 
{ 
    static void WF(int blockSize[], int m, int processSize[], int n) 
    { 
        int allocation[] = new int[n]; 
       
        for (int i = 0; i < allocation.length; i++) 
            allocation[i] = -1; 
            
        for (int i=0; i<n; i++) 
        { 
            int wstIdx = -1; 
            for (int j=0; j<m; j++) { 
                if (blockSize[j] >= processSize[i]) { 
                    if (wstIdx == -1) 
                        wstIdx = j; 
                    else if (blockSize[wstIdx] < blockSize[j]) 
                        wstIdx = j; 
                } 
            } 
       
            if (wstIdx != -1) {  
                allocation[i] = wstIdx; 
        
                blockSize[wstIdx] -= processSize[i]; 
            } 
        } 
       
        System.out.println("\nProcess No.\tProcess Size\tBlock no."); 
        for (int i = 0; i < n; i++) 
        { 
            System.out.print("   " + (i+1) + "\t\t" + processSize[i] + "\t\t"); 
            if (allocation[i] != -1) 
                System.out.print(allocation[i] + 1); 
            else
                System.out.print("Not Allocated"); 
            System.out.println(); 
        } 
    } 
      
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter number of blocks: ");
		int b = sc.nextInt();
		
		System.out.println("Enter number of processes: ");
		int p = sc.nextInt();
		
		int blockSize[] = new int[b];
		int processSize[] = new int[p];
		
		for (int i = 0; i < b; i++) {
			System.out.println("Enter size of block " + (i+1) + " : ");
			blockSize[i] = sc.nextInt();
		}
		
		for (int i = 0; i < p; i++) {
			System.out.println("Enter size of process " + (i+1) + " : ");
			processSize[i] = sc.nextInt();
		}
		
		
        WF(blockSize, b, processSize, p);
        
        sc.close();
	} 
} 
