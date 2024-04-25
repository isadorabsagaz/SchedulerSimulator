package org.example;

import org.example.Infos.SchedulerInfo;
import org.example.Infos.Task;

import java.util.List;

public class Logs {

    public void printLogs(SchedulerInfo info, List<Task> queue) {
        int waitingSum = 0;
        int lifeTimeSum = 0;

        System.out.println("Waiting time and turnaround time of each task: ");

        for(Task task : queue){
            System.out.println("Task "+task.getId()+": WT = "+task.getWaiting_time() +
                    " | TT: "+(task.getComputation_time() + task.getWaiting_time()));

            waitingSum += task.getWaiting_time();
            lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());
        }
        System.out.println("\nUtilization Time = "+(double) info.getTimeCpuUsed() / info.getSimulation_time());
        System.out.println("Productivity = "+(double) info.getTasks_number() / info.getSimulation_time());
        System.out.println("Waiting Time = "+(double) waitingSum / info.getTasks_number());
        System.out.println("Tournaround Time = "+(double) lifeTimeSum / info.getTasks_number());

    }
}
