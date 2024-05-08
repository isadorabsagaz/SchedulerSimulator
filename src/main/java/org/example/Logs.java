package org.example;

import org.example.infos.SchedulerInfo;
import org.example.infos.Task;

import java.util.List;

public class Logs {
    private float waitingTimeSum;
    private float lifeTimeSum;

    public Logs() {
        this.waitingTimeSum = 0;
        this.lifeTimeSum = 0;
    }

    public void printLogs(SchedulerInfo info, List<Task> queue, float utilizationSum) {

        System.out.println("Waiting time and turnaround time of each task: ");
        for(Task task : queue){
            System.out.println("Task "+task.getId()+": WT = "+task.getWaiting_time() +
                    " | TT: "+(task.getComputation_time() + task.getWaiting_time()));

            waitingTimeSum += task.getWaiting_time();
            lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());


            if(task.getCountMissedDeadlines() != 0) {
                System.out.println("Lost deadline for task " + task.getId());
                System.out.println("Frequency of lost deadlines = "+ (float) (task.getN() - 1) / task.getCountMissedDeadlines());
            }
            if(task.getWaiting_time() > info.getSimulation_time()) System.out.println("Starvation of task " + task.getId());
        }

        Task biggestWaitingTime = queue.get(0);
        Task smallestWaitingTime = queue.get(0);
        for (int i = 0; i < queue.size() - 1; i++) {
            if(queue.get(i).getWaiting_time() > biggestWaitingTime.getWaiting_time()){
                biggestWaitingTime = queue.get(i);
            }
            else if(queue.get(i).getWaiting_time() < smallestWaitingTime.getWaiting_time()){
                smallestWaitingTime = queue.get(i);
            }
        }

        System.out.println("\nUtilization Time = "+(float) info.getTimeCpuUsed() / info.getSimulation_time());
        System.out.println("Productivity = "+(float) queue.size() / info.getSimulation_time());
        System.out.println("Waiting Time (avg sys) = "+ waitingTimeSum / queue.size());
        System.out.println("Turnaround Time (avg sys) = "+ lifeTimeSum / queue.size());
        System.out.println("\nBiggest and Smallest Waiting Time: \nTask "+biggestWaitingTime.getId()+" ("+biggestWaitingTime.getWaiting_time()+")"
                +" | Task "+smallestWaitingTime.getId()+" ("+smallestWaitingTime.getWaiting_time()+")");

        System.out.println("\nScalability Test: ");
        switch (info.getScheduler_name()){
            case "fcfs", "rr" -> System.out.println("Test is not necessary");
            case "rm" -> System.out.println("U = "+utilizationSum+" <= "+info.getTasks_number() * (Math.pow(2, (float) 1 / info.getTasks_number()) - 1));
            case "edf" -> System.out.println("U = "+utilizationSum+" <= "+1);
        }

        System.out.println("\nAverage waiting time and turnaround time of each task (multiple executions): ");
        for (int i = 1; i <= info.getTasks_number(); i++) {
            int appearances = 0;
            waitingTimeSum = 0;
            lifeTimeSum = 0;

            for(Task task : queue){
                if(task.getId() == i){
                    waitingTimeSum += task.getWaiting_time();
                    lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());
                    appearances = task.getN();
                }
            }
            System.out.println("Task "+i+" (appearances = "+appearances+"): WT avg = "+waitingTimeSum/appearances+
                    " | TT avg = " +lifeTimeSum/appearances);
        }
    }
}
