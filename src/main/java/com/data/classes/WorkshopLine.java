package com.data.classes;

public class WorkshopLine {
    private int start;
    private int end;

    public WorkshopLine() {
        this(0, 0);
    }

    public WorkshopLine(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void printWorkshopLine() {
        System.out.printf("|Start: %-5d|End: %-5d|%n", this.start, this.end);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("Start: ").append(Integer.toString(this.start))
                .append("\tEnd: ").append(Integer.toString(this.end));
        return strBuild.toString();
    }
}
