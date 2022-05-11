package com.data.classes;

public class Component {
    private int id;
    private String name;
    private String drawing;
    private int routeId;
    private String description;

    public Component() {
        this(0, "-", "-", 0, "-");
    }

    public Component(int id, String name, String drawing, int routeId, String description) {
        this.id = id;
        this.name = name;
        this.drawing = drawing;
        this.routeId = routeId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDrawing() {
        return drawing;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getDescription() {
        return description;
    }

    public void printComponent() {
        System.out.printf("|id: %-5d|name: %-15s|drawing: %-15s|routeId: %-5d|description: %-100s|%n",
                this.id, this.name, this.drawing, this.routeId, this.description);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("id: ").append(Integer.toString(this.id))
                .append("\tname: ").append(this.name)
                .append("\tdrawing: ").append(this.drawing)
                .append("\trouteId: ").append(Integer.toString(this.routeId))
                .append("\tdescription: ").append(this.description);
        return strBuild.toString();
    }
}
