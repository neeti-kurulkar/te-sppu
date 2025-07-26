import java.io.*;
import java.util.*;

public class LRU {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the number of Frames: ");
        int frames = Integer.parseInt(br.readLine());

        System.out.print("Enter the length of the Reference string: ");
        int ref_len = Integer.parseInt(br.readLine());

        int[] reference = new int[ref_len];
        int[][] mem_layout = new int[ref_len][frames];
        int[] buffer = new int[frames];
        List<Integer> stack = new ArrayList<>();
        int hit = 0, fault = 0;

        // Initialize buffer
        for (int j = 0; j < frames; j++) buffer[j] = -1;

        System.out.println("Enter the reference string:");
        for (int i = 0; i < ref_len; i++) {
            reference[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < ref_len; i++) {
            int current = reference[i];

            // Check for a hit
            if (stack.contains(current)) {
                stack.remove((Integer) current);
                stack.add(current); // Move current page to the most recent position in the stack
                hit++;
            } else {
                fault++;
                if (stack.size() < frames) {
                    // If there is space in the buffer, add the page directly
                    for (int j = 0; j < frames; j++) {
                        if (buffer[j] == -1) {
                            buffer[j] = current;
                            break;
                        }
                    }
                } else {
                    // Find the least recently used page and replace it
                    int lru = stack.remove(0); // Remove the least recently used page
                    for (int j = 0; j < frames; j++) {
                        if (buffer[j] == lru) {
                            buffer[j] = current; // Replace LRU page with current page
                            break;
                        }
                    }
                }
                stack.add(current); // Add the current page to the stack as the most recent
            }

            // Update the memory layout
            for (int j = 0; j < frames; j++) {
                mem_layout[i][j] = buffer[j];
            }
            
        }

        // Print memory layout
        System.out.println("\nMemory Layout:");
        for (int i = 0; i < frames; i++) {
            for (int j = 0; j < ref_len; j++) {
                System.out.printf("%3d ", mem_layout[j][i]);
            }
            System.out.println();
        }

        System.out.println("Total Hits: " + hit);
        System.out.println("Hit Ratio: " + (float) hit / ref_len);
        System.out.println("Total Faults: " + fault);
    }
}
