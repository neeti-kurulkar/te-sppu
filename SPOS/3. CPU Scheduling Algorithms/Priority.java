import java.util.*;

class Process {
    String id;
    int arrival_time;
    int burst_time;
    int priority;
    int completion_time;
    int turn_around_time;
    int waiting_time;
    boolean isCompleted = false;

    Process(String pid, int ar, int br, int pr) {
        id = pid;
        arrival_time = ar;
        burst_time = br;
        priority = pr;
    }
}

public class Priority {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number of Processes: ");
        int n = sc.nextInt();
        Process[] process_queue = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Arrival Time for Process " + (i + 1) + " : ");
            int ar = sc.nextInt();
            System.out.print("Enter Burst Time for Process " + (i + 1) + " : ");
            int br = sc.nextInt();
            System.out.print("Enter Priority for Process " + (i + 1) + " : ");
            int pr = sc.nextInt();
            process_queue[i] = new Process(("p" + (i+1)), ar, br, pr);
        }

        Arrays.sort(process_queue, Comparator.comparingInt(p -> p.arrival_time));

        int currentTime = 0, completedProcesses = 0;
        float total_tat = 0, total_wt = 0;

        while (completedProcesses < n) {
            Process currProcess = null;
            int highest_priority = Integer.MAX_VALUE;

            for (Process p : process_queue) {
                if (p.arrival_time <= currentTime && !p.isCompleted && p.priority < highest_priority) {
                    highest_priority = p.priority;
                    currProcess = p;
                }
            }

            if(currProcess != null) {
                currProcess.completion_time = currentTime + currProcess.burst_time;
                currProcess.turn_around_time = currProcess.completion_time - currProcess.arrival_time;
                currProcess.waiting_time = currProcess.turn_around_time - currProcess.burst_time;

                total_tat += currProcess.turn_around_time;
                total_wt += currProcess.waiting_time;

                currProcess.isCompleted = true;
                completedProcesses++;
                currentTime = currProcess.completion_time;
            }
            else {
                currentTime++;
            }
        }

        // Print process details
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Process\t\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < n; i++) {
            System.out.println(process_queue[i].id + "\t\t" + process_queue[i].arrival_time + "\t\t\t" +
                               process_queue[i].burst_time + "\t\t\t" + process_queue[i].completion_time + "\t\t\t" +
                               process_queue[i].turn_around_time + "\t\t\t" + process_queue[i].waiting_time);
        }

        System.out.println("\nAverage Waiting Time = " + (total_wt / n));
        System.out.println("Average Turn Around Time = " + (total_tat / n));

        sc.close();
    }
        
}
