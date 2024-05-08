package org.example.infos;

import java.util.Arrays;

public class SchedulerInfo {

    private int simulation_time;
    private String scheduler_name;
    private int tasks_number;
    private Task[] tasks;
    private int timeCpuUsed;

    public SchedulerInfo(int simulation_time, String scheduler_name, int tasks_number, Task[] tasks) {
        this.simulation_time = simulation_time;
        this.scheduler_name = scheduler_name;
        this.tasks_number = tasks_number;
        this.tasks = tasks;
        this.timeCpuUsed = 0;
    }

    public int getSimulation_time() {
        return simulation_time;
    }

    public void setSimulation_time(int simulation_time) {
        this.simulation_time = simulation_time;
    }

    public String getScheduler_name() {
        return scheduler_name;
    }

    public void setScheduler_name(String scheduler_name) {
        this.scheduler_name = scheduler_name;
    }

    public int getTasks_number() {
        return tasks_number;
    }

    public void setTasks_number(int tasks_number) {
        this.tasks_number = tasks_number;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public int getTimeCpuUsed() {
        return timeCpuUsed;
    }

    public void setTimeCpuUsed(int timeCpuUsed) {
        this.timeCpuUsed = timeCpuUsed;
    }

    @Override
    public String toString() {
        return  "\n - SCHEDULER - " +
                "\n # Scheduler Name: " + scheduler_name.toUpperCase() +
                "\n # Simulation Time: " + simulation_time +
                "\n # Tasks Number: " + tasks_number +
                "\n # Tasks: " + "\n"+Arrays.toString(tasks);
    }

}
