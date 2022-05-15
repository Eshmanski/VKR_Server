package com.data.classes;

public class UsedProduct {
    private int id;
    private int count;

    public UsedProduct() {
        this(0, 0);
    }

    public UsedProduct(int start, int end) {
        this.id = start;
        this.count = end;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void printUsedComponent() {
        System.out.printf("|id: %-5d|count: %-5d|%n", this.id, this.count);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("Id: ").append(Integer.toString(this.id))
                .append("\tCount: ").append(Integer.toString(this.count));
        return strBuild.toString();
    }
}
