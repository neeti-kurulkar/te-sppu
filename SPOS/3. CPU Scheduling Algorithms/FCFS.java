import java.util.*;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int completion_time;
    int turn_around_time;
    int waiting_time;

    Process() {}

    Process(String pid, int ar, int br) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
    }
}

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Number of Processes: ");
        int n = sc.nextInt();
        
        Process[] process_queue = new Process[n];
        Process temp = new Process();
        int ar, br;
        float avgwt = 0, avgtat = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time for Process " + (i + 1) + ": ");
            ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + ": ");
            br = sc.nextInt();
            process_queue[i] = new Process(("p" + (i+1)), ar, br);
        }

        // Sorting based on arrival time
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < (n - i - 1); j++) {
                if (process_queue[j].arrival_time > process_queue[j + 1].arrival_time) {
                    temp = process_queue[j];
                    process_queue[j] = process_queue[j + 1];
                    process_queue[j + 1] = temp;
                }
            }
        }

        // Calculating completion, turnaround, and waiting times
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
            } else {
                if (process_queue[i].arrival_time > process_queue[i - 1].completion_time) {
                    process_queue[i].completion_time = process_queue[i].arrival_time + process_queue[i].burst_time;
                } else {
                    process_queue[i].completion_time = process_queue[i - 1].completion_time + process_queue[i].burst_time;
                }
            }
            process_queue[i].turn_around_time = process_queue[i].completion_time - process_queue[i].arrival_time;
            process_queue[i].waiting_time = process_queue[i].turn_around_time - process_queue[i].burst_time;

            avgwt += process_queue[i].waiting_time;
            avgtat += process_queue[i].turn_around_time;
        }

        // Display the process details
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Process\t\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        System.out.println("--------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            System.out.println(process_queue[i].id + "\t\t" + process_queue[i].arrival_time + "\t\t" + process_queue[i].burst_time + "\t\t" + process_queue[i].completion_time + "\t\t" + process_queue[i].turn_around_time + "\t\t" + process_queue[i].waiting_time);
        }

        // Display average waiting and turnaround times
        System.out.println("Average Waiting Time = " + (avgwt / n));
        System.out.println("Average Turn Around Time = " + (avgtat / n));

        sc.close();
    }
}

