package com.data.classes;

public class Route {
    private int id;
    private WorkshopNode[] workshopNodes;
    private WorkshopLine[] workshopLines;

    public Route() {
        this(0, new WorkshopNode[] {}, new WorkshopLine[] {});
    }

    public Route(int id, WorkshopNode[] workshopNodes, WorkshopLine[] workshopLines) {
        this.id = id;
        this.workshopNodes = workshopNodes;
        this.workshopLines = workshopLines;
    }

    public int getId() { return this.id; }

    public WorkshopNode[] getWorkshopNodes() { return this.workshopNodes; }

    public WorkshopLine[] getWorkshopLines() { return this.workshopLines; }

    public void printRoute() {
        System.out.printf("|id: %-5d|%n", this.id);

        System.out.println("===WorkshopNodes===");
        if(workshopNodes.length != 0) {
            for(WorkshopNode workshopNode : this.workshopNodes) workshopNode.printWorkshopNode();
        } else System.out.println("Empty");

        System.out.println("===WorkshopLines===");
        if(workshopLines.length != 0) {
            for(WorkshopLine workshopLine : this.workshopLines) workshopLine.printWorkshopLine();
        } else System.out.println("Empty");
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("id: ").append(Integer.toString(this.id)).append("\n")
                .append("===WorkshopNodes==\n");

        if(workshopNodes.length != 0) {
            for(WorkshopNode workshopNode : this.workshopNodes) strBuild.append(workshopNode.toString()).append("\n");
        } else strBuild.append("Empty").append("\n");

        strBuild.append("===WorkshopLines==\n");
        if(workshopLines.length != 0) {
            for(WorkshopLine workshopLine : this.workshopLines) strBuild.append(workshopLine.toString()).append("\n");
        } else strBuild.append("Empty").append("\n");

        return strBuild.toString();
    }
}
