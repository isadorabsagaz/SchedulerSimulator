package org.example;

import org.example.infos.SchedulerInfo;
import org.example.infos.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private final SchedulerInfo info;
    private final Task[] tasks;
    private final Task[] cpu;
    private final List<Task> readyQueue;
    private final List<Task> finishedQueue;
    private final Logs log;
    private float utilizationSum = 0;

    public Scheduler(SchedulerInfo info) {
        this.info = info;
        this.tasks = info.getTasks();
        this.cpu = new Task[1];
        this.readyQueue = new ArrayList<>();
        this.finishedQueue = new ArrayList<>();
        this.log = new Logs();
        for (int i = 0; i < info.getTasks_number(); i++) {
            tasks[i].setId(i+1);
            tasks[i].setN(1);
        }
    }

    public void fcfs() {
        for (int i = 0; i <= info.getSimulation_time(); i++) {
            addToReadyQueue(i);

            if (cpu[0] != null) compute(i);
            if (cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            printLogs(i);
        }
        log.printLogs(info, finishedQueue);
    }

    public void roundRobin(){
        for (int i = 0; i <= info.getSimulation_time(); i++) {
            addToReadyQueue(i);

            if(cpu[0] != null) compute(i);
            if(cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            printLogs(i);
        }
        log.printLogs(info, finishedQueue);
    }

    public void rateMonotonic(){
        for (Task task : tasks){
            utilizationSum += (float) task.getComputation_time() / task.getPeriod_time();
        }

        if(utilizationSum > info.getTasks_number() * (Math.pow(2, (float) 1 / info.getTasks_number()) - 1)) {
            System.out.println("! Warning: The set is NOT scalable");
            return;
        }

        Task aux;
        for (int i = 0; i <= info.getSimulation_time(); i++) {
            for (int j = 0; j < info.getTasks_number() - 1; j++) {
                if(tasks[j].getPeriod_time() > tasks[j+1].getPeriod_time()){
                    aux = tasks[j];
                    tasks[j] = tasks[j+1];
                    tasks[j+1] = aux;
                }
            }
            addToReadyQueue(i);
            if(cpu[0] != null) compute(i);
            if(cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            checkMissedDeadline(i);
            printLogs(i);
        }
        log.printLogs(info, finishedQueue);
        log.printLogsPeriodic(info, utilizationSum, finishedQueue);
    }

    public void earliestDeadlineFirst() {
        for (Task task : tasks) {
            utilizationSum += (float) task.getComputation_time() / task.getPeriod_time();
        }

        if (utilizationSum > 1) {
            System.out.println( "! Warning: The set is NOT scalable");
            return;
        }

        Task aux;
        for (int i = 0; i <= info.getSimulation_time(); i++) {
            for (int j = 0; j < info.getTasks_number() - 1; j++) {
                if(tasks[j].getDeadline() > tasks[j+1].getDeadline()){
                    aux = tasks[j];
                    tasks[j] = tasks[j+1];
                    tasks[j+1] = aux;
                }
            }
            addToReadyQueue(i);
            if(cpu[0] != null) compute(i);
            if(cpu[0] == null && !readyQueue.isEmpty()) removeFromReadyQueue();

            checkMissedDeadline(i);
            printLogs(i);
        }
        log.printLogs(info, finishedQueue);
        log.printLogsPeriodic(info, utilizationSum, finishedQueue);
    }

    private void compute(int i){
        cpu[0].setComputation_time(cpu[0].getComputation_time() - 1);

        if (cpu[0].getQuantum() != 0) cpu[0].setQuantum(cpu[0].getQuantum() - 1);
        info.setTimeCpuUsed(info.getTimeCpuUsed() + 1);

        for (Task task : readyQueue) {
            if (task.getOffset() != i && (i - task.getOffset()) % task.getPeriod_time() != 0) {
                task.setWaiting_time(task.getWaiting_time() + 1);
            }
        }

        if (cpu[0].getComputation_time() == 0) {
            cpu[0].setComputation_time(cpu[0].getInitial_computation_time());
            finishedQueue.add(cpu[0]);
            cpu[0] = null;
        }
        else{
            switch (info.getScheduler_name()) {
                case "fcfs" -> {}
                case "rr" -> {
                    if (cpu[0].getQuantum() == 0) {
                        cpu[0].setQuantum(cpu[0].getInitial_quantum());
                        readyQueue.add(cpu[0]);
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
                    for (Task task : readyQueue){
                        if(cpu[0].getDeadline() > task.getDeadline()){
                            readyQueue.add(cpu[0]);
                            cpu[0] = task;
                            readyQueue.remove(task);
                        }
                    }
                }
            }
        }
    }

    private void addToReadyQueue(int i) {
        for (Task task : tasks){
            if (task.getOffset() == i){
                task.setInitial_computation_time(task.getComputation_time());
                task.setInitial_quantum(task.getQuantum());
                readyQueue.add(task);
            }
            else if((i - task.getOffset()) % task.getPeriod_time() == 0){
                task.setN(task.getN() + 1);
                Task periodicTask = new Task(i, task.getComputation_time(), task.getPeriod_time(), 0, task.getDeadline() * (task.getN()),
                      task.getId(), task.getN());
                readyQueue.add(periodicTask);
            }
        }
    }

    private void removeFromReadyQueue(){
        cpu[0] = readyQueue.get(0);
        readyQueue.remove(0);
    }

   private void checkMissedDeadline(int i){
        for (Task task : readyQueue){
            if (task.getDeadline() == i) {
                task.setMissedDeadline(true);
                task.setCountMissedDeadlines(task.getCountMissedDeadlines() + 1);
                System.out.println("! MISSED DEADLINE OF TASK "+task.getId()+"AT TIME "+ i);
            }
        }
    }

    private void printLogs(int i){
        System.out.println("* CURRENT TIME: " + i);
        System.out.println("* READY QUEUE: " + readyQueue);
        System.out.println("* CPU: " + cpu[0]);
        System.out.println("* FINISHED QUEUE: " + finishedQueue);
        System.out.println("==============================================================================================");
    }
}
