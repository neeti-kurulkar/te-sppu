import java.util.Scanner;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int remaining_bt;
    int completion_time;
    int turn_around_time;
    int waiting_time;
    boolean is_completed;

    Process(String pid, int at, int bt) {
        id = pid;
        arrival_time = at;
        burst_time = bt;
        remaining_bt = bt; // Remaining burst time is initially equal to burst time
        is_completed = false;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes (maximum 10): ");
        int n = sc.nextInt();
        Process[] process_queue = new Process[n];

        System.out.println("Enter the Arrival Time and Burst Time for each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " (Arrival Time): ");
            int at = sc.nextInt();
            System.out.print("P" + (i + 1) + " (Burst Time): ");
            int bt = sc.nextInt();
            process_queue[i] = new Process("P" + (i + 1), at, bt);
        }

        System.out.print("Enter the quantum time: ");
        int quantum_time = sc.nextInt();

        // Sort processes by arrival time using Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (process_queue[j].arrival_time > process_queue[j + 1].arrival_time) {
                    Process temp = process_queue[j];
                    process_queue[j] = process_queue[j + 1];
                    process_queue[j + 1] = temp;
                }
            }
        }

        // Initialize variables
        int current_time = 0, processes_completed = 0, total_tat = 0, total_wt = 0; // Total Turnaround Time and Waiting Time

        while (processes_completed < n) {
            boolean process_executed = false;

            for (Process p : process_queue) {
                if(p.arrival_time < current_time && !p.is_completed){             
                    process_executed = true;

                    if(p.remaining_bt > quantum_time) {
                        current_time += quantum_time;
                        p.remaining_bt -= quantum_time;
                    }
                    else {
                        current_time += p.remaining_bt;
                        p.remaining_bt = 0;
                        p.completion_time = current_time;
                        p.turn_around_time = p.completion_time - p.arrival_time;
                        p.waiting_time = p.turn_around_time - p.burst_time;
                        total_tat += p.turn_around_time;
                        total_wt += p.waiting_time;
                        p.is_completed = true;
                        processes_completed++;
                    }
                }
            }
            if (!process_executed) current_time++;
        }

        // Display process information
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Process\t\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        System.out.println("------------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            Process p = process_queue[i];
            System.out.println(p.id + "\t\t" + p.arrival_time + "\t\t" + p.burst_time + "\t\t" +
                               p.completion_time + "\t\t" + p.turn_around_time + "\t\t" + p.waiting_time);
        }

        // Calculate and display average turnaround time and waiting time
        System.out.println("\nAverage Turnaround Time = " + (float) total_tat / n);
        System.out.println("Average Waiting Time = " + (float) total_wt / n);

        sc.close();
    }
}
