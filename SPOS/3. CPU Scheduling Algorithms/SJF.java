import java.util.*;

class Process {
    String id;
    int arrival_time, burst_time, completion_time, turn_around_time, waiting_time, remaining_time;
    boolean is_completed;

    Process () {}

    Process(String id, int arrival_time, int burst_time) {
        this.id = id;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.remaining_time = burst_time;
        this.is_completed = false;
    }
}

public class SJF {
    public static void main(String[] args) {

        int n, at, bt, total_processes_completed = 0, current_time = 0;
        float wt = 0, tat = 0;
        Scanner sc = new Scanner(System.in);
        

        System.out.print("Enter the number of processes: ");
        n = sc.nextInt();
        Process[] process_queue = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time for Process " + (i + 1) + ": ");
            int ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + ": ");
            int br = sc.nextInt();
            process_queue[i] = new Process(("p" + (i+1)), ar, br);
        }

        while (total_processes_completed < n) {
            int shortest_job_index = -1;
            int min_burst_time = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (process_queue[i].arrival_time <= current_time && !process_queue[i].is_completed && process_queue[i].remaining_time < min_burst_time) {
                    min_burst_time = process_queue[i].remaining_time;
                    shortest_job_index = i;
                }
            }

            if (shortest_job_index == -1) {
                current_time++;
                continue;
            }

            Process current_process = process_queue[shortest_job_index];
            current_process.remaining_time--;
            current_time++;

            if (current_process.remaining_time == 0) {
                current_process.is_completed = true;
                current_process.completion_time = current_time;
                current_process.turn_around_time = current_process.completion_time - current_process.arrival_time;
                current_process.waiting_time = current_process.turn_around_time - current_process.burst_time;

                wt += current_process.waiting_time;
                tat += current_process.turn_around_time;

                total_processes_completed++;
            }

        }

        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process p : process_queue) {
            System.out.printf("%s\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n", 
                p.id, p.arrival_time, p.burst_time, p.completion_time, p.turn_around_time, p.waiting_time);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", tat / n);
        System.out.printf("Average Waiting Time: %.2f\n", wt / n);

        sc.close();
        
    }
        
}