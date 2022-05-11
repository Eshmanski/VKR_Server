package com.data.classes;

public class Workshop {
    private int id;
    private String name;
    private String description;

    public Workshop() {
        this(0, "-", "-");
    }

    public Workshop(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return this.id; }

    public String getName() { return name; }

    public String getDescription() { return this.description; }

    public void printWorkshop() {
        System.out.printf("|id: %-5d|name: %-15s|description: %-100s|%n", this.id, this.name, this.description);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("id: ").append(Integer.toString(this.id))
                .append("\tname: ").append(this.name)
                .append("\tdescription: ").append(this.description);
        return strBuild.toString();
    }
}
