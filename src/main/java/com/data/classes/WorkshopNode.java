package com.data.classes;

public class WorkshopNode {
    private int workshopId;
    private int positionX;
    private int positionY;

    public WorkshopNode() {
        this(0, 0, 0);
    }

    public WorkshopNode(int workshopId, int positionX, int positionY) {
        this.workshopId = workshopId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getWorkshopId() {
        return workshopId;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void printWorkshopNode() {
        System.out.printf("|workshopID: %-5d|positionX: %-10d|positionY:  %-10d|%n", this.workshopId, this.positionX, this.positionY);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("workshopID: ").append(Integer.toString(this.workshopId))
                .append("\tpositionX: ").append(Integer.toString(this.positionX))
                .append("\tpositionY: ").append(Integer.toString(this.positionY));
        return strBuild.toString();
    }
}
