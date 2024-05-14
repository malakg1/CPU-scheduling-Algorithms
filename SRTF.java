import java.util.*;

class ShortestRemainingTimeFirst {
    static class Process {
        int processId;
        int burstTime;
        int arrivalTime;
        int remainingTime;
        int agingTime;

        public Process(int processId, int burstTime, int arrivalTime)
        {
            this.processId = processId;
            this.burstTime = burstTime;
            this.arrivalTime = arrivalTime;
            this.remainingTime = burstTime;
            this.agingTime = 0;
        }
    }
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int numberOfProcesses = scanner.nextInt();
        Process[] processes = new Process[numberOfProcesses];
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("Enter details for process " + (i + 1) + ":");
            System.out.println("Arrival time: ");
            int arrivalTime = scanner.nextInt();
            System.out.println("Burst time: ");
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime, arrivalTime);
        }
        display(processes, numberOfProcesses);
    }
    static void display(Process processes[], int n) {
        int waitingTime[] = new int[n], turnaroundTime[] = new int[n];
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        calculateWaitingTime(processes, n, waitingTime);
        calculateTurnAroundTime(processes, n, waitingTime, turnaroundTime);
        System.out.println();
        System.out.println("Processes " +
                " Arrival time " +
                " Burst time " +
                " Waiting time " +
                " Turnaround time");

        for (int i = 0; i < n; i++)
        {
            totalWaitingTime += waitingTime[i];
            totalTurnaroundTime += turnaroundTime[i];
            System.out.println(" P" + processes[i].processId + "\t\t\t"
                    + processes[i].arrivalTime + "\t\t\t "
                    + processes[i].burstTime + " \t\t\t " + waitingTime[i]
                    + "  \t\t\t " + turnaroundTime[i]);
        }
        System.out.println();
        System.out.println("Average waiting time = " +
                (float) totalWaitingTime / n);
        System.out.println("Average turnaround time = " +
                (float) totalTurnaroundTime / n);
    }

    static void calculateWaitingTime(Process processes[], int n, int wt[]) {
        int remainingTime[] = new int[n];
        int complete = 0, currentTime = 0, minRemaining = Integer.MAX_VALUE;
        int shortestProcess = 0, finishTime;
        boolean check = false;
        List<Integer> executionOrder = new ArrayList<>();
        for (int i = 0; i < n; i++){
            remainingTime[i] = processes[i].burstTime;
            processes[i].agingTime = 0;
        }
        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((processes[j].arrivalTime <= currentTime) && (remainingTime[j] < minRemaining) && remainingTime[j] > 0)
                {
                    minRemaining = remainingTime[j];
                    shortestProcess = j;
                    check = true;
                }
            }
            if (!check)
            {
                currentTime++;
                for (int i = 0; i < n; i++) {
                    if (processes[i].arrivalTime <= currentTime && remainingTime[i] > 0) {
                        processes[i].agingTime++;
                        if (processes[i].agingTime >= 5) {
                            processes[i].agingTime = 0;
                            remainingTime[i]--;
                            executionOrder.add(processes[i].processId);
                            if (remainingTime[i] == 0) {
                                complete++;
                                finishTime = currentTime + 1;
                                wt[i] = finishTime - processes[i].burstTime - processes[i].arrivalTime;
                                if (wt[i] < 0)
                                    wt[i] = 0;
                            }
                        }
                    }
                }
                continue;
            }
            remainingTime[shortestProcess]--;
            executionOrder.add(processes[shortestProcess].processId);
            minRemaining = remainingTime[shortestProcess];
            if (minRemaining == 0)
                minRemaining = Integer.MAX_VALUE;

            if (remainingTime[shortestProcess] == 0) {
                complete++;
                check = false;
                finishTime = currentTime + 1;
                wt[shortestProcess] = finishTime - processes[shortestProcess].burstTime - processes[shortestProcess].arrivalTime;
                if (wt[shortestProcess] < 0)
                    wt[shortestProcess] = 0;
            }
            currentTime++;
        }
        System.out.println("Processes Execution Order:");
        if (!executionOrder.isEmpty()) {
            int prevPid = executionOrder.get(0);
            System.out.print("P" + prevPid + " ");
            for (int i = 1; i < executionOrder.size(); i++) {
                int currentPid = executionOrder.get(i);
                if (currentPid != prevPid) {
                    System.out.print("P" + currentPid + " ");
                    prevPid = currentPid;
                }
            }
        }
        System.out.println();
    }
    static void calculateTurnAroundTime(Process processes[], int n, int wt[], int tat[]) {
        for (int i = 0; i < n; i++)
            tat[i] = processes[i].burstTime + wt[i];
    }
}
