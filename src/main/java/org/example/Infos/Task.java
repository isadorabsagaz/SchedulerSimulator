package org.example.Infos;

public class Task {

    private int id;
    private int offset;
    private int computation_time;
    private int period_time;
    private int quantum;
    private int deadline;
    private int waiting_time;
    private int initial_computation_time;
    private int initial_quantum;
    private int n; //number of appearances
    private boolean missedDeadline;
    private int countMissedDeadlines;

    public Task(int offset, int computation_time, int period_time, int quantum, int deadline, int id, int n) {
        this.offset = offset;
        this.computation_time = computation_time;
        this.period_time = period_time;
        this.quantum = quantum;
        this.deadline = deadline;
        this.waiting_time = 0;
        this.initial_computation_time = computation_time;
        this.initial_quantum = quantum;
        this.n = n;
        this.id = id;
        this.missedDeadline = false;
        this.countMissedDeadlines = 0;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getComputation_time() {
        return computation_time;
    }

    public void setComputation_time(int computation_time) {
        this.computation_time = computation_time;
    }

    public int getPeriod_time() {
        return period_time;
    }

    public void setPeriod_time(int period_time) {
        this.period_time = period_time;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInitial_computation_time() {
        return initial_computation_time;
    }

    public void setInitial_computation_time(int initial_computation_time) {
        this.initial_computation_time = initial_computation_time;
    }

    public int getInitial_quantum() {
        return initial_quantum;
    }

    public void setInitial_quantum(int initial_quantum) {
        this.initial_quantum = initial_quantum;

    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getCountMissedDeadlines() {
        return countMissedDeadlines;
    }

    public void setCountMissedDeadlines(int countMissedDeadlines) {
        this.countMissedDeadlines = countMissedDeadlines;
    }

    public boolean isMissedDeadline() {
        return missedDeadline;
    }

    public void setMissedDeadline(boolean missedDeadline) {
        this.missedDeadline = missedDeadline;
    }

    @Override
    public String toString() {
        return "Task " +id+ " {" +
                " offset = " + offset +
                ", computation_time = " + computation_time +
                ", period_time = " + period_time +
                ", quantum = " + quantum +
                ", deadline = " + deadline +
                ", waiting_time = " + waiting_time +
                '}' +
                "\n";
    }
}
