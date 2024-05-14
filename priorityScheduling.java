import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process {
    int id;
    int priority;
    int burstTime;
    int WaitingTime;

    public Process(int id, int priority, int burstTime) {
        this.id = id;
        this.priority = priority;
        this.burstTime = burstTime;
        this.WaitingTime=0;
    }
}

class PriorityScheduling {
    static void  Aging(List<Process> processes)
    {
        for(Process process :processes)
        {
            process.WaitingTime++;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        List<Process> processes = new ArrayList<>();
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter details for Process " + i);
            System.out.print("Enter priority: ");
            int priority = scanner.nextInt();
            System.out.print("Enter burst time: ");
            int burstTime = scanner.nextInt();

            processes.add(new Process(i, priority, burstTime));
        }
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numProcesses - 1; j++) {
                if (processes.get(j).priority > processes.get(j + 1).priority) {
                    Process tempProcess = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, tempProcess);
                }
            }
        }
        int currentTime = 0;
        int total_TurnaroundTime = 0;
        int total_WaitingTime = 0;
        System.out.print("Processes Order: ");
        for (Process process : processes)
        {
            System.out.print(process.id + " ");
        }
        System.out.println();
        int Aging_maxwaitTime=5;
        System.out.println("Process\tPriority\tBurst Time\tWaiting Time\tTurnaround Time");
        for (Process process : processes) {
            int waitingTime = currentTime;
            int turnaroundTime = currentTime + process.burstTime;
            total_WaitingTime += waitingTime;
            total_TurnaroundTime += turnaroundTime;
            System.out.println(process.id + "\t\t\t" + process.priority + "\t\t\t" + process.burstTime + "\t\t\t" +
                    waitingTime + "\t\t\t" + turnaroundTime+"\n");
            Aging(processes);
            currentTime = turnaroundTime;
            if(process.WaitingTime>Aging_maxwaitTime)
            {
                process.priority--;
                process.WaitingTime=0;
            }
        }
        float Avg_waitingTime = (float)total_WaitingTime/numProcesses;
        float Avg_TurnaroundTime = (float)total_TurnaroundTime/numProcesses;
        System.out.println("Average waiting Time = "+(Avg_waitingTime)+"\n");
        System.out.println("Average Turnaround Time = "+(Avg_TurnaroundTime)+"\n");
        scanner.close();
    }
}