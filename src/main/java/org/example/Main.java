package org.example;
import org.example.Infos.SchedulerInfo;
import org.example.Jsons.JsonReader;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        String schedulerName = "edf";
        SchedulerInfo schedulerInfo = new JsonReader().readJson(schedulerName);

        switch(schedulerName){
            case "fcfs" -> scheduler.fcfs(schedulerInfo);
            case "rr" ->  scheduler.roundRobin(schedulerInfo);
            case "rm" -> scheduler.rateMonotonic(schedulerInfo);
            case "edf" ->  scheduler.earliestDeadlineFirst(schedulerInfo);

        }
    }
}