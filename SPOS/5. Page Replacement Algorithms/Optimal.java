import java.io.*;

public class Optimal {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number of Frames: ");
        int frames = Integer.parseInt(br.readLine());

        System.out.println("Enter the length of the Reference string: ");
        int ref_len = Integer.parseInt(br.readLine());

        int[] reference = new int[ref_len];
        int[][] mem_layout = new int[ref_len][frames];
        int[] buffer = new int[frames];
        int hit = 0, fault = 0, pointer = 0;
        boolean isFull = false;

        // Initialize buffer with -1
        for (int j = 0; j < frames; j++) buffer[j] = -1;

        System.out.println("Enter the reference string: ");
        for (int i = 0; i < ref_len; i++) {
            reference[i] = Integer.parseInt(br.readLine());
        }

        // Optimal page replacement logic
        for (int i = 0; i < ref_len; i++) {
            boolean isHit = false;

            // Check for hits
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    isHit = true;
                    hit++;
                    break;
                }
            }

            // Page fault logic
            if (!isHit) {
                if (isFull) {
                    int[] index = new int[frames];
                    boolean[] indexFlag = new boolean[frames];

                    // Determine future uses of pages in buffer
                    for (int j = i + 1; j < ref_len; j++) {
                        for (int k = 0; k < frames; k++) {
                            if (reference[j] == buffer[k] && !indexFlag[k]) {
                                index[k] = j;
                                indexFlag[k] = true;
                                break;
                            }
                        }
                    }

                    // Find the page with the farthest use
                    int max = -1;
                    pointer = 0;
                    for (int j = 0; j < frames; j++) {
                        if (!indexFlag[j]) 
                            index[j] = Integer.MAX_VALUE;
                        
                        if (index[j] > max) {
                            max = index[j];
                            pointer = j;
                        }
                    }
                }

                // Replace the page at pointer
                buffer[pointer] = reference[i];
                fault++;

                if (!isFull) {
                    pointer++;
                    if (pointer == frames) {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }

            // Update memory layout
            for (int j = 0; j < frames; j++) {
                mem_layout[i][j] = buffer[j];
            }
        }

        // Print memory layout
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
