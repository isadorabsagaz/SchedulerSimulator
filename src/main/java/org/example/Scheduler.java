package org.example;

import org.example.Infos.SchedulerInfo;
import org.example.Infos.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private final Task[] cpu;
    private final List<Task> readyQueue;
    private final List<Task> finishedQueue;
    private final Logs log;

    public Scheduler() {
        this.cpu = new Task[1];
        this.readyQueue = new ArrayList<>();
        this.finishedQueue = new ArrayList<>();
        this.log = new Logs();
    }

    public void fcfs(SchedulerInfo info) {
        Task[] tasks = info.getTasks();

        for (int i = 0; i <= info.getSimulation_time(); i++) {
            addToReadyQueue(i, tasks);

            if (cpu[0] != null) compute(i, info, false);
            if (cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            printLogs(i);
        }
        log.calculateStatistics(info);
    }

    public void roundRobin(SchedulerInfo info){
        Task[] tasks = info.getTasks();

        for (int i = 0; i <= info.getSimulation_time(); i++) {
            addToReadyQueue(i, tasks);

            if(cpu[0] != null) compute(i, info,true);
            if(cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            printLogs(i);
        }
        log.calculateStatistics(info);
    }

    public void rateMonotonic(SchedulerInfo info){
        Task[] tasks = info.getTasks();
        Task aux = null;

        for (int i = 0; i <= info.getSimulation_time(); i++) {
            for (int j = 0; j < info.getTasks_number() - 1; j++) {
                if(tasks[j].getPeriod_time() > tasks[j+1].getPeriod_time()){
                    aux = tasks[j];
                    tasks[j] = tasks[j+1];
                    tasks[j+1] = aux;
                }
            }
            addToReadyQueue(i, tasks);
            if(cpu[0] != null) compute(i, info, false);
            if(cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            printLogs(i);
        }
        log.calculateStatistics(info);
    }

    public void erliestDeadlineFirst(SchedulerInfo info){

    }

    private void compute(int i, SchedulerInfo info, boolean hasQuantum){
        cpu[0].setComputation_time(cpu[0].getComputation_time() - 1); //computa quem estiver na cpu
        info.setTimeCpuUsed(info.getTimeCpuUsed() + 1);
        if(hasQuantum) cpu[0].setQuantum(cpu[0].getQuantum() - 1); //decrementa quantum

        for (Task task : readyQueue) {
            if (task.getOffset() != i) task.setWaiting_time(task.getWaiting_time() + 1);  //add wainting time
        }

        if (cpu[0].getComputation_time() == 0) {
            cpu[0].setComputation_time(cpu[0].getInitial_computation_time());  //set computation_time task for the original value
            finishedQueue.add(cpu[0]); //add to finishedQueue
            cpu[0] = null;
        }
        else{
            switch (info.getScheduler_name()) {
                case "fcfs" -> {}
                case "rr" -> {
                    if (cpu[0].getQuantum() == 0) {
                        cpu[0].setQuantum(cpu[0].getInitial_quantum()); //restart quantum
                        readyQueue.add(cpu[0]); //add to readyQueue again
                        cpu[0] = null;
                    }
                }
                case "rm" -> {
                    for (Task task : readyQueue) {
                        if (cpu[0].getPeriod_time() > task.getPeriod_time()) {
                            readyQueue.add(cpu[0]);
                            cpu[0] = task;
                            readyQueue.remove(task);
                        }
                    }
                }
                case "edf" -> {
                    System.out.println();
                }
            }
        }
    }

    private void addToReadyQueue(int i, Task[] tasks){
        for (Task task : tasks) {
            if (task.getOffset() == i || (i - task.getOffset()) % task.getPeriod_time() == 0){ //
                task.setInitial_computation_time(task.getComputation_time());
                task.setInitial_quantum(task.getQuantum());
                readyQueue.add(task);
            }
        }
    }

    private void removeFromReadyQueue(){
        cpu[0] = readyQueue.get(0);
        readyQueue.remove(0);
    }

    private void printLogs(int i){
        System.out.println("* CURRENT TIME: " + i);
        System.out.println("* READY QUEUE: " + readyQueue);
        System.out.println("* CPU: " + cpu[0]);
        System.out.println("* FINISHED QUEUE: " + finishedQueue);
        System.out.println("==============================================================================================");
    }
}
