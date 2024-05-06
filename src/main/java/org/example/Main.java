package org.example;
import org.example.Infos.SchedulerInfo;
import org.example.Jsons.JsonReader;

public class Main {
    public static void main(String[] args) {

        String schedulerName = "edf";
        SchedulerInfo schedulerInfos = new JsonReader().readJson(schedulerName);

        Scheduler scheduler = new Scheduler(schedulerInfos);

        switch(schedulerName){
            case "fcfs" -> scheduler.fcfs();
            case "rr" ->  scheduler.roundRobin();
            case "rm" -> scheduler.rateMonotonic();
            case "edf" ->  scheduler.earliestDeadlineFirst();
        }
    }
}