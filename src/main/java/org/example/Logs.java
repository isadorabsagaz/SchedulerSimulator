package org.example;

import org.example.Infos.SchedulerInfo;
import org.example.Infos.Task;

public class Logs {

    private double utilizationTime;
    private double utilizationTimeRM;
    private double utilizationTimeEDF;
    private double tournAroundTime;
    private double waitingTime;
    private double productivity;

    public Logs() {}

    public void calculateStatistics(SchedulerInfo info) {
        int waitingSum = 0;
        int lifeTimeSum = 0;
        for(Task task : info.getTasks()){
            waitingSum += task.getWaiting_time();
            lifeTimeSum += (task.getComputation_time() + task.getWaiting_time());
        }
        this.utilizationTime = (double) info.getTimeCpuUsed() / info.getSimulation_time();
        this.productivity = (double) info.getTasks_number() / info.getSimulation_time();
        this.waitingTime = (double) waitingSum / info.getTasks_number();
        this.tournAroundTime = (double) lifeTimeSum / info.getTasks_number();

        printLogs();
    }

    public void printLogs() {
        System.out.println("Utilization Time = "+utilizationTime);
        System.out.println("Productivity = "+productivity);
        System.out.println("Waiting Time = "+waitingTime);
        System.out.println("Tourn Around Time = "+tournAroundTime);
    }
}
