package com.data.classes;

public class EnterpriseModel {
    private int id;
    private String type;
    private String title;
    private int bodyId;

    public EnterpriseModel() {
        this(0, "-", "-", 0);
    }

    public EnterpriseModel(int id, String type, String title, int bodyId) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.bodyId = bodyId;
    }

    public void printModel() {
        System.out.printf("|id: %-5d|type: %-15s|title: %-15s|bodyId: %-5d|%n", this.id, this.type, this.title, this.bodyId);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("id: ").append(Integer.toString(this.id))
                .append("\ttype: ").append(this.type)
                .append("\ttitle: ").append(this.title)
                .append("\tbodyId: ").append(Integer.toString(this.bodyId));
        return strBuild.toString();
    }
}
