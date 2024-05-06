package org.example;

import org.example.infos.SchedulerInfo;
import org.example.infos.Task;

import java.util.List;

public class Logs {

    public void printLogs(SchedulerInfo info, List<Task> queue) {
        float waitingSum = 0;
        float lifeTimeSum = 0;
        Task biggestWaitingTime = queue.get(0);
        Task smallestWaitingTime = queue.get(0);

        System.out.println("Waiting time and turnaround time of each task: ");

        for(Task task : queue){
            System.out.println("Task "+task.getId()+": WT = "+task.getWaiting_time() +
                    " | TT: "+(task.getComputation_time() + task.getWaiting_time()));

            waitingSum += task.getWaiting_time();
            lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());
        }

        for (int i = 0; i < queue.size() - 1; i++) {
            if(queue.get(i).getWaiting_time() > biggestWaitingTime.getWaiting_time()){
                biggestWaitingTime = queue.get(i);
            }
            else if(queue.get(i).getWaiting_time() < smallestWaitingTime.getWaiting_time()){
                smallestWaitingTime = queue.get(i);
            }
        }
        System.out.println("\nUtilization Time = "+(float) info.getTimeCpuUsed() / info.getSimulation_time());
        System.out.println("Productivity = "+(float) info.getTasks_number() / info.getSimulation_time());
        System.out.println("Waiting Time (avg sys) = "+ waitingSum / info.getTasks_number());
        System.out.println("Turnaround Time (avg sys) = "+ lifeTimeSum / info.getTasks_number());
        System.out.println("\nBiggest and Smallest Waiting Time: Task "+biggestWaitingTime.getId()+"("+biggestWaitingTime.getWaiting_time()+")"
                +" | Task "+smallestWaitingTime.getId()+"("+smallestWaitingTime.getWaiting_time()+")");
    }


    public void printLogsPeriodic(SchedulerInfo info, float utilizationSum, List<Task> queue){
        System.out.println("\nScalability Test: ");
        switch (info.getScheduler_name()){
           case "rm" -> System.out.println("U = "+utilizationSum+" <= "+info.getTasks_number() * (Math.pow(2, (float) 1 / info.getTasks_number()) - 1));
           case "edf" -> System.out.println("U = "+utilizationSum+" <= "+1);
        }

        System.out.println("\nAverage waiting time and turnaround time of each task (multiple executions): ");
        for (int i = 1; i <= info.getTasks_number(); i++) {
            float waitingTimeSum = 0;
            float lifeTimeSum = 0;
            int appearances = 0;

            for(Task task : queue){
                if(task.getId() == i){
                    waitingTimeSum += task.getWaiting_time();
                    lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());
                    appearances++;
                }
            }
            System.out.println("Task "+i+" (appearances = "+appearances+"): WT avg = "+waitingTimeSum/appearances+
                    " | TT avg = " +lifeTimeSum/appearances);
        }

        for(Task task : queue){
            if(task.isMissedDeadline()){
                System.out.println("Frequency of task "+task.getId()+" losing deadlines = "+ (float) (task.getN() - 1) /task.getCountMissedDeadlines());
            }
        }
    }
}
