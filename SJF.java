import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.completionTime = 0;
    }
}

class SJFScheduler {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numberOfProcesses = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        System.out.println("Enter the arrival time and burst time for each process:");

        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.printf("Process %d - Arrival Time: ", (i + 1));
            int arrivalTime = scanner.nextInt();
            System.out.printf("Process %d - Burst Time: ", (i + 1));
            int burstTime = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        sjfScheduler(processes);

        double averageWaitingTime = calculateAverageWaitingTime(processes);
        System.out.println("Average Waiting Time: " + averageWaitingTime);

        double averageTurnaroundTime = calculateAverageTurnaroundTime(processes);
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);

        scanner.close();
    }

    private static void sjfScheduler(List<Process> processes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the context switch time: ");
        int contextSwitchTime = scanner.nextInt();
        scanner.close();
        int currentTime = 0;
        int completedProcesses = 0;
        List<Process> executionOrder = new ArrayList<>();

        while (completedProcesses < processes.size()) {
            int shortestBurstTime = Integer.MAX_VALUE;
            int shortestProcessIndex = -1;

            for (int i = 0; i < processes.size(); i++) {
                Process process = processes.get(i);
                if (process.arrivalTime <= currentTime && process.burstTime < shortestBurstTime && process.burstTime > 0) {
                    shortestBurstTime = process.burstTime;
                    shortestProcessIndex = i;
                }
            }

            if (shortestProcessIndex == -1)
            {
                currentTime++;
            }
            else
            {
                Process shortestJob = processes.get(shortestProcessIndex);
                if (!executionOrder.isEmpty())
                {
                    currentTime += contextSwitchTime;
                }
                shortestJob.completionTime = currentTime + shortestJob.burstTime;
                shortestJob.waitingTime = currentTime - shortestJob.arrivalTime;
                shortestJob.turnaroundTime = shortestJob.waitingTime + shortestJob.burstTime;

                currentTime = shortestJob.completionTime;
                shortestJob.burstTime = 0;
                completedProcesses++;
                executionOrder.add(shortestJob);
            }
        }
        System.out.println("Process Execution Order:");
        for (Process process : executionOrder) {
            System.out.println("Process " + process.id + " - Waiting Time: " + process.waitingTime
                    + ", Turnaround Time: " + process.turnaroundTime);
        }
    }
    private static double calculateAverageWaitingTime(List<Process> processes)
    {
        int totalWaitingTime = 0;
        for (Process process : processes)
        {
            totalWaitingTime += process.waitingTime;
        }
        return (double) totalWaitingTime / processes.size();
    }
    private static double calculateAverageTurnaroundTime(List<Process> processes)
    {
        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
        }
        return (double) totalTurnaroundTime / processes.size();
    }
}