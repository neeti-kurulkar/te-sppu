import java.io.*;

public class FIFO {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Please enter the number of Frames: ");
        int frames = Integer.parseInt(br.readLine());

        System.out.println("Please enter the length of the Reference string: ");
        int ref_len = Integer.parseInt(br.readLine());

        int reference[] = new int[ref_len];
        int buffer[] = new int[frames];
        int mem_layout[][] = new int[ref_len][frames];
        int pointer = 0, hit = 0, fault = 0;
        
        // Initialize buffer
        for (int j = 0; j < frames; j++) buffer[j] = -1;

        System.out.println("Enter the reference string: ");
        for (int i = 0; i < ref_len; i++) {
            reference[i] = Integer.parseInt(br.readLine());
        }

        // FIFO page replacement logic
        for (int i = 0; i < ref_len; i++) {
            boolean is_hit = false;
            
            // Check for hits
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == reference[i]) {
                    is_hit = true;
                    hit++;
                    break;
                }
            }
            
            // Page fault logic
            if (!is_hit) {
                buffer[pointer] = reference[i];
                fault++;
                pointer = (pointer + 1) % frames;
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

        System.out.println("The number of Hits: " + hit);
        System.out.println("Hit Ratio: " + (float) hit / ref_len);
        System.out.println("The number of Faults: " + fault);
    }
}
