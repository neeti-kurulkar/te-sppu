import java.util.*;

public class NextFit {
    
    static void NF (int block_size[], int process_size[], int b, int p) {
        int allocation[] = new int[p];
        int j = 0, t = b-1;

        for(int i = 0; i < allocation.length; i++) allocation[i] = -1;
        
        for (int i = 0; i < p; i++) {
            while (j < b) {
                if (block_size[j] > process_size[i]){
                    allocation[i] = j;
                    block_size[j] -= process_size[i];
                    t = (j-1) % b;
                    break;
                }
                if (t == j) {
                    t = (j - 1) % b;
                    break;
                }
                j = (j + 1) % b;
            }
        }

        System.out.print("\nProcess No.\tProcess Size\tBlock no.\n");
        for (int i = 0; i < p; i++) {
            System.out.print( i + 1 + "\t\t\t\t" + process_size[i] + "\t\t\t\t");
                if (allocation[i] != -1) {
                    System.out.print(allocation[i] + 1);
                } else {
                    System.out.print("Not Allocated");
                }
                System.out.println(""); 
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
		
		
        NF(blockSize, processSize, b, p);
        
        sc.close();
	}
}
