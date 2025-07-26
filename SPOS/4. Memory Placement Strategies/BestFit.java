import java.util.*;

public class BestFit {
    
    static void BF (int process_size[], int block_size[], int p, int b) {
        int allocation[] = new int[p];

        for (int i = 0; i < allocation.length; i++) allocation[i] = -1;

        for (int i = 0; i < p; i++) {
            int best_index = -1;
            for (int j = 0; j < b; j++) {
                if (block_size[j] > process_size[i]) {
                    if(best_index == -1) best_index = j;
                    else if (block_size[best_index] > block_size[j]) best_index = j;
                }
            }
            if (best_index != -1) {
                allocation[i] = best_index;
                block_size[best_index] -= process_size[i];
            }
        }

        System.out.println("\nProcess No.\tProcess Size\tBlock no."); 
        for (int i = 0; i < p; i++) 
        { 
            System.out.print("   " + (i+1) + "\t\t" + process_size[i] + "\t\t"); 
            if (allocation[i] != -1) 
                System.out.print(allocation[i] + 1); 
            else
                System.out.print("Not Allocated"); 
            System.out.println(); 
        }
    }

    public static void main(String[] args) {
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
		
		
        BF(blockSize, processSize, b, p);
        
        sc.close();
	}
}
