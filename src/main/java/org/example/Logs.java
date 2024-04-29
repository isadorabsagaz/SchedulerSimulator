package org.example;

import org.example.Infos.SchedulerInfo;
import org.example.Infos.Task;

import java.util.List;

public class Logs {

    public void printLogs(SchedulerInfo info, List<Task> queue, boolean scalable) {
        float waitingSum = 0;
        float lifeTimeSum = 0;

        System.out.println("Waiting time and turnaround time of each task: ");

        for(Task task : queue){
            System.out.println("Task "+task.getId()+": WT = "+task.getWaiting_time() +
                    " | TT: "+(task.getComputation_time() + task.getWaiting_time()));

            waitingSum += task.getWaiting_time();
            lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());
        }
        if(scalable) System.out.println("\nThe set IS scalable!");
        System.out.println("\nUtilization Time = "+(float) info.getTimeCpuUsed() / info.getSimulation_time());
        System.out.println("Productivity = "+(float) info.getTasks_number() / info.getSimulation_time());
        System.out.println("Waiting Time (avg) = "+ waitingSum / info.getTasks_number());
        System.out.println("Tournaround Time (avg) = "+ lifeTimeSum / info.getTasks_number());
    }
}
